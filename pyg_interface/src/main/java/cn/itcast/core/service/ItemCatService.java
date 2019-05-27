package cn.itcast.core.service;

import cn.itcast.core.pojo.item.ItemCat;

import java.util.List;

public interface ItemCatService {

    public List<ItemCat> findByParentId(Long parentId);

    public ItemCat findOne(Long id);

    public List<ItemCat> findAll();

    public void add(ItemCat itemCat);

    public List<ItemCat> findRedisItemCat();

    public void saveToMysql(Integer[] ids);
}
