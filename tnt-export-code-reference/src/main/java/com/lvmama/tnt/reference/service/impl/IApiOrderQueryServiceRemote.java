package com.lvmama.tnt.reference.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lvmama.order.api.base.vo.RequestBody;
import com.lvmama.order.api.base.vo.ResponseBody;
import com.lvmama.order.service.api.comm.order.IApiOrderQueryService;
import com.lvmama.order.vo.comm.OrderVo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 导码订单查询
 *
 * @author songjunbao
 * @createdate 2018/3/13
 */
@Service
public class IApiOrderQueryServiceRemote {

    @Reference
    private IApiOrderQueryService iApiOrderQueryService;

    /**
     * 根据分销导码批次号查询订单列表
     * @param batchNo
     * @return
     */
    public ResponseBody<List<OrderVo>> listOrdorderByBatchNo(RequestBody<String> batchNo){
        return iApiOrderQueryService.listOrdorderByBatchNo(batchNo);
    }


}
