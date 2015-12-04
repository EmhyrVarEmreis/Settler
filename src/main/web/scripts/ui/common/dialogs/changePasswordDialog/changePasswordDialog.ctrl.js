(function () {
    'use strict';

    angular.module('settlerApplication').controller('changePasswordDialogCtrl', function ($scope, $modalInstance, conf, $http, Auth,
                                                                                          $state, $modal) {

        $scope.modalTitle = conf.modalTitle;

        $scope.error = false;

        $scope.oldPassword = '';
        $scope.newPassword = '';
        $scope.newPasswordRepeat = '';

        $scope.oldPasswordError = '';
        $scope.newPasswordError = '';
        $scope.newPasswordRepeatError = '';

        $scope.ok = function () {
            if ($scope.verify() === true) {
                $http({
                    method:  'POST',
                    url:     '/api/user/password/change',
                    data:    $.param({
                        userId:      conf.userId,
                        oldPassword: $scope.oldPassword,
                        newPassword: $scope.newPassword
                    }),
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded'
                    }
                }).then(function (result, status) {
                    if (result.data === 'true' || result.data === true) {
                        $modal.open({
                            templateUrl: 'scripts/ui/common/dialogs/statusDialog/statusDialog.html',
                            controller:  'statusDialogCtrl',
                            resolve:     {
                                conf: function () {
                                    return {
                                        modalTitle: 'Informacja',
                                        modalBody:  'Hasło zmienione.<br/>Proszę zalogować się ponownie.',
                                        isOK:       true
                                    };
                                }
                            }
                        });
                        $modalInstance.dismiss(true);
                        Auth.logout();
                        $state.go('login');
                    } else if (result.data === 'false' || result.data === false) {
                        $scope.errorFunction('Wprowadzone dane są nieprawidłowe!');
                    } else {
                        $scope.errorFunction('Błąd serwera!');
                    }
                }, function (error) {
                    // err
                });
            }
        };

        $scope.errorFunction = function (errorMsg) {
            $scope.error = errorMsg;
        };

        $scope.cancel = function () {
            $modalInstance.dismiss(false);
        };

        $scope.verify = function () {
            if ($scope.oldPassword.length === 0) {
                $scope.oldPasswordError = 'Wprowadź stare hasło!';
                $scope.newPasswordError = '';
                $scope.newPasswordRepeatError = '';
                return false;
            } else if ($scope.newPassword.length === 0) {
                $scope.oldPasswordError = '';
                $scope.newPasswordError = 'Podane hasło jest za krótkie!';
                $scope.newPasswordRepeatError = '';
                return false;
            } else if ($scope.newPassword !== $scope.newPasswordRepeat) {
                $scope.oldPasswordError = '';
                $scope.newPasswordError = 'Hasła nie pasują do siebie!';
                $scope.newPasswordRepeatError = $scope.newPasswordError;
                return false;
            }
            return true;
        };

    });
})
();