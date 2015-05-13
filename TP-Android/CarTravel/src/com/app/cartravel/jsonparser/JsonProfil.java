package com.app.cartravel.jsonparser;

import org.json.JSONException;
import org.json.JSONObject;

import com.app.cartravel.classes.Utilisateurs;
import com.app.cartravel.utilitaire.UtilisateurDataSource;

public class JsonProfil {
	
	public static String COURRIEL_USER = "courrielUser";
	public static String NUM_CIVIQUE = "numCivique";
	public static String RUE = "rue";
	public static String VILLE = "ville";
	public static String CODE_POSTAL = "codePostal";
	public static String NUM_TEL = "numTel";
	public static String POS_VOITURE = "posVoiture";
	public static String NOTE_COND = "noteCond";
	public static String NOTE_PASS = "notePass";
	
	public static JSONObject ToJSONObject(Utilisateurs utilisateur)
			throws JSONException {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put(COURRIEL_USER, utilisateur.getCourriel());
		jsonObj.put(NUM_CIVIQUE, utilisateur.getNumCivique());
		jsonObj.put(RUE, utilisateur.getRue());
		jsonObj.put(VILLE, utilisateur.getVille());
		jsonObj.put(CODE_POSTAL, utilisateur.getCodePostal());
		jsonObj.put(NUM_TEL, utilisateur.getNumTel());
		jsonObj.put(POS_VOITURE, utilisateur.getVoiture());
		jsonObj.put(NOTE_COND, utilisateur.getNoteCond());
		jsonObj.put(NOTE_PASS, utilisateur.getNotePass());
		return jsonObj;
	}

	public static Utilisateurs ToUtilisateur(String strJson)
			throws JSONException {
		Utilisateurs u;
		JSONObject jsonUtilisateur = new JSONObject(strJson);
		u = new Utilisateurs(jsonUtilisateur.getString("courrielUser"), "",
				"", jsonUtilisateur.getString("numCivique"), jsonUtilisateur.getString("rue"),
				jsonUtilisateur.getString("ville"), jsonUtilisateur.getString("codePostal"),
				jsonUtilisateur.getString("numTel"), jsonUtilisateur.getInt("posVoiture"),
				(float) jsonUtilisateur.getDouble("noteCond"), (float) jsonUtilisateur.getDouble("notePass"), 
				0, 0);
		return u;
	}
}
