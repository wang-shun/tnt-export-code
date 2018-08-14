package com.lvmama.tnt.biz.service;

import com.lvmama.tnt.dal.domain.TntCodeCommonSetPO;

import java.util.List;

/**
 * @author songjunbao
 * @createdate 2018/1/30
 */
public interface TntCodeCommonSetService {

    boolean isExist(Long goodsId);

    boolean delete(Long goodsId);

    TntCodeCommonSetPO findByGoodsId(Long goodsId);

    long totalCount(TntCodeCommonSetPO tntCodeCommonSetPO);

    List<TntCodeCommonSetPO> findByPage(TntCodeCommonSetPO tntCodeCommonSetPO,int currentPage,int pageSize);

    TntCodeCommonSetPO findById(Long id);

    boolean updateCommonSet(TntCodeCommonSetPO tntCodeCommonSetPO);

    boolean saveCommonSet(TntCodeCommonSetPO tntCodeCommonSetPO);

}
