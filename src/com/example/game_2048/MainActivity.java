package com.example.game_2048;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends Activity {
	private TextView tvscoreTextView;
	private static MainActivity mainActivity = null;
	private int score = 0;

	public MainActivity() {
		mainActivity = this;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		tvscoreTextView = (TextView) findViewById(R.id.tv_Score);
	}

	public static MainActivity getMainActivity() {
		return mainActivity;
	}

	public void clearScore() {
		score = 0;
		showScore();
	}

	public void showScore() {
		tvscoreTextView.setText(score + "");
	}
	
	public void addScore(int s){
		score+=s;
		showScore();
	}

}
