(function() {
    'use strict';

    angular.module('settlerApplication').controller('ImportCtrl', function($scope,
                                                                           $timeout,
                                                                           modalService,
                                                                           Upload) {

        $scope.file = false;

        $scope.import = function(type, file) {
            if (!file) {
                return;
            }

            $scope.file = file;

            file.upload = Upload.upload({
                url:  'api/import/' + type,
                data: {file: file}
            });

            file.upload.then(function(response) {
                $timeout(function() {
                    file.result = response.data;
                });
            }, function(response) {
                if (response.status > 0) {
                    modalService.createErrorDialogFromResponse(response);
                } else {
                    modalService.createSuccessDialog('<strong>Upload zakończony powodzeniem!</strong>');
                }
            }, function(evt) {
                // Math.min is to fix IE which reports 200% sometimes
                file.progress = Math.min(100, parseInt(100.0 * evt.loaded / evt.total));
            });
        };

    });

})();