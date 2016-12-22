(function() {
    'use strict';

    angular.module('settlerApplication').controller('NavBarCtrl',
        function (Principal, Auth, $state, avatarService) {
            var ctrl = this;

            ctrl.permissions = {
                admin: false
            };

            Principal.identity().then(function(account) {
                ctrl.userData = account;
                ctrl.userData.avatar = avatarService.getAvatarByUserId(account.id);
            });

            ctrl.logout = function() {
                Auth.logout();
                $state.go('login');
            };
        }
    );

})();