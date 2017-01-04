(function () {
    'use strict';

    angular.module('settlerServices').service('historyService', function () {

        this.history = [];

        this.prev = function () {
            if (this.history.length === 0) {
                return null;
            } else {
                return this.history[this.history.length - 1];
            }
        };

        this.push = function (url) {
            this.history.push(url);
        };

        this.clear = function () {
            this.history.splice(0, this.history.length)
        };

    });

})();