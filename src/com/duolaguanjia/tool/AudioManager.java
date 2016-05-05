package com.duolaguanjia.tool;

import android.media.MediaRecorder;

import java.io.File;

public class AudioManager {
	private MediaRecorder mMediaRecoder;
	private String mDir;
	private String mCurrentFilePath;
	private static AudioManager mInstance;

	public interface AudioStateListener {
		void wellPrepared();
	}

	public AudioStateListener mListener;

	private AudioManager(String dir) {
		mDir=dir;
	};

	public void setOnAudioStateListener(AudioStateListener audioState) {
		mListener = audioState;
	}

	public static AudioManager getInstance(String dir) {
		if (mInstance == null) {
			synchronized (AudioManager.class) {
				if (mInstance == null) {
					mInstance = new AudioManager(dir);
				}
			}
		}
		return mInstance;
	}

	boolean isPrepared;

	/**
	 * 准备
	 * 
	 * @param isPrepared
	 */
	public void preparAudio() {
		try {
			isPrepared = false;
			File dir = new File(mDir);
			if (!dir.exists()) {
				dir.mkdir();
			}
			String fileName = generateFileName();
			File file = new File(dir, fileName);
			mCurrentFilePath = file.getAbsolutePath();
			mMediaRecoder=new MediaRecorder();
			// 设置输出文件
			mMediaRecoder.setOutputFile(file.getAbsolutePath());
			// 设置麦克风
			mMediaRecoder.setAudioSource(MediaRecorder.AudioSource.MIC);
			// 格式
			mMediaRecoder.setOutputFormat(MediaRecorder.OutputFormat.RAW_AMR );
			// 设置音频编码AMR
			mMediaRecoder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
			mMediaRecoder.prepare();// 准备
			mMediaRecoder.start();
			// 准备结束
			isPrepared = true;
			if (mListener != null) {
				mListener.wellPrepared();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 随机生成文件的名称
	 * 
	 * @return
	 */
	private String generateFileName() {
		// TODO Auto-generated method stub
		return "56d68667.amr";
	}

	/**
	 * 获取音量等级
	 * 
	 * @return
	 */
	public int getVoiceLevel(int maxLevel) {
		if (isPrepared) {
			// 1-32767
			try {
				return maxLevel * mMediaRecoder.getMaxAmplitude() / 32768 + 1;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return 1;

	}

	/**
	 * 取消
	 */
	public void cancel() {
		release();
		if (mCurrentFilePath != null) {
			File file = new File(mCurrentFilePath);
			file.delete();
			mCurrentFilePath = null;
		}

	}

	public void release() {
		try {
			if (mMediaRecoder!=null) {
				mMediaRecoder.setOnErrorListener(null);
				mMediaRecoder.stop();
				mMediaRecoder.release();
				mMediaRecoder = null;
			}
		} catch (IllegalStateException e) {
			e.printStackTrace();
		}
	}

	public String getCurrentFilePath() {
		return mCurrentFilePath;
	}
}
