(function() {
    'use strict';

    angular.module('settlerServices').factory('AuthenticateCli', function($resource) {
        return $resource('api/authenticate', {}, {
            'login': {method: 'POST'}
        });
    });

    angular.module('settlerServices').factory('AuthenticateFbCli', function($resource) {
        return $resource('api/authenticate/fb', {}, {
            'login': {method: 'POST'}
        });
    });

})();
