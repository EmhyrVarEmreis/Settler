(function () {
    'use strict';

    angular.module('settlerApplication').controller('AddRedistributionCtrl', function ($uibModalInstance,
                                                                                       redistributionList,
                                                                                       totalValue,
                                                                                       $rootScope) {
            var $ctrl = this;

            $ctrl.redistributionList = redistributionList;
            $ctrl.totalValue = parseFloat(totalValue.toString().replace(/,/, '.'));
            $ctrl.sum = 0;
            $ctrl.isPercent = null;

            for (var i = 0; i < redistributionList.length; i++) {
                $ctrl.sum = $ctrl.sum + parseFloat(redistributionList[i].value.toString().replace(/,/, '.'));
            }

            $ctrl.selected = {
                value: 0
            };

            $ctrl.refreshSlider = function () {
                if ($ctrl.isPercent) {
                    $ctrl.slider = {
                        options: {
                            floor:     0,
                            ceil:      100,
                            step:      0.01,
                            precision: 2,
                            maxLimit:  100 - ($ctrl.sum / $ctrl.totalValue * 100).toFixed(2)
                        }
                    };
                } else {
                    $ctrl.slider = {
                        options: {
                            floor:     0,
                            ceil:      $ctrl.totalValue,
                            step:      0.01,
                            precision: 2,
                            maxLimit:  $ctrl.totalValue - $ctrl.sum
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
                if ($ctrl.isPercent) {
                    $ctrl.selected.value = $ctrl.selected.value * $ctrl.totalValue / 100;
                }
                $uibModalInstance.close($ctrl.selected);
            };

            $ctrl.cancel = function () {
                $uibModalInstance.dismiss('cancel');
            };


        }
    );

})();