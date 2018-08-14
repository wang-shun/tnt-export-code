package com.lvmama.tnt.reference.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lvmama.tnt.prod.po.TntProduct;
import com.lvmama.tnt.prod.po.TntProductUser;
import com.lvmama.tnt.prod.service.db.TntProductDbService;
import com.lvmama.tnt.prod.service.db.TntProductUserDbService;
import com.lvmama.tnt.prod.service.db.TntProductVoDbService;
import com.lvmama.tnt.prod.vo.TntProductVo;
import com.lvmama.tnt.reference.service.ITntProductRefService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 */
@Service
public class TntProductRefServiceImpl implements ITntProductRefService {

    @Reference
    private TntProductDbService tntProductDbService;
    @Reference
    private TntProductVoDbService tntProductVoDbService;

    @Reference
    private TntProductUserDbService tntProductUserDbService;


    @Override
    public Map<Long,List<TntProduct>> findProduct(Set<Long> productIds) {
        Map<Long, List<TntProduct>> resultMap = new HashMap<>(productIds.size());
        List<TntProduct> products = null;
        for (Long productId : productIds) {
            products = tntProductDbService.getByProductId(productId);
            resultMap.put(productId, products);
        }
        return resultMap;
    }





    /**
     * 查询分销商品信息
     * @param goodsId   商品id
     * @param categoryId 品类
     * @param channelId  分销渠道
     * @return
     */
    public TntProductVo findGoodsInfo(Long goodsId, Long categoryId, String channelId){
        if (goodsId == null){
            throw new IllegalArgumentException("goodsId can not null");
        }

        TntProductVo query= new TntProductVo();
        query.setGoodsId(goodsId);
        query.setCategoryId(categoryId);
        query.setChannelIds(channelId);
        return  tntProductVoDbService.selectOne(query);
    }


    /**
     * 查询商品对分销商的设置信息
     * @param goodsId
     * @param categoryId
     * @param userId
     * @return
     */
    public TntProductUser findGoodsInfoForUser(Long goodsId, Long categoryId, Long userId){
        TntProductUser query = new TntProductUser(categoryId+"");
        query.setGoodsId(goodsId);
        query.setUserId(userId);
        return tntProductUserDbService.selectOne(query);
    }


    @Override
    public Map<Long, TntProductVo> findGoodsInfoByGoodsIds(Set<Long> goodsIds, Long categoryId) {
        Map<Long, TntProductVo> goodsMap = new HashMap<>();
        if (CollectionUtils.isEmpty(goodsIds)){
           return goodsMap;
        }
        for (Long goodsId : goodsIds){
            TntProductVo query= new TntProductVo();
            query.setGoodsId(goodsId);
            query.setCategoryId(categoryId);
            TntProductVo goods = tntProductVoDbService.selectOne(query);
            goodsMap.put(goodsId,goods);

        }

        return goodsMap;
    }

    @Override
    public TntProduct findProductInfoByGoodsId(Long goodsId) {
        List<TntProduct> products = tntProductDbService.getByBranchId(goodsId);
        if (CollectionUtils.isEmpty(products)){
            return null;
        }

        return products.get(0);
    }
}
