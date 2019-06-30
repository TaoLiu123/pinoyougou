app.controller("brandController", function ($scope, brandService,$controller) {

    $controller("baseController",{$scope:$scope});//继承


    //查询全部数据
    $scope.findAll = function () {
        brandService.findAll().success(
            function (response) {

                $scope.list = response;
            }
        );
    }

    //定义分页方法
    $scope.findPage = function (page, rows) {
        brandService.findPage(page, rows).success(
            function (response) {
                $scope.list = response.rows;//显示当前页数据

                $scope.paginationConf.totalItems = response.total;//更新总记录数
            }
        );
    }
    //保存方法
    $scope.save = function () {
        //定定义变量，如果id值为空，表示是新增数据，用save方法，如果不为空，表示为修改数据，调用update方法
        var Object = brandService.save($scope.entity);
        if ($scope.entity.id != null) {
            Object = brandService.update($scope.entity);
        }
        Object.success(
            function (response) {

                if (response.success) {

                    $scope.reloadList();

                } else {
                    alert(response.message);
                }
            }
        );
    }

    // 删除方法

    $scope.dele = function () {

        if(confirm('确定要删除吗？')){
        brandService.dele($scope.selectids).success(
            function (responde) {
                if (responde.success) {

                    $scope.reloadList();

                } else (
                    alert(responde.message)
                );
            }
        );
    };
    }
    //根据id查询
    $scope.findOne = function (id) {
        brandService.findOne(id).success(
            function (response) {
                $scope.entity = response;
            }
        );
    }

    $scope.searchEntity = {};
    // 条件查询
    $scope.search = function (page, rows) {
        brandService.search(page, rows, $scope.searchEntity).success(
            function (response) {
                $scope.list = response.rows;//显示当前页数据
                $scope.paginationConf.totalItems = response.total;//更新总记录数
            }
        );

    }
    $scope.findBrandList=function () {
        brandService.selectOptionList().success(
            function (response) {
                $scope.entity=response;
            }
        )
    }

});
