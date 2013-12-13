package com.example.pokedex;
//Singleton to pass data between the two activities
public class SelPokemon {
	private static SelPokemon instance = null;
	private Pokemon selected;
	   protected SelPokemon() {
	      // Exists only to defeat instantiation.
	   }
	   public static SelPokemon getInstance() {
	      if(instance == null) {
	         instance = new SelPokemon();
	      }
	      return instance;
	   }
	   public void setPokemon(Pokemon p){
		   selected = p;
	   }
	   public Pokemon getPokemon(){
		   return selected;
	   }
}
