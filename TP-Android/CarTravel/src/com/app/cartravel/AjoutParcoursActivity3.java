package com.app.cartravel;

import java.net.URI;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.app.cartravel.classes.Parcours;
import com.app.cartravel.classes.Utilisateurs;
import com.app.cartravel.jsonparser.JsonParcours;
import com.app.cartravel.utilitaire.ParcourDataSource;
import com.app.cartravel.utilitaire.Util;
import com.app.cartravel.utilitaire.UtilisateurDataSource;

public class AjoutParcoursActivity3 extends Activity {

	private final String TAG = this.getClass().getSimpleName();
	private HttpClient m_ClientHttp = new DefaultHttpClient();
	
	private EditText m_NumCiviqueDep;
	private EditText m_RueDep;
	private EditText m_VilleDep;
	private EditText m_CodePostalDep;

	private EditText m_NumCiviqueArr;
	private EditText m_RueArr;
	private EditText m_VilleArr;
	private EditText m_CodePostalArr;

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

		Utilisateurs user = new Utilisateurs();
		UtilisateurDataSource userData = new UtilisateurDataSource(this);

		userData.open();
		user = userData.getConnectedUtilisateur();
		userData.close();

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

		m_NumCiviqueDep.setText(user.getNumCivique());
		m_RueDep.setText(user.getRue());
		m_VilleDep.setText(user.getVille());
		m_CodePostalDep.setText(user.getCodePostal());

		m_NumCiviqueArr = (EditText) findViewById(R.id.txtNumCiviqueArr);
		m_RueArr = (EditText) findViewById(R.id.txtRueArr);
		m_VilleArr = (EditText) findViewById(R.id.txtVilleArr);
		m_CodePostalArr = (EditText) findViewById(R.id.txtCodePostalArr);
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
					m_NumCiviqueDep.getText().toString(), m_RueDep.getText()
							.toString(), m_VilleDep.getText().toString(),
					m_CodePostalDep.getText().toString(), m_NumCiviqueArr
							.getText().toString(), m_RueArr.getText()
							.toString(), m_VilleArr.getText().toString(),
					m_CodePostalArr.getText().toString());
		} else {
			leParcours = new Parcours(util.getId(), m_Jour, m_Heure,
					m_Repetitif, m_NbrPassagers, m_NumCiviqueDep.getText()
							.toString(), m_RueDep.getText().toString(),
					m_VilleDep.getText().toString(), m_CodePostalDep.getText()
							.toString(), m_NumCiviqueArr.getText().toString(),
					m_RueArr.getText().toString(), m_VilleArr.getText()
							.toString(), m_CodePostalArr.getText().toString());
		}

		parcourData = new ParcourDataSource(this);
		parcourData.open();
		parcourData.insert(leParcours);
		parcourData.close();

		Toast.makeText(this, R.string.toast_ajout_parcours,
				Toast.LENGTH_SHORT).show();

		Intent parcourAct = new Intent(this, ParcourActivity.class);
		this.startActivity(parcourAct);
	}
	
	private class PutNewParcoursTask extends AsyncTask<Object, Void, Void> {
		Exception m_Exp;
		Parcours unParcours;
		
		private Context m_Context;
		
		public PutNewParcoursTask(Context p_Context) {
			this.m_Context = p_Context;
			
		}

		@Override
		protected void onPreExecute() {
			setProgressBarVisibility(true);
		}

		@Override
		protected Void doInBackground(Object... params) {
			
			unParcours = (Parcours)params[1];
			Utilisateurs connectedUser = (Utilisateurs)params[0];
			String temps = (String)params[2];
			
			try {
				
				//unParcours.setId(connectedUser.getId() + (int) temps);
				URI uri = new URI("http" + Util.WEB_SERVICE,
						Util.REST_UTILISATEUR + "/" + connectedUser.getCodePostal()
						+ Util.REST_PARCOURS + "/"
						+ unParcours.getId(), null, null);
				HttpPut putMethod = new HttpPut (uri);
				
				String jsonObj = JsonParcours.ToJSONObject(unParcours).toString();
				
				//Log.i(TAG, "JSON : " + jsonObj);
				
				putMethod.setEntity(new StringEntity(jsonObj));
				putMethod.addHeader("Content-Type", "application/json");
				
				String body = m_ClientHttp.execute(putMethod,
						new BasicResponseHandler());
				
				Log.i(TAG, "JSON : " + body);
				
			} catch (Exception e) {
				m_Exp = e;
			}
			// TODO Auto-generated method stub
			return null;
		}
	}
}
