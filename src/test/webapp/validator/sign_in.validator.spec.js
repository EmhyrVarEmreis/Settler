describe('Protractor first sign in test', function() {

    beforeEach(function() {
        browser.driver.get('http://localhost:3000/#/login');
        browser.waitForAngular();
        //expect(browser.getCurrentUrl()).toEqual(browser.baseUrl + '#/login');
    }, 30000);

    it('Should be ', function () {
        var user = browser.driver.findElement(by.id('username'));
        var password = browser.driver.findElement(by.id('password'));
        //var button = browser.driver.findElement(by.css('[ng-click="login()"]'));
        var button = element(by.css('[ng-click="login()"]'));

        user.sendKeys('first');
        password.sendKeys('nope');

        expect(user.getAttribute('value')).toEqual('first');
        expect(password.getAttribute('value')).toEqual('nope'); //TODO ->
/*
        element.all(by.css('a')).each(function (element) {
            var linkTarget = element.getAttribute('href').then(function(attr) {;
            expect(typeof attr).toBe("string");
        });
    });*/

        button.click().then(function (){
            browser.waitForAngular();
            expect(browser.getCurrentUrl() != ("http://localhost:3000/#/panel"));
            //expect(authenticationError.toBe(true));
            // browser.wait(urlChanged("http://localhost:3000/#/panel"), 5000);

        }, 10000);
    });

    /*var urlChanged = function(url) {
        return function () {
            return browser.getCurrentUrl().then(function(actualUrl) {
                return url != actualUrl;
            });
        };
    };*/
});