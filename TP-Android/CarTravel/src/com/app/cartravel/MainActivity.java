package com.app.cartravel;

import java.net.URI;
import java.util.List;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.RatingBar;
import android.widget.TextView;

import com.app.cartravel.classes.Parcours;
import com.app.cartravel.classes.Utilisateurs;
import com.app.cartravel.jsonparser.JsonParcours;
import com.app.cartravel.jsonparser.JsonProfil;
import com.app.cartravel.jsonparser.JsonUtilisateur;
import com.app.cartravel.utilitaire.Util;
import com.app.cartravel.utilitaire.UtilisateurDataSource;
import com.app.cartravel.utilitaires.ArrayAdapters.ParcoursAdapter;
import com.app.cartravel.utilitaires.navigationdrawer.NavigationDrawerUtil;

@SuppressWarnings("deprecation")
public class MainActivity extends Activity {

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
	
	
	private HttpClient m_ClientHttp = new DefaultHttpClient();

	NavigationDrawerUtil menu_gauche = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		menu_gauche = new NavigationDrawerUtil(this);

		mNumCivique = (TextView) findViewById(R.id.txt_num_civ_main);
		mRue = (TextView) findViewById(R.id.txt_rue_main);
		mVille = (TextView) findViewById(R.id.txt_ville_main);
		mCodePostal = (TextView) findViewById(R.id.txt_cod_post_main);
		mNumTel = (TextView) findViewById(R.id.txt_num_tel_main);
		mVoiture = (CheckBox) findViewById(R.id.chck_voiture_main);
		mNoteCond = (RatingBar) findViewById(R.id.rating_cond_main);
		mNotePass = (RatingBar) findViewById(R.id.rating_pass_main);

		mDataSource = new UtilisateurDataSource(this);
		mDataSource.open();
		mUtilisateur = mDataSource.getConnectedUtilisateur();
		mDataSource.close();
		
		new ObtenirProfilTask(this).execute();

		if (mUtilisateur != null) {
			AfficherInfoProfil(mNumCivique, mRue, mVille, mCodePostal, mNumTel,
					mVoiture, mNoteCond, mNotePass);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		if (menu_gauche.getDrawerToggle().onOptionsItemSelected(item)) {
			return true;
		}

		if (id == R.id.action_settings) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		menu_gauche.getDrawerToggle().syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		menu_gauche.getDrawerToggle().onConfigurationChanged(newConfig);
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

	private class ObtenirProfilTask extends
			AsyncTask<Void, Void, Utilisateurs> {
		private Exception m_Exp;
		private Context m_Context;

		public ObtenirProfilTask(Context p_Context) {
			this.m_Context = p_Context;
		}

		protected Utilisateurs doInBackground(Void... params) {
			Utilisateurs m_Utilisateur = null;
			try{
				URI uri = new URI("http", Util.WEB_SERVICE, Util.REST_UTILISATEUR + "/" + 
						mUtilisateur.getCourriel() + Util.REST_PROFIL, null, null);
				HttpGet getMethod = new HttpGet(uri);
				String body = m_ClientHttp.execute(getMethod,
						new BasicResponseHandler());
				m_Utilisateur = (Utilisateurs) JsonProfil.ToUtilisateur(body);
			} catch(Exception e) {
				m_Exp = e;
			}
			return m_Utilisateur;
		}

		protected void onPostExecute(Utilisateurs result) {
			if (m_Exp == null && result != null) {
				setUser(result);
				UtilisateurDataSource uds = new UtilisateurDataSource(m_Context);
				uds.open();
				if (uds.getUtilisateur(result.getCourriel()) == null) {
					uds.insert(result);
				}
				else {
					uds.update(result);
				}
				uds.close();
				
				AfficherInfoProfil(mNumCivique, mRue, mVille, mCodePostal, mNumTel,
						mVoiture, mNoteCond, mNotePass);
			}
		}
		
		public void setUser(Utilisateurs result) {
			result.setDateAjoutUser(mUtilisateur.getDateAjoutUser());
			result.setDernierConnecte(mUtilisateur.getDernierConnecte());
			result.setEstConnecte(mUtilisateur.getEstConnecte());
			result.setId(mUtilisateur.getId());
			result.setMotDePasse(mUtilisateur.getMotDePasse());
			result.setPseudo(mUtilisateur.getPseudo());
			
			mUtilisateur = result;
		}
	}
}
