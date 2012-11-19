angular.module('devoxxmobile', ['ngResource']).
    config(function($routeProvider) {
        $routeProvider.
            when('/', {templateUrl: '/public/views/days.html'}).
            when('/day/:day', {controller: DayController, templateUrl: '/public/views/day.html'}).
            when('/talk/:talkEscapedUri', {controller: TalkController, templateUrl: '/public/views/talk.html'}).
            otherwise({redirectTo:'/'});
}).directive('page', function() {
	return {
		compile: function(elm) {
			return function(scope, elm, attrs) {
				$(elm).addClass('shown');
			}
		}
	}
});

function TalkController($scope, $routeParams, $resource) {
	$scope.talk = $resource('/talk?escapedUri=' + $routeParams.talkEscapedUri).get();
}

function DayController($scope, $routeParams, $resource) {
	$scope.day = $routeParams.day;
	$scope.talkGroups = $resource('/day/' + $routeParams.day).get();
}