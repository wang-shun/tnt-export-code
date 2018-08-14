package com.lvmama.tnt.biz.converter;

import com.lvmama.scenic.api.hoard.vo.HoardCodeGoodsChannelVo;
import com.lvmama.scenic.api.hoard.vo.HoardCodeGoodsVO;
import com.lvmama.tnt.dal.domain.TntCodeGoodsPO;
import com.lvmama.tnt.prod.po.TntProduct;
import com.lvmama.tnt.reference.service.ITntProductRefService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 主站囤码转换成分销导码商品
 *
 * @author songjunbao
 * @createdate 2018/1/30
 */
@Service("hoard2ExportGoodsConverter")
public class Hoard2ExportGoodsConverter implements Converter<HoardCodeGoodsVO,TntCodeGoodsPO>{

    @Autowired
    private ITntProductRefService tntProductRefService;

    @Override
    public TntCodeGoodsPO convert(HoardCodeGoodsVO source) {
        if(source == null){
            throw new IllegalArgumentException("source is null");
        }

        TntCodeGoodsPO codeGoods =  new TntCodeGoodsPO();
//       产品ID需要查询
        TntProduct tntProduct = tntProductRefService.findProductInfoByGoodsId(source.getGoodsId());
        if (tntProduct != null){
            codeGoods.setTntProductId(tntProduct.getTntProductId());
            codeGoods.setProductId(tntProduct.getProductId());
        }
        codeGoods.setCategoryId(11L);
        codeGoods.setGoodsId(source.getGoodsId());
        codeGoods.setGoodsName(source.getGoodsName());
        codeGoods.setProductName(source.getProductName());
        List<HoardCodeGoodsChannelVo>  channelList=  source.getHoardCodeGoodsChannelList();
        codeGoods.setIsExport(isExport(channelList, source.getHoardStatus()) ? "Y":"N");
        codeGoods.setCreateTime(new Date());
        codeGoods.setUpdateTime(new Date());
        return codeGoods;
    }

    /**
     * 是否可导：二级渠道 为其他分销
     * @param channelList
     * @return
     */
    public boolean isExport(List<HoardCodeGoodsChannelVo>  channelList, String hoardStatus){
        if (CollectionUtils.isEmpty(channelList)){
            return false;
        }

        //囤码商品状态
        if ("N".equals(hoardStatus)){
            return false;
        }

        //渠道设置判断：一级渠道，二级渠道 都要设置为分销渠道
        boolean firstChannelFlag = false;
        boolean secondChannelFlag = false;
        for(HoardCodeGoodsChannelVo channel : channelList){
            if("分销商".equals(channel.getChannelName()) && channel.getChannelType() == 1  && "4".equals(channel.getChannelValue())){
                firstChannelFlag = true;
            }

        }

        for(HoardCodeGoodsChannelVo channel : channelList){
            if("其他分销".equals(channel.getChannelName()) && channel.getChannelType() == 2  && "0-0".equals(channel.getChannelValue())){
                secondChannelFlag = true;
            }

        }

        return firstChannelFlag && secondChannelFlag;
    }

}
