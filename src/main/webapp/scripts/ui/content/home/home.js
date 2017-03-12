(function () {
    'use strict';

    angular.module('settlerApplication').config(function ($stateProvider) {
        $stateProvider.state("home", {
            parent:  'publicSite',
            url:     "/home",
            views:   {
                'content@': {
                    templateUrl: 'scripts/ui/content/home/home.html',
                    controller:  'HomeCtrl'
                }
            },
            resolve: {}
        });
    });
})();