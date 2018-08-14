package com.lvmama.tnt.biz.converter;

/**
 * dto和po转换器
 *
 * @author songjunbao
 * @createdate 2018/1/30
 */
public interface Converter<S,T> {

    public T convert(S source);
}
