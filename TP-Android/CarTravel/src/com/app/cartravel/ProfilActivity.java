package com.app.cartravel;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.app.cartravel.classes.Utilisateurs;
import com.app.cartravel.utilitaire.UtilisateurDataSource;

public class ProfilActivity extends Activity {

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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.acitivity_profile);

		mNumCivique = (EditText) findViewById(R.id.txt_num_civ);
		mRue = (EditText) findViewById(R.id.txt_rue);
		mVille = (EditText) findViewById(R.id.txt_ville);
		mCodePostal = (EditText) findViewById(R.id.txt_cod_post);
		mVoiture = (CheckBox) findViewById(R.id.chck_voiture);
		mNoteCond = (RatingBar) findViewById(R.id.rating_cond);
		mNotePass = (RatingBar) findViewById(R.id.rating_pass);
		mExtra = this.getIntent().getExtras();

		setTitle(R.string.title_modif_profil);

		mDataSource = new UtilisateurDataSource(this);
		mDataSource.open();
		mUtilisateur = mDataSource.getConnectedUtilisateur();
		mDataSource.close();

		if (mUtilisateur != null) {

		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.menu_profile, menu);
		return true;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		/*
		 * if (resultCode == RESULT_OK && requestCode == MODIFIER_COMPTE) {
		 * mDataSource = new UtilisateurDataSource(this); mDataSource.open();
		 * 
		 * //Afficher ses nouvelles infos. mUtilisateur =
		 * mDataSource.getConnectedUtilisateur(); mDataSource.close();
		 * AfficherInfoCompte(mCourriel, mPseudo); Toast.makeText(this,
		 * R.string.toast_modif_compte, Toast.LENGTH_SHORT).show(); } if
		 * (resultCode == RESULT_CANCELED && requestCode == MODIFIER_COMPTE) {
		 * Toast.makeText(this, R.string.toast_annul_modif_compte,
		 * Toast.LENGTH_SHORT).show(); }
		 */
	}

	public void AfficherInfoCompte(TextView mNumCivique, TextView mRue,
			TextView mVille, TextView mCodePostal, CheckBox mVoiture,
			RatingBar mNoteCond, RatingBar mNotePass) {
	}

}
