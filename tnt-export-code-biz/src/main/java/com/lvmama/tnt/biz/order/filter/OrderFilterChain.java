package com.lvmama.tnt.biz.order.filter;

import com.lvmama.tnt.biz.order.vo.OrderRequest;
import com.lvmama.tnt.biz.order.vo.OrderResponse;

/**
 * 订单过滤链
 *
 * @author songjunbao
 * @createdate 2018/2/5
 */
public interface OrderFilterChain {
    void process(OrderRequest request, OrderResponse response);
}
