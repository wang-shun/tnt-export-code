package com.lvmama.tnt.reference.service;

import com.lvmama.scenic.api.vo.ResultHandleT;
import com.lvmama.scenic.api.vo.order.OrderBaseVo;
import com.lvmama.scenic.api.vo.order.ScenicOrderInfoBaseVo;
import com.lvmama.scenic.api.vo.prod.ScenicPriceInfoVo;

/**
 * 主站时间价格接口
 *
 * @author songjunbao
 * @createdate 2018/2/9
 */
public interface IScenicOrderRefService {


    /**
     * 创建主站订单
     * @param info
     * @return
     */
    ResultHandleT<OrderBaseVo> createOrder(ScenicOrderInfoBaseVo info);

    /**
     * 计算主站订单价格
     * @param info
     * @return
     */
    ResultHandleT<ScenicPriceInfoVo> countPrice(ScenicOrderInfoBaseVo info);

}
