package com.lvmama.tnt.reference.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lvmama.comm.pet.client.FSClient;
import com.lvmama.comm.pet.service.pub.ComFSService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

/**
 * 文件上传
 *
 * @author songjunbao
 * @createdate 2018/6/8
 */
@Service
public class FsClientRemote implements InitializingBean{
    private static Logger logger = LoggerFactory.getLogger(ChannelRefServiceImpl.class);

    private FSClient fsClient;

    @Reference
    private ComFSService comFSService;

    public FsClientRemote(){
        fsClient = new FSClient();
    }

    public Long uploadFile(String fileName, byte[] fileData, String serverType){
        try {
            return fsClient.uploadFile(fileName, fileData, serverType);
        } catch (Exception e) {
            logger.error("upload error:", e);
            e.printStackTrace();
        }
        return null;

    }


    @Override
    public void afterPropertiesSet() throws Exception {
        fsClient.setComFSRemoteService(comFSService);
    }
}
