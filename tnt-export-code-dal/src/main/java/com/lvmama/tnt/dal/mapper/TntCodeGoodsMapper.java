package com.lvmama.tnt.dal.mapper;

import com.github.pagehelper.Page;
import com.lvmama.tnt.dal.domain.TntCodeGoodsPO;
import org.springframework.stereotype.Repository;

/**
 *
 */
@Repository
public interface TntCodeGoodsMapper {

    long totalCount(TntCodeGoodsPO tntCodeGoodsPO);

    Page<TntCodeGoodsPO> findByPage(TntCodeGoodsPO tntCodeGoodsPO);

    TntCodeGoodsPO findByID(Long id);

    int save(TntCodeGoodsPO tntCodeGoodsPO);

    int update(TntCodeGoodsPO tntCodeGoodsPO);

    TntCodeGoodsPO findByGoodsId(Long goodsId);

}
