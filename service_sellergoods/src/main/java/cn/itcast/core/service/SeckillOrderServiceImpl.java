package cn.itcast.core.service;

import cn.itcast.core.dao.seckill.SeckillOrderDao;
import cn.itcast.core.pojo.entity.PageResult;
import cn.itcast.core.pojo.seckill.SeckillOrder;
import cn.itcast.core.pojo.seckill.SeckillOrderQuery;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class SeckillOrderServiceImpl implements SeckillOrderService {

    @Autowired
    private SeckillOrderDao seckillOrderDao;

    @Override
    public PageResult search(SeckillOrder seckillOrder, Integer page, Integer rows) {
        PageHelper.startPage(page, rows);
        //创建查询条件对象
        SeckillOrderQuery query = new SeckillOrderQuery();
        //创建where条件对象
        SeckillOrderQuery.Criteria criteria = query.createCriteria();
        if (seckillOrder != null) {
            //根据账户名称精确查询自己添加的商品, 如果是管理员账户则查询所有商品
            if (seckillOrder.getSellerId() != null && !"".equals(seckillOrder.getSellerId())
                    && !"admin".equals(seckillOrder.getSellerId()) && !"wc".equals(seckillOrder.getSellerId())) {
                criteria.andSellerIdEqualTo(seckillOrder.getSellerId());
            }
            //根据状态查询
            if (!"".equals(seckillOrder.getStatus()) && seckillOrder.getStatus() !=null ){
                criteria.andStatusEqualTo(seckillOrder.getStatus());
            }
        }

        Page<SeckillOrder> seckillOrderList = (Page<SeckillOrder>) seckillOrderDao.selectByExample(query);
        return new PageResult(seckillOrderList.getTotal(), seckillOrderList.getResult());
    }
}
