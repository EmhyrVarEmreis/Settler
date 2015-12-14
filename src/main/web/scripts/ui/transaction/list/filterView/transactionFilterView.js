(function () {
    'use strict';

    angular.module('settlerApplication')
        .directive('transactionFilterView', function () {
            return {
                scope: {
                    transactionFilter: '=',
                    applyFilters:      '&',
                    clearFilters:      '&'
                },
                restrict:     'E',
                templateUrl:  'scripts/ui/transaction/list/filterView/transactionFilterView.html',
                controller:   'TransactionFilterViewCtrl',
                controllerAs: 'transactionFilterVw'
            };
        });
})();