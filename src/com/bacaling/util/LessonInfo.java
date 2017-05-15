package com.bacaling.util;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class LessonInfo {
	public static int setLevel(int exp){
		int level = 0;
		if(exp == 0){
			level = 0;
		}else if(exp > 0 && exp <= 20){
			level = 1;
		}else if(exp > 20 && exp <= 50){
			level = 2;
		}else if(exp > 50 && exp <= 80){
			level = 3;
		}else if(exp > 80 && exp <= 120){
			level = 4;
		}else if(exp > 120 && exp <= 200){
			level = 5;
		}else if(exp > 200 && exp <= 300){
			level = 6;
		}else if(exp > 300 && exp <= 500){
			level = 7;
		}else if(exp > 500 && exp <= 750){
			level = 8;
		}else if(exp > 750 && exp <= 1000){
			level = 9;
		}else if(exp > 1000 && exp <= 1300){
			level = 10;
		}else if(exp > 1300 && exp <= 1800){
			level = 11;
		}else if(exp > 1800 && exp <= 2500){
			level = 12;
		}else if(exp > 2500 && exp <= 3000){
			level = 13;
		}else if(exp > 3000 && exp <= 3800){
			level = 14;
		}else if(exp > 3800 && exp <= 4500){
			level = 15;
		}else if(exp > 4500 && exp <= 5400){
			level = 16;
		}else if(exp > 5400 && exp <= 6500){
			level = 17;
		}else if(exp > 6500 && exp <= 7700){
			level = 18;
		}else if(exp > 7700 && exp <= 8900){
			level = 19;
		}else{
			level = 20;
		}
		return level;
	}
	
}
