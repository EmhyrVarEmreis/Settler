(function() {
    'use strict';

    angular.module('settlerApplication')
        .directive('commentsDirective', function(commentFactory, $uibModal) {
            return {
                scope:       {
                    id: "="
                },
                restrict:    'E',
                templateUrl: 'scripts/ui/common/directive/comments/commentsDirective.html',
                link:        function($scope) {

                    $scope.comments = null;

                    $scope.loadComments = function() {
                        commentFactory.query({
                            id: $scope.id
                        }).$promise.then(function(data) {
                            $scope.comments = [];
                            var map = {};
                            var i;
                            for (i = 0; i < data.length; i++) {
                                map[data[i].id] = data[i];
                            }
                            for (i = 0; i < data.length; i++) {
                                if (data[i].parentComment) {
                                    if (!map[data[i].parentComment].children) {
                                        map[data[i].parentComment].children = [];
                                    }
                                    map[data[i].parentComment].children.push(data[i]);
                                } else {
                                    $scope.comments.push(data[i]);
                                }
                            }
                        });
                    };

                    $scope.openAddCommentModal = function(parentComment) {
                        var modalInstance = $uibModal.open({
                            animation:    true,
                            templateUrl:  'scripts/ui/common/directive/comments/addComment/addComment.html',
                            controller:   'AddCommentCtrl',
                            controllerAs: '$ctrl'
                        });

                        modalInstance.result.then(function(item) {
                            commentFactory.save(
                                {
                                    object:        $scope.id,
                                    parentComment: parentComment,
                                    value:         item
                                },
                                function() {
                                    $scope.loadComments();
                                }, function(err) {
                                    console.log(err);
                                }
                            );
                        }, function() {
                        });
                    };

                }
            };
        });

})();