(function () {
    'use strict';

    angular.module('settlerApplication').service('modalService', function ($modal) {
        this.createErrorDialogFromResponse = function (response) {

        };
        this.createSuccessDialog = function (body) {
            $modal.open({
                templateUrl: 'scripts/ui/common/dialogs/statusDialog/statusDialog.html',
                controller:  'statusDialogCtrl',
                resolve:     {
                    conf: function () {
                        return {
                            modalTitle: 'Potwierdzenie',
                            modalBody:  body,
                            isOK:       true
                        };
                    }
                }
            });
        };
        this.createErrorDialog = function (body) {
            $modal.open({
                templateUrl: 'scripts/ui/common/dialogs/statusDialog/statusDialog.html',
                controller:  'statusDialogCtrl',
                resolve:     {
                    conf: function () {
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