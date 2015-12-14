angular.module('settlerApplication').filter('myCurrency', function () {
    return function (input, currency) {
        return input + " " + currency;
    };
});