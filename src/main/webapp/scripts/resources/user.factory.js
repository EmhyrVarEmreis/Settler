(function() {
    'use strict';

    angular.module('settlerApplication').factory('userListFactory', function($resource) {
        return $resource('api/user/list');
    });

    angular.module('settlerApplication').factory('userDetailsFactory', function($resource) {
        return $resource('api/user/details');
    });

    angular.module('settlerApplication').factory('userSearchSimpleFactory', function($resource) {
        return $resource('api/user/search/simple');
    });

    angular.module('settlerApplication').factory('userAvatarFactory', function($resource) {
        return $resource('api/user/avatar');
    });

})();
