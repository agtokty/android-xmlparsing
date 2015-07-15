package com.agtokty.hacettepexwaringeh;



import java.util.Calendar;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import database.YemekDB;

public class GununYemekleri extends Activity{
	
	TextView O1,O2,O3,O4,O5,Tvtarih;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gununyemekleri);
		
		
		
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
		
		int a = ci.get(Calendar.MONTH);
		String str2=a+"";
		if(a<9){
			a=a+1;
			str2 = "0"+a;
		}
		
		
		String CiDateTime = "" + str+  "." + 
			    str2 + "." +
			    ci.get(Calendar.YEAR)+""  ;
		
		
		Log.i("tag", CiDateTime);
		
		O1 = (TextView)findViewById(R.id.tv1);
		O2 = (TextView)findViewById(R.id.tv2);
		O3 = (TextView)findViewById(R.id.tv3);
		O4 = (TextView)findViewById(R.id.tv4);
		O5 = (TextView)findViewById(R.id.tv5);
		Tvtarih= (TextView)findViewById(R.id.tvtarih);
		Tvtarih.setText(CiDateTime+" "+day_name);
		
		YemekDB ydb = new YemekDB(GununYemekleri.this);
		ydb.open();
		Cursor c = ydb.getList();
		
		Log.i("tag", CiDateTime);
		
		while(c.moveToNext()){
			if(CiDateTime.equals(c.getString(0))){
				O1.setText(c.getString(1));
				O2.setText(c.getString(2));
				O3.setText(c.getString(3));
				O4.setText(c.getString(4));
				O5.setText(c.getString(5));
				break;
			}

		}
		ydb.close();
		c.close();
		
		
		
		setTitle(CiDateTime);
	}
}
