package com.lvmama.tnt.biz.voucher;

import com.lvmama.tnt.biz.payment.ExportCodeOrderPaymentServiceImpl;
import com.lvmama.tnt.biz.service.TntCodeBatchService;
import com.lvmama.tnt.biz.service.TntCodeInfoService;
import com.lvmama.tnt.dal.domain.TntCodeInfoPO;
import com.lvmama.tnt.reference.service.impl.FsClientRemote;
import com.lvmama.tnt.reference.service.impl.VstCommOrderQueryServiceRemote;
import com.lvmama.tnt.reference.service.impl.VstPassportServiceRemote;
import com.lvmama.vst.api.order.vo.OrdPassCodeVo;
import com.lvmama.vst.api.vo.ComFileVo;
import com.lvmama.vst.api.vo.ResultHandleT;
import net.lingala.zip4j.core.ZipFile;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

/**
 * @author songjunbao
 * @createdate 2018/6/7
 */
@Service("exportCodeVoucherServiceImpl")
public class ExportCodeVoucherServiceImpl implements ExportCodeVoucherService {
    private static Logger logger = LoggerFactory.getLogger(ExportCodeOrderPaymentServiceImpl.class);


    @Autowired
    private TntCodeInfoService tntCodeInfoService;

    @Autowired
    private VstPassportServiceRemote vstPassportServiceRemote;

    @Autowired
    private FsClientRemote fsClientRemote;

    @Autowired
    private TntCodeBatchService tntCodeBatchService;

    @Autowired
    private VstCommOrderQueryServiceRemote vstCommOrderQueryServiceRemote;

    @Override
    public Long uploadPdfZip(String batchNo) {
        if (StringUtils.isEmpty(batchNo)){
            throw new IllegalArgumentException("batchNo is null");
        }

        List<TntCodeInfoPO> codeInfoList = tntCodeInfoService.findCodeInfo(batchNo);
        if (CollectionUtils.isEmpty(codeInfoList)){
            return null;
        }
        String destDirPath = System.getProperty("java.io.tmpdir") + "/PDF_"+ batchNo;
        String filePath = FileUtils.createDir(destDirPath);
        logger.info("创建的缓存文件夹临时路径："+destDirPath+"创建完的文件夹路径："+filePath+"   batchNo="+batchNo);
        downPDFByFileId(filePath, batchNo, codeInfoList);
        //压缩文件目标路径
        String path = System.getProperty("java.io.tmpdir") + "/PDF_"+ batchNo + ".zip";
        //将文件夹开始进行压缩
        ZipFile zipFile = FileUtils.zip(filePath, path );
        if(zipFile!=null){
            logger.info("压缩文件目标路径:"+ path +"压缩完的路径:"+zipFile.getFile().getPath());
            //如果压缩文件存在了，可以将压缩前的文件架删除
            if(zipFile.getFile().exists()){
                File dir = new File(System.getProperty("java.io.tmpdir") + "/PDF_"+ batchNo);
                FileUtils.deleat(dir);
            }
            byte[] bytes = FileUtils.getBytes(zipFile.getFile().getPath());
            return uploadPDFZipFile(zipFile, bytes, batchNo);
        }
        return null;
    }

    /**
     * 下载pdf到服务器临时文件夹中
     * @param
     * @return
     */
    private void downPDFByFileId(String filePath, String batchNo,List<TntCodeInfoPO> codeInfoList) {
        for (TntCodeInfoPO codeInfo : codeInfoList) {

            //判断是否PDF凭证
            if(codeInfo.getPdf() == null){
                continue;
            }

            Long fileId = codeInfo.getFileId() ;

            try {
                if (fileId == null){
                    //兼容历史数据
                    fileId = getFileIdByOrderItemId(codeInfo.getOrderItemId());
                }

                if (fileId == null){
                    continue;
                }

                ResultHandleT<ComFileVo> downloadPDFFileResult = vstPassportServiceRemote.downloadPDFFile(fileId);
                if (downloadPDFFileResult.isFail() || downloadPDFFileResult.getReturnContent() == null || downloadPDFFileResult.getReturnContent().getFileName()==null) {
                    logger.info("获取ComFileVo失败 fileId="+fileId + " batchNo=" + batchNo);
                    continue;
                }
                ComFileVo comFileVo = downloadPDFFileResult.getReturnContent();
                String newFileName = comFileVo.getFileName().replace(comFileVo.getFileName(), codeInfo.getOrderId() + ".pdf");

                //将获取到的pdf文件放入文件夹下
                FileUtils.getFile(comFileVo.getFileData(), filePath, newFileName);

            } catch (Exception e) {
                logger.error("下载文件异常 comByFileId orderId = "+ codeInfo.getOrderId() +", fileId = " + codeInfo.getFileId() ,e);
            }
        }
    }

    private Long getFileIdByOrderItemId(Long orderItemId){
        ResultHandleT<OrdPassCodeVo> resultHandleT =  vstCommOrderQueryServiceRemote.getOrdPassCodeByOrderItemId(orderItemId);
        if (resultHandleT == null || resultHandleT.isFail() || resultHandleT.getReturnContent() == null){
            return null;
        }

        Long fileId = resultHandleT.getReturnContent().getFileId();
        logger.info("调用getOrdPassCodeByOrderItemId接口获取fileId：" + fileId);
        return fileId;
    }



    private Long uploadPDFZipFile(ZipFile zipFile, byte[] zipByte, String batchNo){
        logger.info("上传开始，name is :"+zipFile.getFile().getName());
        try {
            Long uploadFileId = fsClientRemote.uploadFile(zipFile.getFile().getName(), zipByte,"COM_AFFIX");
            logger.info("压缩并上传,批次号={},上传的压缩文件Id={}",batchNo,uploadFileId);
            if(uploadFileId != null){
                //更新导码批次表里的fileId
                tntCodeBatchService.updateFileId(batchNo, uploadFileId);
            }
            return uploadFileId;
        } catch (Exception e) {
            logger.error("上传或者更新异常：批次号="+batchNo , e);
        }finally{
            //将压缩完的zip文件删除
            if(zipFile.getFile().exists() && zipFile.getFile().isFile()){
                zipFile.getFile().delete();
            }
        }
        return null;
    }

}
