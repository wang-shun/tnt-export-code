package com.lvmama.tnt.dal.mapper;

import com.github.pagehelper.Page;
import com.lvmama.tnt.dal.domain.TntCodeInfoPO;
import com.lvmama.tnt.dal.domain.TntExportCodePO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 */
@Repository
public interface TntCodeInfoMapper {

    List<TntExportCodePO> findExportCodeByParam(TntExportCodePO exportCodePO);

    int isBatchOrderExist(@Param("batchNo") String batchNo, @Param("orderId") Long orderId);

    long totalCount(TntCodeInfoPO tntCodeInfoPO);

    Page<TntCodeInfoPO> findByPage(TntCodeInfoPO tntCodeInfoPO);

    TntCodeInfoPO findById(Long id);

    int update(TntCodeInfoPO tntCodeInfoPO);

    int save(TntCodeInfoPO tntCodeInfoPO);

    int updateCodeStatus(TntCodeInfoPO tntCodeInfoPO);

    List<TntCodeInfoPO> findByBatchNo(String batchNo);


}
