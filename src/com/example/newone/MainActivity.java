package com.example.newone;

import java.util.ArrayList;

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

public class MainActivity extends Activity{

	private Button _AddNew;
	private Button _Delete;
	private ListView list;
	private ToDo recordTodo;
	

	private OnClickListener ListenerForAddNew = new OnClickListener() {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(); 
			intent.setClass(MainActivity.this,NewAddToDo.class); 
			startActivity(intent); 
			MainActivity.this.finish(); 
		}
	}; 
		

	private OnClickListener ListenerForDelete = new OnClickListener(){
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
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
		

		
		//��U
		_AddNew.setOnClickListener(ListenerForAddNew);
		_Delete.setOnClickListener(ListenerForDelete);
		this.ShowToDoList();
		list = (ListView) findViewById(R.id.ToDolistView);
	}
	

	public void ShowToDoList(){

		ArrayList<ToDo> showToDos = ToDoManager.getInstance().getUserToDo();	

		ArrayList<String> _todoString = new ArrayList<String>();    
		
		for(ToDo t:showToDos){   /////////////////////
			_todoString.add(t.getDescription());
		}
		
		 list = (ListView) findViewById(R.id.ToDolistView);
		 

		 list.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, _todoString));
		 
		 list.setOnItemSelectedListener(new OnItemSelectedListener(){
				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					recordTodo = ToDoManager.getInstance().getUserToDo().get(arg2);
					Toast.makeText( MainActivity.this, "ismSelected", Toast.LENGTH_LONG).show();
				}
				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
				}});
		 
		 list.setOnItemClickListener(new OnItemClickListener(){
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
						long arg3) {
					// TODO Auto-generated method stub
					recordTodo = ToDoManager.getInstance().getUserToDo().get(arg2);
					Toast.makeText( MainActivity.this, "isClick", Toast.LENGTH_LONG).show();
					arg1.setSelected(true);
				}});
		 

		 list.setTextFilterEnabled(true);
	}
}
