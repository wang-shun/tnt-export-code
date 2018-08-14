package com.lvmama.tnt.biz.service;

import com.lvmama.tnt.dal.domain.TntCodeUserSetJoinGoodsPO;
import com.lvmama.tnt.dal.domain.TntCodeUserSetPO;

import java.util.List;
import java.util.Set;

/**
 * @author songjunbao
 * @createdate 2018/1/30
 */
public interface TntCodeUserSetService {

    int deleteExist(Long goodsId, Set<String> userIds);

    boolean isExist(Long goodsId, Long userId);

    List<TntCodeUserSetPO> findByParam(TntCodeUserSetPO tntCodeUserSetPO);

    long totalCount(TntCodeUserSetPO tntCodeUserSetPO);

    List<TntCodeUserSetPO> findByPage(TntCodeUserSetPO tntCodeUserSetPO, int currentPage, int pageSize);

    List<TntCodeUserSetJoinGoodsPO> findUserSetList(TntCodeUserSetPO tntCodeUserSetPO, int currentPage, int pageSize);

    TntCodeUserSetPO findById(Long id);

    boolean updateUserSet(TntCodeUserSetPO tntCodeUserSetPO);

    boolean saveUserSet(TntCodeUserSetPO tntCodeUserSetPO);

    int saveBatch(List<TntCodeUserSetPO> tntCodeUserSetPOs);

    int deleteUserSet(Long tntUserSetID);
}
