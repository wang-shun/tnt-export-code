package com.lvmama.tnt.dal.mapper;

import com.github.pagehelper.Page;
import com.lvmama.tnt.dal.domain.TntCodeUserSetJoinGoodsPO;
import com.lvmama.tnt.dal.domain.TntCodeUserSetPO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 *
 */
@Repository
public interface TntCodeUserSetMapper {

    int deleteExist(@Param("goodsId") Long goodsId, @Param("userIds") Set<String> userIds);

    int isExist(@Param("goodsId") Long goodsId, @Param("userId") Long userId);

    List<TntCodeUserSetPO> findByParam(TntCodeUserSetPO tntCodeUserSetPO);

    long totalCount(TntCodeUserSetPO tntCodeUserSetPO);

    Page<TntCodeUserSetPO> findByPage(TntCodeUserSetPO tntCodeUserSetPO);

    Page<TntCodeUserSetJoinGoodsPO> findUserSetList(TntCodeUserSetPO tntCodeUserSetPO);

    TntCodeUserSetPO findById(Long id);

    int update(TntCodeUserSetPO tntCodeUserSetPO);

    int save(TntCodeUserSetPO tntCodeUserSetPO);

    int saveBatch(List<TntCodeUserSetPO> tntCodeUserSetPOs);

    int deleteUserSet(Long id);
}
