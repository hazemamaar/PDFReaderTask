package com.example.pdfreader

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.text.method.ScrollingMovementMethod
import androidx.appcompat.app.AppCompatActivity
import com.example.pdfreader.databinding.ActivityMainBinding
import com.itextpdf.text.pdf.PdfReader
import com.itextpdf.text.pdf.parser.PdfTextExtractor
import java.util.*


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var textToSpeech: TextToSpeech? = null
    var extractedText = ""
    private val READ_REQUEST_CODE = 42
    private val PRIMARY = "primary"
    private val LOCAL_STORAGE = "/storage/self/primary/"
    private val EXT_STORAGE = "/storage/7764-A034/"
    private val COLON = ":"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.outputText.movementMethod = ScrollingMovementMethod()
        extractData()
        textToSpeech = TextToSpeech(
            applicationContext
        ) {
            if(it != TextToSpeech.ERROR)
               textToSpeech?.language = Locale.US
        }
      binding.fab.setOnClickListener {
          textToSpeech!!.speak(extractedText, TextToSpeech.QUEUE_FLUSH, null,null )
      }

//        binding.fab.setOnClickListener{
//                intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
//                intent.type = "*/*"
//            registerResult.launch(intent)
//            }

    }
//    private val registerResult =
//        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
//            if (it.data != null) {
//                if (it.resultCode == Activity.RESULT_OK) {
//                    val uri = it.data?.data.toString().toUri()
//                    Log.e("hhhh ", ": ${uri.path}", )
//                    readPdfFile("assets/haz.pdf".toUri())
//                }
//            }
//        }
//    fun readPdfFile(uri: Uri) {
//        val fullPath: String
//        //convert from uri to full path
//        fullPath = if (uri.path!!.contains(PRIMARY)) {
//            LOCAL_STORAGE + uri.path?.split(COLON)?.get(1)
//        } else {
//            EXT_STORAGE + uri.path?.split(COLON)?.get(1)
//        }
//        Log.v("URI", uri.path + " " + fullPath)
//        val stringParser: String
//        try {
//            val pdfReader = PdfReader(fullPath)
//            stringParser = PdfTextExtractor.getTextFromPage(pdfReader, 1).trim()
//            pdfReader.close()
//            Log.e("nnnn", "readPdfFile: $stringParser" )
//            binding.outputText.text = stringParser
//            textToSpeech!!.speak(stringParser, TextToSpeech.QUEUE_FLUSH, null, null)
//        } catch (e: IOException) {
//            e.printStackTrace()
//        }
//    }

    private fun extractData() {
        // on below line we are running a try and catch block
        // to handle extract data operation.
        try {
            // on below line we are creating a
            // variable for storing our extracted text


            // on below line we are creating a
            // variable for our pdf extracter.
            val pdfReader: PdfReader = PdfReader("res/raw/haz.pdf")

            // on below line we are creating
            // a variable for pages of our pdf.
            val n = pdfReader.numberOfPages

            // on below line we are running a for loop.
            for (i in 0 until n) {

                // on below line we are appending
                // our data to extracted
                // text from our pdf file using pdf reader.
                extractedText =
                    """
                 $extractedText${
                        PdfTextExtractor.getTextFromPage(pdfReader, i + 1).trim { it <= ' ' }
                    }
                  
                 """.trimIndent()
                // to extract the PDF content from the different pages
            }

            // on below line we are setting
            // extracted text to our text view.
            binding.outputText.text = extractedText


            // on below line we are
            // closing our pdf reader.
            pdfReader.close()

        }
        // on below line we are handling our
        // exception using catch block
        catch (e: Exception) {
            e.printStackTrace()
        }
    }
}