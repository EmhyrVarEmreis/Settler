(function () {
    'use strict';

    angular.module('settlerApplication').controller('AddRoleAssignmentCtrl', function ($uibModalInstance) {

        var $ctrl = this;

        $ctrl.ok = function () {
            $uibModalInstance.close($ctrl.selected);
        };

        $ctrl.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };

    });

})();