describe('Test of failing singing in', function() {
    var ok_button = element(by.css('[ng-click="ok()"]'));
    beforeEach(function() {
        browser.driver.get('http://localhost:3000/#/login');
        browser.waitForAngular();
        //expect(browser.getCurrentUrl()).toEqual(browser.baseUrl + '#/login');
    }, 30000);

    it('first incorrect login', function () {
        var user = browser.driver.findElement(by.id('username'));
        var password = browser.driver.findElement(by.id('password'));
        var button = element(by.css('[ng-click="login()"]'));

        user.sendKeys('first');
        password.sendKeys('nope');

        expect(validate_after_failed_signin()); //TODO - should be incorrect
        expect(validate_base());
        button.click().then(validate_incorrect(), 10000);
        ok_button.click().then(validate_after_failed_signin(), 10000);
    })

    var validate_incorrect = function () {
        var box = element(by.css('modal-content'));

        expect(box.isPresent());
        expect(ok_button.isPresent());
    }

    var validate_base = function () {
        var login_field = browser.driver.findElement(by.id('username'));
        var password_field = browser.driver.findElement(by.id('password'));

        expect(login_field.isPresent);
        expect(password_field.isPresent);
    }

    var validate_after_failed_signin = function () {
        var info_field = element(by.css('[class="alert alert-danger"]'));
        //var info_field_hidden = element(by.cssContainingText('[class="alert alert-danger" ng-hide]'));

        expect(info_field.getAttribute('class') != ('ng-hide'));
        //expect(info_field != info_field_hidden);
    }

});
