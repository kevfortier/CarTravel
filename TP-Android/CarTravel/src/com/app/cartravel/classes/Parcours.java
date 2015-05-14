package com.app.cartravel.classes;

public class Parcours implements java.io.Serializable {

	private static final long serialVersionUID = 1;

	public static final String ID_NON_DEFINI = "-1";

	private String m_Id;
	private int m_IdProprietaire;
	private int m_IdConducteur;
	private String m_Jour;
	private String m_Heure;
	private boolean m_Repetitif;
	private int m_NbPlaceDispo;
	private int m_NbPlacePrise;
	private float m_DistanceSupMax;

	private String m_NumCiviqueDep;
	private String m_RueDep;
	private String m_VilleDep;
	private String m_CodePostalDep;

	private String m_NumCiviqueArr;
	private String m_RueArr;
	private String m_VilleArr;
	private String m_CodePostalArr;
	private String m_DateAjout;

	
	/**
	 * Constructeur globale
	 
	public Parcours(String p_Id, int p_IdProprietaire, int p_IdConducteur,
			String p_Jour, String p_Heure, boolean p_Repetitif,
			int p_NbPlacePassagers, int p_NbPlaceDispo, int p_NbPlacePrise,
			float p_DistanceSupMax, String p_NumCiviqueDep, String p_RueDep,
			String p_VilleDep, String p_CodePostalDep, String p_NumCiviqueArr,
			String p_RueArr, String p_VilleArr, String p_CodePostalArr,
			String p_DateAjout) {
		super();
		this.m_Id = p_Id;
		this.m_IdProprietaire = p_IdProprietaire;
		this.m_IdConducteur = p_IdConducteur;
		this.m_Jour = p_Jour;
		this.m_Heure = p_Heure;
		this.m_Repetitif = p_Repetitif;
		this.m_NbPlaceDispo = p_NbPlaceDispo;
		this.m_NbPlacePrise = p_NbPlacePrise;
		this.m_DistanceSupMax = p_DistanceSupMax;

		this.m_NumCiviqueDep = p_NumCiviqueDep;
		this.m_RueDep = p_RueDep;
		this.m_VilleDep = p_VilleDep;
		this.m_CodePostalDep = p_CodePostalDep;

		this.m_NumCiviqueArr = p_NumCiviqueArr;
		this.m_RueArr = p_RueArr;
		this.m_VilleArr = p_VilleArr;
		this.m_CodePostalArr = p_CodePostalArr;

		this.m_DateAjout = p_DateAjout;
	}*/
	
	/**
	 * Constructeur pour Conducteur
	 */
	public Parcours(String p_Id, int p_IdProprietaire, int p_IdConducteur,
			String p_Jour, String p_Heure, boolean p_Repetitif,
			int p_NbPlaceDispo, int p_NbPlacePrise, float p_DistanceSupMax,
			String p_NumCiviqueDep, String p_RueDep, String p_VilleDep,
			String p_CodePostalDep, String p_NumCiviqueArr, String p_RueArr,
			String p_VilleArr, String p_CodePostalArr, String p_DateAjout) {
		super();
		this.m_Id = p_Id;
		this.m_IdProprietaire = p_IdProprietaire;
		this.m_IdConducteur = p_IdConducteur;
		this.m_Jour = p_Jour;
		this.m_Heure = p_Heure;
		this.m_Repetitif = p_Repetitif;
		this.m_NbPlaceDispo = p_NbPlaceDispo;
		this.m_NbPlacePrise = p_NbPlacePrise;
		this.m_DistanceSupMax = p_DistanceSupMax;

		this.m_NumCiviqueDep = p_NumCiviqueDep;
		this.m_RueDep = p_RueDep;
		this.m_VilleDep = p_VilleDep;
		this.m_CodePostalDep = p_CodePostalDep;

		this.m_NumCiviqueArr = p_NumCiviqueArr;
		this.m_RueArr = p_RueArr;
		this.m_VilleArr = p_VilleArr;
		this.m_CodePostalArr = p_CodePostalArr;

		this.m_DateAjout = p_DateAjout;
	}

	/**
	 * Constructeur sans ID pour conducteur
	 */
	public Parcours(int p_IdProprietaire, int p_IdConducteur, String p_Jour,
			String p_Heure, boolean p_Repetitif, int p_NbPlaceDispo,
			float p_DistanceSupMax, String p_NumCiviqueDep, String p_RueDep,
			String p_VilleDep, String p_CodePostalDep, String p_NumCiviqueArr,
			String p_RueArr, String p_VilleArr, String p_CodePostalArr,
			String p_DateAjout) {
		super();
		this.m_Id = ID_NON_DEFINI;
		this.m_IdProprietaire = p_IdProprietaire;
		this.m_IdConducteur = p_IdConducteur;
		this.m_Jour = p_Jour;
		this.m_Heure = p_Heure;
		this.m_Repetitif = p_Repetitif;
		this.m_NbPlaceDispo = p_NbPlaceDispo;
		this.m_DistanceSupMax = p_DistanceSupMax;

		this.m_NumCiviqueDep = p_NumCiviqueDep;
		this.m_RueDep = p_RueDep;
		this.m_VilleDep = p_VilleDep;
		this.m_CodePostalDep = p_CodePostalDep;

		this.m_NumCiviqueArr = p_NumCiviqueArr;
		this.m_RueArr = p_RueArr;
		this.m_VilleArr = p_VilleArr;
		this.m_CodePostalArr = p_CodePostalArr;

		this.m_DateAjout = p_DateAjout;
	}

	/**
	 * Constructeur pour passagers
	 */
	public Parcours(String p_Id, int p_IdProprietaire, String p_Jour,
			String p_Heure, boolean p_Repetitif,
			String p_NumCiviqueDep, String p_RueDep, String p_VilleDep,
			String p_CodePostalDep, String p_NumCiviqueArr, String p_RueArr,
			String p_VilleArr, String p_CodePostalArr, String p_DateAjout) {
		super();
		this.m_Id = p_Id;
		this.m_IdProprietaire = p_IdProprietaire;
		this.m_Jour = p_Jour;
		this.m_Heure = p_Heure;
		this.m_Repetitif = p_Repetitif;

		this.m_NumCiviqueDep = p_NumCiviqueDep;
		this.m_RueDep = p_RueDep;
		this.m_VilleDep = p_VilleDep;
		this.m_CodePostalDep = p_CodePostalDep;

		this.m_NumCiviqueArr = p_NumCiviqueArr;
		this.m_RueArr = p_RueArr;
		this.m_VilleArr = p_VilleArr;
		this.m_CodePostalArr = p_CodePostalArr;

		this.m_DateAjout = p_DateAjout;
	}

	/**
	 * Constructeur sans ID pour passagers
	 */
	public Parcours(int p_IdProprietaire, String p_Jour, String p_Heure,
			boolean p_Repetitif,
			String p_NumCiviqueDep, String p_RueDep, String p_VilleDep,
			String p_CodePostalDep, String p_NumCiviqueArr, String p_RueArr,
			String p_VilleArr, String p_CodePostalArr, String p_DateAjout) {
		super();
		this.m_Id = ID_NON_DEFINI;
		this.m_IdProprietaire = p_IdProprietaire;
		this.m_Jour = p_Jour;
		this.m_Heure = p_Heure;
		this.m_Repetitif = p_Repetitif;

		this.m_NumCiviqueDep = p_NumCiviqueDep;
		this.m_RueDep = p_RueDep;
		this.m_VilleDep = p_VilleDep;
		this.m_CodePostalDep = p_CodePostalDep;

		this.m_NumCiviqueArr = p_NumCiviqueArr;
		this.m_RueArr = p_RueArr;
		this.m_VilleArr = p_VilleArr;
		this.m_CodePostalArr = p_CodePostalArr;

		this.m_DateAjout = p_DateAjout;
	}

	public Parcours() {
		this.m_Id = ID_NON_DEFINI;
	}

	public String getId() {
		return m_Id;
	}

	public void setId(String Id) {
		this.m_Id = Id;
	}

	public int getIdProprietaire() {
		return m_IdProprietaire;
	}

	public void setIdProprietaire(int IdProprietaire) {
		this.m_IdProprietaire = IdProprietaire;
	}

	public int getIdConducteur() {
		return m_IdConducteur;
	}

	public void setIdConducteur(int IdConducteur) {
		this.m_IdConducteur = IdConducteur;
	}

	public String getJour() {
		return m_Jour;
	}

	public void setJour(String Jour) {
		this.m_Jour = Jour;
	}

	public String getHeure() {
		return m_Heure;
	}

	public void setHeure(String Heure) {
		this.m_Heure = Heure;
	}

	public String getNumCiviqueDep() {
		return m_NumCiviqueDep;
	}

	public void setNumCiviqueDep(String NumCiviqueDep) {
		this.m_NumCiviqueDep = NumCiviqueDep;
	}

	public String getRueDep() {
		return m_RueDep;
	}

	public void setRueDep(String RueDep) {
		this.m_RueDep = RueDep;
	}

	public String getVilleDep() {
		return m_VilleDep;
	}

	public void setVilleDep(String VilleDep) {
		this.m_VilleDep = VilleDep;
	}

	public String getCodePostalDep() {
		return m_CodePostalDep;
	}

	public void setCodePostalDep(String CodePostalDep) {
		this.m_CodePostalDep = CodePostalDep;
	}

	public String getNumCiviqueArr() {
		return m_NumCiviqueArr;
	}

	public void setNumCiviqueArr(String NumCiviqueArr) {
		this.m_NumCiviqueArr = NumCiviqueArr;
	}

	public String getRueArr() {
		return m_RueArr;
	}

	public void setRueArr(String RueArr) {
		this.m_RueArr = RueArr;
	}

	public String getVilleArr() {
		return m_VilleArr;
	}

	public void setVilleArr(String VilleArr) {
		this.m_VilleArr = VilleArr;
	}

	public String getCodePostalArr() {
		return m_CodePostalArr;
	}

	public void setCodePostalArr(String CodePostalArr) {
		this.m_CodePostalArr = CodePostalArr;
	}

	public boolean getRepetitif() {
		return m_Repetitif;
	}

	public void setTypeParcours(boolean Repetitif) {
		this.m_Repetitif = Repetitif;
	}

	public int getNbPlaceDispo() {
		return m_NbPlaceDispo;
	}

	public void setNbPlaceDispo(int NbPlaceDispo) {
		this.m_NbPlaceDispo = NbPlaceDispo;
	}

	public int getNbPlacePrise() {
		return m_NbPlacePrise;
	}

	public void setNbPlacePrise(int NbPlacePrise) {
		this.m_NbPlacePrise = NbPlacePrise;
	}

	public float getDistanceSupMax() {
		return m_DistanceSupMax;
	}

	public void setDistanceSupMax(float DistanceSupMax) {
		this.m_DistanceSupMax = DistanceSupMax;
	}

	public String getDateAjout() {
		return m_DateAjout;
	}

	public void setDateAjout(String DateAjout) {
		this.m_DateAjout = DateAjout;
	}
}
