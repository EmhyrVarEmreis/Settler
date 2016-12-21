(function() {
    'use strict';

    angular.module('settlerServices').service('dictionaryService', function (dictionaryFactory) {

        this.cached = {};

        this.getDict = function(dictName) {
            var cached = this.cached[dictName];
            if (cached === undefined) {
                cached = dictionaryFactory.query({name: dictName});
                this.cached[dictName] = cached;
            }
            return cached;
        }
    });

})();