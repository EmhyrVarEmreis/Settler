(function () {
    'use strict';

    angular.module('settlerApplication').filter('isEmpty', function () {
        return function (input, replaceText) {
            if (input) return input;
            return replaceText;
        };
    });
})();

