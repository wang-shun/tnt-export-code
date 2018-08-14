package com.lvmama.tnt.reference.service;


import com.lvmama.tnt.common.domain.TntMerchantDTO;

import java.util.List;

/**
 *
 */
public interface ITntUserRefService {

    List<TntMerchantDTO> findTntMerchantInfo(String userName, String operator);
}
