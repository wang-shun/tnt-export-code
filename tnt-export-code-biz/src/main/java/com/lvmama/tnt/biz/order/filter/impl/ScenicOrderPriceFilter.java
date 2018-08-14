package com.lvmama.tnt.biz.order.filter.impl;

import com.lvmama.scenic.api.vo.ResultHandleT;
import com.lvmama.scenic.api.vo.order.ScenicOrderInfoBaseVo;
import com.lvmama.scenic.api.vo.prod.ScenicPriceInfoVo;
import com.lvmama.tnt.biz.order.filter.OrderFilter;
import com.lvmama.tnt.biz.order.filter.OrderFilterChain;
import com.lvmama.tnt.biz.order.vo.ExportCodeOrderException;
import com.lvmama.tnt.biz.order.vo.OrderErrorCode;
import com.lvmama.tnt.biz.order.vo.OrderRequest;
import com.lvmama.tnt.biz.order.vo.OrderResponse;
import com.lvmama.tnt.export.code.api.constant.ExportCodeType;
import com.lvmama.tnt.reference.service.IScenicOrderRefService;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 主站价格过滤
 *
 * @author songjunbao
 * @createdate 2018/2/9
 */
@Service("scenicOrderPriceFilter")
public class ScenicOrderPriceFilter implements OrderFilter {

    private static Logger logger = LoggerFactory.getLogger(ScenicOrderPriceFilter.class);

    @Autowired
    private IScenicOrderRefService scenicOrderRefService;

    @Override
    public void process(OrderRequest request, OrderResponse response, OrderFilterChain chain) {
        logger.info(">>>scenic order price check, batchNo:" + request.getBatchNo());

//        if (!ExportCodeType.REALTIME.name().equals(request.getCodeType())){
//            //实时导码才需要校验主站价格 （需要确认）
//            chain.process(request, response);
//            return;
//        }

        ScenicOrderInfoBaseVo baseVo = new ScenicOrderInfoBaseVo();
        List<ScenicOrderInfoBaseVo.ItemInfo> itemInfos = new ArrayList<>();

        ScenicOrderInfoBaseVo.ItemInfo itemInfo = convert2Item(request);
        itemInfos.add(itemInfo);
        baseVo.setItemList(itemInfos);
        ResultHandleT<ScenicPriceInfoVo> supplyResult = scenicOrderRefService.countPrice(baseVo);
        if (supplyResult == null || supplyResult.isFail() || supplyResult.getReturnContent() == null) {
            logger.error("request_supply_price_error_{},msg:{}", request.getGoodsId(),supplyResult.getMsg());
            throw new ExportCodeOrderException(OrderErrorCode.SCENIC_SUPPLY_PRICE_ERROR.getMessage(),
                    OrderErrorCode.SCENIC_SUPPLY_PRICE_ERROR.getCode());
        }

        ScenicPriceInfoVo scenicPriceInfoVo =  supplyResult.getReturnContent();
        Long oughtPay = scenicPriceInfoVo.getOughtPayFen();
        //促销金额
        Long promotionAmount = scenicPriceInfoVo.getPromotionAmount();
        Long vstOughtPay = oughtPay + promotionAmount;
        //携带主站订单金额和优惠金额
        request.setDiscountOrderAmount(vstOughtPay - request.getOrderSettleAmount());
        request.setLvmamaOrderAmount(vstOughtPay);

        chain.process(request, response);


    }

    private ScenicOrderInfoBaseVo.ItemInfo convert2Item(OrderRequest request){
        String visitTime = DateFormatUtils.format(request.getVisitTime(), "yyyy-MM-dd");
        ScenicOrderInfoBaseVo.ItemInfo itemInfo = new ScenicOrderInfoBaseVo.ItemInfo();
        itemInfo.setGoodsId(request.getGoodsId());
        itemInfo.setVisitTime(visitTime);
        itemInfo.setQuantity(request.getQuantity());
        itemInfo.setAdultQuantity(request.getAdultQuantity());
        itemInfo.setChildQuantity(request.getChildQuantity());
        return itemInfo;
    }
}
