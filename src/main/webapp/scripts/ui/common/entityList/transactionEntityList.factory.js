(function() {
    'use strict';

    angular.module('settlerApplication').factory('transactionEntityListFactory', function(transactionListFactory) {
        var object = {};

        object.columnsCount = 0;
        object.defaultUrl = '#/transaction/details/:id';
        object.defaultSorting = {id: 'asc'};
        object.count = 25;
        object.page = 1;
        object.emptyString = 'Brak wyników spełniających podane kryteria';
        object.title = 'Lista transakcji';

        object.columns = [
            {
                field:      'id',
                title:      'ID',
                isVisible:  false,
                filterable: false,
                sortable:   true,
                hideOn:     {xs: true},
                type:       'default',
                isEmpty:    'N/D',
                index:      object.columnsCount++
            },
            {
                field:      'reference',
                title:      '<span class="glyphicon glyphicon-barcode" aria-hidden="true"></span>',
                isVisible:  true,
                filterable: true,
                sortable:   true,
                hideOn:     {xs: false},
                type:       'default',
                isEmpty:    "N/D",
                index:      object.columnsCount++,
                url:        '#/transaction/details/:id'
            },
            {
                field:      'type',
                title:      '<span class="glyphicon glyphicon-tags" aria-hidden="true"></span>',
                isVisible:  true,
                filterable: true,
                sortable:   true,
                hideOn:     {xs: true},
                type:       'default',
                isEmpty:    "N/D",
                index:      object.columnsCount++
            },
            {
                field:      'description',
                title:      '<span class="glyphicon glyphicon-info-sign" aria-hidden="true"></span>',
                isVisible:  true,
                filterable: true,
                sortable:   true,
                hideOn:     {xs: false},
                type:       'default',
                isEmpty:    "N/D",
                index:      object.columnsCount++
            },
            {
                field:      'creator',
                title:      '<span class="glyphicon glyphicon-user" aria-hidden="true"></span>',
                isVisible:  true,
                filterable: true,
                sortable:   true,
                hideOn:     {},
                type:       'default',
                isEmpty:    "N/D",
                index:      object.columnsCount++
            },
            {
                field:      'owners',
                title:      '<span class="glyphicon glyphicon-hand-up" aria-hidden="true"></span>',
                isVisible:  true,
                filterable: true,
                sortable:   true,
                hideOn:     {xs: true},
                type:       'default',
                isEmpty:    "N/D",
                index:      object.columnsCount++
            },
            {
                field:      'contractors',
                title:      '<span class="glyphicon glyphicon-hand-down" aria-hidden="true"></span>',
                isVisible:  true,
                filterable: true,
                sortable:   true,
                hideOn:     {xs: true},
                type:       'default',
                isEmpty:    "N/D",
                index:      object.columnsCount++
            },
            {
                field:      'value',
                title:      '<span class="glyphicon glyphicon-usd" aria-hidden="true"></span>',
                isVisible:  true,
                filterable: true,
                sortable:   true,
                hideOn:     {},
                type:       'money',
                isEmpty:    "N/D",
                index:      object.columnsCount++
            },
            {
                field:      'created',
                title:      '<span class="glyphicon glyphicon-time" aria-hidden="true"></span>',
                isVisible:  true,
                filterable: true,
                sortable:   true,
                hideOn:     {},
                type:       'date',
                isEmpty:    "N/D",
                index:      object.columnsCount++
            },
            {
                field:      'evaluated',
                title:      '<span class="glyphicon glyphicon-thumbs-up" aria-hidden="true"></span>',
                isVisible:  true,
                filterable: true,
                sortable:   false,
                hideOn:     {xs: true},
                type:       'boolean',
                isEmpty:    "N/D",
                index:      object.columnsCount++
            },
            {
                field:      'comments',
                title:      '<span class="glyphicon glyphicon-comment" aria-hidden="true"></span>',
                isVisible:  true,
                filterable: true,
                sortable:   true,
                hideOn:     {xs: true},
                type:       'default',
                isEmpty:    "Brak",
                index:      object.columnsCount++
            }
        ];

        object.listFactory = transactionListFactory;

        return object;
    });

})();