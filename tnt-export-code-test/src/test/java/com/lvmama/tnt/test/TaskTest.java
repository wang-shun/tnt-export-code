package com.lvmama.tnt.test;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.lvmama.scenic.api.hoard.service.ScenicHoardCodeGoodsService;
import com.lvmama.scenic.api.hoard.vo.HoardCodeGoodsStockVo;
import com.lvmama.scenic.api.hoard.vo.HoardCodeGoodsVO;
import com.lvmama.scenic.api.order.service.ScenicOrderRequiredService;
import com.lvmama.scenic.api.order.vo.ScenicOrdRequiredVO;
import com.lvmama.scenic.api.vo.ResultHandleT;
import com.lvmama.tnt.biz.channel.NoticeChannel;
import com.lvmama.tnt.biz.channel.NoticeRequest;
import com.lvmama.tnt.biz.channel.NoticeService;
import com.lvmama.tnt.prod.po.TntProduct;
import com.lvmama.tnt.reference.service.ITntProductRefService;
import com.lvmama.tnt.web.task.ExportCodeTask;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

/**
 *
 */
public class TaskTest extends AbstractTest {

    @Autowired
    private ExportCodeTask exportCodeTask;
    @Test
    public void testTask(){
        exportCodeTask.createBatchOrder(0L,"");

    }


}
