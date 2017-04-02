(function() {
    'use strict';

    angular.module('settlerApplication').filter('setDecimal', function() {
        return function(input, places) {
            if (isNaN(input)) {
                return input;
            }
            var n;
            if (input.toString().indexOf('.') === -1 && input.toString().indexOf(',') === -1) {
                input += '.';
                n = 0;
            } else {
                n = input.toString().split(/[.,]/)[1].length;
            }
            for (var i = n; i < places; i++) {
                input += '0';
            }
            return input;
        };
    });

})();