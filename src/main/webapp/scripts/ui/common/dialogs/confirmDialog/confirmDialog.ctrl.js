(function () {
    'use strict';

    angular.module('settlerApplication').controller('confirmDialogCtrl', function($scope, $uibModalInstance, conf) {

        $scope.modalTitle = conf.modalTitle;
        $scope.modalBody = conf.modalBody;

        $scope.ok = function () {
            $uibModalInstance.close();
        };

        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };
    });
})();