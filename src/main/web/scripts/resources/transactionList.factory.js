'use strict';

angular.module('settlerApplication').factory('transactionListFactory', function ($resource) {
    return $resource('api/transaction/list');
});
