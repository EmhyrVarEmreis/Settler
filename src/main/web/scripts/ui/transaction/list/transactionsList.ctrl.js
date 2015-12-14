'use strict';

angular.module('settlerApplication').controller('transactionsListCtrl', function ($scope, ngTableParams, transactionListFactory) {

    $scope.showTransactionFilterView = false;

    $scope.transactionFilter = {};

    $scope.columns = {
        "reference":  {"name": 'Numer transakcji', "isVisible": true},
        "type":       {"name": 'Typ transakcji', "isVisible": true},
        "owner":      {"name": 'Właściciel', "isVisible": true},
        "contractor": {"name": 'Kontraktor', "isVisible": true},
        "value":      {"name": 'Wartość', "isVisible": true},
        "created":    {"name": 'Utworzono', "isVisible": true},
        "confirmed":  {"name": 'Potwierdzono', "isVisible": true},
        "evaluated":  {"name": 'Ewaluowano', "isVisible": true}
    };

    $scope.tableParams = new ngTableParams({
        page:    1,
        count:   25,
        sorting: {
            id: 'asc'
        }
    }, {
        counts:  [],
        total:   0,
        getData: function ($defer, params) {
            transactionListFactory.get(
                {
                    "page":    params.page(),
                    "limit":   params.count(),
                    "sortBy":  params.orderBy(),
                    "filters": $scope.transactionFilter
                }, function (data) {
                    params.total(data.total);
                    $defer.resolve(data.content);
                });
        }
    });

    $scope.toggleTransactionFilterView = function () {
        if (!$scope.showTransactionFilterView) {
            $scope.showTransactionFilterView = true;
        } else {
            $scope.showTransactionFilterView = !$scope.showTransactionFilterView;
        }
    };

    $scope.applyFilters = function () {
        $scope.tableParams.page(1);
        $scope.tableParams.reload();
    };

    $scope.clearFilters = function () {
        $scope.transactionFilter = {};
        $scope.applyFilters();
    };

});