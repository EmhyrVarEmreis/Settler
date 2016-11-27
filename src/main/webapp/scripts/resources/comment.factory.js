(function () {
    'use strict';

    angular.module('settlerApplication').factory('commentByObjectIdFactory', function ($resource) {
        return $resource('api/comment/getByObjectId');
    });

})();
