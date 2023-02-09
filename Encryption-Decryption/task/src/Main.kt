import java.io.File

const val A_CODE = 'A'.code
const val Z_CODE = 'A'.code
const val a_CODE = 'A'.code
const val z_CODE = 'A'.code

fun encUnicode(text: String, key: Int): String{
    var output = ""
    for (letter in text){
        output += (letter.code + key).toChar()
    }
    return output
}

fun decUnicode(text: String, key: Int): String{
    var output = ""
    for (letter in text){
        output += (letter.code - key).toChar()
    }
    return output
}


fun encShift(text: String, key: Int): String{
    var output = ""
    for (ch in text){
        when (ch.code) {
            in 65..90 -> {
                val sum = (ch.code + key - 65) % 26 + 65
                output += sum.toChar()
            }
            in 97 .. 122 -> {
                val sum = (ch.code + key - 97) % 26 + 97
                output += sum.toChar()
            }
            else -> output += ch
        }
    }

    return output
}

fun decShift(text: String, key: Int): String{
    var output = ""
    for (ch in text){
        when (ch.code) {
            in 65..90 -> {
                val sum = if(ch.code - key < 65){
                     ch.code % 65 + 90 - key
                }
                else  ch.code - key
                output += sum.toChar()
            }
            in 97 .. 122 -> {
                val sum = if(ch.code - key < 97){
                    ch.code % 97 + 122 - key + 1
                }
                else  ch.code - key
                output += sum.toChar()
            }
            else -> output += ch
        }
    }
    return output
}

fun main(args: Array<String>) {
    var mode = "enc"
    var algorithm = "shift"
    var key: Int = 0
    var data = ""
    var inFile = ""
    var outFile = ""
    for (i in args.indices){
        if (args[i] == "-mode") mode = args[i + 1]
        else if (args[i] == "-in") inFile = args[i + 1]
        else if (args[i] == "-out") outFile = args[i + 1]
        else if (args[i] == "-alg") algorithm = args[i + 1]
        else if (args[i] == "-key") key = args[i + 1].toInt()
        else if (args[i] == "-data") data = args[i + 1]
    }
    if (inFile != ""){
        data = File(inFile).readText()
    }
    var output = ""
    output = if(algorithm == "shift"){
        when(mode){
            "enc" -> encShift(data, key)
            "dec" -> decShift(data, key)
            else -> return
        }
    }
    else{
        when(mode){
            "enc" -> encUnicode(data, key)
            "dec" -> decUnicode(data, key)
            else -> return
        }
    }

    if (outFile != ""){
        File(outFile).writeText(output)
    }
    else print(output)

}