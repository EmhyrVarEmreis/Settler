(function () {
    'use strict';

    angular.module('settlerApplication').controller('PanelCtrl', function ($scope,
                                                                           totalChargeUserToFactory,
                                                                           totalChargeUserFromFactory) {

        $scope.charges = {};

        totalChargeUserToFactory.query({
            id: null
        }).$promise.then(function (data) {
            $scope.charges.to = data;
        });

        totalChargeUserFromFactory.query({
            id: null
        }).$promise.then(function (data) {
            $scope.charges.from = data;
        });

    });

})();