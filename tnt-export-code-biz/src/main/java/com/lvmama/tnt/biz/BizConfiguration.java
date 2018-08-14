/* 
 * Copyright © 2017 www.lvmama.com
 */

/*
 * 修订记录:
 * @author 钟勋（zhongxun@lvmama.com） 2017-08-04 15:51 创建
 */
package com.lvmama.tnt.biz;

import com.lvmama.tnt.biz.order.filter.OrderFilter;
import com.lvmama.tnt.biz.order.filter.OrderFilterChain;
import com.lvmama.tnt.biz.order.filter.impl.OrderFilterChainImpl;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.ArrayList;
import java.util.List;

/**
 * biz层配置
 */
@Configuration
public class BizConfiguration {

    @Bean
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    OrderFilterChain orderFilterChain(OrderFilter goodsInfoValidFilter, OrderFilter memberAccountFilter,
                                      OrderFilter timePriceFilter, OrderFilter scenicOrderPriceFilter){
        OrderFilterChainImpl orderFilterChain = new OrderFilterChainImpl();
        List<OrderFilter> filters = new ArrayList<>();
        filters.add(goodsInfoValidFilter);
        filters.add(memberAccountFilter);
        filters.add(timePriceFilter);
        filters.add(scenicOrderPriceFilter);
        orderFilterChain.setFilters(filters);

        return orderFilterChain;

    }



}
