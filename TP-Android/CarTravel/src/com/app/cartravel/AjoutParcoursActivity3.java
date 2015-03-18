package com.app.cartravel;

import com.app.cartravel.classes.Parcours;
import com.app.cartravel.classes.Utilisateurs;
import com.app.cartravel.utilitaire.ParcourDataSource;
import com.app.cartravel.utilitaire.UtilisateurDataSource;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class AjoutParcoursActivity3 extends Activity {

	private EditText m_NumCiviqueDep;
	private EditText m_RueDep;
	private EditText m_VilleDep;
	private EditText m_CodePostalDep;

	private EditText m_NumCiviqueArr;
	private EditText m_RueArr;
	private EditText m_VilleArr;
	private EditText m_CodePostalArr;

	private CheckBox m_AdrProfil;

	private boolean m_Cond;
	private boolean m_Repetitif;
	private String m_Jour;
	private String m_Heure;

	private int m_NbrPassagers;
	private int m_CapaciteMax;
	private int m_DistanceMax;

	private Bundle extras;

	UtilisateurDataSource utilData;
	ParcourDataSource parcourData;
	
	Utilisateurs util;
	Parcours leParcours;
	

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		extras = this.getIntent().getExtras();
		m_Cond = extras.getBoolean(AjoutParcoursActivity2.EXTRA_CONDUCTEUR);
		m_Repetitif = extras.getBoolean(AjoutParcoursActivity2.EXTRA_REPETITIF);
		m_Jour = extras.getString(AjoutParcoursActivity2.EXTRA_DATE);
		m_Heure = extras.getString(AjoutParcoursActivity2.EXTRA_HEURE);
		setContentView(R.layout.activity_ajout_modif_parcour_3);

		if (m_Cond) {
			m_CapaciteMax = extras
					.getInt(AjoutParcoursActivity2.EXTRA_CAPACITEMAX);
			m_DistanceMax = extras
					.getInt(AjoutParcoursActivity2.EXTRA_DISTANCEMAX);
		} else {
			m_NbrPassagers = extras
					.getInt(AjoutParcoursActivity2.EXTRA_NBRPASSAGERS);
		}

		final ActionBar actionBar = getActionBar();

		actionBar.setDisplayHomeAsUpEnabled(true);

		m_NumCiviqueDep = (EditText) findViewById(R.id.txtNumCiviqueDepart);
		m_RueDep = (EditText) findViewById(R.id.txtRueDepart);
		m_VilleDep = (EditText) findViewById(R.id.txtVilleDepart);
		m_CodePostalDep = (EditText) findViewById(R.id.txtCodePostalDepart);

		m_NumCiviqueArr = (EditText) findViewById(R.id.txtNumCiviqueArr);
		m_RueArr = (EditText) findViewById(R.id.txtRueArr);
		m_VilleArr = (EditText) findViewById(R.id.txtVilleArr);
		m_CodePostalArr = (EditText) findViewById(R.id.txtCodePostalArr);

		m_AdrProfil = (CheckBox) findViewById(R.id.chkAddrProfil);
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
		utilData = new UtilisateurDataSource(this);
		utilData.open();
		util = utilData.getConnectedUtilisateur();
		utilData.close();

		if (m_Cond) {
			leParcours = new Parcours(util.getId(), util.getId(), m_Jour,
					m_Heure, m_Repetitif, m_CapaciteMax, m_DistanceMax,
					m_NumCiviqueDep.toString(), m_RueDep.toString(),
					m_VilleDep.toString(), m_CodePostalDep.toString(),
					m_NumCiviqueArr.toString(), m_RueArr.toString(),
					m_VilleArr.toString(), m_CodePostalArr.toString());
		} else {
			leParcours = new Parcours(util.getId(), m_Jour, m_Heure,
					m_Repetitif, m_NumCiviqueDep.toString(),
					m_RueDep.toString(), m_VilleDep.toString(),
					m_CodePostalDep.toString(), m_NumCiviqueArr.toString(),
					m_RueArr.toString(), m_VilleArr.toString(),
					m_CodePostalArr.toString());
		}
		
		parcourData = new ParcourDataSource(this);
		parcourData.open();
		parcourData.insert(leParcours);
		parcourData.close();
		
		Toast.makeText(this, "Le parcour a �t� ajouter avec succ�s",
				Toast.LENGTH_SHORT).show();
		
		Intent parcourAct = new Intent(this, ParcourActivity.class);
		this.startActivity(parcourAct);
	}
}
