package com.lvmama.tnt.reference.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lvmama.tnt.common.domain.TntMerchantDTO;
import com.lvmama.tnt.reference.service.ITntUserRefService;
import com.lvmama.tnt.user.po.TntOperatingSpecialist;
import com.lvmama.tnt.user.po.TntUser;
import com.lvmama.tnt.user.po.TntUserDetail;
import com.lvmama.tnt.user.service.TntOperatingSpecialistService;
import com.lvmama.tnt.user.service.TntUserService;
import com.lvmama.tnt.user.vo.TntUserVo;
import com.lvmama.tnt.util.Page;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 */
@Service
public class TntUserRefServiceImpl implements ITntUserRefService {

    @Reference
    private TntUserService tntUserService;

    @Reference
    private TntOperatingSpecialistService tntOperatingSpecialistService;

    @Override
    public List<TntMerchantDTO> findTntMerchantInfo(String userName, String operator) {
        List<TntMerchantDTO> tntMerchantDTOS = new ArrayList<>();

        TntOperatingSpecialist tntOperatingSpecialist = new TntOperatingSpecialist();
        tntOperatingSpecialist.setCategory(TntOperatingSpecialist.Category.OPERATE);
        Map<String,String> operators = tntOperatingSpecialistService.map(tntOperatingSpecialist);

        String operatingSpecialistId = null;
        if (!StringUtils.isEmpty(operator)) {
            for (Map.Entry entry : operators.entrySet()) {
                if (operator.equals(entry.getValue())) {
                    operatingSpecialistId = (String) entry.getKey();
                    break;
                }
            }
        }

        TntUserDetail userDetail = new TntUserDetail();
        userDetail.setUserName(userName);
        userDetail.setChannelCode("DISTRIBUTOR_B2B");
        userDetail.setOperatingSpecialistId(StringUtils.isEmpty(operatingSpecialistId)?null:Long.valueOf(operatingSpecialistId));
        Page<TntUserDetail> page = Page.page(100,1, userDetail);
        List<TntUserDetail> users =  tntUserService.selectUserDetailAndUser(page);

        TntMerchantDTO tntMerchantDTO = null;
        for (TntUserDetail tntUser : users) {
            if (tntUser.getChannelId() == null || 102L != tntUser.getChannelId()) {
                continue;
            }
            tntMerchantDTO = new TntMerchantDTO();
            tntMerchantDTO.setUserName(tntUser.getUserName());
            tntMerchantDTO.setUserId(tntUser.getUserId());
            tntMerchantDTO.setCompanyName(tntUser.getCompanyName());
            tntMerchantDTO.setOperator(operators.get(String.valueOf(tntUser.getOperatingSpecialistId())));
            tntMerchantDTOS.add(tntMerchantDTO);
        }
        return tntMerchantDTOS;
    }
}
