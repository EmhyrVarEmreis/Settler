(function() {
    'use strict';

    angular.module('settlerApplication').controller('LoginCtrl', function($rootScope, $scope, $state, $timeout, Auth) {
        $scope.user = {};
        $scope.errors = {};

        $scope.authenticationErrorStatusCode = false;
        $scope.authenticationErrorSuperStatusCode = false;


        $timeout(function() {
            angular.element('[ng-model="username"]').focus();
        });

        $scope.login = function() {
            Auth.logout();
            $scope.authenticationError = false;
            Auth.login({
                login:    $scope.username,
                password: $scope.password
            }).then(function(data) {
                $state.go('panel');
            }).catch(function(error) {
                $scope.authenticationError = true;
                $scope.authenticationErrorSuperStatusCode = error.data.status;
                $scope.authenticationErrorStatusCode = error.status;
            });

        };
    });

})();