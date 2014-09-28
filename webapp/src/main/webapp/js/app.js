'use strict';

angular.module('ex', ['ex.controllers', 'ex.services']).
    config(['$routeProvider', function ($routeProvider) {
        
        $routeProvider.when('/students', {templateUrl: 'html/student-list.html', controller: 'StudentsList'});
        $routeProvider.when('/students/add', {templateUrl: 'html/student-add.html', controller: 'StudentAdd'});
        $routeProvider.when('/students/:id', {templateUrl: 'html/student-view.html', controller: 'StudentView'});
        
        $routeProvider.when('/courses', {templateUrl: 'html/course-list.html', controller: 'CoursesList'});
        $routeProvider.when('/courses/add', {templateUrl: 'html/course-add.html', controller: 'CourseAdd'});
        $routeProvider.when('/courses/:id', {templateUrl: 'html/course-view.html', controller: 'CourseView'});
        $routeProvider.when('/courses/:courseId/assignments/:assignmentId', {templateUrl: 'html/assignment-view.html', controller: 'AssignmentView'});
        
        $routeProvider.otherwise({redirectTo: '/'});
    }
]);
