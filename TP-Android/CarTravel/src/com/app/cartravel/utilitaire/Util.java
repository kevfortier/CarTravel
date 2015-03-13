package com.app.cartravel.utilitaire;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import android.content.res.TypedArray;
import android.location.Location;

public class Util {

	public final static String REST_UTILISATEUR = "/utilisateurs";
	public final static String REST_CONNEXION = "/connexion";
	public final static String WEB_SERVICE = "10.248.18.225:8080";

	public final static String GOOGLE_SENDER_ID = "133519372687";

	// public final static String REST_CONTACTS = "/contacts";

	/**
	 * Permet de transformer une chaine de caracteres en une liste selon un
	 * s�parateur
	 * 
	 * @param parse
	 * @param separateur
	 * @return
	 */
	public static List<String> ParseStringToList(String parse, String separateur) {
		List<String> liste = null;
		if (parse != null && !parse.equals("[]")) {
			liste = new ArrayList<String>();
			// Enlève les "[" et "]" entourant la chaîne de caractères
			parse = parse.replace("[", "").replace("]", "").trim();
			// Sépare la chaîne selon le séparateur spécifié
			String[] items = parse.split(separateur);

			for (String item : items) {
				liste.add(item.trim());
			}
		} else
			liste = new ArrayList<String>();
		return liste;
	}

	public static int[] GetIntArrayResourceReferences(TypedArray p_Array) {
		int length = p_Array.length();
		int[] intArray = new int[length];

		for (int i = 0; i < length; i++) {
			intArray[i] = p_Array.getResourceId(i, 0);
		}
		p_Array.recycle();
		return intArray;
	}

	/**
	 * Enlève les éléments vides d'une liste de String
	 * 
	 * @param listeAFiltrer
	 * @return
	 */
	public static List<String> RemoveEmptyString(List<String> listeAFiltrer) {
		List<String> liste = new ArrayList<String>();
		for (String item : listeAFiltrer) {
			if (item != null && item != "")
				liste.add(item);
		}
		return liste;
	}

	/**
	 * Permet de valider si les chaînes de caractères existent
	 * 
	 * @param chaines
	 * @return true si valide, false sinon
	 */
	public static Boolean ValiderString(String[] chaines) {
		for (int i = 0; i < chaines.length; i++) {
			if (chaines[i].isEmpty())
				return false;
		}
		return true;
	}

	/**
	 * Génère un SHA1 à partir d'une chaîne de caractère Source :
	 * http://www.sha1-online.com/sha1-java/
	 * 
	 * @param input
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public static String sha1(String input) throws NoSuchAlgorithmException {
		MessageDigest mDigest = MessageDigest.getInstance("SHA1");
		byte[] result = mDigest.digest(input.getBytes());
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < result.length; i++) {
			sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16)
					.substring(1));
		}

		return sb.toString();
	}

	public static boolean isCourriel(String courriel) {
		String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
		java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
		java.util.regex.Matcher m = p.matcher(courriel);
		return m.matches();
	}

	public static ArrayList<Double> GetLatFromLoc(ArrayList<Location> p_lstPts) {
		ArrayList<Double> lstLat = new ArrayList<Double>();

		int iSizePoint = p_lstPts.size();
		for (int i = 0; i < iSizePoint; i++) {
			Location uneLoc = new Location(p_lstPts.get(i));
			lstLat.add(uneLoc.getLatitude());
		}

		return lstLat;
	}

	public static ArrayList<Double> GetLngFromLoc(ArrayList<Location> p_lstPts) {
		ArrayList<Double> lstLng = new ArrayList<Double>();

		int iSizePoint = p_lstPts.size();
		for (int i = 0; i < iSizePoint; i++) {
			Location uneLoc = new Location(p_lstPts.get(i));
			lstLng.add(uneLoc.getLatitude());
		}
		return lstLng;
	}

	public static boolean verifCodePostal(String p_CodePostal) {
		boolean verifCodePostal = false;
		String regExCodePostal = "^[A-Z][0-9][A-Z]?[0-9][A-Z][0-9]$";
		verifCodePostal = p_CodePostal.matches(regExCodePostal);

		return verifCodePostal;
	}
}
