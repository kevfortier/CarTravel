package com.app.cartravel.utilitaires.navigationdrawer;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.app.cartravel.ConnexionActivity;
import com.app.cartravel.MainActivity;
import com.app.cartravel.ProfilActivity;
import com.app.cartravel.R;
import com.app.cartravel.classes.Utilisateurs;
import com.app.cartravel.utilitaire.UtilisateurDataSource;

@SuppressWarnings("deprecation")
public class NavigationDrawerUtil {
	private DrawerLayout mDrawerLayout;
	private ActionBarDrawerToggle mDrawerToggle;
	private ListView mDrawerList;
	private Activity mContext;

	private CustomDrawerAdapter mAdapter;
	private List<DrawerItem> mDrawerData;

	public NavigationDrawerUtil(Activity p_context) {
		mContext = p_context;
		mDrawerData = genererDonneesMenu();
		mDrawerLayout = (DrawerLayout) p_context
				.findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) p_context.findViewById(R.id.left_drawer);
		mDrawerToggle = new ActionBarDrawerToggle(p_context, mDrawerLayout,
				R.drawable.ic_drawer, R.string.drawer_open,
				R.string.drawer_close) {
			public void onDrawerClosed(View view) {
				super.onDrawerClosed(view);
				// Appelle "onPrepareOptionsMenu" pour recréer l'actionbar
				mContext.invalidateOptionsMenu();
			}

			public void onDrawerOpened(View view) {
				super.onDrawerOpened(view);
				// Appelle "onPrepareOptionsMenu" pour recréer l'actionbar
				mContext.invalidateOptionsMenu();
			}
		};

		mDrawerLayout.setDrawerListener(mDrawerToggle);

		mContext.getActionBar().setDisplayHomeAsUpEnabled(true);
		mContext.getActionBar().setHomeButtonEnabled(true);

		mAdapter = new CustomDrawerAdapter(p_context, R.layout.menu_list_item,
				mDrawerData);
		mDrawerList.setAdapter(mAdapter);
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
	}

	private List<DrawerItem> genererDonneesMenu() {
		List<DrawerItem> items = new ArrayList<DrawerItem>();

		String[] mMenuItems = mContext.getResources().getStringArray(
				R.array.menu_items);
		// À utiliser pour mettre des icones avec les éléments de menu
		// int[] mMenuIcons =
		// Util.GetIntArrayResourceReferences(mContext.getResources().obtainTypedArray(R.array.menu_icons));
		for (int i = 0; i < mMenuItems.length; i++) {
			DrawerItem ligne = new DrawerItem(mMenuItems[i], -1, 0);
			items.add(ligne);
		}

		return items;
	}

	private class DrawerItemClickListener implements
			ListView.OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			selectItem(position, view);
		}

	}

	private void selectItem(int position, View view) {
		mDrawerList.setItemChecked(position, true);

		// ((TextView)view.findViewById(R.id.menu_item_label)).setTextAppearance(mContext,
		// R.style.menu_item_bold);
		Intent i = null;
		// Selon l'item du menu choisi, si on est pas déjà dans cette
		// activité, on l'ouvre
		switch (position) {
		case 0: // Accueil
			if (mContext.getClass() != MainActivity.class)
				i = new Intent(mContext, MainActivity.class);
			break;
		case 1: // Covoiturage
			break;
		case 2: // Mon compte
			break;
		case 3: // Mon profil
			if (mContext.getClass() != ProfilActivity.class)
				i = new Intent(mContext, ProfilActivity.class);
			break;
		case 4: // Param�tres
			break;
		case 5: // D�connexion
			UtilisateurDataSource dataSource = new UtilisateurDataSource(
					mContext);
			dataSource.open();
			Utilisateurs usager = dataSource.getConnectedUtilisateur();
			usager.setEstConnecte(false);
			dataSource.update(usager);
			dataSource.close();

			i = new Intent(mContext, ConnexionActivity.class);
			mContext.finish();
			break;
		}
		if (i != null)
			mContext.startActivity(i);

		mDrawerLayout.closeDrawer(mDrawerList);
	}

	/**
	 * @return the mDrawerLayout
	 */
	public DrawerLayout getDrawerLayout() {
		return mDrawerLayout;
	}

	/**
	 * @return the mDrawerToggle
	 */
	public ActionBarDrawerToggle getDrawerToggle() {
		return mDrawerToggle;
	}

	/**
	 * @return the mDrawerList
	 */
	public ListView getDrawerList() {
		return mDrawerList;
	}
}