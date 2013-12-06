package com.example.pokedex;

import java.util.ArrayList;

public class Pokemon {
	String name;
	int id;
	String img;
	ArrayList <String> type;
	int[] stats;
	
	Pokemon (String n, int i, String url, ArrayList <String> t, int[] s){
		name = n;
		id = i;
		img = url;
		type = t;
		stats = s;
	}
	Pokemon (String n){
		name = n;
		//temp constructor for testing 
	}

}
