(function () {
    'use strict';

    angular.module('settlerApplication').controller('UserDetailsCtrl', function ($scope,
                                                                                 NgTableParams,
                                                                                 userDetailsFactory,
                                                                                 $stateParams,
                                                                                 modalService,
                                                                                 $timeout,
                                                                                 avatarService,
                                                                                 Upload) {

        $scope.data = {};
        $scope.avatarFile = null;
        $scope.avatar = null;

        $scope.loadAvatar = function () {
            $scope.avatar = avatarService.getAvatarByUserId(!$scope.data.id ? $stateParams.state : $scope.data.id);
        };

        if ($stateParams.state !== 'new') {
            userDetailsFactory.get(
                {
                    id: $stateParams.state
                },
                function (data) {
                    $scope.data = data;
                }, function (err) {
                    modalService.createErrorDialogFromResponse(err);
                }
            );
            $scope.loadAvatar();
        }

        $scope.save = function () {
            userDetailsFactory.save(
                $scope.data,
                function (data) {
                    $scope.data = data;
                }, function (err) {
                    modalService.createErrorDialogFromResponse(err);
                }
            );
        };

        $scope.changeAvatarTrigger = function () {
            angular.element('#changeAvatar').trigger('click');
        };

        $scope.changeAvatar = function (file) {
            if (!file) {
                return;
            }

            $scope.avatarFile = file;

            file.upload = Upload.upload({
                url:  'api/file/upload',
                data: {
                    file:  file,
                    login: $scope.data.login,
                    id:    !$scope.data.avatar ? undefined : $scope.data.avatar
                }
            });

            file.upload.then(function (response) {
                $timeout(function () {
                    file.result = response.data;
                    $scope.data.avatar = response.data.id;
                    $scope.avatarFile = null;
                    avatarService.deCacheByUserId(!$scope.data.id ? $stateParams.state : $scope.data.id);
                    $scope.loadAvatar();
                });
            }, function (response) {
                if (response.status > 0) {
                    modalService.createErrorDialogFromResponse(response);
                }
                $scope.avatarFile = null;
            }, function (evt) {
                // Math.min is to fix IE which reports 200% sometimes
                file.progress = Math.min(100, parseInt(100.0 * evt.loaded / evt.total));
            });
        };

    });

})();