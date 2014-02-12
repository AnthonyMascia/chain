package com.example.dreamjournal;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class Activity_AddDream extends Activity {

	LinkedList<String> dreamList;
	Builder deleteDreamBuilder;
	AlertDialog deleteDreamDialog;
	ImageView img_saved, img_deleted;
	EditText dreamEntry;
	CheckBox cb_nightmare;
	String DREAM_DATA;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_adddream);
		
		dreamList = new LinkedList<String>();
		
		img_saved = (ImageView) findViewById(R.id.img_saved);
		img_deleted= (ImageView) findViewById(R.id.img_deleted);
		dreamEntry = (EditText) findViewById(R.id.et_dreamEntry);
		cb_nightmare = (CheckBox) findViewById(R.id.cb_nightmare);
		setUpAlertDialog();
		
		checkTouched();
	}

	private void setUpAlertDialog() {
		deleteDreamBuilder = new AlertDialog.Builder(this);
		deleteDreamBuilder.setMessage("Are you sure you want to delete this dream?");
		deleteDreamBuilder.setCancelable(false);
		deleteDreamBuilder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Activity_AddDream.this.finish();
			}
		});
		
		deleteDreamBuilder.setPositiveButton("Nevermind", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});
		
		deleteDreamDialog = deleteDreamBuilder.create();
		
		
		
		
		
	}

	private void checkTouched() {

		img_saved.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				String dream = dreamEntry.getText().toString();
				dreamList.add(dream);
				
				int dreamsSavedAmt = dreamList.size();
				
				FileOutputStream fos;
				try{
					fos = openFileOutput("Dream " + dreamsSavedAmt, Context.MODE_PRIVATE);
					fos.write(dream.getBytes());
					fos.close();
					Toast.makeText(getApplicationContext(), "Dream Saved", Toast.LENGTH_SHORT).show();
				} catch (Exception e){
					System.out.println("fail");
					e.printStackTrace();
				}
				
				 try {
				        FileInputStream inputStream = openFileInput(DREAM_DATA);
				        BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));
				        StringBuilder total = new StringBuilder();
				        String line;
				        while ((line = r.readLine()) != null) {
				            total.append(line);
				        }
				        dreamEntry.setText(line + "1311");
				        r.close();
				        inputStream.close();
				        Log.d("File", "File contents: " + total);
				    } catch (Exception e) {
				        e.printStackTrace();
				    }
			}
		});
		
		img_deleted.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				deleteDreamDialog.show();
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
