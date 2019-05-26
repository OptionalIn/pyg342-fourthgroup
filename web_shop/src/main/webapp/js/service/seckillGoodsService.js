//服务层
app.service('seckillGoodsService',function($http){
	    	
	//读取列表数据绑定到表单中
	this.findAll=function(){
		return $http.get('../seckillGoods/findAll.do');
	}
	//分页 
	this.findPage=function(page,rows){
		return $http.get('../seckillGoods/findPage.do?page='+page+'&rows='+rows);
	}
	//查询实体
	this.findOne=function(id){
		return $http.get('../seckillGoods/findOne.do?id='+id);
	}
	//增加 
	this.add=function(entity){
		return  $http.post('../seckillGoods/add.do',entity );
	}
	//修改 
	this.update=function(entity){
		return  $http.post('../seckillGoods/update.do',entity );
	}
	//提交审核
    this.toCheck=function(ids){
        return $http.get('../seckillGoods/toCheck.do?ids='+ids);
    }
	//删除
	this.dele=function(ids){
		return $http.get('../seckillGoods/delete.do?ids='+ids);
	}
	//搜索
	this.search=function(page,rows,searchEntity){
		return $http.post('../seckillGoods/search.do?page='+page+"&rows="+rows, searchEntity);
	}

    //根据该卖家查询所有商品
    this.findGoodsBySellerId=function(){
        return $http.get('../goods/findGoodsBySellerId.do?');
    }

    //根据goodsId查询所有库存列表
    this.findByGoodsId=function(goodsId){
        return $http.get('../goods/findByGoodsId.do?goodsId='+goodsId);
    }
});
