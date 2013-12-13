package com.example.pokedex;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

public class PokemonActivity extends Activity {
	//Use start activity for result to be able to store evolutions
	// use setResult(cancel) so that it knows to return when you press back and choose
	//evolution
	ImageView pkmnPic;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pokemon);
		pkmnPic = (ImageView) findViewById(R.id.pkmnPic);
		SelPokemon instance = SelPokemon.getInstance();
		Pokemon currPokemon = instance.getPokemon();
		new fetchPokemonimg().execute(currPokemon.getImg());
		TextView pkmnClass = (TextView) findViewById(R.id.pClass);
		TextView pkmnHeight = (TextView) findViewById(R.id.pHeight);
		TextView pkmnWeight = (TextView) findViewById(R.id.pWeight);
		TextView pkmnName = (TextView) findViewById(R.id.pknmName);
		TextView pkmnTypeO = (TextView) findViewById(R.id.typeOne);
		TextView pkmnTypeT = (TextView) findViewById(R.id.typeTwo);
		TextView pkmnStatH = (TextView) findViewById(R.id.Health);
		TextView pkmnStatA = (TextView) findViewById(R.id.Attack);
		TextView pkmnStatD = (TextView) findViewById(R.id.Defense);
		TextView pkmnStatspA = (TextView) findViewById(R.id.spAttack);
		TextView pkmnStatspD = (TextView) findViewById(R.id.spDefense);
		TextView pkmnStatSpe = (TextView) findViewById(R.id.Speed);
		pkmnName.setText(currPokemon.getName());
		pkmnClass.setText("Classification: "+currPokemon.getpClass());
		pkmnHeight.setText("Height: "+currPokemon.getHeight());
		pkmnWeight.setText("Weight: "+currPokemon.getWeight()+"lbs");
		ArrayList<String> pType = currPokemon.getType();
		pkmnTypeO.setText(pType.get(0));
		getTypeColor(pType.get(0),pkmnTypeO);
		if(pType.size()>1){
			pkmnTypeT.setText(pType.get(1));
			getTypeColor(pType.get(1),pkmnTypeT);
		}
		int [] pStats = currPokemon.getStats();
		pkmnStatH.setText("       HP: "+pStats[0]);
		pkmnStatA.setText("   Attack: "+pStats[1]);
		pkmnStatD.setText("  Defense: "+pStats[2]);
		pkmnStatspA.setText(" spAttack: "+pStats[3]);
		pkmnStatspD.setText("spDefense: "+pStats[4]);
		pkmnStatSpe.setText("    Speed: "+pStats[5]);

	}
	//gets the color for the Pokemon Type
	public void getTypeColor(String type, TextView v){
		Log.d("GC", type);
		if(type.equals("Grass")){
			v.setBackgroundColor(0xFF78C850);
		} else if(type.equals("Fire")){
			v.setBackgroundColor(0xFFF08030);
		} else if(type.equals("Water")){
			v.setBackgroundColor(0xFF6890F0);
		} else if(type.equals("Normal")){
			v.setBackgroundColor(0xFFA8A878);
		} else if(type.equals("Fighting")){
			v.setBackgroundColor(0xFFC03028);
		} else if(type.equals("Flying")){
			v.setBackgroundColor(0xFFA890F0);
		} else if(type.equals("Poison")){
			v.setBackgroundColor(0xFFA040A0);
		} else if(type.equals("Electric")){
			v.setBackgroundColor(0xFFF8D030);
		} else if(type.equals("Ground")){
			v.setBackgroundColor(0xFFE0C068);
		} else if(type.equals("Psychic")){
			v.setBackgroundColor(0xFFF85888);
		} else if(type.equals("Rock")){
			v.setBackgroundColor(0xFFB8A038);
		} else if(type.equals("Ice")){
			v.setBackgroundColor(0xFF98D8D8);
		} else if(type.equals("Bug")){
			v.setBackgroundColor(0xFFA8B820);
		} else if(type.equals("Dragon")){
			v.setBackgroundColor(0xFF7038F8);
		} else if(type.equals("Ghost")){
			v.setBackgroundColor(0xFF705898);
		} else if(type.equals("Steel")){
			v.setBackgroundColor(0xFFB8B8D0);
		} else {
			v.setBackgroundColor(0xFF000000);
		}
		
		
	}
	//used from Paint App
	private class fetchPokemonimg extends AsyncTask<String, Void, Bitmap> {
		/**
		 * The system calls this to perform work in a worker thread and delivers
		 * it the parameters given to AsyncTask.execute()
		 */
		protected Bitmap doInBackground(String... urls) {
			return imgFetcher(urls[0]);
		}

		/**
		 * The system calls this to perform work in the UI thread and delivers
		 * the result from doInBackground()
		 */
		protected void onPostExecute(Bitmap result) {
			Drawable d = new BitmapDrawable(getResources(), result);
			pkmnPic.setBackgroundDrawable(d);
		}
	}
	private Bitmap imgFetcher(String s) {
		Bitmap img = null;
		URL url;
		try {
			url = new URL(s);
			img = BitmapFactory.decodeStream(url.openStream());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return img;

	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.pokemon, menu);
		return true;
	}

}
