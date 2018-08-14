package com.lvmama.tnt.export.code.api.domain.vo;

import java.io.Serializable;

/**
 *
 */
public class ExportCodeInfoVO implements Serializable {

    private static final long serialVersionUID = 5553278285716536903L;
    private String fileName;
    private byte[] body;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }
}
