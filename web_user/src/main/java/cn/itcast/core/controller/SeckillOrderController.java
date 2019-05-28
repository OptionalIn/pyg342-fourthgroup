package cn.itcast.core.controller;

import cn.itcast.core.pojo.entity.PageBean;
import cn.itcast.core.pojo.entity.Result;
import cn.itcast.core.pojo.seckill.SeckillOrder;
import cn.itcast.core.service.SeckillOrderService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/seckillOrder")
public class SeckillOrderController {

    @Reference
    private SeckillOrderService seckillOrderService;

    /**
     * 分页查询
     * @param
     * @param
     * @return
     */
    @RequestMapping("/findPage")
    public PageBean<SeckillOrder> findPage(Integer page, Integer rows){
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        if(page == null){
            page=1;
        }
        if(rows == null){
            rows=3;
        }
        PageBean<SeckillOrder> pageBean = seckillOrderService.findPage(userName, page, rows);

        return pageBean;

    }


    /**
     * 取消订单
     * @param id
     * @return
     */
    @RequestMapping("/cancel")
    public Result cancel(Long id){
        try {
            seckillOrderService.cancel(id);
            return new Result(true,"取消成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true,"取消失败");
        }
    }
}
