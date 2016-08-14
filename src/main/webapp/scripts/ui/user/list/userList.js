(function () {
    'use strict';

    angular.module('settlerApplication').config(function ($stateProvider) {
        $stateProvider.state("userList", {
            parent:  'restrictedSite',
            url:     "/user/list",
            views:   {
                'content@': {
                    templateUrl: 'scripts/ui/user/list/userList.html',
                    controller:  'UserListCtrl'
                }
            },
            resolve: {}
        });
    });
})();