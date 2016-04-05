exports.defineAutoTests = function() {

    describe('Dns Query Tests', function () {

        var ipRegex = /\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3}/; // naive but ok for the purpouse

        it("dns query should be defined", function () {
            expect(cordova.plugins.dns).toBeDefined();
            expect(cordova.plugins.dns.resolve).toBeDefined();
            expect(cordova.plugins.dns.resolveAll).toBeDefined();
        });

        it("resolve should return an ip address", function (done) {
            cordova.plugins.dns.resolve('seed.bitcoin.sipa.be').then(function (ip) {
                expect(ip).toMatch(ipRegex);
                done();
            });
        });

        it("resolveAll should return multiple ip addresses", function (done) {
            cordova.plugins.dns.resolveAll('seed.bitcoin.sipa.be').then(function (ips) {
                expect(ips.length).toBeGreaterThan(1);
                ips.forEach(function (ip) {
                    expect(ip).toMatch(ipRegex);
                });
                done();
            });
        });

        it("an invalid domain error should be returned", function (done) {
            cordova.plugins.dns.resolve('zzzz///').then(function () {
            }, function (error) {
                expect(error).toEqual('Unable to resolve host "zzzz///": No address associated with hostname');
                done();
            });
        });

        it("resolveSrv should return an srv lookup", function (done) {
            cordova.plugins.dns.srvLookup('_pexapp._tcp', 'pexipdemo.com').then(function (ips) {
                expect(ips[0]).toEqual('https://no-rp.pexipdemo.com:443');
                done();
            }, function(error) {expect(error).toBe(false);});
        });

        it("resolveSrv should fallback with a missing srv lookup", function (done) {
            cordova.plugins.dns.srvLookup('_pexapp._tcp', 'lol.com').then(function (ips) {
                expect(ips[0]).toEqual('https://lol.com:443');
                done();
            }, function(error) {expect(error).toBe(false);});
        });

    });

};

