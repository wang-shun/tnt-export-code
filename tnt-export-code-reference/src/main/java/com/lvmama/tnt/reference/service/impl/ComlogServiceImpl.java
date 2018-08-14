package com.lvmama.tnt.reference.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lvmama.comm.pet.po.pub.ComLog;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.tnt.reference.service.IComlogService;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 *
 */
@Service
public class ComlogServiceImpl implements IComlogService {

    @Reference
    private ComLogService comLogService;


    @Override
    public void addBatchLog(String content, Long objectId) {
        ComLog comlog = new ComLog();
        comlog.setContent(content);
        comlog.setContentType(com.lvmama.comm.log.Constant.COM_LOG_CONTENT_TYPE.VARCHAR.name());
        comlog.setCreateTime(new Date());
        comlog.setLogName("");
        comlog.setLogType("导码批次管理");
        comlog.setObjectId(objectId);
        comlog.setObjectType("EXPORT_BACTH_TYPE");
        comlog.setOperatorName("jobTask");
        comLogService.addComLog(comlog);
    }
}
