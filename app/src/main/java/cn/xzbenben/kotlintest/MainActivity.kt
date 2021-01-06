package cn.xzbenben.kotlintest

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    }

    fun turnAichang(view: View) {
        val uri =
            Uri.parse("aichang://djturn?data_type=51&banzouid=86403154")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)


    }

    fun turnAichangNoBzid(view: View) {
        val uri =
            Uri.parse("aichang://djturn?data_type=51")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)


    }

    fun turnAichangCarsShop(view: View) {
        val uri =
            Uri.parse("aichang://aichang?data_type=52")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)


    }

    fun turnFrontLine(view: View) {
        val uri =
            Uri.parse("frontline://xxx?action=0")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)


    }

}