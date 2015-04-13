package com.app.cartravel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ListView;

import com.app.cartravel.classes.Parcours;
import com.app.cartravel.classes.Utilisateurs;
import com.app.cartravel.utilitaire.ParcourDataSource;
import com.app.cartravel.utilitaire.UtilisateurDataSource;
import com.app.cartravel.utilitaires.ArrayAdapters.ParcoursAdapter;
import com.app.cartravel.utilitaires.ArrayAdapters.ParcoursItem;

public class ParcourActivity extends Activity implements ActionBar.TabListener {
	public static final int AJOUTER_PARCOUR = 1;

	private SectionsPagerAdapter mSectionsPagerAdapter;
	private ViewPager mViewPager;

	ListView m_MesDemandesConducteur;
	ListView m_MesDemandesPassagers;
	ListView m_ConducteurPot;
	ListView m_MesConducteurs;
	ListView m_MesPassagersPot;
	ListView m_MesPassagers;

	View m_ParcoursView;

	private List<Parcours> m_LstParcours;
	private ParcourDataSource dataParcours;
	//private ParcoursAdapter m_Adapter;

	private UtilisateurDataSource dataUser;
	private Utilisateurs m_UtilisateurConnecte;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Permet d'avoir le Progress Circle dans le ActionBar
		// Doit être faire *avant* de charger le layout
		this.requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);

		setContentView(R.layout.activity_parcour);

		// Set up the action bar.
		final ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// Create the adapter that will return a fragment for each of the
		// three
		// primary sections of the activity.
		mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

		// When swiping between different sections, select the corresponding
		// tab. We can also use ActionBar.Tab#select() to do this if we have
		// a reference to the Tab.
		mViewPager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						actionBar.setSelectedNavigationItem(position);
					}
				});

		// For each of the sections in the app, add a tab to the action bar.
		for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
			// Create a tab with text corresponding to the page title
			// defined by
			// the adapter. Also specify this Activity object, which
			// implements
			// the TabListener interface, as the callback (listener) for
			// when
			// this tab is selected.
			actionBar.addTab(actionBar.newTab()
					.setText(mSectionsPagerAdapter.getPageTitle(i))
					.setTabListener(this));
		}
	}

	public void fillListMesDemandeConducteur(ListView laListe) {
		dataParcours = new ParcourDataSource(this);
		dataParcours.open();
		List<Parcours> toutsParcours = dataParcours.getAllParcours();
		dataParcours.close();
		if (!toutsParcours.isEmpty()) {
			dataUser = new UtilisateurDataSource(this);
			dataUser.open();
			m_UtilisateurConnecte = dataUser.getConnectedUtilisateur();
			dataUser.close();

			for (Parcours unParcours : toutsParcours) {
				if (unParcours.getIdProprietaire() == m_UtilisateurConnecte
						.getId()
						&& unParcours.getIdConducteur() != m_UtilisateurConnecte
								.getId()) {
					if (m_LstParcours == null) {
						m_LstParcours = new ArrayList<Parcours>();
					}
					m_LstParcours.add(unParcours);
				}
			}
			if (m_LstParcours != null) {

				laListe.setAdapter(new ParcoursAdapter(this,
						R.layout.lst_parcours_item, ConvertParcoursToListItems(m_LstParcours)));
/*
				View view;
				LayoutInflater inflater = (LayoutInflater) getBaseContext()
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				view = inflater.inflate(R.layout.fragment_parcours, null);

				m_MesDemandesConducteur = (ListView) view
						.findViewById(R.id.lst_demande_conducteurs);
				m_Adapter = new ParcoursAdapter(this,
						R.layout.lst_parcours_item,
						ConvertParcoursToListItems(m_LstParcours));
				m_MesDemandesConducteur.setAdapter(m_Adapter);*/
			}
		}
	}

	public void fillListMesDemandePassagers() {
		if (m_LstParcours != null) {
			dataUser.open();
			m_UtilisateurConnecte = dataUser.getConnectedUtilisateur();
			dataUser.close();
			m_MesDemandesConducteur = (ListView) this
					.findViewById(R.id.lst_demande_conducteurs);
			for (Parcours unParcour : m_LstParcours) {
				if (unParcour.getIdProprietaire() == m_UtilisateurConnecte
						.getId()
						&& unParcour.getIdConducteur() == m_UtilisateurConnecte
								.getId()) {
					// TODO
				}
			}
		}
	}

	public void fillListConducteursPot() {
		if (m_LstParcours != null) {
			dataUser.open();
			m_UtilisateurConnecte = dataUser.getConnectedUtilisateur();
			dataUser.close();
			m_MesDemandesConducteur = (ListView) this
					.findViewById(R.id.lst_demande_conducteurs);
			for (Parcours unParcour : m_LstParcours) {
				if (unParcour.getIdProprietaire() != m_UtilisateurConnecte
						.getId()
						&& unParcour.getIdConducteur() == m_UtilisateurConnecte
								.getId()) {
					// TODO
				}
			}
		}
	}

	public void fillListMesConducteurs() {
		if (m_LstParcours != null) {
			dataUser.open();
			m_UtilisateurConnecte = dataUser.getConnectedUtilisateur();
			dataUser.close();
			m_MesDemandesConducteur = (ListView) this
					.findViewById(R.id.lst_demande_conducteurs);
			for (Parcours unParcour : m_LstParcours) {
				if (unParcour.getIdConducteur() == m_UtilisateurConnecte
						.getId()) {
					// TODO
				}
			}
		}
	}

	public void fillListPassagersPot() {
		if (m_LstParcours != null) {
			dataUser.open();
			m_UtilisateurConnecte = dataUser.getConnectedUtilisateur();
			dataUser.close();
			m_MesDemandesConducteur = (ListView) this
					.findViewById(R.id.lst_demande_conducteurs);
			for (Parcours unParcour : m_LstParcours) {
				if (unParcour.getIdConducteur() == m_UtilisateurConnecte
						.getId()) {
					// TODO
				}
			}
		}
	}

	public void fillListMesPassagers() {
		if (m_LstParcours != null) {
			dataUser.open();
			m_UtilisateurConnecte = dataUser.getConnectedUtilisateur();
			dataUser.close();
			m_MesDemandesConducteur = (ListView) this
					.findViewById(R.id.lst_demande_conducteurs);
			for (Parcours unParcour : m_LstParcours) {
				if (unParcour.getIdConducteur() == m_UtilisateurConnecte
						.getId()) {
					// TODO
				}
			}
		}
	}

	private List<ParcoursItem> ConvertParcoursToListItems(
			List<Parcours> parcours) {
		List<ParcoursItem> items = new ArrayList<ParcoursItem>();
		for (Parcours unParcours : parcours) {
			String strAdresse = unParcours.getNumCiviqueArr()
					+ unParcours.getRueArr();
			items.add(new ParcoursItem(unParcours.getJour(), strAdresse));
		}
		return items;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.menu_ajout_parcours, menu);

		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			return true;
		case R.id.action_ajout_parcours:
			Intent i = new Intent(this, AjoutParcoursActivity1.class);
			this.startActivityForResult(i, AJOUTER_PARCOUR);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	protected void onActivityResult(int p_RequestCode, int p_resultCode,
			Intent p_Data) {
	}

	// Sï¿½lection du fragment
	@Override
	public void onTabSelected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		// When the given tab is selected, switch to the corresponding page in
		// the ViewPager.
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	// Choix du fragment
	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a PlaceholderFragment (defined as a static inner class
			// below).
			Fragment frag = null;

			if (position == 0) {
				frag = PlaceholderFragmentParcours.newInstance(position + 1);
			} else if (position == 1) {
				frag = PlaceholderFragmentPassager.newInstance(position + 1);
			} else {
				frag = PlaceholderFragmentConducteur.newInstance(position + 1);
			}

			return frag;
		}

		@Override
		public int getCount() {
			// Show 4 total pages.
			return 3;
		}

		public CharSequence getPageTitle(int position) {

			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.frag_parcour).toUpperCase(l);
			case 1:
				return getString(R.string.frag_passager).toUpperCase(l);
			case 2:
				return getString(R.string.frag_conducteur).toUpperCase(l);
			}
			return null;
		}
	}

	// pour le fragment de la description
	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragmentPassager extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		private static final String ARG_SECTION_NUMBER = "section_number";

		/**
		 * Returns a new instance of this fragment for the given section number.
		 */
		public static PlaceholderFragmentPassager newInstance(int sectionNumber) {
			PlaceholderFragmentPassager fragment = new PlaceholderFragmentPassager();
			Bundle args = new Bundle();
			args.putInt(ARG_SECTION_NUMBER, sectionNumber);
			fragment.setArguments(args);
			return fragment;
		}

		public PlaceholderFragmentPassager() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {

			View rootView = inflater.inflate(R.layout.fragment_passager,
					container, false);

			return rootView;
		}
	}

	// Pour le fragment de l'image.
	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragmentConducteur extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		private static final String ARG_SECTION_NUMBER = "section_number";

		/**
		 * Returns a new instance of this fragment for the given section number.
		 */
		public static PlaceholderFragmentConducteur newInstance(
				int sectionNumber) {
			PlaceholderFragmentConducteur fragment = new PlaceholderFragmentConducteur();
			Bundle args = new Bundle();
			args.putInt(ARG_SECTION_NUMBER, sectionNumber);
			fragment.setArguments(args);
			return fragment;
		}

		public PlaceholderFragmentConducteur() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {

			View rootView = inflater.inflate(R.layout.fragment_conducteur,
					container, false);

			return rootView;
		}
	}

	public static class PlaceholderFragmentParcours extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		private static final String ARG_SECTION_NUMBER = "section_number";

		/**
		 * Returns a new instance of this fragment for the given section number.
		 */
		public static PlaceholderFragmentParcours newInstance(int sectionNumber) {
			PlaceholderFragmentParcours fragment = new PlaceholderFragmentParcours();
			Bundle args = new Bundle();
			args.putInt(ARG_SECTION_NUMBER, sectionNumber);
			fragment.setArguments(args);
			return fragment;
		}

		public PlaceholderFragmentParcours() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {

			ParcourActivity activity = ((ParcourActivity) this.getActivity());

			View rootView = inflater.inflate(R.layout.fragment_parcours,
					container, false);

			activity.fillListMesDemandeConducteur((ListView) rootView.findViewById(R.id.lst_demande_conducteurs));

			return rootView;
		}
	}
}