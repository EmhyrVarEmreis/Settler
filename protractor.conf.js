const config = {
  baseUrl: 'http://localhost:4000/',

  params: {
    testPolicyRef: ''
  },

  specs: [
    '/test/e2e-spec/*.e2e-spec.js'  //'./target/**/*.e2e-spec.js'
  ],

  exclude: [],

  // 'jasmine' by default will use the latest jasmine framework
  framework: 'jasmine',

  // allScriptsTimeout: 110000,

  jasmineNodeOpts: {
    // showTiming: true,
    showColors: true,
    isVerbose: false,
    includeStackTrace: false,
    // defaultTimeoutInterval: 400000
  },

  directConnect: true,

  capabilities: {
    browserName: 'chrome'
  },

  onPrepare: function() {
    const SpecReporter = require('jasmine-spec-reporter');
    // add jasmine spec reporter
    jasmine.getEnv().addReporter(new SpecReporter({ displayStacktrace: true }));

    browser.ignoreSynchronization = false;

    browser.driver.manage().window().maximize();
    browser.driver.get('k2.primaris.local:8080/obsluga-grupowe/');
    browser.driver.findElement(by.id('username')).sendKeys('partners@000855');
    browser.driver.findElement(by.id('password')).sendKeys('partners@000855', protractor.Key.ENTER);

    browser.get('#/polisy/AR/');
    element.all(by.css('div[class="datatable-body-cell-label"] span')).get(1).getText()
      .then(function(policyRef) {
        browser.params.testPolicyRef = policyRef;
      });
  },


  /**
   * Angular 2 configuration
   *
   * useAllAngular2AppRoots: tells Protractor to wait for any angular2 apps on the page instead of just the one matching
   * `rootEl`
   */
  useAllAngular2AppRoots: true
};

if (process.env.TRAVIS) {
  config.capabilities = {
    browserName: 'firefox'
  };
}

exports.config = config;
