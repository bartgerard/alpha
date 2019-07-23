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

# ServerSentEvents

    @GetMapping(path = "stream-flux", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> flux() {
        return ...;
    }

OR

    @GetMapping("stream-sse")
    public Flux<ServerSentEvent<String>> flux() {
        return Flux.interval(Duration.ofSeconds(1))
                .map(sequence -> ServerSentEvent.<String>builder()
                        .id(String.valueOf(sequence))
                        .event("periodic-event")
                        .data("SSE - " + LocalTime.now().toString())
                        .build()
                );
    }
