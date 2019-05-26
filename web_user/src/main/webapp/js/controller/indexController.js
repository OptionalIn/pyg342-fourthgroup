//首页控制器
app.controller('indexController',function($scope,$controller,loginService,orderService){
	$scope.showName=function(){
			loginService.showName().success(
					function(response){
						$scope.loginName=response.loginName;
					}
			);
	}
    // 显示状态 1、未付款，2、已付款，3、未发货，4、已发货，5、交易成功，6、交易关闭,7、待评价
    $scope.status = ["未付款","已付款","未发货","已发货","交易成功","交易关闭","待评价"];
    //订单查询
    $scope.search=function(){

        orderService.search().success(
            function(response){
                $scope.list=response;
            }
        );
    }
});