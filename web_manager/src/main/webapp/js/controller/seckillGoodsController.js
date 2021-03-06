//控制层
app.controller('seckillGoodsController', function ($scope, $controller, $location, seckillGoodsService) {

    $controller('baseController', {$scope: $scope});//继承

    //读取列表数据绑定到表单中  
    $scope.findAll = function () {
        seckillGoodsService.findAll().success(
            function (response) {
                $scope.list = response;
            }
        );
    }


    $scope.searchEntity = {};//定义搜索对象
    //搜索
    $scope.search = function (page, rows) {
        seckillGoodsService.search(page, rows, $scope.searchEntity).success(
            function (response) {
                $scope.list = response.rows;
                $scope.paginationConf.totalItems = response.total;//更新总记录数
            }
        );
    }

    // 审核的方法:
    $scope.updateStatus = function(status){
        seckillGoodsService.updateStatus($scope.selectIds,status).success(function(response){
            if(response.success){
                $scope.reloadList();//刷新列表
                $scope.selectIds = [];
            }else{
                alert(response.message);
            }
        });
    }

    //批量删除
    $scope.dele = function () {
        //获取选中的复选框
        seckillGoodsService.dele($scope.selectIds).success(
            function (response) {
                if (response.success) {
                    $scope.reloadList();//刷新列表
                    $scope.selectIds = [];
                }
            }
        );
    }

    // 显示状态
    $scope.status = ["未审核", "审核通过", "审核未通过","未提交", "关闭"];


});	
