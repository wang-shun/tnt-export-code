package com.lvmama.tnt.biz.service;

import com.lvmama.tnt.dal.domain.TntCodeGoodsPO;

import java.util.List;

/**
 * @author songjunbao
 * @createdate 2018/1/30
 */
public interface TntCodeGoodsService {
    long totalCount(TntCodeGoodsPO tntCodeGoodsPO);

    /**
     * 如果pageSize>0分页， 否则查全部
     *
     * @param tntCodeGoodsPO
     * @param currentPage
     * @param pageSize
     * @return
     */
    List<TntCodeGoodsPO> findByPage(TntCodeGoodsPO tntCodeGoodsPO, int currentPage, int pageSize);

    TntCodeGoodsPO findByID(Long id);

    boolean saveCodeGoods(TntCodeGoodsPO tntCodeGoodsPO);

    boolean updateCodeGoods(TntCodeGoodsPO tntCodeGoodsPO);

    TntCodeGoodsPO findByGoodsId(Long goodsId);
}
