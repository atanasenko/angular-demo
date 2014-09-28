'use strict';

var app = angular.module('ex.controllers', ['angularFileUpload']);

// http://stackoverflow.com/questions/14718826/angularjs-disable-partial-caching-on-dev-machine
app.run(function ($rootScope, $templateCache) {
    $rootScope.$on('$viewContentLoaded', function () {
        $templateCache.removeAll();
    });
});

app.controller('StudentsList', ['$scope', 'StudentFactory', '$location',
    function ($scope, StudentFactory, $location) {

        $scope.view = function (student) {
            $location.path('/students/' + student.id);
        };

        $scope.add = function () {
            $location.path('/students/add');
        };
        
        $scope.avgGradeSort = function(student) {
            var grade = $scope.avgGrade(student);
            return grade ? -grade : 0;
        };
        $scope.avgGrade = function(student) {
            
            var courses = student.courses;
            var grade = 0;
            var amount = 0;
            for(var i = 0; i < courses.length; i++) {
                if(courses[i].courseGrade) {
                    grade += courses[i].courseGrade;
                    amount++;
                }
            }
            
            if(grade == 0) return undefined;
            return Math.round(grade / amount);
        };

        $scope.students = StudentFactory.query();
    }
]);

app.controller('StudentView', ['$scope', '$routeParams', 'StudentFactory', 'CourseFactory', 'StudentCourseFactory', '$location',
    function ($scope, $routeParams, StudentFactory, CourseFactory, StudentCourseFactory, $location) {
        
        $scope.update = function () {
            StudentFactory.update($scope.student);
        };
        
        $scope.delete = function () {
            StudentFactory.delete({ id: $scope.student.id }, function(){
                $location.path('/students');
            });
        };
        
        $scope.list = function () {
            $location.path('/students');
        };
        
        $scope.addCourse = function(course) {
            StudentCourseFactory.add({ studentId: $scope.student.id, courseId: course.id }, function(){
                $scope.student = StudentFactory.get({id: $routeParams.id});
            });
        };
        
        $scope.removeCourse = function(course) {
            StudentCourseFactory.remove({ studentId: $scope.student.id, courseId: course.id }, function(){
                $scope.student = StudentFactory.get({id: $routeParams.id});
            });
        };
        
        $scope.student = StudentFactory.get({id: $routeParams.id});
        $scope.availableCourses = CourseFactory.query();
        $scope.filteredCourses = function() {
          var items = $scope.availableCourses;
          var inuse = $scope.student.courses;
          if(!inuse) return items;
          
          var filtered = [];
          for(var i = 0; i < items.length; i++) {
            var contains = false;
            var item = items[i];
            for(var j = 0; j < inuse.length; j++) {
              if(item.id == inuse[j].course.id) {
                contains = true;
                break;
              }
            }
            
            if(!contains) filtered.push(item);
          }
          return filtered;
        };
    }
]);

app.controller('StudentAdd', ['$scope', 'StudentFactory', '$location',
    function ($scope, StudentFactory, $location) {
        $scope.save = function () {
            $scope.student = StudentFactory.save($scope.student, function(){
                $location.path('/students/' + $scope.student.id);
            });
        };
        
        $scope.back = function() {
            $location.path('/students');
        };
    }
]);



app.controller('CoursesList', ['$scope', 'CourseFactory', '$location',
    function ($scope, CourseFactory, $location) {

        $scope.view = function (course) {
            $location.path('/courses/' + course.id);
        };

        $scope.add = function () {
            $location.path('/courses/add');
        };

        $scope.courses = CourseFactory.query();
    }
]);

app.controller('CourseView', ['$scope', '$routeParams', 'CourseFactory', 'AssignmentFactory', '$location',
    function ($scope, $routeParams, CourseFactory, AssignmentFactory, $location) {
        
        $scope.update = function () {
            CourseFactory.update($scope.course);
        };
        
        $scope.delete = function () {
            CourseFactory.delete({ id: $scope.course.id }, function(){
                $location.path('/courses');
            });
        };
        
        $scope.list = function () {
            $location.path('/courses');
        };
        
        $scope.addAssignment = function(assignment) {
            AssignmentFactory.save({courseId: $routeParams.id, name: assignment.name, courseWeight: assignment.courseWeight}, function(){
                $scope.course = CourseFactory.get({id: $routeParams.id});
                $scope.newAssignment = null;
            });
        };
        
        $scope.viewAssignment = function(assignment) {
            $location.path('/courses/' + $routeParams.id + '/assignments/' + assignment.id);
        }
        
        $scope.course = CourseFactory.get({id: $routeParams.id});
    }
]);

app.controller('CourseAdd', ['$scope', 'CourseFactory', '$location',
    function ($scope, CourseFactory, $location) {
        $scope.save = function () {
            $scope.course = CourseFactory.save($scope.course, function(){
                $location.path('/courses/' + $scope.course.id);
            });
        };
        
        $scope.back = function() {
            $location.path('/courses');
        };
    }
]);

app.controller('AssignmentView', ['$scope', '$routeParams', 'AssignmentFactory', 'AssignmentGradeFactory', 'FileUploader', '$location',
    function ($scope, $routeParams, AssignmentFactory, AssignmentGradeFactory, FileUploader, $location) {
        
        $scope.update = function () {
            AssignmentFactory.update({ 
                courseId: $routeParams.courseId, 
                assignmentId: $scope.assignment.id, 
                name: $scope.assignment.name, 
                courseWeight: $scope.assignment.courseWeight
            });
        };
        
        $scope.delete = function () {
            AssignmentFactory.delete({ courseId: $routeParams.courseId, assignmentId: $scope.assignment.id }, function(){
                $location.path('/courses/' + $routeParams.courseId);
            });
        };
        
        $scope.list = function () {
            $location.path('/courses/' + $routeParams.courseId);
        };
        
        $scope.updateGrade = function(studentGrade) {
            AssignmentGradeFactory.update({
                courseId: $routeParams.courseId,
                assignmentId: $routeParams.assignmentId,
                studentId: studentGrade.id,
                grade: studentGrade.grade
            });
        };
        
        
        $scope.assignment = AssignmentFactory.get({ courseId: $routeParams.courseId, assignmentId: $routeParams.assignmentId });
        $scope.studentGrades = AssignmentGradeFactory.query({ courseId: $routeParams.courseId, assignmentId: $routeParams.assignmentId });
        
        $scope.initUploader = function() {
            var uploader = $scope.uploader = new FileUploader({
                url: '/api/courses/' + $routeParams.courseId + '/assignments/' + $routeParams.assignmentId + '/grades/upload',
                queueLimit: 1
            });
            uploader.onCompleteItem = function(item){
                item.remove();
                $scope.studentGrades = AssignmentGradeFactory.query({ courseId: $routeParams.courseId, assignmentId: $routeParams.assignmentId });
            };
        };
        $scope.initUploader();
        
    }
]);
