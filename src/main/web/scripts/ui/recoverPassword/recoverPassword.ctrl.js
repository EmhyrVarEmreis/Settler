(function () {
    'use strict';

    angular.module('settlerApplication').controller('RecoverPasswordCtrl', function ($rootScope, $scope, $state, $timeout, Auth, modalService,
                                                                                         $http, $stateParams) {

        $scope.error = false;

        $scope.goLogin = function () {
            $state.go('login');
        };

        $scope.validateToken = function () {
            $http({
                method:  'POST',
                url:     '/api/user/password/token/verify',
                data:    $.param({
                    token:  $stateParams.token,
                    userId: $stateParams.userId
                }),
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                }
            }).then(function (result, status) {
                $scope.isTokenValid = (result.data.isValid === 'true' || result.data.isValid === true);
                $scope.login = result.data.login;
            }, function (error) {
                $scope.error = true;
            });
        };

        $scope.setPassword = function () {
            if ($scope.newPassword && ($scope.isTokenValid === true || $scope.isTokenValid == "true")) {
                $http({
                    method:  'POST',
                    url:     '/api/user/password/recover',
                    data:    $.param({
                        userId:      $stateParams.userId,
                        token:       $stateParams.token,
                        newPassword: $scope.newPassword
                    }),
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded'
                    }
                }).then(function (result) {
                    if (result.data === 'true' || result.data === true) {
                        modalService.createSuccessDialog('<strong>Hasło zmienione! Zaloguj się ponownie</strong>');
                        $state.go('login');
                    } else if (result.data === 'false' || result.data === false) {
                        modalService.createErrorDialog('<strong>Upewnij się, że użyto odpowiedniego linku i spróbuj ' +
                            'ponownie. W razie dalszych problemów skontaktuj się z administratorem strony.</strong>');
                    } else {
                        modalService.createErrorDialog('<strong>Serwer nie odpowiada lub nastapił krytyczny błąd ' +
                            'wewnętrzny. Skontaktuj się z administratorem.</strong>');
                    }
                }, function (error) {
                    modalService.createErrorDialog('<strong>Serwer nie odpowiada lub nastapił krytyczny błąd ' +
                        'wewnętrzny. Skontaktuj się z administratorem.</strong>');
                    $scope.recoverMode = false;
                });
                $scope.newPassword = undefined;
            }
        };

        $scope.validateToken();

    });
})();