package net.wasitec.sieveanalisis.servicios;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import net.wasitec.sieveanalisis.bean.PublicidadBean;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

public class Publicidad {

	private String url = null;
	private Bitmap bitmap_picture = null;
	private ImageView bmImage;

	public Publicidad(ImageView bmImage) {
		this.bmImage = bmImage;
		new JSONConnectar().execute();
	}

	public Bitmap getImg() {
		return bitmap_picture;
	}

	public String getUrl() {
		return url;
	}

	private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

		protected Bitmap doInBackground(String... urls) {
			String urldisplay = urls[0];
			Bitmap mIcon11 = null;
			try {
				InputStream in = new URL(urldisplay).openStream();
				mIcon11 = BitmapFactory.decodeStream(in);
			} catch (Exception e) {
				e.printStackTrace();
			}
			bitmap_picture = mIcon11;
			return mIcon11;
		}

		protected void onPostExecute(Bitmap result) {

			bmImage.setImageBitmap(result);
		}
	}

	class JSONConnectar extends AsyncTask<String, Void, String> {
		private JSONObject JSONresult = null;

		@Override
		protected void onPostExecute(String result) {
			String img = null;
			try {
				JSONArray jsonArray = JSONresult.getJSONArray("result");
				JSONObject objJson = jsonArray.getJSONObject(0);
				PublicidadBean objItem = new PublicidadBean();
				JSONObject objJSON2 = objJson.getJSONObject("Imagen");
				url = objJson.getString("URLANDROID");
				img = objJSON2.getString("url");
				objItem.setImg(img);
				objItem.setUrl(url);
				new DownloadImageTask().execute(img);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		@Override
		protected String doInBackground(String... params) {
			JSONresult = getJSONfromURL();
			return null;
		}

	}

	public static JSONObject getJSONfromURL() {
		InputStream is = null;
		String result = "";
		JSONObject jArray = null;
		try {
			JSONObject json = new JSONObject();
			HttpClient httpclient = new DefaultHttpClient();

			result = json.toString();
			StringEntity se = new StringEntity(result);

			HttpPost httppost = new HttpPost(
					"https://api.parse.com/1/functions/getrandom");

			httppost.setEntity(se);
			httppost.setHeader("X-Parse-Application-Id",
					"Zmnlh6sR7SjU1ilC44eXqxnCrSvJAiPiUVxr2MSe");
			httppost.setHeader("X-Parse-REST-API-Key",
					"172gHgTjdQDh9j0SxJ1XP0cEcsmj2tzalAjfYE7a");
			httppost.setHeader("Content-Type", "application/json");
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(is));
			String line = null;
			String res = "";
			while ((line = reader.readLine()) != null) {
				res += line;
			}
			is.close();
			result = res;
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			jArray = new JSONObject(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jArray;
	}
}
