package com.lvmama.tnt.biz.service.impl.remote;

import com.alibaba.dubbo.config.annotation.Service;
import com.lvmama.tnt.biz.service.TntCodeInfoService;
import com.lvmama.tnt.biz.voucher.ExportCodeVoucherService;
import com.lvmama.tnt.comm.util.order.StringUtil;
import com.lvmama.tnt.common.domain.ResponseDTO;
import com.lvmama.tnt.common.util.BeanCopyUtils;
import com.lvmama.tnt.dal.domain.TntCodeInfoPO;
import com.lvmama.tnt.dal.domain.TntExportCodePO;
import com.lvmama.tnt.export.code.api.constant.CodeUseStatus;
import com.lvmama.tnt.export.code.api.domain.dto.ExportCodeInfoDTO;
import com.lvmama.tnt.export.code.api.domain.dto.TntCodeInfoDTO;
import com.lvmama.tnt.export.code.api.domain.vo.ExportCodeInfoVO;
import com.lvmama.tnt.export.code.api.service.ITntCodeInfoRemoteService;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.xssf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 */
@Service(group = "export", timeout = 600000)
public class TntCodeInfoRemoteServiceImpl implements ITntCodeInfoRemoteService {
    private Logger logger = LoggerFactory.getLogger(TntCodeInfoRemoteServiceImpl.class);

    private static final String QR_CODE_RUL = "http://pic.lvmama.com";

    @Autowired
    private TntCodeInfoService tntCodeInfoService;

    @Autowired
    private ExportCodeVoucherService exportCodeVoucherService;

    @Override
    public ResponseDTO<String> saveOrUpdateCodeInfo(TntCodeInfoDTO tntCodeInfoDTO) {
        logger.info("sync tntCodeInfo : {}", tntCodeInfoDTO);
        ResponseDTO<String> responseDTO = new ResponseDTO<String>();
        if (tntCodeInfoDTO == null && tntCodeInfoDTO.getOrderId() == null) {
            responseDTO.setSuccess(false);
            responseDTO.setRespMsg("orderId is null");
        }

        String batchNo = tntCodeInfoDTO.getBatchNo();
        Long orderId = tntCodeInfoDTO.getOrderId();

        try {
            TntCodeInfoPO tntCodeInfoPO = new TntCodeInfoPO();
            BeanUtils.copyProperties(tntCodeInfoDTO, tntCodeInfoPO);
            if (tntCodeInfoService.isBatchOrderExist(batchNo, orderId)) {
                tntCodeInfoService.updateCodeInfo(tntCodeInfoPO);
            } else {
                tntCodeInfoService.saveCodeInfo(tntCodeInfoPO);
            }
        } catch (Exception e) {
            logger.error("", e);
            responseDTO.setSuccess(false);
            responseDTO.setRespMsg(e.getMessage());
        }

        return responseDTO;
    }

    @Override
    public ResponseDTO<List<ExportCodeInfoDTO>> exportCode(ExportCodeInfoDTO exportCodeInfoDTO) {
        List<ExportCodeInfoDTO> list = new ArrayList<>();
        ResponseDTO responseDTO = new ResponseDTO();
        if (exportCodeInfoDTO == null) {
            responseDTO.setSuccess(false);
            responseDTO.setRespMsg("param is null");
        }
        TntExportCodePO tntExportCodePO = new TntExportCodePO();
        BeanCopyUtils.copyBean(exportCodeInfoDTO, tntExportCodePO);
        List<TntExportCodePO> exportCodePOS = tntCodeInfoService.findExportCodeByParam(tntExportCodePO);

        if (exportCodePOS != null) {
            list = new ArrayList<>(exportCodePOS.size());
            ExportCodeInfoDTO dto = null;
            for (TntExportCodePO po : exportCodePOS) {
                dto = new ExportCodeInfoDTO();
                BeanCopyUtils.copyBean(po, dto);
                list.add(dto);
            }
            responseDTO.setResult(list);
        }
        return responseDTO;
    }

    @Override
    public ResponseDTO<ExportCodeInfoVO> exportCodeInfo(ExportCodeInfoDTO exportCodeInfoDTO) {
        ResponseDTO responseDTO = new ResponseDTO();
        if (exportCodeInfoDTO == null) {
            responseDTO.setSuccess(false);
            responseDTO.setRespMsg("param is null");
        }
        String batchNo = exportCodeInfoDTO.getBatchNo();
        Long orderId = exportCodeInfoDTO.getOrderId();
        TntExportCodePO tntExportCodePO = new TntExportCodePO();
        BeanCopyUtils.copyBean(exportCodeInfoDTO, tntExportCodePO);
        List<TntExportCodePO> exportCodePOS = tntCodeInfoService.findExportCodeByParam(tntExportCodePO);
        ExportCodeInfoVO vo = new ExportCodeInfoVO();
        String fileName = orderId == null ? batchNo : String.valueOf(orderId);
        if (exportCodePOS != null) {
            byte[] data = writeData(exportCodePOS);
            vo.setBody(data);
            vo.setFileName(fileName+".xlsx");
        }
        responseDTO.setResult(vo);
        return responseDTO;
    }

    private byte[] writeData (List<TntExportCodePO> exportCodePOS){
        XSSFWorkbook workbook = new XSSFWorkbook();
        //获取参数个数作为excel列数
        int columeCount = 13;
        //获取List size作为excel行数
        int rowCount = exportCodePOS.size();
        XSSFSheet sheet = workbook.createSheet("导码凭证");
        //创建第一栏
        XSSFRow headRow = sheet.createRow(0);
        String[] titleArray = {"批次号", "订单号", "产品ID", "产品名称", "商品ID", "商品名称", "联系人", "联系人手机", "游玩日期", "辅助码（数字）", "二维码（URL）", "PDF（URL)", "分销商ID"};
        for (int m = 0; m <= columeCount - 1; m++) {
            XSSFCell cell = headRow.createCell(m);
            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
            sheet.setColumnWidth(m, 6000);
            XSSFCellStyle style = workbook.createCellStyle();
            XSSFFont font = workbook.createFont();
            font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            short color = HSSFColor.RED.index;
            font.setColor(color);
            style.setFont(font);
            //填写数据
            cell.setCellStyle(style);
            cell.setCellValue(titleArray[m]);
        }
        int index = 0;
        //写入数据
        for (TntExportCodePO entity : exportCodePOS) {
            XSSFRow row = sheet.createRow(index + 1);
            for (int n = 0; n <= columeCount - 1; n++) {
                row.createCell(n);
            }
            row.getCell(0).setCellValue(convert(entity.getBatchNo()));
            row.getCell(1).setCellValue(convert(entity.getOrderId()));
            row.getCell(2).setCellValue(convert(entity.getProductId()));
            row.getCell(3).setCellValue(convert(entity.getProductName()));
            row.getCell(4).setCellValue(convert(entity.getGoodsId()));
            row.getCell(5).setCellValue(convert(entity.getGoodsName()));
            row.getCell(6).setCellValue(convert(entity.getContactName()));
            row.getCell(7).setCellValue(convert(entity.getContactMobile()));
            row.getCell(8).setCellValue(convertDate(entity.getVisitTime()));
            row.getCell(9).setCellValue(convert(entity.getAddCode()));
            String url = convert(entity.getCodeUrl());
            String pdf = convert(entity.getPdfUrl());
            //绝对路径
            row.getCell(10).setCellValue(StringUtils.isEmpty(url)?"":QR_CODE_RUL+url);
            row.getCell(11).setCellValue(StringUtils.isEmpty(pdf)?"":QR_CODE_RUL+pdf);
            row.getCell(12).setCellValue(convert(entity.getUserId()));
            index++;
        }
        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            workbook.write(os);
            return os.toByteArray();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private String convertDate(Date date) {
        return date == null ? "" : sdf.format(date);
    }

    private String convert(Object object) {
        return object == null ? "" : object.toString();
    }


    @Override
    public ResponseDTO<Boolean> updateCodeUseStatus(Long itemOrderId) {
        if (itemOrderId == null){
            throw new IllegalArgumentException("itemOrderId is null");
        }

        ResponseDTO<Boolean> responseDTO = new ResponseDTO<>();
        boolean success = tntCodeInfoService.updateCodeUseStatus(itemOrderId,
                CodeUseStatus.USE.name());
        responseDTO.setSuccess(true);
        responseDTO.setResult(success);
        return responseDTO;
    }

    @Override
    public ResponseDTO<Long> uploadExportCodePDFZip(String batchNo) {
        if (StringUtils.isEmpty(batchNo)){
            throw new IllegalArgumentException("batchNo is null");
        }
        ResponseDTO<Long> responseDTO = new ResponseDTO<>();
        try {
            Long uploadId = exportCodeVoucherService.uploadPdfZip(batchNo);
            responseDTO.setResult(uploadId);
        }catch (Exception e){
            logger.error("uploadExportCodePDFZip error:", e);
            responseDTO.setSuccess(false);
            responseDTO.setRespMsg(e.getMessage());
        }
        return responseDTO;
    }
}
