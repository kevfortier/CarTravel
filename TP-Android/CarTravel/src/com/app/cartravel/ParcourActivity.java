package com.app.cartravel;

import java.util.Locale;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.app.cartravel.classes.Utilisateurs;
import com.app.cartravel.utilitaire.UtilisateurDataSource;

public class ParcourActivity extends Activity implements ActionBar.TabListener {

	private SectionsPagerAdapter mSectionsPagerAdapter;
	private ViewPager mViewPager;

	private Utilisateurs m_Utilisateur;

	private Bundle extras;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Permet d'avoir le Progress Circle dans le ActionBar
		// Doit être faire *avant* de charger le layout
		this.requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);

		extras = this.getIntent().getExtras();

		UtilisateurDataSource uds = new UtilisateurDataSource(this);
		uds.open();
		m_Utilisateur = uds.getConnectedUtilisateur();
		uds.close();

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

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// app icon in action bar clicked; goto parent activity.
			this.finish();
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
			// Show 3 total pages.
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

			View rootView = inflater.inflate(R.layout.fragment_parcours,
					container, false);

			return rootView;
		}
	}
}