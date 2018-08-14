package com.lvmama.tnt.reference.service;

import com.lvmama.tnt.prod.po.TntProduct;
import com.lvmama.tnt.prod.po.TntProductUser;
import com.lvmama.tnt.prod.vo.TntProductVo;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 */
public interface ITntProductRefService {

    Map<Long,List<TntProduct>> findProduct(Set<Long> productIds);



    /**
     * 查询分销商品信息
     * @param goodsId   商品id
     * @param categoryId 品类
     * @param channelId  分销渠道
     * @return
     */
    TntProductVo findGoodsInfo(Long goodsId, Long categoryId, String channelId);

    /**
     * 查询商品对分销商的设置信息
     * @param goodsId
     * @param categoryId
     * @param userId
     * @return
     */
    TntProductUser findGoodsInfoForUser(Long goodsId, Long categoryId, Long userId);


    Map<Long, TntProductVo> findGoodsInfoByGoodsIds(Set<Long> goodsIds, Long categoryId);

    TntProduct findProductInfoByGoodsId(Long goodsId);
}
