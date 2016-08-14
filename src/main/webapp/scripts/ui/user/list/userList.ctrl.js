(function () {
    'use strict';

    angular.module('settlerApplication').controller('UserListCtrl', function ($scope,
                                                                              NgTableParams,
                                                                              userListFactory) {

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
                field:     'login',
                title:     'Login',
                isVisible: true,
                filter:    true,
                type:      'default',
                isEmpty:   'N/D',
                index:     $scope.columnsCount++,
                url:       '#/user/details/:id'
            },
            {
                field:     'firstName',
                title:     'Imię',
                isVisible: true,
                filter:    true,
                type:      'default',
                isEmpty:   'N/D',
                index:     $scope.columnsCount++
            },
            {
                field:     'lastName',
                title:     'Nazwisko',
                isVisible: true,
                filter:    true,
                type:      'default',
                isEmpty:   'N/D',
                index:     $scope.columnsCount++
            },
            {
                field:     'email',
                title:     'Adres e-mail',
                isVisible: true,
                filter:    true,
                type:      'default',
                isEmpty:   'N/D',
                index:     $scope.columnsCount++
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
                index:       $scope.columnsCount++
            },
            {
                field:       'created',
                title:       'Utworzony',
                isVisible:   true,
                filter:      true,
                type:        'date',
                isEmpty:     'N/D',
                placeholder: 'Data utworzenia',
                index:       $scope.columnsCount++
            }
        ];

        $scope.userListFactory = userListFactory;

    });
})();