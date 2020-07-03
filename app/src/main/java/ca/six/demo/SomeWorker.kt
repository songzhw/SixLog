package ca.six.demo

import android.os.AsyncTask
import ca.six.log.L

class SomeWorker {
    fun doIt() {
        val task = object : AsyncTask<Unit, Unit, Unit>() {
            override fun doInBackground(vararg params: Unit?) {
                Thread.sleep(2500)
            }

            override fun onPostExecute(result: Unit?) {
                L.trace("AsycTask")
            }
        }

        task.execute()
    }
}