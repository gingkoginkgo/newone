package com.example.newone;

public class ToDo implements Comparable{
	private String ActionType;
	private String StartTime;
	private long DeadlineTime;
	private String Description;
	private String Target_Place;
	private float Priority_Rate;
	private float _score;
	
	public ToDo(String _ActionType , String _StartTime , long _DeadlineTime , String _Description, String _Target_Place,Float _rate)
	{
		ActionType = _ActionType;
		StartTime = _StartTime;
		DeadlineTime = _DeadlineTime;
		Description = _Description;
		Target_Place = _Target_Place;
		Priority_Rate = _rate;
		_score = _rate;
	}
	
	public String getActionType(){
		return ActionType;
	}
	public String getStartTime(){
		return StartTime;
	}
	public long getDeadlineTime(){
		return DeadlineTime;
	}
	public String getDescription(){
		return Description;
	}
	public String getTarget_Place(){
		return Target_Place;
	}
	public float getPriority_Rate(){
		return Priority_Rate;
	}
	public float getScore(){
		return _score;
	}
	public void setScore(float s){
		_score = s;
	}

	@Override
	public int compareTo(Object arg0) {
		ToDo tmp = (ToDo)arg0;
		float ret = this._score - tmp.getScore();
		return (int)ret;
	}
}
