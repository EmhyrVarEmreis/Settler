(function() {
    'use strict';

    angular.module('settlerApplication').factory('userEntityListFactory', function(userListFactory) {
        var object = {};

        object.columnsCount = 0;
        object.defaultUrl = '#/user/details/:id';
        object.defaultSorting = {id: 'asc'};
        object.count = 25;
        object.page = 1;
        object.emptyString = 'Brak wyników spełniających podane kryteria';
        object.title = 'Lista użytkowników';

        object.columnsCount = 0;
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
                field:      'login',
                title:      'Login',
                isVisible:  true,
                filterable: true,
                sortable:   true,
                hideOn:     {xs: false},
                type:       'default',
                isEmpty:    'N/D',
                index:      object.columnsCount++,
                url:        '#/user/details/:id'
            },
            {
                field:      'firstName',
                title:      'Imię',
                isVisible:  true,
                filterable: true,
                sortable:   true,
                hideOn:     {xs: true},
                type:       'default',
                isEmpty:    'N/D',
                index:      object.columnsCount++
            },
            {
                field:      'lastName',
                title:      'Nazwisko',
                isVisible:  true,
                filterable: true,
                sortable:   true,
                hideOn:     {xs: true},
                type:       'default',
                isEmpty:    'N/D',
                index:      object.columnsCount++
            },
            {
                field:      'email',
                title:      'Adres e-mail',
                isVisible:  true,
                filterable: true,
                sortable:   true,
                hideOn:     {xs: true},
                type:       'default',
                isEmpty:    'N/D',
                index:      object.columnsCount++
            },
            {
                field:       'status',
                title:       'Status',
                filterField: 'status',
                sortField:   'status',
                isVisible:   true,
                filterable:  true,
                sortable:    true,
                hideOn:      {xs: false},
                type:        'default',
                isEmpty:     'N/D',
                index:       object.columnsCount++
            },
            {
                field:       'created',
                title:       'Utworzony',
                isVisible:   true,
                filterable:  true,
                sortable:    true,
                hideOn:      {xs: false},
                type:        'date',
                isEmpty:     'N/D',
                placeholder: 'Data utworzenia',
                index:       object.columnsCount++
            }
        ];

        object.listFactory = userListFactory;

        return object;

    });

})();