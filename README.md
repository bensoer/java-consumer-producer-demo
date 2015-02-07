# About
Currently there is only the demo using monitors, but a demo with smephores will be coming soon. Each folder contains the
uncompiled source files of each program. Monitors folder is using moinitors, and a semiphores folder will be coming with a
demo using semiphores

# Use
Download the repository and then from the console access the folders from outside of the type of consumer producer demo you 
would like to use. These classes have been built within packages so you have to run them appropriatly

To compile:
````
C:\MyGitRepo javac monitors/ConsumerProducer.java
````

To run:
````
C:\MyGitRepo java monitors/ConsumerProducer
````

# Optional Arguments
The ConsumerProducer program can be run with 2 additional optional arguments passed at initialization. The first parameter is the number of seconds the program will run before terminating itself. The second parameter is to give the size of the internal shared buffer. If either of these parameters are exempt default values of infinite time and 20 units in the buffer are used.
````
C:\MyGitRepo java monitors/ConsumerProducer 20  // runs the program for 20 seconds with default buffer size

C:\MyGitRepo java monitors/ConsumerProducer 45 12 // runs the program for 45 seconds with buffer size of 12 units
````
