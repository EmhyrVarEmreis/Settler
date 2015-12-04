'use strict';

angular.module('settlerApplication').config(function ($stateProvider) {
    $stateProvider.state("recoverPassword", {
        parent:  'publicSite',
        url:     "/recoverPassword/:userId/:token",
        views:   {
            'content@': {
                templateUrl: 'scripts/ui/recoverPassword/recoverPassword.html',
                controller:  'RecoverPasswordCtrl'
            }
        },
        resolve: {}
    });
});