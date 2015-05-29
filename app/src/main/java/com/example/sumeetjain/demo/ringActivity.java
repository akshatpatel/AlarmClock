package com.example.sumeetjain.demo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.Vibrator;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import java.io.IOException;

public class ringActivity extends Activity
{
        private static MediaPlayer mediaPlayer;
        private static Vibrator vibrator;
        private static AudioManager volMgr;
        private static int RINGERMODE;

   @Override
    protected void onCreate(Bundle savedInstanceState)
   {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.cancelalarm);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

            volMgr = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            switch (volMgr.getRingerMode())
            {
                case AudioManager.RINGER_MODE_SILENT:
                        RINGERMODE = 1;
                        volMgr.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                break;

                case AudioManager.RINGER_MODE_VIBRATE:
                        RINGERMODE = 2;
                        volMgr.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                break;

                case AudioManager.RINGER_MODE_NORMAL:
                        RINGERMODE = 3;
                        volMgr.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                break;
        }

        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (AlarmDisplayActivity.buttonVibrate)
        {
            vibrator.vibrate(new long[]{1000, 1000}, 0);
        }
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setLooping(true);
            try
           {
                      mediaPlayer.setDataSource(getApplicationContext(), AlarmDisplayActivity.ringtoneUri);
           }
            catch (IllegalArgumentException e)
            {
                      e.printStackTrace();
            }
            catch (SecurityException e)
            {
                      e.printStackTrace();
            }
            catch (NullPointerException e)
            {
                      e.printStackTrace();
            }
            catch (IllegalStateException e)
            {
                      e.printStackTrace();
            }
            catch (IOException e)
            {
                      e.printStackTrace();
            }

            try
            {
                     mediaPlayer.prepare();
            }
            catch (IllegalStateException e)
            {
                    e.printStackTrace();
            }
            catch (IOException e)
            {
                     e.printStackTrace();
            }
             mediaPlayer.start();
            //wake and unlock the screen
           PowerManager pm=(PowerManager)getSystemService(POWER_SERVICE);
           PowerManager.WakeLock wakeLock=pm.newWakeLock(PowerManager.PROXIMITY_SCREEN_OFF_WAKE_LOCK,"wakelog tag");
           wakeLock.acquire(100);
        }

     @Override
        public boolean onKeyDown(int keyCode, KeyEvent event)
        {
                if (keyCode == KeyEvent.KEYCODE_BACK){
                return true;
        }
        return true;
    }

    public void ringCancel(View v)
    {
        //stop serivce
            this.stopService(new Intent(this,AlarmService.class));
        //stop playing music
            mediaPlayer.pause();
            mediaPlayer=null;
        //stop vibration if any
            if(AlarmDisplayActivity.buttonVibrate)
            {
                    vibrator.cancel();
            }
            vibrator=null;
        //recover the RingerMode
            switch (RINGERMODE)
            {
                case 1:
                        volMgr.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                        break;
                case 2:
                        volMgr.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
                        break;
                case 3:
                        volMgr.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                        break;
            }

        volMgr=null;
        this.finish();
    }
}
