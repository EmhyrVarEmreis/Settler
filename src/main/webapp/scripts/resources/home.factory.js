(function () {
    'use strict';

    angular.module('settlerApplication').factory('homeFactory', function ($resource) {
        return $resource('api/home');
    });

})();
