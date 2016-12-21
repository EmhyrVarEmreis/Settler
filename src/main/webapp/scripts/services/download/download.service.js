(function() {
    'use strict';

    angular.module('settlerServices').service('downloadService', function (dictionaryService, localStorageService) {
        var service = this;

        service.download = function(url, properties) {
            var form = $("<form/>");
            form.attr({action: url, method: 'POST'});
            var appendValue = function(name, value) {
                $("<input/>").attr({type: 'hidden', name: name, value: value}).appendTo(form);
            };
            appendValue('x-auth-token', localStorageService.get('token'));
            if (properties) {
                for (var key in properties)
                    if (properties.hasOwnProperty(key))
                        appendValue(key, properties[key]);
            }
            $('body').append(form);
            form.submit();
            form.remove();
        };
    });

})();