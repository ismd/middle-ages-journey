'use strict';

angular.module('main', ['ngRoute', 'userService', 'characterService', 'registrationService',
    'characterCreateService', 'backgroundImageDirective', 'worldService',
    'wsService', 'commonService', 'chatService', 'chatDirective', 'enterDirective'])
    .config(function($routeProvider, $locationProvider, $httpProvider) {
        $locationProvider.html5Mode(true);

        $routeProvider.
            when('/', {
                templateUrl: '/partial/news/index.html'
            }).
            when('/registration', {
                templateUrl: '/partial/registration/index.html',
                controller: RegistrationCtrl
            }).
            when('/character/create', {
                templateUrl: '/partial/character/create.html',
                controller: CharacterCreateCtrl
            }).
            when('/world', {
                templateUrl: '/partial/world/index.html',
                controller: WorldCtrl
            }).
            when('/world/inventory', {
                templateUrl: '/partial/world/inventory.html'
            }).
            otherwise({
                redirectTo: '/'
            });

        $httpProvider.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';
        $httpProvider.defaults.transformRequest = function(data) {
            if (data === undefined) {
                return data;
            }

            return $.param(data);
        };
    });
