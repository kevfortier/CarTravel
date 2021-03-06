package com.app.cartravel.utilitaire;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

	// Version BD
	private final static int DB_VERSION = 2;

	// Nom de la BD
	private final static String DATABASE_NAME = "cartravel.sqlite";

	// Nom des tables
	public final static String TABLE_UTILISATEUR = "utilisateur";
	public final static String TABLE_PARCOURS = "parcours";
	public final static String TABLE_PARCOURS_PASSAGER = "parcours_passager";

	// Noms des colonnes d'un utilisateur
	public final static String COL_ID_USER = "id_utilisateur";
	public final static String COL_COURRIEL = "courriel";
	public final static String COL_PSEUDO = "pseudo";
	public final static String COL_MOTDEPASSE = "motdepasse";

	public final static String COL_NO_CIVIQUE = "no_civique";
	public final static String COL_RUE = "rue";
	public final static String COL_VILLE = "ville";
	public final static String COL_CODE_POSTAL = "code_postal";
	public final static String COL_NUM_TELEPHONE = "num_telephone";
	public final static String COL_VOITURE = "voiture";
	public final static String COL_RATING_COND = "rating_cond";
	public final static String COL_RATING_PASS = "rating_pass";

	public final static String COL_ESTCONNECTE = "estconnecte";
	public final static String COL_DERNIERCONNECTE = "dernierconnecte";

	public final static String COL_PROFIL_DATE = "date_profil";
	public final static String COL_USER_DATE = "date_user";

	// Noms des colonnes d'un parcour
	public final static String COL_ID_PARCOUR = "id_parcour";
	public final static String COL_ID_PROPRIETAIRE = "id_proprietaire";
	public final static String COL_ID_CONDUCTEUR = "id_conducteur";
	public final static String COL_JOUR = "jour";
	public final static String COL_HEURE = "heure";
	public final static String COL_TYPE_PARCOUR = "type";
	public final static String COL_NBR_PLACE_DISPO = "nbr_place_dispo";
	public final static String COL_NBR_PLACE_PRISE = "nbr_place_prise";
	public final static String COL_DISTANCE_SUP_MAX = "distance_max"; // Null si
																		// passager.

	public final static String COL_PARCOURS_DATE = "date_parcours";

	// Addresse d�part
	public final static String COL_NO_CIVIQUE_DEP = "no_civique_dep";
	public final static String COL_RUE_DEP = "rue_dep";
	public final static String COL_VILLE_DEP = "ville_dep";
	public final static String COL_CODE_POSTAL_DEP = "code_postal_dep";
	// Addresse arriv�
	public final static String COL_NO_CIVIQUE_ARR = "no_civique_arr";
	public final static String COL_RUE_ARR = "rue_arr";
	public final static String COL_VILLE_ARR = "ville_arr";
	public final static String COL_CODE_POSTAL_ARR = "code_postal_arr";

	// Noms des colonnes d'un parcour-passager
	public final static String COL_ID_PARCOURS_PASSAGER = "id_parcours_passager";
	public final static String COL_ID_PASSAGERS = "id_utilisateur";
	public final static String COL_NBR_PASSAGERS = "nbr_passagers";
	public final static String COL_PARCOURS_PASSAGERS_DATE = "date_parcours_passagers";

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		// Cr�e la table pour les utilisateurs
		db.execSQL("create table " + TABLE_UTILISATEUR + " (" + COL_ID_USER
				+ " integer primary key autoincrement, " + COL_COURRIEL
				+ " text, " + COL_PSEUDO + " text, " + COL_MOTDEPASSE
				+ " text, " + COL_NO_CIVIQUE + " text, " + COL_RUE + " Text, "
				+ COL_VILLE + " text, " + COL_CODE_POSTAL + " text, "
				+ COL_NUM_TELEPHONE + " text, " + COL_VOITURE + " integer, "
				+ COL_RATING_COND + " real, " + COL_RATING_PASS + " real, "
				+ COL_ESTCONNECTE + " real, " + COL_DERNIERCONNECTE + " real, "
				+ COL_USER_DATE + " text, " + COL_PROFIL_DATE + " text)");

		// Cr�e la table pour les parcours
		db.execSQL("create table " + TABLE_PARCOURS + " (" + COL_ID_PARCOUR
				+ " text primary key, " + COL_ID_PROPRIETAIRE + " real, "
				+ COL_ID_CONDUCTEUR + " real, " + COL_JOUR + " text, "
				+ COL_HEURE + " text, " + COL_TYPE_PARCOUR + " text, "
				+ COL_NBR_PLACE_DISPO + " real, " + COL_NBR_PLACE_PRISE
				+ " integer, " + COL_DISTANCE_SUP_MAX + " real, "
				+ COL_NO_CIVIQUE_DEP + " text, " + COL_RUE_DEP + " text, "
				+ COL_VILLE_DEP + " text, " + COL_CODE_POSTAL_DEP + " text, "
				+ COL_NO_CIVIQUE_ARR + " text, " + COL_RUE_ARR + " text, "
				+ COL_VILLE_ARR + " text, " + COL_CODE_POSTAL_ARR + " text, "
				+ COL_PARCOURS_DATE + " text)");

		// Cr�e la table pour les parcours-passagers
		db.execSQL("create table " + TABLE_PARCOURS_PASSAGER + " ("
				+ COL_ID_PARCOURS_PASSAGER + " text primary key, "
				+ COL_ID_PASSAGERS + " text, " + COL_NBR_PASSAGERS + " real, " 
				+ COL_PARCOURS_PASSAGERS_DATE +	" text)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("drop table if exists " + TABLE_UTILISATEUR);
		db.execSQL("drop table if exists " + TABLE_PARCOURS);
		db.execSQL("drop table if exists " + TABLE_PARCOURS_PASSAGER);
		this.onCreate(db);
	}
}