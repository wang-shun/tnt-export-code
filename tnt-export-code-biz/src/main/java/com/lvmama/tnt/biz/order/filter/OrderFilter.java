package com.lvmama.tnt.biz.order.filter;

import com.lvmama.tnt.biz.order.vo.OrderRequest;
import com.lvmama.tnt.biz.order.vo.OrderResponse;

/**
 * 订单请求参数过滤
 *
 * @author songjunbao
 * @createdate 2018/2/5
 */
public interface OrderFilter {

    void process(OrderRequest request, OrderResponse response, OrderFilterChain chain);

}
