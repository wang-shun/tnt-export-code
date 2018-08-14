package com.lvmama.tnt.reference.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lvmama.tnt.cash.account.entity.TntCashAccount;
import com.lvmama.tnt.cash.account.service.ITntCashAccountService;
import com.lvmama.tnt.cash.vo.ResultGod;
import com.lvmama.tnt.common.domain.ResponseDTO;
import com.lvmama.tnt.finance.service.account.credit.ICreditFinanceAccountRemoteService;
import com.lvmama.tnt.finance.service.account.credit.exception.CreditFinanceAccountException;
import com.lvmama.tnt.finance.service.account.credit.vo.CreditFinanceAccountVO;
import com.lvmama.tnt.reference.service.IFinanceRefService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class FinanceRefServiceImpl implements IFinanceRefService {

    private static Logger logger = LoggerFactory.getLogger(FinanceRefServiceImpl.class);

    @Reference
    private ITntCashAccountService tntCashAccountService;

    @Reference
    private ICreditFinanceAccountRemoteService creditFinanceAccountRemoteService;

    @Override
    public ResponseDTO<Boolean> validateCashPayPassword(Long userId, String payPassword) {
        ResponseDTO<Boolean> response = new ResponseDTO<>();
        TntCashAccount tntCashAccount = new TntCashAccount();
        tntCashAccount.setUserId(userId);
        tntCashAccount.setPaymentPassword(payPassword);
        ResultGod<String> resultGod = tntCashAccountService.validatePayPassword(tntCashAccount);
        if (resultGod != null) {
            response.setSuccess(resultGod.isSuccess());
            response.setRespCode(resultGod.getErrorCode());
            response.setRespMsg(resultGod.getErrorText());
            response.setResult(resultGod.isSuccess());
        }
        return response;
    }

    @Override
    public ResponseDTO<Long> validateCashPayBalance(Long userId, Long amount) {
        ResponseDTO<Long> response = new ResponseDTO<>();
        TntCashAccount tntCashAccount = new TntCashAccount();
        tntCashAccount.setUserId(userId);
        ResultGod<TntCashAccount> resultGod = tntCashAccountService.validateCashBalance(tntCashAccount, amount);
        if (resultGod != null) {
            response.setSuccess(resultGod.isSuccess());
            response.setRespCode(resultGod.getErrorCode());
            response.setRespMsg(resultGod.getErrorText());
            if (resultGod.getResult() != null) {
                response.setResult(resultGod.getResult().getBalance());
            }
        }

        return response;
    }

    @Override
    public ResponseDTO<Boolean> validateCreditPayPassword(Long userId, String password) {
        ResponseDTO<Boolean> responseDTO = new ResponseDTO<>();
        try {
            boolean result =  creditFinanceAccountRemoteService.validatePassword(userId, password);
            responseDTO.setSuccess(result);
            responseDTO.setResult(result);
        } catch (CreditFinanceAccountException e) {
            logger.error("", e);
            responseDTO.setSuccess(false);
            responseDTO.setResult(false);
            responseDTO.setRespMsg(e.getMessage());
        }
        return responseDTO;
    }

    @Override
    public ResponseDTO<Long> validateCreditPayBalance(Long userId, Long amount) {
        ResponseDTO<Long> responseDTO = new ResponseDTO<Long>();
        try {
            CreditFinanceAccountVO creditFinanceAccountVO = creditFinanceAccountRemoteService.selectByUserId(userId);
            if (creditFinanceAccountVO != null) {
                Long availableCredit = creditFinanceAccountVO.getAvailableCredit();
                if (availableCredit != null && availableCredit > 0) {
                    boolean available = amount<availableCredit;
                    responseDTO.setSuccess(available);
                    responseDTO.setResult(available?availableCredit:null);
                }
            }
        } catch (CreditFinanceAccountException e) {
            logger.error("", e);
            responseDTO.setSuccess(false);
            responseDTO.setRespMsg(e.getMessage());
        }
        return responseDTO;
    }
}
