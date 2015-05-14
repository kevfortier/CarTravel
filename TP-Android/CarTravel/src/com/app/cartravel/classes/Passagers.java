package com.app.cartravel.classes;

public class Passagers {
	private String m_IdParcours;
	private int m_IdPassager;
	private int m_NbrPassagers;

	/**
	 * Constructeur
	 */
	public Passagers(String p_IdParcours, int p_IdPassager, int p_NbrPassagers) {
		super();
		this.m_IdParcours = p_IdParcours;
		this.m_IdPassager = p_IdPassager;
		this.m_NbrPassagers = p_NbrPassagers;
	}

	public String getdParcours() {
		return m_IdParcours;
	}

	public void setIdParcours(String IdParcours) {
		this.m_IdParcours = IdParcours;
	}

	public int getPassager() {
		return m_IdPassager;
	}

	public void setIdPassager(int IdPassager) {
		this.m_IdPassager = IdPassager;
	}

	public int getNbrPassagers() {
		return m_NbrPassagers;
	}

	public void setNbrPassagers(int NbrPassagers) {
		this.m_NbrPassagers = NbrPassagers;
	}
}
