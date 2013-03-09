var userServices = angular.module('userServices', []);

userServices.factory('User', function($rootScope, $http) {
    return {
        'login': function(username, password) {
            var authForm    = $('div#auth-form');
            var loginButton = $('button#login-button');

            loginButton.addClass('disabled');

            $http.post('/api/auth/login', {
                username: username,
                password: password
            }).success(function(data) {
                if ('ok' === data.status) {
                    $rootScope.$broadcast('logged', true);
                    $rootScope.$broadcast('setUser', data.user);

                    authForm.modal('hide');
                } else {
                    alert('Не удалось войти');
                    loginButton.removeClass('disabled');
                }
            }).error(function() {
                alert('Не удалось обратиться к серверу');
            });
        },
        'showCharactersList': function() {
            var selectCharacterForm = $('div#select-character');

            selectCharacterForm.modal();

            $http.get('/api/user/characters').success(function(data) {
                $rootScope.$broadcast('characters-list-update', data);
            }).error(function() {
                alert('Не удалось обратиться к серверу');
            });
        }
    };
});