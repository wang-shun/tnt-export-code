package com.lvmama.tnt.dal.mapper;

import com.github.pagehelper.Page;
import com.lvmama.tnt.dal.domain.TntBatchOrderPO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 */
@Repository
public interface TntBatchOrderMapper {
    List<Long> findAllOrderIdsByBatchNo(String batchNo);

    long totalCount(TntBatchOrderPO tntBatchOrderPO);

    Page<TntBatchOrderPO> findByPage(TntBatchOrderPO tntBatchOrderPO);

    TntBatchOrderPO findById(Long id);

    int updateByOrderId(TntBatchOrderPO tntBatchOrderPO);

    int save(TntBatchOrderPO tntBatchOrderPO);

    TntBatchOrderPO findByOrderId(Long orderId);

    int countOrderStatusByBatchNo(@Param("batchNo") String batchNo, @Param("paymentStatus") String paymentStatus);

    int countPassCodeStatusByBatchNo(@Param("batchNo") String batchNo, @Param("passcodeApplyStatus") String passcodeApplyStatus);
}
