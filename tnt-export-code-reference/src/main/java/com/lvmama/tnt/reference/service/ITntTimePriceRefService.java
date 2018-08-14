package com.lvmama.tnt.reference.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lvmama.tnt.timeprice.core.api.TicketTimePriceFacadeService;
import com.lvmama.tnt.timeprice.core.api.request.PriceRequest;
import com.lvmama.tnt.timeprice.core.api.response.PriceResponse;
import com.lvmama.tnt.timeprice.core.api.response.vo.TicketTimePriceVO;

/**
 * 分销时间价格相关服务
 *
 * @author songjunbao
 * @createdate 2018/2/7
 */
public interface ITntTimePriceRefService {

    public PriceResponse<TicketTimePriceVO> getTimePrice(PriceRequest request);
}
