package com.lvmama.tnt.test;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.lvmama.boot.starter.jms.JmsUtils;
import com.lvmama.comm.jms.MessageFactory;
import com.lvmama.comm.pet.po.pay.PayPayment;
import com.lvmama.comm.pet.service.pay.PayPaymentService;
import com.lvmama.order.api.base.vo.RequestBody;
import com.lvmama.order.api.base.vo.ResponseBody;
import com.lvmama.order.service.api.comm.order.IApiOrderQueryService;
import com.lvmama.order.vo.comm.OrderVo;
import com.lvmama.scenic.api.hoard.service.ScenicHoardCodeGoodsService;
import com.lvmama.scenic.api.hoard.service.ScenicHoardService;
import com.lvmama.scenic.api.hoard.vo.HoardCodeGoodsStockVo;
import com.lvmama.scenic.api.hoard.vo.HoardCodeGoodsVO;
import com.lvmama.scenic.api.order.service.ScenicOrderRequiredService;
import com.lvmama.scenic.api.order.vo.ScenicOrdRequiredVO;
import com.lvmama.scenic.api.vo.ResultHandleT;
import com.lvmama.tnt.biz.channel.NoticeChannel;
import com.lvmama.tnt.biz.channel.NoticeRequest;
import com.lvmama.tnt.biz.channel.NoticeService;
import com.lvmama.tnt.biz.service.TntCodeInfoService;
import com.lvmama.tnt.biz.voucher.ExportCodeVoucherService;
import com.lvmama.tnt.common.util.BatchNoCreater;
import com.lvmama.tnt.dal.domain.TntCodeInfoPO;
import com.lvmama.tnt.prod.po.TntProduct;
import com.lvmama.tnt.reference.service.ITntProductRefService;
import com.lvmama.tnt.reference.service.impl.VstCommOrderQueryServiceRemote;
import com.lvmama.vst.api.order.vo.OrdPassCodeVo;
import com.lvmama.vst.api.passport.service.VstPassportService;
import com.lvmama.vst.api.vo.PassFileVo;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 *
 */
public class ServiceTest extends AbstractTest {
    @Reference
    public ScenicHoardCodeGoodsService scenicHoardCodeGoodsService;

    @Autowired
    private ITntProductRefService tntProductRefService;

    @Autowired
    private NoticeService noticeService;

    @Reference
    private ScenicHoardService scenicHoardService;

    @Reference
    private ScenicOrderRequiredService scenicOrderRequiredService;

    @Reference
    private IApiOrderQueryService iApiOrderQueryService;

    @Reference
    private PayPaymentService paymentService;

    @Reference
    private VstPassportService vstPassportService;

    @Autowired
    private TntCodeInfoService tntCodeInfoService;

    @Autowired
    private ExportCodeVoucherService exportCodeVoucherService;

    @Autowired
    private VstCommOrderQueryServiceRemote vstCommOrderQueryServiceRemote;

    @Test
    public void testScenicHoardCodeGoodsService(){
        ResultHandleT<HoardCodeGoodsVO> resultHandleT =  scenicHoardCodeGoodsService.getHoardCodeGoods(4081612L);
        if (resultHandleT.isSuccess() && resultHandleT.getReturnContent() != null) {
            logger.info("HoardCodeGoodsVO data:"+ JSON.toJSONString(resultHandleT));

        }
    }

    @Test
    public void testScenicHoardCodeGoodsListService(){
        ResultHandleT<List<HoardCodeGoodsStockVo>> resultHandleT =  scenicHoardCodeGoodsService.listHoardCodeGoods(2068165L);
        if (resultHandleT.isSuccess() && resultHandleT.getReturnContent() != null) {
            logger.info("HoardCodeGoodsVO data:"+ JSON.toJSONString(resultHandleT));

        }
    }


    @Test
    public void testProductInfoService(){
        TntProduct tntProduct = tntProductRefService.findProductInfoByGoodsId(2068165L);
        System.out.println(tntProduct);
    }


    @Test
    public void testNoticeService(){
        NoticeRequest request = NoticeRequest.newBuilder().channel(NoticeChannel.SMS)
                .content("导码完成").addressList(Arrays.asList("13122675190")).build();

        noticeService.notice(request);

    }

    @Test
    public void testOrderRequired(){
        ResultHandleT<ScenicOrdRequiredVO> resultHandleT = scenicOrderRequiredService.findOrderRequiredListId(null, Arrays.asList(934496L));
        if (resultHandleT != null){
            ScenicOrdRequiredVO requiredVO =  resultHandleT.getReturnContent();
        }


    }

    @Test
    public void testLockStock() {
        String batchNo = BatchNoCreater.batchNoGenerator();
        ResultHandleT<HoardCodeGoodsStockVo> stockVoResultHandleT =  scenicHoardService.checkLockHoardStock(batchNo, 2068165L, parseDate("2018-03-26","yyyy-MM-dd"), 1L);
        System.out.println(stockVoResultHandleT.getReturnContent());
    }

    @Test
    public void testReleaseStock(){
        String batchNo = "DM20180403798948";
        ResultHandleT<Boolean> resultHandleT =  scenicHoardService.releaseHoardStock(batchNo);
        System.out.println(resultHandleT.getReturnContent());


    }

    private Date parseDate(String date, String pattern){
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);

        try {
            return sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Test
    public void testApiOrderQueryService(){
        RequestBody<String> requestBody = new RequestBody<>();
        requestBody.setT("DM20180330609126");

        ResponseBody<List<OrderVo>> orders =  iApiOrderQueryService.listOrdorderByBatchNo(requestBody);
        System.out.println(orders);

    }



    @Test
    public void testPayPayment(){
        PayPayment payPayment = paymentService.selectByPaymentId(3535278L);

        System.out.println(payPayment);
    }


    @Test
    public void testJms(){

        JmsUtils.sendTopicMessage(MessageFactory.newPaymentSuccessCallMessage(11111L), "ActiveMQ.RESOURCE");
        System.out.println("");
    }


    @Test
    public void testVstPassportService(){
        Long orderId = 62991099L;
        Long orderItemId = 2024934041L ;
      com.lvmama.vst.api.vo.ResultHandleT<PassFileVo> passFileVoResultHandleT =  vstPassportService.queryIntfPassFile(orderItemId);
        System.out.println(passFileVoResultHandleT.getReturnContent().getFileIds());

        List<Long> fileIds = passFileVoResultHandleT.getReturnContent().getFileIds();
        for(Long fileId : fileIds){
            TntCodeInfoPO codeInfoPO = new TntCodeInfoPO();
            codeInfoPO.setBatchNo("DM20180608001");
            codeInfoPO.setOrderItemId(orderItemId++);
            codeInfoPO.setOrderId(orderId ++);
            codeInfoPO.setPdf("pic.lvmama.com/pdf");
            codeInfoPO.setUseStatus("NOUSE");
            codeInfoPO.setFileId(fileId);
            codeInfoPO.setPassCodeId(10000L);
            codeInfoPO.setCodeUrl("");
            tntCodeInfoService.saveCodeInfo(codeInfoPO);


        }


    }


    @Test
    public void testUploadPdfZip(){
        exportCodeVoucherService.uploadPdfZip("DM20180608001");
    }

    @Test
    public void testGetFileId(){

       com.lvmama.vst.api.vo.ResultHandleT<OrdPassCodeVo> resultHandleT =  vstCommOrderQueryServiceRemote.getOrdPassCodeByOrderItemId(2024927368L);
       Long fileId = resultHandleT.getReturnContent().getFileId();


    }


    @Test
    public void testZip(){
        try {
            File temp = File.createTempFile("zipTest", ".zip");
            temp.delete();

            ZipFile zipFile = new ZipFile(temp.getAbsoluteFile());
            ZipParameters parameters = new ZipParameters();
            parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
            parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);    // 压缩级别
            zipFile.addFile(new File("E:\\songjunbao\\work\\tnt-soft\\process.png"), parameters);



        }catch (Exception e){
            e.printStackTrace();
        }



    }




}
