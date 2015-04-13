package com.app.cartravel.jsonparser;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.app.cartravel.classes.Parcours;

public class JsonParcours {

	public static String PARCOURS_ID = "idParcours";
	public static String PARCOURS_PROPRIETAIRE = "proprietaire";
	public static String PARCOURS_CONDUCTEUR = "conducteur";
	public static String PARCOURS_JOUR = "jour";
	public static String PARCOURS_HEURE = "heure";
	public static String PARCOURS_REPETITIF = "repetitif";
	public static String PARCOURS_NBR_PASSAGERS = "nbrPassagers";
	public static String PARCOURS_NBR_PLACES_DISPO = "nbrPlacesDispo";
	public static String PARCOURS_NBR_PLACES_PRISE = "nbrPlacesPrise";
	public static String PARCOURS_DIST_SUPP_MAX = "distSuppMax";
	public static String PARCOURS_NUM_CIV_DEP = "numCivDep";
	public static String PARCOURS_RUE_DEP = "rueDep";
	public static String PARCOURS_VILLE_DEP = "villeDep";
	public static String PARCOURS_CODE_POSTAL_DEP = "codePostalDep";
	public static String PARCOURS_NUM_CIV_ARR = "numCivArr";
	public static String PARCOURS_RUE_ARR = "rueArr";
	public static String PARCOURS_VILLE_ARR = "villeArr";
	public static String PARCOURS_CODE_POSTAL_ARR = "codePostalArr";

	public static ArrayList<Parcours> parseListeParcours(String p_body)
			throws JSONException {
		ArrayList<Parcours> listeParcours = new ArrayList<Parcours>();
		JSONArray array = new JSONArray(p_body);

		for (int i = 0; i < array.length(); i++) {

			JSONObject jsonParcours = array.getJSONObject(i);

			Parcours parcours = new Parcours(
					jsonParcours.getString(PARCOURS_ID),
					jsonParcours.getInt(PARCOURS_PROPRIETAIRE),
					jsonParcours.getInt(PARCOURS_CONDUCTEUR),
					jsonParcours.getString(PARCOURS_JOUR),
					jsonParcours.getString(PARCOURS_HEURE),
					jsonParcours.getBoolean(PARCOURS_REPETITIF),
					jsonParcours.getInt(PARCOURS_NBR_PASSAGERS),
					jsonParcours.getInt(PARCOURS_NBR_PLACES_DISPO),
					jsonParcours.getInt(PARCOURS_NBR_PLACES_PRISE),
					(float) jsonParcours.getDouble(PARCOURS_DIST_SUPP_MAX),
					jsonParcours.getString(PARCOURS_NUM_CIV_DEP),
					jsonParcours.getString(PARCOURS_RUE_DEP),
					jsonParcours.getString(PARCOURS_VILLE_DEP),
					jsonParcours.getString(PARCOURS_CODE_POSTAL_DEP),
					jsonParcours.getString(PARCOURS_NUM_CIV_ARR),
					jsonParcours.getString(PARCOURS_RUE_ARR),
					jsonParcours.getString(PARCOURS_VILLE_ARR),
					jsonParcours.getString(PARCOURS_CODE_POSTAL_ARR));
			listeParcours.add(parcours);
		}
		return listeParcours;
	}

	public static JSONObject ToJSONObject(Parcours parcours)
			throws JSONException {
		JSONObject jsonObj = new JSONObject();

		jsonObj.put(PARCOURS_ID, parcours.getId());
		jsonObj.put(PARCOURS_PROPRIETAIRE, parcours.getIdProprietaire());
		jsonObj.put(PARCOURS_CONDUCTEUR, parcours.getIdConducteur());
		jsonObj.put(PARCOURS_JOUR, parcours.getJour());
		jsonObj.put(PARCOURS_HEURE, parcours.getHeure());
		jsonObj.put(PARCOURS_REPETITIF, parcours.getRepetitif());
		jsonObj.put(PARCOURS_NBR_PASSAGERS, parcours.getNbPlacePassagers());
		jsonObj.put(PARCOURS_NBR_PLACES_DISPO, parcours.getNbPlaceDispo());
		jsonObj.put(PARCOURS_NBR_PLACES_PRISE, parcours.getNbPlacePrise());
		jsonObj.put(PARCOURS_DIST_SUPP_MAX, parcours.getDistanceSupMax());
		jsonObj.put(PARCOURS_NUM_CIV_DEP, parcours.getNumCiviqueDep());
		jsonObj.put(PARCOURS_RUE_DEP, parcours.getRueDep());
		jsonObj.put(PARCOURS_VILLE_DEP, parcours.getVilleDep());
		jsonObj.put(PARCOURS_CODE_POSTAL_DEP, parcours.getCodePostalDep());
		jsonObj.put(PARCOURS_NUM_CIV_ARR, parcours.getNumCiviqueArr());
		jsonObj.put(PARCOURS_RUE_ARR, parcours.getRueArr());
		jsonObj.put(PARCOURS_VILLE_ARR, parcours.getVilleArr());
		jsonObj.put(PARCOURS_CODE_POSTAL_ARR, parcours.getCodePostalArr());

		return jsonObj;
	}
}
