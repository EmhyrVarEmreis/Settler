(function () {
    'use strict';

    angular.module('settlerApplication').config(function ($stateProvider) {
        $stateProvider.state("userDetails", {
            parent:  'restrictedSite',
            url:     "/user/details/:state",
            views:   {
                'content@': {
                    templateUrl: 'scripts/ui/user/details/userDetails.html',
                    controller:  'UserDetailsCtrl'
                }
            },
            resolve: {}
        });
    });
})();