package xyz.balbucio.browser;

import android.Manifest;
import android.animation.*;
import android.app.*;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.*;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.media.*;
import android.net.*;
import android.net.Uri;
import android.os.*;
import android.os.Bundle;
import android.text.*;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.style.*;
import android.util.*;
import android.view.*;
import android.view.View;
import android.view.View.*;
import android.view.animation.*;
import android.webkit.*;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.*;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import androidx.annotation.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import java.io.*;
import java.io.InputStream;
import java.text.*;
import java.util.*;
import java.util.regex.*;
import org.json.*;

public class BrowserActivity extends AppCompatActivity {
	
	private LinearLayout linear1;
	private WebView webview1;
	private ProgressBar progressbar1;
	private LinearLayout linear3;
	private LinearLayout linear2;
	private LinearLayout linear5;
	private ImageView ssl;
	private EditText url;
	private ImageView imageview6;
	private ImageView imageview2;
	private LinearLayout linear4;
	private LinearLayout linear6;
	private ImageView imageview3;
	private ImageView imageview4;
	private ImageView imageview5;
	
	private Intent jaooakqnqkziaj;
	// content;
	private Intent intenter = new Intent();
	private SharedPreferences shrd;
	private Intent intent = new Intent();
	private AlertDialog.Builder pop;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.browser);
		initialize(_savedInstanceState);
		
		if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED
		|| ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
			ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1000);
		} else {
			initializeLogic();
		}
	}
	
	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (requestCode == 1000) {
			initializeLogic();
		}
	}
	
	private void initialize(Bundle _savedInstanceState) {
		linear1 = findViewById(R.id.linear1);
		webview1 = findViewById(R.id.webview1);
		webview1.getSettings().setJavaScriptEnabled(true);
		webview1.getSettings().setSupportZoom(true);
		progressbar1 = findViewById(R.id.progressbar1);
		linear3 = findViewById(R.id.linear3);
		linear2 = findViewById(R.id.linear2);
		linear5 = findViewById(R.id.linear5);
		ssl = findViewById(R.id.ssl);
		url = findViewById(R.id.url);
		imageview6 = findViewById(R.id.imageview6);
		imageview2 = findViewById(R.id.imageview2);
		linear4 = findViewById(R.id.linear4);
		linear6 = findViewById(R.id.linear6);
		imageview3 = findViewById(R.id.imageview3);
		imageview4 = findViewById(R.id.imageview4);
		imageview5 = findViewById(R.id.imageview5);
		shrd = getSharedPreferences("settings", Activity.MODE_PRIVATE);
		pop = new AlertDialog.Builder(this);
		
		webview1.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageStarted(WebView _param1, String _param2, Bitmap _param3) {
				final String _url = _param2;
				ssl.setImageResource(R.drawable.ic_lock_open_white);
				progressbar1.setProgress((int)webview1.getProgress());
				if (webview1.getUrl().contains("https://")) {
					ssl.setImageResource(R.drawable.ic_lock_white);
					SketchwareUtil.showMessage(getApplicationContext(), "A conexão é segura!");
				}
				super.onPageStarted(_param1, _param2, _param3);
			}
			
			@Override
			public void onPageFinished(WebView _param1, String _param2) {
				final String _url = _param2;
				if (webview1.getUrl().contains("https://")) {
					ssl.setImageResource(R.drawable.ic_lock_white);
				}
				else {
					ssl.setImageResource(R.drawable.ic_lock_open_white);
				}
				if (FileUtil.isExistFile(FileUtil.getPackageDataDir(getApplicationContext()).concat("/History"))) {
					FileUtil.writeFile(FileUtil.getPackageDataDir(getApplicationContext()).concat("/History"), FileUtil.readFile(FileUtil.getPackageDataDir(getApplicationContext()).concat("/History")).concat("-/-/-".concat(_url)));
				}
				else {
					FileUtil.writeFile(FileUtil.getPackageDataDir(getApplicationContext()).concat("/History"), _url);
				}
				url.setText(_url);
				progressbar1.setVisibility(View.GONE);
				webview1.setDownloadListener(new DownloadListener() {
					public void onDownloadStart(String url, String userAgent,  String contentDisposition, String mimetype,                 long contentLength) {    
						
						if (shrd.contains("questiondownload")) {
							if (Boolean.valueOf(shrd.getString("questiondownload", ""))) {
								pop.setTitle("Download");
								pop.setIcon(R.drawable.ic_get_app_black);
								pop.setMessage("O site quer executar um Download, deseja continuar e baixar o Arquivo? Lembre-se, tome cuidado com o que baixa!");
								pop.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface _dialog, int _which) {
										if (Boolean.valueOf(shrd.getString("otherdownload", ""))) {
											webview1.setDownloadListener(new DownloadListener() {
												public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
													Intent intent = new Intent(Intent.ACTION_VIEW);
													intent.setData(Uri.parse(url));
													startActivity (intent);
												}
											});
										}
										else {
											webview1.setDownloadListener(new DownloadListener() {
												public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
													DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
													String cookies = CookieManager.getInstance().getCookie(url);
													request.addRequestHeader("cookie", cookies);
													request.addRequestHeader("User-Agent", userAgent);
													request.setDescription("Downloading file...");
													request.setTitle(URLUtil.guessFileName(url, contentDisposition, mimetype));
													request.allowScanningByMediaScanner(); request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
													java.io.File aatv = new java.io.File(Environment.getExternalStorageDirectory().getPath() + "//balbBrowser/Downloads");
													if(!aatv.exists()){if (!aatv.mkdirs()){ Log.e("TravellerLog ::","Problem creating Image folder");}} request.setDestinationInExternalPublicDir("//balbBrowser/Downloads", URLUtil.guessFileName(url, contentDisposition, mimetype));
													DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
													manager.enqueue(request);
													showMessage("Downloading File....");
													//Notif if success
													BroadcastReceiver onComplete = new BroadcastReceiver() {
														public void onReceive(Context ctxt, Intent intent) {
															showMessage("Download Complete!");
															unregisterReceiver(this);
														}};
													registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
												}
											});
											webview1.getSettings().setAppCacheMaxSize(5*1024*1024);
											webview1.getSettings().setAppCachePath(getApplicationContext().getCacheDir().getAbsolutePath());
											webview1.getSettings().setAllowFileAccess(true);
											webview1.getSettings().setAppCacheEnabled(true);
											webview1.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
											webview1.getSettings().setLoadWithOverviewMode(true);
											webview1.getSettings().setUseWideViewPort(true);
											webview1.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
											webview1.getSettings().setDomStorageEnabled(true);
											webview1.getSettings().setSaveFormData(true);
											startActivity(new android.content.Intent(android.app.DownloadManager.ACTION_VIEW_DOWNLOADS));
										}
									}
								});
								pop.setNegativeButton("Não", new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface _dialog, int _which) {
										
									}
								});
								pop.create().show();
							}
							else {
								if (Boolean.valueOf(shrd.getString("otherdownload", ""))) {
									webview1.setDownloadListener(new DownloadListener() {
										public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
											Intent intent = new Intent(Intent.ACTION_VIEW);
											intent.setData(Uri.parse(url));
											startActivity (intent);
										}
									});
								}
								else {
									webview1.setDownloadListener(new DownloadListener() {
										public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
											DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
											String cookies = CookieManager.getInstance().getCookie(url);
											request.addRequestHeader("cookie", cookies);
											request.addRequestHeader("User-Agent", userAgent);
											request.setDescription("Downloading file...");
											request.setTitle(URLUtil.guessFileName(url, contentDisposition, mimetype));
											request.allowScanningByMediaScanner(); request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
											java.io.File aatv = new java.io.File(Environment.getExternalStorageDirectory().getPath() + "/balbBrowser/Downloads");
											if(!aatv.exists()){if (!aatv.mkdirs()){ Log.e("TravellerLog ::","Problem creating Image folder");}} request.setDestinationInExternalPublicDir("/balbBrowser/Downloads", URLUtil.guessFileName(url, contentDisposition, mimetype));
											DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
											manager.enqueue(request);
											showMessage("Downloading File....");
											//Notif if success
											BroadcastReceiver onComplete = new BroadcastReceiver() {
												public void onReceive(Context ctxt, Intent intent) {
													showMessage("Download Complete!");
													unregisterReceiver(this);
												}};
											registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
										}
									});
									webview1.getSettings().setAppCacheMaxSize(5*1024*1024);
									webview1.getSettings().setAppCachePath(getApplicationContext().getCacheDir().getAbsolutePath());
									webview1.getSettings().setAllowFileAccess(true);
									webview1.getSettings().setAppCacheEnabled(true);
									webview1.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
									webview1.getSettings().setLoadWithOverviewMode(true);
									webview1.getSettings().setUseWideViewPort(true);
									webview1.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
									webview1.getSettings().setDomStorageEnabled(true);
									webview1.getSettings().setSaveFormData(true);
									startActivity(new android.content.Intent(android.app.DownloadManager.ACTION_VIEW_DOWNLOADS));
								}
							}
						}
						else {
							shrd.edit().putString("questiondownload", String.valueOf(true)).commit();
							shrd.edit().putString("otherdownload", String.valueOf(true)).commit();
							pop.setTitle("Download");
							pop.setIcon(R.drawable.ic_get_app_black);
							pop.setMessage("O site quer executar um Download, deseja continuar e baixar o Arquivo? Lembre-se, tome cuidado com o que baixa!");
							pop.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface _dialog, int _which) {
									webview1.setDownloadListener(new DownloadListener() {
										public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
											DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
											String cookies = CookieManager.getInstance().getCookie(url);
											request.addRequestHeader("cookie", cookies);
											request.addRequestHeader("User-Agent", userAgent);
											request.setDescription("Downloading file...");
											request.setTitle(URLUtil.guessFileName(url, contentDisposition, mimetype));
											request.allowScanningByMediaScanner(); request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
											java.io.File aatv = new java.io.File(Environment.getExternalStorageDirectory().getPath() + "/balbBrowser/Downloads");
											if(!aatv.exists()){if (!aatv.mkdirs()){ Log.e("TravellerLog ::","Problem creating Image folder");}} request.setDestinationInExternalPublicDir("/balbBrowser/Downloads", URLUtil.guessFileName(url, contentDisposition, mimetype));
											DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
											manager.enqueue(request);
											showMessage("Downloading File....");
											//Notif if success
											BroadcastReceiver onComplete = new BroadcastReceiver() {
												public void onReceive(Context ctxt, Intent intent) {
													showMessage("Download Complete!");
													unregisterReceiver(this);
												}};
											registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
										}
									});
								}
							});
							pop.setNegativeButton("Não", new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface _dialog, int _which) {
									
								}
							});
							pop.create().show();
						}
						   
						 } });
				super.onPageFinished(_param1, _param2);
			}
		});
		
		ssl.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				intenter.setClass(getApplicationContext(), ConsoleActivity.class);
				startActivity(intenter);
			}
		});
		
		url.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
				final String _charSeq = _param1.toString();
				if (_charSeq.contains("\n")) {
					if (_charSeq.contains(".") || _charSeq.contains("http")) {
						webview1.loadUrl(url.getText().toString());
					}
					else {
						if (url.getText().toString().contains("https://") || url.getText().toString().contains("http://")) {
								webview1.loadUrl(url.getText().toString());
						}
						else {
								webview1.loadUrl("https://www.google.com/search?ie=UTF-8&client=ms-android-samsung&source=android-browser&q=".concat(url.getText().toString()));
						}
					}
				}
				if (_charSeq.contains("\n") && _charSeq.contains(".")) {
					webview1.loadUrl(url.getText().toString());
				}
			}
			
			@Override
			public void beforeTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
				
			}
			
			@Override
			public void afterTextChanged(Editable _param1) {
				
			}
		});
		
		imageview6.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View _view) {
				if (url.getText().toString().contains("https://") || url.getText().toString().contains("http://")) {
						webview1.loadUrl(url.getText().toString());
				}
				else {
						webview1.loadUrl("https://www.google.com/search?ie=UTF-8&client=ms-android-samsung&source=android-browser&q=".concat(url.getText().toString()));
				}
				return true;
			}
		});
		
		imageview6.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				webview1.loadUrl(webview1.getUrl());
			}
		});
		
		imageview2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				
			}
		});
		
		imageview4.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				intenter.setClass(getApplicationContext(), SettingsActivity.class);
				startActivity(intenter);
			}
		});
		
		imageview5.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				intenter.setClass(getApplicationContext(), HistoryActivity.class);
				startActivity(intenter);
			}
		});
	}
	
	private void initializeLogic() {
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