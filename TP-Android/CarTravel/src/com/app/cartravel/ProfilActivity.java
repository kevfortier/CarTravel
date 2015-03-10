package com.app.cartravel;

import com.app.cartravel.classes.Utilisateurs;
import com.app.cartravel.utilitaire.UtilisateurDataSource;

import android.app.Activity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;

public class ProfilActivity extends Activity{

	private Utilisateurs m_Utilisateur;
	private UtilisateurDataSource m_DataSource;
	private Bundle m_extra;
	
	private EditText m_NumCivique;
	private EditText m_Rue;
	private EditText m_Ville;
	private EditText m_CodePostal;
	private CheckBox m_NoteCond;
	private CheckBox m_NotePass;
	
	protected void OnCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.profil_activity);
		
		m_NumCivique = (EditText) findViewById(R.id.);
	}
	
}
