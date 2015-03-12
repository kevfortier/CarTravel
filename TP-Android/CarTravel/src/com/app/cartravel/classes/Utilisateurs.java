package com.app.cartravel.classes;

import java.util.ArrayList;
import java.util.List;

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
	private Boolean m_Voiture;
	private float m_NoteCond;
	private float m_NotePass;
	
	private Boolean m_EstConnecte;
	private Boolean m_DernierConnecte;

	/**
	 * Constructeur
	 */
	public Utilisateurs(int p_Id, String p_Courriel, String p_Pseudo,
			String p_MotDePasse, String p_NumCivique, String p_Rue,
			String p_Ville, String p_CodePostal, Boolean p_Voiture,
			float p_NoteCond, float p_NotePass,
			Boolean p_EstConnecte, Boolean p_DernierConnecte) 
	{
		super();
		this.m_Id = p_Id;
		this.m_Courriel = p_Courriel;
		this.m_Pseudo = p_Pseudo;
		this.m_MotDePasse = p_MotDePasse;
		
		this.m_NumCivique = p_NumCivique;
		this.m_Rue = p_Rue;
		this.m_Ville = p_Ville;
		this.m_CodePostal = p_CodePostal;
		this.m_Voiture = p_Voiture;
		this.m_NoteCond = p_NoteCond;
		this.m_NotePass = p_NotePass;
		
		this.m_EstConnecte = p_EstConnecte;
		this.m_DernierConnecte = p_DernierConnecte;
	}

	/**
	 * Constructeur sans ID
	 */
	public Utilisateurs(String p_Courriel, String p_Pseudo,
			String p_MotDePasse, String p_NumCivique, String p_Rue,
			String p_Ville, String p_CodePostal, Boolean p_Voiture,
			float p_NoteCond, float p_NotePass,
			Boolean p_EstConnecte, Boolean p_DernierConnecte) 
	{
		this.m_Courriel = p_Courriel;
		this.m_Pseudo = p_Pseudo;
		this.m_MotDePasse = p_MotDePasse;
		this.m_NumCivique = p_NumCivique;
		
		this.m_Rue = p_Rue;
		this.m_Ville = p_Ville;
		this.m_CodePostal = p_CodePostal;
		this.m_Voiture = p_Voiture;
		this.m_NoteCond = p_NoteCond;
		this.m_NotePass = p_NotePass;
		
		this.m_EstConnecte = p_EstConnecte;
		this.m_DernierConnecte = p_DernierConnecte;
	}

	/**
	 * Constructeur vide
	 */
	public Utilisateurs() {
		this.m_Id = ID_NON_DEFINI;
		this.m_EstConnecte = false;
		this.m_DernierConnecte = false;
	}

	public int getId() {
		return m_Id;
	}

	public void setId(int p_Id) {
		this.m_Id = p_Id;
	}

	public String getCourriel() {
		return m_Courriel;
	}

	public void setCourriel(String p_Courriel) {
		this.m_Courriel = p_Courriel;
	}

	public String getPseudo() {
		return m_Pseudo;
	}

	public void setPseudo(String p_Pseudo) {
		this.m_Pseudo = p_Pseudo;
	}

	public String getMotDePasse() {
		return m_MotDePasse;
	}

	public void setMotDePasse(String p_MotDePasse) {
		this.m_MotDePasse = p_MotDePasse;
	}
	
	public String getNumCivique()
	{
		return m_NumCivique;
	}
	
	public void setNumCivique(String p_NumCivique)
	{
		this.m_NumCivique = p_NumCivique;
	}
	
	public String getRue()
	{
		return m_Rue;
	}
	
	public void setRue(String p_Rue)
	{
		m_Rue = p_Rue;
	}
	
	public String getVille()
	{
		return m_Ville;
	}
	
	public void setVille(String p_Ville)
	{
		m_Ville = p_Ville;
	}
	
	public String getCodePostal()
	{
		return m_CodePostal;
	}
	
	public void setCodePostal(String p_CodePostal)
	{
		m_CodePostal= p_CodePostal;
	}
	
	public boolean getVoiture()
	{
		return m_Voiture;
	}
	
	public void setVoiture(boolean p_Voiture)
	{
		m_Voiture = p_Voiture;
	}
	
	public float getNoteCond()
	{
		return m_NoteCond;
	}
	
	public void setNoteCond(float p_NoteCond)
	{
		m_NoteCond = p_NoteCond;
	}
	
	public float getNotePass()
	{
		return m_NotePass;
	}
	
	public void setNotePass(float p_NotePass)
	{
		m_NotePass = p_NotePass;
	}
	
	public Boolean getEstConnecte() {
		return m_EstConnecte;
	}

	public void setEstConnecte(Boolean p_EstConnecte) {
		this.m_EstConnecte = p_EstConnecte;
	}
	
	public Boolean getDernierConnecte() {
		return m_DernierConnecte;
	}

	public void setDernierConnecte(Boolean p_DernierConnecte) {
		this.m_DernierConnecte = p_DernierConnecte;
	}
}
