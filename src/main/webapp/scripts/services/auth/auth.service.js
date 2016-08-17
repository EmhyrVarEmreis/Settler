(function() {
    'use strict';

    angular.module('settlerServices')
        .factory('Auth', function Auth(Principal, AuthenticateCli, localStorageService, $rootScope, $state, $q/*, Account, Register, Activate, Password*/) {
            return {
                login: function(credentials) {
                    var deferred = $q.defer();

                    AuthenticateCli.login(credentials).$promise.then(function(data) {
                        localStorageService.set('token', data.token);
                        Principal.identity(true).then(function(identity) {
                            identity.passwordExpireDate = data.passwordExpireDate;
                            deferred.resolve(identity);
                        }).catch(function(err) {
                            deferred.reject(err);
                        });
                    }).catch(function(err) {
                        //this.logout();
                        deferred.reject(err);
                    });

                    return deferred.promise;
                },

                logout: function() {
                    localStorageService.remove('token');
                    Principal.authenticate(null);
                },

                authorize: function() {
                    return Principal.identity().then(function() {
                        if (!$rootScope.toState.data) return;
                        var isAuthenticated = Principal.isAuthenticated();
                        var siteRoles = $rootScope.toState.data.roles || [];

                        if (siteRoles && siteRoles.length > 0 && !Principal.isInAnyRole(siteRoles)) {
                            if (isAuthenticated) {
                                $state.go('accessdenied');
                            } else {
                                $state.go('login');
                            }
                        }
                    });
                }
            };
        });

})();