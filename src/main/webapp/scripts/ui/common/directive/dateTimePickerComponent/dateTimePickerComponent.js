(function () {
    'use strict';

    angular.module('settlerApplication')
        .directive('dateTimePickerComponent', function () {
            var format = 'YYYY-MM-DD HH:mm:ss';
            var locale = 'pl';
            return {
                scope:       {
                    ngModel: "=",
                    label:   "@",
                    name:    "@"
                },
                restrict:    'E',
                templateUrl: 'scripts/ui/common/directive/dateTimePickerComponent/dateTimePickerComponent.html',
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