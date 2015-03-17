package com.app.cartravel;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AjoutParcoursActivity2 extends Activity {

	private EditText m_NbrPassagers;
	private EditText m_CapaciteMax;
	private EditText m_DistanceMax;

	private boolean m_Cond;
	private boolean m_Repetitif;
	private String m_Jour;
	private String m_Heure;

	public static final String EXTRA_CONDUCTEUR = "conducteur";
	public static final String EXTRA_HEURE = "heure";
	public static final String EXTRA_DATE = "date";
	public static final String EXTRA_REPETITIF = "repetitif";

	public static final String EXTRA_NBRPASSAGERS = "nbrPassagers";
	public static final String EXTRA_CAPACITEMAX = "capaciteMax";
	public static final String EXTRA_DISTANCEMAX = "distanceMax";

	private Bundle extras;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		extras = this.getIntent().getExtras();
		m_Cond = extras.getBoolean(AjoutParcoursActivity1.EXTRA_CONDUCTEUR);
		m_Repetitif = extras.getBoolean(AjoutParcoursActivity1.EXTRA_REPETITIF);
		m_Jour = extras.getString(AjoutParcoursActivity1.EXTRA_DATE);
		m_Heure = extras.getString(AjoutParcoursActivity1.EXTRA_HEURE);

		setContentView(R.layout.activity_ajout_modif_parcour_2);

		final ActionBar actionBar = getActionBar();

		actionBar.setDisplayHomeAsUpEnabled(true);

		m_NbrPassagers = (EditText) findViewById(R.id.txtNbrPassagers);
		m_CapaciteMax = (EditText) findViewById(R.id.txtCapaciteMax);
		m_DistanceMax = (EditText) findViewById(R.id.txtDistanceMax);

		if (m_Cond) {
			findViewById(R.id.lblNbrPassagers).setVisibility(View.INVISIBLE);
			m_NbrPassagers.setVisibility(View.INVISIBLE);
		} else {
			findViewById(R.id.lblCapaciteMax).setVisibility(View.INVISIBLE);
			findViewById(R.id.lblDistanceMax).setVisibility(View.INVISIBLE);
			m_CapaciteMax.setVisibility(View.INVISIBLE);
			m_DistanceMax.setVisibility(View.INVISIBLE);
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
			confirmParcour();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void confirmParcour() {
		if (m_Cond) {
			if (! m_CapaciteMax.toString().trim().isEmpty()) {
				if (! m_DistanceMax.toString().trim().isEmpty()) {
					Intent i = new Intent(this, AjoutParcoursActivity3.class);
					i.putExtra(EXTRA_CONDUCTEUR, m_Cond);
					i.putExtra(EXTRA_HEURE, m_Heure);
					i.putExtra(EXTRA_DATE, m_Jour);
					i.putExtra(EXTRA_REPETITIF, m_Repetitif);
					i.putExtra(EXTRA_CAPACITEMAX, Integer.parseInt(m_CapaciteMax.getText().toString()));
					i.putExtra(EXTRA_DISTANCEMAX, Integer.parseInt(m_DistanceMax.getText().toString()));
					this.startActivity(i);
				}else{
					Toast.makeText(this, "Le champ distance max. doit être remplis",
							Toast.LENGTH_SHORT).show();
				}
			}else{
				Toast.makeText(this, "Le champ capacite max. doit être remplis",
						Toast.LENGTH_SHORT).show();
			}
		} else {
			if (!m_NbrPassagers.toString().trim().isEmpty()) {
				Intent i = new Intent(this, AjoutParcoursActivity3.class);
				i.putExtra(EXTRA_CONDUCTEUR, m_Cond);
				i.putExtra(EXTRA_HEURE, m_Heure);
				i.putExtra(EXTRA_DATE, m_Jour);
				i.putExtra(EXTRA_REPETITIF, m_Repetitif);
				i.putExtra(EXTRA_NBRPASSAGERS, Integer.parseInt(m_NbrPassagers.getText().toString()));
				this.startActivity(i);
			} else {
				Toast.makeText(this, "Le champ Nbr. Passagers doit être remplis",
						Toast.LENGTH_SHORT).show();
			}
		}
	}
}
