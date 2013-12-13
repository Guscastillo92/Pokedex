package com.example.pokedex;

import java.util.ArrayList;

public class Pokemon {
	private String name; //Pokemon Variables
	private int id;
	private String pClass;
	private String Height;
	private String Weight;
	private String ID;
	private String img;
	private ArrayList <String> type;
	private int[] stats;
	
	public Pokemon (String n, int i, String I,String url, ArrayList <String> t, int[] s,String c, String h, String w){
		//Constructor for a whole Pokemon
		setName(n);
		setID(I);
		setId(i);
		setImg(url);
		setType(t);
		setStats(s);
		setpClass(c);
		setHeight(h);
		setWeight(w);
	}
	Pokemon (String n){
		setName(n);
		//temp constructor for testing 
	}
	//Setters and Getters
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public ArrayList <String> getType() {
		return type;
	}
	public void setType(ArrayList <String> type) {
		this.type = type;
	}
	public int[] getStats() {
		return stats;
	}
	public void setStats(int[] stats) {
		this.stats = stats;
	}
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getpClass() {
		return pClass;
	}
	public void setpClass(String pClass) {
		this.pClass = pClass;
	}
	public String getHeight() {
		return Height;
	}
	public void setHeight(String height) {
		Height = height;
	}
	public String getWeight() {
		return Weight;
	}
	public void setWeight(String weight) {
		Weight = weight;
	}

}
