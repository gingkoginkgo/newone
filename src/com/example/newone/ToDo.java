package com.example.newone;



public class ToDo {
	
	private String ActionType;
	private String StartTime;
	private String DeadlineTime;
	private String Description;
	private String Target_Place;
	
	public ToDo(String _ActionType , String _StartTime , String _DeadlineTime , String _Description, String _Target_Place)
	{
		ActionType = _ActionType;
		StartTime = _StartTime;
		DeadlineTime = _DeadlineTime;
		Description = _Description;
		Target_Place = _Target_Place;
		
	}
	
	public String getActionType(){
		return ActionType;
		}
	public String getStartTime(){
		return StartTime;
		}
	public String getDeadlineTime(){
		return DeadlineTime;
		}
	public String getDescription(){
		return Description;
		}
	public String getTarget_Place(){
		return Target_Place;
		}
	
	
	
}
