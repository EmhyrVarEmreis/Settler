(function () {
    'use strict';

    angular.module('settlerApplication').factory('totalChargeUserToFactory', function ($resource) {
        return $resource('api/charge/user/to');
    });

    angular.module('settlerApplication').factory('totalChargeUserFromFactory', function ($resource) {
        return $resource('api/charge/user/from');
    });

})();
