(function() {
    'use strict';

    angular.module('settlerServices')
        .factory('Auth', function Auth(Principal, AuthenticateCli, AuthenticateFbCli, localStorageService, $rootScope, $state, $q) {
            var _fbId;
            return {
                isToken: function() {
                    return !!localStorageService.get('token');
                },

                getFbId: function() {
                    if (!FB) {
                        console.log("Facebook (FB) is not initialized");
                        return '';
                    }
                    FB.login(function(response) {
                        return response.id;
                    });
                },

                login: function(credentials) {
                    return this.loginMain(AuthenticateCli, credentials);
                },

                loginFb: function(credentials) {
                    return this.loginMain(AuthenticateFbCli, credentials);
                },

                loginMain: function(cli, credentials) {
                    var deferred = $q.defer();

                    cli.login(credentials).$promise.then(function(data) {
                        localStorageService.set('token', data.token);
                        Principal.identity(true).then(function(identity) {
                            identity.passwordExpireDate = data.passwordExpireDate;
                            deferred.resolve(identity);
                        }).catch(function(err) {
                            deferred.reject(err);
                        });
                    }).catch(function(err) {
                        deferred.reject(err);
                    });

                    return deferred.promise;
                },

                logout: function() {
                    localStorageService.remove('token');
                    Principal.authenticate(null);
                    this.logoutFb();
                },

                logoutFb: function() {
                    _fbId = null;
                    if (!FB) {
                        console.log("Facebook (FB) is not initialized");
                        return;
                    }
                    FB.getLoginStatus(function(response) {
                        if (response.status === 'connected') {
                            FB.logout();
                        } else if (response.status === 'not_authorized') {
                        } else {
                        }
                    });
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