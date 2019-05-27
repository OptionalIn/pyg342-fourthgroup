package cn.itcast.core.service;

import cn.itcast.core.pojo.entity.MyOrder;

import java.util.List;

public interface MyOrderService {
    List<MyOrder> search(String userName);
}
