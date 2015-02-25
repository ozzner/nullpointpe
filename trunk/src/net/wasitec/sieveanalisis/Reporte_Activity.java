package net.wasitec.sieveanalisis;
import net.wasitec.sieveanalisis.servicios.Publicidad;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.viewpagerindicator.CirclePageIndicator;

public class Reporte_Activity extends SherlockFragmentActivity {
	ViewPager pager = null;

	Reporte_Pager_Adapter pagerAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.reporte_fragment);
		ColorDrawable colorDrawable = new ColorDrawable(Color.TRANSPARENT);
		getWindow().setBackgroundDrawable(colorDrawable);
		this.pager = (ViewPager) this.findViewById(R.id.pager);
		// Crea un adaptador con el fragment que se mostrara en el pager
		Reporte_Pager_Adapter adapter = new Reporte_Pager_Adapter(
				getSupportFragmentManager());
		adapter.addFragment(Reporte_Informe_Fragment.newInstance(0));
		adapter.addFragment(Reporte_Tabla_Fragment.newInstance(1));
		adapter.addFragment(Reporte_Grafico_Fragment.newInstance(2,
				getBaseContext()));
		CirclePageIndicator indicator = (CirclePageIndicator) findViewById(R.id.indicator);
		this.pager.setAdapter(adapter);
		iniciarPublicidad();
		indicator.setViewPager(pager);
		indicator.setSnap(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}

	public void iniciarPublicidad() {
		ImageButton imgBanner = (ImageButton) findViewById(R.id.btnPublicidad4);
		final Publicidad pub = new Publicidad(imgBanner);

		imgBanner.setImageBitmap(pub.getImg());
		imgBanner.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent i = new Intent(Intent.ACTION_VIEW);
				i.setData(Uri.parse(pub.getUrl()));
				startActivity(i);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.reporte_, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case android.R.id.home:
			onBackPressed();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
