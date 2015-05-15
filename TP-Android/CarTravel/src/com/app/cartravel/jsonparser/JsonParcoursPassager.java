package com.app.cartravel.jsonparser;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.app.cartravel.classes.ParcoursPassager;

public class JsonParcoursPassager {
	public static String PARCOURS_PASSAGER_ID = "idParcours";
	public static String PARCOURS_PASSAGER_ID_PASSAGERS = "idUtilisateur";
	public static String PARCOURS_PASSAGER_NBR_PASSAGERS = "nbrPassagers";

	public static ArrayList<ParcoursPassager> parseListeParcours(String p_body)
			throws JSONException {
		ArrayList<ParcoursPassager> listeParcoursPassager = new ArrayList<ParcoursPassager>();
		JSONArray array = new JSONArray(p_body);

		for (int i = 0; i < array.length(); i++) {
			JSONObject jsonParcoursPassager = array.getJSONObject(i);

			ParcoursPassager parcoursPassager = new ParcoursPassager(
					jsonParcoursPassager.getString(PARCOURS_PASSAGER_ID),
					jsonParcoursPassager
							.getString(PARCOURS_PASSAGER_ID_PASSAGERS),
					jsonParcoursPassager
							.getInt(PARCOURS_PASSAGER_NBR_PASSAGERS));
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

		return jsonObj;
	}
}
