(function() {
    'use strict';

    angular.module('settlerApplication').config(function($stateProvider) {
        $stateProvider.state("settlementDetails", {
            parent:  'restrictedSite',
            url:     "/settlement/details/:state",
            views:   {
                'content@': {
                    templateUrl: 'scripts/ui/content/settlement/details/settlementDetails.html',
                    controller:  'SettlementDetailsCtrl'
                }
            },
            resolve: {}
        });
    });
    
})();