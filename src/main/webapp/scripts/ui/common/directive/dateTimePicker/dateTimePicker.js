(function () {
    'use strict';

    angular.module('settlerApplication')
        .directive('dateTimePicker', function () {
            var format = 'YYYY-MM-DD HH:mm:ss';
            var locale = 'pl';
            return {
                scope:       {
                    ngModel:     "=",
                    ngDisabled:  "=",
                    label:       "@",
                    name:        "@",
                    placeholder: "@"
                },
                restrict:    'E',
                templateUrl: 'scripts/ui/common/directive/dateTimePicker/dateTimePicker.html',
                link:        function ($scope, element) {

                    var elementDiv = element.find('div');
                    elementDiv.datetimepicker({
                        format: format,
                        locale: locale
                    });

                    elementDiv.on('dp.change', function (event) {
                        $scope.$apply(function () {
                            $scope.ngModel = (event.date ? event.date.format(format) : null);
                        });
                    });

                }
            };
        });

})();