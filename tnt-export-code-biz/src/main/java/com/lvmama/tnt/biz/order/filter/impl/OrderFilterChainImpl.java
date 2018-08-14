package com.lvmama.tnt.biz.order.filter.impl;

import com.lvmama.tnt.biz.order.filter.OrderFilter;
import com.lvmama.tnt.biz.order.filter.OrderFilterChain;
import com.lvmama.tnt.biz.order.vo.OrderRequest;
import com.lvmama.tnt.biz.order.vo.OrderResponse;

import java.util.List;

/**
 * @author songjunbao
 * @createdate 2018/2/11
 */
public class OrderFilterChainImpl implements OrderFilterChain {

    private List<OrderFilter> filters ;

    //记录节点的位置
    private int index = 0;

    @Override
    public void process(OrderRequest request, OrderResponse response) {
        if(index == filters.size()) return;

        OrderFilter filter = filters.get(index);
        //索引+1
        index ++;
        filter.process(request, response, this);

    }

    public List<OrderFilter> getFilters() {
        return filters;
    }

    public void setFilters(List<OrderFilter> filters) {
        this.filters = filters;
    }
}
