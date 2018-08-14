package com.lvmama.tnt.biz.processer;

import com.github.ltsopensource.core.domain.Action;
import com.github.ltsopensource.core.domain.Job;
import com.github.ltsopensource.spring.tasktracker.JobRunnerItem;
import com.github.ltsopensource.spring.tasktracker.LTS;
import com.github.ltsopensource.tasktracker.Result;
import com.lvmama.scenic.api.hoard.vo.HoardCodeGoodsChannelVo;
import com.lvmama.scenic.api.hoard.vo.HoardCodeGoodsVO;
import com.lvmama.tnt.biz.converter.Hoard2ExportGoodsConverter;
import com.lvmama.tnt.biz.service.TntCodeGoodsService;
import com.lvmama.tnt.dal.domain.TntCodeGoodsPO;
import com.lvmama.tnt.reference.service.IScenicRefService;
import com.lvmama.tnt.reference.service.ITntProductRefService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 *  分销导码商品同步
 *
 * @author songjunbao
 * @createdate 2018/1/26
 */

@LTS
public class ExportCodeGoodsTracker {
    private static Logger logger = LoggerFactory.getLogger(ExportCodeGoodsTracker.class);


    @Autowired
    private IScenicRefService scenicRefService;

    @Autowired
    private TntCodeGoodsService tntCodeGoodsService;

    @Autowired
    private Hoard2ExportGoodsConverter hoard2ExportGoodsConverter;

    @Autowired
    private ITntProductRefService tntProductRefService;

    @JobRunnerItem(shardValue = "HOARD_CODE_GOODS_ADD")
    public Result addGoods(Job job){
        logger.info("addCodeGoods job:"+ job);
        String goodsId = job.getParam("bizId");
        HoardCodeGoodsVO horadCodeGoods = scenicRefService.getHoardCodeGoods(Long.valueOf(goodsId));
        TntCodeGoodsPO po = hoard2ExportGoodsConverter.convert(horadCodeGoods);
        tntCodeGoodsService.saveCodeGoods(po);
        return new Result(Action.EXECUTE_SUCCESS);
    }




    @JobRunnerItem(shardValue = "HOARD_CODE_GOODS_MODIFY")
    public Result modifyGoods(Job job){
        logger.info("modifyGoods job:"+ job);
        String goodsId = job.getParam("bizId");
        TntCodeGoodsPO codeGoods = tntCodeGoodsService.findByGoodsId(Long.valueOf(goodsId));
        HoardCodeGoodsVO horadCodeGoods = scenicRefService.getHoardCodeGoods(Long.valueOf(goodsId));

        if (codeGoods != null){
            List<HoardCodeGoodsChannelVo> channelList=  horadCodeGoods.getHoardCodeGoodsChannelList();
            codeGoods.setIsExport(hoard2ExportGoodsConverter.isExport(channelList, horadCodeGoods.getHoardStatus()) ? "Y":"N");
            codeGoods.setProductName(horadCodeGoods.getProductName());
            codeGoods.setGoodsName(horadCodeGoods.getGoodsName());
            //修改时间
            codeGoods.setUpdateTime(new Date());
            tntCodeGoodsService.updateCodeGoods(codeGoods);
        } else {
            tntCodeGoodsService.saveCodeGoods(hoard2ExportGoodsConverter.convert(horadCodeGoods));
        }


        return new Result(Action.EXECUTE_SUCCESS);

    }


    @JobRunnerItem(shardValue = "HOARD_CODE_GOODS_ENABLED")
    public Result enableGoods(Job job){
        logger.info("enableGoods job:"+ job);
        String goodsId = job.getParam("bizId");
        TntCodeGoodsPO codeGoods = tntCodeGoodsService.findByGoodsId(Long.valueOf(goodsId));
        if(codeGoods != null){
            codeGoods.setIsExport("Y");
            codeGoods.setUpdateTime(new Date());
            tntCodeGoodsService.updateCodeGoods(codeGoods);

        }

        return new Result(Action.EXECUTE_SUCCESS);

    }


    @JobRunnerItem(shardValue = "HOARD_CODE_GOODS_DISABLED")
    public Result disableGoods(Job job){
        logger.info("disableGoods job:"+ job);
        String goodsId = job.getParam("bizId");
        TntCodeGoodsPO codeGoods = tntCodeGoodsService.findByGoodsId(Long.valueOf(goodsId));
        if(codeGoods != null){
            codeGoods.setIsExport("N");
            codeGoods.setUpdateTime(new Date());
            tntCodeGoodsService.updateCodeGoods(codeGoods);

        }

        return new Result(Action.EXECUTE_SUCCESS);

    }




}
