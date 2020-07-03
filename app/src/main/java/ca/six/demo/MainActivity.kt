package ca.six.demo

import android.os.Bundle
import android.os.Handler
import android.os.Message
import androidx.appcompat.app.AppCompatActivity
import ca.six.log.L
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            L.trace("handler")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvMain.setOnClickListener {
            L.d("hello SixLog")
            SomeHelper.work()
        }

        L.trace("onCreate")

        handler.sendEmptyMessageDelayed(11, 2000)
    }
}