package com.lvmama.tnt.reference.service.impl;

import com.lvmama.tnt.result.TradePayResult;
import com.lvmama.tnt.service.TntBizPayService;
import com.lvmama.tnt.vo.pay.TntBizPayTradeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 分销支付(系统级别重试5次，超时60s)
 * @author songjunbao
 * @createdate 2018/2/26
 */
@Service
public class TntBizPayServiceRemote {
    @Autowired
    private TntBizPayService tntBizPayService;

    public TradePayResult pay(TntBizPayTradeVO tntBizPayTradeVO) throws Exception{
        return tntBizPayService.pay(tntBizPayTradeVO);
    }





}
