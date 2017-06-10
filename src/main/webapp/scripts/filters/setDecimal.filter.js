(function() {
    'use strict';

    angular.module('settlerApplication').filter('setDecimal', function() {
        return function(input, places) {
            if (!input || isNaN(input)) {
                return input;
            }
            return parseFloat(("" + input).replace(/,/, '.')).toFixed(places);
        };
    });

})();