(function () {
    'use strict';

    angular.module('settlerApplication').controller('statusDialogCtrl', function ($scope, $modalInstance, conf) {

        $scope.modalTitle = conf.modalTitle;
        $scope.modalBody = conf.modalBody;
        $scope.isOK = conf.isOK;

        $scope.ok = function () {
            $modalInstance.close();
        };
    });
})();