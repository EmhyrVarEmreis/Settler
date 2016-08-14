(function () {
    'use strict';

    angular.module('settlerApplication').controller('TransactionListCtrl', function ($scope,
                                                                                     NgTableParams,
                                                                                     transactionListFactory) {

        $scope.columnsCount = 0;
        $scope.columns = [
            {
                field:     'id',
                title:     'ID',
                isVisible: false,
                filter:    false,
                type:      'default',
                isEmpty:   'N/D',
                index:     $scope.columnsCount++
            },
            {
                field:     'reference',
                title:     'Numer transakcji',
                isVisible: true,
                filter:    true,
                type:      'default',
                isEmpty:   "N/D",
                index:     $scope.columnsCount++,
                url:       '#/transaction/details/:id'
            },
            {
                field:     'type',
                title:     'Typ transakcji',
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
                field:     'owners',
                title:     'Właściciel',
                isVisible: true,
                filter:    true,
                type:      'default',
                isEmpty:   "N/D",
                index:     $scope.columnsCount++
            },
            {
                field:     'contractors',
                title:     'Kontraktor',
                isVisible: true,
                filter:    true,
                type:      'default',
                isEmpty:   "N/D",
                index:     $scope.columnsCount++
            },
            {
                field:     'value',
                title:     'Wartość',
                isVisible: true,
                filter:    true,
                type:      'money',
                isEmpty:   "N/D",
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
                field:     'confirmed',
                title:     'Potwierdzono',
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
                field:     'comments',
                title:     'Komentarze',
                isVisible: true,
                filter:    true,
                type:      'default',
                isEmpty:   "Brak",
                index:     $scope.columnsCount++
            }
        ];

        $scope.transactionListFactory = transactionListFactory;

    });
})();