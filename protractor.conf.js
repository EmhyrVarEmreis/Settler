exports.config = {
    framework:       'jasmine',
    seleniumAddress: 'http://localhost:4444/wd/hub',
    specs:           ['src/test/webapp/validator/*spec.js'],
    baseUrl: 'http://localhost:3000',
}