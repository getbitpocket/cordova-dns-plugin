exports.defineAutoTests = function() {

    describe('Dns Query Tests', function () {

        var ipRegex = /\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3}/; // naive but ok for the purpouse

        it("dns query should be defined", function () {
            expect(cordova.plugins.dns).toBeDefined();
            expect(cordova.plugins.dns.resolve).toBeDefined();
            expect(cordova.plugins.dns.resolveAll).toBeDefined();
        });

        it("resolve should return an ip address", function (done) {
            cordova.plugins.dns.resolve('seed.bitcoin.sipa.be', function (ip) {
                expect(ip).toMatch(ipRegex);
                done();
            });
        });

        it("resolveAll should return multiple ip addresses", function (done) {
            cordova.plugins.dns.resolveAll('seed.bitcoin.sipa.be', function (ips) {
                expect(ips.length).toBeGreaterThan(1);
                ips.forEach(function (ip) {
                    expect(ip).toMatch(ipRegex);
                });
                done();
            });
        });

        it("an invalid domain error should be returned", function (done) {
            cordova.plugins.dns.resolve('zzzz///', function () {
            }, function (error) {
                expect(error).toEqual('Unable to resolve host "zzzz///": No address associated with hostname');
                done();
            });
        });

    });

};

