package com.lvmama.tnt.export.code.api.constant;

/**
 * 人群类型
 *
 * @author songjunbao
 * @createdate 2018/3/13
 */
public enum  PeopleType {

    PEOPLE_TYPE_ADULT("成人"),
    PEOPLE_TYPE_CHILD("儿童"),
    PEOPLE_TYPE_OLDER("老人");

    private final String cnName;

    PeopleType(String name){
        this.cnName=name;
    }

    public String getCode(){
        return this.name();
    }

    public String getCnName(){
        return this.cnName;
    }

    public static String getCnName(String code){
        for(PeopleType item : PeopleType.values()){
            if(item.getCode().equals(code))
            {
                return item.getCnName();
            }
        }
        return code;
    }

    @Override
    public String toString(){
        return this.name();
    }
}
