package com.lvmama.tnt.biz.order.filter.impl;

import com.lvmama.scenic.api.order.vo.ScenicOrdRequiredVO;
import com.lvmama.scenic.api.vo.ResultHandleT;
import com.lvmama.tnt.biz.order.filter.OrderFilter;
import com.lvmama.tnt.biz.order.filter.OrderFilterChain;
import com.lvmama.tnt.biz.order.vo.ExportCodeOrderException;
import com.lvmama.tnt.biz.order.vo.OrderErrorCode;
import com.lvmama.tnt.biz.order.vo.OrderRequest;
import com.lvmama.tnt.biz.order.vo.OrderResponse;
import com.lvmama.tnt.biz.service.TntCodeCommonSetService;
import com.lvmama.tnt.biz.service.TntCodeGoodsService;
import com.lvmama.tnt.biz.service.TntCodeUserSetService;
import com.lvmama.tnt.dal.domain.TntCodeCommonSetPO;
import com.lvmama.tnt.dal.domain.TntCodeGoodsPO;
import com.lvmama.tnt.export.code.api.constant.CommonPolicy;
import com.lvmama.tnt.prod.po.TntProductUser;
import com.lvmama.tnt.prod.vo.TntProductVo;
import com.lvmama.tnt.reference.service.ITntProductRefService;
import com.lvmama.tnt.reference.service.impl.ScenicOrderRequiredServiceRemote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 商品信息是否有效：
 * 1.商品有效可售
 * 2.商品是否可导
 * 3.商品对分销商不分销
 * @author songjunbao
 * @createdate 2018/2/5
 */
@Service("goodsInfoValidFilter")
public class GoodsInfoValidFilter implements OrderFilter{
    private static Logger logger = LoggerFactory.getLogger(GoodsInfoValidFilter.class);

    @Autowired
    private ITntProductRefService tntProductRefService;

    @Autowired
    private TntCodeGoodsService tntCodeGoodsService;

    @Autowired
    private ScenicOrderRequiredServiceRemote scenicOrderRequiredServiceRemote;

    @Autowired
    private TntCodeCommonSetService tntCodeCommonSetService;

    @Autowired
    private TntCodeUserSetService tntCodeUserSetService;

    @Override
    public void process(OrderRequest request, OrderResponse response, OrderFilterChain chain) {
        logger.info(">>>goods info check, batchNo:" + request.getBatchNo());
        Long goodsId = request.getGoodsId();
        Long userId = request.getUserId();
        Long categoryId = request.getCategoryId();
        String channelId = request.getChannelId();

       TntProductVo tntProductVo =  tntProductRefService.findGoodsInfo(goodsId,categoryId,channelId);
       //不存在
       if (tntProductVo == null){
           logger.info("分销导码,分销商品不存在,goodsId:"+goodsId);
           throw new ExportCodeOrderException(OrderErrorCode.TNT_GOODS_NOT_FOUND.getMessage(),
                   OrderErrorCode.TNT_GOODS_NOT_FOUND.getCode());
       }
       //不可售
       if("N".equals(tntProductVo.getValid())){
           logger.info("分销导码,分销商品不可售,goodsId:"+goodsId);
           throw new ExportCodeOrderException(OrderErrorCode.TNT_GOODS_INVALID.getMessage(),
                   OrderErrorCode.TNT_GOODS_INVALID.getCode());
       }

       TntProductUser tntProductUser = tntProductRefService.findGoodsInfoForUser(goodsId, categoryId, userId);
       //对该分销商设置了黑名单
       if (tntProductUser != null && "false".equals(tntProductUser.getValid())){
           logger.info("分销导码，分销商品,goodsId:"+goodsId+"对分销商不分销，userId"+userId);
           throw new ExportCodeOrderException(OrderErrorCode.TNT_GOODS_BLACK_FOR_USER.getMessage(),
                    OrderErrorCode.TNT_GOODS_BLACK_FOR_USER.getCode());
       }

       TntCodeGoodsPO tntCodeGoodsPO = tntCodeGoodsService.findByGoodsId(goodsId);
        // 不可导
       if(tntCodeGoodsPO == null ||"N".equals(tntCodeGoodsPO.getIsExport())){
           logger.info("分销导码，分销商品不可导,goodsId:"+goodsId);
           throw new ExportCodeOrderException(OrderErrorCode.TNT_GOODS_NOT_EXPORT.getMessage(),
                   OrderErrorCode.TNT_GOODS_NOT_EXPORT.getCode());
       }

       //证件必填
       ResultHandleT<ScenicOrdRequiredVO> requiredHandleT =  scenicOrderRequiredServiceRemote.findOrderRequiredListId(goodsId);
        if (requiredHandleT != null && requiredHandleT.isSuccess() && requiredHandleT.getReturnContent() != null){
            ScenicOrdRequiredVO requiredVO =  requiredHandleT.getReturnContent();
            if (!ScenicOrdRequiredVO.BIZ_ORDER_REQUIRED_TRAV_NUM_LIST.TRAV_NUM_NO.name()
                    .equals(requiredVO.getIdNumType())){
                logger.info("分销导码，分销商品设置了证件类型必填不可导,goodsId:"+goodsId);
                throw new ExportCodeOrderException(OrderErrorCode.CREATE_ORDER_MUST_CRED.getMessage(),
                        OrderErrorCode.CREATE_ORDER_MUST_CRED.getCode());
            }

        }

       //对分销商不可导
        if (!isExportForUser(goodsId, userId)){
            logger.info("分销导码，分销商品对分销商不可导,goodsId:"+goodsId+", userId:"+userId);
            throw new ExportCodeOrderException(OrderErrorCode.TNT_GOODS_NOT_EXPORT_FOR_USER.getMessage(),
                    OrderErrorCode.TNT_GOODS_NOT_EXPORT_FOR_USER.getCode());
        }


        chain.process(request, response);
    }


    private boolean isExportForUser(Long goodsId, Long userId){
        TntCodeCommonSetPO commonSet = tntCodeCommonSetService.findByGoodsId(goodsId);
        if (commonSet == null || CommonPolicy.ALLNOT.name().equals(commonSet.getPolicy())){
            return false;
        }

        if (CommonPolicy.ALL.name().equals(commonSet.getPolicy())){
            return true;
        }

        if (CommonPolicy.USER.name().equals(commonSet.getPolicy())){
            return tntCodeUserSetService.isExist(goodsId, userId);
        }

        return false;
    }

}
