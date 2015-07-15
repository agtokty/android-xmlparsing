package com.agtokty.hacettepexwaringeh;





import java.util.Calendar;

import javax.xml.parsers.*;
import org.w3c.dom.*;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import database.YemekDB;



import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener{
	
	Button Bogle   ,Bgun;
	private ProgressDialog pDialog;
	WebView WVshare;
	ImageView ivupdate;


	public static String PACKAGE_NAME;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setTitle(R.string.app_name);
		Bogle = (Button)findViewById(R.id.bogle);
		ivupdate =(ImageView)findViewById(R.id.ivupdate); 
//		Bgun = (Button)findViewById(R.id.bgun);
		
		Bogle.setOnClickListener(this);
		ivupdate.setOnClickListener(this);
//		Bgun.setOnClickListener(this);
		PACKAGE_NAME=getApplicationContext().getPackageName();
		
		
		SharedPreferences wmbPreference = PreferenceManager.getDefaultSharedPreferences(this);
		boolean isFirstRun = wmbPreference.getBoolean("FIRSTRUN", true);
		if (isFirstRun && isInternetAvailable())
		{
		    // Code to run once
		    SharedPreferences.Editor editor = wmbPreference.edit();
		    editor.putBoolean("FIRSTRUN", false);
		    editor.commit();
		    new LoadYemeks().execute();
		}
		
		/**********gunun yemekleri*************/
		
		TextView O1,O2,O3,O4,O5,Tvtarih;
		
		Calendar ci = Calendar.getInstance(); 
		int day_id = ci.get(Calendar.DAY_OF_WEEK);
		String day_name="";
		switch (day_id) {
		case Calendar.MONDAY:
			day_name="Pazartesi";
		    break;
		case Calendar.TUESDAY:
			day_name="Salı";
		    break;
		case Calendar.WEDNESDAY:
			day_name="Çarşamba";
		    break;
		case Calendar.THURSDAY:
			day_name="Perşembe";
		    break;
		case Calendar.FRIDAY:
			day_name="Cuma";
		    break;
		case Calendar.SATURDAY:
			day_name="Cumartesi";
			break;
		case Calendar.SUNDAY:
			day_name="Pazar";
		    break;
		}
		
		
		
		int g = ci.get(Calendar.DAY_OF_MONTH);
		
		String str=g+"";
		if(g<10)
			 str = "0"+g;
		
		
		/** EN SON BURASI DEGISTI 05/10/2014 **/
		int a = ci.get(Calendar.MONTH);
		Log.i("TAG", "AY NUMARASI: "+a);
		String str2 = null;
		
		a=a+1;
		
		if(a<10){
			
			str2 = "0"+a;
			
		}
		else str2=a+"";

		
		String CiDateTime = "" + str+  "." + 
			    str2 + "." +
			    ci.get(Calendar.YEAR)+""  ;
		
		

		
		O1 = (TextView)findViewById(R.id.tv1);
		O2 = (TextView)findViewById(R.id.tv2);
		O3 = (TextView)findViewById(R.id.tv3);
		O4 = (TextView)findViewById(R.id.tv4);
		O5 = (TextView)findViewById(R.id.tv5);
		Tvtarih= (TextView)findViewById(R.id.tvtarih);
		Tvtarih.setText(CiDateTime+" "+day_name);
		
		YemekDB ydb = new YemekDB(MainActivity.this);
		ydb.open();
		Cursor c = ydb.getList();
		
		
		Log.i("tag", CiDateTime);
		
		
		
		while(c.moveToNext()){
			String date = c.getString(0);
			String[] parts = date.split(" ");
			Log.i("tag", parts[0] + " -----------  "+parts[1]);
			if(CiDateTime.equals(parts[0])){
				O1.setText(c.getString(1));
				O2.setText(c.getString(2));
				O3.setText(c.getString(3));
				O4.setText(c.getString(4));
				O5.setText("Kalori : " + c.getString(5));
				break;
			}
		}
		ydb.close();
		c.close();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.update:
			if(isInternetAvailable()){
				new LoadYemeks().execute();
			}
			else
				Toast.makeText(MainActivity.this, R.string.nonet, Toast.LENGTH_SHORT).show();
			break;
		case R.id.share:
			Intent sendIntent = new Intent();
			sendIntent.setAction(Intent.ACTION_SEND);
			sendIntent.putExtra(Intent.EXTRA_TEXT, R.string.title +
					" : https://play.google.com/store/apps/details?id="+PACKAGE_NAME);
			sendIntent.setType("text/plain");
			startActivity(sendIntent);
			break;
		case R.id.about:
			AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
			 
	        // Setting Dialog Title
	        alertDialog.setTitle(R.string.about);
	        
	        alertDialog.setIcon(R.drawable.about);
	        
	        // Setting Dialog Message
	        alertDialog.setMessage(R.string.appabout);
	 
	        // Showing Alert Message
	        alertDialog.show();
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bogle:
			YemekDB ydb = new YemekDB(MainActivity.this);
			ydb.open();
			Cursor c = ydb.getList();
			if(c.getCount()>0 && c!=null){
				ydb.close();
				c.close();
			Intent ogle = new Intent(this,YemekListesi.class);
			ogle.putExtra("zaman", "ogle");
			startActivity(ogle);
			}
			else
				showalertUpdate();
			break;
		case R.id.ivupdate:
			if(isInternetAvailable()){
				new LoadYemeks().execute();
			}
			else
				Toast.makeText(MainActivity.this, R.string.nonet, Toast.LENGTH_SHORT).show();
//		case R.id.bgun:
//				Intent i = new Intent(this, GununYemekleri.class);
//				startActivity(i);
//			break;
		default:
			break;
		}
		
	}
	
	public boolean isInternetAvailable(){
		  try {
		      ConnectivityManager nInfo = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		      nInfo.getActiveNetworkInfo().isConnectedOrConnecting();

		      Log.i("TAG", "Net avail:"
		              + nInfo.getActiveNetworkInfo().isConnectedOrConnecting());

		      ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		      NetworkInfo netInfo = cm.getActiveNetworkInfo();
		      if (netInfo != null && netInfo.isConnectedOrConnecting()) {
		          Log.i("TAG", "Network is available");
		          return true;
		      } else {
		          Log.i("TAG", "Network is not available");
		          return false;
		      }

		  } catch (Exception e) {
		      return false;
		  }
	}
	
	public void updateXMLdata() {

		JSONParser jParser = new JSONParser();
		YemekDB ydb = new YemekDB(MainActivity.this);
		ydb.open();
		ydb.recreate();
		
		


		String url = "http://www.sksdb.hacettepe.edu.tr/YemekListesi.xml";   
        try
        {
            DocumentBuilderFactory f = 
                    DocumentBuilderFactory.newInstance();
            DocumentBuilder b = f.newDocumentBuilder();
            Document doc = b.parse(url);
 
            doc.getDocumentElement().normalize();
            Log.i("tag", "Root element: " +  doc.getDocumentElement().getNodeName());
       
            NodeList nList = doc.getElementsByTagName("gun");
            
        	System.out.println("----------------------------");
         
        	for (int temp = 0; temp < nList.getLength(); temp++) {
         
        		Node nNode = nList.item(temp);
         
        		System.out.println("\nCurrent Element :" + nNode.getNodeName());
         
        		if (nNode.getNodeType() == Node.ELEMENT_NODE) {
         
        			Element eElement = (Element) nNode;
         
        			//System.out.println("Staff id : " + eElement.getAttribute("id"));
        			String tarih =  eElement.getElementsByTagName("tarih").item(0).getTextContent();
        			Log.i("tag","tarih  : "+ tarih);
        			String y1 =  eElement.getElementsByTagName("yemek").item(0).getTextContent();
        			String y2 =  eElement.getElementsByTagName("yemek").item(1).getTextContent();
        			String y3 =  eElement.getElementsByTagName("yemek").item(2).getTextContent();
        			String y4 =  eElement.getElementsByTagName("yemek").item(3).getTextContent();
        			String kalori =  eElement.getElementsByTagName("kalori").item(0).getTextContent();
        			Log.i("tag","y1  : "+ y1);
        			Log.i("tag","y2  : "+ y2);
        			Log.i("tag","y3  : "+ y3);
        			Log.i("tag","y4  : "+ y4);
        			Log.i("tag","kalori  : "+ kalori);
        			
					ydb.open();
					ydb.add(tarih, y1, y2, y3, y4,kalori);
					ydb.close();
        				
         
        		}
        	}
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }	

				
	}	
	
	public class LoadYemeks extends AsyncTask<Void, Void, Boolean> {

		@Override
	protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(MainActivity.this);
			CharSequence str = getText(R.string.updating);
			pDialog.setMessage(str);
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		@Override
		protected Boolean doInBackground(Void... arg0) {
			updateXMLdata();
			Intent intent = getIntent();
			finish();
			startActivity(intent);
			return null;

		}


		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			pDialog.dismiss();
			//updateList();
		}
	}
	

    

	@SuppressWarnings("deprecation")
	public void showalertUpdate(){
		AlertDialog alertDialog = new AlertDialog.Builder(
				MainActivity.this).create();
		
		// Setting Dialog Title
		alertDialog.setTitle("Uyarı !");

		// Setting Dialog Message
		
		alertDialog.setMessage(getText(R.string.updatealert));

		// Setting Icon to Dialog
		alertDialog.setIcon(R.drawable.dbupdate);

		// Setting OK Button
		alertDialog.setButton(getText(R.string.okey),
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog,	int which) {
						
					}
				});
		
		// Showing Alert Message
		alertDialog.show();
	}
}
