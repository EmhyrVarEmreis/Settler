(function () {
    'use strict';

    angular.module('settlerApplication').controller('confirmDialogCtrl', function ($scope, $modalInstance, conf) {

        $scope.modalTitle = conf.modalTitle;
        $scope.modalBody = conf.modalBody;

        $scope.ok = function () {
            $modalInstance.close();
        };

        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };
    });
})();