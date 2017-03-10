describe('Protractor first sign in test', function() {

    beforeEach(function() {
        browser.driver.get('http://localhost:3000/#/login');
        browser.waitForAngular();
        //expect(browser.getCurrentUrl()).toEqual(browser.baseUrl + '#/login');
    }, 30000);

    it('first failed singin in: ', function () {
        var user = browser.driver.findElement(by.id('username'));
        var password = browser.driver.findElement(by.id('password'));
        var button = element(by.css('[ng-click="login()"]'));

        user.sendKeys('first');
        password.sendKeys('nope');

        expect(user.getAttribute('value')).toEqual('first');
        expect(password.getAttribute('value')).toEqual('nope');

/*
        element.all(by.css('a')).each(function (element) {
            var linkTarget = element.getAttribute('href').then(function(attr) {;
            expect(typeof attr).toBe("string");
        });
    });*/

        button.click().then(validate_incorrect(), 10000);
    });

    var validate_incorrect = function () {
        browser.waitForAngular();
        expect(browser.getCurrentUrl() != ("http://localhost:3000/#/panel"));
    };

/*    var find_content = function(){

    };*/ //TODO - how can I bind user, password and button instead of writing it in "it" function.

    /*var urlChanged = function(url) {
        return function () {
            return browser.getCurrentUrl().then(function(actualUrl) {
                return url != actualUrl;
            });
        };
    };*/

    it('testing admin login, incorrect password: ', function () {
        var user = browser.driver.findElement(by.id('username'));
        var password = browser.driver.findElement(by.id('password'));
        var button = element(by.css('[ng-click="login()"]'));

        user.sendKeys('admin');
        password.sendKeys('nope');

        button.click().then(validate_incorrect(), 10000);
    });




});