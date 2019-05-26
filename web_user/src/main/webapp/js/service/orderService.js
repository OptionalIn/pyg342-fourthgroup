//服务层
app.service('orderService',function($http){

    //搜索
    this.search=function(){
        return $http.post('../order/search.do');
    }

});
