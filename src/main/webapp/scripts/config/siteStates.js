(function() {
    'use strict';

    angular.module('settlerApplication').config(function($stateProvider) {

        $stateProvider.state("publicSite", {
            abstract: true,
            views:    {
                'footer@': {
                    templateUrl:  'scripts/ui/footer/footer.html',
                    controller:   'FooterCtrl',
                    controllerAs: 'ftr'
                }
            }
        });

        $stateProvider.state("restrictedSite", {
            abstract: true,
            parent:   'publicSite',
            data:     {
                roles: ['authenticated']
            },
            resolve:  {
                authorize: function(Auth) {
                    return Auth.authorize();
                }
            },
            views:    {
                'navBar@': {
                    templateUrl:  'scripts/ui/navBar/navBar.html',
                    controller:   'NavBarCtrl',
                    controllerAs: 'nav'
                }
            }
        });

    });

})();