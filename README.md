# brainwaves-to-audio

## Purpose ##
Read OSC data from Mind Monitor to PC 
and convert the brainwave data to audio.

## Features ##
* Generate sound for each brainwave type
* Change sound amplitude according to brainwave power
* Beep if the data quality is bad (blinking, jaw clench, sensors not fitting well) 

## Requirements ##
* Java (15 or later recommended)

## Configuration ##
* config.properties

## Run via Gradle ##
1. Change to project directory
1. `gradle run`

## Run as JAR ##
1. Change to project directory
1. Build JAR using `gradle fatJar`
1. Run `java -jar JARFILE.jar`
