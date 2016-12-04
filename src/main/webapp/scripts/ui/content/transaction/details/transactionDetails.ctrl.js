(function () {
    'use strict';

    angular.module('settlerApplication').controller('TransactionDetailsCtrl', function ($scope,
                                                                                        NgTableParams,
                                                                                        transactionDetailsFactory,
                                                                                        $stateParams,
                                                                                        modalService,
                                                                                        $uibModal) {

        $scope.data = {
            owners:      [],
            contractors: []
        };

        $scope.isNew = $stateParams.state === 'new';

        if (!$scope.isNew) {
            transactionDetailsFactory.get(
                {
                    id: $stateParams.state
                },
                function (data) {
                    $scope.data = data;
                }, function (err) {
                    modalService.createErrorDialogFromResponse(err);
                }
            );
        }

        $scope.save = function () {
            transactionDetailsFactory.save(
                $scope.data,
                function (data) {
                    $scope.data = data;
                }, function (err) {
                    modalService.createErrorDialogFromResponse(err);
                }
            );
        };

        $scope.getPercentString = function (t, r) {
            return (parseFloat(r.value.toString().replace(/,/, '.')) / parseFloat(t.value.toString().replace(/,/, '.')) * 100).toFixed(2);
        };

        $scope.getValueString = function (t) {
            return parseFloat(t.value.toString().replace(/,/, '.')).toFixed(2);
        };

        $scope.openAddRedistributionModal = function (redistributionList) {
            var modalInstance = $uibModal.open({
                animation:    true,
                templateUrl:  'scripts/ui/content/transaction/details/addRedistribution/addRedistribution.html',
                controller:   'AddRedistributionCtrl',
                controllerAs: '$ctrl',
                resolve:      {
                    redistributionList: function () {
                        return redistributionList;
                    },
                    totalValue:         function () {
                        return $scope.data.value;
                    }
                }
            });

            modalInstance.result.then(function (selectedItem) {
                redistributionList.push(
                    {
                        value: selectedItem.value,
                        user:  selectedItem.user
                    }
                );
            }, function () {
            });
        };

        $scope.addNewCategory = function () {
            if (!$scope.data.categories) {
                $scope.data.categories = [];
            }
            $scope.data.categories.push($scope.newCategory);
            $scope.newCategory = null;
        };

    });

})();