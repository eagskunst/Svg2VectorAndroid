package com.vector.svg2vectorandroid

/**
 * Created by ravi on 19/12/17.
 */

object Runner {
    @JvmStatic
    fun main(args: Array<String>) {
        if (args.isEmpty()) {
            println("Provide source directory as first argument for svg files to be converted\n Example: java -jar Svg2VectorAndroid-1.0.jar <SourceDirectoryPath> ")
            return
        }
        val processor =
                when (args.size) {
                    1 -> SvgFilesProcessor(args[0])
                    2 -> SvgFilesProcessor(args[0], args[1])
                    3 -> SvgFilesProcessor(args[0], args[1], args[2])
                    else -> SvgFilesProcessor(args[0], args[1], args[2], args[3])
                }
        val sourceDirectory = args[0]

        if (sourceDirectory.isNotEmpty()) {
            println("Processing files in source: $sourceDirectory")
            processor.process()
        }
    }
}