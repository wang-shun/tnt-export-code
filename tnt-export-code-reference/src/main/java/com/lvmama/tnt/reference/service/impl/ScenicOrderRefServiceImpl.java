package com.lvmama.tnt.reference.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lvmama.scenic.api.order.service.ScenicCommOrderManageService;
import com.lvmama.scenic.api.vo.ResultHandleT;
import com.lvmama.scenic.api.vo.order.OrderBaseVo;
import com.lvmama.scenic.api.vo.order.ScenicOrderInfoBaseVo;
import com.lvmama.scenic.api.vo.prod.ScenicPriceInfoVo;
import com.lvmama.tnt.reference.service.IScenicOrderRefService;
import org.springframework.stereotype.Service;

/**
 * 调用主站景乐下单接口(系统级别重试5次，超时60s)
 * @author songjunbao
 * @createdate 2018/2/9
 */
@Service
public class ScenicOrderRefServiceImpl implements IScenicOrderRefService {

    @Reference
    private ScenicCommOrderManageService scenicCommOrderManageService;

    @Override
    public ResultHandleT<OrderBaseVo> createOrder(ScenicOrderInfoBaseVo info) {
        return scenicCommOrderManageService.createOrder(info);
    }

    @Override
    public ResultHandleT<ScenicPriceInfoVo> countPrice(ScenicOrderInfoBaseVo info) {
        return scenicCommOrderManageService.countPrice(info);
    }
}
