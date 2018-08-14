package com.lvmama.tnt.reference.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lvmama.comm.pet.po.pay.PayPayment;
import com.lvmama.comm.pet.service.pay.PayPaymentService;
import org.springframework.stereotype.Service;

/**
 * 支付组服务
 *
 * @author songjunbao
 * @createdate 2018/2/26
 */
@Service
public class PayPaymentServiceRemote {
    @Reference
    private PayPaymentService payPaymentService;


    /**
     * 保存支付流水号
     * @param payPayment
     * @return
     */
    public Long savePayment(PayPayment payPayment){
        return payPaymentService.savePayment(payPayment);

    }

}
