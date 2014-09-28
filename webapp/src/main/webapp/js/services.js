'use strict';

var services = angular.module('ex.services', ['ngResource']);

services.factory('StudentFactory', function ($resource) {
    return $resource('/api/students/:id', {}, {
        update: { method: 'PUT', params: {id: '@id'} },
        delete: { method: 'DELETE', params: {id: '@id'} }
    });
});

services.factory('CourseFactory', function ($resource) {
    return $resource('/api/courses/:id', {}, {
        update: { method: 'PUT', params: {id: '@id'} },
        delete: { method: 'DELETE', params: {id: '@id'} }
    });
});

services.factory('StudentCourseFactory', function ($resource) {
    return $resource('/api/students/:studentId/courses/:courseId', { studentId: '@studentId', courseId: '@courseId' }, {
        add: { method: 'PUT' }
    });
});

services.factory('AssignmentFactory', function ($resource) {
    return $resource('/api/courses/:courseId/assignments/:assignmentId', { courseId: '@courseId', assignmentId: '@assignmentId' }, {
        update: { method: 'PUT' }
    });
});

services.factory('AssignmentGradeFactory', function ($resource) {
    return $resource('/api/courses/:courseId/assignments/:assignmentId/grades/:studentId', { courseId: '@courseId', assignmentId: '@assignmentId', studentId: '@studentId' }, {
        update: { method: 'PUT' }
    });
});
