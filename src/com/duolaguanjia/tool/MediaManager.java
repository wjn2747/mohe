package com.duolaguanjia.tool;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;

import java.io.IOException;

public class MediaManager {
	
	private static MediaPlayer mMediaPlay;
	
	private static boolean isPause;
	
	
	public  static void  playSound(String filePath,OnCompletionListener  listener)
	{
		if (filePath==null || filePath.equalsIgnoreCase("")) {
			return ;
		}
		if (mMediaPlay==null) {
			mMediaPlay=new MediaPlayer();
			mMediaPlay.setOnErrorListener(new OnErrorListener() {
				
				@Override
				public boolean onError(MediaPlayer arg0, int arg1, int arg2) {
					mMediaPlay.reset();
					return false;
				}
			});
		}else {
			mMediaPlay.reset();
		}
		try {
			mMediaPlay.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mMediaPlay.setOnCompletionListener(listener);
			mMediaPlay.setDataSource(filePath);
			mMediaPlay.prepareAsync();
			mMediaPlay.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
				@Override
				public void onPrepared(MediaPlayer mp) {
					mMediaPlay.start();
				}
			});
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public  static void  pause()
	{
		if (mMediaPlay!=null && mMediaPlay.isPlaying()) {
			mMediaPlay.pause();
			isPause=true;
		}
	}
	
	public  static void  onResume()
	{
		if (mMediaPlay!=null && isPause) {
			mMediaPlay.start();
			isPause=false;
		}
	}
	public  static void  release()
	{
		if (mMediaPlay!=null ) {
			mMediaPlay.release();
			mMediaPlay=null;
		}
	}
}
