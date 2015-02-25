package net.wasitec.sieveanalisis.servicios;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.wasitec.sieveanalisis.bean.VariablesGlobales;

import android.annotation.SuppressLint;
import android.util.Log;
import android.widget.EditText;

public class Calcular {
	private List<Double> diferencias = new ArrayList<Double>();

	public double sumarPeso(List<EditText> sieveEdit, List<EditText> tareEdit) {
		double sumatoria = 0;
		for (int i = 0; i < sieveEdit.size(); i++) {
			double diferencia = Double.parseDouble(sieveEdit.get(i).getText()
					.toString())
					- Double.parseDouble(tareEdit.get(i).getText().toString());
			sumatoria = sumatoria + diferencia;
			diferencias.add(diferencia);
		}
		sumatoria = Math.round(sumatoria * 100.0) / 100.0;
		return sumatoria;

	}

	public double[] pesoSieve(List<EditText> sieveEdit) {
		double[] result = new double[sieveEdit.size()];
		for (int i = 0; i < sieveEdit.size(); i++) {
			result[i] = Double.parseDouble(sieveEdit.get(i).getText()
					.toString());
		}
		return result;
	}

	public double[] resultadoFraccion() {
		double[] result = new double[diferencias.size()];
		for (int i = 0; i < diferencias.size(); i++) {
			result[i] = diferencias.get(i);
			result[i] = Math.round(result[i] * 1000.0) / 1000.0;
		}
		return result;
	}

	public double[] resultadoFraccionPorcentaje(double[] fracciones,
			double sumaTotal) {
		double[] result = new double[diferencias.size()];
		for (int i = 0; i < fracciones.length; i++) {
			result[i] = (fracciones[i] / sumaTotal) * 100;
			result[i] = Math.round(result[i] * 1000.0) / 1000.0;
		}
		return result;
	}

	@SuppressLint("SimpleDateFormat")
	public String fechaActual() {
		Date ahora = new Date();
		SimpleDateFormat formateador = new SimpleDateFormat("dd-MM-yyyy");
		return formateador.format(ahora);
	}

	public double[] resultadoAcumulado(double[] resultadoFraccion) {
		double[] result = new double[resultadoFraccion.length];

		for (int i = 0; i < resultadoFraccion.length; i++) {
			if (i > 0) {
				result[i] = resultadoFraccion[i] + result[i - 1];
				result[i] = Math.round(result[i] * 1000.0) / 1000.0;
			} else {
				result[i] = resultadoFraccion[i];
				result[i] = Math.round(result[i] * 1000.0) / 1000.0;
			}
		}
		return result;
	}

	public String[] sievePicker(List<EditText> sievePicker, int tamaño) {
		int tam = (sievePicker.size()) + 1;
		String[] result = new String[tamaño + 1];
		result[0] = "botton";
		for (int i = 0; i < tamaño - 1; i++) {
			Log.e("sievePicker " + i, sievePicker.get(i).getText().toString());
			result[i + 1] = sievePicker.get(i).getText().toString();
		}
		result[result.length - 1] = " ";
		return result;
	}

	public boolean sumaValida(double suma) {
		double pesoInicial = Double.parseDouble(new VariablesGlobales()
				.getDatos().getPesoInicial());
		Log.e("suma", "" + suma);
		Log.e("pesoInicial", "" + pesoInicial);
		if (suma <= pesoInicial && suma * 100 / pesoInicial >= 98)
			return true;

		return false;
	}

	public boolean validarNoVacios(List<EditText> tareEdit,
			List<EditText> sieveEdit, List<EditText> sievePicker) {
		boolean resultado = true;
		List<EditText> tempo = sievePicker;
		tempo.add(sievePicker.get(0));
		for (int j = 0; j < tareEdit.size(); j++) {
			if (tareEdit.get(j).getText().toString().equals("")
					|| sieveEdit.get(j).getText().toString().equals("")
					|| tempo.get(j).getText().toString().equals("")) {
				resultado = false;
				break;
			}
		}
		return resultado;
	}

	public boolean validarSieveMayorTare(List<EditText> sieveEdit,
			List<EditText> tareEdit) {
		boolean resultado = true;
		for (int i = 0; i < sieveEdit.size(); i++) {
			if (Double.parseDouble(sieveEdit.get(i).getText().toString()) < Double
					.parseDouble(tareEdit.get(i).getText().toString())) {
				resultado = false;
				break;
			}
		}
		return resultado;
	}
}
