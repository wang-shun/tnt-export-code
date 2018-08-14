package com.lvmama.tnt.biz.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.lvmama.tnt.biz.service.TntBatchOrderService;
import com.lvmama.tnt.dal.domain.TntBatchOrderPO;
import com.lvmama.tnt.dal.mapper.TntBatchOrderMapper;
import com.lvmama.tnt.export.code.api.constant.PasscodeStatus;
import com.lvmama.tnt.export.code.api.constant.PaymentStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author songjunbao
 * @createdate 2018/1/30
 */
@Service("tntBatchOrderServiceImpl")
public class TntBatchOrderServiceImpl implements TntBatchOrderService {

    @Autowired
    private TntBatchOrderMapper tntBatchOrderMapper;


    @Override
    public List<Long> findAllOrderIdsByBatchNo(String batchNo) {
        return tntBatchOrderMapper.findAllOrderIdsByBatchNo(batchNo);
    }

    @Override
    public long totalCount(TntBatchOrderPO tntBatchOrderPO) {
        return tntBatchOrderMapper.totalCount(tntBatchOrderPO);
    }


    @Override
    public List<TntBatchOrderPO> findByPage(TntBatchOrderPO tntBatchOrderPO, int currentPage, int pageSize) {
        PageHelper.startPage(currentPage, pageSize);
        Page<TntBatchOrderPO> page = tntBatchOrderMapper.findByPage(tntBatchOrderPO);
        return page.getResult();
    }

    @Override
    public TntBatchOrderPO findById(Long id) {
        return tntBatchOrderMapper.findById(id);
    }

    @Override
    public boolean updateBatchOrder(TntBatchOrderPO tntBatchOrderPO) {
        return tntBatchOrderMapper.updateByOrderId(tntBatchOrderPO) > 0;
    }

    @Override
    public boolean saveBatchOrder(TntBatchOrderPO tntBatchOrderPO) {
        return tntBatchOrderMapper.save(tntBatchOrderPO) > 0;
    }

    @Override
    public TntBatchOrderPO findByOrderId(Long orderId) {
        if (orderId == null){
            throw new IllegalArgumentException("orderId is null");
        }

        return tntBatchOrderMapper.findByOrderId(orderId);
    }

    @Override
    public int countOrderSuccessNum(String batchNo) {
        return tntBatchOrderMapper.countOrderStatusByBatchNo(batchNo, PaymentStatus.PAYED.name());
    }

    @Override
    public int countPassCodeSuccessNum(String batchNo) {
        return tntBatchOrderMapper.countPassCodeStatusByBatchNo(batchNo, PasscodeStatus.PASSCOED_SUCCESS.name());
    }
}
