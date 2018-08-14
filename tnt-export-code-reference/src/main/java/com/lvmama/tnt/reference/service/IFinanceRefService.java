package com.lvmama.tnt.reference.service;


import com.lvmama.tnt.common.domain.ResponseDTO;

/**
 *
 */
public interface IFinanceRefService {

    /**
     * 验证预存款支付密码
     * @param userId
     * @param payPassword
     * @return
     */
    ResponseDTO<Boolean> validateCashPayPassword(Long userId, String payPassword);

    /**
     * 验证预存款余额
     * @param userId
     * @param amount
     * @return 当前预存款余额
     */
    ResponseDTO<Long> validateCashPayBalance(Long userId, Long amount);

    /**
     * 验证授信支付密码
     * @param userId
     * @param password
     * @return
     */
    ResponseDTO<Boolean> validateCreditPayPassword(Long userId, String password);

    /**
     * 授信当前可用余额
     * @param userId
     * @param amount
     * @return
     */
    ResponseDTO<Long> validateCreditPayBalance(Long userId, Long amount);
}
