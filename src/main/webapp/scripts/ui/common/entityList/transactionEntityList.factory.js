(function() {
    'use strict';

    angular.module('settlerApplication').factory('transactionEntityListFactory', function(transactionListFactory) {
        var object = {};

        object.columnsCount = 0;
        object.defaultUrl = '#/transaction/details/:id';
        object.defaultSorting = {evaluated: 'desc'};
        object.count = 10;
        object.page = 1;
        object.emptyString = 'Brak wyników spełniających podane kryteria';
        object.title = 'Lista transakcji';
        object.newEntityButton = 'Nowa transakcja';
        object.newEntityButtonUrl = '#/transaction/details/new';

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
                field:       'reference',
                title:       '<span class="glyphicon glyphicon-barcode" aria-hidden="true"></span>',
                placeholder: 'Numer',
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
                field:       'categories',
                title:       '<span class="glyphicon glyphicon-tags" aria-hidden="true"></span>',
                placeholder: 'Kategorie',
                isVisible:   true,
                filterable:  true,
                sortable:    false,
                hideOn:      {xs: true},
                type:        'default',
                isEmpty:     "N/D",
                index:       object.columnsCount++
            },
            {
                field:       'description',
                title:       '<span class="glyphicon glyphicon-info-sign" aria-hidden="true"></span>',
                placeholder: 'Opis',
                isVisible:   true,
                filterable:  true,
                sortable:    true,
                hideOn:      {xs: false},
                type:        'default',
                isEmpty:     "N/D",
                index:       object.columnsCount++
            },
            {
                field:       'creator',
                sortField:   'creator.login',
                title:       '<span class="glyphicon glyphicon-user" aria-hidden="true"></span>',
                placeholder: 'Twórca',
                isVisible:   true,
                filterable:  true,
                sortable:    true,
                hideOn:      {},
                type:        'default',
                isEmpty:     "N/D",
                index:       object.columnsCount++
            },
            {
                field:       'owners',
                title:       '<span class="glyphicon glyphicon-hand-up" aria-hidden="true"></span>',
                placeholder: 'Właściciel',
                isVisible:   true,
                filterable:  true,
                sortable:    false,
                hideOn:      {xs: true},
                type:        'default',
                isEmpty:     "N/D",
                transform:   function(data, entry) {
                    return data.user + ' (' + parseFloat(data.percentage.toString().replace(/,/, '.')).toFixed(0) + '%)';
                },
                index:       object.columnsCount++
            },
            {
                field:       'contractors',
                title:       '<span class="glyphicon glyphicon-hand-down" aria-hidden="true"></span>',
                placeholder: 'Kontraktor',
                isVisible:   true,
                filterable:  true,
                sortable:    false,
                hideOn:      {xs: true},
                type:        'default',
                isEmpty:     "N/D",
                transform:   function(data, entry) {
                    return data.user + ' (' + parseFloat(data.percentage.toString().replace(/,/, '.')).toFixed(0) + '%)';
                },
                index:       object.columnsCount++
            },
            {
                field:       'value',
                title:       '<span class="glyphicon glyphicon-usd" aria-hidden="true"></span>',
                placeholder: 'Wartość',
                isVisible:   true,
                filterable:  true,
                sortable:    true,
                hideOn:      {},
                type:        'money',
                isEmpty:     "N/D",
                index:       object.columnsCount++
            },
            {
                field:       'evaluated',
                title:       '<span class="glyphicon glyphicon-time" aria-hidden="true"></span>',
                placeholder: 'Ewaluowana',
                isVisible:   true,
                filterable:  true,
                sortable:    true,
                hideOn:      {},
                type:        'date',
                isEmpty:     "N/D",
                index:       object.columnsCount++
            },
            {
                field:       'comments',
                sortField:   'comments.size',
                title:       '<span class="glyphicon glyphicon-comment" aria-hidden="true"></span>',
                placeholder: 'Komentarze',
                isVisible:   true,
                filterable:  true,
                sortable:    false,
                hideOn:      {xs: true},
                type:        'default',
                isEmpty:     "Brak",
                index:       object.columnsCount++
            }
        ];

        object.listFactory = transactionListFactory;

        return object;
    });

})();