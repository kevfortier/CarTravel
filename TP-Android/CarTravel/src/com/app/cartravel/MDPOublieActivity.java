package com.app.cartravel;

import java.net.URI;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import com.app.cartravel.classes.Utilisateurs;
import com.app.cartravel.jsonparser.JsonProfil;
import com.app.cartravel.jsonparser.JsonUtilisateur;
import com.app.cartravel.utilitaire.Util;
import com.app.cartravel.utilitaire.UtilisateurDataSource;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MDPOublieActivity extends Activity {

	private Utilisateurs mUserExistant;
	private EditText courriel;
	private EditText courrielConfirmation;
	private Utilisateurs mUtillisateur;
	
	private HttpClient m_ClientHttp = new DefaultHttpClient();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_mdp_oublie);
		courriel = (EditText) this.findViewById(R.id.courriel_envoie);
		courrielConfirmation = (EditText) this.findViewById(R.id.courriel_envoie_verif);
		
	}
	
	public void EnvoieCourriel(View source) {
		String strCourriel = courriel.getText().toString().trim();
		String strCourrielConfirm = courrielConfirmation.getText().toString().trim();
		
		if(Util.ValiderString(new String[] {strCourriel, strCourrielConfirm})){
			if(Util.isCourriel(strCourriel)) {
				if(strCourriel.matches(strCourrielConfirm)){
					UtilisateurDataSource uds = new UtilisateurDataSource(this);
					uds.open();
					Boolean userVerif = uds.getCourrielUser(strCourriel);
					
					
					if (!userVerif){
						new ObtenirUtilisateur(this).execute(strCourriel);
					}
					
					if (mUserExistant != null || userVerif ){
						Intent email = new Intent(Intent.ACTION_SEND, Uri.parse("mailto:"));
						email.setType("message/rfc822");
						email.putExtra(Intent.EXTRA_EMAIL, strCourriel);
						email.putExtra(Intent.EXTRA_SUBJECT, R.string.mdp_oublie);
						email.putExtra(Intent.EXTRA_TEXT, "Voici votre mot de passe: " + uds.getUtilisateur(strCourriel).getMotDePasse());
						uds.close();
						try {
							startActivity(Intent.createChooser(email,  "Choisissez le serveur courriel..."));
							Toast.makeText(
									this,
									this.getResources().getText(
											R.string.courriel_envoye),
									Toast.LENGTH_SHORT).show();
						}catch (android.content.ActivityNotFoundException ex){
							Toast.makeText(
									this,
									this.getResources().getText(
											R.string.aucun_compte_existant),
									Toast.LENGTH_SHORT).show();
						}
					} else {
						Toast.makeText(
								this,
								this.getResources().getText(
										R.string.aucun_compte_existant),
								Toast.LENGTH_SHORT).show();
					}
					
				} else {
					Toast.makeText(
							this,
							this.getResources().getText(
									R.string.toast_courriel_identique),
							Toast.LENGTH_SHORT).show();
				} 
			} else {
			Toast.makeText(
					this,
					this.getResources().getText(
							R.string.toast_courriel_invalide),
					Toast.LENGTH_SHORT).show();
			}
		} else {
			if (!Util.ValiderString(new String[] { strCourriel })) {
				Toast.makeText(
						this,
						this.getResources().getText(
								R.string.toast_courriel_vide),
						Toast.LENGTH_SHORT).show();
			}
		}
	}
	
	private class ObtenirUtilisateur extends AsyncTask<String, Void, Utilisateurs> {
		private Exception m_Exp;
		private Context m_Context;
		
		public ObtenirUtilisateur(Context p_context) {
			this.m_Context = p_context;
		}
		
		@Override
		protected Utilisateurs doInBackground(String... params) {
			Utilisateurs m_Utilisateur = null;		
			String strCourriel = params[0];
			try {
				URI uri = new URI(
						"http",
						Util.WEB_SERVICE,
						Util.REST_UTILISATEUR + "/" + strCourriel,
						null, null);
				HttpGet getMethod = new HttpGet(uri);
				String body = m_ClientHttp.execute(getMethod,
						new BasicResponseHandler());
				m_Utilisateur = (Utilisateurs) JsonUtilisateur.ToUtilisateur(body);
			} catch (Exception e) {
				m_Exp = e;
			}
			return m_Utilisateur;
		}
		
		protected void onPostExecute(Utilisateurs result) {
			if (m_Exp == null && result != null) {

				UtilisateurDataSource uds = new UtilisateurDataSource(m_Context);
				uds.open();
				if (uds.getUtilisateur(result.getCourriel()) == null) {
					uds.insert(result);
				}
				mUserExistant = uds.getConnectedUtilisateur();
				uds.close();
			}
		}
	}
}
