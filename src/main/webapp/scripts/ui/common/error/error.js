(function() {
    'use strict';

    angular.module('settlerApplication').config(function($stateProvider) {
        $stateProvider.state('error', {
            parent: 'publicSite',
            url:    '/error',
            views:  {
                'content@': {
                    templateUrl: 'scripts/ui/common/error/error.html'
                }
            }
        }).state('accessdenied', {
            parent: 'publicSite',
            url:    '/accessdenied',
            views:  {
                'content@': {
                    templateUrl: 'scripts/ui/common/error/accessDenied.html'
                }
            }
        });
    });

})();