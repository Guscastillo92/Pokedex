package com.example.pokedex;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

public class MainActivity extends ListActivity {
	//initialize some global variables
	protected ArrayList <Pokemon> Pokemonlist;
	protected ArrayList <Pokemon> searchList;
	protected PokemonAdapter dex ;
	protected PokemonFilter PFilter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String pokemon = "didnt work";
        setContentView(R.layout.activity_main);
        try {
			pokemon = getPokemon(); //gets the PokemonJSON
		} catch (IOException e) {
			e.printStackTrace();
		}
         Pokemonlist = parsePokemon(pokemon);//parses the pokemon JSON
         //Creates the Pokemon Adapter and displays the list with the pokemon
         searchList = Pokemonlist;
        dex = new PokemonAdapter(this, 
				R.layout.pkmnentry,
				R.id.pokemonName,
				Pokemonlist);
        setListAdapter(dex);
        
	
    }
    //PokemonAdapter that holds a custom view
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
			//set the variables in the view
			TextView text = (TextView) v.findViewById(R.id.pokemonName);
			ImageView icon = (ImageView) v.findViewById(R.id.pokemonIcon);
			Pokemon pkmn = getItem(position);
			String pkmnIcon = "p"+pkmn.getID();
			//fetches the name of the icon so that it can be set as a drawable
			int id = getResources().getIdentifier( pkmnIcon, "drawable", getPackageName());
			icon.setImageResource(id);
			text.setText(pkmn.getName());
			return v;
		}
		@Override
		public Filter getFilter() {
			//implemented my own filter for a search option
		    if (PFilter == null)
		    	PFilter = new PokemonFilter();
		     
		    return PFilter;
		}
	}
	//Custom Filter got the main code from http://www.survivingwithandroid.com/2012/10/android-listview-custom-filter-and.html
	//Modified to fit my project
	private class PokemonFilter extends Filter {
	    @Override
	    protected FilterResults performFiltering(CharSequence constraint) {
	    	FilterResults results = new FilterResults();
	        // We implement here the filter logic
	        if (constraint == null || constraint.length() == 0) {
	            // No filter implemented we return all the list
	            results.values = Pokemonlist;
	            results.count = Pokemonlist.size();
	        }
	        else {
	            // We perform filtering operation
	            List<Pokemon> temp = new ArrayList<Pokemon>();
	             
	            for (Pokemon p : Pokemonlist) {
	                if (p.getName().toUpperCase().contains(constraint.toString().toUpperCase()))
	                    temp.add(p);
	            }
	             
	            results.values = temp;
	            results.count = temp.size();
	     
	        }
	        searchList= (ArrayList<Pokemon>) results.values;
	        return results;    
	    }
	 
	    @SuppressWarnings("unchecked")
		@Override
	    protected void publishResults(CharSequence constraint,FilterResults results) {
	        if (results.count == 0){
	        	
	        }
	        else {//reset the list adapter to the new adapter here
	        	dex = new PokemonAdapter(MainActivity.this,
	    				R.layout.pkmnentry,
	    				R.id.pokemonName,
	    				(List<Pokemon>) results.values);
	            MainActivity.this.setListAdapter(dex);
	        }
	    }
	     
	}
    
    public String getPokemon() throws IOException {
    	//gets the json from assets and stores it into a string
        InputStream file = getAssets().open("Pokemon.json");

        byte[] data = new byte[file.available()];
        file.read(data);
        file.close();
        return new String(data);
    }
    public ArrayList<Pokemon> parsePokemon(String pkmnJSON){
    	//parses the json to create pokemon objects
    	ArrayList<Pokemon> PokemonL = new ArrayList<Pokemon>();
    	JSONArray jsonPkmn;
    	try{
    		jsonPkmn = new JSONArray(pkmnJSON);
			for(int x = 0; x < jsonPkmn.length()-1; x++){
				// Could not get progress bar to work here so I scrapped it
				JSONObject temppkmn = jsonPkmn.getJSONObject(x);
				JSONArray JAtype = temppkmn.getJSONArray("type");
				JSONObject JAstats = temppkmn.getJSONObject("stats");
				int [] stats = new int [6];
				stats[0] = JAstats.getInt("hp");
				stats[1] = JAstats.getInt("attack");
				stats[2] = JAstats.getInt("defense");
				stats[3] = JAstats.getInt("spattack");
				stats[4] = JAstats.getInt("spdefense");
				stats[5] = JAstats.getInt("speed");
				ArrayList <String> type = new ArrayList<String>();
				for (int y = 0; y < JAtype.length(); y++){
					type.add(JAtype.getString(y));
				}
				JSONObject misc = temppkmn.getJSONObject("misc");
				PokemonL.add(new Pokemon(temppkmn.getString("name"),x,temppkmn.getString("id"), temppkmn.getString("img"), type, stats,
						misc.getString("classification"),misc.getString("height"), misc.getString("weight")));
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
        MenuItem searchItem = menu.findItem(R.id.search);
        //Use this to get my action bar to do a search
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){

			@Override
			public boolean onQueryTextChange(String arg0) {
				//This is where the query is read user can submit or simply type
				//and it will search
				dex.getFilter().filter(arg0);
				return false;
			}

			@Override
			public boolean onQueryTextSubmit(String arg0) {
				dex.getFilter().filter(arg0);
				return false;
			}
        	
        });
        return true;
    }
    @Override
	public void onListItemClick(ListView listView, View view, int position, long id){
    	//This call for the PokemonActivity based on what list item is clicked on
    	
    	Pokemon temp = searchList.get(position);
    	SelPokemon dexentry = SelPokemon.getInstance();
    	dexentry.setPokemon(temp);
    	Intent i = new Intent(this, PokemonActivity.class);
    	startActivity(i);
    	
		
	}
    
}
