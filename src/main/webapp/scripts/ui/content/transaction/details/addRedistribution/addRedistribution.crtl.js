(function () {
    'use strict';

    angular.module('settlerApplication').controller('AddRedistributionCtrl', function ($uibModalInstance,
                                                                                       redistributionList,
                                                                                       totalValue,
                                                                                       $rootScope) {
        var $ctrl = this;

        $ctrl.redistributionList = redistributionList;
        $ctrl.totalValue = parseFloat(totalValue.toString().replace(/,/, '.'));
        $ctrl.percentageSum = 0;
        $ctrl.isPercent = null;

        for (var i = 0; i < redistributionList.length; i++) {
            $ctrl.percentageSum = $ctrl.percentageSum + parseFloat(redistributionList[i].percentage.toString().replace(/,/, '.'));
        }

        $ctrl.selected = {
            percentage: 0
        };

        $ctrl.refreshSlider = function () {
            if ($ctrl.isPercent) {
                $ctrl.slider = {
                    options: {
                        floor:     0,
                        ceil:      100,
                        step:      0.01,
                        precision: 2,
                        maxLimit:  100 - $ctrl.percentageSum
                    }
                };
            } else {
                $ctrl.slider = {
                    options: {
                        floor:     0,
                        ceil:      $ctrl.totalValue,
                        step:      0.01,
                        precision: 2,
                        maxLimit:  $ctrl.totalValue - ($ctrl.percentageSum * $ctrl.totalValue / 100)
                    }
                };
            }
            $rootScope.$broadcast('rzSliderForceRender');
        };

        $ctrl.setPercentage = function (value) {
            $ctrl.isPercent = value;
            $ctrl.refreshSlider();
        };

        $ctrl.ok = function () {
            if (!$ctrl.isPercent) {
                $ctrl.selected.percentage = $ctrl.selected.percentage / $ctrl.totalValue * 100;
            }
            $uibModalInstance.close($ctrl.selected);
        };

        $ctrl.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };


    });

})();