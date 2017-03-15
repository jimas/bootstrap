package com.bootstrap.jimas.common;

import java.io.Serializable;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
/**
 * @Description 分页查询类
 * @author weqinjia.liu
 * @Date 2016年12月25日
 */
public class SpringDataPageable<T> implements Pageable,Serializable {

    private static final long serialVersionUID = 6275528053337688997L;

    //当前页
    private Integer pagenamber=1;
    //每页多少条数
    private Integer pagesize=10;
    //排序
    private Sort sort;
    
    private T param;
    
    public Integer getPagenamber() {
        return pagenamber;
    }

    public void setPagenamber(Integer pagenamber) {
        this.pagenamber = pagenamber;
    }

    public Integer getPagesize() {
        return pagesize;
    }

    public void setPagesize(Integer pagesize) {
        this.pagesize = pagesize;
    }

    public void setSort(Sort sort) {
        this.sort = sort;
    }

    @Override
    public Pageable first() {
        return null;
    }

    //第二页所需要增加的条数
    @Override
    public int getOffset() {
        return (getPagenamber()-1)*getPagesize();
    }

    @Override
    public int getPageNumber() {
        return getPagenamber();
    }

    @Override
    public int getPageSize() {
        return getPagesize();
    }

    @Override
    public Sort getSort() {
        return sort;
    }

    @Override
    public boolean hasPrevious() {
        return false;
    }

    @Override
    public Pageable next() {
        return null;
    }

    @Override
    public Pageable previousOrFirst() {
        return null;
    }

    public T getParam() {
        return param;
    }

    public void setParam(T param) {
        this.param = param;
    }
    
    

}
