package com.lvmama.tnt.dal.mapper;

import com.github.pagehelper.Page;
import com.lvmama.tnt.dal.domain.TntCodeBatchJoinCodeGoodsPO;
import com.lvmama.tnt.dal.domain.TntCodeBatchPO;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 *
 */
@Repository
public interface TntCodeBatchMapper {

    TntCodeBatchJoinCodeGoodsPO findByBatchNo(String batchNo);

    long totalCountByJoin(TntCodeBatchJoinCodeGoodsPO tntCodeBatchJoinCodeGoodsPO);

    Page<TntCodeBatchJoinCodeGoodsPO> findByPageJoin(TntCodeBatchJoinCodeGoodsPO tntCodeBatchJoinCodeGoodsPO);

    long totalCount(TntCodeBatchPO tntCodeBatchPO);

    Page<TntCodeBatchPO> findByPage(TntCodeBatchPO tntCodeBatchPO);

    TntCodeBatchPO findById(Long id);

    int update(TntCodeBatchPO tntCodeBatchPO);

    int save(TntCodeBatchPO tntCodeBatchPO);

    List<TntCodeBatchPO> findBatch(TntCodeBatchPO tntCodeBatchPO);

    List<Map<String, Integer>> queryWaitingCodeNum();

    int updateByBatchNo(TntCodeBatchPO tntCodeBatchPO);
}
