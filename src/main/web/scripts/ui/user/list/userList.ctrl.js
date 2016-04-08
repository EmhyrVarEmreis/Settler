(function () {
    'use strict';

    angular.module('settlerApplication').controller('UserListCtrl', function ($scope, NgTableParams, userListFactory) {

        $scope.columns = [
            {
                field:     'login',
                title:     'Login',
                isVisible: true,
                filter:    true,
                type:      'default',
                isEmpty:   'N/D',
                index:     0,
                url:       '#/user/details/:id'
            },
            {
                field:     'firstName',
                title:     'Imię',
                isVisible: true,
                filter:    true,
                type:      'default',
                isEmpty:   'N/D',
                index:     1
            },
            {
                field:     'lastName',
                title:     'Nazwisko',
                isVisible: true,
                filter:    true,
                type:      'default',
                isEmpty:   'N/D',
                index:     2
            },
            {
                field:     'email',
                title:     'Adres e-mail',
                isVisible: true,
                filter:    true,
                type:      'default',
                isEmpty:   'N/D',
                index:     3
            },
            {
                field:       'status',
                title:       'Status',
                filterField: 'status',
                sortField:   'status',
                isVisible:   true,
                filter:      true,
                type:        'default',
                isEmpty:     'N/D',
                index:       4
            },
            {
                field:       'created',
                title:       'Utworzony',
                isVisible:   true,
                filter:      true,
                type:        'date',
                isEmpty:     'N/D',
                placeholder: 'Data utworzenia',
                index:       5
            }
        ];

        $scope.userListFactory = userListFactory;

    });
})();