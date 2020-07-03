package ca.six.demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ca.six.log.L
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvMain.setOnClickListener {
            println("szw click")
            L.d("hello SixLog")

            SomeHelper.work()
        }
    }
}