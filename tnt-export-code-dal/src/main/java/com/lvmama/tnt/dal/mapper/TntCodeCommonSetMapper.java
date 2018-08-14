package com.lvmama.tnt.dal.mapper;

import com.github.pagehelper.Page;
import com.lvmama.tnt.dal.domain.TntCodeCommonSetPO;
import org.springframework.stereotype.Repository;

/**
 *
 */
@Repository
public interface TntCodeCommonSetMapper {

    int isExist(Long goodsId);

    int delete(Long goodsId);

    TntCodeCommonSetPO findByGoodsId(Long goodsId);

    long totalCount(TntCodeCommonSetPO tntCodeCommonSetPO);

    Page<TntCodeCommonSetPO> findByPage(TntCodeCommonSetPO tntCodeCommonSetPO);

    TntCodeCommonSetPO findById(Long id);

    int update(TntCodeCommonSetPO tntCodeCommonSetPO);

    int save(TntCodeCommonSetPO tntCodeCommonSetPO);
}
