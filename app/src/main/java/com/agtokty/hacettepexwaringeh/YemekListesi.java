package com.agtokty.hacettepexwaringeh;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import database.YemekDB;



import android.app.ListActivity;
import android.app.ProgressDialog;
import android.database.Cursor;
import android.database.SQLException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;
	
public class YemekListesi extends ListActivity{
	
	// JSON IDS:
	private static final String TAG_GUN = "gun";
	private static final String TAG_BIR = "bir";
	private static final String TAG_IKI = "iki";
	private static final String TAG_UC = "uc";
	private static final String TAG_DORT = "dort";
	private static final String TAG_CAL = "cal";
	
	
	private ProgressDialog pDialog;
	TextView TVyemeklistadi;

	private ArrayList<HashMap<String, String>> mCommentList;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.yemeklist);
		TVyemeklistadi = (TextView)findViewById(R.id.tvyemeklistadi);

		new LoadComments().execute();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
	}

	public void updateDate() {

		mCommentList = new ArrayList<HashMap<String, String>>();

		try {

			YemekDB ydb = new YemekDB(YemekListesi.this);
			ydb.open();
			Cursor c = ydb.getList();
			// looping through all posts according to the json object returned
			while(c.moveToNext()) {
				
				
				// gets the content of each tag
				String gun = c.getString(0);
				String bir = c.getString(1);
				String iki = c.getString(2);
				String uc = c.getString(3);
				String dort = c.getString(4);
				String cal = c.getString(5);
				// creating new HashMap
				HashMap<String, String> map = new HashMap<String, String>();
				



				
				
					Log.i("tag","gun : "+gun);
					map.put(TAG_GUN, gun);
					map.put(TAG_BIR, bir);
					map.put(TAG_IKI, iki);
					map.put(TAG_UC, uc);
					map.put(TAG_DORT, dort);
					map.put(TAG_CAL, "Kalori : " +cal);

					// adding HashList to ArrayList
					mCommentList.add(map);
				
			   
			 }
		  
			ydb.close();
			c.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}	
	
	private void updateList() {
		ListAdapter adapter = new SimpleAdapter(this, mCommentList,
				R.layout.singleyemek, new String[] { TAG_GUN, TAG_BIR,
						TAG_IKI,TAG_UC,TAG_DORT,TAG_CAL }, new int[] { R.id.tvgun, R.id.tvbir,
						R.id.tviki,R.id.tvuc,R.id.tvdort,R.id.tvcal });

		setListAdapter(adapter);

	}	
	
	public class LoadComments extends AsyncTask<Void, Void, Boolean> {

			@Override
		protected void onPreExecute() {
				super.onPreExecute();
				pDialog = new ProgressDialog(YemekListesi.this);
				pDialog.setMessage("Yemek Listesi Yükleniyor...");
				pDialog.setIndeterminate(false);
				pDialog.setCancelable(true);
				pDialog.show();
			}

			@Override
			protected Boolean doInBackground(Void... arg0) {
				updateDate();
				return null;

			}


			@Override
			protected void onPostExecute(Boolean result) {
				super.onPostExecute(result);
				pDialog.dismiss();
				updateList();		
			}
	}
	

	
    public boolean compareDate (int year , int monthOfYear ,int dayOfMonth )
    {
        @SuppressWarnings("deprecation")
		Date inputDate = new Date(year, monthOfYear, dayOfMonth);
        Long inputTime = inputDate.getTime();
        Calendar calendar=Calendar.getInstance();
        @SuppressWarnings("deprecation")
        Date validDate = new Date(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), (calendar.get(Calendar.DAY_OF_MONTH)+30));
        Long validTime = validDate.getTime();
        if(validTime>inputTime){
        	//Log.i("tag"," önce");
        	return false;
            
        }
        else{
        	//Log.i("tag"," sonra");
        	return true;
        }
            
    }

    




}
