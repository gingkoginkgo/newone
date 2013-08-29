package com.example.newone;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


public class NewAddToDo extends Activity {
	private Button _Done;
	private RadioGroup _ActionType;
	private EditText _StartTime;
	private EditText _Deadline;
	private EditText _Description;
	private RatingBar _ratingbar;
	private SQLiteAc AC = new SQLiteAc(this);
	
	private OnClickListener ListenerForDone = new OnClickListener() {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			RadioButton _choice = (RadioButton) findViewById(_ActionType.getCheckedRadioButtonId());

			final String _tmpActionType =  _choice.getText().toString();			
			final String _tmpStartTime = _StartTime.getText().toString();
			final String _tmpDeadlineStr = _Deadline.getText().toString();
			final float _tmpRanting = _ratingbar.getRating();
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HHmm");
			Date date = new Date(System.currentTimeMillis());
			try {
				date = sdf.parse(_tmpDeadlineStr);

			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			final long _tmpDeadline = date.getTime();
			
			final String _tmpDescription = _Description.getText().toString();
			final httpService _hs = new httpService();
			final String _url = "http://yileibug.dyndns-ip.com:8080/ProtegeJSP/TestOWL.jsp";
			
			
			
			// Handler for httpservice thread_
			final Handler mHandler = new Handler() { 
			     public void handleMessage(Message msg) { 
			 		String _targetPlace=  msg.getData().get("1").toString();
			 		_targetPlace = _targetPlace.replace("\r\n", "");
			 		_targetPlace = _targetPlace.replace(" ", "");
			 		AC.incert(_tmpDescription, _tmpStartTime, _tmpDeadlineStr, _tmpRanting, _tmpActionType, _targetPlace);
			 		Cursor c = AC.getdatas();
			 		c.moveToFirst();
			 		int i = c.getInt(0);
					ToDo tmp = new ToDo(i, _tmpActionType, _tmpStartTime, _tmpDeadline, _tmpDescription, _targetPlace, _tmpRanting);
					ToDoManager.getInstance().addUserToDo(tmp);
					Intent intent = new Intent(); 
					intent.setClass(NewAddToDo.this,MainActivity.class); 
					startActivity(intent); 
					NewAddToDo.this.finish(); 
			     } 
			 };
			 
			 // http thread cant run on main thread
			Thread thread = new Thread(){
			public void run(){
				Bundle bundle = new Bundle();
				bundle.putString("1", _hs.httpServiceGet(_url, "action="+_tmpActionType));	
				Message msg = new Message();
				msg.setData(bundle);
				mHandler.sendMessage(msg);
			}
			};
			thread.start();
	
	////////////////////////		
			
		}
	}; 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addtodo);
		//setup ui
		_StartTime = (EditText) findViewById(R.id.StartTimeEditText);
		_Deadline = (EditText) findViewById(R.id.DeadlineEditText);
		_Description = (EditText) findViewById(R.id.DescriptionEditText);
		_ActionType = (RadioGroup) findViewById(R.id.radioGroup1);
		_Done = (Button) findViewById(R.id.Donebutton);
		_ratingbar = (RatingBar)findViewById(R.id.PriorityRatingBar);
		_Done.setOnClickListener(ListenerForDone);

		
	}

	
	
}
