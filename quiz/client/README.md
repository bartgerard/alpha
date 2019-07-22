# SubSink

Manage subscriptions.

    sudo npm install subsink --save

# Protractor

Please make sure to install

    npm install protractor

Update web driver manager

    webdriver-manager update

Run this command from your root

    node node_modules\protractor\bin\webdriver-manager update

Now start up a server with:

    webdriver-manager start

Also make sure that your protractor.conf.js file has below line

    // baseUrl: 'http://localhost:4200/',
    seleniumAddress: 'http://localhost:4444/wd/hub/',

Now run your e2e tests on different browsers

    ng e2e
