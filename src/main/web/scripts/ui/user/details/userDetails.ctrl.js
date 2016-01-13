(function () {
    'use strict';

    angular.module('settlerApplication').controller('UserDetailsCtrl', function ($scope,
                                                                                 NgTableParams,
                                                                                 userDetailsFactory,
                                                                                 $stateParams) {

        $scope.data = {};
        if ($stateParams.state !== 'new') {
            $scope.data = userDetailsFactory.get({id: $stateParams.state});
        }

    });

})();