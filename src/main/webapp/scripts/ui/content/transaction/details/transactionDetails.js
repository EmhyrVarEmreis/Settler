(function() {
    'use strict';

    angular.module('settlerApplication').config(function($stateProvider) {
        $stateProvider.state("transactionDetails", {
            parent:  'restrictedSite',
            url:     "/transaction/details/:state",
            params:  {
                state: {
                    value:   'new',
                    dynamic: true
                }
            },
            views:   {
                'content@': {
                    templateUrl: 'scripts/ui/content/transaction/details/transactionDetails.html',
                    controller:  'TransactionDetailsCtrl'
                }
            },
            resolve: {}
        });
    });

})();