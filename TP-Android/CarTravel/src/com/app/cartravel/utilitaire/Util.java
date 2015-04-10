package com.app.cartravel.utilitaire;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.res.TypedArray;
import android.location.Location;

public class Util {

	public final static String REST_UTILISATEUR = "/utilisateurs";
	public final static String REST_PARCOURS = "/parcours";
	public final static String REST_CONNEXION = "/connexion";
	public final static String WEB_SERVICE = "192.168.0.110:8080";

	public final static String GOOGLE_SENDER_ID = "133519372687";

	/**
	 * Permet de transformer une chaine de caracteres en une liste selon un
	 * sÈparateur
	 * 
	 * @param parse
	 * @param separateur
	 * @return
	 */
	public static List<String> ParseStringToList(String parse, String separateur) {
		List<String> liste = null;
		if (parse != null && !parse.equals("[]")) {
			liste = new ArrayList<String>();
			// Enl√®ve les "[" et "]" entourant la cha√Æne de caract√®res
			parse = parse.replace("[", "").replace("]", "").trim();
			// S√©pare la cha√Æne selon le s√©parateur sp√©cifi√©
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
	 * Enl√®ve les √©l√©ments vides d'une liste de String
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
	 * Permet de valider si les cha√Ænes de caract√®res existent
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
	 * G√©n√®re un SHA1 √† partir d'une cha√Æne de caract√®re Source :
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

	//Permet de vÈrifier les heures saisies
	public static boolean verifHeure(String p_Heure) {
		boolean verifHeure = false;
		int iHeure = Integer.parseInt(p_Heure);
		if (iHeure >= 0 && iHeure <= 23) {
			verifHeure = true;
		}
		return verifHeure;
	}
	
	//Permet de vÈrifier les minutes d'une heure saisie
	public static boolean verifMinute(String p_Minute) {
		boolean verifMinute = false;
		int iMinutes = Integer.parseInt(p_Minute);
		if (iMinutes >= 0 && iMinutes <= 59) {
			verifMinute = true;
		}
		return verifMinute;
	}
	
	//Permet de vÈrifier une date de 1900 ‡ 2099
	public static boolean verifDate(String p_Date) {
		String regExDate = "(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((19|20)\\d\\d)";
		Pattern pattern = Pattern.compile(regExDate);
		Matcher matcher = pattern.matcher(p_Date);
		
		if(matcher.matches()){
			matcher.reset();
			if(matcher.find()){
				String dd = matcher.group(1);
			    String mm = matcher.group(2);
			    int yy = Integer.parseInt(matcher.group(3));
			    if (dd.equals("31") &&  (mm.equals("4") || mm .equals("6") || mm.equals("9") ||
			                  mm.equals("11") || mm.equals("04") || mm .equals("06") ||
			                  mm.equals("09"))) {
			    	return false;
			    } else if (mm.equals("2") || mm.equals("02")) {
			    	if(yy % 4==0){
			    		if(dd.equals("30") || dd.equals("31")){
			    			return false;
			            } else{
			                return true;
			            }
			        } else{
			        	if(dd.equals("29")||dd.equals("30")||dd.equals("31")){
			        		return false;
			            } else{
			            	return true;
			            }
			        }
			    } else{
			    	return true;
			    }
			}else{
				return false;
			}
		}else{
			return false;
		}  
	}
	
	//Permet de vÈrifier les codes postals.
	public static boolean verifCodePostal(String p_CodePostal) {
		boolean verifCodePostal = false;
		String regExCodePostal = "^[a-zA-Z][0-9][a-zA-Z]?[0-9][a-zA-Z][0-9]$";
		verifCodePostal = p_CodePostal.matches(regExCodePostal);
	
		return verifCodePostal;
	}
	
	//Permet de vÈrifier les numÈros de tÈlÈphone.
	public static boolean verifNumTel(String p_NumTel) {
		boolean verifNumTel = false;
		String regExNumTel = "\\d{10}";
		verifNumTel = p_NumTel.matches(regExNumTel);

		return verifNumTel;
	}

	public static boolean verifChaineCharac(String p_Chaine) {
		boolean verifChaine = false;
		String regEx = "[a-zA-Z ]+";
		verifChaine = p_Chaine.matches(regEx);

		return verifChaine;
	}
}
