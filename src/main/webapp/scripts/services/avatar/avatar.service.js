(function () {
    'use strict';

    angular.module('settlerServices').service('avatarService', function (userAvatarFactory, modalService) {

        this.getAvatarByUserId = function (id, callback) {
            return this.loadAvatar(id, null, callback);
        };

        this.getAvatarByUserLogin = function (login, callback) {
            return this.loadAvatar(null, login, callback);
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