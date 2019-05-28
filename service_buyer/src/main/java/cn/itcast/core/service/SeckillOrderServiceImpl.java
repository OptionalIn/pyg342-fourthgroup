package cn.itcast.core.service;

import cn.itcast.core.dao.seckill.SeckillOrderDao;
import cn.itcast.core.pojo.entity.PageBean;
import cn.itcast.core.pojo.entity.PageResult;
import cn.itcast.core.pojo.seckill.SeckillOrder;
import cn.itcast.core.pojo.seckill.SeckillOrderQuery;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

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
            if (!"".equals(seckillOrder.getStatus()) && seckillOrder.getStatus() != null) {
                criteria.andStatusEqualTo(seckillOrder.getStatus());
            }
        }

        Page<SeckillOrder> seckillOrderList = (Page<SeckillOrder>) seckillOrderDao.selectByExample(query);
        return new PageResult(seckillOrderList.getTotal(), seckillOrderList.getResult());
    }

    /**
     * 前端页面手动分页查询
     *
     * @param
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public PageBean<SeckillOrder> findPage(String userId, Integer pageNum, Integer pageSize) {
        //创建PageBean对象
        PageBean<SeckillOrder> pageBean = new PageBean<>();
        //  封装数据
        //当前页 --- 页面传参  private Integer pageNum;
        pageBean.setPageNum(pageNum);
        //每页条数 --- 页面传参 private Integer pageSize;
        pageBean.setPageSize(pageSize);
        //总条数 ---  数据库查询 count  private Integer totalCount;
        Integer totalCount = seckillOrderDao.findTotalCount(userId);
        pageBean.setTotalCount(totalCount);
        //总页数 -- 计算: Math.ceil(totalCount * 1.0 / pageSize) private Integer totalPage;
        pageBean.setTotalPage((int) Math.ceil(totalCount * 1.0 / pageSize));
        //当前页数据 -- 从数据查询 private List<T> list;
        //分页查询数据
        Integer startIndex = (pageNum - 1) * pageSize;
        List<SeckillOrder> seckillOrderList = seckillOrderDao.findByPage(userId, startIndex, pageSize);
        pageBean.setList(seckillOrderList);
        //返回PageBean
        return pageBean;
    }

    /**
     * 取消订单
     *
     * @param id
     */
    @Override
    public void cancel(Long id) {

        if (id != null) {
            SeckillOrder seckillOrder = new SeckillOrder();
            seckillOrder.setStatus("4");
            seckillOrder.setId(id);
            seckillOrderDao.updateByPrimaryKeySelective(seckillOrder);
        }
    }
}
