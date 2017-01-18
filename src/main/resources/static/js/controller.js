app.controller('boardsController', function ($scope, $http) {
    $scope.headingTitle = "Board List";
    $scope.currentBoard = null;

    $scope.getBoard = function (board) {
        console.log("Hello " + board.hex);
        $scope.currentBoard = board;
    }

    $http.get('/boards')
        .success(function (data) {
            $scope.boards = data;
            console.log(data);
        })
});

app.controller('flightsController', function ($localStorage, $scope, $http) {
    $scope.headingTitle = "Flights List";
    if ($localStorage.flights == null) {
        console.log('There is no data in localStorage. Doing HTTP call.');
        $http.get('/flights')
            .success(function (data) {
                $scope.flights = data;
                $localStorage.flights = data;
            });
    } else {
        console.log('Loading flights from localStorage Cache.');
        $scope.flights = $localStorage.flights;
    }
});

app.controller('mapsController', function ($localStorage, $scope, $http) {
    $scope.headingTitle = "Maps";
    if ($localStorage.points == null) {
        var bulk = [];
        $http.get('/flights').success(function (data) {
            console.log('HTTP call for points.');
            for (var current in data) {
                bulk.push(
                    {
                        'name': 'NAME',
                        'latitude': data[current].lat,
                        'longitude': data[current].lon
                    }
                );
            }
            $localStorage.points = bulk;
            $scope.points = bulk;
        });
    } else {
        console.log('Loading points from localStorage Cache.');
        $scope.points = $localStorage.points;
    }
});

// $scope.points = [
//     { "name": "Canberra", "latitude": -35.282614, "longitude": 149.127775 },
//     { "name": "Melbourne", "latitude": -37.815482, "longitude": 144.983460 },
//     { "name": "Sydney", "latitude": -33.869614, "longitude": 151.187451 }
// ];
// $scope.customIcon = {
//     "scaledSize": [32, 32],
//     "url": "http://www.cliparthut.com/clip-arts/823/arrowhead-clip-art-823528.png"
// };