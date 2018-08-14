package com.lvmama.tnt.export.code.api.service;

import com.lvmama.tnt.common.domain.ResponseDTO;
import com.lvmama.tnt.export.code.api.domain.dto.ExportCodeInfoDTO;
import com.lvmama.tnt.export.code.api.domain.dto.TntCodeInfoDTO;
import com.lvmama.tnt.export.code.api.domain.vo.ExportBatchInfoVO;
import com.lvmama.tnt.export.code.api.domain.vo.ExportCodeInfoVO;

import java.util.List;

/**
 * 同步批次订单码信息
 */
public interface ITntCodeInfoRemoteService {

    /**
     * 保存或更新订单码信息
     * @param tntCodeInfoDTO
     * @return
     */
    ResponseDTO<String> saveOrUpdateCodeInfo(TntCodeInfoDTO tntCodeInfoDTO);


    /**
     * 导出导码凭证
     * @param exportCodeInfoDTO
     * @return
     */
    ResponseDTO<List<ExportCodeInfoDTO>> exportCode(ExportCodeInfoDTO exportCodeInfoDTO);

    /**
     *
     * @param exportCodeInfoDTO
     * @return
     */
    ResponseDTO<ExportCodeInfoVO> exportCodeInfo(ExportCodeInfoDTO exportCodeInfoDTO);


    /**
     * 根据子订单号，更新二维码履行状态
     * @param
     * @return
     */
    ResponseDTO<Boolean> updateCodeUseStatus(Long itemOrderId);

    /**
     * 上传导码批次下单的PDF凭证
     * @param batchNo
     * @return
     */
    ResponseDTO<Long> uploadExportCodePDFZip(String batchNo);
}
