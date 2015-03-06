/**
 * 
 */
package com.apprade.adapter;

import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.apprade.R;
import com.apprade.entity.Entity_Comentario;
import com.apprade.entity.Entity_Usuario;
import com.apprade.helper.Helper_SubRoutines;

/**
 * @author Renzo Santillán
 *
 */
public class Adapter_ListView extends ArrayAdapter<Entity_Comentario> {

	private List<Entity_Comentario> oComments;
	private LayoutInflater layInf;
	private Helper_SubRoutines oRoutine;

	public Adapter_ListView(Context context,
			List<Entity_Comentario> oListaComment) {

		super(context, R.layout.adapter_list_row, oListaComment);
		this.oComments = oListaComment;
		this.layInf = LayoutInflater.from(context);
		oRoutine = new Helper_SubRoutines();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View itemView = convertView;
		if (itemView == null) {
			itemView = layInf.inflate(R.layout.adapter_list_row, parent, false);
		}

		Entity_Comentario currentComment = oComments.get(position);
		TextView tvUsuario = (TextView) itemView.findViewById(R.id.row_usuario);

		Entity_Usuario oUser = new Entity_Usuario();

		oUser = currentComment.getUsuario().get(position);
		String sNombre = oUser.getNombre();
		tvUsuario.setText(sNombre);

		TextView tvComment = (TextView) itemView
				.findViewById(R.id.row_comentario);
		tvComment.setText("" + currentComment.getMensaje());

		// Condition:
		TextView tvFecha = (TextView) itemView.findViewById(R.id.row_date1);
		TextView tvFecha2 = (TextView) itemView.findViewById(R.id.row_date2);
		tvFecha.setText(oRoutine.customDateConverter(currentComment.getFecha(),
				"yyyy-MM-dd HH:mm:ss", "yyyy-MMM-dd"));
		tvFecha2.setText(oRoutine.customDateConverter(
				currentComment.getFecha(), "yyyy-MM-dd HH:mm:ss",
				Helper_SubRoutines.TAG_FORMAT_TIME));
		return itemView;
	}
}
