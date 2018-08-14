package com.lvmama.tnt.biz.service;

import com.lvmama.tnt.dal.domain.TntCodeInfoPO;
import com.lvmama.tnt.dal.domain.TntExportCodePO;

import java.util.List;

/**
 * @author songjunbao
 * @createdate 2018/1/30
 */
public interface TntCodeInfoService {

    boolean isBatchOrderExist(String batchNo, Long orderId);

    long totalCount(TntCodeInfoPO tntCodeInfoPO);

    List<TntCodeInfoPO> findByPage(TntCodeInfoPO tntCodeInfoPO, int currentPage, int pageSize);

    TntCodeInfoPO findById(Long id);

    boolean updateCodeInfo(TntCodeInfoPO tntCodeInfoPO);

    boolean saveCodeInfo(TntCodeInfoPO tntCodeInfoPO);

    List<TntExportCodePO> findExportCodeByParam(TntExportCodePO exportCodePO);

    List<TntCodeInfoPO> findCodeInfo(String batchNo);

    boolean updateCodeUseStatus(Long orderItemId, String useStatus);
}
