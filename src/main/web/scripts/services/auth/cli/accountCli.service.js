(function () {
    'use strict';

    angular.module('settlerServices').factory('AccountCli', function ($resource) {
        return $resource('api/account', {}, {
            'get': {
                method:      'GET', params: {}, isArray: false,
                interceptor: {
                    response: function (response) {
                        // expose response
                        return response;
                    }
                }
            }
        });
    });
})();
