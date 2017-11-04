(function () {
    'use strict';

    angular.module('settlerApplication').controller('entityGenericListCtrl', function ($scope,
                                                                                       $stateParams,
                                                                                       NgTableParams,
                                                                                       userEntityListFactory,
                                                                                       categoryEntityListFactory,
                                                                                       transactionEntityListFactory,
                                                                                       transactionEntityViewFactory) {

        $scope.getListFactory = function () {
            switch ($scope.$resolve.$$state.data.entity) {
                case 'user':
                    return userEntityListFactory;
                case 'category':
                    return categoryEntityListFactory;
                case 'transaction':
                    return transactionEntityListFactory;
                case 'transactionView':
                    return transactionEntityViewFactory;
                default:
                    return {};
            }
        };

        angular.merge($scope, $scope.getListFactory());

    });

})();