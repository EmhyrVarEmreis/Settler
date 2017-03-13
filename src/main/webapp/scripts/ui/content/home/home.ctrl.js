(function () {
    'use strict';

    angular.module('settlerApplication').controller('HomeCtrl', function($scope, homeFactory, Auth) {

        $scope.stats = homeFactory.get();

        $scope.isAuth = function() {
            return Auth.isToken();
        };

    });

})();