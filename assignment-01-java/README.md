# Assignment 01 Concurrent Programming in Java
## Prerequisites
Instructions for Ubuntu 20.04
```bash
sudo apt-get install default-jre    # Install JRE
export JAVA_HOME=$(readlink -f /usr/bin/java | sed "s:bin/java::")    # Making sure JAVA_HOME is set
javac -version    # Make sure javac is working and preferably relatively updated
java -version    # Make sure java is working and preferably relatively updated
```
## How to run
```bash
make answer-01    # Build and run answer 01
make answer-02    # Build and run answer 02
make answer-03-a    # Build and run answer 03 variant (a)
make answer-03-b    # Build and run answer 03 variant (b)
make help    # Display help
make clean    # Clean up
```