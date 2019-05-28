package cn.itcast.core.pojo.entity;

import java.io.Serializable;
import java.util.List;

public class PageBean<T> implements Serializable{
    //当前页 --- 页面传参
    private Integer pageNum;
    //每页条数 --- 页面传参
    private Integer pageSize;
    //总条数 ---  数据库查询 count
    private Integer totalCount;
    //总页数 -- 计算: Math.ceil(totalCount * 1.0 / pageSize)
    private Integer totalPage;
    //当前页数据 -- 从数据查询
    private List<T> list;

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
