package com.app.cartravel;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import android.annotation.SuppressLint;
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
import com.app.cartravel.classes.ParcoursPassager;
import com.app.cartravel.classes.Utilisateurs;
import com.app.cartravel.jsonparser.JsonParcours;
import com.app.cartravel.jsonparser.JsonParcoursPassager;
import com.app.cartravel.utilitaire.ParcourDataSource;
import com.app.cartravel.utilitaire.ParcoursPassagerDataSource;
import com.app.cartravel.utilitaire.Util;
import com.app.cartravel.utilitaire.UtilisateurDataSource;

@SuppressLint("SimpleDateFormat") 
@SuppressWarnings("unused")
public class AjoutParcoursActivity3 extends Activity {

	private final String TAG = this.getClass().getSimpleName();
	private HttpClient m_ClientHttp = new DefaultHttpClient();
	private HttpClient m_ClientHttp2 = new DefaultHttpClient();

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
	private float m_DistanceMax;

	private Bundle extras;

	UtilisateurDataSource utilData;
	ParcourDataSource parcourData;
	ParcoursPassagerDataSource parcPassData;

	Utilisateurs util;
	Parcours leParcours;
	ParcoursPassager leParcoursPassager;

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
					.getFloat(AjoutParcoursActivity2.EXTRA_DISTANCEMAX);
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

		String strNumCivDep = m_NumCiviqueDep.getText().toString().trim();
		String strRueDep = m_RueDep.getText().toString().trim();
		String strVilleDep = m_VilleDep.getText().toString().trim();
		String strCodePostalDep = m_CodePostalDep.getText().toString().trim();

		String strNumCivArr = m_NumCiviqueArr.getText().toString().trim();
		String strRueArr = m_RueArr.getText().toString().trim();
		String strVilleArr = m_VilleArr.getText().toString().trim();
		String strCodePostalArr = m_CodePostalArr.getText().toString().trim();

		if (Util.ValiderString(new String[] { strNumCivDep, strRueDep,
				strVilleDep, strCodePostalDep, strNumCivArr, strRueArr,
				strVilleArr, strCodePostalArr })) {
			if (Util.verifChaineCharac(strRueDep)) {
				if (Util.verifChaineCharac(strVilleDep)) {
					if (Util.verifCodePostal(strCodePostalDep)) {
						if (Util.verifChaineCharac(strRueArr)) {
							if (Util.verifChaineCharac(strVilleArr)) {
								if (Util.verifCodePostal(strCodePostalArr)) {

									long time = System.currentTimeMillis();
									Calendar c = Calendar.getInstance();
									SimpleDateFormat sdf = new SimpleDateFormat(
											"dd:MMMM:yyyy HH:mm:ss a");
									String strDate = sdf.format(c.getTime());

									String idParcours = util.getPseudo()
											+ String.valueOf(time);

									if (m_Cond) {
										leParcours = new Parcours(idParcours,
												util.getId(), util.getId(),
												m_Jour, m_Heure, m_Repetitif,
												m_CapaciteMax, 0,
												m_DistanceMax, strNumCivDep,
												strRueDep, strVilleDep,
												strCodePostalDep, strNumCivArr,
												strRueArr, strVilleArr,
												strCodePostalArr, strDate);
									} else {
										leParcours = new Parcours(idParcours,
												util.getId(), m_Jour, m_Heure,
												m_Repetitif, strNumCivDep,
												strRueDep, strVilleDep,
												strCodePostalDep, strNumCivArr,
												strRueArr, strVilleArr,
												strCodePostalArr, strDate);
										leParcoursPassager = new ParcoursPassager(
												idParcours, util.getCourriel(),
												m_NbrPassagers, strDate);
									}

									parcourData = new ParcourDataSource(this);
									parcourData.open();
									parcourData.insert(leParcours);
									parcourData.close();

									new PutNewParcoursTask(this).execute(
											leParcours, idParcours);

									if (leParcoursPassager != null) {
										parcPassData = new ParcoursPassagerDataSource(
												this);
										parcPassData.open();
										parcPassData.insert(leParcoursPassager);
										parcPassData.close();

										new PutNewParcoursPassagersTask(this)
												.execute(leParcoursPassager,
														idParcours);
									}

									Toast.makeText(this,
											R.string.toast_ajout_parcours,
											Toast.LENGTH_SHORT).show();

									Intent parcourAct = new Intent(this,
											ParcourActivity.class);
									this.startActivity(parcourAct);

								} else {
									Toast.makeText(
											this,
											R.string.toast_code_postal_arr_invalide,
											Toast.LENGTH_SHORT).show();
								}
							} else {
								Toast.makeText(this,
										R.string.toast_ville_arr_invalide,
										Toast.LENGTH_SHORT).show();
							}
						} else {
							Toast.makeText(this,
									R.string.toast_rue_arr_invalide,
									Toast.LENGTH_SHORT).show();
						}
					} else {
						Toast.makeText(this,
								R.string.toast_code_postal_dep_invalide,
								Toast.LENGTH_SHORT).show();
					}
				} else {
					Toast.makeText(this, R.string.toast_ville_dep_invalide,
							Toast.LENGTH_SHORT).show();
				}

			} else {
				Toast.makeText(this, R.string.toast_rue_dep_invalide,
						Toast.LENGTH_SHORT).show();
			}

		} else {
			if (!Util.ValiderString(new String[] { strNumCivDep })) {
				Toast.makeText(this, R.string.toast_num_civ_dep_vide,
						Toast.LENGTH_SHORT).show();
			}
			if (!Util.ValiderString(new String[] { strRueDep })) {
				Toast.makeText(this, R.string.toast_rue_dep_vide,
						Toast.LENGTH_SHORT).show();
			}
			if (!Util.ValiderString(new String[] { strVilleDep })) {
				Toast.makeText(this, R.string.toast_ville_dep_vide,
						Toast.LENGTH_SHORT).show();
			}
			if (!Util.ValiderString(new String[] { strCodePostalDep })) {
				Toast.makeText(this, R.string.toast_code_postal_dep_vide,
						Toast.LENGTH_SHORT).show();
			}
			if (!Util.ValiderString(new String[] { strNumCivArr })) {
				Toast.makeText(this, R.string.toast_num_civ_arr_vide,
						Toast.LENGTH_SHORT).show();
			}
			if (!Util.ValiderString(new String[] { strRueArr })) {
				Toast.makeText(this, R.string.toast_rue_arr_vide,
						Toast.LENGTH_SHORT).show();
			}
			if (!Util.ValiderString(new String[] { strVilleArr })) {
				Toast.makeText(this, R.string.toast_ville_arr_vide,
						Toast.LENGTH_SHORT).show();
			}
			if (!Util.ValiderString(new String[] { strCodePostalArr })) {
				Toast.makeText(this, R.string.toast_code_postal_arr_vide,
						Toast.LENGTH_SHORT).show();
			}
		}
	}

	private class PutNewParcoursTask extends AsyncTask<Object, Void, Void> {
		private Exception m_Exp;
		private Parcours unParcours;
		private Context m_Context;
		private String idParcours;

		public PutNewParcoursTask(Context p_Context) {
			this.m_Context = p_Context;

		}

		@Override
		protected void onPreExecute() {
			setProgressBarIndeterminateVisibility(true);
		}

		@Override
		protected Void doInBackground(Object... params) {

			unParcours = (Parcours) params[0];
			idParcours = (String) params[1];

			try {
				URI uri = new URI("http", Util.WEB_SERVICE, Util.REST_PARCOURS
						+ "/" + idParcours, null, null);
				HttpPut putMethod = new HttpPut(uri);

				unParcours.setId(idParcours);

				String jsonObj = JsonParcours.ToJSONObject(unParcours)
						.toString();

				Log.i(TAG, "JSON : " + jsonObj);

				putMethod.setEntity(new StringEntity(jsonObj));
				putMethod.addHeader("Content-Type", "application/json");

				// body = null???
				String body = m_ClientHttp.execute(putMethod,
						new BasicResponseHandler());
				Log.i(TAG, "Recu : " + body);

			} catch (Exception e) {
				m_Exp = e;
			}
			return null;
		}
	}

	private class PutNewParcoursPassagersTask extends
			AsyncTask<Object, Void, Void> {
		private Exception m_Exp;
		private ParcoursPassager unParcoursPassager;
		private Context m_Context;
		private String idParcours;

		public PutNewParcoursPassagersTask(Context p_Context) {
			this.m_Context = p_Context;

		}

		@Override
		protected void onPreExecute() {
			setProgressBarIndeterminateVisibility(true);
		}

		@Override
		protected Void doInBackground(Object... params) {

			unParcoursPassager = (ParcoursPassager) params[0];
			idParcours = (String) params[1];

			try {
				URI uri = new URI("http", Util.WEB_SERVICE,
						Util.REST_PARCOURS_PASSAGER + "/" + idParcours, null,
						null);
				HttpPut putMethod = new HttpPut(uri);

				unParcoursPassager.SetIdParcoursPassager(idParcours);

				String jsonObj = JsonParcoursPassager.ToJSONObject(
						unParcoursPassager).toString();

				Log.i(TAG, "JSON : " + jsonObj);

				putMethod.setEntity(new StringEntity(jsonObj));
				putMethod.addHeader("Content-Type", "application/json");

				String body = m_ClientHttp2.execute(putMethod,
						new BasicResponseHandler());
				Log.i(TAG, "Recu : " + body);

			} catch (Exception e) {
				m_Exp = e;
			}
			return null;
		}
	}
}