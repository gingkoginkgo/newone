package com.example.newone;

public class ToDo implements Comparable {
	private int num;
	private String ActionType;
	private String StartTime;
	private long DeadlineTime;
	private String Description;
	private String Target_Place;
	private float Priority_Rate;
	private float _score;
	private POI _poi;

	public ToDo(int _num, String _ActionType, String _StartTime,
			long _DeadlineTime, String _Description, String _Target_Place,
			Float _rate) {
		num = _num;
		ActionType = _ActionType;
		StartTime = _StartTime;
		DeadlineTime = _DeadlineTime;
		Description = _Description;
		Target_Place = _Target_Place;
		Priority_Rate = _rate;
		_poi = null;
		_score = _rate;
	}

	public int getNum() {
		return num;
	}

	public String getActionType() {
		return ActionType;
	}

	public String getStartTime() {
		return StartTime;
	}

	public long getDeadlineTime() {
		return DeadlineTime;
	}

	public String getDescription() {
		return Description;
	}

	public String getTarget_Place() {
		return Target_Place;
	}

	public float getPriority_Rate() {
		return Priority_Rate;
	}

	public float getScore() {
		return _score;
	}

	public void setScore(float s) {
		_score = s;
	}

	@Override
	public int compareTo(Object arg0) {
		ToDo tmp = (ToDo) arg0;
		float ret = tmp.getScore() - this._score;
		return (int) ret;
	}

	public void resetScore() {
		_score = Priority_Rate;
		_poi = null;
	}

	public void setIsByLoc(POI b) {
		_poi = b;
	}

	public POI getPOI() {
		return _poi;
	}
}
