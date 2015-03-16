package com.app.cartravel;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.app.cartravel.classes.Utilisateurs;
import com.app.cartravel.utilitaire.UtilisateurDataSource;

public class CompteActivity extends Activity {

	public static final int MODIFIER_COMPTE = 2;

	public Utilisateurs mUtilisateur;
	private TextView mCourriel;
	private TextView mPseudo;
	private UtilisateurDataSource mDataSource;

	// private Bundle m_extra;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		getActionBar().setDisplayHomeAsUpEnabled(true);

		mDataSource = new UtilisateurDataSource(this);
		mDataSource.open();
		mUtilisateur = mDataSource.getConnectedUtilisateur();
		mDataSource.close();

		setContentView(R.layout.activity_compte);
		mCourriel = (TextView) findViewById(R.id.txt_username);
		mPseudo = (TextView) findViewById(R.id.txt_pseudo);

		if (mUtilisateur != null) {
			AfficherInfoCompte(mCourriel, mPseudo);
		}

	}

	// Menu de cette page
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_compte, menu);
		return true;
	}

	// Modification ou non du compte.
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK && requestCode == MODIFIER_COMPTE) {
			mDataSource = new UtilisateurDataSource(this);
			mDataSource.open();

			// Afficher ses nouvelles infos.
			mUtilisateur = mDataSource.getConnectedUtilisateur();
			mDataSource.close();
			AfficherInfoCompte(mCourriel, mPseudo);
			Toast.makeText(this, R.string.toast_modif_compte,
					Toast.LENGTH_SHORT).show();
		}
		if (resultCode == RESULT_CANCELED && requestCode == MODIFIER_COMPTE) {
			Toast.makeText(this, R.string.toast_annul_modif_compte,
					Toast.LENGTH_SHORT).show();
		}
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		Intent i;
		switch (item.getItemId()) {
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			return true;
		case R.id.action_modifier_compte:
			i = new Intent(this, CompteModifActivity.class);
			this.startActivityForResult(i, MODIFIER_COMPTE);
        	return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	// Méthode permettant d'afficher les info. du compte d'un utilisateur
	public void AfficherInfoCompte(TextView mCourriel, TextView mPseudo) {
		mCourriel.setText(mUtilisateur.getCourriel());
		mPseudo.setText(mUtilisateur.getPseudo());

	}

}
