package cn.itcast.core.controller;

import cn.itcast.core.pojo.entity.Result;
import cn.itcast.core.pojo.item.ItemCat;
import cn.itcast.core.service.ItemCatService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 分类管理
 */
@RestController
@RequestMapping("/itemCat")
public class ItemCatController {

    @Reference
    private ItemCatService catService;

    /**
     * 根据父级id查询所有子集
     * @param parentId
     * @return
     */
    @RequestMapping("/findByParentId")
    public List<ItemCat> findByParentId(Long parentId) {
        List<ItemCat> list = catService.findByParentId(parentId);
        return list;
    }

    @RequestMapping("/findRedisItemCat")
    public List<ItemCat> findRedisItemCat() {
        List<ItemCat> all = catService.findRedisItemCat();
        return all;
    }

    @RequestMapping("/saveToMysql")
    public Result saveToMysql(Integer[] ids){
        try {
            catService.saveToMysql(ids);
            return new Result(true,"审核成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"审核失败");

        }
    }
}
