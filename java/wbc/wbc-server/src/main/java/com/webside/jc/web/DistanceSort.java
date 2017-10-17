package com.webside.jc.web;

import java.util.Comparator;

import com.webside.jc.model.GameBattle;


/**
 * @title : 组织List集合根据比赛现状(Distance)排序
 * */
public class DistanceSort implements Comparator {
	
	public int compare(Object o1, Object o2) {
		GameBattle gb1 = (GameBattle)o1;
		GameBattle gb2 = (GameBattle)o2;
		if(gb1.getGbType()!=null && gb2.getGbType() !=null){
			if(Long.parseLong(gb1.getGbType())< Long.parseLong(gb2.getGbType())){
				return -1; 
			}else{
				if(Long.parseLong(gb1.getGbType()) == Long.parseLong(gb2.getGbType())){
					return 0; 
					//return 0; 
				}else{
					return 1;
				}
			}
		}
		return 0;
	}
}