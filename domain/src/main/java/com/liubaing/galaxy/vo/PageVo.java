package com.liubaing.galaxy.vo;

import java.util.List;

/**
 * @author shuaih
 * @version 16-3-26.
 */
public class PageVo<T> {

    public List<T> result;
    public long totalItems;

    public PageVo(List<T> result, long totalItems) {
        this.result = result;
        this.totalItems = totalItems;
    }

    public PageVo() {
    }

}