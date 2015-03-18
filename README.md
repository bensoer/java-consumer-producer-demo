# About
Each folder contains the uncompiled source files of each program. Monitors folder is using moinitors, and the semiphores folder is using semiphores.

The monitor program has some additional features to it as specified below. The semaphore program though only runs as a general demo.

# Use
Download the repository and then from the console access the folders from outside of the type of consumer producer demo you 
would like to use. These classes have been built within packages so you have to run them appropriatly

To compile:
````
C:\java-consumer-producer-demo javac monitors/ConsumerProducer.java
C:\java-consumer-producer-demo javac semaphores/ConsumerProducer.java
````

To run:
````
C:\java-consumer-producer-demo java monitors/ConsumerProducer
C:\java-consumer-producer-demo java semaphores/ConsumerProducer
````

# Monitor: Optional Arguments
The ConsumerProducer implementation with monitors program can be run with 2 additional optional arguments passed at initialization. The first parameter is the number of seconds the program will run before terminating itself. The second parameter is to give the size of the internal shared buffer. If either of these parameters are exempt default values of infinite time and 20 units in the buffer are used.
````
C:\MyGitRepo java monitors/ConsumerProducer 20  // runs the program for 20 seconds with default buffer size

C:\MyGitRepo java monitors/ConsumerProducer 45 12 // runs the program for 45 seconds with buffer size of 12 units
````
