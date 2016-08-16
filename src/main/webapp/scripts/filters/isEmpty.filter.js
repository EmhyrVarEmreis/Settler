(function() {
    'use strict';

    angular.module('settlerApplication').filter('isEmpty', function() {
        return function(input, replaceText) {
            if (input == null || (typeof input === 'undefined')) {
                return replaceText;
            } else {
                return input;
            }
        };
    });

})();

