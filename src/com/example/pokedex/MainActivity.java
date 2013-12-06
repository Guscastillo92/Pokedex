package com.example.pokedex;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String pokemon = "didnt work";
        setContentView(R.layout.activity_main);
        try {
			pokemon = getPokemon();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        TextView main = (TextView) findViewById(R.id.textBox);
        ArrayList <Pokemon> Pokemonlist = parsePokemon(pokemon);
        PokemonAdapter dex = new PokemonAdapter(this,
				R.layout.pkmnentry,
				R.id.textBox,
				Pokemonlist);
        
		/** Set the handle */
    }
	private class PokemonAdapter extends ArrayAdapter<Pokemon>{
		public PokemonAdapter(Context context, int resource, int textViewResourceId, List<Pokemon> objects){
		super(context, resource,textViewResourceId, objects);			
			
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent){
			View v;
			if ( null == convertView){
				LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				
				v =inflater.inflate(R.layout.pkmnentry, parent,false);
			} else {
				v = convertView;
				
			}
			TextView text = (TextView) v.findViewById(R.id.pokemonName);
			Pokemon pkmn = getItem(position);
			text.setText(pkmn.name);
			return v;
		}
	}
    
    public String getPokemon() throws IOException {
        InputStream file = getAssets().open("Pokemon.json");

        byte[] data = new byte[file.available()];
        file.read(data);
        file.close();
        return new String(data);
    }
    public ArrayList<Pokemon> parsePokemon(String pkmnJSON){
    	ArrayList<Pokemon> PokemonL = new ArrayList<Pokemon>();
    	JSONArray jsonPkmn;
    	try{
    		jsonPkmn = new JSONArray(pkmnJSON);
			for(int x = 0; x < jsonPkmn.length(); x++){
				//Will parse JSON into Pokemon object
				JSONObject temppkmn = jsonPkmn.getJSONObject(x);
				PokemonL.add(new Pokemon(temppkmn.getString("name")));
			}

    		
    	}
    	catch (JSONException e) {
    		e.printStackTrace();
    	}
    	
    	return PokemonL;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
