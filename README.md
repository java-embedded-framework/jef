Java Embedded Framework (JEF)
========================

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
[One Wire](https://en.wikipedia.org/wiki/1-Wire) interfaces without writing 
anything except Java code - no JNI or native additional code is required. This functionality is comparable to 
Linux like platforms.

JEF allows you to create pure java based system only in Java or compile you applications 
to small(10+ megabytes) pure native binaries(C++ like ) for 64-bits platforms (commercial version only) 
without any code modification. 

Projects Using JEF
==================
JEF is very young library. If you're using JNA, feel free 
to [tell us about it](mailto:support@iot-hub.ru).  
Include some details about your company, project name, purpose and size and tell us 
how you use the library.

There are also a number of examples and projects within the `examples` directory of the JEF 
project itself.

Supported Platforms
===================
JEF will build on most linux-like platforms with a reasonable set of GNU tools and a JDK.

If your platform is supported by [libffi](http://en.wikipedia.org/wiki/Libffi), then chances 
are you can build JEF for it.

If your platform is supported by [GraalVM Native Image Compiler](https://github.com/graalvm/graalvm-ce-builds/releases/), 
then chances are you can build JEF binaries images for these platforms...

Download
========

Version 0.1-ALPHA
You can download directly from github and build it.
For the future releases' library will be added to maven repository.

Features
========
* Ability to access devices over SPI interface
* Ability to access devices over I2C interface
* Ability to access devices over Serial interface
* Direct access to GPIO over memory (RPi-4 only supported at this moment)
* Ability to compile you code to JVM packages and Native binaries (commercial only) without 
  any code modification 

Community and Support
=====================
All questions should be posted to the [StackOverflow](http://stackoverflow.com/questions/tagged/jef). 
Issues can be submitted [here on Github](https://github.com/java-embedded-framework/jef/issues).

For commercial support, please contact [here](mailto:sales@iot-hub.ru).

Using the Library
=================
* [Getting Started](GettingStarted.md)

Contributing
============
Please see details [here](CONTRIBUTING.md)

License
=======
Please see details [here](LICENSE)

What is planned in future releases?
=======
* Refactoring and improvements based on community feedbacks
* Add additional devices support in device library
* Add support for other devices based on BCM2711 chipset (CM4/CM4 lite/400)
* Add support for other Broadcom chipsets like BCM2835(RPi 1/2/3/Zero) and BCM2837(RPi 2B/3B/CM3)
* Add support for Allwinner chipsets (A63/A64/A133 as first) and boards.
* Integration with Quarkus
* Integration with Spring
