package com.lvmama.tnt.reference.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.lvmama.scenic.api.hoard.service.ScenicHoardCodeGoodsService;
import com.lvmama.scenic.api.hoard.service.ScenicHoardService;
import com.lvmama.scenic.api.hoard.vo.HoardCodeGoodsStockVo;
import com.lvmama.scenic.api.hoard.vo.HoardCodeGoodsVO;
import com.lvmama.scenic.api.vo.ResultHandleT;
import com.lvmama.tnt.reference.service.IScenicRefService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 *  统一调用Scenic 的接口
 */
@Service
public class ScenicRefServiceImpl implements IScenicRefService {

    private Logger logger = LoggerFactory.getLogger(ScenicRefServiceImpl.class);

    @Reference
    public ScenicHoardCodeGoodsService scenicHoardCodeGoodsService;

    @Reference
    private ScenicHoardService scenicHoardService;


    @Override
    public HoardCodeGoodsVO getHoardCodeGoods(Long goodsId) {
        ResultHandleT<HoardCodeGoodsVO> resultHandleT =  scenicHoardCodeGoodsService.getHoardCodeGoods(goodsId);
        if (resultHandleT.isSuccess() && resultHandleT.getReturnContent() != null) {
            logger.info("HoardCodeGoodsVO data:"+JSON.toJSONString(resultHandleT));
            return resultHandleT.getReturnContent();

        } else {
            logger.info("resultHandleT:{}", JSON.toJSONString(resultHandleT));
        }
        return null;
    }

    @Override
    public List<HoardCodeGoodsStockVo> listHoardCodeGoods(Long goodsId) {
        try {
            ResultHandleT<List<HoardCodeGoodsStockVo>> resultHandleT =  scenicHoardCodeGoodsService.listHoardCodeGoods(goodsId);
            if (resultHandleT.isSuccess() && resultHandleT.getReturnContent() != null) {
                return resultHandleT.getReturnContent();
            } else {
                logger.info("resultHandleT:{}", JSON.toJSONString(resultHandleT));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public HoardCodeGoodsStockVo checkLockHoardStock(String exportCodeBatch, Long goodsId, Date visitDate, Long quantity) {
        ResultHandleT<HoardCodeGoodsStockVo> resultHandleT =  scenicHoardService.checkLockHoardStock(exportCodeBatch, goodsId, visitDate, quantity);
        if (resultHandleT.isSuccess() && resultHandleT.getReturnContent() != null) {
            return resultHandleT.getReturnContent();
        } else {
            logger.info("resultHandleT:{}", JSON.toJSONString(resultHandleT));
        }
        return null;
    }

    @Override
    public Boolean releaseHoardStock(String exportBatchNo) {

        ResultHandleT<Boolean> resultHandleT =  scenicHoardService.releaseHoardStock(exportBatchNo);
        if (resultHandleT.isSuccess() && resultHandleT.getReturnContent() != null) {
            return resultHandleT.getReturnContent();
        } else {
            logger.info("resultHandleT:{}", JSON.toJSONString(resultHandleT));
            return false;
        }
    }
}
