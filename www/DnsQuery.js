
var exec = require('cordova/exec');

var DnsQuery = function() {};

DnsQuery.prototype.resolve = function(hostname, success, error) {
    exec(success, error, "DnsQuery", "resolve", [hostname]);
};

DnsQuery.prototype.resolveAll = function(hostname, success, error) {
    exec(success, error, "DnsQuery", "resolveAll", [hostname]);
};

module.exports = new DnsQuery();