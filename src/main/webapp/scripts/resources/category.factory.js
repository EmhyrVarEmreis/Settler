(function () {
    'use strict';

    angular.module('settlerApplication').factory('categoryListFactory', function ($resource) {
        return $resource('api/category/list');
    });

    angular.module('settlerApplication').factory('categoryDetailsFactory', function ($resource) {
        return $resource('api/category/details');
    });

    angular.module('settlerApplication').factory('categorySearchSimpleFactory', function ($resource) {
        return $resource('api/category/search/simple');
    });

    angular.module('settlerApplication').factory('categoryWithValueFactory', function ($resource) {
        return $resource('api/category/values');
    });

})();
