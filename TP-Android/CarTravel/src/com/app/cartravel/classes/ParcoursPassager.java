package com.app.cartravel.classes;

import java.util.ArrayList;
import java.util.List;

public class ParcoursPassager implements java.io.Serializable {

	private static final long serialVersionUID = 1;
	public static final String ID_NON_DEFINI = "-1";

	private String m_Id;
	private List<String> m_List_Id_Passager;
	private int m_Nbr_Passagers;
	private String m_DateAjout;

	/**
	 * Constructeur pour passager Si jamais l'utilisateur est un passager et
	 * qu'il entre un nombre de passagers plus élevé que 1, on remplit le reste
	 * de la liste par des zéros et on s'organise pour la gestion plus tard en
	 * vérifiant si tous les passagers sont des utilisateurs de l'app. ou non.
	 */
	public ParcoursPassager(String p_Id, String p_Id_Passager,
			int p_Nbr_Passagers, String p_DateAjout) {
		super();
		this.m_Id = p_Id;
		this.m_List_Id_Passager = new ArrayList<String>();
		this.m_List_Id_Passager.add(p_Id_Passager);
		this.m_Nbr_Passagers = p_Nbr_Passagers;
		if (p_Nbr_Passagers > 1) {
			for (int i = 1; i < p_Nbr_Passagers; i++) {
				this.m_List_Id_Passager.add("0");
			}
		}
		this.m_DateAjout = p_DateAjout;
	}
	
	/**
	 * Constructeur lorsqu'on va chercher un ParcoursPassager sur
	 * le service Web.
	 * @param p_Id
	 * @param p_IdPassagers
	 * @param p_Nbr_Passagers
	 */
	public ParcoursPassager(String p_Id, List<String> p_IdPassagers,
			int p_Nbr_Passagers, String p_DateAjout){
		this.m_Id = p_Id;
		this.m_List_Id_Passager = p_IdPassagers;
		this.m_Nbr_Passagers= p_Nbr_Passagers;
		this.m_DateAjout = p_DateAjout;
	}

	public ParcoursPassager() {
		m_Id = ID_NON_DEFINI;
	}

	public String getIdParcoursPassager() {
		return m_Id;
	}

	public void SetIdParcoursPassager(String Id) {
		this.m_Id = Id;
	}

	public List<String> getListIdPassager() {
		return m_List_Id_Passager;
	}

	public boolean getPassagerPresent(String IdPassager) {
		return m_List_Id_Passager.contains(IdPassager);
	}

	public void setListIdPassager(List<String> List_Id_Passager) {
		this.m_List_Id_Passager = List_Id_Passager;
	}

	public void setListIdPassager(String IdPassager, int Participation,
			int NbrPassagers) {
		// Si un utilisateur se propose en tant que passager, le
		// paramètre Participation sera égale à 1, s'il décide de s'enlever
		// alors
		// le paramètre Participation sera égale à 0;
		if (Participation == 1) {
			int i = 1;

			// On s'assure que le nouveau passager s'ajoute à la fin de la liste
			// et non au début.
			while (!m_List_Id_Passager.get(i).equals(null)) {
				i++;
			}
			m_List_Id_Passager.add(i, IdPassager);

			// Si le nombre de passagers à une participation est supérieur à un.
			if (NbrPassagers > 1) {
				// Position après le nouveau passager.
				i++;
				while (i < (NbrPassagers + 1)) {
					m_List_Id_Passager.add(i, "0");
					i++;
				}
				// Incluant le passager qui participe au covoiturage.
				m_Nbr_Passagers += NbrPassagers;
			} else {
				m_Nbr_Passagers += 1;
			}

		} else {
			//unused
			//boolean boolUnPassager = false;
			
			
			// On cherche le passager qui désire se retirer.
			int i = m_List_Id_Passager.indexOf(IdPassager);
			// Index suivant celui du passager.
			int i2 = i + 1;
			// On vérifie si lors de la participation à un covoiturage ou lors
			// d'une création le nombre
			// de passagers est supérier à 1. Si oui on supprime les éléments
			// suivant l'utilisateur qui
			// désire ne plus particper au covoiturage. Donc, ceux qui sont
			// égales à zéro.
			if (m_List_Id_Passager.get(i2).equals("0")) {

				// À vérifier si la liste se refait d'elle-même****
				while (m_List_Id_Passager.get(i2).equals("0")) {
					// m_List_Id_Passager.remove(i2);

					m_List_Id_Passager.set(i2, null);

					i2++;
				}
				// Nombre de passagers diminue du nombre de passagers passé en
				// paramètre dans ce cas-ci.
				m_Nbr_Passagers -= NbrPassagers;

			} else {
				// À vérifier si la liste se refait d'elle-même****
				// m_List_Id_Passager.remove(i);

				m_List_Id_Passager.set(i, null);

				// Nombre de passager diminue de 1 dans l'autre cas.
				m_Nbr_Passagers -= NbrPassagers;
			}

			int i3 = 0;
			List<String> listIdPassagersTempo = new ArrayList<String>();

			for (int i4 = 0; i4 < m_List_Id_Passager.size(); i4++) {
				if (!m_List_Id_Passager.get(i4).equals(null)) {
					listIdPassagersTempo.add(i3, m_List_Id_Passager.get(i4));
					i3++;
				}
			}

			m_List_Id_Passager.clear();
			m_List_Id_Passager = listIdPassagersTempo;
		}
	}

	public int getNbrPassagers() {
		return m_Nbr_Passagers;
	}

	public void setNbrPassagers(int NbrPassagers) {
		this.m_Nbr_Passagers = NbrPassagers;
	}
	
	public String getDateAjout() {
		return m_DateAjout;
	}
	
	public void setDateAjout(String DateAjout) {
		this.m_DateAjout = DateAjout;
	}
}
