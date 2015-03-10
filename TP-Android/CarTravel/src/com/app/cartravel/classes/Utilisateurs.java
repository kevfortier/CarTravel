package com.app.cartravel.classes;

import java.util.ArrayList;
import java.util.List;

public class Utilisateurs {

	public static final int ID_NON_DEFINI = -1;
	
	private int m_Id;
	private String m_Courriel;
	private String m_Pseudo;
	private String m_MotDePasse;
	private Boolean m_EstConnecte;
	private Boolean m_DernierConnecte;

	/**
	 * Constructeur
	 */
	public Utilisateurs(int p_Id, String p_Courriel, String p_Pseudo,
			String p_MotDePasse, Boolean p_EstConnecte, Boolean p_DernierConnecte) {
		super();
		this.m_Id = p_Id;
		this.m_Courriel = p_Courriel;
		this.m_Pseudo = p_Pseudo;
		this.m_MotDePasse = p_MotDePasse;
		this.m_EstConnecte = p_EstConnecte;
		this.m_DernierConnecte = p_DernierConnecte;
	}

	/**
	 * Constructeur sans ID
	 */
	public Utilisateurs(String p_Courriel, String p_Pseudo,
			String p_MotDePasse, Boolean p_EstConnecte, Boolean p_DernierConnecte,
			List<String> p_Contacts) {
		this.m_Courriel = p_Courriel;
		this.m_Pseudo = p_Pseudo;
		this.m_MotDePasse = p_MotDePasse;
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
