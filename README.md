# SubSink

Manage subscriptions.

    sudo npm install subsink --save
    
# Cypress

    sudo npm install cypress --save-dev

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

# Spring Cloud Gateway

## Automatic Import From Eureka

    /*
    spring:
      cloud:
        gateway:
          discovery.locator:
            enabled: true
            lowerCaseServiceId: true
     */
    
## Reload routes

    POST /application/gateway/refresh
    
# Eureka

## Manual

    final EurekaClient discoveryClient
    
    final String quizServiceUrl = discoveryClient.getNextServerFromEureka("quiz-service", false)
            .getHomePageUrl();
            
    final String teamServiceUrl = discoveryClient.getNextServerFromEureka("team-service", false)
            .getHomePageUrl();
            
## Automatic

    lb://QUIZ-SERVICE
    
# J4EV3

1. Search for the Bluetooth mac address.

        system_profiler SPBluetoothDataType
        
# npm

    sudo npm install --unsafe-perm=true --allow-root
    
    sudo npm install --unsafe-perm -g @angular/cli
    
# CSS

    http://getbem.com/introduction/
    
