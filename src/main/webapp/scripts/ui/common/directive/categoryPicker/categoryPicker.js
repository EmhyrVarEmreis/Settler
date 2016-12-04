(function () {
    'use strict';

    angular.module('settlerApplication')
        .directive('categoryPicker', function (categorySearchSimpleFactory) {
            return {
                scope:       {
                    ngModel: "="
                },
                restrict:    'E',
                templateUrl: 'scripts/ui/common/directive/categoryPicker/categoryPicker.html',
                link:        function ($scope) {

                    $scope.categoryList = [];

                    $scope.refreshCategoryList = function (input) {
                        if (input == null) {
                            input = '';
                        }
                        categorySearchSimpleFactory.query({
                            limit:  10,
                            string: input
                        }).$promise.then(function (result) {
                            $scope.categoryList = result;
                            return $scope.categoryList;
                        });
                    };

                }
            };
        });

})();