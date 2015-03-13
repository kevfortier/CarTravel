package com.app.cartravel;

import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.app.cartravel.classes.Utilisateurs;
import com.app.cartravel.utilitaire.Util;
import com.app.cartravel.utilitaire.UtilisateurDataSource;

public class ProfilModifActivity extends Activity{

	private Utilisateurs mUtilisateur;
	private UtilisateurDataSource mDataSource;
	private Bundle mExtra;
	
	private EditText mNumCivique;
	private EditText mRue;
	private EditText mVille;
	private EditText mCodePostal;
	private CheckBox mVoiture;
	private RatingBar mNoteCond;
	private RatingBar mNotePass;
	private boolean mVerifModif = false;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_profil_modif);
		setTitle(R.string.title_modif_profil);
		
		mNumCivique = (EditText) findViewById(R.id.txt_num_civ);
		mRue = (EditText) findViewById(R.id.txt_rue);
		mVille= (EditText) findViewById(R.id.txt_ville);
		mCodePostal = (EditText) findViewById(R.id.txt_cod_post);
		mVoiture = (CheckBox) findViewById(R.id.chck_voiture);
		mNoteCond = (RatingBar) findViewById(R.id.rating_cond);
		mNotePass = (RatingBar) findViewById(R.id.rating_pass);
		
		mDataSource = new UtilisateurDataSource(this);
		mDataSource.open();
		mUtilisateur = mDataSource.getConnectedUtilisateur();
		mDataSource.close();
		
		if(mUtilisateur!= null)
		{
			AfficherInfoCompte(mNumCivique, mRue, mVille, mCodePostal, mVoiture, mNoteCond, mNotePass);
		}
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		getMenuInflater().inflate(R.menu.menu_profile_modif, menu);
		
		return true;
	}

	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		if (item.getItemId() == R.id.action_confirmer_profil)
		{
			
		}
		
		return super.onOptionsItemSelected(item);
	}
	
	
	public void ModifierProfil()
	{
		String strNumCivique = mNumCivique.getText().toString().trim();
		String strRue = mRue.getText().toString().trim();
		String strVille = mVille.getText().toString().trim();
		String strCodePostal = mCodePostal.getText().toString().trim();
		Intent i = new Intent();
		
		if(Util.ValiderString(new String[]{strNumCivique, strRue, strVille, strCodePostal}))
		{
			int intNumCivique = 0;

			try {
				intNumCivique = Integer.parseInt(strNumCivique);
				if (Util.verifCodePostal(strCodePostal))
				{
					mDataSource = new UtilisateurDataSource(this);
					mDataSource.open();
					
				}
				else
				{
					Toast.makeText(this, R.string.toast_code_postal_confirm, Toast.LENGTH_SHORT).show();
				}
			} 
			catch(NumberFormatException nfe) 
			{
				Toast.makeText(this, R.string.toast_num_civique_confirm, Toast.LENGTH_SHORT).show();
			} 
			
		}
		else
		{
			if(!Util.ValiderString(new String[]{strNumCivique}))
			{
				Toast.makeText(this, R.string.toast_num_civique_vide, Toast.LENGTH_SHORT).show();
			}
			if(!Util.ValiderString(new String[]{strRue}))
			{
				Toast.makeText(this, R.string.toast_rue_vide, Toast.LENGTH_SHORT).show();
			}
			if(!Util.ValiderString(new String[]{strVille}))
			{
				Toast.makeText(this, R.string.toast_ville_vide, Toast.LENGTH_SHORT).show();
			}
			if(!Util.ValiderString(new String[]{strCodePostal}))
			{
				Toast.makeText(this, R.string.toast_code_postal_vide, Toast.LENGTH_SHORT).show();
			}
		}
		if (mVerifModif = false)
		{
			this.setResult(RESULT_CANCELED);
		}
	}
	
	public void AfficherInfoCompte(TextView mNumCivique, TextView mRue,
			TextView mVille, TextView mCodePostal, CheckBox mVoiture,
			RatingBar mNoteCond, RatingBar mNotePass) 
	{
		 mNumCivique.setText(mUtilisateur.getNumCivique());
		 mRue.setText(mUtilisateur.getRue());
		 mVille.setText(mUtilisateur.getVille());
		 mCodePostal.setText(mUtilisateur.getCodePostal());
		 mVoiture.setChecked(mUtilisateur.getVoiture());
		 mNoteCond.setRating(mUtilisateur.getNoteCond());
		 mNotePass.setRating(mUtilisateur.getNotePass());
	}
	
	
}
