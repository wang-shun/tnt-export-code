package com.lvmama.tnt.biz.converter;

import com.lvmama.tnt.biz.order.constant.ExportCodeConstant;
import com.lvmama.tnt.biz.order.vo.OrderRequest;
import com.lvmama.tnt.dal.domain.TntCodeBatchPO;
import org.springframework.stereotype.Service;

/**
 * 批次信息转化成导码下单信息
 *
 * @author songjunbao
 * @createdate 2018/4/8
 */
@Service("batch2OrderRequestConverter")
public class Batch2OrderRequestConverter implements Converter<TntCodeBatchPO, OrderRequest>{

    @Override
    public OrderRequest convert(TntCodeBatchPO source) {
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setProductId(source.getProductId());
        orderRequest.setCategoryId(source.getCategoryId());
        orderRequest.setGoodsId(source.getGoodsId());
        orderRequest.setUserName(source.getUserName());
        orderRequest.setUserId(source.getUserId());
        orderRequest.setCodeType(source.getCodeType());
        orderRequest.setBatchNo(source.getBatchNo());
        orderRequest.setUserIp(ExportCodeConstant.DEFAULT_IP);
        //分销商名称 是CompanyName 还是realName 好点
        orderRequest.setDistributorName(source.getCompanyName());
        orderRequest.setEnFirstName(source.getEnFirstName());
        orderRequest.setEnLastName(source.getEnLastName());
        //数量为1
        orderRequest.setQuantity(ExportCodeConstant.DEFAULT_QUANTITY);
        orderRequest.setVisitTime(source.getVisitTime());
        orderRequest.setBatchPrice(source.getPrice());
        orderRequest.setPeopleType(source.getPeopleType());
        orderRequest.setContactName(source.getContactName());
        orderRequest.setContactMobile(source.getContactMobile());
        orderRequest.setContactEmail(source.getContactEmail());
        //分销订单结算价
        orderRequest.setOrderSettleAmount(source.getPrice());
        return orderRequest;
    }
}
