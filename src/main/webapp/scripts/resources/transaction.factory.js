(function() {
    'use strict';

    angular.module('settlerApplication').factory('transactionListFactory', function($resource) {
        return $resource('api/transaction/list');
    });

    angular.module('settlerApplication').factory('transactionDetailsFactory', function($resource) {
        return $resource('api/transaction/details');
    });

    angular.module('settlerApplication').factory('transactionMostUsedUsersFactory', function ($resource) {
        return $resource('api/transaction/users');
    });

})();
