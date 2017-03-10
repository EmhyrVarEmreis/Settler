exports.config = {
    framework:          'jasmine',
    seleniumAddress:    'http://localhost:4444/wd/hub',
    specs:              ['src/test/webapp/**/*spec.js'],
    baseUrl:            'http://localhost:3000'

/*
    multiCapabilities: [{
        'browserName': 'firefox'
    }, {
        'browserName': 'chrome'
    }]
*/

}