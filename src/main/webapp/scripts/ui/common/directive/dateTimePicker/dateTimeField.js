(function () {
    'use strict';

    angular.module('settlerApplication')
        .directive('dateTimeField', function () {
            var format = 'YYYY-MM-DD HH:mm:ss';
            var locale = 'pl';

            return {
                restrict: 'A',
                require:  'ngModel',
                link:     function (scope, element, attributes, ctrl) {
                    element.datetimepicker({
                        format: format,
                        locale: locale
                    });

                    element.on('dp.change', function (event) {
                        scope.$apply(function () {
                            ctrl.$setViewValue(event.date ? event.date.format(format) : null);
                        });
                    });
                }
            };
        });

})();