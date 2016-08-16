(function() {
    'use strict';

    angular.module('settlerApplication').factory('settlementEntityListFactory', function(settlementListFactory) {
        var object = {};

        object.columnsCount = 0;
        object.defaultUrl = '#/settlement/details/:id';
        object.defaultSorting = {id: 'asc'};
        object.count = 25;
        object.page = 1;
        object.emptyString = 'Brak wyników spełniających podane kryteria';
        object.title = 'Lista rozliczeń';

        object.columns = [
            {
                field:       'id',
                title:       'ID',
                description: 'User ID',
                placeholder: 'User ID',
                isVisible:   false,
                filterable:  false,
                sortable:    true,
                hideOn:      {xs: true},
                type:        'default',
                isEmpty:     'N/D',
                index:       object.columnsCount++
            },
            {
                field:       'reference',
                title:       '<span class="glyphicon glyphicon-barcode" aria-hidden="true"></span>',
                description: 'Numer rozliczenia',
                placeholder: 'Numer rozliczenia',
                isVisible:   true,
                filterable:  true,
                sortable:    true,
                hideOn:      {xs: false},
                type:        'default',
                isEmpty:     "N/D",
                index:       object.columnsCount++,
                url:         '#/transaction/details/:id'
            },
            {
                field:       'type',
                title:       '<span class="glyphicon glyphicon-tags" aria-hidden="true"></span>',
                description: 'Typ rozliczenia',
                placeholder: 'Typ rozliczenia',
                isVisible:   true,
                filterable:  true,
                sortable:    true,
                hideOn:      {xs: true, sm: true},
                type:        'default',
                isEmpty:     "N/D",
                index:       object.columnsCount++
            },
            {
                field:       'creator',
                title:       '<span class="glyphicon glyphicon-user" aria-hidden="true"></span>',
                description: 'Twórce',
                placeholder: 'Twórce',
                isVisible:   true,
                filterable:  true,
                sortable:    true,
                hideOn:      {xs: true, sm: true},
                type:        'default',
                isEmpty:     "N/D",
                index:       object.columnsCount++
            },
            {
                field:       'owner',
                title:       '<span class="glyphicon glyphicon-hand-up" aria-hidden="true"></span>',
                description: 'User',
                placeholder: 'User',
                isVisible:   true,
                filterable:  true,
                sortable:    true,
                hideOn:      {xs: false},
                type:        'default',
                isEmpty:     "B/D",
                index:       object.columnsCount++
            },
            {
                field:       'contractor',
                title:       '<span class="glyphicon glyphicon-hand-down" aria-hidden="true"></span>',
                description: 'Kontraktor',
                placeholder: 'Kontraktor',
                isVisible:   true,
                filterable:  true,
                sortable:    true,
                hideOn:      {xs: false},
                type:        'default',
                isEmpty:     "N/D",
                index:       object.columnsCount++
            },
            {
                field:       'balance',
                title:       '<span class="glyphicon glyphicon-usd" aria-hidden="true"></span>',
                description: 'Bilans',
                placeholder: 'Bilans',
                isVisible:   true,
                filterable:  true,
                sortable:    true,
                hideOn:      {xs: false},
                type:        'default',
                isEmpty:     "N/D",
                index:       object.columnsCount++
            },
            {
                field:       'transactions',
                title:       '<span class="glyphicon glyphicon-list-alt" aria-hidden="true"></span>',
                description: 'Ilosć transakcji',
                placeholder: 'Ilosć transakcji',
                isVisible:   true,
                filterable:  true,
                sortable:    true,
                hideOn:      {xs: true, sm: true},
                type:        'default',
                isEmpty:     "Brak",
                index:       object.columnsCount++
            },
            {
                field:       'created',
                title:       '<span class="glyphicon glyphicon-time" aria-hidden="true"></span>',
                description: 'Utworzono',
                placeholder: 'Utworzono',
                isVisible:   true,
                filterable:  true,
                sortable:    true,
                hideOn:      {xs: true},
                type:        'date',
                isEmpty:     "N/D",
                index:       object.columnsCount++
            },
            {
                field:       'evaluated',
                title:       '<span class="glyphicon glyphicon-thumbs-up" aria-hidden="true"></span>',
                description: 'Ewaluowano',
                placeholder: 'Ewaluowano',
                isVisible:   true,
                filterable:  true,
                sortable:    true,
                hideOn:      {xs: true, sm: true},
                type:        'boolean',
                isEmpty:     "N/D",
                index:       object.columnsCount++
            },
            {
                field:       'startDate',
                title:       '<span class="glyphicon glyphicon-arrow-right" aria-hidden="true"></span>',
                description: 'Od',
                placeholder: 'Od',
                isVisible:   true,
                filterable:  true,
                sortable:    true,
                hideOn:      {xs: true},
                type:        'date',
                isEmpty:     "N/D",
                index:       object.columnsCount++
            },
            {
                field:       'endDate',
                title:       '<span class="glyphicon glyphicon-arrow-left" aria-hidden="true"></span>',
                description: 'Do',
                placeholder: 'Do',
                isVisible:   true,
                filterable:  true,
                sortable:    true,
                hideOn:      {xs: true},
                type:        'date',
                isEmpty:     "N/D",
                index:       object.columnsCount++
            },
            {
                field:       'comments',
                title:       '<span class="glyphicon glyphicon-comment" aria-hidden="true"></span>',
                description: 'Ilość komentarzy',
                placeholder: 'Ilość komentarzy',
                isVisible:   true,
                filterable:  true,
                sortable:    true,
                hideOn:      {xs: true},
                type:        'default',
                isEmpty:     "Brak",
                index:       object.columnsCount++
            }
        ];

        object.listFactory = settlementListFactory;

        return object;
    });

})();