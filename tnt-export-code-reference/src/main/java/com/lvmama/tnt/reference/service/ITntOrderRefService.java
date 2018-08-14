package com.lvmama.tnt.reference.service;

import com.lvmama.tnt.common.domain.OrderInfoDTO;

import java.util.List;

/**
 *  订单查询
 */
public interface ITntOrderRefService {

    List<OrderInfoDTO> findOrdersByIds(List<Long> orderIds, Long userId, String performStatus);

    Long ordersSumAmount(List<Long> orderIds,  Long userId);

}
