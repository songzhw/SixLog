package ca.six.demo

import android.os.AsyncTask

class SomeWorker {
    fun doIt() {
        val task = object : AsyncTask<Unit, Unit, Unit>() {
            override fun doInBackground(vararg params: Unit?) {
                Thread.sleep(2500)
            }

            override fun onPostExecute(result: Unit?) {
                ThirdMan().three()
            }
        }

        task.execute()
    }
}