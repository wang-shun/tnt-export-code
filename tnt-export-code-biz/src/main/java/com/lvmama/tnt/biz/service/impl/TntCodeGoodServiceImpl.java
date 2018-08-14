package com.lvmama.tnt.biz.service.impl;

import com.github.pagehelper.PageHelper;
import com.lvmama.tnt.biz.service.TntCodeGoodsService;
import com.lvmama.tnt.dal.domain.TntCodeGoodsPO;
import com.lvmama.tnt.dal.mapper.TntCodeGoodsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author songjunbao
 * @createdate 2018/1/30
 */
@Service
public class TntCodeGoodServiceImpl implements TntCodeGoodsService {
    @Autowired
    private TntCodeGoodsMapper tntCodeGoodsMapper;


    @Override
    public long totalCount(TntCodeGoodsPO tntCodeGoodsPO) {
        return tntCodeGoodsMapper.totalCount(tntCodeGoodsPO);
    }

    @Override
    public List<TntCodeGoodsPO> findByPage(TntCodeGoodsPO tntCodeGoodsPO, int currentPage, int pageSize) {
        if (pageSize > 0 ){
            PageHelper.startPage(currentPage, pageSize);
        }

        return tntCodeGoodsMapper.findByPage(tntCodeGoodsPO).getResult();
    }

    @Override
    public TntCodeGoodsPO findByID(Long id) {
        return tntCodeGoodsMapper.findByID(id);
    }

    @Override
    public boolean saveCodeGoods(TntCodeGoodsPO tntCodeGoodsPO) {
        return tntCodeGoodsMapper.save(tntCodeGoodsPO) > 0;
    }

    @Override
    public boolean updateCodeGoods(TntCodeGoodsPO tntCodeGoodsPO) {
        return tntCodeGoodsMapper.update(tntCodeGoodsPO) > 0;
    }

    @Override
    public TntCodeGoodsPO findByGoodsId(Long goodsId) {
        return tntCodeGoodsMapper.findByGoodsId(goodsId);
    }
}
