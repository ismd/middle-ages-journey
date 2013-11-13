'use strict';

angular.module('userService', []).factory('User', function($q, $rootScope, $http, Redirector, Ws) {
    var service = {},
        user = null;

    service.login = function(username, password) {
        var defer = $q.defer();

        Ws.send({
            controller: 'User',
            action: 'login',
            args: {
                username: username,
                password: password
            }
        }).then(function(data) {
            user = data;

            $('div#auth-form').modal('hide');
            defer.resolve(user);
            $rootScope.$broadcast('login-success', user);
        }, function(message) {
            defer.reject(message);
        });

        return defer.promise;
    };

    service.logout = function() {
        var defer = $q.defer();

        Ws.send({
            controller: 'User',
            action: 'logout'
        }).then(function() {
            $rootScope.$broadcast('logout-success');
            Redirector.redirect('/');
            defer.resolve();
        });

        return defer.promise;
    };

    service.showCharactersList = function() {
        var defer = $q.defer();

        $('div#select-character').modal();

        Ws.send({
            controller: 'User',
            action: 'listCharacters'
        }).then(function(data) {
            defer.resolve(data);
        }, function(message) {
            defer.reject(message);
        });

        return defer.promise;
    };

    return service;
});
