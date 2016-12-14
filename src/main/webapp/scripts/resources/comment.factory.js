(function () {
    'use strict';

    angular.module('settlerApplication').factory('commentFactory', function($resource) {
        return $resource('api/comment');
    });

})();
