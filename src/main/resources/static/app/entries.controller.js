(function () {
    'use strict';
    angular
        .module('app')
        .controller('EntriesController', EntriesController);
    EntriesController.$inject = ['$http'];

    function EntriesController($http) {
        var vm = this;
        vm.entries = [];
        vm.getAll = getAll;
        vm.getBalance = getBalance;

        init();

        function init() {
            getAll();
        }

        function getAll() {
            var url = "/api/entries/";
            var entriesPromise = $http.get(url);
            entriesPromise.then(function (response) {
                vm.entries = response.data;
            });
        }

        function getBalance() {
            var url = "/api/balance";
            var entriesPromise = $http.get(url);
            entriesPromise.then(function (response) {
                vm.entries = response.data;
            });
        }

    }

})();