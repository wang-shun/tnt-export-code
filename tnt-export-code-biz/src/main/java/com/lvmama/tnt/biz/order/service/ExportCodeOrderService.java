package com.lvmama.tnt.biz.order.service;

import com.lvmama.tnt.biz.order.vo.OrderRequest;
import com.lvmama.tnt.biz.order.vo.OrderResponse;

/**
 * 导码订单服务:
 *  导码订单都不发短信，囤码方式设置批次号
 *
 * @author songjunbao
 * @createdate 2018/2/5
 */
public interface ExportCodeOrderService {


    /**
     * 创建订单（主站订单和分销订单）
     */
    OrderResponse createOrder(OrderRequest request);

}
