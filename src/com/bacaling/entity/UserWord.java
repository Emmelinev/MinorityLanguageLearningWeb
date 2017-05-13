package com.bacaling.entity;

public class UserWord extends Word{
	private int lastPracticed;
	private int practiceCount;
	private int StrengthLevel;
	
	public UserWord(String word) {
		super(word);
	}
	
	public int getLastPracticed() {
		return lastPracticed;
	}
	public void setLastPracticed(int lastPracticed) {
		this.lastPracticed = lastPracticed;
	}
	public int getPracticeCount() {
		return practiceCount;
	}
	public void setPracticeCount(int practiceCount) {
		this.practiceCount = practiceCount;
	}
	public int getStrengthLevel() {
		return StrengthLevel;
	}
	public void setStrengthLevel() {
		if(practiceCount >= 20){
			StrengthLevel = 4;
		}else{
			int r = (int) Math.ceil(0.13*this.practiceCount-0.08*this.lastPracticed+0.8);
			if(r>4){
				r = 4;
			}
			this.StrengthLevel = r;
		}
		
	}
	
}
