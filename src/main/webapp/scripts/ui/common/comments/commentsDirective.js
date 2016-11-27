(function () {
    'use strict';

    angular.module('settlerApplication')
        .directive('commentsDirective', function (commentByObjectIdFactory) {
            return {
                scope: {
                    id: "="
                },
                transclude: true,
                restrict: 'E',
                templateUrl: 'scripts/ui/common/comments/commentsDirective.html',
                link: function ($scope) {

                    $scope.comments = null;

                    $scope.loadComments = function () {
                        commentByObjectIdFactory.query({
                            id: $scope.id
                        }).$promise.then(function (data) {
                            $scope.comments = data;
                        });
                    };

                }
            };
        });

})();