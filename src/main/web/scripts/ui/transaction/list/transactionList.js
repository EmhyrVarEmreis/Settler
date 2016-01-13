(function () {
    'use strict';

    angular.module('settlerApplication').config(function ($stateProvider) {
        $stateProvider.state("transactionList", {
            parent:  'restrictedSite',
            url:     "/transaction/list",
            views:   {
                'content@': {
                    templateUrl: 'scripts/ui/transaction/list/transactionList.html',
                    controller:  'TransactionListCtrl'
                }
            },
            resolve: {}
        });
    });
})();