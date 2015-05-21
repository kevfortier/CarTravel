package com.app.cartravel.jsonparser;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.app.cartravel.classes.ParcoursPassager;

public class JsonParcoursPassager {
	public static String PARCOURS_PASSAGER_ID = "idParcours";
	public static String PARCOURS_PASSAGER_ID_PASSAGERS = "idUtilisateur";
	public static String PARCOURS_PASSAGER_NBR_PASSAGERS = "nbrPassagers";
	public static String PARCOURS_PASSAGER_DATE_AJOUT = "date_parcours_passager";

	public static ArrayList<ParcoursPassager> parseListeParcours(String p_body)
			throws JSONException {
		ArrayList<ParcoursPassager> listeParcoursPassager = new ArrayList<ParcoursPassager>();
		JSONArray array = new JSONArray(p_body);

		for (int i = 0; i < array.length(); i++) {
			JSONObject jsonParcoursPassager = array.getJSONObject(i);
			
			List<String> listIdPassagers = new ArrayList<String>();
			JSONArray jsonIdPassagers = jsonParcoursPassager.getJSONArray(PARCOURS_PASSAGER_ID_PASSAGERS);
			
			for (int j = 0; j < jsonIdPassagers.length(); j++){
				listIdPassagers.add(jsonIdPassagers.getString(j));
			}
			
			ParcoursPassager parcoursPassager = new ParcoursPassager(
					jsonParcoursPassager.getString(PARCOURS_PASSAGER_ID),
					listIdPassagers,
					jsonParcoursPassager
							.getInt(PARCOURS_PASSAGER_NBR_PASSAGERS),
					jsonParcoursPassager.getString(PARCOURS_PASSAGER_DATE_AJOUT));
			listeParcoursPassager.add(parcoursPassager);
		}
		return listeParcoursPassager;
	}

	public static JSONObject ToJSONObject(ParcoursPassager parcoursPassager)
			throws JSONException {
		JSONObject jsonObj = new JSONObject();

		jsonObj.put(PARCOURS_PASSAGER_ID,
				parcoursPassager.getIdParcoursPassager());
		jsonObj.put(PARCOURS_PASSAGER_ID_PASSAGERS,
				(ArrayList<String>) parcoursPassager.getListIdPassager());
		jsonObj.put(PARCOURS_PASSAGER_NBR_PASSAGERS,
				parcoursPassager.getNbrPassagers());
		jsonObj.put(PARCOURS_PASSAGER_DATE_AJOUT, parcoursPassager.getDateAjout());

		return jsonObj;
	}
	
	public static ParcoursPassager ToParcoursPassager(String strJson)
			throws JSONException {
		ParcoursPassager pP;
		JSONObject jsonParcoursPassager = new JSONObject(strJson);
		
		List<String> listIdPassagers = new ArrayList<String>();
		JSONArray jsonIdPassagers = jsonParcoursPassager.getJSONArray(PARCOURS_PASSAGER_ID_PASSAGERS);
		
		for (int j = 0; j < jsonIdPassagers.length(); j++){
			listIdPassagers.add(jsonIdPassagers.getString(j));
		}
		
		pP = new ParcoursPassager(
				jsonParcoursPassager.getString(PARCOURS_PASSAGER_ID),
				listIdPassagers,
				jsonParcoursPassager.getInt(PARCOURS_PASSAGER_NBR_PASSAGERS),
				jsonParcoursPassager.getString(PARCOURS_PASSAGER_DATE_AJOUT));
		return pP;
	}
}
