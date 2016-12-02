(function () {
    'use strict';

    angular.module('settlerApplication')
        .directive('smartTableButtons', function () {
            return {
                scope:       {
                    showFilters:        '=',
                    showVisibility:     '=',
                    applyFilters:       '=',
                    clearFilters:       '=',
                    toggleFilters:      '=',
                    toggleVisibility:   "=",
                    newEntityButton:    "=",
                    newEntityButtonUrl: "="
                },
                transclude:  true,
                restrict:    'E',
                templateUrl: 'scripts/ui/common/smartTable/buttons/smartTableButtons.html',
                link:        function (scope, elm) {

                }
            };
        });

})();
