/**
 * 
 */
package com.apprade.adapter;

import java.util.ArrayList;

import com.apprade.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author renzo
 *
 */
public class Adapter_SpinnerNavActionBar extends BaseAdapter{

	private TextView tvTitulo;
	private ImageView ivIcon;
	private ArrayList<Adapter_SpinnerItem> arrSpinnerItem;	
	private Context contexto;
	
	
	public Adapter_SpinnerNavActionBar(Context ctx,ArrayList<Adapter_SpinnerItem> spinner) {
		this.arrSpinnerItem = spinner;
		this.contexto = ctx;
	}
	@Override
	public int getCount() {
		return arrSpinnerItem.size();
	}
	@Override
	public Object getItem(int position) {
		return arrSpinnerItem.get(position);
	}
	@Override
	public long getItemId(int position) {
		return position;
	}
	
	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		if (convertView == null) {
			LayoutInflater layInf = (LayoutInflater)contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = layInf.inflate(R.layout.adapter_spinner_actionbar,null);
		}
		
		
		ivIcon = (ImageView) convertView.findViewById(R.id.iv_icono);
		tvTitulo = (TextView) convertView.findViewById(R.id.tv_categoria);
		
		ivIcon.setImageResource(arrSpinnerItem.get(position).getIcon());
		ivIcon.setVisibility(View.GONE);
		
		tvTitulo.setText(arrSpinnerItem.get(position).getCategory());
		
		return convertView;
	}

	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		
		if (convertView == null) {
			LayoutInflater layInf = (LayoutInflater)contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = layInf.inflate(R.layout.adapter_spinner_actionbar,null);
		}
		
		ivIcon = (ImageView) convertView.findViewById(R.id.iv_icono);
		tvTitulo = (TextView) convertView.findViewById(R.id.tv_categoria);
		
		ivIcon.setImageResource(arrSpinnerItem.get(position).getIcon());
		tvTitulo.setText(arrSpinnerItem.get(position).getCategory());
		
		return convertView;
	}
	
	
	
	

}
