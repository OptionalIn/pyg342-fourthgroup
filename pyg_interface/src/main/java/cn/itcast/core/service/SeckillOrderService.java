package cn.itcast.core.service;

import cn.itcast.core.pojo.entity.PageResult;
import cn.itcast.core.pojo.seckill.SeckillOrder;

public interface SeckillOrderService {
    PageResult search(SeckillOrder seckillOrder, Integer page, Integer rows);
}
