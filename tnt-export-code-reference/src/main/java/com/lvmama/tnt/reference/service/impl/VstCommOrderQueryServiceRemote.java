package com.lvmama.tnt.reference.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lvmama.vst.api.order.service.VstCommOrderQueryService;
import com.lvmama.vst.api.order.vo.OrdPassCodeVo;
import com.lvmama.vst.api.vo.ResultHandleT;
import com.lvmama.vst.api.vo.order.OrderBaseVo;
import org.springframework.stereotype.Service;

@Service
public class VstCommOrderQueryServiceRemote {

    @Reference
    private VstCommOrderQueryService vstCommOrderQueryService;

    public ResultHandleT<OrderBaseVo> findOrderDetail(Long channelId, Long orderId) {
         return vstCommOrderQueryService.findOrderDetail(channelId, orderId);
    }


    public ResultHandleT<Long> getOrderIdByPassCodeId(Long passCodeId) {
        return vstCommOrderQueryService.getOrderIdByPassCodeId(passCodeId);
    }

    public ResultHandleT<Long> getOrderIdByOrderItemId(Long itemOrderId) {
        return vstCommOrderQueryService.getOrderIdByOrderItemId(itemOrderId);
    }

     public ResultHandleT<OrdPassCodeVo> getOrdPassCodeByOrderItemId(Long itemOrderId){
        return vstCommOrderQueryService.getOrdPassCodeByOrderItemId(itemOrderId);

     }


}
