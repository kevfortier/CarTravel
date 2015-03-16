package com.app.cartravel;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;

public class AjoutParcoursActivity3 extends Activity {
	public static final int CONFIRMER_PARCOUR = 1;

	private EditText m_NumCiviqueDep;
	private EditText m_RueDep;
	private EditText m_VilleDep;
	private EditText m_CodePostalDep;

	private EditText m_NumCiviqueArr;
	private EditText m_RueArr;
	private EditText m_VilleArr;
	private EditText m_CodePostalArr;

	private CheckBox m_AdrProfil;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ajout_modif_parcour_3);

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
			Intent i = new Intent(this, ParcourActivity.class);
			this.startActivityForResult(i, CONFIRMER_PARCOUR);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
