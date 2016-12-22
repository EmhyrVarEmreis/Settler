(function () {
    'use strict';

    angular.module('settlerServices').service('avatarService', function (userAvatarFactory, modalService) {

        var defaultAvatarLocation = '/images/avatar.png';
        this.defaultAvatarLocation = defaultAvatarLocation;

        this.cacheById = {};

        this.deCacheByUserId = function (id) {
            this.cacheById['' + id] = undefined;
        };

        var getString = function (data) {
            if (data) {
                return 'data:' + data.type + ';base64,' + data.content;
            } else {
                return defaultAvatarLocation;
            }
        };
        this.getString = getString;

        this.getAvatarByUserId = function (id, callback, asString) {
            var cached = this.cacheById['' + id];
            if (cached === undefined) {
                cached = userAvatarFactory.get(
                    {
                        id: !id ? undefined : id
                    }
                );
                this.cacheById['' + id] = cached;
                if (true || asString) {
                    cached.$promise.then(function (data) {
                        if (data.content) {
                            cached.string = getString(data);
                        } else {
                            cached.string = defaultAvatarLocation;
                        }
                    });
                }
            }
            return cached;
        };

        this.getAvatarByUserLogin = function (login, callback, asString) {
            var newCallback = callback;
            if (asString) {
                var getString = this.getString;
                newCallback = function (data) {
                    return callback(getString(data));
                };
            }
            return this.loadAvatar(null, login, newCallback);
        };

        this.loadAvatar = function (id, login, callback) {
            return userAvatarFactory.get(
                {
                    id:    !id ? undefined : id,
                    login: !login ? undefined : login
                },
                function (data) {
                }, function (err) {
                }
            ).$promise.then(function (data) {
                if (data.content) {
                    callback(data);
                } else {
                    callback(null);
                }
            });
        };

    });

})();