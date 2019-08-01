# General

## Import Custom Libs

    mvn install:install-file
      -Dfile=<path-to-file>
      -DgroupId=<group-id>
      -DartifactId=<artifact-id>
      -Dversion=<version>
      -Dpackaging=<packaging>
      -DgeneratePom=true

# J4EV3 by LLeddy

https://github.com/LLeddy/J4EV3

    mvn install:install-file -Dfile=lib/J4EV3WithoutDependencies.jar -DgroupId=com.github.lleddy -DartifactId=j4ev3 -Dversion=1.0.0 -Dpackaging=jar
 
# Bluecove 2.1.2

    mvn install:install-file -Dfile=lib/bluecove-2.1.2.jar -DgroupId=io.ultreia -DartifactId=bluecove -Dversion=2.1.2 -Dpackaging=jar

    
    