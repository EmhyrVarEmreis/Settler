'use strict';

angular.module('settlerApplication').factory('userListFactory', function ($resource) {
    return $resource('api/user/list');
});


