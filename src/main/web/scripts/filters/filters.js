var app = angular.module('settlerApplication');

app.filter('isEmpty', function () {
    return function (input, replaceText) {
        if (input) return input;
        return replaceText;
    }
});

