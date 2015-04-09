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
import android.widget.Toast;

import com.app.cartravel.classes.Utilisateurs;
import com.app.cartravel.utilitaire.Util;
import com.app.cartravel.utilitaire.UtilisateurDataSource;

public class AjoutParcoursActivity1 extends Activity {

	private RadioButton m_Conducteur;
	private RadioButton m_Passager;

	private EditText m_Heure;
	private EditText m_Minutes;
	private EditText m_Jour;

	private Spinner m_Repetitif;

	public static final String EXTRA_CONDUCTEUR = "conducteur";
	public static final String EXTRA_HEURE = "heure";
	public static final String EXTRA_DATE = "date";
	public static final String EXTRA_REPETITIF = "repetitif";

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

		// Ajout des infos dans le Spinner
		m_Repetitif = (Spinner) this.findViewById(R.id.type_parcour_spinner);

		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.type_parcour_array,
				android.R.layout.simple_spinner_item);

		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		m_Repetitif.setAdapter(adapter);

		m_userDataSource = new UtilisateurDataSource(this);
		m_userDataSource.open();
		m_Utilisateur = m_userDataSource.getConnectedUtilisateur();
		m_userDataSource.close();

		if (m_Utilisateur.getVoiture() == 0) {
			m_Conducteur.setVisibility(View.INVISIBLE);
		}
		m_Passager.setChecked(true);
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
			confirmParcour();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void confirmParcour() {
		boolean cond;
		boolean repetitif;

		if (m_Conducteur.isChecked()) {
			cond = true;
		} else {
			cond = false;
		}
		if (m_Repetitif.getSelectedItem().toString() == "Ponctuel") {
			repetitif = false;
		} else {
			repetitif = true;
		}
		if (Util.verifHeure(m_Heure.getText().toString())) {
			if (Util.verifMinute(m_Minutes.getText().toString())) {
				if (Util.verifDate(m_Jour.getText().toString().trim())) {
					Intent i = new Intent(this, AjoutParcoursActivity2.class);
					i.putExtra(EXTRA_CONDUCTEUR, cond);
					i.putExtra(EXTRA_HEURE, m_Heure.getText().toString() + ":"
							+ m_Minutes.getText().toString());
					i.putExtra(EXTRA_DATE, m_Jour.getText().toString());
					i.putExtra(EXTRA_REPETITIF, repetitif);
					this.startActivity(i);
				} else {
					Toast.makeText(this, "Le champ date doit être remplis",
							Toast.LENGTH_SHORT).show();
				}
			} else {
				Toast.makeText(this, "Le champ minute doit être remplis",
						Toast.LENGTH_SHORT).show();
			}
		} else {
			Toast.makeText(this, "Le champ heure doit être remplis",
					Toast.LENGTH_SHORT).show();
		}
	}
}