(function () {
    'use strict';

    angular.module('settlerApplication')
        .directive('userFilterView', function () {
            return {
                scope: {
                    userFilter:   '=',
                    applyFilters: '&',
                    clearFilters: '&'
                },
                restrict:     'E',
                templateUrl:  'scripts/ui/user/list/filterView/userFilterView.html',
                controller:   'UserFilterViewCtrl',
                controllerAs: 'userFilterVw'
            };
        });
})();