package com.lvmama.tnt.biz.service.impl;

import com.github.pagehelper.PageHelper;
import com.lvmama.tnt.biz.service.TntCodeUserSetService;
import com.lvmama.tnt.dal.domain.TntCodeUserSetJoinGoodsPO;
import com.lvmama.tnt.dal.domain.TntCodeUserSetPO;
import com.lvmama.tnt.dal.mapper.TntCodeUserSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * @author songjunbao
 * @createdate 2018/1/30
 */
@Service("tntCodeUserSetServiceImpl")
public class TntCodeUserSetServiceImpl implements TntCodeUserSetService {

    @Autowired
    private TntCodeUserSetMapper tntCodeUserSetMapper;

    @Override
    public int deleteExist(Long goodsId, Set<String> userIds) {
        return tntCodeUserSetMapper.deleteExist(goodsId, userIds);
    }

    @Override
    public boolean isExist(Long goodsId, Long userId) {
        return tntCodeUserSetMapper.isExist(goodsId, userId) > 0;
    }

    @Override
    public List<TntCodeUserSetPO> findByParam(TntCodeUserSetPO tntCodeUserSetPO) {
        return tntCodeUserSetMapper.findByParam(tntCodeUserSetPO);
    }

    @Override
    public long totalCount(TntCodeUserSetPO tntCodeUserSetPO) {
        return tntCodeUserSetMapper.totalCount(tntCodeUserSetPO);
    }

    @Override
    public List<TntCodeUserSetPO> findByPage(TntCodeUserSetPO tntCodeUserSetPO, int currentPage, int pageSize) {
        PageHelper.startPage(currentPage, pageSize);
        return tntCodeUserSetMapper.findByPage(tntCodeUserSetPO).getResult();
    }

    @Override
    public List<TntCodeUserSetJoinGoodsPO> findUserSetList(TntCodeUserSetPO tntCodeUserSetPO, int currentPage, int pageSize) {
        PageHelper.startPage(currentPage, pageSize);
        return tntCodeUserSetMapper.findUserSetList(tntCodeUserSetPO).getResult();
    }

    @Override
    public TntCodeUserSetPO findById(Long id) {
        return tntCodeUserSetMapper.findById(id);
    }

    @Override
    public boolean updateUserSet(TntCodeUserSetPO tntCodeUserSetPO) {
        return tntCodeUserSetMapper.update(tntCodeUserSetPO) > 0;
    }

    @Override
    public boolean saveUserSet(TntCodeUserSetPO tntCodeUserSetPO) {
        return tntCodeUserSetMapper.save(tntCodeUserSetPO) > 0;
    }

    @Override
    public int saveBatch(List<TntCodeUserSetPO> tntCodeUserSetPOs) {
        return tntCodeUserSetMapper.saveBatch(tntCodeUserSetPOs);
    }

    @Override
    public int deleteUserSet(Long id) {
        return tntCodeUserSetMapper.deleteUserSet(id);
    }
}
