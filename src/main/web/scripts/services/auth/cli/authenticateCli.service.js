'use strict';

angular.module('settlerServices').factory('AuthenticateCli', function ($resource) {
    return $resource('api/authenticate', {}, {
        'login': {method: 'POST'}
    });
});
