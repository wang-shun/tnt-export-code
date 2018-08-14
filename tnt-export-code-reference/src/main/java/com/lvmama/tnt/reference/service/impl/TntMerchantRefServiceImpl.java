package com.lvmama.tnt.reference.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lvmama.tnt.reference.service.ITntMerchantRefService;
import com.lvmama.tnt.user.po.TntMemberAccount;
import com.lvmama.tnt.user.po.TntUser;
import com.lvmama.tnt.user.service.TntMemberAccountService;
import com.lvmama.tnt.user.service.TntUserService;
import com.lvmama.tnt.user.vo.TntBaseUserVo;
import org.springframework.stereotype.Service;

/**
 * @author songjunbao
 * @createdate 2018/2/5
 */
@Service("tntMerchantRefServiceImpl")
public class TntMerchantRefServiceImpl implements ITntMerchantRefService {


    @Reference
    private TntUserService tntUserService;

    @Reference
    private TntMemberAccountService tntMemberAccountService;

    @Override
    public TntUser getUserInfo(Long userId) {
        return tntUserService.findWithDetailByUserId(userId);
    }


    @Override
    public TntMemberAccount getTntMemberAccount(TntBaseUserVo tntBaseUserVo) {
        //TODO 参数校验
        return tntMemberAccountService.getTntMemberAccount(tntBaseUserVo);
    }
}
