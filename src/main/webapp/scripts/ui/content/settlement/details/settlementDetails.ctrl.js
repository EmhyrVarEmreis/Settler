(function() {
    'use strict';

    angular.module('settlerApplication').controller('SettlementDetailsCtrl', function($scope,
                                                                                      NgTableParams,
                                                                                      settlementDetailsFactory,
                                                                                      $stateParams,
                                                                                      modalService) {

        $scope.data = {};
        if ($stateParams.state !== 'new') {
            settlementDetailsFactory.get({id: $stateParams.state},
                function(data) {
                    $scope.data = data;
                }, function(err) {
                    modalService.createErrorDialogFromResponse(err);
                }
            );
        }

    });

})();