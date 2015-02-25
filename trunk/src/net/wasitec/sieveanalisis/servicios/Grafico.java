package net.wasitec.sieveanalisis.servicios;

import net.wasitec.sieveanalisis.bean.VariablesGlobales;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.BarChart;
import org.achartengine.chart.LineChart;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.SeriesSelection;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

public class Grafico {
	private GraphicalView mChart;
	private double[] income;
	private double[] expense;
	private String[] mMonth;

	public void openChart(LinearLayout chartContainer, final Context context) {
		mMonth = new VariablesGlobales().getMalla().getPicker();

		income = new VariablesGlobales().getMalla().getAcumulado();
		expense = new VariablesGlobales().getMalla().getFraccionPorcentaje();
		// comenzarEnCero();
		double Y = calcularY();// 50 o 100 si sobrepasa el 50%
		//
		int[] x = new int[income.length];
		for (int i = 0; i < x.length; i++) {
			x[i] = i;
		}
		// Creating an XYSeries for Income
		XYSeries incomeSeries = new XYSeries("Q3[%]", 1);
		// Creating an XYSeries for Expense
		XYSeries expenseSeries = new XYSeries("P3[%]", 0);
		// Adding data to Income and Expense Series
		for (int i = 0; i < x.length; i++) {
			incomeSeries.add(x[i], income[i]);
			expenseSeries.add(x[i], expense[i]);
		}

		// Creating a dataset to hold each series
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		// Adding Expense Series to dataset
		dataset.addSeries(0, expenseSeries);
		// Adding Income Series to the dataset
		dataset.addSeries(1, incomeSeries);

		// Creating XYSeriesRenderer to customize pe[%]Series
		XYSeriesRenderer expenseRenderer = new XYSeriesRenderer();
		expenseRenderer.setColor((Color.rgb(66, 132, 189)));
		expenseRenderer.setPointStyle(PointStyle.CIRCLE);
		expenseRenderer.setFillPoints(true);
		expenseRenderer.setLineWidth(2);
		expenseRenderer.setDisplayChartValues(true);
		expenseRenderer.setChartValuesTextSize(20);
		// Creating XYSeriesRenderer to customize incomeSeries
		XYSeriesRenderer incomeRenderer = new XYSeriesRenderer();
		incomeRenderer.setColor(Color.RED);
		incomeRenderer.setPointStyle(PointStyle.CIRCLE);
		incomeRenderer.setFillPoints(true);
		incomeRenderer.setLineWidth(2);
		incomeRenderer.setDisplayChartValues(true);
		incomeRenderer.setChartValuesTextSize(0);
		// Creating a XYMultipleSeriesRenderer to customize the whole chart
		XYMultipleSeriesRenderer multiRenderer = new XYMultipleSeriesRenderer(2);
		multiRenderer.setXLabels(0);
		multiRenderer.setMarginsColor(Color.rgb(191, 191, 191));
		multiRenderer.setChartTitle("P3[%] vs Q3[%]");
		multiRenderer.setXTitle("X[mm]");
		multiRenderer.setYLabels(10);
		multiRenderer.setYTitle("P3[%]", 0);
		multiRenderer.setYTitle("Q3[%]", 1);
		multiRenderer.setYAxisMin(0, 0);
		multiRenderer.setYAxisMax(Y, 0);
		multiRenderer.setYAxisMin(0, 1);
		multiRenderer.setYAxisMax(100, 1);
		multiRenderer.setYLabelsColor(0, Color.BLACK);
		multiRenderer.setYLabelsColor(1, Color.BLACK);
		multiRenderer.setYAxisAlign(Align.LEFT, 0);
		multiRenderer.setYAxisAlign(Align.RIGHT, 1);
		multiRenderer.setYLabelsAlign(Align.LEFT, 0);
		multiRenderer.setYLabelsAlign(Align.RIGHT, 1);
		multiRenderer.setZoomButtonsVisible(true);
		multiRenderer.setBarSpacing(0.5);
		multiRenderer.setLegendHeight(1);
		multiRenderer.setFitLegend(true);
		multiRenderer.setAxesColor(Color.LTGRAY);
		multiRenderer.setLabelsColor(Color.BLACK);
		multiRenderer.setXLabelsColor(Color.BLACK);
		multiRenderer.setLabelsTextSize(19);
		multiRenderer.setLegendTextSize(19);
		multiRenderer.setShowGrid(true);
		multiRenderer.setAxisTitleTextSize(19);
		multiRenderer.setChartTitleTextSize(18);
		multiRenderer.setPointSize(8);
		for (int i = 0; i < x.length; i++) {
			multiRenderer.addXTextLabel(i, mMonth[i]);
		}

		multiRenderer.addSeriesRenderer(expenseRenderer);
		multiRenderer.addSeriesRenderer(incomeRenderer);
		String[] types = new String[] { BarChart.TYPE, LineChart.TYPE };

		mChart = (GraphicalView) ChartFactory.getCombinedXYChartView(context,
				dataset, multiRenderer, types);

		multiRenderer.setClickEnabled(true);
		multiRenderer.setSelectableBuffer(10);
		mChart.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				SeriesSelection seriesSelection = mChart
						.getCurrentSeriesAndPoint();

				if (seriesSelection != null) {
					int seriesIndex = seriesSelection.getSeriesIndex();
					String selectedSeries = " ";
					if (seriesIndex == 0)
						selectedSeries = "Q3[%]";
					else
						selectedSeries = "p3[%]";
					String month = mMonth[(int) seriesSelection.getXValue()];
					int amount = (int) seriesSelection.getValue();
					Toast.makeText(context,
							selectedSeries + " en " + month + " : " + amount,
							Toast.LENGTH_SHORT).show();
				}
			}

		});

		// Adding the Combined Chart to the LinearLayout
		chartContainer.addView(mChart);
	}

	private double calcularY() {
		double resultado = 50;
		for (int i = 0; i < expense.length; i++) {
			Log.e("expense[i]", "" + expense[i]);
			if (expense[i] > 50) {
				resultado = 100;
				break;
			}
		}
		return resultado;
	}

	private void comenzarEnCero() {
		double[] incomeTemporal = new double[new VariablesGlobales().getMalla()
				.getAcumulado().length + 1];
		double[] expenseTemporal = new double[new VariablesGlobales()
				.getMalla().getFraccionPorcentaje().length + 1];
		incomeTemporal[0] = 0;
		expenseTemporal[0] = 0;
		for (int i = 0; i < new VariablesGlobales().getMalla().getAcumulado().length; i++) {
			incomeTemporal[i + 1] = new VariablesGlobales().getMalla()
					.getAcumulado()[i];
			expenseTemporal[i + 1] = new VariablesGlobales().getMalla()
					.getFraccionPorcentaje()[i];
		}

		income = incomeTemporal;
		expense = expenseTemporal;
	}
}
