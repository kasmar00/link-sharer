package io.github.kasmar00.linksharer


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


class Sharing : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        when {
            intent?.action == Intent.ACTION_SEND -> {
                if ("text/plain" == intent.type) {
                    handleSendText(intent) // Handle text being sent
                } else if (intent.type?.startsWith("image/") == true) {
//                    handleSendImage(intent) // Handle single image being sent
                }
            }

            intent?.action == Intent.ACTION_SEND_MULTIPLE && intent.type?.startsWith("image/") == true -> {
//                handleSendMultipleImages(intent) // Handle multiple images being sent
            }

            else -> {
            }
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sharing)

        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, text)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }

    private lateinit var text: String

    private fun handleSendText(intent: Intent) {
        intent.getStringExtra(Intent.EXTRA_TEXT)?.let {
            // Update UI to reflect text being shared
            println(it)
            text = clearTrackingParams(replaceHost(it))
        }
    }

    private fun clearTrackingParams(text: String) =
        Uri.parse(text).buildUpon().clearQuery().build().toString()

    private fun replaceHost(text: String) = if (listOf(
            "fixupx",
            "fxtwitter",
            "ddinstagram"
        ).none { it2 -> text.contains(it2) }
    ) text.replace("twitter.com", "fxtwitter.com")
        .replace("x.com", "fixupx.com")
        .replace("instagram.com", "ddinstagram.com")
    else text
}