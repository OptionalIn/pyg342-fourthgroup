package cn.itcast.core.controller;

import cn.itcast.core.pojo.entity.PageResult;
import cn.itcast.core.pojo.entity.Result;
import cn.itcast.core.pojo.entity.SeckillEntity;
import cn.itcast.core.pojo.seckill.SeckillGoods;
import cn.itcast.core.pojo.seckill.SeckillOrder;
import cn.itcast.core.service.SeckillGoodsService;
import cn.itcast.core.service.SeckillOrderService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 秒杀订单
 */
@RestController
@RequestMapping("/seckillOrder")
public class SeckillOrderController {

    @Reference
    private SeckillOrderService seckillOrderService;

    /**
     * 商品分页查询
     */
    @RequestMapping("/search")
    public PageResult search(@RequestBody SeckillOrder seckillOrder, Integer page, Integer rows) {
        //1. 获取当前登录用户的用户名
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        //2. 向查询条件对象中添加当前登录用户的用户名作为查询条件
        seckillOrder.setSellerId(userName);
        //3. 进行分页查询
        PageResult pageResult = seckillOrderService.search(seckillOrder, page, rows);
        return pageResult;
    }

}
