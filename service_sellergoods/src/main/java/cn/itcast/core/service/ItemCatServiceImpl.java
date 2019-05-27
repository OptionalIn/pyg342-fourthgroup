package cn.itcast.core.service;

import cn.itcast.core.dao.item.ItemCatDao;
import cn.itcast.core.pojo.item.ItemCat;
import cn.itcast.core.pojo.item.ItemCatQuery;
import cn.itcast.core.util.Constants;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class ItemCatServiceImpl implements ItemCatService {

    @Autowired
    private ItemCatDao catDao;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public List<ItemCat> findByParentId(Long parentId) {
        /**
         * 缓存分类数据到redis中
         */
        //1. 查询所有分类表数据
        List<ItemCat> catList = catDao.selectByExample(null);
        if (catList != null) {
            for (ItemCat itemCat : catList) {
                redisTemplate.boundHashOps(Constants.REDIS_CATEGORYLIST).put(itemCat.getName(), itemCat.getTypeId());
            }
        }

        /**
         * 2. 根据父级id进行查询
         */
        ItemCatQuery query = new ItemCatQuery();
        ItemCatQuery.Criteria criteria = query.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        List<ItemCat> list = catDao.selectByExample(query);
        return list;
    }

    @Override
    public ItemCat findOne(Long id) {
        return catDao.selectByPrimaryKey(id);
    }

    @Override
    public List<ItemCat> findAll() {
        return catDao.selectByExample(null);
    }

    /**
     * 分类申请  存入Redis中
     *
     * @param itemCat 所添加的分类对象
     */
    @Override
    public void add(ItemCat itemCat) {
        redisTemplate.boundHashOps(Constants.REDIS_WEISHEN_CATEGORYLIST).put(itemCat.getName(), itemCat.getTypeId());
    }

    /**
     * 从redis中获取分类申请对象
     *
     * @return
     */
    @Override
    public List<ItemCat> findRedisItemCat() {
        List<ItemCat> itemCatList = new ArrayList<>();
        Set keys = redisTemplate.boundHashOps(Constants.REDIS_WEISHEN_CATEGORYLIST).keys();
        for (Object key : keys) {
            Long typeId = (Long) redisTemplate.boundHashOps(Constants.REDIS_WEISHEN_CATEGORYLIST).get(key);
            ItemCat itemCat = new ItemCat();
            itemCat.setName(String.valueOf(key));
            itemCat.setTypeId(typeId);
            itemCatList.add(itemCat);
        }
        return itemCatList;
    }

    /**
     * 分类审核 把分类数据存入mysql数据库中
     *
     * @param itemCat
     */
    @Override
    public void saveToMysql(Integer[] ids) {
        List<ItemCat> itemCatList = new ArrayList<>();
        Set keys = redisTemplate.boundHashOps(Constants.REDIS_WEISHEN_CATEGORYLIST).keys();
        for (Object key : keys) {
            Long typeId = (Long) redisTemplate.boundHashOps(Constants.REDIS_WEISHEN_CATEGORYLIST).get(key);
            ItemCat itemCat1 = new ItemCat();
            itemCat1.setName(String.valueOf(key));
            itemCat1.setTypeId(typeId);
            itemCat1.setParentId(0L);
            itemCatList.add(itemCat1);
        }
        if (ids != null) {
            for (Integer id : ids) {

                int i = catDao.insertSelective(itemCatList.get(id));

                if (i == 1) {
                    redisTemplate.boundHashOps(Constants.REDIS_WEISHEN_CATEGORYLIST).delete(itemCatList.get(id).getName());

                }
            }

        }

    }
}