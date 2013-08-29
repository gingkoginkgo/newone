package com.example.newone;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.content.Intent;
import android.database.Cursor;

public class MainActivity extends Activity {

	private Button _AddNew;
	private Button _Delete;
	private ListView list;
	private ToDo recordTodo;
	private SQLiteAc AC = new SQLiteAc(this);

	private OnClickListener ListenerForAddNew = new OnClickListener() {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			intent.setClass(MainActivity.this, NewAddToDo.class);
			startActivity(intent);
			MainActivity.this.finish();
		}
	};

	private OnClickListener ListenerForDelete = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			AC.delete(recordTodo.getNum());
			ToDoManager.getInstance().deleteUserToDo(recordTodo);
			ShowToDoList();
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Intent intent = new Intent(MainActivity.this, posChecker.class);
		startService(intent);

		_AddNew = (Button) findViewById(R.id.AddNewbutton);
		_Delete = (Button) findViewById(R.id.Deletebutton);
		_AddNew.setOnClickListener(ListenerForAddNew);
		_Delete.setOnClickListener(ListenerForDelete);

		
		ToDo td;
		list = (ListView) findViewById(R.id.ToDolistView);
		ToDoManager TDM = ToDoManager.getInstance();
		TDM.clearUserToDo();
		
		Cursor c = AC.getdatas();
		if (c.getCount() != 0) {
			c.moveToFirst();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HHmm");
			for (int i = 0; i < c.getCount(); i++) {
				c.moveToPosition(i);
				Date date = new Date();
				try {
					date = sdf.parse(c.getString(3));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				final long dl = date.getTime();
				td = new ToDo(c.getInt(0), c.getString(5), c.getString(2), dl,
						c.getString(1), c.getString(6), c.getFloat(4));
				TDM.addUserToDo(td);
				c.moveToNext();
			}
		}
		this.ShowToDoList();
	}

	public void ShowToDoList() {
		ArrayList<ToDo> showToDos = ToDoManager.getInstance().getUserToDo();
		ArrayList<String> _todoString = new ArrayList<String>();
		// 由分數大到小
		for (ToDo t : showToDos) {
			_todoString.add(t.getDescription());
		}
		list = (ListView) findViewById(R.id.ToDolistView);
		list.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, _todoString));
		list.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				recordTodo = ToDoManager.getInstance().getUserToDo().get(arg2);
				Toast.makeText(MainActivity.this, "ismSelected",
						Toast.LENGTH_LONG).show();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});

		list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				recordTodo = ToDoManager.getInstance().getUserToDo().get(arg2);
				Toast.makeText(MainActivity.this, "isClick", Toast.LENGTH_LONG)
						.show();
				arg1.setSelected(true);
			}
		});

		list.setTextFilterEnabled(true);
	}
}
