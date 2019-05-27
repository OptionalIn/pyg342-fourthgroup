package cn.itcast.core.service;

import cn.itcast.core.dao.item.ItemDao;
import cn.itcast.core.dao.seckill.SeckillGoodsDao;
import cn.itcast.core.pojo.entity.GoodsEntity;
import cn.itcast.core.pojo.entity.PageResult;
import cn.itcast.core.pojo.entity.SeckillEntity;
import cn.itcast.core.pojo.item.Item;
import cn.itcast.core.pojo.seckill.SeckillGoods;
import cn.itcast.core.pojo.seckill.SeckillGoodsQuery;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional
public class SeckillGoodsServiceImpl implements SeckillGoodsService {


    @Autowired
    private SeckillGoodsDao seckillGoodsDao;

    @Reference
    private GoodsService goodsService;

    @Autowired
    private ItemDao itemDao;


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

    /**
     * 添加秒杀商品
     *
     * @param seckillEntity
     */
    @Override
    public void add(SeckillEntity seckillEntity) {
        //封装数据
        SeckillGoods seckillGoods = getSeckillGoods(seckillEntity);

        seckillGoodsDao.insertSelective(seckillGoods);
    }

    /**
     * 批量提交审核
     *
     * @param ids
     */
    @Override
    public void toCheck(Long[] ids) {
        if (ids != null) {
            for (Long id : ids) {
                SeckillGoodsQuery query = new SeckillGoodsQuery();
                SeckillGoodsQuery.Criteria criteria = query.createCriteria();
                criteria.andIdEqualTo(id);

                SeckillGoods seckillGoods = new SeckillGoods();
                seckillGoods.setStatus("0");
                seckillGoodsDao.updateByExampleSelective(seckillGoods, query);
            }
        }
    }

    /**
     * 批量审核秒杀商品申请
     * @param ids
     * @param status
     */
    @Override
    public void updateStatus(Long[] ids, String status) {
        if (ids != null) {
            for (Long id : ids) {
                SeckillGoodsQuery query = new SeckillGoodsQuery();
                SeckillGoodsQuery.Criteria criteria = query.createCriteria();
                criteria.andIdEqualTo(id);

                SeckillGoods seckillGoods = new SeckillGoods();
                seckillGoods.setStatus(status);
                seckillGoodsDao.updateByExampleSelective(seckillGoods, query);
            }
        }
    }


    /**
     * 查询所有未审核的秒杀商品申请
     *
     * @param seckillGoods
     * @param page
     * @param rows
     * @return
     */
    @Override
    public PageResult findAllNoCheck(SeckillGoods seckillGoods, Integer page, Integer rows) {
        PageHelper.startPage(page, rows);
        //创建查询条件对象
        SeckillGoodsQuery query = new SeckillGoodsQuery();
        //创建where条件对象
        SeckillGoodsQuery.Criteria criteria = query.createCriteria();
        if (seckillGoods != null) {
            //根据账户名称精确查询自己添加的商品, 如果是管理员账户则查询所有商品
            criteria.andStatusEqualTo("0");
            Page<SeckillGoods> seckillGoodsList = (Page<SeckillGoods>) seckillGoodsDao.selectByExample(query);
            return new PageResult(seckillGoodsList.getTotal(), seckillGoodsList.getResult());
        }
        return null;
    }


    /**
     * 抽取封装数据的方法
     *
     * @param seckillEntity
     * @return
     */
    private SeckillGoods getSeckillGoods(SeckillEntity seckillEntity) {
        if (seckillEntity != null) {
            SeckillGoods seckillGoods = seckillEntity.getSeckillGoods();
            //获取goods对象
            Long goodsId = seckillEntity.getGoods().getId();
            GoodsEntity goodsEntity = goodsService.findOne(goodsId);
            //获取item对象
            Long itemId = seckillEntity.getItem().getId();
            Item item = itemDao.selectByPrimaryKey(itemId);

            //封装数据
            seckillGoods.setGoodsId(goodsEntity.getGoods().getId());
            seckillGoods.setItemId(itemId);
            seckillGoods.setPrice(item.getPrice());
            seckillGoods.setCreateTime(new Date());
            seckillGoods.setStockCount(seckillEntity.getSeckillGoods().getNum());
            //保存时初是状态设置为2代表保存，但未提交，提交审核将审核状态改为0，[0 "未审核", 1 "审核通过", 2 "审核未通过", 3 "未提交", 4 "关闭"];
            seckillGoods.setStatus("3");
            //商品图片的获取
            String image = item.getImage();
            seckillGoods.setSmallPic(image);
            //审核通过需要更改审核状态和审核时间
            return seckillGoods;
        }
        return null;
    }
}
