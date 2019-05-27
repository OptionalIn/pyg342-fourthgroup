//控制层
app.controller('seckillOrderController', function ($scope, $controller, $location, seckillOrderService) {

    $controller('baseController', {$scope: $scope});//继承

    //读取列表数据绑定到表单中  
    $scope.findAll = function () {
        seckillOrderService.findAll().success(
            function (response) {
                $scope.list = response;
            }
        );
    }


    $scope.searchEntity = {};//定义搜索对象
    //搜索
    $scope.search = function (page, rows) {
        seckillOrderService.search(page, rows, $scope.searchEntity).success(
            function (response) {
                $scope.list = response.rows;
                $scope.paginationConf.totalItems = response.total;//更新总记录数
            }
        );
    }

    // 查询对应状态的订单
    $scope.$watch("searchEntity.status",function(newValue,oldValue){
        $scope.searchEntity.status=newValue;
        $scope.reloadList();
    });



    // 显示状态
    $scope.status = ["未付款", "已付款", "已发货","已完成", "已取消"];


});	
