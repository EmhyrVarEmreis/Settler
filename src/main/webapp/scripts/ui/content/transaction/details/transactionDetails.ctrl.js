(function() {
    'use strict';

    angular.module('settlerApplication').controller('TransactionDetailsCtrl', function($scope,
                                                                                       NgTableParams,
                                                                                       transactionDetailsFactory,
                                                                                       userSearchSimpleFactory,
                                                                                       $stateParams,
                                                                                       modalService) {

        $scope.data = {};
        if ($stateParams.state !== 'new') {
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
            transactionDetailsFactory.save(
                $scope.data,
                function(data) {
                    $scope.data = data;
                }, function(err) {
                    modalService.createErrorDialogFromResponse(err);
                }
            );
        };

        $scope.getValueString = function(t, r) {
            return (parseFloat(r.value) / parseFloat(t.value) * 100).toFixed(2);
        };

        $scope.refreshCreatorList = function(input) {
            if (input == null || input.length < 3) {
                return [];
            }
            $scope.creatorList = userSearchSimpleFactory.query({
                limit:  10,
                string: input
            });
            return $scope.creatorList;
        }

    });

})();