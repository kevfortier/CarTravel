package com.app.cartravel;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;

import com.app.cartravel.AjoutParcoursActivity1;
import com.app.cartravel.AjoutParcoursActivity2;

public class AjoutParcoursActivity3 extends Activity{
	public static final int CONFIRMER_PARCOUR = 1; 
	
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ajout_modif_parcour_3);
		
		final ActionBar actionBar = getActionBar();

		actionBar.setDisplayHomeAsUpEnabled(true);
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
			// app icon in action bar clicked; goto parent activity.
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
