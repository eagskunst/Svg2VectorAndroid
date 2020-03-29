package com.vector.svg2vectorandroid

import com.android.ide.common.vectordrawable.Svg2Vector
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.lang.IllegalArgumentException
import java.nio.file.*
import java.nio.file.attribute.BasicFileAttributes
import java.util.*

/**
 * Created by ravi on 18/12/17.
 */
class SvgFilesProcessor @JvmOverloads
constructor(private val sourceSvgDirectory: String, destinationVectorDirectory: String = "$sourceSvgDirectory/ProcessedSVG",
            fileExtension: String = "xml", fileSuffix: String = "_svg") {

    private val destinationVectorPath: Path = Paths.get(destinationVectorDirectory)
    private val extension: String = fileExtension
    private val extensionSuffix: String = fileSuffix

    fun process() {
        try {
            val options = EnumSet.of(FileVisitOption.FOLLOW_LINKS)
            val sourceSvgPath: Path = Paths.get(sourceSvgDirectory)
            //check first if source is a directory
            if (Files.isDirectory(sourceSvgPath)) {
                Files.walkFileTree(Paths.get(sourceSvgDirectory), options, Int.MAX_VALUE, object : FileVisitor<Path> {
                    @Throws(IOException::class)
                    override fun postVisitDirectory(dir: Path,
                                                    exc: IOException): FileVisitResult {
                        return FileVisitResult.CONTINUE
                    }

                    override fun preVisitDirectory(dir: Path,
                                                   attrs: BasicFileAttributes): FileVisitResult { // Skip folder which is processing svgs to xml
                        if (dir == destinationVectorPath) {
                            return FileVisitResult.SKIP_SUBTREE
                        }
                        val opt = arrayOf<CopyOption>(StandardCopyOption.COPY_ATTRIBUTES, StandardCopyOption.REPLACE_EXISTING)
                        val newDirectory = destinationVectorPath.resolve(sourceSvgPath.relativize(dir))
                        try {
                            Files.copy(dir, newDirectory, *opt)
                        } catch (ex: FileAlreadyExistsException) {
                            println("FileAlreadyExistsException $ex")
                        } catch (x: IOException) {
                            return FileVisitResult.SKIP_SUBTREE
                        }
                        return FileVisitResult.CONTINUE
                    }

                    @Throws(IOException::class)
                    override fun visitFile(file: Path,
                                           attrs: BasicFileAttributes): FileVisitResult {
                        convertToVector(file, destinationVectorPath.resolve(sourceSvgPath.relativize(file)))
                        return FileVisitResult.CONTINUE
                    }

                    @Throws(IOException::class)
                    override fun visitFileFailed(file: Path,
                                                 exc: IOException): FileVisitResult {
                        return FileVisitResult.CONTINUE
                    }
                })
                println("Files generated.")
            } else {
                println("source not a directory")
            }
        } catch (e: IOException) {
            println("IOException: ${e.message}")
        }catch (e: IllegalArgumentException){
            println("Please re-run the program")
            e.printStackTrace()
        }
    }

    @Throws(IOException::class)
    private fun convertToVector(source: Path, target: Path) { // convert only if it is .svg
        if (source.fileName.toString().endsWith(".svg")) {
            val targetFile = getFileWithXMlExtention(target, extension, extensionSuffix)
            val fous = FileOutputStream(targetFile)
            Svg2Vector.parseSvgToXml(source.toFile(), fous)
        } else {
            println("Skipping file as its not svg " + source.fileName.toString())
        }
    }

    private fun getFileWithXMlExtention(target: Path, extention: String, extentionSuffix: String?): File {
        val svgFilePath = target.toFile().absolutePath
        val svgBaseFile = StringBuilder()
        val index = svgFilePath.lastIndexOf(".")
        if (index != -1) {
            val subStr = svgFilePath.substring(0, index)
            svgBaseFile.append(subStr)
        }
        svgBaseFile.append(extentionSuffix ?: "")
        svgBaseFile.append(".")
        svgBaseFile.append(extention)
        return File(svgBaseFile.toString())
    }

}