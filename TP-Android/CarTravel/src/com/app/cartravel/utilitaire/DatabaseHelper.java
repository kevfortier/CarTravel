package com.app.cartravel.utilitaire;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

	// Version BD
	private final static int DB_VERSION = 1;

	// Nom
	private final static String DATABASE_NAME = "cartravel.sqlite";

	// Nom des tables
	public final static String TABLE_UTILISATEUR = "utilisateur";
	public final static String TABLE_PARCOUR = "parcour";

	// Noms des colonnes d'un utilisateur
	public final static String COL_ID_USER = "id_utilisateur";
	public final static String COL_COURRIEL = "courriel";
	public final static String COL_PSEUDO = "pseudo";
	public final static String COL_MOTDEPASSE = "motdepasse";
	public final static String COL_ESTCONNECTE = "estconnecte";
	public final static String COL_DERNIERCONNECTE = "dernierconnecte";
	public final static String COL_NO_CIVIQUE = "no_civique";
	public final static String COL_RUE = "rue";
	public final static String COL_VILLE = "ville";
	public final static String COL_CODE_POSTAL = "code_postal";
	public final static String COL_RATING = "rating";
	
	// Noms des colonnes d'un parcour
	public final static String COL_ID_PARCOUR = "id_parcour";
	public final static String COL_ID_CONDUCTEUR = "id_utilisateur";
	public final static String COL_JOUR = "jour";
	public final static String COL_HEURE = "heure";
	public final static String COL_TYPE_PARCOUR = "type";
	public final static String COL_NBR_PLACE_DISPO = "nbr_place_dispo";
	public final static String COL_NBR_PLACE_PRISE = "nbr-place_prise";
	public final static String COL_DISTANCE_SUP_MAX = "distance_max"; //Null si passager.
	//Addresse départ
	public final static String COL_NO_CIVIQUE_DEP = "no_civique_dep";
	public final static String COL_RUE_DEP = "rue_dep";
	public final static String COL_VILLE_DEP = "ville_dep";
	public final static String COL_CODE_POSTAL_DEP = "code_postal_dep";
	//Addresse arrivé
	public final static String COL_NO_CIVIQUE_ARR = "no_civique_arr";
	public final static String COL_RUE_ARR = "rue_arr";
	public final static String COL_VILLE_ARR = "ville_arr";
	public final static String COL_CODE_POSTAL_ARR = "code_postal_arr";
	

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		// CrÃ©e la table pour les utilisateurs
		db.execSQL("create table " + TABLE_UTILISATEUR + " (" + COL_ID_USER
				+ " integer primary key autoincrement, " + COL_COURRIEL
				+ " text, " + COL_PSEUDO + " text, " + COL_MOTDEPASSE
				+ " text, " + COL_ESTCONNECTE + " text, " + COL_DERNIERCONNECTE
				+ " text, " + COL_NO_CIVIQUE + " text, " + COL_RUE 
				+ " Text, " + COL_VILLE + " text, " + COL_CODE_POSTAL
				+ " text, " + COL_RATING + " text)");
		
		db.execSQL("create table " + TABLE_PARCOUR + " (" + COL_ID_USER
				+ " integer primary key autoincrement, " + COL_COURRIEL
				+ " text, " + COL_PSEUDO + " text, " + COL_MOTDEPASSE
				+ " text, " + COL_ESTCONNECTE + " text, " + COL_DERNIERCONNECTE
				+ " text, " + COL_NO_CIVIQUE + " text, " + COL_RUE 
				+ " Text, " + COL_VILLE + " text, " + COL_CODE_POSTAL
				+ " text, " + COL_RATING + " text)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("drop table if exists " + TABLE_UTILISATEUR);
		db.execSQL("drop table if exists " + TABLE_PARCOUR);
		this.onCreate(db);
	}
}
