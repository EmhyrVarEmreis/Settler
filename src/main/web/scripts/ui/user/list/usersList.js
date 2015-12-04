'use strict';

angular.module('settlerApplication').config(function ($stateProvider) {
    $stateProvider.state("usersList", {
        parent:  'restrictedSite',
        url:     "/usersList",
        views:   {
            'content@': {
                templateUrl: 'scripts/ui/user/list/usersList.html',
                controller:  'usersListCtrl'
            }
        },
        resolve: {}
    });
});