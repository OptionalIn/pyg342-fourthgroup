//控制层
app.controller('itemCatController' ,function($scope,$controller   ,itemCatService){

    $controller('baseController',{$scope:$scope});//继承

    //记录当前上级分类ID，默认为0
    $scope.parentId = 0;

    //商品分类对应模板数据集
    $scope.templateList={data:[{id:35,text:'手机'},{id:37,text:'电视'},{id:38,text:'电脑'},{id:39,text:'空调'}]};

    //读取列表数据绑定到表单中
    $scope.findAll=function(){
        itemCatService.findAll().success(
            function(response){
                $scope.list=response;
            }
        );
    }

    //分页
    $scope.findPage=function(page,rows){
        itemCatService.findPage(page,rows).success(
            function(response){
                $scope.list=response.rows;
                $scope.paginationConf.totalItems=response.total;//更新总记录数
            }
        );
    }

    //查询实体
    $scope.findOne=function(id){
        itemCatService.findOne(id).success(
            function(response){
                $scope.entity= response;
            }
        );
    }

    //保存
    $scope.save=function(){
            //设置其上级ID
            $scope.entity.parentId = $scope.parentId;

            itemCatService.add( $scope.entity).success(
                function(response){
                    if(response.success){
                        alert(response.message);
                        //重新查询
                        $scope.reloadList();//重新加载
                    }else{
                        alert(response.message);
                    }
                }
             );
    }


    //批量删除
    $scope.dele=function(){
        //获取选中的复选框
        itemCatService.dele( $scope.selectIds ).success(
            function(response){
                if(response.success){
                    $scope.reloadList();//刷新列表
                    $scope.selectIds = [];
                }
            }
        );
    }

    $scope.searchEntity={};//定义搜索对象

    //搜索
    $scope.search=function(page,rows){
        itemCatService.search(page,rows,$scope.searchEntity).success(
            function(response){
                $scope.list=response.rows;
                $scope.paginationConf.totalItems=response.total;//更新总记录数
            }
        );
    }

    // 根据父ID查询分类
    $scope.findByParentId =function(parentId){
        itemCatService.findByParentId(parentId).success(function(response){
            $scope.list=response;
            //设置当前分类的上级ID
            $scope.parentId = parentId;
        });
    }

    // 定义一个变量记录当前是第几级分类
    $scope.grade = 1;

    $scope.setGrade = function(value){
        $scope.grade = value;
    }

    $scope.selectList = function(p_entity){

        if($scope.grade == 1){
            $scope.entity_1 = null;
            $scope.entity_2 = null;
        }
        if($scope.grade == 2){
            $scope.entity_1 = p_entity;
            $scope.entity_2 = null;
        }
        if($scope.grade == 3){
            $scope.entity_2 = p_entity;
        }

        $scope.findByParentId(p_entity.id);
    }











});	
