//[開發模式:Singleton=>確保只有一份]

package com.example.newone;

import java.util.ArrayList;

public class ToDoManager {
    private static ToDoManager _instance = null; 
    private ArrayList<ToDo> AllToDoListSavedInHere; //[ArrayList][泛型(generic)]   
   
    private ToDoManager() {  
    	AllToDoListSavedInHere = new ArrayList<ToDo>();	
    } 
    public static ToDoManager getInstance() { 
        if (_instance == null) {
            _instance = new ToDoManager(); 
        }
        return _instance;
    }
 
    //使用者新增的ToDo放到這邊存起來
    public void addUserToDo(ToDo _todo){
    	AllToDoListSavedInHere.add(_todo);
    }
    //從這把存放著的"使用者的ToDo"給刪掉
    public void deleteUserToDo(ToDo _todo){
    	if(_todo==null)
    	{return;}
    	AllToDoListSavedInHere.remove(_todo);
    }
    
  //從這把存放著的"使用者的ToDo"給取出
    public ArrayList<ToDo> getUserToDo(){
    	return AllToDoListSavedInHere;
    }
    
}
