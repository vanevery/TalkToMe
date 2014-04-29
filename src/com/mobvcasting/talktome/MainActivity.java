package com.mobvcasting.talktome;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {
	public static final String LOGTAG = "SPEECH";
	public static final int SPEECH_RECOGNITION = 0;
	Button talkButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		talkButton = (Button) this.findViewById(R.id.button1);
		talkButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		
		// Create an intent to trigger the built-in voice recognition engine/interface
		// http://developer.android.com/reference/android/speech/RecognizerIntent.html
		Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH); 

		// Tell the user what the topic is
		intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Tell me a story");

		// There are two language models available or recognition
		  // LANGUAGE_MODEL_WEB_SEARCH : web search terms
		  // LANGUAGE_MODEL_FREE_FORM  : free-form speech
		intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);

		// When the recogniser is done, it will trigger onActivityResult
		startActivityForResult(intent, SPEECH_RECOGNITION);
	}
	
	@Override
	 protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// The right request
		if (requestCode == SPEECH_RECOGNITION) {
			// The right result code
			if (resultCode == RESULT_OK) {
				// ArrayList of all of the speech results
				ArrayList<String> speechResults = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
				if (!speechResults.isEmpty()) {
					// Loop through the results and do something
					for (int i = 0; i < speechResults.size(); i++) {
						Log.v(LOGTAG, speechResults.get(i));
						Toast.makeText(this, speechResults.get(i), Toast.LENGTH_SHORT).show();
						
						
					}
					
					// OR just use the first result
					if (speechResults.get(0).startsWith("call")) {
						// Create the call intent
						Log.v(LOGTAG, "Starts with call");
					} else if (speechResults.get(0).startsWith("sms")) {
						// Create the sms intent
						Log.v(LOGTAG, "Starts with sms");
					} else if (speechResults.get(0).startsWith("email")) {
						// Create the email intent
						Log.v(LOGTAG, "Starts with email");
					}
				}
			}
			else if (resultCode == RecognizerIntent.RESULT_AUDIO_ERROR ||
					resultCode == RecognizerIntent.RESULT_CLIENT_ERROR ||
					resultCode == RecognizerIntent.RESULT_NETWORK_ERROR ||
					resultCode == RecognizerIntent.RESULT_NO_MATCH ||
					resultCode == RecognizerIntent.RESULT_SERVER_ERROR)
			{
				// There was an error.. 
				Toast.makeText(this, "Error: " + resultCode, Toast.LENGTH_SHORT).show();
			}
		}

		super.onActivityResult(requestCode, resultCode, data);
	}
}
