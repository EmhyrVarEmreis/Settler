(function () {
    'use strict';

    angular.module('settlerApplication').config(function ($stateProvider) {
        $stateProvider.state("transactionDetails", {
            parent:  'restrictedSite',
            url:     "/transaction/details/:state",
            views:   {
                'content@': {
                    templateUrl: 'scripts/ui/transaction/details/transactionDetails.html',
                    controller:  'TransactionDetailsCtrl'
                }
            },
            resolve: {}
        });
    });
})();