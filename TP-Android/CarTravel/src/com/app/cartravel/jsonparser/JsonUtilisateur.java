package com.app.cartravel.jsonparser;

import org.json.JSONException;
import org.json.JSONObject;

import com.app.cartravel.classes.Utilisateurs;

public class JsonUtilisateur {
	public static JSONObject ToJSONObject(Utilisateurs u) throws JSONException {
		JSONObject jsonObj = new JSONObject();

		jsonObj.put("pseudo", u.getPseudo());
		jsonObj.put("password", u.getMotDePasse());

		return jsonObj;
	}

	public static JSONObject ToJSONObject(String[] id, String[] str)
			throws JSONException {
		JSONObject jsonObj = null;

		if (id.length == str.length) {
			jsonObj = new JSONObject();
			for (int i = 0; i < id.length; i++) {
				jsonObj.put(id[i], str[i]);
			}
		}

		return jsonObj;
	}

	public static Utilisateurs ToUtilisateur(String strJson)
			throws JSONException {
		Utilisateurs u;
		JSONObject jsonUtilisateur = new JSONObject(strJson);
		u = new Utilisateurs("", jsonUtilisateur.getString("pseudo"),
				jsonUtilisateur.getString("password"), "", "", "", "", "", 0,
				0, 0, 0, 0);
		return u;
	}
}