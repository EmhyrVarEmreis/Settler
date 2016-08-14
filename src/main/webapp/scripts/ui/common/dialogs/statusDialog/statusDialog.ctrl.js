(function () {
    'use strict';

    angular.module('settlerApplication').controller('statusDialogCtrl', function($scope, $uibModalInstance, conf) {

        $scope.modalTitle = conf.modalTitle;
        $scope.modalBody = conf.modalBody;
        $scope.isOK = conf.isOK;

        $scope.ok = function () {
            $uibModalInstance.close();
        };
    });
})();