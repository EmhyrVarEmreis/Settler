(function() {
    'use strict';

    angular.module('settlerApplication').controller('entityGenericListCtrl', function($scope,
                                                                                      $stateParams,
                                                                                      NgTableParams,
                                                                                      userEntityListFactory,
                                                                                      transactionEntityListFactory) {

        $scope.getListFactory = function() {
            switch ($scope.$resolve.$$state.data.entity) {
                case 'user':
                    return userEntityListFactory;
                case 'transaction':
                    return transactionEntityListFactory;
                default:
                    return {};
            }
        };

        angular.merge($scope, $scope.getListFactory());

    });

})();