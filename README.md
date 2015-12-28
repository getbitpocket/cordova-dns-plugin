# Cordova DNS Query Plugin

A Plugin for querying IP addresses to domain names

## Installation

`cordova plugin add cordova-dns-plugin`

## Quick Example

```javascript

    // Resolving single IP address
    cordova.plugins.dns.resolve('seed.bitcoin.sipa.be',function (ip) {
        console.log("Resolved IP address: " + ip);    
    },function(error) {
        console.log("Error: " + error);
    });
    
    // Resolving all available IP addresses
    cordova.plugins.dns.resolveAll('seed.bitcoin.sipa.be',function (ips) {
        ips.forEach(function(ip) {
            console.log("Resolved IP address: " + ip);
        });    
    },function(error) {
        console.log("Error: " + error);
    });

```

## Supported Platforms

 - Android
 
## Running Tests

 - Add tests as plugin `cordova plugin add https://github.com/getbitpocket/cordova-dns-plugin.git#:/tests`
 - Follow the [Cordova Plugins Tests](https://github.com/apache/cordova-plugin-test-framework#running-plugin-tests) guide
 - Run the tests by launching app `cordova emulate`