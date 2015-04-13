package com.example.myfirstapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.R.integer;
import android.annotation.SuppressLint;



public class MyData {
		public static List<Map<String, String>> getDataSource(String str){
		
		List<String> list = new ArrayList<String>();
		String[] strings = str.split(" ");
		for (String enString : strings){
			list.add(enString);
		}
		List<Map<String, String>> map;
		map = new ArrayList<Map<String,String>>();
		int i=0;
		int n=list.size();
		for(i=0; i<n/2; i++){
			Map<String, String> map2 = new HashMap<String, String>();
			
			map2.put("channel",list.get(2*i));
			map2.put("interference",list.get(2*i+1));
			
			map.add(map2);
		}
		
		return map;
		
		
	}
	
		
}
