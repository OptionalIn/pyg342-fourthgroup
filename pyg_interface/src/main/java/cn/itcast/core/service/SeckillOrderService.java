package cn.itcast.core.service;

import cn.itcast.core.pojo.entity.PageBean;
import cn.itcast.core.pojo.entity.PageResult;
import cn.itcast.core.pojo.seckill.SeckillOrder;

public interface SeckillOrderService {
    PageResult search(SeckillOrder seckillOrder, Integer page, Integer rows);

    //手动分页查询
    PageBean<SeckillOrder> findPage(String userId, Integer pageNum, Integer pageSize);

    //取消订单
    void cancel(Long id);
}
