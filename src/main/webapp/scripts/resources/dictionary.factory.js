(function () {
    'use strict';

    angular.module('settlerApplication').factory('dictionaryFactory', function ($resource) {
        return $resource('api/dictionary');
    });

})();
