package com.lvmama.tnt.biz.service.impl;

import com.github.pagehelper.PageHelper;
import com.lvmama.tnt.biz.service.TntCodeCommonSetService;
import com.lvmama.tnt.dal.domain.TntCodeCommonSetPO;
import com.lvmama.tnt.dal.mapper.TntCodeCommonSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author songjunbao
 * @createdate 2018/1/30
 */
@Service("tntCodeCommonSetServiceImpl")
public class TntCodeCommonSetServiceImpl implements TntCodeCommonSetService {

    @Autowired
    private TntCodeCommonSetMapper tntCodeCommonSetMapper;

    @Override
    public boolean isExist(Long goodsId) {
        return tntCodeCommonSetMapper.isExist(goodsId) > 0;
    }

    @Override
    public boolean delete(Long goodsId) {
        return tntCodeCommonSetMapper.delete(goodsId)>0;
    }

    @Override
    public TntCodeCommonSetPO findByGoodsId(Long goodsId) {
        return tntCodeCommonSetMapper.findByGoodsId(goodsId);
    }

    @Override
    public long totalCount(TntCodeCommonSetPO tntCodeCommonSetPO) {
        return tntCodeCommonSetMapper.totalCount(tntCodeCommonSetPO);
    }

    @Override
    public List<TntCodeCommonSetPO> findByPage(TntCodeCommonSetPO tntCodeCommonSetPO, int currentPage, int pageSize) {
        PageHelper.startPage(currentPage, pageSize);
        return tntCodeCommonSetMapper.findByPage(tntCodeCommonSetPO).getResult();
    }

    @Override
    public TntCodeCommonSetPO findById(Long id) {
        return tntCodeCommonSetMapper.findById(id);
    }

    @Override
    public boolean updateCommonSet(TntCodeCommonSetPO tntCodeCommonSetPO) {
        return tntCodeCommonSetMapper.update(tntCodeCommonSetPO) > 0;
    }

    @Override
    public boolean saveCommonSet(TntCodeCommonSetPO tntCodeCommonSetPO) {
        return tntCodeCommonSetMapper.save(tntCodeCommonSetPO) > 0 ;
    }
}
