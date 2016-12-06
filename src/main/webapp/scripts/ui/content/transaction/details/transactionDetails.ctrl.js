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

        $scope.isNew = function () {
            return $stateParams.state === 'new';
        };

        if (!$scope.isNew()) {
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
            if ($scope.isFormValid()) {
                transactionDetailsFactory.save(
                    $scope.data,
                    function (data) {
                        $scope.data = data;
                        $stateParams.state = $scope.data.id;
                    }, function (err) {
                        modalService.createErrorDialogFromResponse(err);
                    }
                );
            }
        };

        $scope.isFormValid = function () {
            return !$scope.transactionDetailsForm.$invalid
                && (!$scope.data.owners || $scope.data.owners.length < 1)
                && (!$scope.data.categories || $scope.data.categories.length < 1)
                && (!$scope.data.contractors || $scope.data.contractors.length < 1);
        };

        $scope.getPercentString = function (t, r) {
            if (!r.value || !t.value) {
                return parseFloat('0').toFixed(2);
            } else {
                return (parseFloat(r.value.toString().replace(/,/, '.')) / parseFloat(t.value.toString().replace(/,/, '.')) * 100).toFixed(2);
            }
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

            modalInstance.result.then(function (item) {
                redistributionList.push(
                    {
                        value: item.value,
                        user:  item.user
                    }
                );
            }, function () {
            });
        };

        $scope.openAddCategoryModal = function () {
            var modalInstance = $uibModal.open({
                animation:    true,
                templateUrl:  'scripts/ui/content/transaction/details/addCategory/addCategory.html',
                controller:   'AddCategoryCtrl',
                controllerAs: '$ctrl'
            });

            modalInstance.result.then(function (item) {
                if (!$scope.data.categories) {
                    $scope.data.categories = [];
                }
                for (var i = 0; i < $scope.data.categories.length; i++) {
                    if ($scope.data.categories[i].code === item.code) {
                        return;
                    }
                }
                $scope.data.categories.push(item);
            }, function () {
            });
        };

    });

})();