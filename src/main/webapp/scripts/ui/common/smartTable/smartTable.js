(function() {
    'use strict';

    angular.module('settlerApplication')
        .directive('smartTable', function(NgTableParams, $location) {
            return {
                scope:       {
                    name:             "=",
                    factory:          '=',
                    page:             '=',
                    count:            '=',
                    defaultUrl:       '=',
                    sorting:          '=',
                    columns:          '=',
                    showFilters:      '=',
                    showVisibility:   '=',
                    applyFilters:     '=',
                    clearFilters:     '=',
                    toggleFilters:    '=',
                    toggleVisibility: "=",
                    emptyTableString: "="
                },
                transclude:  true,
                restrict:    'E',
                templateUrl: 'scripts/ui/common/smartTable/smartTable.html',
                link:        function($scope) {

                    $scope.filters = {};
                    $scope.data = {};

                    $scope.tableParams = new NgTableParams({
                        sorting: $scope.sorting,
                        page:    $scope.page,
                        count:   $scope.count
                    }, {
                        getData: function(params) {
                            if (!$scope.factory) {
                                params.total(0);
                                return null;
                            }
                            return $scope.factory.get({
                                page:    params.page(),
                                limit:   params.count(),
                                sortBy:  params.orderBy(),
                                filters: $scope.removeEmptyFilters($scope.filters)
                            }).$promise.then(function(data) {
                                params.total(data.total);
                                $scope.retrieveData(data.content);
                                return data.content;
                            });
                        }
                    });

                    $scope.retrieveData = function(data) {
                        $scope.data = {};
                        for (var entry in data) {
                            if (data.hasOwnProperty(entry)) {
                                var v = {};
                                for (var key in $scope.columns) {
                                    if ($scope.columns.hasOwnProperty(key)) {
                                        v[$scope.columns[key].field] = $scope.getField($scope.columns[key].field, data[entry]);
                                    }
                                }
                                $scope.data[entry] = v;
                            }
                        }
                    };

                    $scope.removeEmptyFilters = function(filters) {
                        for (var i in filters) {
                            if (filters.hasOwnProperty(i) && (filters[i] === null || filters[i] === undefined || filters[i] === '')) {
                                delete filters[i];
                            }
                        }
                        return filters;
                    };

                    $scope.applyFilters = function() {
                        $scope.tableParams.page(1);
                        $scope.tableParams.reload();
                    };

                    $scope.clearFilters = function() {
                        $scope.transactionFilter = {};
                        $scope.applyFilters();
                    };

                    $scope.toggleFilters = function() {
                        $scope.showFilters = !$scope.showFilters;
                    };

                    $scope.toggleVisibility = function() {
                        $scope.showVisibility = !$scope.showVisibility;
                    };

                    $scope.getVisibleColumnsCount = function() {
                        var i = 0;
                        $scope.columns.forEach(function(column) {
                            if (column.isVisible) {
                                i++;
                            }
                        });
                        return i;
                    };

                    $scope.getUrl = function(url, model) {
                        var reg = /(:[a-zA-Z]+)/g;
                        var matches = [], found;
                        while (found = reg.exec(url)) {
                            matches.push(found[0]);
                            reg.lastIndex -= found[0].split(':')[1].length;
                        }
                        matches.forEach(function(match) {
                            url = url.replace(match, model[match.substring(1)]);
                        });
                        return url;
                    };

                    $scope.getField = function(field, model) {
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

                    $scope.isArray = function(field) {
                        return field && Array.isArray(field);
                    };

                    $scope.navigateToUrl = function(model) {
                        if ($scope.defaultUrl) {
                            var url = $scope.getUrl($scope.defaultUrl, model);
                            if (url.substring(0, 2) === '#/') {
                                url = url.substring(2, url.length);
                            }
                            $location.path(url);
                        }
                    };

                    $scope.getCssClasses = function(column, tableParams) {
                        return angular.extend({}, $scope.getCssClassesTableHeaders(column, tableParams), $scope.getCssClassesHide(column));
                    };

                    $scope.getCssClassesTableHeaders = function(column, tableParams) {
                        return {
                            'sort-asc':  tableParams.isSortBy(column.sortField ? column.filterField : column.field, 'asc'),
                            'sort-desc': tableParams.isSortBy(column.sortField ? column.filterField : column.field, 'desc')
                        };
                    };

                    $scope.getCssClassesHide = function(column) {
                        return {
                            'hidden-xs': $scope.getHideOn(column).xs,
                            'hidden-sm': $scope.getHideOn(column).sm
                        };
                    };

                    $scope.isFilterable = function(column) {
                        return !!(column.filter || column.filterable);
                    };

                    $scope.getHideOn = function(column) {
                        return column.hideOn ? column.hideOn : {};
                    };

                    $scope.getTransformedEntry = function(value, column, entry) {
                        if (column.transform) {
                            return column.transform(value, entry);
                        } else {
                            return value;
                        }
                    };

                    $scope.tableParamsSorting = function(tableParams, column) {
                        if (column.sortable) {
                            tableParams.sorting(column.sortField ? column.sortField : column.field, tableParams.isSortBy(column.sortField ? column.sortField : column.field, 'asc') ? 'desc' : 'asc');
                        }
                    };

                }
            };
        });

})();