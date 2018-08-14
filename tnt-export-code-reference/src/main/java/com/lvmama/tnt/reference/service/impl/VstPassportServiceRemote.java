package com.lvmama.tnt.reference.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lvmama.vst.api.passport.service.VstPassportService;
import com.lvmama.vst.api.vo.ComFileVo;
import com.lvmama.vst.api.vo.ResultHandleT;
import org.springframework.stereotype.Service;

/**
 * vst码服务
 * @author songjunbao
 * @createdate 2018/6/7
 */
@Service
public class VstPassportServiceRemote {

    @Reference
    private VstPassportService vstPassportService;


    public ResultHandleT<ComFileVo> downloadPDFFile(Long fileId){
        return vstPassportService.downloadPDFFile(fileId);
    }


}
