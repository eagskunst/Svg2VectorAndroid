# Svg2VectorAndroid

Convert multiple .svg files to vector drawables for Android.

This forks servers as minimal enchantment of the original repository. The Google algorithm leaves the default width and 
height of the served .svg. This could lead to compilation problems where the compiler throws an OutOfMemory exception.
Also, for some reason it ads '_pt_' to some measures, which also leads to compilation problems. That is why I created a 
Python script (_cleaner.py_) that would optimize the files to the minimum required width (200dp) and height.

## Compiling/Executing
### Java project 

Install and configure Kotlin in your favorite IDE if you have not.
* Clone the repository.
* Open in on your favorite IDE.
* Configure the program arguments (Eg: Edit configurations -> Program arguments in IntelliJ). The arguments are separated
by spaces.
* Run
You can also change the code in Runner.main to a custom instance of SvgFilesProcessor

### Python script
* Install Python3 on your system if you have not.
* After running the SvgFilesProcessor, copy the _cleaner.py_ to the directory with the processed files (source/ProcessedSVG by default).
* Execute the following command while in the directory:
    
        $ python3 cleaner.py

## Known issues
* The Kotlin project needs to be executed two times because in the first one throws a 
IllegalArgumentException caused by a null value that in fact is not null in any moment.
* The _cleaner.py_ script could throw an exception when the width or height is a Float.
To solve this, round the values of the .xml file with floating values (eg: 205.53dp -> 206dp)

## Plans
* Fix issues.
* Migrate Python3 code to Kotlin.
* Create .jar .
* Create UI for the process.