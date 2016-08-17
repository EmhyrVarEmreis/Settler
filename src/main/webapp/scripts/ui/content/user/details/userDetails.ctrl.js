(function() {
    'use strict';

    angular.module('settlerApplication').controller('UserDetailsCtrl', function($scope,
                                                                                NgTableParams,
                                                                                userDetailsFactory,
                                                                                $stateParams,
                                                                                modalService) {

        $scope.data = {};
        if ($stateParams.state !== 'new') {
            userDetailsFactory.get(
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
            userDetailsFactory.save(
                $scope.data,
                function(data) {
                    $scope.data = data;
                }, function(err) {
                    modalService.createErrorDialogFromResponse(err);
                }
            );
        };

    });

})();