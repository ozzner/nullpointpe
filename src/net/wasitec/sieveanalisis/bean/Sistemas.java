package net.wasitec.sieveanalisis.bean;

public class Sistemas {
	// private String[] iso = { "16.000 ", "14.000 ", "13.200 ", "12.500 ",
	// "11.200 ", "10.000 ", "9.500 ", "9.000 ", "8.000 ", "7.100 ",
	// "6.700 ", "6.300 ", "5.600 ", "5.000 ", "4.750 ", "4.500 ",
	// "4.000 ", "3.550 ", "3.350 ", "3.150 ", "2.800 ", "2.500 ",
	// "2.360 ", "2.240 ", "2.000 ", "1.800 ", "1.700 ", "1.600 ",
	// "1.400 ", "1.250 ", "1.180 ", "1.120 ", "1.000 ", "0.900", "0.850",
	// "0.800", "0.710", "0.630", "0.600", "0.560", "0.500", "0.450",
	// "0.425", "0.400", "0.355", "0.315", "0.300", "0.280", "0.250",
	// "0.224", "0.212", "0.200", "0.180", "0.160", "0.150", "0.140",
	// "0.125", "0.112", "0.106", "0.100", "0.090", "0.080", "0.075",
	// "0.071", "0.063", "0.056", "0.053", "0.050", "0.045", "0.040",
	// "0.038", "0.036", "0.032", "0.025", "0.020" };

	private String[] iso = { "0.020", "0.025", "0.032", "0.036", "0.038",
			"0.040", "0.045", "0.050", "0.053", "0.056", "0.063", "0.071",
			"0.075", "0.080", "0.090", "0.100", "0.106", "0.112", "0.125",
			"0.140", "0.150", "0.160", "0.180", "0.200", "0.212", "0.224",
			"0.250", "0.280", "0.300", "0.315", "0.355", "0.400", "0.425",
			"0.450", "0.500", "0.560", "0.600", "0.630", "0.710", "0.800",
			"0.850", "0.900", "1.000", "1.120", "1.180", "1.250", "1.400",
			"1.600", "1.700", "1.800", "2.000", "2.240", "2.360", "2.500",
			"2.800", "3.150", "3.350", "3.550", "4.000", "4.500", "4.750",
			"5.000", "5.600", "6.300", "6.700", "7.100", "8.000", "9.000",
			"9.500", "10.000", "11.200", "12.500", "13.200", "14.000", "16.000" };

	public String[] getIso() {
		return iso;
	}

	// private String[] astm = { "5/8", "0.530", "1/2", "7/16", "3/8", "5/16",
	// "0.265", "1/4", "3 1/2", "4", "5", "6", "7", "8", "10", "12", "14",
	// "16", "18", "20", "25", "30", "35", "40", "45", "50", "60", "70",
	// "80", "100", "120", "140", "170", "200", "230", "270", "325",
	// "400", "450", "500", "635" };

	private String[] astm = { "635", "500", "450", "400", "325", "270", "230",
			"200", "170", "140", "120", "100", "80", "70", "60", "50", "45",
			"40", "35", "30", "25", "20", "18", "16", "14", "12", "10", "8",
			"7", "6", "5", "4", "3 1/2", "1/4", "0.265", "5/16", "3/8", "7/16",
			"1/2", "0.530", "5/8" };

	public String[] getAstm() {
		return astm;
	}

	private String[] tyler = { "2 1/2", "3", "3 1/2", "4", "5", "6", "7", "8",
			"9", "10", "12", "14", "16", "20", "24", "28", "32", "35", "42",
			"48", "60", "65", "80", "100", "115", "150", "170", "200", "250",
			"270", "325", "400" };

	public String[] getTyler() {
		return tyler;
	}

	private String[] britanico = { "3", "3 1/2", "4", "5", "6", "7", "8", "10",
			"12", "14", "16", "18", "22", "25", "30", "36", "44", "52", "60",
			"72", "85", "100", "120", "150", "170", "200", "240", "300", "350",
			"400", "440" };

	public String[] getBritanico() {
		return britanico;
	}

}
