angular.module('devoxxmobile', ['ngResource']).
    config(function($routeProvider) {
        $routeProvider.
            when('/', {templateUrl: '/public/views/days.html'}).
            when('/day/:day', {controller: DayController, templateUrl: '/public/views/day.html'}).
            when('/talk/:talk', {controller: TalkController, templateUrl: '/public/views/talk.html'}).
            otherwise({redirectTo:'/'});
});

function TalkController($scope) {
	
}

function DayController($scope, $routeParams, $resource) {
	$scope.day = $routeParams.day;
	$scope.talkGroups = $resource('/day/' + $routeParams.day).get();
}