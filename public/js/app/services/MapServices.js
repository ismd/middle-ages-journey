var mapServices = angular.module('mapServices', []);

mapServices.factory('Map', function($http, $rootScope) {
    var x;
    var y;

    var canvas = $('canvas#map');
    var ctx    = canvas.get(0).getContext('2d');

    // Test data
    ctx.strokeRect(0, 0, 50, 50);
    ctx.strokeRect(50, 0, 50, 50);
    ctx.strokeRect(100, 0, 50, 50);
    ctx.strokeRect(150, 0, 50, 50);
    ctx.strokeRect(200, 0, 50, 50);
    ctx.strokeRect(250, 0, 50, 50);
    ctx.strokeRect(300, 0, 50, 50);
    ctx.strokeRect(0, 50, 50, 50);
    ctx.strokeRect(50, 50, 50, 50);
    ctx.strokeRect(100, 50, 50, 50);
    ctx.strokeRect(150, 50, 50, 50);
    ctx.strokeRect(200, 50, 50, 50);
    ctx.strokeRect(250, 50, 50, 50);
    ctx.strokeRect(300, 50, 50, 50);
    ctx.strokeRect(0, 100, 50, 50);
    ctx.strokeRect(50, 100, 50, 50);
    ctx.strokeRect(100, 100, 50, 50);
    ctx.strokeRect(150, 100, 50, 50);
    ctx.strokeRect(200, 100, 50, 50);
    ctx.strokeRect(250, 100, 50, 50);
    ctx.strokeRect(300, 100, 50, 50);
    ctx.strokeRect(0, 150, 50, 50);
    ctx.strokeRect(50, 150, 50, 50);
    ctx.strokeRect(100, 150, 50, 50);
    ctx.strokeRect(150, 150, 50, 50);
    ctx.strokeRect(200, 150, 50, 50);
    ctx.strokeRect(250, 150, 50, 50);
    ctx.strokeRect(300, 150, 50, 50);
    ctx.strokeRect(0, 200, 50, 50);
    ctx.strokeRect(50, 200, 50, 50);
    ctx.strokeRect(100, 200, 50, 50);
    ctx.strokeRect(150, 200, 50, 50);
    ctx.strokeRect(200, 200, 50, 50);
    ctx.strokeRect(250, 200, 50, 50);
    ctx.strokeRect(300, 200, 50, 50);

    return {
        'init': function(init_x, init_y) {
            x = init_x;
            y = init_y;
        },
        'move': function(direction) {
            var newX = x;
            var newY = y;

            switch (direction) {
                case 'top':
                    newY--;
                    break;

                case 'right':
                    newX++;
                    break;

                case 'bottom':
                    newY++;
                    break;

                case 'left':
                    newX--;
                    break;
            }

            $http.post('/api/character/move', {
                x: newX,
                y: newY
            }).success(function(data) {
                if ('ok' === data.status) {
                    x = data.x;
                    y = data.y;

                    $rootScope.$broadcast('move', x, y);
                } else {
                    alert('Ошибка при перемещении');
                }
            }).error(function() {
                alert('Не удалось обратиться к серверу');
            });
        }
    };
});