'use strict';

angular.module('registrationService', []).factory('Registration', function($rootScope, $http) {
    var service = {};

    service.register = function(data) {
        $http.post('/api/registration/register', data).success(function(data) {
            $rootScope.$broadcast('registered', 'ok' === data.status, data.message);
        }).error(function() {
            alert('Не удалось обратиться к серверу');
        });
    };

    return service;
});