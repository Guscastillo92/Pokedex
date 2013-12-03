package com.example.pokedex;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
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
        ArrayList <String> Pokemon = parsePokemon(pokemon);
        for (int p = 0; p < Pokemon.size(); p++){
        	main.append(Pokemon.get(p));
        }
    }
    
    public String getPokemon() throws IOException {
        InputStream file = getAssets().open("Pokemon.json");

        byte[] data = new byte[file.available()];
        file.read(data);
        file.close();
        return new String(data);
    }
    public ArrayList<String> parsePokemon(String pkmnJSON){
    	ArrayList<String> Pokemon = new ArrayList<String>();
    	JSONArray jsonPkmn;
    	try{
    		jsonPkmn = new JSONArray(pkmnJSON);
			for(int x = 0; x < jsonPkmn.length(); x++){
				JSONObject temppkmn = jsonPkmn.getJSONObject(x);
				Pokemon.add(temppkmn.getString("name"));
			}

    		
    	}
    	catch (JSONException e) {
    		e.printStackTrace();
    	}
    	
    	return Pokemon;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
