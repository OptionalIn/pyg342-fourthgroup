//服务层
app.service('seckillOrderService',function($http){
	    	
	//读取列表数据绑定到表单中
	this.findAll=function(){
		return $http.get('../seckillOrder/findAll.do');
	}
	//分页 
	this.findPage=function(page,rows){
		return $http.get('../seckillOrder/findPage.do?page='+page+'&rows='+rows);
	}

	//取消订单
    this.cancel=function(id){
        return $http.get('../seckillOrder/cancel.do?id='+id);
    }

});
