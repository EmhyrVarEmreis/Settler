(function() {
    'use strict';

    angular.module('settlerServices')
        .factory('Principal', function($q, AccountCli) {
            var _identity,
                _promise,
                _authenticated = false;

            return {
                isIdentityResolved: function() {
                    return angular.isDefined(_identity);
                },
                isAuthenticated:    function() {
                    return _authenticated;
                },
                isInRole:           function(role) {
                    if (!_authenticated || !_identity.roles) {
                        return false;
                    }

                    return _identity.roles.indexOf(role) !== -1;
                },
                isInAnyRole:        function(roles) {
                    if (!_authenticated || !_identity.roles) {
                        return false;
                    }

                    for (var i = 0; i < roles.length; i++) {
                        if (this.isInRole(roles[i])) {
                            return true;
                        }
                    }

                    return false;
                },
                authenticate:       function(identity) {
                    _identity = identity;
                    _authenticated = identity !== null;
                },
                identity:           function(force) {

                    if (force === true) {
                        _identity = undefined;
                        _promise = undefined;
                    }

                    if (_promise !== null && _promise !== undefined) return _promise;

                    var deferred = $q.defer();
                    _promise = deferred.promise;

                    AccountCli.get().$promise
                        .then(function(account) {
                            _identity = account.data;
                            console.log(_identity);
                            if (_identity.roles === null || _identity.roles === undefined) _identity.roles = [];
                            _identity.roles.push('authenticated');
                            _authenticated = true;
                            deferred.resolve(_identity);
                        })
                        .catch(function() {
                            _identity = null;
                            _authenticated = false;
                            deferred.resolve(_identity);
                        });
                    return deferred.promise;
                }
            };
        });

})();