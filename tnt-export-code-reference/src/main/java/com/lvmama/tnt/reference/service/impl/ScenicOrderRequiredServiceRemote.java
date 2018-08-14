package com.lvmama.tnt.reference.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lvmama.scenic.api.order.service.ScenicOrderRequiredService;
import com.lvmama.scenic.api.order.vo.ScenicOrdRequiredVO;
import com.lvmama.scenic.api.vo.ResultHandleT;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * 商品下单必填项
 *
 * @author songjunbao
 * @createdate 2018/4/12
 */
@Service
public class ScenicOrderRequiredServiceRemote {
    @Reference
    private ScenicOrderRequiredService scenicOrderRequiredService;

    public ResultHandleT<ScenicOrdRequiredVO>  findOrderRequiredListId(Long goodsId){
        return scenicOrderRequiredService.findOrderRequiredListId(null, Arrays.asList(goodsId));
    }

}
