(function() {
    'use strict';

    angular.module('settlerApplication').controller('PanelCtrl', function($scope,
                                                                          totalChargeUserToFactory,
                                                                          totalChargeUserFromFactory,
                                                                          categoryWithValueFactory,
                                                                          userWithValueFactory) {

        $scope.charges = {
            to:   {},
            from: {}
        };

        $scope.categoriesWithValue = categoryWithValueFactory.query();
        $scope.usersWithValue = userWithValueFactory.query();

        totalChargeUserToFactory.query({
            id: null
        }).$promise.then(function(data) {
            $scope.charges.to.list = data;

            totalChargeUserFromFactory.query({
                id: null
            }).$promise.then(function(data) {
                $scope.charges.from.list = data;
                var i, j;

                $scope.charges.to.sum = 0;
                for (i = 0; i < $scope.charges.to.list.length; i++) {
                    $scope.charges.to.sum += $scope.charges.to.list[i].charge;
                }
                $scope.charges.from.sum = 0;
                for (i = 0; i < $scope.charges.from.list.length; i++) {
                    $scope.charges.from.sum += $scope.charges.from.list[i].charge;
                }

                for (i = 0; i < $scope.charges.to.list.length; i++) {
                    for (j = 0; j < $scope.charges.from.list.length; j++) {
                        if ($scope.charges.to.list[i].userFromId === $scope.charges.from.list[j].userToId) {
                            if ($scope.charges.to.list[i].charge < $scope.charges.from.list[j].charge) {
                                $scope.charges.from.list[j].charge = (parseFloat($scope.charges.from.list[j].charge)
                                - parseFloat($scope.charges.to.list[i].charge)).toFixed(2);
                                $scope.charges.to.list.splice(i, 1);
                                i--;
                            } else {
                                $scope.charges.to.list[i].charge = (parseFloat($scope.charges.to.list[i].charge)
                                - parseFloat($scope.charges.from.list[j].charge)).toFixed(2);
                                $scope.charges.from.list.splice(j, 1);
                                j--;
                            }
                        }
                    }
                }

            });
        });


    });

})();