'use strict';

angular.module('settlerApplication').config(function ($stateProvider) {
    $stateProvider.state("transactionsList", {
        parent:  'restrictedSite',
        url:     "/transactionsList",
        views:   {
            'content@': {
                templateUrl: 'scripts/ui/transaction/list/transactionsList.html',
                controller:  'transactionsListCtrl'
            }
        },
        resolve: {}
    });
});