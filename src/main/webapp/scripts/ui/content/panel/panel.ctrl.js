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

            totalChargeUserFromFactory.query({
                id: null
            }).$promise.then(function (data) {
                $scope.charges.from = data;

                for (var i = 0; i < $scope.charges.to.length; i++) {
                    for (var j = 0; j < $scope.charges.from.length; j++) {
                        if ($scope.charges.to[i].userFromId === $scope.charges.from[j].userToId) {
                            if ($scope.charges.to[i].charge < $scope.charges.from[j].charge) {
                                $scope.charges.from[j].charge = (parseFloat($scope.charges.from[j].charge)
                                - parseFloat($scope.charges.to[i].charge)).toFixed(2);
                                $scope.charges.to.splice(i, 1);
                                i--;
                            } else {
                                $scope.charges.to[i].charge = (parseFloat($scope.charges.to[i].charge)
                                - parseFloat($scope.charges.from[j].charge)).toFixed(2);
                                $scope.charges.from.splice(j, 1);
                                j--;
                            }
                        }
                    }
                }

            });
        });


    });

})();