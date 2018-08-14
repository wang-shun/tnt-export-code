package com.lvmama.tnt.reference.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lvmama.tnt.comm.vo.ResultGod;
import com.lvmama.tnt.order.po.TntOrder;
import com.lvmama.tnt.order.service.TntOrderService;
import com.lvmama.tnt.order.vo.TntOrderBaseVo;
import org.springframework.stereotype.Service;

/**
 * @author songjunbao
 * @createdate 2018/2/13
 */
@Service
public class TntOrderServiceRemote {

    @Reference
    private TntOrderService tntOrderService;

    /**
     * 创建分销订单
     * @param orderBase
     * @return
     */
    public ResultGod<TntOrder> createOrder(TntOrderBaseVo orderBase){
        return tntOrderService.createOrder(orderBase);
    }

}
