(function () {
    'use strict';

    angular.module('settlerApplication').factory('roleAssignmentsByTargetFactory', function ($resource) {
        return $resource('api/permission/role/assignment/target');
    });

})();
