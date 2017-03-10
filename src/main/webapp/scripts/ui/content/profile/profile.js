(function() {
    'use strict';

    angular.module('settlerApplication').config(function($stateProvider) {
        $stateProvider.state("profile", {
            parent:  'restrictedSite',
            url:     "/profile",
            views:   {
                'content@': {
                    templateUrl: 'scripts/ui/content/profile/profile.html',
                    controller:  'ProfileCtrl'
                }
            },
            resolve: {}
        });
    });

})();