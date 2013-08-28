//[Singleton]

package com.example.newone;

import java.util.ArrayList;
import java.util.Collections;

public class ToDoManager {
    private static ToDoManager _instance = null; 
    private ArrayList<ToDo> AllToDoListSavedInHere; //[ArrayList][generic]   
   
    private ToDoManager() {  
    	AllToDoListSavedInHere = new ArrayList<ToDo>();	
    } 

	public static ToDoManager getInstance() { 
        if (_instance == null) {
            _instance = new ToDoManager(); 
        }
        return _instance;
    }
 

    public void addUserToDo(ToDo _todo){
    	assert(_todo!=null);
    	AllToDoListSavedInHere.add(_todo);
    }

    public void deleteUserToDo(ToDo _todo){
    	if(_todo==null)
    	{return;}
    	AllToDoListSavedInHere.remove(_todo);
    }
    
    public ArrayList<ToDo> getUserToDo(){
    	Collections.sort(AllToDoListSavedInHere);
    	return AllToDoListSavedInHere;
    }
    
    public void resetAllScore(){
    	for(ToDo t : AllToDoListSavedInHere){
    		t.resetScore();
    	}
    }
}
