(function () {
    'use strict';

    angular.module('settlerApplication').controller('SettlementListCtrl', function ($scope,
                                                                                    NgTableParams,
                                                                                    settlementListFactory) {

        $scope.columns = [
            {
                field:     'reference',
                title:     'Numer rozliczenia',
                isVisible: true,
                filter:    true,
                type:      'default',
                isEmpty:   "N/D",
                index:     0,
                url:       '#/transaction/details/:id'
            },
            {
                field:     'type',
                title:     'Typ rozliczenia',
                isVisible: true,
                filter:    true,
                type:      'default',
                isEmpty:   "N/D",
                index:     1
            },
            {
                field:     'owner',
                title:     'Właściciel',
                isVisible: true,
                filter:    true,
                type:      'default',
                isEmpty:   "N/D",
                index:     2
            },
            {
                field:     'contractor',
                title:     'Kontraktor',
                isVisible: true,
                filter:    true,
                type:      'default',
                isEmpty:   "N/D",
                index:     3
            },
            {
                field:     'balance',
                title:     'Bilans',
                isVisible: true,
                filter:    true,
                type:      'default',
                isEmpty:   "N/D",
                index:     4
            },
            {
                field:     'transactions',
                title:     'Transakcje',
                isVisible: true,
                filter:    true,
                type:      'default',
                isEmpty:   "Brak",
                index:     5
            },
            {
                field:     'created',
                title:     'Utworzono',
                isVisible: true,
                filter:    true,
                type:      'date',
                isEmpty:   "N/D",
                index:     6
            },
            {
                field:     'evaluated',
                title:     'Ewaluowano',
                isVisible: true,
                filter:    true,
                type:      'date',
                isEmpty:   "N/D",
                index:     7
            },
            {
                field:     'startDate',
                title:     'Od',
                isVisible: true,
                filter:    true,
                type:      'date',
                isEmpty:   "N/D",
                index:     8
            },
            {
                field:     'endDate',
                title:     'Do',
                isVisible: true,
                filter:    true,
                type:      'date',
                isEmpty:   "N/D",
                index:     9
            },
            {
                field:     'comments',
                title:     'Komentarze',
                isVisible: true,
                filter:    true,
                type:      'default',
                isEmpty:   "Brak",
                index:     10
            }
        ];

        $scope.settlementListFactory = settlementListFactory;

    });
})();