(function() {
    'use strict';

    angular.module('settlerApplication').controller('TransactionDetailsCtrl', function($scope,
                                                                                       NgTableParams,
                                                                                       transactionDetailsFactory,
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

    });

})();