package com.app.cartravel.utilitaires.ArrayAdapters;

public class ParcoursItem {
	private String m_Date;
	private String m_Adresse;

	public ParcoursItem(String p_Date, String p_Adresse) {
		this.m_Date = p_Date;
		this.m_Adresse = p_Adresse;
	}

	public String getDate() {
		return m_Date;
	}

	public void setDate(String m_Date) {
		this.m_Date = m_Date;
	}

	public String getAdresse() {
		return m_Adresse;
	}

	public void setAdresse(String m_Adresse) {
		this.m_Adresse = m_Adresse;
	}
}
