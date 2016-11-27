(function () {
    'use strict';

    angular.module('settlerApplication').config(function ($stateProvider) {
        $stateProvider.state("categoryList", {
            parent:  'restrictedSite',
            url:     "/category/list",
            views:   {
                'content@': {
                    templateUrl: 'scripts/ui/common/entityList/entityList.html',
                    controller:  'entityGenericListCtrl'
                }
            },
            resolve: {},
            data:    {
                entity: 'category'
            }
        });
    });

})();