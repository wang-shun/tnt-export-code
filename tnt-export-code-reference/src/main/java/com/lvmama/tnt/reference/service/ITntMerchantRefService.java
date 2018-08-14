package com.lvmama.tnt.reference.service;

import com.lvmama.tnt.user.po.TntMemberAccount;
import com.lvmama.tnt.user.po.TntUser;
import com.lvmama.tnt.user.vo.TntBaseUserVo;

/**
 * 分销商依赖服务
 *
 * @author songjunbao
 * @createdate 2018/2/5
 */
public interface ITntMerchantRefService {

    /**
     * 获取分销商信息
     * @param userId
     * @return
     */
    TntUser getUserInfo(Long userId);

    /**
     * 分销用户对应主站的会员账户信息
     * @param tntBaseUserVo
     * @return
     */
    TntMemberAccount getTntMemberAccount(TntBaseUserVo tntBaseUserVo);

}
