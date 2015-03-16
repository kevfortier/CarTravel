package com.app.cartravel;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;


public class AjoutParcoursActivity2 extends Activity{
	public static final int CONFIRMER_PARCOUR = 1;
	
	public EditText m_NbrPassagers;
	public EditText m_CapaciteMax;
	public EditText m_DistanceMax;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ajout_modif_parcour_2);
		
		final ActionBar actionBar = getActionBar();

		actionBar.setDisplayHomeAsUpEnabled(true);
		
		m_NbrPassagers = (EditText) findViewById(R.id.txtNbrPassagers);
		m_CapaciteMax = (EditText) findViewById(R.id.txtCapaciteMax);
		m_DistanceMax = (EditText) findViewById(R.id.txtDistanceMax);
		
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
			Intent i = new Intent(this, AjoutParcoursActivity3.class);
            this.startActivityForResult(i, CONFIRMER_PARCOUR);
        	return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
