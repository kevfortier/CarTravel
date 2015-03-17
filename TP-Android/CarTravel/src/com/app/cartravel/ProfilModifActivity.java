package com.app.cartravel;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.app.cartravel.classes.Utilisateurs;
import com.app.cartravel.utilitaire.Util;
import com.app.cartravel.utilitaire.UtilisateurDataSource;

public class ProfilModifActivity extends Activity {

	private Utilisateurs mUtilisateur;
	private UtilisateurDataSource mDataSource;
	private Bundle mExtra;

	private EditText mNumCivique;
	private EditText mRue;
	private EditText mVille;
	private EditText mCodePostal;
	private EditText mNumTel;
	private CheckBox mVoiture;
	private boolean mVerifModif = false;

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
		Intent i = new Intent();

		if (Util.ValiderString(new String[] { strNumCivique, strRue, strVille,
				strCodePostal, strNumTel })) {
			int intNumCivique = 0;

			if (Util.verifCodePostal(strCodePostal)) {

				if (Util.verifNumTel(strNumTel)) {
					UtilisateurDataSource dataSource = new UtilisateurDataSource(
							this);

					dataSource.open();
					mUtilisateur.setNumCivique(strNumCivique);
					mUtilisateur.setRue(strRue);
					mUtilisateur.setVille(strVille);
					mUtilisateur.setCodePostal(strCodePostal);
					mUtilisateur.setNumTel(strNumTel);

					if (mVoiture.isChecked()) {
						mUtilisateur.setVoiture(1);
					} else {
						mUtilisateur.setVoiture(0);
					}

					dataSource.update(mUtilisateur);
					dataSource.close();

					mVerifModif = true;

					this.setResult(RESULT_OK, i);
					this.finish();
				} else {
					Toast.makeText(this, R.string.toast_num_tel_confirm,
							Toast.LENGTH_SHORT).show();
				}

			} else {
				Toast.makeText(this, R.string.toast_code_postal_confirm,
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
		if (mVerifModif = false) {
			this.setResult(RESULT_CANCELED);
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

}
