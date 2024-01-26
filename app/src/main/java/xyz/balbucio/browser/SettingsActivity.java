package xyz.balbucio.browser;

import android.animation.*;
import android.app.*;
import android.app.Activity;
import android.content.*;
import android.content.SharedPreferences;
import android.content.res.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.media.*;
import android.net.*;
import android.os.*;
import android.text.*;
import android.text.style.*;
import android.util.*;
import android.view.*;
import android.view.View.*;
import android.view.animation.*;
import android.webkit.*;
import android.widget.*;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import androidx.annotation.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.google.android.material.appbar.AppBarLayout;
import java.io.*;
import java.text.*;
import java.util.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.*;
import org.json.*;

public class SettingsActivity extends AppCompatActivity {
	
	private Toolbar _toolbar;
	private AppBarLayout _app_bar;
	private CoordinatorLayout _coordinator;
	private HashMap<String, Object> sett = new HashMap<>();
	
	private ArrayList<String> mecanismo = new ArrayList<>();
	
	private ScrollView vscroll1;
	private LinearLayout linear1;
	private TextView textview4;
	private Switch switch1;
	private Spinner spinner1;
	private TextView textview2;
	private Switch switch2;
	private Switch switch3;
	private TextView textview3;
	private Switch switch4;
	private TextView textview5;
	private Switch switch5;
	private Switch switch6;
	
	private SharedPreferences shrd;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.settings);
		initialize(_savedInstanceState);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		_app_bar = findViewById(R.id._app_bar);
		_coordinator = findViewById(R.id._coordinator);
		_toolbar = findViewById(R.id._toolbar);
		setSupportActionBar(_toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
		_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _v) {
				onBackPressed();
			}
		});
		vscroll1 = findViewById(R.id.vscroll1);
		linear1 = findViewById(R.id.linear1);
		textview4 = findViewById(R.id.textview4);
		switch1 = findViewById(R.id.switch1);
		spinner1 = findViewById(R.id.spinner1);
		textview2 = findViewById(R.id.textview2);
		switch2 = findViewById(R.id.switch2);
		switch3 = findViewById(R.id.switch3);
		textview3 = findViewById(R.id.textview3);
		switch4 = findViewById(R.id.switch4);
		textview5 = findViewById(R.id.textview5);
		switch5 = findViewById(R.id.switch5);
		switch6 = findViewById(R.id.switch6);
		shrd = getSharedPreferences("settings", Activity.MODE_PRIVATE);
		
		spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> _param1, View _param2, int _param3, long _param4) {
				final int _position = _param3;
				
			}
			
			@Override
			public void onNothingSelected(AdapterView<?> _param1) {
				
			}
		});
	}
	
	private void initializeLogic() {
		setTitle("Configurações");
		if (shrd.contains("apkpure")) {
			switch1.setChecked(Boolean.valueOf(shrd.getString("apkpure", "")));
		}
		else {
			shrd.edit().putString("apkpure", String.valueOf(true)).commit();
			switch1.setChecked(true);
		}
		if (shrd.contains("otherdownload")) {
			switch2.setChecked(Boolean.valueOf(shrd.getString("otherdownload", "")));
		}
		else {
			shrd.edit().putString("otherdownload", String.valueOf(true)).commit();
			switch2.setChecked(true);
		}
		if (shrd.contains("questiondownload")) {
			switch3.setChecked(Boolean.valueOf(shrd.getString("questiondownload", "")));
		}
		else {
			shrd.edit().putString("questiondownload", String.valueOf(true)).commit();
			switch3.setChecked(true);
		}
		if (shrd.contains("cache")) {
			switch4.setChecked(Boolean.valueOf(shrd.getString("cache", "")));
		}
		else {
			shrd.edit().putString("cache", String.valueOf(true)).commit();
			switch4.setChecked(true);
		}
		if (shrd.contains("desingbeta")) {
			switch6.setChecked(Boolean.valueOf(shrd.getString("desingbeta", "")));
		}
		else {
			shrd.edit().putString("desingbeta", String.valueOf(true)).commit();
			switch6.setChecked(true);
		}
		mecanismo.add("google.com");
		mecanismo.add("search.brave.com");
		spinner1.setAdapter(new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, mecanismo));
		if (shrd.getString("mecanismo", "").equals("search.brave.com")) {
			spinner1.setSelection((int)(1));
		}
		if (shrd.contains("desingsimples")) {
			switch5.setChecked(Boolean.valueOf(shrd.getString("desingsimples", "")));
		}
		else {
			shrd.edit().putString("desingsimples", String.valueOf(false)).commit();
			switch5.setChecked(false);
		}
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		shrd.edit().putString("apkpure", String.valueOf(switch1.isChecked())).commit();
		shrd.edit().putString("otherdownload", String.valueOf(switch2.isChecked())).commit();
		shrd.edit().putString("questiondownload", String.valueOf(switch3.isChecked())).commit();
		shrd.edit().putString("mecanismo", mecanismo.get((int)(spinner1.getSelectedItemPosition()))).commit();
		shrd.edit().putString("cache", String.valueOf(switch4.isChecked())).commit();
		shrd.edit().putString("desingsimples", String.valueOf(switch5.isChecked())).commit();
		shrd.edit().putString("desingbeta", String.valueOf(switch6.isChecked())).commit();
	}
	
	@Override
	public void onStop() {
		super.onStop();
		shrd.edit().putString("apkpure", String.valueOf(switch1.isChecked())).commit();
		shrd.edit().putString("otherdownload", String.valueOf(switch2.isChecked())).commit();
		shrd.edit().putString("questiondownload", String.valueOf(switch3.isChecked())).commit();
		shrd.edit().putString("cache", String.valueOf(switch4.isChecked())).commit();
		shrd.edit().putString("desingbeta", String.valueOf(switch6.isChecked())).commit();
	}
	
	@Deprecated
	public void showMessage(String _s) {
		Toast.makeText(getApplicationContext(), _s, Toast.LENGTH_SHORT).show();
	}
	
	@Deprecated
	public int getLocationX(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[0];
	}
	
	@Deprecated
	public int getLocationY(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[1];
	}
	
	@Deprecated
	public int getRandom(int _min, int _max) {
		Random random = new Random();
		return random.nextInt(_max - _min + 1) + _min;
	}
	
	@Deprecated
	public ArrayList<Double> getCheckedItemPositionsToArray(ListView _list) {
		ArrayList<Double> _result = new ArrayList<Double>();
		SparseBooleanArray _arr = _list.getCheckedItemPositions();
		for (int _iIdx = 0; _iIdx < _arr.size(); _iIdx++) {
			if (_arr.valueAt(_iIdx))
			_result.add((double)_arr.keyAt(_iIdx));
		}
		return _result;
	}
	
	@Deprecated
	public float getDip(int _input) {
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, _input, getResources().getDisplayMetrics());
	}
	
	@Deprecated
	public int getDisplayWidthPixels() {
		return getResources().getDisplayMetrics().widthPixels;
	}
	
	@Deprecated
	public int getDisplayHeightPixels() {
		return getResources().getDisplayMetrics().heightPixels;
	}
}