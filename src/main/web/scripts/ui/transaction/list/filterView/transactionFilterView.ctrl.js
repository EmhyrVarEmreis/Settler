(function () {
    'use strict';

    angular.module('settlerApplication').controller('TransactionFilterViewCtrl', TransactionFilterViewCtrl);

    function TransactionFilterViewCtrl() {
        var ctrl = this;

        ctrl.reference = '';
    }
})();