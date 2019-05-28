//控制层
app.controller('seckillOrderController', function ($scope, $controller, $location, seckillOrderService) {

    //读取列表数据绑定到表单中  
    $scope.findAll = function () {
        seckillOrderService.findAll().success(
            function (response) {
                $scope.list = response;
            }
        );
    }

    // $scope.pageSearchEntity={};

    //搜索
    $scope.findAllSeckillOrder = function (page, rows) {
        seckillOrderService.findPage(page, rows).success(
            function (response) {
                $scope.pagebean = response;
            }
        );
    }


    //取消订单
    $scope.cancel=function (id) {
        seckillOrderService.cancel(id).success(
            function (response) {
                if(response.success){
                    alert(response.message);
                    //重新查询
                    location.href="home-seckill-order.html";
                }else{
                    alert(response.message);
                }
            }
        );
    }


    // 显示状态
    $scope.status = ["未付款", "已付款", "已发货","已完成", "已取消"];
    $scope.operationStatus = ["", "退款", "退货/退款","申请售后", "已关闭"];
    $scope.orderStatus = ["立即付款", "提醒发货", "确认收获","评价", "已取消"];


});	
