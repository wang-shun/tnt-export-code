package com.lvmama.tnt.reference.service;

import com.lvmama.scenic.api.hoard.vo.HoardCodeGoodsStockVo;
import com.lvmama.scenic.api.hoard.vo.HoardCodeGoodsVO;
import com.lvmama.scenic.api.vo.ResultHandleT;

import java.util.Date;
import java.util.List;

/**
 *
 */
public interface IScenicRefService {
     /**
     * 根据囤码商品ID获取囤码商品信息（全量返回）
     * @param goodsId 囤码商品ID
     * @return  囤码商品VO
     */
    HoardCodeGoodsVO getHoardCodeGoods(Long goodsId);


    /**
     * 根据商品ID获取囤码商品可售日期、及对应的库存
     * @param goodsId
     * @return
     */
    List<HoardCodeGoodsStockVo> listHoardCodeGoods(Long goodsId);


    /**
     * B2B批量导码 库存校验及锁库存操作
     * 1、校验指定日期商品是否可售（渠道限制、囤码商品是否有效、）是否有足够库存
     * @param goodsId  商品ID
     * @param visitDate  游玩日期
     * @param quantity 份数
     * @param exportCodeBatch 导码批次
     * @return
     */
    HoardCodeGoodsStockVo checkLockHoardStock(String exportCodeBatch, Long goodsId, Date visitDate, Long quantity);


    /**
     * B2B批量导码 根据导码批次号释放库存
     * 将对应的导码批次号清除
     * @param exportBatchNo 分销导码批次号
     * @return
     */
    Boolean releaseHoardStock(String exportBatchNo);
}
