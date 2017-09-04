(function () {
    angular.module('settlerApplication').controller('TransactionDetailsCtrl', function ($scope,
                                                                                        $state,
                                                                                        $stateParams,
                                                                                        $filter,
                                                                                        $uibModal,
                                                                                        Principal,
                                                                                        NgTableParams,
                                                                                        transactionDetailsFactory,
                                                                                        userAvatarFactory,
                                                                                        modalService,
                                                                                        avatarService) {

        $scope.data = {
            owners:      [],
            contractors: []
        };

        $scope.avatars = {};

        Principal.identity().then(function (account) {
            $scope.currentUser = account;
        });

        $scope.getAvatar = function (userId) {
            return avatarService.getAvatarByUserId(userId);
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

        $scope.reloadState = function() {
            $state.go(
                $state.current,
                {
                    state: $stateParams.state
                },
                {
                    location: true,
                    inherit:  true,
                    notify:   false
                }
            );
        };

        $scope.duplicate = function() {
            $stateParams.state = 'new';
            $scope.data.id = undefined;
            $scope.data.reference = undefined;
            $scope.reloadState();
        };

        $scope.save = function () {
            if ($scope.isFormValid()
                && $scope.isRedistributionListComplete($scope.data.owners)
                && $scope.isRedistributionListComplete($scope.data.contractors)) {
                transactionDetailsFactory.save(
                    $scope.data,
                    function (data) {
                        $scope.data = data;
                        $stateParams.state = $scope.data.id;
                        $scope.reloadState();
                    }, function (err) {
                        modalService.createErrorDialogFromResponse(err);
                    }
                );
            }
        };

        $scope.isFormValid = function () {
            return !$scope.transactionDetailsForm.$invalid
                && !(!$scope.data.owners || $scope.data.owners.length < 1)
                && !(!$scope.data.categories || $scope.data.categories.length < 1)
                && !(!$scope.data.contractors || $scope.data.contractors.length < 1);
        };

        $scope.parseFloatOwn = function (s) {
            return parseFloat(s.toString().replace(/,/, '.'));
        };

        $scope.getPercentString = function (t, r) {
            if (!r.percentage || !t.value) {
                return parseFloat('0').toFixed(2);
            } else {
                return $scope.parseFloatOwn(r.percentage).toFixed(2);
            }
        };

        $scope.getValueString = function (t, r) {
            if (!r.percentage || !t.value) {
                return $scope.formatNumber(parseFloat('0'));
            } else {
                return $scope.formatNumber($scope.parseFloatOwn(r.percentage) * $scope.parseFloatOwn(t.value) / 100);
            }
        };

        $scope.formatNumber = function (num) {
            return $filter('number')(num, 2);
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
                        percentage: item.percentage,
                        user:       item.user
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

        $scope.addLoggedUserToRedistribution = function (list, percentage) {
            list.push(
                {
                    percentage: percentage,
                    user:       $scope.currentUser
                }
            );
        };

        $scope.isRedistributionListComplete = function (list) {
            var sum = 0;
            for (var i = 0; i < list.length; i++) {
                sum += list[i].percentage;
            }
            return sum === 100;
        };

    });

    'use strict';

})();