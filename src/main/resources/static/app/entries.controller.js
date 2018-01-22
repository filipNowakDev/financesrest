(function(){
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
            var url = "/entries/all";
            var entriesPromise = $http.get(url);
            entriesPromise.then(function(response){
                vm.entries = response.data;
            });
        }

        function getBalance(){
            var url = "/entries/balance";
            var entriesPromise = $http.get(url);
            entriesPromise.then(function(response){
                vm.entries = response.data;
            });
        }

    }

})();