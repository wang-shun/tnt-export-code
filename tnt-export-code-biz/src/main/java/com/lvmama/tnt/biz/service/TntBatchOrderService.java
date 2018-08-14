package com.lvmama.tnt.biz.service;

import com.lvmama.tnt.dal.domain.TntBatchOrderPO;

import java.util.List;

/**
 * @author songjunbao
 * @createdate 2018/1/30
 */
public interface TntBatchOrderService {

    List<Long> findAllOrderIdsByBatchNo(String batchNo);

    long totalCount(TntBatchOrderPO tntBatchOrderPO);

    List<TntBatchOrderPO> findByPage(TntBatchOrderPO tntBatchOrderPO,int currentPage,int pageSize);

    TntBatchOrderPO findById(Long id);

    boolean updateBatchOrder(TntBatchOrderPO tntBatchOrderPO);

    boolean saveBatchOrder(TntBatchOrderPO tntBatchOrderPO);

    TntBatchOrderPO findByOrderId(Long orderId);

    int countOrderSuccessNum(String batchNo);

    int countPassCodeSuccessNum(String batchNo);

}
