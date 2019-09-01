package idiotlabs.dice

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.View
import android.widget.ImageView

import com.crashlytics.android.Crashlytics
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView

import io.fabric.sdk.android.Fabric
import java.util.Random

class MainActivity : Activity() {
    private var ivDice: ImageView? = null
    private var x = 0f
    private var y = 0f

    private val handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            showDice(Random().nextInt(6) + 1, msg.what)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Fabric.with(this, Crashlytics())
        setContentView(R.layout.activity_main)

        // alertdialog
        val sharedPref = getPreferences(Context.MODE_PRIVATE)
        val isInitialized = sharedPref.getBoolean("INIT", false)

        if (false) {
            this.simpleAlert()

            val editor = sharedPref.edit()
            editor.putBoolean("INIT", true)
            editor.apply()
        }

        ivDice = findViewById<View>(R.id.dice) as ImageView

        // ads
//        MobileAds.initialize(applicationContext, "ca-app-pub-8206166796422159~4404315621")
        MobileAds.initialize(this)

        val mAdView = findViewById<View>(R.id.adView) as AdView
        // live
        val adRequest = AdRequest.Builder().build()
        // test
        //        AdRequest adRequest = new AdRequest.Builder()
        //                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
        //                .addTestDevice("3EA55B11DB92B9BE")
        //                .build();
        mAdView.loadAd(adRequest)
    }

    fun DiceClick(arg0: View) {

        try {
            // get Touch Position in ImageView
            arg0.setOnTouchListener { v, event ->
                x = event.x
                y = event.y
                false
            }

            // Rolling
            Thread(Runnable {
                for (i in 0..19) {
                    try {
                        //handler.sendMessage(handler.obtainMessage());
                        handler.sendEmptyMessage(i)
                        Thread.sleep(15)
                    } catch (t: Throwable) {
                    }

                }
            }).start()

        } catch (e: Exception) {
            throw RuntimeException(e)
        }

    }

    fun showDice(rnd: Int, loop: Int) {
        var rnd = rnd

        if (loop >= 19 && x > 320 && y > 320) {
            rnd = Random().nextInt(3) + 4
        } else if (loop >= 19 && x < 90 && y < 90) {
            rnd = Random().nextInt(3) + 1
        }

        when (rnd) {
            1 -> ivDice!!.setImageResource(R.drawable.one)
            2 -> ivDice!!.setImageResource(R.drawable.two)
            3 -> ivDice!!.setImageResource(R.drawable.three)
            4 -> ivDice!!.setImageResource(R.drawable.four)
            5 -> ivDice!!.setImageResource(R.drawable.five)
            6 -> ivDice!!.setImageResource(R.drawable.six)
        }
    }

    fun simpleAlert() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("너무 궁금해서요")
        builder.setMessage("설문조사 하나만 해주실 수 있을까요?")
        builder.setPositiveButton("네"
        ) { dialog, which ->
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://goo.gl/forms/PRFPsNNxoGosIXhv2"))
            startActivity(browserIntent)
            dialog.dismiss()
        }
        builder.setNegativeButton("아니오"
        ) { dialog, which ->
            dialog.dismiss()
            dialog.dismiss()
        }
        builder.setCancelable(false)
        builder.show()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
