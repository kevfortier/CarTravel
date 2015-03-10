package com.app.cartravel;

import java.util.Locale;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.app.cartravel.utilitaires.navigationdrawer.NavigationDrawerUtil;

@SuppressWarnings("deprecation")
public class MainActivity extends Activity {

	NavigationDrawerUtil menu_gauche = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		menu_gauche = new NavigationDrawerUtil(this);
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

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	/**
	 * Classe pour le choix du fragment sur le fragment MainActivity
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(android.support.v4.app.FragmentManager fm) {
			super(fm);
		}

		/**
		 * Fait afficher le fragment choisi
		 */
		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a PlaceholderFragment (defined as a static inner class
			// below).
			Fragment frag = null;

			if (position == 0) {
				frag = PlaceholderFragmentProfile.newInstance(position + 1);
			} else if (position == 1) {
				frag = PlaceholderFragmentPassager.newInstance(position + 1);
			} else {
				frag = PlaceholderFragmentConducteur.newInstance(position + 1);
			}

			return frag;
		}

		/**
		 * Retourne le nombre de fragment disponible
		 */
		@Override
		public int getCount() {
			return 3;
		}
		
		/**
		 * Donne le titre du fragment qui sera affiché
		 * dans la barre de menu
		 */
		@Override
		public CharSequence getPageTitle(int position) {

			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.frag_profil).toUpperCase(l);
			case 1:
				return getString(R.string.frag_passager).toUpperCase(l);
			case 2:
				return getString(R.string.frag_conducteur).toUpperCase(l);
			}
			return null;
		}
	}

	/**
	 * Classe pour le fragment du Profil
	 */
	public static class PlaceholderFragmentProfile extends Fragment {
		private static final String ARG_SECTION_NUMBER = "section_number";
		/**
		 * Returns a new instance of this fragment for the given section number.
		 */
		public static PlaceholderFragmentProfile newInstance(int sectionNumber) {
			PlaceholderFragmentProfile fragment = new PlaceholderFragmentProfile();
			Bundle args = new Bundle();
			args.putInt(ARG_SECTION_NUMBER, sectionNumber);
			fragment.setArguments(args);
			return fragment;
		}
	}

	/**
	 * Classe pour le fragment du Passager
	 */
	public static class PlaceholderFragmentPassager extends Fragment {
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
	}

	/**
	 * Classe pour le fragment du Conducteur
	 */
	public static class PlaceholderFragmentConducteur extends Fragment {
		private static final String ARG_SECTION_NUMBER = "section_number";
		/**
		 * Returns a new instance of this fragment for the given section number.
		 */
		public static PlaceholderFragmentConducteur newInstance(int sectionNumber) {
			PlaceholderFragmentConducteur fragment = new PlaceholderFragmentConducteur();
			Bundle args = new Bundle();
			args.putInt(ARG_SECTION_NUMBER, sectionNumber);
			fragment.setArguments(args);
			return fragment;
		}
	}
}
