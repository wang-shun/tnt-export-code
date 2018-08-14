package com.lvmama.tnt.export.code.api.service;

import com.lvmama.tnt.common.domain.PageResponseDTO;
import com.lvmama.tnt.common.domain.ResponseDTO;
import com.lvmama.tnt.export.code.api.domain.vo.ExportBatchInfoVO;
import com.lvmama.tnt.export.code.api.domain.vo.ExportOrderVO;

import java.util.List;

/**
 *  批次订单管理
 */
public interface ITntBatchOrderRemoteService {

    /**
     * 批次订单列表
     * @param exportOrderVO
     * @return
     */
    PageResponseDTO<ExportOrderVO> findOrderByPage(ExportOrderVO exportOrderVO);

    /**
     * 批次任务列表
     * @param exportBatchInfoVO
     * @return
     */
    PageResponseDTO<List<ExportBatchInfoVO>> findByPage(ExportBatchInfoVO exportBatchInfoVO);

    /**
     * 批次任务详情
     * @param batchNo
     * @return
     */
    ResponseDTO<ExportBatchInfoVO> findByBatchNo(String batchNo, Long userId);

    /**
     * 强制终止批次任务
     * @param batchNo
     * @return
     */
    ResponseDTO<String> stopBatchTask(String batchNo);


    ResponseDTO<Boolean> createBatch(ExportBatchInfoVO exportBatchInfoVO);

    ResponseDTO<String> findBatchNo(Long orderId);

    void updateBatchOrderStatus(Long orderId, String payStatus, String passcodeStatus);

    ResponseDTO<Void> updateBatchFileId(String batchNo, Long fileId);
}
