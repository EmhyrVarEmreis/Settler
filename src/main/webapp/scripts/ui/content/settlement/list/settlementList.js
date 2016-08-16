(function() {
    'use strict';

    angular.module('settlerApplication').config(function($stateProvider) {
        $stateProvider.state("settlementList", {
            parent:  'restrictedSite',
            url:     "/settlement/list",
            views:   {
                'content@': {
                    templateUrl: 'scripts/ui/common/entityList/entityList.html',
                    controller:  'entityGenericListCtrl'
                }
            },
            resolve: {},
            data:    {
                entity: 'settlement'
            }
        });
    });

})();