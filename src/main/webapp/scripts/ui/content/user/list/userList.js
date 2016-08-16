(function() {
    'use strict';

    angular.module('settlerApplication').config(function($stateProvider) {
        $stateProvider.state("userList", {
            parent:  'restrictedSite',
            url:     "/user/list",
            views:   {
                'content@': {
                    templateUrl: 'scripts/ui/common/entityList/entityList.html',
                    controller:  'entityGenericListCtrl'
                }
            },
            resolve: {},
            data:    {
                entity: 'user'
            }
        });
    });

})();