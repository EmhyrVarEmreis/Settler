(function() {
    'use strict';

    angular.module('settlerApplication')
        .directive('dictionarySelector', function(dictionaryService) {
            return {
                scope:       {
                    dictionary:    '=',
                    dictionaryKey: '@',
                    label:         '@',
                    name:          '@',
                    clearBtn:      '@',
                    selectFirst:   '@',
                    placeholder:   '@',
                    ngModel:       '='
                },
                transclude:  true,
                restrict:    'E',
                templateUrl: 'scripts/ui/common/dictionaries/dictionarySelector/dictionarySelector.html',
                link:        function(scope, elm) {
                    var transcluded = elm.contents().text();
                    scope.withLabel = transcluded.length > 0; // true or false

                    if (scope.dictionaryKey)
                        scope.dictData = dictionaryService.getDict(scope.dictionaryKey);
                    else
                        scope.dictData = scope.dictionary;
                }
            };
        });

})();