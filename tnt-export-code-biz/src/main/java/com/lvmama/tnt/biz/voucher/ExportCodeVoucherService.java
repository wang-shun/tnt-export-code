package com.lvmama.tnt.biz.voucher;


/**
 * 导码凭证服务
 *
 * @author songjunbao
 * @createdate 2018/6/7
 */
public interface ExportCodeVoucherService {

    /**
     * 压缩并上传批次包含的PDF至文件服务器上
     * @param batchNo
     */
    Long uploadPdfZip(String batchNo);

}
