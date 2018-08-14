package com.lvmama.tnt.biz.service;

import com.github.pagehelper.Page;
import com.lvmama.tnt.dal.domain.TntCodeBatchJoinCodeGoodsPO;
import com.lvmama.tnt.dal.domain.TntCodeBatchPO;
import com.lvmama.tnt.export.code.api.constant.BatchStatus;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/1/30.
 */
public interface TntCodeBatchService {
    TntCodeBatchJoinCodeGoodsPO findByBatchNo(String batchNo);

    long totalCountByJoin(TntCodeBatchJoinCodeGoodsPO tntCodeBatchJoinCodeGoodsPO);

    Page<TntCodeBatchJoinCodeGoodsPO> findByPageJoin(TntCodeBatchJoinCodeGoodsPO tntCodeBatchJoinCodeGoodsPO, int pageNo, int pageSize);

    long totalCount(TntCodeBatchPO tntCodeBatchPO);

    List<TntCodeBatchPO> findByPage(TntCodeBatchPO tntCodeBatchPO,int currentPage,int pageSize);

    List<Map<String ,Integer>> queryWaitingCodeNum();

    /**
     * 按批次状态查询批次（按创建时间升序）
     * @param batchStatus
     * @return
     */
    List<TntCodeBatchPO> findStatusBatch(String batchStatus);

    TntCodeBatchPO findById(Long id);

    /**
     * 更新批次状态
     * @param batchId
     * @param batchStatus
     */
    void updateBatchStatusAndMessage(Long batchId, BatchStatus batchStatus, String statusMessage);

    /**
     * 更新订单成功数
     * @param batchId
     * @param orderSuccessNum
     */
    void updateOrderSuccessNum(Long batchId, Integer orderSuccessNum);

    /**
     * 更新申码成功数
     * @param batchId
     * @param codeSuccessNum
     */
    void updateOrderCodeNum(Long batchId, Integer codeSuccessNum);

    boolean saveCodeBatch(TntCodeBatchPO tntCodeBatchPO);

    void updateRetryCount(Long batchId, Integer retryCount);

    boolean updateFileId(String batchNo, Long fileId);
}
