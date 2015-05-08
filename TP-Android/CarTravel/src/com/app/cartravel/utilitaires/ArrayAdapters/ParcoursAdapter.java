package com.app.cartravel.utilitaires.ArrayAdapters;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.app.cartravel.R;

public class ParcoursAdapter extends ArrayAdapter<ParcoursItem> {
	private Context m_Context;
	private List<ParcoursItem> m_Parcours;
	private int m_LayoutResId;

	public ParcoursAdapter(Context p_Context, int p_LayoutResId,
			List<ParcoursItem> p_Parcours) {
		super(p_Context, p_LayoutResId, p_Parcours);
		this.m_Context = p_Context;
		this.m_Parcours = p_Parcours;
		this.m_LayoutResId = p_LayoutResId;
	}

	public View getView(int p_Position, View p_ConvertView, ViewGroup p_Parent) {

		View view = p_ConvertView;
		ParcoursHolder contenant;

		if (view == null) {
			LayoutInflater inflater = ((Activity) m_Context)
					.getLayoutInflater();
			contenant = new ParcoursHolder();

			view = inflater.inflate(m_LayoutResId, p_Parent, false);
			contenant.ligne = (TextView) view.findViewById(R.id.txtLigne);
			view.setTag(contenant);
		} else {
			contenant = (ParcoursHolder) view.getTag();
		}

		ParcoursItem item = (ParcoursItem) this.m_Parcours.get(p_Position);

		if (item.getDate() != null && item.getAdresse() != null) {
			String strLigne = item.getDate() + " - " + item.getAdresse();
			contenant.ligne.setText(strLigne);
		}

		return view;
	}

	private static class ParcoursHolder {
		public TextView ligne;
	}
}
