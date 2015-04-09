package com.app.cartravel.utilitaires.ArrayAdapters;

import java.util.List;

import com.app.cartravel.R;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ParcoursAdapter extends ArrayAdapter<ParcoursItem>{
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
	
	public View getView(int p_Position, View p_ConvertView, ViewGroup p_Parent){
		ParcoursHolder contenant;
		View view = p_ConvertView;
		
		if(view == null){
			LayoutInflater inflater = ((Activity)m_Context).getLayoutInflater();
			contenant = new ParcoursHolder();
			
			view = inflater.inflate(m_LayoutResId, p_Parent, false);
			contenant.date = (TextView)view.findViewById(R.id.txtDate);
			contenant.adresse = (TextView)view.findViewById(R.id.txtAdresse);
			view.setTag(contenant);
		}
		else{
			contenant = (ParcoursHolder)view.getTag();
		}
		
		ParcoursItem item = (ParcoursItem)this.m_Parcours.get(p_Position);
		
		if(item.getDate() != null){
			contenant.date.setText(item.getDate());
		}
		if(item.getAdresse() != null){
			contenant.adresse.setText(item.getAdresse());
		}
		
		return view;		
	}
	
	private static class ParcoursHolder{
		TextView date;
		TextView adresse;
	}
}
