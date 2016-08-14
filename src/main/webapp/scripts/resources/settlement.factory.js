(function () {
    'use strict';

    angular.module('settlerApplication').factory('settlementListFactory', function ($resource) {
        return $resource('api/settlement/list');
    });

    angular.module('settlerApplication').factory('settlementDetailsFactory', function ($resource) {
        return $resource('api/settlement/details');
    });

})();
