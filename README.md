Java Embedded Framework (JEF)
========================

[![Build](https://github.com/java-embedded-framework/jef/workflows/Build/badge.svg)](https://github.com/java-embedded-framework/jef/actions?query=workflow%3ABuild)
[![Maven Central](https://img.shields.io/maven-central/v/ru.iothub.jef/linux-core.svg?label=Maven%20Central)](https://search.maven.org/artifact/ru.iothub.jef/linux-core)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

<!--The definitive JEF reference (including an overview and usage details) 
is in the [JavaDoc](http://java-embedded-framework.github.io/jef/0.1-ALPHA/javadoc/).  
Please read the [overview](http://java-embedded-framework.github.io/jef/0.1-ALPHA/javadoc/overview-summary.html#overview_description).  
Questions, comments, or exploratory conversations should begin on the 
[mailing list](http://groups.google.com/group/jna-users), 
although you may find it easier to find answers to already-solved problems 
on [StackOverflow](http://stackoverflow.com/questions/tagged/jef).
-->

JEF provides Java programs easy high level access directly from code 
to hardware interfaces like [SPI](https://en.wikipedia.org/wiki/Serial_Peripheral_Interface), 
[I2C](https://ru.wikipedia.org/wiki/I%C2%B2C), [Serial](https://en.wikipedia.org/wiki/Serial_port), 
[GPIO](https://en.wikipedia.org/wiki/General-purpose_input/output) or 
[One Wire](https://en.wikipedia.org/wiki/1-Wire) interfaces **without writing 
anything except Java code** - no JNI or native additional code is required. 
This functionality is comparable to Linux like platforms.

JEF allows you to create pure java based system only in Java or compile you applications 
to small(10+ megabytes) pure native binaries(C++ like ) for 64-bits platforms (commercial version only) 
without any code modification. 

Supported Platforms
===================
JEF will build on most linux-like platforms with a reasonable set of GNU tools and a JDK.

If your platform is supported by [libffi](http://en.wikipedia.org/wiki/Libffi), then chances
are you can build JEF for it.

If your platform is supported by [GraalVM Native Image Compiler](https://github.com/graalvm/graalvm-ce-builds/releases/),
then chances are you can build JEF binaries images for these platforms... 

Supported boards
===================
Please see details [here](PLATFORMS.md)


System requirements
===================
* Linux kernel 4.8+ for core level (or Raspberry Pi OS/Armbian for mcu level)
* Java 1.8+
* Maven 3+

Project Modules
==================

* **linux-core** - API for access to POSIX functions and protocols via Java
* **mcu-core** - Hi level wrapper for linux-core for Raspberry Pi boards
* **device-library** - Some 'drivers' implementation for devices
* **examples** - usage examples

Download and Install
========
Add Github as repository to your pom.xml
```
    <distributionManagement>
        <repository>
            <id>jef</id>
            <name>GitHub Java Embedded Frameworks Packages</name>
            <url>https://maven.pkg.github.com/java-embedded-framework/jef</url>
            <uniqueVersion>false</uniqueVersion>
        </repository>
    </distributionManagement>
```

and add necessary dependencies
```
    <dependency>
      <groupId>ru.iothub.jef</groupId>
      <artifactId>linux-core</artifactId>
      <version>0.0.1</version>
    </dependency>
    
    <dependency>
      <groupId>ru.iothub.jef</groupId>
      <artifactId>mcu-core</artifactId>
      <version>0.0.1</version>
    </dependency>
    
    <dependency>
      <groupId>ru.iothub.jef</groupId>
      <artifactId>device-library</artifactId>
      <version>0.0.1</version>
    </dependency>
    
    <dependency>
      <groupId>ru.iothub.jef</groupId>
      <artifactId>examples</artifactId>
      <version>0.0.1</version>
    </dependency>
```
Version 0.1-SNAPSHOT
You can download directly from github and build it.
For the future releases' library will be added to maven repository.


Using the Library
=================
* [Getting Started](GettingStarted.md)

Projects Using JEF
==================
JEF is very young library. If you're using JEF, feel free 
to [tell us about it](mailto:support@iot-hub.ru).  
Include some details about your company, project name, purpose and size and tell us 
how you use the library.

There are also a number of examples and projects within the `examples` directory of the JEF 
project itself.


Features
========
* Ability to access devices over SPI interface
* Ability to access devices over I2C interface
* Ability to access devices over Serial interface
* Ability to access GPIO over Linux GPIO interface
* Ability to compile you code to JVM packages and Native binaries (commercial version only) without 
  any code modification 

Community and Support
=====================
All questions should be posted to the [StackOverflow](http://stackoverflow.com/questions/tagged/jef). 
Issues can be submitted [here on Github](https://github.com/java-embedded-framework/jef/issues).

Contributing
============
Please see details [here](CONTRIBUTING.md)

License
=======
From 21.05.2022 licence changed to Apache2.0
Please see details [here](LICENSE)

For vendors
=======
If you want to add your board support to framework or 
add support of your devices to 
[device library](https://github.com/java-embedded-framework/jef/tree/master/device-library/src/main/java/ru/iothub/jef/devices/library) 
[please contact](mailto:support@iot-hub.ru)

What is planned in future releases?
=======
* Refactoring and improvements based on community feedbacks
* Migration to [Project Panama](https://openjdk.java.net/projects/panama/) from [Java Native Access](https://github.com/java-native-access/jna) for Java 16+
* Add additional devices support in device library
* Add support for other chipsets/boards (Orange Pi/Banana Pi/Olimex) in mcu-core module.
