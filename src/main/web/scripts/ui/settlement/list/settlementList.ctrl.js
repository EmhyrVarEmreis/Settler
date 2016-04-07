(function () {
    'use strict';

    angular.module('settlerApplication').controller('SettlementListCtrl', function ($scope,
                                                                                    NgTableParams,
                                                                                    settlementListFactory) {
        $scope.columnsCount = 0;
        $scope.columns = [
            {
                field:     'reference',
                title:     'Numer rozliczenia',
                isVisible: true,
                filter:    true,
                type:      'default',
                isEmpty:   "N/D",
                index:     $scope.columnsCount++,
                url:       '#/transaction/details/:id'
            },
            {
                field:     'type',
                title:     'Typ rozliczenia',
                isVisible: true,
                filter:    true,
                type:      'default',
                isEmpty:   "N/D",
                index:     $scope.columnsCount++
            },
            {
                field:     'creator',
                title:     'Twórca',
                isVisible: true,
                filter:    true,
                type:      'default',
                isEmpty:   "N/D",
                index:     $scope.columnsCount++
            },
            {
                field:     'owner',
                title:     'Właściciel',
                isVisible: true,
                filter:    true,
                type:      'default',
                isEmpty:   "N/D",
                index:     $scope.columnsCount++
            },
            {
                field:     'contractor',
                title:     'Kontraktor',
                isVisible: true,
                filter:    true,
                type:      'default',
                isEmpty:   "N/D",
                index:     $scope.columnsCount++
            },
            {
                field:     'balance',
                title:     'Bilans',
                isVisible: true,
                filter:    true,
                type:      'default',
                isEmpty:   "N/D",
                index:     $scope.columnsCount++
            },
            {
                field:     'transactions',
                title:     'Transakcje',
                isVisible: true,
                filter:    true,
                type:      'default',
                isEmpty:   "Brak",
                index:     $scope.columnsCount++
            },
            {
                field:     'created',
                title:     'Utworzono',
                isVisible: true,
                filter:    true,
                type:      'date',
                isEmpty:   "N/D",
                index:     $scope.columnsCount++
            },
            {
                field:     'evaluated',
                title:     'Ewaluowano',
                isVisible: true,
                filter:    true,
                type:      'date',
                isEmpty:   "N/D",
                index:     $scope.columnsCount++
            },
            {
                field:     'startDate',
                title:     'Od',
                isVisible: true,
                filter:    true,
                type:      'date',
                isEmpty:   "N/D",
                index:     $scope.columnsCount++
            },
            {
                field:     'endDate',
                title:     'Do',
                isVisible: true,
                filter:    true,
                type:      'date',
                isEmpty:   "N/D",
                index:     $scope.columnsCount++
            },
            {
                field:     'comments',
                title:     'Komentarze',
                isVisible: true,
                filter:    true,
                type:      'default',
                isEmpty:   "Brak",
                index:     $scope.columnsCount++
            }
        ];

        $scope.settlementListFactory = settlementListFactory;

    });
})();