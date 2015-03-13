package com.app.cartravel;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.app.cartravel.classes.Utilisateurs;
import com.app.cartravel.utilitaire.Util;
import com.app.cartravel.utilitaire.UtilisateurDataSource;

public class CompteModifActivity extends Activity{

	private Utilisateurs mUtilisateur;
	private TextView mCourriel;
	private TextView mCourrielVerif;
	private TextView mPseudo;
	private TextView mMDP;
	private TextView mMDPVerif;
	private boolean MVerifModif = false;
	private UtilisateurDataSource mDataSource;
	private Bundle m_extra;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_compte_modif);
		
		mCourriel = (EditText) findViewById(R.id.txt_username);
		mCourrielVerif = (EditText) findViewById(R.id.txt_confirmer_username);
		mPseudo = (EditText)findViewById(R.id.txt_pseudo);
		mMDP = (EditText) findViewById(R.id.txt_password);
		mMDPVerif = (EditText) findViewById(R.id.txt_confirmer_password);
		
		setTitle(R.string.title_modif_compte);
		
		mDataSource = new UtilisateurDataSource(this);
		mDataSource.open();
		mUtilisateur = mDataSource.getConnectedUtilisateur();
		mDataSource.close();
		
		if (mUtilisateur != null) {
			AfficherInfoCompte(mCourriel, mPseudo, mMDP);
		}
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		getMenuInflater().inflate(R.menu.menu_compte_modif, menu);
		
		return true;
	}
	
	
	//Lorsque l'utilisateur confirme les modifications de son compte.
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		if (item.getItemId() == R.id.action_confirmer_compte)
		{
			ModifierInfoCompte();
		}
		
		return super.onOptionsItemSelected(item);
	}

	public void ModifierInfoCompte()
	{
		String strCourriel = mCourriel.getText().toString().trim();
		String strCourrielConfirmation = mCourrielVerif.getText().toString().trim();
		String strPseudo = mPseudo.getText().toString().trim();
		String strMotDePasse = mMDP.getText().toString().trim();
		String strMotDePasseConfirmation = mMDPVerif.getText().toString().trim();
		Intent i = new Intent();
		
		if(Util.ValiderString(new String[]{strCourriel, strCourrielConfirmation, strPseudo, strMotDePasse, strMotDePasseConfirmation})){
			if(Util.isCourriel(strCourriel))
			{
				if (strCourriel.equals(strCourrielConfirmation))
				{
					if(strMotDePasse.equals(strMotDePasseConfirmation))
					{
						mDataSource = new UtilisateurDataSource(this);
						mDataSource.open();
						mUtilisateur.setCourriel(strCourriel);
						mUtilisateur.setPseudo(strPseudo);
						mUtilisateur.setMotDePasse(strMotDePasse);
						mDataSource.update(mUtilisateur);
						mDataSource.close();
						
						MVerifModif = true;
						
						this.setResult(RESULT_OK, i);
						this.finish();
					}
					else{
						Toast.makeText(this, R.string.toast_mdp_identique, Toast.LENGTH_SHORT).show();
					}
				}
				else
				{
					Toast.makeText(this, R.string.toast_courriel_identique, Toast.LENGTH_SHORT).show();
				}
			}
			else
			{
				Toast.makeText(this, R.string.toast_courriel_invalide, Toast.LENGTH_SHORT).show();
			}
		}
		else
		{
			if (!Util.ValiderString(new String[]{strCourriel})) {
				Toast.makeText(this, R.string.toast_courriel_vide, Toast.LENGTH_SHORT).show();
			}
			if (!Util.ValiderString(new String[]{strCourrielConfirmation})) {
				Toast.makeText(this,R.string.toast_confirm_courriel_vide, Toast.LENGTH_SHORT).show();
			}
			if (!Util.ValiderString(new String[]{strPseudo})) {
				Toast.makeText(this,R.string.toast_pseudo_vide, Toast.LENGTH_SHORT).show();
			}
			if (!Util.ValiderString(new String[]{strMotDePasse})) {
				Toast.makeText(this,R.string.toast_mdp_vide, Toast.LENGTH_SHORT).show();
			}
			if (!Util.ValiderString(new String[]{strMotDePasseConfirmation})) {
				Toast.makeText(this, R.string.toast_confirm_mdp_vide, Toast.LENGTH_SHORT).show();
			}
		}
		
		if (MVerifModif = false)
		{
			this.setResult(RESULT_CANCELED);
		}
	}
	
	
	//Méthode permettant d'afficher les info. du compte d'un utilisateur
	public void AfficherInfoCompte(TextView mCourriel,
			TextView mPseudo, TextView mMDP) 
	{
		mCourriel.setText(mUtilisateur.getCourriel());
		mPseudo.setText(mUtilisateur.getPseudo());
		mMDP.setText(mUtilisateur.getMotDePasse());
	}
	
}
