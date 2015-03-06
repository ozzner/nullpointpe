/**
 * 
 */
package com.apprade.adapter;

import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.Toast;

import com.apprade.helper.Helper_SubRoutines;

/**
 * @author Renzo Santillán
 *
 */
public class Adapter_Dialog_Fragment extends DialogFragment implements OnDateSetListener
{
	private static String sFecha ;
	private Helper_SubRoutines oRoutine;
	public static String sFechaMMM;
	/**
	 * @return the sFecha
	 */
	public String getsFecha() {
		return sFecha;
	}

	/**
	 * @param sFecha the sFecha to set
	 */
	public void setsFecha(String sFecha) {
		Adapter_Dialog_Fragment.sFecha = sFecha;
	}

	
	
	public Adapter_Dialog_Fragment() {
		oRoutine = new Helper_SubRoutines();
	}

	 @Override
	    public Dialog onCreateDialog(Bundle savedInstanceState) {
	        // Use the current date as the default date in the picker
	        final Calendar c = Calendar.getInstance();
	        int year = c.get(Calendar.YEAR);
	        int month = c.get(Calendar.MONTH);
	        int day = c.get(Calendar.DAY_OF_MONTH);

	        return new DatePickerDialog(getActivity(), this, year, month, day);
	    }

	@Override
	public void onDateSet(DatePicker view, int year, int monthOfYear,
			int dayOfMonth) {
		sFecha = (year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
		String sFechaMMM = oRoutine.customDateConverter(sFecha,Helper_SubRoutines.TAG_FORMAT_DATE_MM, Helper_SubRoutines.TAG_FORMAT_DATE_MMM);
		sFecha = oRoutine.customDateConverter(sFecha, "yyyy-MM-dd", "yyyy-MM-dd");
		Toast.makeText(getActivity(), sFechaMMM, Toast.LENGTH_SHORT).show();
		setsFecha(sFecha);
	}

	
	
	
}
