package com.app.cartravel.utilitaires.arrayadapters;

import java.io.Serializable;

public class ContactItem implements Serializable{
	private static final long serialVersionUID = 1;
	private String m_Courriel;
	private String m_Pseudo;
	
	public ContactItem(String p_courriel, String p_Pseudo){
		this.m_Courriel = p_courriel;
		this.m_Pseudo = p_Pseudo;
	}

	/**
	 * @return the m_Courriel
	 */
	public String getCourriel() {
		return m_Courriel;
	}

	/**
	 * @param m_Courriel the m_Courriel to set
	 */
	public void setCourriel(String m_Courriel) {
		this.m_Courriel = m_Courriel;
	}

	/**
	 * @return the m_Pseudo
	 */
	public String getPseudo() {
		return m_Pseudo;
	}

	/**
	 * @param m_Pseudo the m_Pseudo to set
	 */
	public void setPseudo(String m_Pseudo) {
		this.m_Pseudo = m_Pseudo;
	}
}