package com.lvmama.tnt.reference.service;


/**
 *
 */
public interface IComlogService {

    /**
     *
     * @param content 记录做了什么操作
     * @param objectId batchNo 去掉前面两位
     */
    void addBatchLog(String content, Long objectId) ;
}
