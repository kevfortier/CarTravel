package com.app.cartravel.utilitaire;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

	// Version BD
	private final static int DB_VERSION = 11;

	// Nom de la BD
	private final static String DATABASE_NAME = "cartravel.sqlite";

	// Nom des tables
	public final static String TABLE_UTILISATEUR = "utilisateur";
	public final static String TABLE_PARCOURS = "parcours";
	public final static String TABLE_PARCOUR_PASSAGER = "parcour_passager";

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
	// Addresse départ
	public final static String COL_NO_CIVIQUE_DEP = "no_civique_dep";
	public final static String COL_RUE_DEP = "rue_dep";
	public final static String COL_VILLE_DEP = "ville_dep";
	public final static String COL_CODE_POSTAL_DEP = "code_postal_dep";
	// Addresse arrivé
	public final static String COL_NO_CIVIQUE_ARR = "no_civique_arr";
	public final static String COL_RUE_ARR = "rue_arr";
	public final static String COL_VILLE_ARR = "ville_arr";
	public final static String COL_CODE_POSTAL_ARR = "code_postal_arr";

	// Noms des colonnes d'un parcour-passager
	public final static String COL_ID_PASSAGER = "id_utilisateur";
	public final static String COL_NBR_PASSAGER = "nbr_passager";

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		// Crée la table pour les utilisateurs
		db.execSQL("create table " + TABLE_UTILISATEUR + " (" + COL_ID_USER
				+ " integer primary key autoincrement, " + COL_COURRIEL
				+ " text, " + COL_PSEUDO + " text, " + COL_MOTDEPASSE
				+ " text, " + COL_NO_CIVIQUE + " text, " + COL_RUE + " Text, "
				+ COL_VILLE + " text, " + COL_CODE_POSTAL + " text, "
				+ COL_NUM_TELEPHONE + " text, " + COL_VOITURE + " integer, "
				+ COL_RATING_COND + " real, " + COL_RATING_PASS + " real, "
				+ COL_ESTCONNECTE + " integer, " + COL_DERNIERCONNECTE
				+ " integer)");

		// Crée la table pour les parcours
		db.execSQL("create table " + TABLE_PARCOURS + " (" + COL_ID_PARCOUR
				+ " integer primary key autoincrement, " + COL_ID_PROPRIETAIRE
				+ " integer, " + COL_ID_CONDUCTEUR + " integer, " + COL_JOUR
				+ " text, " + COL_HEURE + " text, " + COL_TYPE_PARCOUR
				+ " text, " + COL_NBR_PLACE_DISPO + " integer, "
				+ COL_NBR_PLACE_PRISE + " integer, " + COL_DISTANCE_SUP_MAX
				+ " real, " + COL_NO_CIVIQUE_DEP + " text, " + COL_RUE_DEP
				+ " text, " + COL_VILLE_DEP + " text, " + COL_CODE_POSTAL_DEP
				+ " text, " + COL_NO_CIVIQUE_ARR + " text, " + COL_RUE_ARR
				+ " text, " + COL_VILLE_ARR + " text, " + COL_CODE_POSTAL_ARR
				+ " text)");

		// Crée la table pour les parcours-passagers
		db.execSQL("create table " + TABLE_PARCOUR_PASSAGER + " ("
				+ COL_ID_PARCOUR + " text, " + COL_ID_PASSAGER + " integer, "
				+ COL_NBR_PASSAGER + " integer, " + " primary key ("
				+ COL_ID_PARCOUR + ", " + COL_ID_PASSAGER + "))");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("drop table if exists " + TABLE_UTILISATEUR);
		db.execSQL("drop table if exists " + TABLE_PARCOURS);
		db.execSQL("drop table if exists " + TABLE_PARCOUR_PASSAGER);
		this.onCreate(db);
	}
}