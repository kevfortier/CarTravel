package com.app.cartravel.classes;

public class Utilisateurs {

	public static final int ID_NON_DEFINI = -1;

	private int m_Id;
	private String m_Courriel;
	private String m_Pseudo;
	private String m_MotDePasse;

	private String m_NumCivique;
	private String m_Rue;
	private String m_Ville;
	private String m_CodePostal;
	private String m_NumTel;
	private int m_Voiture;
	private float m_NoteCond;
	private float m_NotePass;

	private int m_EstConnecte;
	private int m_DernierConnecte;

	private String m_DateAjoutUser;
	private String m_DateAjoutProfil;

	/**
	 * Constructeur
	 */
	public Utilisateurs(int p_Id, String p_Courriel, String p_Pseudo,
			String p_MotDePasse, String p_NumCivique, String p_Rue,
			String p_Ville, String p_CodePostal, String P_NumTel,
			int p_Voiture, float p_NoteCond, float p_NotePass,
			int p_EstConnecte, int p_DernierConnecte, String p_DateAjoutUser,
			String p_DateAjoutProfil) {
		super();
		this.m_Id = p_Id;
		this.m_Courriel = p_Courriel;
		this.m_Pseudo = p_Pseudo;
		this.m_MotDePasse = p_MotDePasse;

		this.m_NumCivique = p_NumCivique;
		this.m_Rue = p_Rue;
		this.m_Ville = p_Ville;
		this.m_CodePostal = p_CodePostal;
		this.m_NumTel = P_NumTel;
		this.m_Voiture = p_Voiture;
		this.m_NoteCond = p_NoteCond;
		this.m_NotePass = p_NotePass;

		this.m_EstConnecte = p_EstConnecte;
		this.m_DernierConnecte = p_DernierConnecte;

		this.m_DateAjoutUser = p_DateAjoutUser;
		this.m_DateAjoutProfil = p_DateAjoutProfil;
	}

	/**
	 * Constructeur sans ID
	 */
	public Utilisateurs(String p_Courriel, String p_Pseudo,
			String p_MotDePasse, String p_NumCivique, String p_Rue,
			String p_Ville, String p_CodePostal, String p_NumTel,
			int p_Voiture, float p_NoteCond, float p_NotePass,
			int p_EstConnecte, int p_DernierConnecte, String p_DateAjout,
			String p_DateAjoutProfil) {
		this.m_Courriel = p_Courriel;
		this.m_Pseudo = p_Pseudo;
		this.m_MotDePasse = p_MotDePasse;
		this.m_NumCivique = p_NumCivique;

		this.m_Rue = p_Rue;
		this.m_Ville = p_Ville;
		this.m_CodePostal = p_CodePostal;
		this.m_NumTel = p_NumTel;
		this.m_Voiture = p_Voiture;
		this.m_NoteCond = p_NoteCond;
		this.m_NotePass = p_NotePass;

		this.m_EstConnecte = p_EstConnecte;
		this.m_DernierConnecte = p_DernierConnecte;

		this.m_DateAjoutUser = p_DateAjout;
		this.m_DateAjoutProfil = p_DateAjoutProfil;
	}

	/**
	 * Constructeur vide
	 */
	public Utilisateurs() {
		this.m_Id = ID_NON_DEFINI;
		this.m_EstConnecte = 0;
		this.m_DernierConnecte = 0;
	}

	public int getId() {
		return m_Id;
	}

	public void setId(int m_Id) {
		this.m_Id = m_Id;
	}

	public String getCourriel() {
		return m_Courriel;
	}

	public void setCourriel(String m_Courriel) {
		this.m_Courriel = m_Courriel;
	}

	public String getPseudo() {
		return m_Pseudo;
	}

	public void setPseudo(String m_Pseudo) {
		this.m_Pseudo = m_Pseudo;
	}

	public String getMotDePasse() {
		return m_MotDePasse;
	}

	public void setMotDePasse(String m_MotDePasse) {
		this.m_MotDePasse = m_MotDePasse;
	}

	public String getNumCivique() {
		return m_NumCivique;
	}

	public void setNumCivique(String m_NumCivique) {
		this.m_NumCivique = m_NumCivique;
	}

	public String getRue() {
		return m_Rue;
	}

	public void setRue(String m_Rue) {
		this.m_Rue = m_Rue;
	}

	public String getVille() {
		return m_Ville;
	}

	public void setVille(String m_Ville) {
		this.m_Ville = m_Ville;
	}

	public String getCodePostal() {
		return m_CodePostal;
	}

	public void setCodePostal(String m_CodePostal) {
		this.m_CodePostal = m_CodePostal;
	}

	public String getNumTel() {
		return m_NumTel;
	}

	public void setNumTel(String m_NumTel) {
		this.m_NumTel = m_NumTel;
	}

	public int getVoiture() {
		return m_Voiture;
	}

	public void setVoiture(int m_Voiture) {
		this.m_Voiture = m_Voiture;
	}

	public float getNoteCond() {
		return m_NoteCond;
	}

	public void setNoteCond(float m_NoteCond) {
		this.m_NoteCond = m_NoteCond;
	}

	public float getNotePass() {
		return m_NotePass;
	}

	public void setNotePass(float m_NotePass) {
		this.m_NotePass = m_NotePass;
	}

	public int getEstConnecte() {
		return m_EstConnecte;
	}

	public void setEstConnecte(int m_EstConnecte) {
		this.m_EstConnecte = m_EstConnecte;
	}

	public int getDernierConnecte() {
		return m_DernierConnecte;
	}

	public void setDernierConnecte(int m_DernierConnecte) {
		this.m_DernierConnecte = m_DernierConnecte;
	}

	public String getDateAjoutUser() {
		return m_DateAjoutUser;
	}

	public void setDateAjoutUser(String dateAjoutUser) {
		this.m_DateAjoutUser = dateAjoutUser;
	}

	public String getDateAjoutProfil() {
		return m_DateAjoutProfil;
	}

	public void setDateAjoutProfil(String dateAjoutProfil) {
		this.m_DateAjoutProfil = dateAjoutProfil;
	}
}
