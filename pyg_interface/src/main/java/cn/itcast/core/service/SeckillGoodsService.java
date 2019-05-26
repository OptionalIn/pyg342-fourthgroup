package cn.itcast.core.service;


import cn.itcast.core.pojo.entity.PageResult;
import cn.itcast.core.pojo.entity.SeckillEntity;
import cn.itcast.core.pojo.seckill.SeckillGoods;

public interface SeckillGoodsService {

    /**
     * 高级查询秒杀商品
     * @param seckillGoods
     * @param page
     * @param rows
     * @return
     */
    PageResult search(SeckillGoods seckillGoods, Integer page, Integer rows);

    /**
     * 添加秒杀商品
     * @param seckillEntity
     */
    void add(SeckillEntity seckillEntity);

    /**
     * 提交审核
     * @param ids
     */
    void toCheck(Long[] ids);
}
