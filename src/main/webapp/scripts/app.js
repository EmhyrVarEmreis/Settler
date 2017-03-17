(function () {
    'use strict';

    angular

        .module('settlerApplication', [
            'settlerServices',
            'LocalStorageModule',
            'ngResource',
            'ngTable',
            'angular-loading-bar',
            'ngTable',
            'ui.select',
            'ui.router',
            'ngSanitize',
            'ngFileUpload',
            'ui.bootstrap',
            'angularMoment',
            'rzModule'
        ])

        .factory('authInterceptor', function ($rootScope, $q, $location, localStorageService, $injector, historyService) {
            return {
                // Add authorization token to headers
                request:       function (config) {
                    config.headers = config.headers || {};
                    var token = localStorageService.get('token');

                    if (!token || typeof(token) != 'string') return config;
                    var expires = token.split(':')[1];

                    if (!expires) return config;
                    expires = parseInt(expires);

                    if (expires > new Date().getTime()) {
                        config.headers['x-auth-token'] = token;
                    }

                    return config;
                },
                response:      function (response) {
                    var token = response.headers('x-auth-token');
                    if (token) localStorageService.set('token', token);
                    return response;
                },
                responseError: function (rejection) {
                    if (rejection.status === 401 && !$rootScope.sessionLost) {
                        $rootScope.sessionLost = true;
                        $location.path('/login');
                        if (historyService.prev() !== null) {
                            $rootScope.sessionLost = false;
                        } else {
                            $injector.get('$uibModal').open({
                                templateUrl: 'scripts/ui/common/dialogs/statusDialog/statusDialog.html',
                                controller:  'statusDialogCtrl',
                                resolve:     {
                                    conf: function () {
                                        return {
                                            modalTitle: 'Informacja',
                                            modalBody:  'Twoja sesja wygasła lub cofnięto dostęp do zasobu!<br/><strong>Zaloguj się ponownie!</strong>',
                                            isOK:       false
                                        };
                                    }
                                }
                            }).result.then(function () {
                                $rootScope.sessionLost = false;
                            }, function () {
                                $rootScope.sessionLost = false;
                            });
                        }
                        historyService.clear();
                    }
                    return $q.reject(rejection);
                }
            };
        })

        .config(function ($stateProvider, $urlRouterProvider, $httpProvider) {
            $urlRouterProvider.otherwise(function ($injector, $location) {
                $injector.get('historyService').push($location.$$path);
                if (!!$injector.get('localStorageService').get('token')) {
                    return '/panel';
                } else {
                    return '/home';
                }
            });
            $httpProvider.interceptors.push('authInterceptor');
        })

        .run(function($rootScope, $state, Auth, Principal, $window) {
            $rootScope.$on('$stateChangeStart', function (event, toState, toStateParams) {
                $rootScope.toState = toState;
                $rootScope.toStateParams = toStateParams;

                if (Principal.isIdentityResolved()) {
                    Auth.authorize();
                }
            });

            $rootScope.$on('$stateChangeSuccess', function (event, toState, toParams, fromState, fromParams) {
                $rootScope.previousStateName = fromState.name;
                $rootScope.previousStateParams = fromParams;
            });

            $rootScope.back = function () {
                // If previous state is 'activate' or do not exist go to 'home'
                if ($rootScope.previousStateName === 'activate' || $state.get($rootScope.previousStateName) === null) {
                    $state.go('home');
                } else {
                    $state.go($rootScope.previousStateName, $rootScope.previousStateParams);
                }
            };

            $window.fbAsyncInit = function() {
                // Executed when the SDK is loaded

                FB.init({

                    /*
                     The app id of the web app;
                     To register a new app visit Facebook App Dashboard
                     ( https://developers.facebook.com/apps/ )
                     */

                    appId: '413655548988654',

                    /*
                     Adding a Channel File improves the performance
                     of the javascript SDK, by addressing issues
                     with cross-domain communication in certain browsers.
                     */

                    // channelUrl: 'app/channel.html',

                    /*
                     Set if you want to check the authentication status
                     at the start up of the app
                     */

                    status: true,

                    /*
                     Enable cookies to allow the server to access
                     the session
                     */

                    cookie: true,

                    /* Parse XFBML */

                    xfbml: true
                });

                FB.Event.subscribe('auth.authResponseChange', function(res) {

                    if (res.status === 'connected') {
                        console.log("authorized");
                        /*
                         The user is already logged,
                         is possible retrieve his personal info
                         */
                        _self.getUserInfo();

                        /*
                         This is also the point where you should create a
                         session for the current user.
                         For this purpose you can use the data inside the
                         res.authResponse object.
                         */

                    }
                    else {
                        console.log("bad");
                        /*
                         The user is not logged to the app, or into Facebook:
                         destroy the session on the server.
                         */

                    }

                });

            };

        });
})();


