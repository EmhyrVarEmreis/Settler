(function () {
    'use strict';

    angular.module('settlerApplication').controller('TransactionsListCtrl', function ($scope, NgTableParams, transactionListFactory) {

        $scope.columns = [
            {
                "field":     'reference',
                "title":     'Numer transakcji',
                "isVisible": true,
                "filter":    true,
                "type":      'default',
                "isEmpty":   "N/D",
                "index":     0
            },
            {
                "field":     'type',
                "title":     'Typ transakcji',
                "isVisible": true,
                "filter":    true,
                "type":      'default',
                "isEmpty":   "N/D",
                "index":     1
            },
            {
                "field":     'owner',
                "title":     'Właściciel',
                "isVisible": true,
                "filter":    true,
                "type":      'default',
                "isEmpty":   "N/D",
                "index":     2
            },
            {
                "field":     'contractor',
                "title":     'Kontraktor',
                "isVisible": true,
                "filter":    true,
                "type":      'default',
                "isEmpty":   "N/D",
                "index":     3
            },
            {
                "field":     'value',
                "title":     'Wartość',
                "isVisible": true,
                "filter":    true,
                "type":      'default',
                "isEmpty":   "N/D",
                "index":     4
            },
            {
                "field":     'created',
                "title":     'Utworzono',
                "isVisible": true,
                "filter":    true,
                "type":      'date',
                "isEmpty":   "N/D",
                "index":     5
            },
            {
                "field":     'confirmed',
                "title":     'Potwierdzono',
                "isVisible": true,
                "filter":    true,
                "type":      'date',
                "isEmpty":   "N/D",
                "index":     6
            },
            {
                "field":     'evaluated',
                "title":     'Ewaluowano',
                "isVisible": true,
                "filter":    true,
                "type":      'date',
                "isEmpty":   "N/D",
                "index":     7
            }
        ];

        $scope.transactionListFactory = transactionListFactory;

    });
})();