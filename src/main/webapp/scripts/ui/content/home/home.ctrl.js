(function () {
    'use strict';

    angular.module('settlerApplication').controller('HomeCtrl', function ($scope, homeFactory) {

        $scope.stats = homeFactory.get();

    });

})();