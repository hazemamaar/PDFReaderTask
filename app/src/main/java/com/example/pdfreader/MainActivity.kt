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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.outputText.movementMethod = ScrollingMovementMethod()

        textToSpeech = TextToSpeech(
            applicationContext
        ) {
            if (it != TextToSpeech.ERROR)
                textToSpeech?.language = Locale.US
        }
        textToSpeech()
        extractData()


    }

    private fun textToSpeech() {
        binding.fab.setOnClickListener {
            textToSpeech!!.speak(extractedText, TextToSpeech.QUEUE_FLUSH, null, null)
        }
    }

    private fun extractData() {
        try {
            val pdfReader = PdfReader("res/raw/haz.pdf")
            val n = pdfReader.numberOfPages
            for (i in 0 until n) {
                extractedText =
                    """
                 $extractedText${
                        PdfTextExtractor.getTextFromPage(pdfReader, i + 1).trim { it <= ' ' }
                    }
                  
                 """.trimIndent()
            }
            binding.outputText.text = extractedText
            pdfReader.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}