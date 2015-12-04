(function () {
    'use strict';

    angular.module('settlerApplication').controller('UserFilterViewCtrl', UserFilterViewCtrl);

    function UserFilterViewCtrl() {
        var ctrl = this;

        ctrl.name = '';
        ctrl.login = '';
        ctrl.email = '';
    }
})();