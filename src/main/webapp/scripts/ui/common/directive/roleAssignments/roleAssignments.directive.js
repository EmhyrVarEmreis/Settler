(function () {
    'use strict';

    angular.module('settlerApplication')
        .directive('roleAssignmentsDirective', function (roleAssignmentsByTargetFactory, $uibModal) {
            return {
                scope:       {
                    targetId: "="
                },
                restrict:    'E',
                templateUrl: 'scripts/ui/common/directive/roleAssignments/roleAssignments.directive.html',
                link:        function ($scope) {

                    $scope.roleAssignments = null;

                    $scope.load = function () {
                        roleAssignmentsByTargetFactory.query({
                            id: $scope.targetId
                        }).$promise.then(function (data) {
                            $scope.roleAssignments = data;
                        });
                    };

                    $scope.openAddModal = function () {
                        var modalInstance = $uibModal.open({
                            animation:    true,
                            templateUrl:  'scripts/ui/common/directive/roleAssignments/add/addRoleAssignment.html',
                            controller:   'AddCommentCtrl',
                            controllerAs: '$ctrl'
                        });

                        modalInstance.result.then(function (item) {

                        }, function () {
                        });
                    };

                    $scope.openDeleteModal = function (roleAssignment) {
                        var modalInstance = $uibModal.open({
                            animation:    true,
                            templateUrl:  'scripts/ui/common/directive/roleAssignments/delete/deleteRoleAssignment.html',
                            controller:   'DeleteCommentCtrl',
                            controllerAs: '$ctrl'
                        });

                        modalInstance.result.then(function (item) {

                        }, function () {
                        });
                    };

                }
            };
        });

})();