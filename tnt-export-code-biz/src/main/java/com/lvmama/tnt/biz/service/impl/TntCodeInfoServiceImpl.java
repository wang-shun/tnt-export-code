package com.lvmama.tnt.biz.service.impl;

import com.github.pagehelper.PageHelper;
import com.lvmama.tnt.biz.service.TntCodeInfoService;
import com.lvmama.tnt.dal.domain.TntCodeInfoPO;
import com.lvmama.tnt.dal.domain.TntExportCodePO;
import com.lvmama.tnt.dal.mapper.TntCodeInfoMapper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author songjunbao
 * @createdate 2018/1/30
 */
@Service("tntCodeInfoServiceImpl")
public class TntCodeInfoServiceImpl implements TntCodeInfoService {

    @Autowired
    private TntCodeInfoMapper tntCodeInfoMapper;

    @Override
    public boolean isBatchOrderExist(String batchNo, Long orderId) {
        return tntCodeInfoMapper.isBatchOrderExist(batchNo, orderId) > 0;
    }

    @Override
    public long totalCount(TntCodeInfoPO tntCodeInfoPO) {
        return tntCodeInfoMapper.totalCount(tntCodeInfoPO);
    }

    @Override
    public List<TntCodeInfoPO> findByPage(TntCodeInfoPO tntCodeInfoPO, int currentPage, int pageSize) {
        PageHelper.startPage(currentPage, pageSize);
        return tntCodeInfoMapper.findByPage(tntCodeInfoPO).getResult();
    }

    @Override
    public TntCodeInfoPO findById(Long id) {
        return tntCodeInfoMapper.findById(id);
    }

    @Override
    public boolean updateCodeInfo(TntCodeInfoPO tntCodeInfoPO) {
        return tntCodeInfoMapper.update(tntCodeInfoPO) > 0;
    }

    @Override
    public boolean saveCodeInfo(TntCodeInfoPO tntCodeInfoPO) {
        return tntCodeInfoMapper.save(tntCodeInfoPO) > 0;
    }

    @Override
    public List<TntExportCodePO> findExportCodeByParam(TntExportCodePO exportCodePO) {
        return tntCodeInfoMapper.findExportCodeByParam(exportCodePO);
    }

    @Override
    public List<TntCodeInfoPO> findCodeInfo(String batchNo) {
        if (StringUtils.isEmpty(batchNo)){
            throw new IllegalArgumentException("batchNo is null");
        }
        return tntCodeInfoMapper.findByBatchNo(batchNo);
    }

    @Override
    public boolean updateCodeUseStatus(Long orderItemId, String useStatus) {
        TntCodeInfoPO updateItem = new TntCodeInfoPO();
        updateItem.setOrderItemId(orderItemId);
        updateItem.setUseStatus(useStatus);
        return tntCodeInfoMapper.updateCodeStatus(updateItem) > 0 ;
    }
}
