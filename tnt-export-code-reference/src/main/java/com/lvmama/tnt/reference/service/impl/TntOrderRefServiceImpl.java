package com.lvmama.tnt.reference.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lvmama.tnt.comm.util.order.StringUtil;
import com.lvmama.tnt.common.domain.OrderInfoDTO;
import com.lvmama.tnt.order.po.TntOrder;
import com.lvmama.tnt.order.service.TntOrderService;
import com.lvmama.tnt.reference.service.ITntOrderRefService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
@Service
public class TntOrderRefServiceImpl implements ITntOrderRefService {

    @Reference
    private TntOrderService tntOrderService;

    @Override
    public List<OrderInfoDTO> findOrdersByIds(List<Long> orderIds, Long userId, String performStatus) {
        List<OrderInfoDTO> list = new ArrayList<>();
        OrderInfoDTO dto = null;
        List<TntOrder> orders = tntOrderService.queryWithItems(orderIds, userId);
        for (TntOrder order : orders) {
            if (StringUtil.isNotEmptyString(performStatus)) {
                if (!performStatus.equals(order.getPerformStatus())) {
                    continue;
                }
            }
            dto = new OrderInfoDTO();
            assignment(order, dto);
            list.add(dto);
        }
        return list;
    }

    @Override
    public Long ordersSumAmount(List<Long> orderIds, Long userId) {
        Long sumAmount = 0L;
        if (orderIds == null) {
            return sumAmount;
        }
        List<TntOrder> orders = tntOrderService.queryWithItems(orderIds, userId);
        for (TntOrder order : orders) {
            sumAmount += order.getOrderAmount();
        }

        return sumAmount;
    }

    private void assignment(TntOrder order, OrderInfoDTO dto) {
        dto.setContactMoblie(order.getContactMoblie());
        dto.setContactName(order.getContactName());
        dto.setCreateTime(order.getCreateTime());
        dto.setOrderAmount(order.getOrderAmount());
        dto.setOrderId(order.getOrderId());
        dto.setOrderStatus(order.getOrderStatus());
        dto.setPassCodeStatus(order.getPassCodeStatus());
        dto.setPaymentStatus(order.getPaymentStatus());
        dto.setPaymentTarget(order.getPaymentTarget());
        dto.setTntOrderId(order.getTntOrderId());
        dto.setQuantity(order.getQuantity());
        dto.setProductName(order.getProductName());
        dto.setProductId(order.getProductId());
        dto.setPaymentType(order.getPaymentType()==null?"":order.getPaymentType().getValue());
        dto.setVisitTime(order.getVisitTime());
        dto.setPerformStatus(order.getPerformStatus());
        dto.setIsSuiShiTui(order.getIsSuiShiTui());
        dto.setLastCancelTime(order.getLastCancelTime());
        dto.setCategoryId(order.getCategoryId());
    }
}
