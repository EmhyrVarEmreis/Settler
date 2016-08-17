(function() {
    'use strict';

    angular.module('settlerApplication').factory('userListFactory', function($resource) {
        return $resource('api/user/list');
    });

    angular.module('settlerApplication').factory('userDetailsFactory', function($resource) {
        return $resource('api/user/details');
    });

})();
