(function() {
    'use strict';

    angular.module('settlerApplication').config(function($stateProvider) {
        $stateProvider.state("transactionList", {
            parent:  'restrictedSite',
            url:     "/transaction/list",
            views:   {
                'content@': {
                    templateUrl: 'scripts/ui/list-entity/list-entity.html',
                    controller:  'entityGenericListCtrl'
                }
            },
            resolve: {},
            data:    {
                entity: 'transaction'
            }
        });
    });

})();