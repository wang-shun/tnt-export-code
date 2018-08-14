package com.lvmama.tnt.common.domain;

import java.io.Serializable;

/**
 *
 */
public class TntMerchantDTO implements Serializable{

    private static final long serialVersionUID = 3055046723774145587L;

    private Long userId;
    private String userName;
    private String companyName;
    private String operator;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }
}
