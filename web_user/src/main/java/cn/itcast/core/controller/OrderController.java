package cn.itcast.core.controller;

import cn.itcast.core.pojo.entity.MyOrder;
import cn.itcast.core.pojo.entity.PageResult;
import cn.itcast.core.service.MyOrderService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Reference
    private MyOrderService myOrderService;

    @RequestMapping("/search")
    public List<MyOrder> search(){
        //1. 获取当前登录用户的用户名
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        return myOrderService.search(userName);
    }
}
