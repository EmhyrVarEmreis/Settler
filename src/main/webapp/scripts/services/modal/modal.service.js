(function() {
    'use strict';

    angular.module('settlerServices').service('modalService', function ($uibModal) {

        this.createErrorDialogFromResponse = function(response) {
            console.log(response);
        };

        this.createSuccessDialog = function(body) {
            $uibModal.open({
                templateUrl: 'scripts/ui/common/dialogs/statusDialog/statusDialog.html',
                controller:  'statusDialogCtrl',
                resolve:     {
                    conf: function() {
                        return {
                            modalTitle: 'Potwierdzenie',
                            modalBody:  body,
                            isOK:       true
                        };
                    }
                }
            });
        };

        this.createErrorDialog = function(body) {
            $uibModal.open({
                templateUrl: 'scripts/ui/common/dialogs/statusDialog/statusDialog.html',
                controller:  'statusDialogCtrl',
                resolve:     {
                    conf: function() {
                        return {
                            modalTitle: 'Błąd',
                            modalBody:  body,
                            isOK:       false
                        };
                    }
                }
            });
        };

    });

})();