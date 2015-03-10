package com.app.cartravel;


import java.net.URI;
import java.util.ArrayList;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.app.cartravel.classes.Utilisateurs;
import com.app.cartravel.jsonparser.JsonUtilisateur;
import com.app.cartravel.utilitaire.Util;
import com.app.cartravel.utilitaire.UtilisateurDataSource;

public class CreationCompteActivity extends Activity {

	private EditText courriel;
	private EditText courrielConfirmation;
	private EditText pseudo;
	private EditText motDePasse;
	private EditText motDePasseConfirmation;
	
	private HttpClient m_ClientHttp = new DefaultHttpClient();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_creation_compte);
		courriel = (EditText)this.findViewById(R.id.new_username);
		courrielConfirmation = (EditText)this.findViewById(R.id.new_username_confirmation);
		pseudo = (EditText)this.findViewById(R.id.new_pseudo);
		motDePasse = (EditText)this.findViewById(R.id.new_password);
		motDePasseConfirmation = (EditText)this.findViewById(R.id.new_password_confirmation);
	}

	public void CreerCompte(View source){
		
		String strCourriel = courriel.getText().toString().trim();
		String strCourrielConfirmation = courrielConfirmation.getText().toString().trim();
		String strPseudo = pseudo.getText().toString().trim();
		String strMotDePasse = motDePasse.getText().toString().trim();
		String strMotDePasseConfirmation = motDePasseConfirmation.getText().toString().trim();
		
		if(Util.ValiderString(new String[]{strCourriel, strCourrielConfirmation, strPseudo, strMotDePasse, strMotDePasseConfirmation})){
			if(Util.isCourriel(strCourriel)){
				if (strCourriel.equals(strCourrielConfirmation)){
					if(strMotDePasse.equals(strMotDePasseConfirmation)){
						new CreerCompteTask(this).execute(strCourriel, strPseudo, strMotDePasse);
					}
					else{
						Toast.makeText(this, this.getResources().getText(R.string.toast_courriel_invalide), Toast.LENGTH_SHORT).show();
					}
				}
				else{
					Toast.makeText(this, this.getResources().getText(R.string.toast_courriel_invalide), Toast.LENGTH_SHORT).show();
				}
			}
			else{
				Toast.makeText(this, this.getResources().getText(R.string.toast_courriel_invalide), Toast.LENGTH_SHORT).show();
			}
		}
		else{
			if (!Util.ValiderString(new String[]{strCourriel})) {
				Toast.makeText(this, this.getResources().getText(R.string.toast_courriel_vide), Toast.LENGTH_SHORT).show();
			}
			if (!Util.ValiderString(new String[]{strCourriel})) {
				Toast.makeText(this, this.getResources().getText(R.string.toast_confirm_courriel_vide), Toast.LENGTH_SHORT).show();
			}
			if (!Util.ValiderString(new String[]{strCourriel})) {
				Toast.makeText(this, this.getResources().getText(R.string.toast_pseudo_vide), Toast.LENGTH_SHORT).show();
			}
			if (!Util.ValiderString(new String[]{strCourriel})) {
				Toast.makeText(this, this.getResources().getText(R.string.toast_mdp_vide), Toast.LENGTH_SHORT).show();
			}
			if (!Util.ValiderString(new String[]{strCourriel})) {
				Toast.makeText(this, this.getResources().getText(R.string.toast_confirm_mdp_vide), Toast.LENGTH_SHORT).show();
			}
		}
		
		// Vérifier si le nom de compte existe déjà sur le service Web
		// Si oui, mettre le focus sur le nom d'utilisateur et avertir l'usager
		// Si non, créer l'utilisateur dans le service web et sur l'appareil
	}
	
	
	
	private class CreerCompteTask extends AsyncTask<String, Void, Void>{
		private Exception m_Exp;
		private Context m_Context;

		private Utilisateurs utilisateur;
		
		public CreerCompteTask(Context p_Context){
			this.m_Context = p_Context;
		}
		 
		@Override
		protected Void doInBackground(String... params) {
			try{
				utilisateur = new Utilisateurs(params[0], params[1], Util.sha1(params[2]), false, false, new ArrayList<String>());
				
				URI uri = new URI("http",Util.WEB_SERVICE, Util.REST_UTILISATEUR + "/" + utilisateur.getCourriel(), null , null);
				
				HttpPut putMethod = new HttpPut(uri);
				
				putMethod.setEntity(new StringEntity(JsonUtilisateur.ToJSONObject(utilisateur).toString()));
	    		putMethod.addHeader("Content-Type", "application/json");
	    		
				String body = m_ClientHttp.execute(putMethod, new BasicResponseHandler());

				Log.i("Reponse : ", body);
			}
			catch(Exception e){
				m_Exp = e;
			}
			
			return null;
		}
		
		@Override
		protected void onPostExecute(Void unused) {
			// S'il n'y a pas de message d'erreur
			if(m_Exp == null){
				UtilisateurDataSource dataSource = new UtilisateurDataSource(m_Context);
				dataSource.open();
				// Ajout du nouvel utilisateur
				utilisateur.setId(dataSource.insert(utilisateur));
				dataSource.modifierDernierConnecte(utilisateur);
				dataSource.close();
				Toast.makeText(m_Context, m_Context.getResources().getText(R.string.toast_compte_creer), Toast.LENGTH_LONG).show();
				((Activity)m_Context).finish();
			}
			else{
				Toast.makeText(m_Context, m_Context.getResources().getText(R.string.toast_erreur_creation_compte), 
						Toast.LENGTH_SHORT).show();
				Log.i("CREATIONCOMPTE", "Erreur : " + m_Exp.getMessage());
				motDePasse.setText(null);
				motDePasseConfirmation.setText(null);
			}
		}
	}
}
