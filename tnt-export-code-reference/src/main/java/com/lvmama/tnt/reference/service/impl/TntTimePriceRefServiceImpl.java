package com.lvmama.tnt.reference.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lvmama.tnt.reference.service.ITntTimePriceRefService;
import com.lvmama.tnt.timeprice.core.api.TicketTimePriceFacadeService;
import com.lvmama.tnt.timeprice.core.api.request.PriceRequest;
import com.lvmama.tnt.timeprice.core.api.response.PriceResponse;
import com.lvmama.tnt.timeprice.core.api.response.vo.TicketTimePriceVO;
import org.springframework.stereotype.Service;

/**
 * @author songjunbao
 * @createdate 2018/2/7
 */
@Service
public class TntTimePriceRefServiceImpl implements ITntTimePriceRefService {

    @Reference(group = "primary")
    private TicketTimePriceFacadeService TicketTimePriceFacadeService;

    public PriceResponse<TicketTimePriceVO> getTimePrice(PriceRequest request) {
        return TicketTimePriceFacadeService.getTimePrice(request);
    }
}
