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
        // 进行分页查询
        PageResult pageResult = seckillGoodsService.findAllNoCheck(seckillGoods, page, rows);
        return pageResult;
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

    /**
     * 批量审核
     * @param ids
     * @return
     */
    @RequestMapping("/updateStatus")
    public Result updateStatus(Long[] ids,String status) {
        try {
            seckillGoodsService.updateStatus(ids,status);
            return new Result(true, "提交成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "提交失败!");
        }
    }


}
