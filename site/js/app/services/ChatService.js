(function() {
    'use strict';

    window.mainModule.factory('Chat', ['Ws', '$q', 'Common', function(Ws, $q, Common) {
        var service = {};

        service.init = function() {
            var defer = $q.defer();

            Ws.send({
                controller: 'Chat',
                action: 'init'
            }).then(function(data) {
                defer.resolve(data);
            }, function(message) {
                defer.reject(message);
            });

            return defer.promise;
        };

        service.send = function(message) {
            var defer = $q.defer();

            Ws.send({
                controller: 'Chat',
                action: 'send',
                args: {
                    message: message
                }
            }).then(function(data) {
                defer.resolve(data.user);
            }, function(message) {
                defer.reject(message);
            });

            return defer.promise;
        };

        service.getMembers = function() {
            var defer = $q.defer();

            Ws.send({
                controller: 'Chat',
                action: 'getMembers'
            }).then(function(data) {
                defer.resolve(data.members);
            }, function(message) {
                defer.reject(message);
            });

            return defer.promise;
        };

        service.focus = function(message) {
            Common.focus($('div#chat div#controls input'), message);
        };

        return service;
    }]);
})();