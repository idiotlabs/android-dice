package idiotlabs.dice;

import android.app.Activity;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.Random;

public class MainActivity extends Activity {
    private ImageView ivDice;
    private AudioManager am;
    private float x = 0;
    private float y = 0;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            showDice(new Random().nextInt(6) + 1, msg.what);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ivDice = (ImageView)findViewById(R.id.dice);

        //am = (AudioManager)getSystemService(Context.AUDIO_SERVICE);

        MobileAds.initialize(getApplicationContext(), "ca-app-pub-8206166796422159~4404315621");

        AdView mAdView = (AdView) findViewById(R.id.adView);
        // live
        AdRequest adRequest = new AdRequest.Builder().build();
        // test
//        AdRequest adRequest = new AdRequest.Builder()
//                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
//                .addTestDevice("3EA55B11DB92B9BE")
//                .build();
        mAdView.loadAd(adRequest);
    }

    public void DiceClick(View arg0) {

        // get Touch Position in ImageView
        arg0.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                x = event.getX();
                y = event.getY();
                return false;
            }
        });

        // Rolling...?
        new Thread(new Runnable() {
            public void run() {
                for (int i = 0; i < 20; i++) {
                    try {
                        //handler.sendMessage(handler.obtainMessage());
                        handler.sendEmptyMessage(i);
                        Thread.sleep(15);
                    } catch (Throwable t) {
                    }
                }
            }
        }).start();

        // Get Ring Volume
//        float volume = am.getStreamVolume(AudioManager.STREAM_RING);
    }

    public void showDice(int rnd, int loop) {
        if (loop >= 19 && x > 340 && y > 340) {
            rnd = new Random().nextInt(3) + 4;
        }
        else if (loop >= 19 && x < 90 && y < 90) {
            rnd = new Random().nextInt(3) + 1;
        }

        switch(rnd) {
            case 1:
                ivDice.setImageResource(R.drawable.one);
                break;
            case 2:
                ivDice.setImageResource(R.drawable.two);
                break;
            case 3:
                ivDice.setImageResource(R.drawable.three);
                break;
            case 4:
                ivDice.setImageResource(R.drawable.four);
                break;
            case 5:
                ivDice.setImageResource(R.drawable.five);
                break;
            case 6:
                ivDice.setImageResource(R.drawable.six);
                break;
            default:
        }
    }

    /*
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // Hidden VolumeControl
        switch (event.getKeyCode()) {
            case KeyEvent.KEYCODE_VOLUME_UP:
                am.adjustVolume(AudioManager.ADJUST_RAISE, 0);
                return true;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                am.adjustVolume(AudioManager.ADJUST_LOWER, 0);
                return true;
            default:
                return super.onKeyDown(keyCode, event);
        }
    }
    */

    @Override
    protected void onPause() {
        super.onPause();
    }

    protected void onDestroy() {
        super.onDestroy();
    }
}
