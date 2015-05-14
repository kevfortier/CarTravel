package com.app.cartravel;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.app.cartravel.classes.Parcours;
import com.app.cartravel.classes.ParcoursPassager;
import com.app.cartravel.classes.Utilisateurs;
import com.app.cartravel.utilitaire.ParcoursPassagerDataSource;
import com.app.cartravel.utilitaire.UtilisateurDataSource;

public class UnParcoursActivity extends Activity {

	private Bundle extras;

	private Parcours unParcours;
	private ParcoursPassager unParcoursPassagers;

	private Utilisateurs unUser;
	private UtilisateurDataSource userData;
	private ParcoursPassagerDataSource ParcPassData;

	private TextView m_Demandeur;
	private TextView m_Conducteur;
	private TextView m_Date;
	private TextView m_Reptitif;
	private TextView m_NbrPassagers;
	private TextView m_nbrPlaceTot;
	private TextView m_NbrPlacePrise;
	private TextView m_DistanceSupMax;
	private TextView m_AdresseDep;
	private TextView m_VilleDep;
	private TextView m_CodePostalDep;
	private TextView m_AdresseArr;
	private TextView m_VilleArr;
	private TextView m_CodePostalArr;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_un_parcours);

		final ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);

		extras = this.getIntent().getExtras();

		if (extras != null) {
			if (extras.getSerializable(ParcourActivity.EXTRA_PARCOURS) != null) {
				unParcours = (Parcours) extras
						.getSerializable(ParcourActivity.EXTRA_PARCOURS);
				
			ParcPassData = new ParcoursPassagerDataSource(this);
			unParcoursPassagers = ParcPassData.getParcoursPassager(unParcours.getId());
			
			}
		}

		m_Demandeur = (TextView) findViewById(R.id.txtProprpietaire);
		m_Conducteur = (TextView) findViewById(R.id.txtConducteur);
		m_Date = (TextView) findViewById(R.id.txtDate);
		m_Reptitif = (TextView) findViewById(R.id.txtRepetitif);
		m_NbrPassagers = (TextView) findViewById(R.id.txtPlacePassagers);
		m_nbrPlaceTot = (TextView) findViewById(R.id.txtPlaceDispo);
		m_NbrPlacePrise = (TextView) findViewById(R.id.txtPlacePrise);
		m_DistanceSupMax = (TextView) findViewById(R.id.txtDistanceSupMax);
		m_AdresseDep = (TextView) findViewById(R.id.txtAdresseDepart);
		m_VilleDep = (TextView) findViewById(R.id.txtVilleDepart);
		m_CodePostalDep = (TextView) findViewById(R.id.txtCodePostalDepart);
		m_AdresseArr = (TextView) findViewById(R.id.txtAdresseArrive);
		m_VilleArr = (TextView) findViewById(R.id.txtVilleArrive);
		m_CodePostalArr = (TextView) findViewById(R.id.txtCodePostalArrive);

		afficherParcours(m_Demandeur, m_Conducteur, m_Date, m_Reptitif,
				m_NbrPassagers, m_nbrPlaceTot, m_NbrPlacePrise,
				m_DistanceSupMax, m_AdresseDep, m_VilleDep, m_CodePostalDep,
				m_AdresseArr, m_VilleArr, m_CodePostalArr);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.menu_modif_parcours, menu);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void afficherParcours(TextView p_Demandeur, TextView p_Conducteur,
			TextView p_Date, TextView p_Repetitif, TextView p_NbrPassagers,
			TextView p_nbrPlaceTot, TextView p_NbrPlacePrise,
			TextView p_DistanceSupMax, TextView p_AdresseDep,
			TextView p_VilleDep, TextView p_CodePostalDep,
			TextView p_AdresseArr, TextView p_VilleArr, TextView p_CodePostalArr) {

		userData = new UtilisateurDataSource(this);
		userData.open();
		unUser = userData.getUtilisateur(unParcours.getIdProprietaire());
		userData.close();

		String strRepet = "";

		if (unParcours.getRepetitif()) {
			strRepet = "Repetitif";
		} else {
			strRepet = "Ponctuel";
		}

		p_Demandeur.setText(unUser.getPseudo());

		if (unParcours.getIdConducteur() != 0) {
			userData.open();
			unUser = userData.getUtilisateur(unParcours.getIdConducteur());
			userData.close();
			p_Conducteur.setText(unUser.getPseudo());
		}

		p_Date.setText(unParcours.getJour());
		p_Repetitif.setText(strRepet);
		p_NbrPassagers
				.setText(String.valueOf(unParcoursPassagers.getNbrPassagers()));
		p_nbrPlaceTot.setText(String.valueOf(unParcours.getNbPlaceDispo()));
		p_NbrPlacePrise.setText(String.valueOf(unParcours.getNbPlacePrise()));
		p_DistanceSupMax
				.setText(Float.toString(unParcours.getDistanceSupMax()));
		p_AdresseDep.setText(unParcours.getNumCiviqueDep()
				+ unParcours.getRueDep());
		p_VilleDep.setText(unParcours.getVilleDep());
		p_CodePostalDep.setText(unParcours.getCodePostalDep());
		p_AdresseArr.setText(unParcours.getNumCiviqueArr() + " "
				+ unParcours.getRueArr());
		p_VilleArr.setText(unParcours.getVilleArr());
		p_CodePostalArr.setText(unParcours.getCodePostalArr());
	}

	public void btnConducteur(View source) {
		// TODO Click du btn Conducteur
	}

	public void btnPassager(View source) {
		// TODO Click du btn Conducteur
	}
}
