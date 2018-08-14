package com.lvmama.tnt.test;

import com.github.ltsopensource.core.json.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.scenic.api.back.goods.po.ScenicSuppGoods;
import com.lvmama.tnt.biz.order.constant.ExportCodeConstant;
import com.lvmama.tnt.biz.service.TntCodeBatchService;
import com.lvmama.tnt.common.util.BatchNoCreater;
import com.lvmama.tnt.dal.domain.*;
import com.lvmama.tnt.dal.mapper.*;
import com.lvmama.tnt.export.code.api.constant.*;
import com.lvmama.tnt.reference.service.IComlogService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class MapperTest extends AbstractTest {

    @Autowired
    private TntCodeGoodsMapper tntCodeGoodsMapper;
    @Autowired
    private TntBatchOrderMapper tntBatchOrderMapper;
    @Autowired
    private TntCodeBatchMapper tntCodeBatchMapper;
    @Autowired
    private TntCodeCommonSetMapper tntCodeCommonSetMapper;
    @Autowired
    private TntCodeInfoMapper tntCodeInfoMapper;
    @Autowired
    private TntCodeUserSetMapper tntCodeUserSetMapper;

    @Autowired
    private IComlogService comlogService;

    @Autowired
    private TntCodeBatchService tntCodeBatchService;

    @Test
    public void tets() {
    }

    @Test
    public void insertLog() {
        comlogService.addBatchLog("创建批次", 20180325907509L);
    }

    @Test
    public void testBatchInf() {
        TntCodeBatchJoinCodeGoodsPO po = tntCodeBatchMapper.findByBatchNo("DM6212345620180128001");
        System.out.println(po);
    }

    @Test
    public void testTntCodeUserSet() {
        TntCodeUserSetPO po = new TntCodeUserSetPO();
        po.setGoodsId(1235L);
        po.setUserId(123456L);

        int count = tntCodeUserSetMapper.save(po);
        Assert.assertEquals("save failed",Integer.valueOf(1), Integer.valueOf(count));

        po.setUserId(123L);
        count = tntCodeUserSetMapper.update(po);
        Assert.assertEquals("update failed",Integer.valueOf(1), Integer.valueOf(count));

        Long id = po.getId();
        TntCodeUserSetPO findPO = tntCodeUserSetMapper.findById(id);
        Assert.assertNotNull(findPO);

        Long totalCount = tntCodeUserSetMapper.totalCount(new TntCodeUserSetPO());
        Assert.assertNotSame(Long.valueOf(0), Long.valueOf(totalCount) );

        PageHelper.startPage(1, 10);
        Page<TntCodeUserSetPO> list = tntCodeUserSetMapper.findByPage(new TntCodeUserSetPO());
        Assert.assertNotNull(list);

    }

    @Test
    public void testTntCodeInfo() {
        TntCodeInfoPO po = new TntCodeInfoPO();
        po.setOrderId(62987966L);
        po.setBatchNo("DM20180404716447");
        po.setCodeUrl("/uploads/pc/place2/201804/e8c6d90e9bd34945601a15ff3bc68411.jpg");
        po.setOrderItemId(2024928138L);
        po.setPassCodeId(15561611L);
        po.setAddCode("450018");
        po.setUseStatus(CodeUseStatus.NOUSE.getCnName());
        po.setFileId(1L);
        int count = tntCodeInfoMapper.save(po);
        Assert.assertEquals("save failed",Integer.valueOf(1), Integer.valueOf(count));

        po.setCodeUrl("CODEUPDATE");
        count = tntCodeInfoMapper.update(po);
        Assert.assertEquals("update failed",Integer.valueOf(1), Integer.valueOf(count));

        Long id = po.getId();
        TntCodeInfoPO findPO = tntCodeInfoMapper.findById(id);
        Assert.assertNotNull(findPO);

        Long totalCount = tntCodeInfoMapper.totalCount(new TntCodeInfoPO());
        Assert.assertNotSame(Long.valueOf(0), Long.valueOf(totalCount) );

        Page<TntCodeInfoPO> list = tntCodeInfoMapper.findByPage(new TntCodeInfoPO());
        Assert.assertNotNull(list);

    }

    @Test
    public void testTntCodeCommonSet() {
        TntCodeCommonSetPO po = new TntCodeCommonSetPO();
        po.setGoodsId(123456L);
        po.setCodeType("CODE");
        int count = tntCodeCommonSetMapper.save(po);
        Assert.assertEquals("save failed",Integer.valueOf(1), Integer.valueOf(count));

        po.setCodeType("CODEUPDATE");
        count = tntCodeCommonSetMapper.update(po);
        Assert.assertEquals("update failed",Integer.valueOf(1), Integer.valueOf(count));

        Long id = po.getId();
        TntCodeCommonSetPO findPO = tntCodeCommonSetMapper.findById(id);
        Assert.assertNotNull(findPO);

        Long totalCount = tntCodeCommonSetMapper.totalCount(new TntCodeCommonSetPO());
        Assert.assertNotSame(Long.valueOf(0), Long.valueOf(totalCount) );

        Page<TntCodeCommonSetPO> list = tntCodeCommonSetMapper.findByPage(new TntCodeCommonSetPO());
        Assert.assertNotNull(list);

    }

    @Test
    public void testTntCodeBatch() {
        TntCodeBatchPO po = new TntCodeBatchPO();
        po.setBatchNo(BatchNoCreater.batchNoGenerator());
        po.setBatchStatus(BatchStatus.WAITING.name());
        po.setCreateTime(new Date());
        po.setVisitTime(new Date());
        po.setPrice(1000L);
        po.setCodeNum(200);
        po.setGoodsId(172127L);
        po.setMerchantId(62L);
        po.setUserId(62L);
        po.setContactName("测试刘妍");
        po.setContactMobile("13122675180");
        po.setPaymentType(PaymentType.CASH.name());
        po.setProductId(172127L);
        po.setStatusMessage("异常终止");
        po.setPeopleType(PeopleType.PEOPLE_TYPE_ADULT.name());
        po.setTravellerPolicy(TravellerPolicy.CONTACTER.name());
        po.setCategoryId(11L);
        po.setFileId(1L);
        int count = tntCodeBatchMapper.save(po);
        Assert.assertEquals("save failed",Integer.valueOf(1), Integer.valueOf(count));

        po.setBatchNo("B12345");
        count = tntCodeBatchMapper.update(po);
        Assert.assertEquals("update failed",Integer.valueOf(1), Integer.valueOf(count));

        Long id = po.getId();
        TntCodeBatchPO findPO = tntCodeBatchMapper.findById(id);
        Assert.assertNotNull(findPO);

        Long totalCount = tntCodeBatchMapper.totalCount(new TntCodeBatchPO());
        Assert.assertNotSame(Long.valueOf(0), Long.valueOf(totalCount) );

        Page<TntCodeBatchPO> list = tntCodeBatchMapper.findByPage(new TntCodeBatchPO());
        Assert.assertNotNull(list);

    }

    @Test
    public void testTntBatchOrder() {
        TntBatchOrderPO po = new TntBatchOrderPO();
        po.setBatchNo("12345");
        po.setOrderId(12345678L);
        po.setTntOrderId(12345678L);
        po.setCreateTime(new Date());
        int count = tntBatchOrderMapper.save(po);
        Assert.assertEquals("save failed",Integer.valueOf(1), Integer.valueOf(count));

        po.setBatchNo("B12345");
        Assert.assertEquals("update failed",Integer.valueOf(1), Integer.valueOf(count));

        Long id = po.getId();
        TntBatchOrderPO findPO = tntBatchOrderMapper.findById(id);
        Assert.assertNotNull(findPO);

        Long totalCount = tntBatchOrderMapper.totalCount(new TntBatchOrderPO());
        Assert.assertNotSame(Long.valueOf(0), Long.valueOf(totalCount) );

        Page<TntBatchOrderPO> list = tntBatchOrderMapper.findByPage(new TntBatchOrderPO());
        Assert.assertNotNull(list);

    }


    @Test
    public void saveTntCodeGoods() {
        TntCodeGoodsPO po = new TntCodeGoodsPO();
        po.setGoodsId(123456L);
        po.setGoodsName("GOODS");
        po.setProductName("PRODUCT");
        po.setProductId(1234L);
        po.setIsExport("Y");
        int count = tntCodeGoodsMapper.save(po);
        Assert.assertEquals("save failed",Integer.valueOf(1), Integer.valueOf(count));

        po.setGoodsName("GOODSNAME_UPDATE");
        count = tntCodeGoodsMapper.update(po);
        Assert.assertEquals("update failed",Integer.valueOf(1), Integer.valueOf(count));

        Long id = po.getId();
        TntCodeGoodsPO findPO = tntCodeGoodsMapper.findByID(id);
        Assert.assertNotNull(findPO);

        Long totalCount = tntCodeGoodsMapper.totalCount(new TntCodeGoodsPO());
        Assert.assertNotSame(Long.valueOf(0), Long.valueOf(totalCount) );

        Page<TntCodeGoodsPO> list = tntCodeGoodsMapper.findByPage(new TntCodeGoodsPO());
        Assert.assertNotNull(list);

    }

    @Test
    public void testUpdateStatusAndMsg(){
        tntCodeBatchService.updateBatchStatusAndMessage(106L,BatchStatus.TERMINATE, ExportCodeConstant.STOP_FAIL_MESSAGE);


    }

    @Test
    public void testFindByBatchNo(){
       List<TntCodeInfoPO> infoPOList = tntCodeInfoMapper.findByBatchNo("DM20180404716447");
        System.out.println(infoPOList.size());
    }


    @Test
    public void testUpdateByBatchNo(){
        TntCodeBatchPO batchPO = new TntCodeBatchPO();
        batchPO.setFileId(2L);
        batchPO.setBatchNo("B12345");
        tntCodeBatchMapper.updateByBatchNo(batchPO);

    }

    @Test
    public void testFindBatch(){
        TntCodeBatchPO batchPO = new TntCodeBatchPO();
        batchPO.setId(1L);
//        batchPO.setBatchNo("B12345");
       List<TntCodeBatchPO> batchPO2=  tntCodeBatchMapper.findBatch(batchPO);
       System.out.println(batchPO2);
    }


    @Test
    public void testUpdateCodeStatus(){
        TntCodeInfoPO code = new TntCodeInfoPO();
        code.setOrderItemId(2024934041L);
        code.setUseStatus(CodeUseStatus.DESTROYED.name());
        tntCodeInfoMapper.updateCodeStatus(code);

    }


}
