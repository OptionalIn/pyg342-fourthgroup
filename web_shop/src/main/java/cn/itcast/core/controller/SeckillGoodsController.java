package cn.itcast.core.controller;

import cn.itcast.core.pojo.entity.PageResult;
import cn.itcast.core.pojo.entity.Result;
import cn.itcast.core.pojo.entity.SeckillEntity;
import cn.itcast.core.pojo.seckill.SeckillGoods;
import cn.itcast.core.service.SeckillGoodsService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 商品管理
 */
@RestController
@RequestMapping("/seckillGoods")
public class SeckillGoodsController {

    @Reference
    private SeckillGoodsService seckillGoodsService;

    /**
     * 商品分页查询
     */
    @RequestMapping("/search")
    public PageResult search(@RequestBody SeckillGoods seckillGoods, Integer page, Integer rows) {
        //1. 获取当前登录用户的用户名
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        //2. 向查询条件对象中添加当前登录用户的用户名作为查询条件
        seckillGoods.setSellerId(userName);
        //3. 进行分页查询
        PageResult pageResult = seckillGoodsService.search(seckillGoods, page, rows);
        return pageResult;
    }

    /**
     * 商品添加
     *
     * @param seckillEntity 商品实体, 包含商品对象, 商品详情对象, 库存集合对象
     * @return
     */
    @RequestMapping("/add")
    public Result add(@RequestBody SeckillEntity seckillEntity) {
        try {
            SeckillGoods seckillGoods = seckillEntity.getSeckillGoods();
            //1. 获取当前登录用户的用户名
            String userName = SecurityContextHolder.getContext().getAuthentication().getName();
            //2. 将用户身份信息放入商品对象中
            seckillEntity.getSeckillGoods().setSellerId(userName);
            //3. 保存
            seckillGoodsService.add(seckillEntity);
            return new Result(true, "添加成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "添加失败!");
        }
    }

    /**
     * 批量提交审核
     * @param ids
     * @return
     */
    @RequestMapping("/toCheck")
    public Result toCheck(Long[] ids) {
        try {
            seckillGoodsService.toCheck(ids);
            return new Result(true, "提交成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "提交失败!");
        }
    }

}
