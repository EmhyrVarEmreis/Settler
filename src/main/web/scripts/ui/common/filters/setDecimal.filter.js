angular.module('settlerApplication').filter('setDecimal', function () {
    return function (input, places) {
        if (isNaN(input)) return input;
        for (var i = input.toString().split(".")[1].length; i < places; i++) {
            input += "0";
        }
        return input;
    };
});