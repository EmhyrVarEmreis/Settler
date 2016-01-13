(function () {
    'use strict';

    angular.module('settlerApplication')
        .directive('smartTable', function (NgTableParams) {
            return {
                scope:       {
                    name:             "@",
                    factory:          '=',
                    page:             '@',
                    count:            '@',
                    sorting:          '=',
                    columns:          '=',
                    showFilters:      '=',
                    showVisibility:   '=',
                    applyFilters:     '=',
                    clearFilters:     '=',
                    toggleFilters:    '=',
                    toggleVisibility: "=",
                    emptyTableString: "@"
                },
                transclude:  true,
                restrict:    'E',
                templateUrl: 'scripts/ui/common/smartTable/smartTable.html',
                link:        function (scope) {

                    scope.filters = {};

                    scope.tableParams = new NgTableParams({
                        page:    scope.page,
                        count:   scope.count,
                        sorting: scope.sorting
                    }, {
                        counts:  [],
                        total:   0,
                        getData: function ($defer, params) {
                            scope.factory.get(
                                {
                                    "page":    params.page(),
                                    "limit":   params.count(),
                                    "sortBy":  params.orderBy(),
                                    "filters": scope.filters
                                }, function (data) {
                                    params.total(data.total);
                                    $defer.resolve(data.content);
                                });
                        }
                    });

                    scope.applyFilters = function () {
                        scope.tableParams.page(1);
                        scope.tableParams.reload();
                    };

                    scope.clearFilters = function () {
                        scope.transactionFilter = {};
                        scope.applyFilters();
                    };

                    scope.toggleFilters = function () {
                        scope.showFilters = !scope.showFilters;
                    };

                    scope.toggleVisibility = function () {
                        scope.showVisibility = !scope.showVisibility;
                    };

                    scope.getVisibleColumnsCount = function () {
                        var i = 0;
                        scope.columns.forEach(function (column) {
                            if (column.isVisible) {
                                i++;
                            }
                        });
                        return i;
                    };

                    scope.getUrl = function (url, model) {
                        var reg = /(:[a-zA-Z]+)/g;
                        var matches = [], found;
                        while (found = reg.exec(url)) {
                            matches.push(found[0]);
                            reg.lastIndex -= found[0].split(':')[1].length;
                        }
                        matches.forEach(function (match) {
                            url = url.replace(match, model[match.substring(1)]);
                        });
                        return url;
                    };

                    scope.getField = function (field, model) {
                        if (field.indexOf('.') === -1) {
                            return model[field];
                        } else {
                            var a = model;
                            while (field.indexOf('.') !== -1) {
                                a = a[field.substring(0, field.indexOf('.') !== -1 ? field.indexOf('.') : field.length)];
                                field = field.substring(field.indexOf('.') + 1);
                            }
                            a = a[field];
                            return a;
                        }

                    };

                }
            };
        });
})();