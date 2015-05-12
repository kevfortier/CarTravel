package com.app.cartravel;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.app.cartravel.classes.Utilisateurs;
import com.app.cartravel.utilitaire.UtilisateurDataSource;

public class ProfilActivity extends Activity {

	public static final int MODIFIER_PROFIL = 2;

	private Utilisateurs mUtilisateur;
	private UtilisateurDataSource mDataSource;

	private TextView mNumCivique;
	private TextView mRue;
	private TextView mVille;
	private TextView mCodePostal;
	private TextView mNumTel;
	private CheckBox mVoiture;
	private RatingBar mNoteCond;
	private RatingBar mNotePass;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		getActionBar().setDisplayHomeAsUpEnabled(true);

		setContentView(R.layout.acitivity_profile);

		setTitle(R.string.title_profil);

		mNumCivique = (TextView) findViewById(R.id.txt_num_civ);
		mRue = (TextView) findViewById(R.id.txt_rue);
		mVille = (TextView) findViewById(R.id.txt_ville);
		mCodePostal = (TextView) findViewById(R.id.txt_cod_post);
		mNumTel = (TextView) findViewById(R.id.txt_num_tel);
		mVoiture = (CheckBox) findViewById(R.id.chck_voiture);
		mNoteCond = (RatingBar) findViewById(R.id.rating_cond);
		mNotePass = (RatingBar) findViewById(R.id.rating_pass);

		mDataSource = new UtilisateurDataSource(this);
		mDataSource.open();
		mUtilisateur = mDataSource.getConnectedUtilisateur();
		mDataSource.close();

		if (mUtilisateur != null) {
			AfficherInfoProfil(mNumCivique, mRue, mVille, mCodePostal, mNumTel,
					mVoiture, mNoteCond, mNotePass);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.menu_profile, menu);
		return true;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (resultCode == RESULT_OK && requestCode == MODIFIER_PROFIL) {
			mDataSource = new UtilisateurDataSource(this);
			mDataSource.open();
			mUtilisateur = mDataSource.getConnectedUtilisateur();
			mDataSource.close();
			AfficherInfoProfil(mNumCivique, mRue, mVille, mCodePostal, mNumTel,
					mVoiture, mNoteCond, mNotePass);
			Toast.makeText(this, R.string.toast_modif_compte,
					Toast.LENGTH_SHORT).show();
		}

		if (resultCode == RESULT_CANCELED && requestCode == MODIFIER_PROFIL) {
			Toast.makeText(this, R.string.toast_annul_modif, Toast.LENGTH_SHORT)
					.show();
		}
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		Intent i;
		switch (item.getItemId()) {
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			return true;
		case R.id.action_modifier_profil:
			i = new Intent(this, ProfilModifActivity.class);
			this.startActivityForResult(i, MODIFIER_PROFIL);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void AfficherInfoProfil(TextView mNumCivique, TextView mRue,
			TextView mVille, TextView mCodePostal, TextView mNumTel,
			CheckBox mVoiture, RatingBar mNoteCond, RatingBar mNotePass) {
		mNumCivique.setText(mUtilisateur.getNumCivique());
		mRue.setText(mUtilisateur.getRue());
		mVille.setText(mUtilisateur.getVille());
		mCodePostal.setText(mUtilisateur.getCodePostal());
		mNumTel.setText(mUtilisateur.getNumTel());
		if (mUtilisateur.getVoiture() == 1) {
			mVoiture.setChecked(true);
		} else {
			mVoiture.setChecked(false);
		}
		mNoteCond.setRating(mUtilisateur.getNoteCond());
		mNotePass.setRating(mUtilisateur.getNotePass());
	}

}
