package com.lvmama.tnt.biz.order.filter.impl;

import com.lvmama.tnt.biz.order.constant.ExportCodeConstant;
import com.lvmama.tnt.biz.order.filter.OrderFilter;
import com.lvmama.tnt.biz.order.filter.OrderFilterChain;
import com.lvmama.tnt.biz.order.vo.ExportCodeOrderException;
import com.lvmama.tnt.biz.order.vo.OrderErrorCode;
import com.lvmama.tnt.biz.order.vo.OrderRequest;
import com.lvmama.tnt.biz.order.vo.OrderResponse;
import com.lvmama.tnt.reference.service.ITntMerchantRefService;
import com.lvmama.tnt.user.po.TntMemberAccount;
import com.lvmama.tnt.user.po.TntUser;
import com.lvmama.tnt.user.vo.TntBaseUserVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 会员校验
 *
 * @author songjunbao
 * @createdate 2018/2/5
 */
@Service("memberAccountFilter")
public class MemberAccountFilter implements OrderFilter {
    private static Logger logger = LoggerFactory.getLogger(MemberAccountFilter.class);

    @Autowired
    private ITntMerchantRefService tntMerchantRefService;

    @Override
    public void process(OrderRequest request, OrderResponse response, OrderFilterChain chain) {
        logger.info(">>>member account check, batchNo:" + request.getBatchNo());
        Long userId = request.getUserId();
        TntUser tntUser = tntMerchantRefService.getUserInfo(userId);
        TntBaseUserVo memberInfo = new TntBaseUserVo();
        memberInfo.setUserId(userId);
        memberInfo.setUserName(tntUser.getUserName());
        memberInfo.setRealName(tntUser.getRealName());
        memberInfo.setCompanyName(tntUser.getDetail().getCompanyName());

        TntMemberAccount tntMemberAccount = tntMerchantRefService.getTntMemberAccount(memberInfo);
        logger.info("tntMemberAccount:" + tntMemberAccount);
        if (tntMemberAccount == null){
            logger.error("分销会员账户不存在userId:"+userId);
            throw new ExportCodeOrderException(OrderErrorCode.MEMBER_ACCOUNT_NOT_FOUND.getMessage(),
                    OrderErrorCode.MEMBER_ACCOUNT_NOT_FOUND.getCode());
        }
        //绑定会员信息
        request.setMemberId(tntMemberAccount.getMemberId());
        request.setMemberNo(tntMemberAccount.getMemberNo());

        //绑定客服信息，短信签名
        request.setCustomerServiceTel(ExportCodeConstant.LVMAMA_SERVICE_TEL);
        request.setDistributionSuffix(tntUser.getDetail().getSmsRemark());
        request.setDistributorName(tntUser.getRealName());
        request.setUserName(tntUser.getUserName());
        request.setDistributorMobile(tntUser.getMobileNumber());
        chain.process(request, response);

    }
}
