package com.app.cartravel.utilitaires.navigationdrawer;

public class DrawerItem {
	private String m_Nom;
	private int m_IdIcone;
	private int m_NbNotifications;

	/**
	 * @param p_NomItem
	 * @param p_ItemIconeId
	 *            (-1 si aucune icone)
	 * @param p_NbNotifications
	 */
	public DrawerItem(String p_Nom, int p_IdIcone, int p_NbNotifications) {
		super();
		this.m_Nom = p_Nom;
		this.m_IdIcone = p_IdIcone;
		this.m_NbNotifications = p_NbNotifications;
	}

	/**
	 * @return the m_Nom
	 */
	public String getNom() {
		return m_Nom;
	}

	/**
	 * @param m_Nom
	 *            the m_Nom to set
	 */
	public void setNom(String m_Nom) {
		this.m_Nom = m_Nom;
	}

	/**
	 * @return the m_IdIcone
	 */
	public int getIdIcone() {
		return m_IdIcone;
	}

	/**
	 * @param m_IdIcone
	 *            the m_IdIcone to set
	 */
	public void setIdIcone(int m_IdIcone) {
		this.m_IdIcone = m_IdIcone;
	}

	/**
	 * @return the m_NbNotifications
	 */
	public int getNbNotifications() {
		return m_NbNotifications;
	}

	/**
	 * @param m_NbNotifications
	 *            the m_NbNotifications to set
	 */
	public void setNbNotifications(int m_NbNotifications) {
		this.m_NbNotifications = m_NbNotifications;
	}
}