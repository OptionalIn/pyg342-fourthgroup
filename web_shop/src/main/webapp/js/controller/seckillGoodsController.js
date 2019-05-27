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

    //获取该商家的所有商品列表
    $scope.selectGoodsList=function () {
        seckillGoodsService.findGoodsBySellerId().success(
            function (response) {
                $scope.goodsList = response;
            }
        )
    }

    // 查询对应商品下的库存列表:
    $scope.$watch("entity.goods.id",function(newValue,oldValue){
        seckillGoodsService.findByGoodsId(newValue).success(function(response){
            $scope.itemList = response;
        });
    });


    //保存
    $scope.save=function(){
        var serviceObject;//服务层对象
        if($scope.entity.seckillGoods.id!=null){//如果有ID
            serviceObject=seckillGoodsService.update( $scope.entity ); //修改
        }else{
            serviceObject=seckillGoodsService.add( $scope.entity  );//增加
        }
        serviceObject.success(
            function(response){
                if(response.success){
                    //重新查询
                    alert(response.message);
                    location.href="seckill_goods.html";
                }else{
                    alert(response.message);
                }
            }
        );
    }

    //批量提交审核
    $scope.toCheck= function () {
        //获取选中的复选框
        seckillGoodsService.toCheck($scope.selectIds).success(
            function (response) {
                if (response.success) {
                    $scope.reloadList();//刷新列表
                    $scope.selectIds = [];
                }
            }
        );
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
