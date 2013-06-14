//[�}�o�Ҧ�:Singleton=>�T�O�u���@��]

package com.example.newone;

import java.util.ArrayList;

public class ToDoManager {
    private static ToDoManager _instance = null; 
    private ArrayList<ToDo> AllToDoListSavedInHere; //[ArrayList][�x��(generic)]   
   
    private ToDoManager() {  
    	AllToDoListSavedInHere = new ArrayList<ToDo>();	
    } 
    public static ToDoManager getInstance() { 
        if (_instance == null) {
            _instance = new ToDoManager(); 
        }
        return _instance;
    }
 
    //�ϥΪ̷s�W��ToDo���o��s�_��
    public void addUserToDo(ToDo _todo){
    	assert(_todo!=null);
    	AllToDoListSavedInHere.add(_todo);
    }
    //�q�o��s��۪�"�ϥΪ̪�ToDo"���R��
    public void deleteUserToDo(ToDo _todo){
    	if(_todo==null)
    	{return;}
    	AllToDoListSavedInHere.remove(_todo);
    }
    
  //�q�o��s��۪�"�ϥΪ̪�ToDo"����X
    public ArrayList<ToDo> getUserToDo(){
    	return AllToDoListSavedInHere;
    }
    
}
