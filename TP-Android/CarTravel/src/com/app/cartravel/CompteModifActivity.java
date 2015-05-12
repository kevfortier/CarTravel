package com.app.cartravel;

import java.net.URI;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.app.cartravel.classes.Utilisateurs;
import com.app.cartravel.jsonparser.JsonUtilisateur;
import com.app.cartravel.utilitaire.Util;
import com.app.cartravel.utilitaire.UtilisateurDataSource;

public class CompteModifActivity extends Activity {

	private String mCourrielDep;
	private String mCourrielVerifDep;
	private String mPseudoDep;
	private String mMDPDep;
	private String mMDPVerifDep;

	private Utilisateurs mUtilisateur;
	private EditText mCourriel;
	private EditText mCourrielVerif;
	private EditText mPseudo;
	private EditText mMDP;
	private EditText mMDPVerif;
	private UtilisateurDataSource mDataSource;
	
	private HttpClient m_ClientHttp = new DefaultHttpClient();

	// private Bundle m_extra;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_compte_modif);
		setTitle(R.string.title_modif_compte);

		getActionBar().setDisplayHomeAsUpEnabled(true);

		mCourriel = (EditText) findViewById(R.id.txt_username);
		mCourrielVerif = (EditText) findViewById(R.id.txt_confirmer_username);
		mPseudo = (EditText) findViewById(R.id.txt_pseudo);
		mMDP = (EditText) findViewById(R.id.txt_password);

		mMDPVerif = (EditText) findViewById(R.id.txt_confirmer_password);

		mDataSource = new UtilisateurDataSource(this);
		mDataSource.open();
		mUtilisateur = mDataSource.getConnectedUtilisateur();
		mDataSource.close();

		if (mUtilisateur != null) {
			AfficherInfoCompte(mCourriel, mPseudo, mMDP);
			mCourrielDep = mCourriel.getText().toString().trim();
			mCourrielVerifDep = mCourrielVerif.getText().toString().trim();
			mPseudoDep = mPseudo.getText().toString().trim();
			mMDPDep = mMDP.getText().toString().trim();
			mMDPVerifDep = mMDPVerif.getText().toString().trim();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.menu_compte_modif, menu);

		return true;
	}

	// Lorsque l'utilisateur confirme les modifications de son compte.
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			return true;
		case R.id.action_confirmer_compte:
			// Faire afficher l'activit� de ton bouton de menu
			ModifierInfoCompte();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void ModifierInfoCompte() {
		Boolean modifCourriel = false;
		Boolean modifMDP = false;
		Boolean modifPseudo = false;
		Boolean erreurRencontree = false;

		String strCourriel = mCourriel.getText().toString().trim();
		String strCourrielConfirmation = mCourrielVerif.getText().toString()
				.trim();
		String strPseudo = mPseudo.getText().toString().trim();
		String strMotDePasse = mMDP.getText().toString().trim();
		String strMotDePasseConfirmation = mMDPVerif.getText().toString()
				.trim();
		Intent i = new Intent();

		if (Util.ValiderString(new String[] { strCourriel, strPseudo,
				strMotDePasse })) {

			if (!mCourrielDep.matches(strCourriel)) {

				if (Util.isCourriel(strCourriel)) {
					if (!strCourrielConfirmation.isEmpty()) {
						if (strCourriel.equals(strCourrielConfirmation)) {
							modifCourriel = true;
						} else {
							Toast.makeText(this,
									R.string.toast_courriel_identique,
									Toast.LENGTH_SHORT).show();
							erreurRencontree = true;
						}
					} else {
						Toast.makeText(this,
								R.string.toast_courriel_confirm_vide,
								Toast.LENGTH_SHORT).show();
						erreurRencontree = true;
					}
				} else {
					Toast.makeText(this, R.string.toast_courriel_invalide,
							Toast.LENGTH_SHORT).show();
					erreurRencontree = true;
				}
			} else {
				if (!mCourrielVerifDep.matches(strCourrielConfirmation)) {
					Toast.makeText(this, R.string.toast_courriel_identique,
							Toast.LENGTH_SHORT).show();
					erreurRencontree = true;
				}
			}

			if (!mMDPDep.matches(strMotDePasse)) {
				if (!strMotDePasseConfirmation.isEmpty()) {
					if (strMotDePasse.equals(strMotDePasseConfirmation)) {
						modifMDP = true;
					} else {
						Toast.makeText(this, R.string.toast_mdp_identique,
								Toast.LENGTH_SHORT).show();
						erreurRencontree = true;
					}
				} else {
					Toast.makeText(this, R.string.toast_mdp_confirm_vide,
							Toast.LENGTH_SHORT).show();
					erreurRencontree = true;
				}

			} else {
				if (!mMDPVerifDep.matches(strMotDePasseConfirmation)) {
					Toast.makeText(this, R.string.toast_mdp_identique,
							Toast.LENGTH_SHORT).show();
					erreurRencontree = true;
				}
			}

			UtilisateurDataSource dataSource = new UtilisateurDataSource(this);
			dataSource.open();

			if (modifCourriel) {
				mUtilisateur.setCourriel(strCourriel);
			}

			if (modifMDP) {
				mUtilisateur.setMotDePasse(strMotDePasse);
			}

			if (!mPseudoDep.matches(strPseudo)) {
				mUtilisateur.setPseudo(strPseudo);
				modifPseudo = true;
			}

			dataSource.update(mUtilisateur);
			dataSource.close();

			if (!erreurRencontree || modifPseudo) {
				new ModifierCompteTask(this).execute(strCourriel, strPseudo, strMotDePasse);
				this.setResult(RESULT_OK, i);
				this.finish();
			} else {
				Toast.makeText(this, R.string.toast_annul_modif,
						Toast.LENGTH_SHORT).show();
			}

		} else {
			if (!Util.ValiderString(new String[] { strCourriel })) {
				Toast.makeText(this, R.string.toast_courriel_vide,
						Toast.LENGTH_SHORT).show();
			}
			if (!Util.ValiderString(new String[] { strPseudo })) {
				Toast.makeText(this, R.string.toast_pseudo_vide,
						Toast.LENGTH_SHORT).show();
			}
			if (!Util.ValiderString(new String[] { strMotDePasse })) {
				Toast.makeText(this, R.string.toast_mdp_vide,
						Toast.LENGTH_SHORT).show();
			}
		}
	}

	// M�thode permettant d'afficher les info. du compte d'un utilisateur
	public void AfficherInfoCompte(EditText mCourriel, EditText mPseudo,
			EditText mMDP) {
		mCourriel.setText(mUtilisateur.getCourriel());
		mPseudo.setText(mUtilisateur.getPseudo());
		mMDP.setText(mUtilisateur.getMotDePasse());
	}
	
	private class ModifierCompteTask extends AsyncTask<String, Void, Void> {
		private Exception m_Exp;
		private Context m_Context;
		private Utilisateurs utilisateur;
		
		public ModifierCompteTask(Context p_Context) {
			this.m_Context = p_Context;
		}
		
		@Override
		protected Void doInBackground(String... params) {
			try {
				utilisateur = new Utilisateurs(params[0], params[1],
						Util.sha1(params[2]), "", "", "", "", "", 0, 0, 0, 0, 0);
				
				URI uri = new URI(
						"http",
						Util.WEB_SERVICE,
						Util.REST_UTILISATEUR + "/" + utilisateur.getCourriel(),
						null, null);
				
				HttpPut putMethod = new HttpPut(uri);

				putMethod.setEntity(new StringEntity(JsonUtilisateur
						.ToJSONObject(utilisateur).toString()));
				putMethod.addHeader("Content-Type", "application/json");

				String body = m_ClientHttp.execute(putMethod,
						new BasicResponseHandler());
			} catch (Exception e) {
				m_Exp = e;
			}
			return null;
		}
	}
}
