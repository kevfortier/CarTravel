package com.app.cartravel.jsonparser;


import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.app.cartravel.classes.Utilisateurs;
import com.app.cartravel.utilitaires.arrayadapters.ContactItem;


public class JsonUtilisateur {
	public static JSONObject ToJSONObject(Utilisateurs u) throws JSONException {
		JSONObject jsonObj = new JSONObject();
		
		jsonObj.put("pseudo", u.getPseudo());
		jsonObj.put("password", u.getMotDePasse());
		
		return jsonObj;
	}
	
	public static JSONObject ToJSONObject(String[] id, String[] str) throws JSONException{
		JSONObject jsonObj = null;
		
		if(id.length == str.length){
			jsonObj = new JSONObject();
			for(int i = 0; i < id.length; i++){
				jsonObj.put(id[i], str[i]);
			}
		}
		
		return jsonObj;
	}
	
	
	
	public static Utilisateurs ToUtilisateur(String strJson) throws JSONException{
		Utilisateurs u;
		List<String> contacts = new ArrayList<String>();
		JSONObject jsonUtilisateur = new JSONObject(strJson);
		JSONArray contactsJSON = jsonUtilisateur.getJSONArray("contacts");
		for(int i = 0; i < contactsJSON.length(); i++)
			contacts.add(contactsJSON.getString(i));
		u = new Utilisateurs("", jsonUtilisateur.getString("pseudo"),
				jsonUtilisateur.getString("password"),"","","","",false,0,0,false,false);
		return u;
	}
	
	//Méthodes pour gérer des contacts et nous n'en avons pas.
	/*
	public static List<Utilisateurs> ToListUtilisateurs(String strJson) throws JSONException{
		List<Utilisateurs> liste = new ArrayList<Utilisateurs>(); 
		JSONArray array = new JSONArray(strJson);

    	for (int i = 0; i < array.length(); i++) {
    		List<String> contacts = new ArrayList<String>();
    	    JSONObject jsonUtilisateur = array.getJSONObject(i);
    	    JSONArray contactsJSON = jsonUtilisateur.getJSONArray("contacts");
    	    for(int j = 0; j < contactsJSON.length(); j++)
    			contacts.add(contactsJSON.getString(j));
    	    Utilisateurs u = new Utilisateurs(jsonUtilisateur.getString("username"), jsonUtilisateur.getString("pseudo"),
    				jsonUtilisateur.getString("password"),false,false,contacts);
    	    liste.add(u);
    	}
        
		return liste;
	}
	*/
	
	/**
	 * Converti une chaine de caractÃ¨res JSON en liste de ContactItem 
	 * @param strJson
	 * @return List<ContactItem>
	 * @throws JSONException
	 */
	public static List<ContactItem> ToListContactItem(String strJson) throws JSONException{
		List<ContactItem> liste = new ArrayList<ContactItem>();
		JSONArray array = new JSONArray(strJson);
		
		for(int i = 0; i < array.length(); i++){
			JSONObject jsonContact = array.getJSONObject(i);
			ContactItem c = new ContactItem(jsonContact.getString("username"),
					jsonContact.getString("pseudo"));
			liste.add(c);
		}
		return liste;
	}	
}