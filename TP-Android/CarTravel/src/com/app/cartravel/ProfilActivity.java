package com.app.cartravel;

import com.app.cartravel.classes.Utilisateurs;
import com.app.cartravel.utilitaire.UtilisateurDataSource;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RatingBar;

public class ProfilActivity extends Activity{

	private Utilisateurs m_Utilisateur;
	private UtilisateurDataSource m_DataSource;
	private Bundle m_extra;
	
	private EditText m_NumCivique;
	private EditText m_Rue;
	private EditText m_Ville;
	private EditText m_CodePostal;
	private CheckBox m_Voiture;
	private RatingBar m_NoteCond;
	private RatingBar m_NotePass;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.acitivity_profile);
		
		m_NumCivique = (EditText) findViewById(R.id.txt_num_civ);
		m_Rue = (EditText) findViewById(R.id.txt_rue);
		m_Ville= (EditText) findViewById(R.id.txt_ville);
		m_CodePostal = (EditText) findViewById(R.id.txt_cod_post);
		m_Voiture = (CheckBox) findViewById(R.id.chck_voiture);
		m_NoteCond = (RatingBar) findViewById(R.id.rating_cond);
		m_NotePass = (RatingBar) findViewById(R.id.rating_pass);
		m_extra = this.getIntent().getExtras();
		
		// À voir avec Kevin
		if(m_extra != null)
		{
			m_DataSource = new UtilisateurDataSource(this);
			m_DataSource.open();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		return super.onCreateOptionsMenu(menu);
	}

	
}
