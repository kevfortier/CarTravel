package com.app.cartravel.utilitaires.navigationdrawer;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.cartravel.R;
import com.app.cartravel.utilitaires.navigationdrawer.DrawerItem;

public class CustomDrawerAdapter extends ArrayAdapter<DrawerItem> {
	private Context m_Context;
	private List<DrawerItem> m_DrawerItems;
	private int m_LayoutResourceId;

	public CustomDrawerAdapter(Context p_Context, int p_LayoutResourceId,
			List<DrawerItem> p_DrawerItems) {
		super(p_Context, p_LayoutResourceId, p_DrawerItems);
		this.m_Context = p_Context;
		this.m_DrawerItems = p_DrawerItems;
		this.m_LayoutResourceId = p_LayoutResourceId;
	}

	public View getView(int p_Position, View p_ConvertView, ViewGroup p_Parent) {
		DrawerItemHolder contenant;
		View view = p_ConvertView;

		if (view == null) {
			LayoutInflater inflater = ((Activity) m_Context)
					.getLayoutInflater();
			contenant = new DrawerItemHolder();

			view = inflater.inflate(m_LayoutResourceId, p_Parent, false);
			contenant.icone = (ImageView) view
					.findViewById(R.id.menu_item_icon);
			contenant.titre = (TextView) view
					.findViewById(R.id.menu_item_label);
			contenant.notifications = (TextView) view
					.findViewById(R.id.menu_item_notification);
			view.setTag(contenant);
		} else {
			contenant = (DrawerItemHolder) view.getTag();
		}

		DrawerItem item = (DrawerItem) this.m_DrawerItems.get(p_Position);

		contenant.titre.setText(item.getNom());

		if (item.getIdIcone() != -1) {
			contenant.icone.setImageDrawable(view.getResources().getDrawable(
					item.getIdIcone()));
			contenant.icone.setVisibility(View.VISIBLE);
		} else {
			contenant.icone.setVisibility(View.GONE);
		}

		if (item.getNbNotifications() > 0) {
			contenant.notifications.setText(item.getNbNotifications() + "");
			contenant.notifications.setVisibility(View.VISIBLE);
		} else {
			contenant.notifications.setVisibility(View.GONE);
		}

		return view;
	}

	private static class DrawerItemHolder {
		ImageView icone;
		TextView titre;
		TextView notifications;
	}
}
