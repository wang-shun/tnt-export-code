package com.lvmama.tnt.biz.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.lvmama.tnt.biz.service.TntCodeBatchService;
import com.lvmama.tnt.dal.domain.TntCodeBatchJoinCodeGoodsPO;
import com.lvmama.tnt.dal.domain.TntCodeBatchPO;
import com.lvmama.tnt.dal.mapper.TntCodeBatchMapper;
import com.lvmama.tnt.export.code.api.constant.BatchStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author songjunbao
 * @createdate 2018/1/30
 */
@Service("tntCodeBatchServiceImpl")
public class TntCodeBatchServiceImpl implements TntCodeBatchService {

    @Autowired
    private TntCodeBatchMapper tntCodeBatchMapper;

    @Override
    public TntCodeBatchJoinCodeGoodsPO findByBatchNo(String batchNo) {
        return tntCodeBatchMapper.findByBatchNo(batchNo);
    }

    @Override
    public long totalCountByJoin(TntCodeBatchJoinCodeGoodsPO tntCodeBatchJoinCodeGoodsPO) {
        return tntCodeBatchMapper.totalCountByJoin(tntCodeBatchJoinCodeGoodsPO);
    }

    @Override
    public Page<TntCodeBatchJoinCodeGoodsPO> findByPageJoin(TntCodeBatchJoinCodeGoodsPO tntCodeBatchJoinCodeGoodsPO, int pageNo, int pageSize) {
        PageHelper.startPage(pageNo, pageSize);
        return tntCodeBatchMapper.findByPageJoin(tntCodeBatchJoinCodeGoodsPO);
    }

    @Override
    public long totalCount(TntCodeBatchPO tntCodeBatchPO) {
        return tntCodeBatchMapper.totalCount(tntCodeBatchPO);
    }

    @Override
    public List<TntCodeBatchPO> findByPage(TntCodeBatchPO tntCodeBatchPO, int currentPage, int pageSize) {
        PageHelper.startPage(currentPage, pageSize);
        return tntCodeBatchMapper.findByPage(tntCodeBatchPO).getResult();
    }

    @Override
    public List<Map<String, Integer>> queryWaitingCodeNum() {
        return tntCodeBatchMapper.queryWaitingCodeNum();
    }

    @Override
    public TntCodeBatchPO findById(Long id) {
        return tntCodeBatchMapper.findById(id);
    }


    @Override
    public void updateBatchStatusAndMessage(Long batchId, BatchStatus batchStatus,  String statusMessage) {
        TntCodeBatchPO statusCondition = new TntCodeBatchPO();
        statusCondition.setId(batchId);
        statusCondition.setBatchStatus(batchStatus.name());
        statusCondition.setStatusMessage(statusMessage);
        statusCondition.setUpdateTime(new Date());
        tntCodeBatchMapper.update(statusCondition);
    }

    @Override
    public boolean saveCodeBatch(TntCodeBatchPO tntCodeBatchPO) {
        return tntCodeBatchMapper.save(tntCodeBatchPO) > 0;
    }

    @Override
    public List<TntCodeBatchPO> findStatusBatch(String batchStatus) {
        TntCodeBatchPO statusCondition = new TntCodeBatchPO();
        statusCondition.setBatchStatus(batchStatus);
        return tntCodeBatchMapper.findBatch(statusCondition);
    }

    @Override
    public void updateOrderSuccessNum(Long batchId, Integer orderSuccessNum) {
        TntCodeBatchPO updateItem = new TntCodeBatchPO();
        updateItem.setId(batchId);
        updateItem.setOrderSuccessNum(orderSuccessNum);
        updateItem.setUpdateTime(new Date());
        tntCodeBatchMapper.update(updateItem);
    }

    @Override
    public void updateOrderCodeNum(Long batchId, Integer codeSuccessNum) {
        TntCodeBatchPO updateItem = new TntCodeBatchPO();
        updateItem.setId(batchId);
        updateItem.setCodeSuccessNum(codeSuccessNum);
        updateItem.setUpdateTime(new Date());
        tntCodeBatchMapper.update(updateItem);
    }

    @Override
    public void updateRetryCount(Long batchId, Integer retryCount) {
        TntCodeBatchPO updateItem = new TntCodeBatchPO();
        updateItem.setId(batchId);
        updateItem.setRetryCount(retryCount);
        updateItem.setUpdateTime(new Date());
        tntCodeBatchMapper.update(updateItem);
    }

    @Override
    public boolean updateFileId(String batchNo, Long fileId) {
        TntCodeBatchPO updateItem = new TntCodeBatchPO();
        //唯一性
        updateItem.setBatchNo(batchNo);
        updateItem.setFileId(fileId);
        return tntCodeBatchMapper.updateByBatchNo(updateItem) == 1 ;
    }
}
