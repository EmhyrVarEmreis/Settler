(function () {
    'use strict';

    angular.module('settlerApplication')
        .directive('userPicker', function (userSearchSimpleFactory) {
            return {
                scope:       {
                    ngModel:     "=",
                    placeholder: "@"
                },
                restrict:    'E',
                templateUrl: 'scripts/ui/common/directive/userPicker/userPicker.html',
                link:        function ($scope) {

                    $scope.userList = [];

                    console.log($scope.placeholder);

                    $scope.refreshUserList = function (input) {
                        if (input == null || input.length < 3) {
                            return [];
                        }
                        userSearchSimpleFactory.query({
                            limit:  10,
                            string: input
                        }).$promise.then(function (result) {
                            $scope.userList = result;
                            return $scope.userList;
                        });
                    };

                }
            };
        });

})();