# Svg2VectorAndroid

This project helps to batch convert svg files to vector drawable xmls in one shot, with option to provide extention and extention suffix.

Simply pass source directory path to SvgFilesProcessor and call process.

Sample below:

SvgFilesProcessor processor = new SvgFilesProcessor("/Volumes/Development/Features/MySvgs");
processor.process();

This will create a new folder "ProcessedSvgs" inside source folder.

It uses studio ide class Svg2Vector class implementation (reference https://android.googlesource.com/platform/tools/base/+/master/sdk-common/src/main/java/com/android/ide/common/vectordrawable/Svg2Vector.java)
to parse svg and convert to xml file.

If you directly want to use the jar , use as below:

java -jar Svg2VectorAndroid-1.0.jar "SourceDirectoryPath"
