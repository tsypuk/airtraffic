var app = angular.module('app', ['ngRoute', 'ngResource', 'ngStorage', 'ngMap']);

app.config(function ($routeProvider) {
    $routeProvider
        .when('/flights', {
            templateUrl: '/views/flights.html',
            controller: 'flightsController'
        })
        .when('/boards', {
            templateUrl: '/views/boards.html',
            controller: 'boardsController'
        })
        .when('/maps', {
            templateUrl: 'views/maps.html',
            controller: 'mapsController'

        })
        .otherwise(
            {redirectTo: '/'}
        );
});