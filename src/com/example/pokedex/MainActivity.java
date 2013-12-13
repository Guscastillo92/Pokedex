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
	protected ArrayList <Pokemon> Pokemonlist;
	protected PokemonAdapter dex ;
	protected PokemonFilter PFilter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String pokemon = "didnt work";
        setContentView(R.layout.activity_main);
        try {
			pokemon = getPokemon();
		} catch (IOException e) {
			e.printStackTrace();
		}
         Pokemonlist = parsePokemon(pokemon);
        dex = new PokemonAdapter(this,
				R.layout.pkmnentry,
				R.id.pokemonName,
				Pokemonlist);
        setListAdapter(dex);
        
	
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
			ImageView icon = (ImageView) v.findViewById(R.id.pokemonIcon);
			Pokemon pkmn = getItem(position);
			String pkmnIcon = "p"+pkmn.getID();
			Log.d("Gus", pkmnIcon);
			int id = getResources().getIdentifier( pkmnIcon, "drawable", getPackageName());
			icon.setImageResource(id);
			text.setText(pkmn.getName());
			return v;
		}
		@Override
		public Filter getFilter() {
		    if (PFilter == null)
		    	PFilter = new PokemonFilter();
		     
		    return PFilter;
		}
	}
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
	                if (p.getName().contains(constraint.toString()))
	                    temp.add(p);
	            }
	             
	            results.values = temp;
	            results.count = temp.size();
	     
	        }
	        return results;    
	    }
	 
	    @SuppressWarnings("unchecked")
		@Override
	    protected void publishResults(CharSequence constraint,FilterResults results) {
	        if (results.count == 0){
	        	
	        }
	        else {
	        	dex = new PokemonAdapter(MainActivity.this,
	    				R.layout.pkmnentry,
	    				R.id.pokemonName,
	    				(List<Pokemon>) results.values);
	            MainActivity.this.setListAdapter(dex);
	        }
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
			for(int x = 0; x < jsonPkmn.length()-1; x++){
				//Will parse JSON into Pokemon object Hide and show progress bar in this method
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
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){

			@Override
			public boolean onQueryTextChange(String arg0) {
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
    	Pokemon temp = Pokemonlist.get(position);
    	SelPokemon dexentry = SelPokemon.getInstance();
    	dexentry.setPokemon(temp);
    	Intent i = new Intent(this, PokemonActivity.class);
    	startActivity(i);
    	
		
	}
    
}
