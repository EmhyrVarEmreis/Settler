(function () {
    'use strict';

    angular.module('settlerApplication')
        .directive('commentsDirective', function (commentByObjectIdFactory) {
            return {
                scope:       {
                    id: "="
                },
                restrict:    'E',
                templateUrl: 'scripts/ui/common/directive/comments/commentsDirective.html',
                link:        function ($scope) {

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