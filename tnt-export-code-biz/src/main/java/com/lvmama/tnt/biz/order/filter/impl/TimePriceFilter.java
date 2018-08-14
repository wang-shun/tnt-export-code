package com.lvmama.tnt.biz.order.filter.impl;

import com.lvmama.tnt.biz.order.filter.OrderFilter;
import com.lvmama.tnt.biz.order.filter.OrderFilterChain;
import com.lvmama.tnt.biz.order.vo.ExportCodeOrderException;
import com.lvmama.tnt.biz.order.vo.OrderErrorCode;
import com.lvmama.tnt.biz.order.vo.OrderRequest;
import com.lvmama.tnt.biz.order.vo.OrderResponse;
import com.lvmama.tnt.reference.service.ITntTimePriceRefService;
import com.lvmama.tnt.timeprice.core.api.request.PriceRequest;
import com.lvmama.tnt.timeprice.core.api.response.PriceResponse;
import com.lvmama.tnt.timeprice.core.api.response.vo.TicketTimePriceVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 时间价格表过滤
 *
 * @author songjunbao
 * @createdate 2018/2/5
 */
@Service("timePriceFilter")
public class TimePriceFilter implements OrderFilter {
    private static  Logger logger = LoggerFactory.getLogger(TimePriceFilter.class);

    @Autowired
    private ITntTimePriceRefService tntTimePriceRefService;

    @Override
    public void process(OrderRequest request, OrderResponse response, OrderFilterChain chain) {
        logger.info(">>>tnt time price, batchNo:" + request.getBatchNo());

        Long userId = request.getUserId();
        Date visitTime = request.getVisitTime();
        Long goodsId = request.getGoodsId();
        Long batchPrice = request.getBatchPrice();

        PriceRequest priceRequest = PriceRequest.newBuilder().startDate(visitTime).endDate(visitTime)
                .saleUnitId(goodsId).packType(null).aperiodicFlag(null).userId(userId)
                .raiseRuleType(null).build();
        PriceResponse<TicketTimePriceVO> priceResponse = tntTimePriceRefService
                .getTimePrice(priceRequest);
        //是否设置分销价
        if (priceResponse == null || !priceResponse.isSuccess() || priceResponse.getObject() == null) {
            logger.error("getTimePrice error saleUnitId = {}", goodsId);
            throw new ExportCodeOrderException(OrderErrorCode.TNT_TIME_PRICE_NO_SET.getMessage(),
                    OrderErrorCode.TNT_TIME_PRICE_NO_SET.getCode());
        }
        //是否分销价与批次价格不同
        if (!batchPrice.equals(priceResponse.getObject().getSettlementPrice())){
            logger.error("settle price change saleUnitId = {}", goodsId);
            throw new ExportCodeOrderException(OrderErrorCode.TNT_TIME_PRICE_NOT_MATCH.getMessage(),
                    OrderErrorCode.TNT_TIME_PRICE_NOT_MATCH.getCode());
        }

        //设置退款类型
        request.setOrderRefundType(priceResponse.getObject().getTradePriceType());
        //分销的驴妈妈价与主站的驴妈妈价比较（防止主站调高了价格，分销的价格不同步）

        chain.process(request, response);


    }


}
