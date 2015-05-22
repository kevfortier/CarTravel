package com.app.cartravel.utilitaire;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.app.cartravel.classes.Utilisateurs;

public class UtilisateurDataSource {
	private DatabaseHelper m_Helper;
	private SQLiteDatabase m_Db;

	public UtilisateurDataSource(Context p_Context) {
		m_Helper = new DatabaseHelper(p_Context);
	}

	public void open() {
		m_Db = this.m_Helper.getWritableDatabase();
	}

	public void close() {
		m_Db.close();
	}

	public int insert(Utilisateurs p_Utilisateur) {
		ContentValues row = utilisateurToContentValues(p_Utilisateur);
		int newId = (int) m_Db.insert(DatabaseHelper.TABLE_UTILISATEUR, null,
				row);
		p_Utilisateur.setId(newId);
		return newId;
	}

	public void update(Utilisateurs p_Utilisateur) {
		ContentValues row = utilisateurToContentValues(p_Utilisateur);
		m_Db.update(DatabaseHelper.TABLE_UTILISATEUR, row,
				DatabaseHelper.COL_ID_USER + "=" + p_Utilisateur.getId(), null);
	}

	public void delete(int p_Id) {
		m_Db.delete(DatabaseHelper.TABLE_UTILISATEUR,
				DatabaseHelper.COL_ID_USER + "=" + p_Id, null);
	}

	public void removeAll() {
		m_Db.delete(DatabaseHelper.TABLE_UTILISATEUR, null, null);
	}

	public Utilisateurs getUtilisateur(int p_Id) {
		Utilisateurs u = null;
		Cursor c = m_Db
				.query(DatabaseHelper.TABLE_UTILISATEUR, null,
						DatabaseHelper.COL_ID_USER + "=" + p_Id, null, null,
						null, null);
		c.moveToFirst();
		if (!c.isAfterLast()) {
			u = cursorToUtilisateur(c);
		}
		return u;
	}

	public Utilisateurs getUtilisateur(String p_Username) {
		Utilisateurs u = null;
		Cursor c = m_Db.query(DatabaseHelper.TABLE_UTILISATEUR, null,
				DatabaseHelper.COL_COURRIEL + "='" + p_Username + "'", null,
				null, null, null);
		c.moveToFirst();
		if (!c.isAfterLast()) {
			u = cursorToUtilisateur(c);
		}
		return u;
	}
	
	public Boolean getCourrielUser(String p_Courriel) {
		Boolean verifCourriel = false;
		Cursor c = m_Db.query(DatabaseHelper.TABLE_UTILISATEUR, null,
				DatabaseHelper.COL_COURRIEL + "='" + p_Courriel + "'", null,
				null, null, null);
		c.moveToFirst();
		if (!c.isAfterLast()) {
			verifCourriel = true;
		}
		return verifCourriel;
	}
	
	public Boolean getPseudoUser(String p_Pseudo) {
		Boolean verifPseudo = false;
		Cursor c = m_Db.query(DatabaseHelper.TABLE_UTILISATEUR, null,
				DatabaseHelper.COL_PSEUDO + "='" + p_Pseudo + "'", null,
				null, null, null);
		c.moveToFirst();
		if (!c.isAfterLast()) {
			verifPseudo = true;
		}
		return verifPseudo;
	}

	public Utilisateurs getConnectedUtilisateur() {
		Utilisateurs u = null;
		Cursor c = m_Db.query(DatabaseHelper.TABLE_UTILISATEUR, null,
				DatabaseHelper.COL_ESTCONNECTE + "=1", null, null, null, null);
		c.moveToFirst();
		if (!c.isAfterLast()) {
			u = cursorToUtilisateur(c);
		}
		return u;
	}

	public Utilisateurs getDernierConnecte() {
		Utilisateurs u = null;
		Cursor c = m_Db.query(DatabaseHelper.TABLE_UTILISATEUR, null,
				DatabaseHelper.COL_DERNIERCONNECTE + "=1", null, null, null,
				null);
		c.moveToFirst();
		if (!c.isAfterLast()) {
			u = cursorToUtilisateur(c);
		}
		return u;
	}

	/**
	 * Met à jour le dernier utilisateur connecté
	 * 
	 * @param nouveau
	 */
	public void modifierDernierConnecte(Utilisateurs nouveau) {
		Utilisateurs dernierConnecte = getDernierConnecte();
		if (dernierConnecte != null) {
			dernierConnecte.setDernierConnecte(0);
			update(dernierConnecte);
		}
		nouveau.setDernierConnecte(1);
		update(nouveau);

	}

	public List<Utilisateurs> getAllUtilisateurs() {
		List<Utilisateurs> utilisateurs = new ArrayList<Utilisateurs>();
		Cursor c = m_Db.query(DatabaseHelper.TABLE_UTILISATEUR, null, null,
				null, null, null, null);
		c.moveToFirst();
		while (!c.isAfterLast()) {
			Utilisateurs u = cursorToUtilisateur(c);
			utilisateurs.add(u);
			c.moveToNext();
		}
		return utilisateurs;
	}

	private ContentValues utilisateurToContentValues(Utilisateurs p_Utilisateur) {
		ContentValues row = new ContentValues();
		row.put(DatabaseHelper.COL_COURRIEL, p_Utilisateur.getCourriel());
		row.put(DatabaseHelper.COL_PSEUDO, p_Utilisateur.getPseudo());
		row.put(DatabaseHelper.COL_MOTDEPASSE, p_Utilisateur.getMotDePasse());
		row.put(DatabaseHelper.COL_NO_CIVIQUE, p_Utilisateur.getNumCivique());
		row.put(DatabaseHelper.COL_RUE, p_Utilisateur.getRue());
		row.put(DatabaseHelper.COL_VILLE, p_Utilisateur.getVille());
		row.put(DatabaseHelper.COL_CODE_POSTAL, p_Utilisateur.getCodePostal());
		row.put(DatabaseHelper.COL_NUM_TELEPHONE, p_Utilisateur.getNumTel());
		row.put(DatabaseHelper.COL_VOITURE, p_Utilisateur.getVoiture());
		row.put(DatabaseHelper.COL_RATING_COND, p_Utilisateur.getNoteCond());
		row.put(DatabaseHelper.COL_RATING_PASS, p_Utilisateur.getNotePass());
		row.put(DatabaseHelper.COL_ESTCONNECTE, p_Utilisateur.getEstConnecte());
		row.put(DatabaseHelper.COL_DERNIERCONNECTE,
				p_Utilisateur.getDernierConnecte());
		row.put(DatabaseHelper.COL_USER_DATE, p_Utilisateur.getDateAjoutUser());
		row.put(DatabaseHelper.COL_PROFIL_DATE,
				p_Utilisateur.getDateAjoutProfil());
		return row;
	}

	private Utilisateurs cursorToUtilisateur(Cursor c) {
		Utilisateurs r = new Utilisateurs(c.getInt(c
				.getColumnIndex(DatabaseHelper.COL_ID_USER)), c.getString(c
				.getColumnIndex(DatabaseHelper.COL_COURRIEL)), c.getString(c
				.getColumnIndex(DatabaseHelper.COL_PSEUDO)), c.getString(c
				.getColumnIndex(DatabaseHelper.COL_MOTDEPASSE)), c.getString(c
				.getColumnIndex(DatabaseHelper.COL_NO_CIVIQUE)), c.getString(c
				.getColumnIndex(DatabaseHelper.COL_RUE)), c.getString(c
				.getColumnIndex(DatabaseHelper.COL_VILLE)), c.getString(c
				.getColumnIndex(DatabaseHelper.COL_CODE_POSTAL)), c.getString(c
				.getColumnIndex(DatabaseHelper.COL_NUM_TELEPHONE)), c.getInt(c
				.getColumnIndex(DatabaseHelper.COL_VOITURE)), c.getFloat(c
				.getColumnIndex(DatabaseHelper.COL_RATING_COND)), c.getFloat(c
				.getColumnIndex(DatabaseHelper.COL_RATING_PASS)), c.getInt(c
				.getColumnIndex(DatabaseHelper.COL_ESTCONNECTE)), c.getInt(c
				.getColumnIndex(DatabaseHelper.COL_DERNIERCONNECTE)),
				c.getString(c.getColumnIndex(DatabaseHelper.COL_USER_DATE)),
				c.getString(c.getColumnIndex(DatabaseHelper.COL_PROFIL_DATE)));
		return r;
	}
}
