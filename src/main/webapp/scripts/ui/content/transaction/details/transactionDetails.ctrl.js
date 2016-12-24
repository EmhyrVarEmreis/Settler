(function() {
    'use strict';

    angular.module('settlerApplication').controller('TransactionDetailsCtrl', function($scope,
                                                                                       NgTableParams,
                                                                                       transactionDetailsFactory,
                                                                                       $state,
                                                                                       $stateParams,
                                                                                       modalService,
                                                                                       avatarService,
                                                                                       userAvatarFactory,
                                                                                       $uibModal,
                                                                                       Principal) {

        $scope.data = {
            owners:      [],
            contractors: []
        };

        $scope.avatars = {};

        Principal.identity().then(function(account) {
            $scope.currentUser = account;
        });

        $scope.getAvatar = function(userId) {
            return avatarService.getAvatarByUserId(userId);
        };

        $scope.isNew = function() {
            return $stateParams.state === 'new';
        };

        if (!$scope.isNew()) {
            transactionDetailsFactory.get(
                {
                    id: $stateParams.state
                },
                function(data) {
                    $scope.data = data;
                }, function(err) {
                    modalService.createErrorDialogFromResponse(err);
                }
            );
        }

        $scope.save = function() {
            if ($scope.isFormValid()
                && $scope.isRedistributionListComplete($scope.data.owners)
                && $scope.isRedistributionListComplete($scope.data.contractors)) {
                transactionDetailsFactory.save(
                    $scope.data,
                    function(data) {
                        $scope.data = data;
                        $stateParams.state = $scope.data.id;
                        $state.go(
                            $state.current,
                            {
                                state: $scope.data.id
                            },
                            {
                                location: true,
                                inherit:  true,
                                notify:   false
                            }
                        );
                    }, function(err) {
                        modalService.createErrorDialogFromResponse(err);
                    }
                );
            }
        };

        $scope.isFormValid = function() {
            return !$scope.transactionDetailsForm.$invalid
                && !(!$scope.data.owners || $scope.data.owners.length < 1)
                && !(!$scope.data.categories || $scope.data.categories.length < 1)
                && !(!$scope.data.contractors || $scope.data.contractors.length < 1);
        };

        $scope.parseFloatOwn = function(s) {
            return parseFloat(s.toString().replace(/,/, '.'));
        };

        $scope.getPercentString = function(t, r) {
            if (!r.percentage || !t.value) {
                return parseFloat('0').toFixed(2);
            } else {
                return $scope.parseFloatOwn(r.percentage).toFixed(2);
            }
        };

        $scope.getValueString = function(t, r) {
            if (!r.percentage || !t.value) {
                return parseFloat('0').toFixed(2);
            } else {
                return ($scope.parseFloatOwn(r.percentage) * $scope.parseFloatOwn(t.value) / 100).toFixed(2);
            }
        };

        $scope.openAddRedistributionModal = function(redistributionList) {
            var modalInstance = $uibModal.open({
                animation:    true,
                templateUrl:  'scripts/ui/content/transaction/details/addRedistribution/addRedistribution.html',
                controller:   'AddRedistributionCtrl',
                controllerAs: '$ctrl',
                resolve:      {
                    redistributionList: function() {
                        return redistributionList;
                    },
                    totalValue:         function() {
                        return $scope.data.value;
                    }
                }
            });

            modalInstance.result.then(function(item) {
                redistributionList.push(
                    {
                        percentage: item.percentage,
                        user:       item.user
                    }
                );
            }, function() {
            });
        };

        $scope.openAddCategoryModal = function() {
            var modalInstance = $uibModal.open({
                animation:    true,
                templateUrl:  'scripts/ui/content/transaction/details/addCategory/addCategory.html',
                controller:   'AddCategoryCtrl',
                controllerAs: '$ctrl'
            });

            modalInstance.result.then(function(item) {
                if (!$scope.data.categories) {
                    $scope.data.categories = [];
                }
                for (var i = 0; i < $scope.data.categories.length; i++) {
                    if ($scope.data.categories[i].code === item.code) {
                        return;
                    }
                }
                $scope.data.categories.push(item);
            }, function() {
            });
        };

        $scope.addLoggedUserToRedistribution = function(list, percentage) {
            list.push(
                {
                    percentage: percentage,
                    user:       $scope.currentUser
                }
            );
        };

        $scope.isRedistributionListComplete = function(list) {
            var sum = 0;
            for (var i = 0; i < list.length; i++) {
                sum += list[i].percentage;
            }
            return sum === 100;
        };

    });

})();