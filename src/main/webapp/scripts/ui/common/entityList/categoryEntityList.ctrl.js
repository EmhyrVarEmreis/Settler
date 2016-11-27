(function () {
    'use strict';

    angular.module('settlerApplication').factory('categoryEntityListFactory', function (categoryListFactory) {
        var object = {};

        object.columnsCount = 0;
        //object.defaultUrl = '#/category/details/:id';
        object.defaultSorting = {id: 'asc'};
        object.count = 25;
        object.page = 1;
        object.emptyString = 'Brak wyników spełniających podane kryteria';
        object.title = 'Lista kategorii';

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
                field:      'code',
                title:      'Kod',
                isVisible:  true,
                filterable: true,
                sortable:   true,
                hideOn:     {xs: false},
                type:       'default',
                isEmpty:    'N/D',
                index:      object.columnsCount++
            },
            {
                field:      'description',
                title:      'Opis',
                isVisible:  true,
                filterable: true,
                sortable:   true,
                hideOn:     {xs: false},
                type:       'default',
                isEmpty:    'N/D',
                index:      object.columnsCount++
            }
        ];

        object.listFactory = categoryListFactory;

        return object;

    });

})();