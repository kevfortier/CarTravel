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
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.app.cartravel.classes.Utilisateurs;
import com.app.cartravel.jsonparser.JsonProfil;
import com.app.cartravel.utilitaire.Util;
import com.app.cartravel.utilitaire.UtilisateurDataSource;

@SuppressLint("SimpleDateFormat")
@SuppressWarnings("unused")
public class ProfilModifActivity extends Activity {

	private HttpClient m_ClientHttp = new DefaultHttpClient();
	private Utilisateurs mUtilisateur;
	private UtilisateurDataSource mDataSource;

	private String mNumCivDep;
	private String mRueDep;
	private String mVilleDep;
	private String mCodePostDep;
	private String mNumTelDep;

	private EditText mNumCivique;
	private EditText mRue;
	private EditText mVille;
	private EditText mCodePostal;
	private EditText mNumTel;
	private CheckBox mVoiture;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_profil_modif);
		setTitle(R.string.title_modif_profil);

		getActionBar().setDisplayHomeAsUpEnabled(true);

		mNumCivique = (EditText) findViewById(R.id.txt_num_civ);
		mRue = (EditText) findViewById(R.id.txt_rue);
		mVille = (EditText) findViewById(R.id.txt_ville);
		mCodePostal = (EditText) findViewById(R.id.txt_cod_post);
		mNumTel = (EditText) findViewById(R.id.txt_num_tel);
		mVoiture = (CheckBox) findViewById(R.id.chck_voiture);

		setTitle(R.string.title_modif_profil);

		mDataSource = new UtilisateurDataSource(this);
		mDataSource.open();
		mUtilisateur = mDataSource.getConnectedUtilisateur();
		mDataSource.close();

		if (mUtilisateur != null) {
			AfficherInfoCompte(mNumCivique, mRue, mVille, mCodePostal, mNumTel,
					mVoiture);
			mNumCivDep = mNumCivique.getText().toString().trim();
			mRueDep = mRue.getText().toString().trim();
			mVilleDep = mVille.getText().toString().trim();
			mCodePostDep = mCodePostal.getText().toString().trim();
			mNumTelDep = mNumTel.getText().toString().trim();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.menu_profile_modif, menu);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			return true;
		case R.id.action_confirmer_profil:
			ModifierProfil();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void ModifierProfil() {
		String strNumCivique = mNumCivique.getText().toString().trim();
		String strRue = mRue.getText().toString().trim();
		String strVille = mVille.getText().toString().trim();
		String strCodePostal = mCodePostal.getText().toString().trim();
		String strNumTel = mNumTel.getText().toString().trim();
		Boolean boolVoiture = mVoiture.isChecked();

		Intent i = new Intent();

		if (Util.ValiderString(new String[] { strNumCivique, strRue, strVille,
				strCodePostal, strNumTel })) {
			if (!mNumCivDep.matches(strNumCivique) || !mRueDep.matches(strRue)
					|| !mVilleDep.matches(strVille)
					|| !mCodePostDep.matches(strCodePostal)
					|| !mNumTelDep.matches(strNumTel)
					|| !mVoiture.isChecked() != boolVoiture) {

				if (Util.verifChaineCharac(strRue)) {

					if (Util.verifChaineCharac(strVille)) {

						if (Util.verifCodePostal(strCodePostal)) {

							if (Util.verifNumTel(strNumTel)) {
								UtilisateurDataSource dataSource = new UtilisateurDataSource(
										this);

								Calendar c = Calendar.getInstance();
								SimpleDateFormat sdf = new SimpleDateFormat(
										"dd:MMMM:yyyy HH:mm:ss a");
								String strDate = sdf.format(c.getTime());

								dataSource.open();
								mUtilisateur.setNumCivique(strNumCivique);
								mUtilisateur.setRue(strRue);
								mUtilisateur.setVille(strVille);
								mUtilisateur.setCodePostal(strCodePostal);
								mUtilisateur.setNumTel(strNumTel);
								mUtilisateur.setDateAjoutProfil(strDate);

								if (mVoiture.isChecked()) {
									mUtilisateur.setVoiture(1);
								} else {
									mUtilisateur.setVoiture(0);
								}

								dataSource.update(mUtilisateur);
								dataSource.close();

								new PutProfilTask(this).execute(mUtilisateur);
								this.setResult(RESULT_OK, i);
								this.finish();
							} else {
								Toast.makeText(this,
										R.string.toast_num_tel_confirm,
										Toast.LENGTH_SHORT).show();
							}

						} else {
							Toast.makeText(this,
									R.string.toast_code_postal_confirm,
									Toast.LENGTH_SHORT).show();
						}

					} else {
						Toast.makeText(this, R.string.toast_ville_invalide,
								Toast.LENGTH_SHORT).show();
					}
				} else {
					Toast.makeText(this, R.string.toast_rue_invalide,
							Toast.LENGTH_SHORT).show();
				}
			} else {
				Toast.makeText(this, R.string.toast_annul_modif,
						Toast.LENGTH_SHORT).show();
			}

		} else {
			if (!Util.ValiderString(new String[] { strNumCivique })) {
				Toast.makeText(this, R.string.toast_num_civique_vide,
						Toast.LENGTH_SHORT).show();
			}
			if (!Util.ValiderString(new String[] { strRue })) {
				Toast.makeText(this, R.string.toast_rue_vide,
						Toast.LENGTH_SHORT).show();
			}
			if (!Util.ValiderString(new String[] { strVille })) {
				Toast.makeText(this, R.string.toast_ville_vide,
						Toast.LENGTH_SHORT).show();
			}
			if (!Util.ValiderString(new String[] { strCodePostal })) {
				Toast.makeText(this, R.string.toast_code_postal_vide,
						Toast.LENGTH_SHORT).show();
			}
		}
	}

	public void AfficherInfoCompte(EditText mNumCivique, EditText mRue,
			EditText mVille, EditText mCodePostal, EditText mNumTel,
			CheckBox mVoiture) {
		mNumCivique.setText(mUtilisateur.getNumCivique());
		mRue.setText(mUtilisateur.getRue());
		mVille.setText(mUtilisateur.getVille());
		mCodePostal.setText(mUtilisateur.getCodePostal());
		mNumTel.setText(mUtilisateur.getNumTel());
		if (mUtilisateur.getVoiture() == 1) {
			mVoiture.setChecked(true);
		}
	}

	private class PutProfilTask extends AsyncTask<Object, Void, Void> {
		private Exception m_Exp;
		private Utilisateurs m_utilisateur_connect;
		private Context m_Context;

		public PutProfilTask(Context p_Context) {
			this.m_Context = p_Context;
		}

		@Override
		protected Void doInBackground(Object... params) {

			m_utilisateur_connect = (Utilisateurs) params[0];

			try {
				URI uri = new URI("http", Util.WEB_SERVICE,
						Util.REST_UTILISATEUR + "/"
								+ m_utilisateur_connect.getCourriel()
								+ Util.REST_PROFIL, null, null);
				HttpPut putMethod = new HttpPut(uri);

				String jsonObj = JsonProfil.ToJSONObject(m_utilisateur_connect)
						.toString();

				putMethod.setEntity(new StringEntity(jsonObj));
				putMethod.addHeader("Content-Type", "application/json");

				String body = m_ClientHttp.execute(putMethod,
						new BasicResponseHandler());
			} catch (Exception e) {
				m_Exp = e;
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			// S'il n'y a pas de message d'erreur
			if (m_Exp == null) {
				UtilisateurDataSource dataSource = new UtilisateurDataSource(
						m_Context);
				dataSource.open();
				// TODO Creation du profil.

				dataSource.close();
			} else {
				Toast.makeText(
						m_Context,
						m_Context.getResources().getText(
								R.string.toast_erreur_creation_profil),
						Toast.LENGTH_SHORT).show();
			}
		}
	}
}
