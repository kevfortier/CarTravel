package com.app.cartravel;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

import com.app.cartravel.classes.Utilisateurs;
import com.app.cartravel.utilitaire.UtilisateurDataSource;

public class AjoutParcoursActivity1 extends Activity{
	public static final int CONFIRMER_PARCOUR = 1; 
	
	public RadioButton m_Conducteur;
	public RadioButton m_Passager;
	
	public EditText m_Heure;
	public EditText m_Minutes;
	public EditText m_Jour;
	
	public Spinner m_Repetitif;
	
	private Utilisateurs m_Utilisateur;
	private UtilisateurDataSource m_userDataSource;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ajout_modif_parcour_1);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		m_Conducteur = (RadioButton) this.findViewById(R.id.rdbConducteur);
		m_Passager = (RadioButton) this.findViewById(R.id.rdbPassager);
		
		m_Heure = (EditText) this.findViewById(R.id.txtHeure);
		m_Minutes = (EditText) this.findViewById(R.id.txtMinute);
		
		m_Jour = (EditText) this.findViewById(R.id.txtDate);
		
		//Ajout des infos dans le Spinner
		m_Repetitif = (Spinner) this.findViewById(R.id.type_parcour_spinner);
		
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
		        R.array.type_parcour_array, android.R.layout.simple_spinner_item);
		
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		m_Repetitif.setAdapter(adapter);
		
		m_userDataSource = new UtilisateurDataSource(this);
		m_userDataSource.open();
		m_Utilisateur = m_userDataSource.getConnectedUtilisateur();
		m_userDataSource.close();
		
		
		if (! m_Utilisateur.getVoiture()) {
			m_Conducteur.setVisibility(View.INVISIBLE);
			m_Passager.setChecked(true);
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_modif_parcours, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			return true;
		case R.id.action_confirmer_parcour:
			Intent i = new Intent(this, AjoutParcoursActivity2.class);
            this.startActivityForResult(i, CONFIRMER_PARCOUR);
        	return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}