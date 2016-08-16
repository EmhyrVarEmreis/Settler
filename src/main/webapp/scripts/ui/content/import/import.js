(function() {
    'use strict';

    angular.module('settlerApplication').config(function($stateProvider) {
        $stateProvider.state("import", {
            parent:  'restrictedSite',
            url:     "/import",
            views:   {
                'content@': {
                    templateUrl: 'scripts/ui/content/import/import.html'
                }
            },
            resolve: {}
        });
    });
})();