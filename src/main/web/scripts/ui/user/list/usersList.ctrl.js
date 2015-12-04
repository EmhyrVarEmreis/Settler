'use strict';

angular.module('settlerApplication').controller('usersListCtrl', function ($scope, ngTableParams, userListFactory) {

    $scope.showUserFilterView = false;

    $scope.userFilter = {};

    $scope.columns = {
        "login":     {"name": 'Login', "isVisible": true},
        "firstName": {"name": 'Imię', "isVisible": true},
        "lastName":  {"name": 'Nazwisko', "isVisible": true},
        "email":     {"name": 'Adres e-mail', "isVisible": true},
        "created":   {"name": 'Utworzony', "isVisible": true}
    };

    $scope.tableParams = new ngTableParams({
        page:    1,
        count:   25,
        sorting: {
            lname: 'asc'
        }
    }, {
        counts:  [],
        total:   0,
        getData: function ($defer, params) {
            userListFactory.get(
                {
                    "page":    params.page(),
                    "limit":   params.count(),
                    "sortBy":  params.orderBy(),
                    "filters": $scope.userFilter
                }, function (data) {
                    params.total(data.total);
                    $defer.resolve(data.content);
                });
        }
    });

    $scope.toggleUserFilterView = function () {
        if (!$scope.showUserFilterView) {
            $scope.showUserFilterView = true;
        } else {
            $scope.showUserFilterView = !$scope.showUserFilterView;
        }
    };

    $scope.applyFilters = function () {
        $scope.tableParams.page(1);
        $scope.tableParams.reload();
    };

    $scope.clearFilters = function () {
        $scope.userFilter = {};
        $scope.applyFilters();
    };

});