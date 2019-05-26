package cn.itcast.core.service;

import cn.itcast.core.dao.seckill.SeckillGoodsDao;
import cn.itcast.core.pojo.entity.PageResult;
import cn.itcast.core.pojo.seckill.SeckillGoods;
import cn.itcast.core.pojo.seckill.SeckillGoodsQuery;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class SeckillGoodsServiceImpl implements SeckillGoodsService {


    @Autowired
    private SeckillGoodsDao seckillGoodsDao;


    @Override
    public PageResult search(SeckillGoods seckillGoods, Integer page, Integer rows) {
        PageHelper.startPage(page, rows);
        //创建查询条件对象
        SeckillGoodsQuery query = new SeckillGoodsQuery();
        //创建where条件对象
        SeckillGoodsQuery.Criteria criteria = query.createCriteria();
        if (seckillGoods != null) {
            //根据账户名称精确查询自己添加的商品, 如果是管理员账户则查询所有商品
            if (seckillGoods.getSellerId() != null && !"".equals(seckillGoods.getSellerId())
                    && !"admin".equals(seckillGoods.getSellerId()) && !"wc".equals(seckillGoods.getSellerId())) {
                criteria.andSellerIdEqualTo(seckillGoods.getSellerId());
            }
        }

        Page<SeckillGoods> seckillGoodsList = (Page<SeckillGoods>) seckillGoodsDao.selectByExample(query);
        return new PageResult(seckillGoodsList.getTotal(), seckillGoodsList.getResult());
    }

}
