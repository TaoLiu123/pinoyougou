app.controller("baseController", function ($scope) {

//分页插件的属性展示
    $scope.paginationConf = {
        currentPage: 1,//当前页
        totalItems: 10,//总记录数
        itemsPerPage: 10,//每页显示记录数
        perPageOptions: [10, 20, 30, 40, 50],//页面显示条数控制
        onChange: function () {//选项控制
            $scope.reloadList();//重新加载
        }
    };
//封装刷新页面方法
    $scope.reloadList = function () {
        $scope.search($scope.paginationConf.currentPage, $scope.paginationConf.itemsPerPage);
    }

    $scope.selectIds = [];//用户勾选的id集合
//用户勾选复选框
    $scope.updateSelection = function ($event, id) {

        if ($event.target.checked) {
            $scope.selectIds.push(id);//向集合中添加选中的id
        } else {
            var indext = $scope.selectIds.indexOf(id);//查找id的位置
            $scope.selectIds.splice(indext, 1);//第一个参数表表示要一处的位置，第二个参数表示移掉几个

        }
    }

    $scope.jsonToString = function (jsonString, key) {

        var json = JSON.parse(jsonString);//将json字符串转换为json对象

        var value = "";
        for (var i = 0; i < json.length; i++) {
            if (i > 0) {
                value += ","
            }
            value+=  json[i][key];

        }
        return value;
    }
});