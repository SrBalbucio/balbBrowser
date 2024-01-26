package xyz.balbucio.browser;

import android.Manifest;
import android.animation.*;
import android.app.*;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.*;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
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
import android.os.Vibrator;
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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.annotation.*;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import de.hdodenhof.circleimageview.*;
import java.io.*;
import java.io.InputStream;
import java.text.*;
import java.util.*;
import java.util.regex.*;
import org.json.*;

public class MainActivity extends AppCompatActivity {
	
	private Toolbar _toolbar;
	private AppBarLayout _app_bar;
	private CoordinatorLayout _coordinator;
	private FloatingActionButton _fab;
	private DrawerLayout _drawer;
	
	private TextView textview1;
	private HorizontalScrollView hscroll1;
	private HorizontalScrollView console;
	private ProgressBar progressbar1;
	private WebView webview1;
	private LinearLayout linear7;
	private ImageView imageview1;
	private ImageView imageview5;
	private ImageView imageview6;
	private CircleImageView circleimageview1;
	private HorizontalScrollView barrapesquisa;
	private ImageView imageview2;
	private ImageView imageview3;
	private ImageView imageview4;
	private EditText url;
	private TextView consoletext;
	private TextView _drawer_textview1;
	private CircleImageView _drawer_circleimageview1;
	private ScrollView _drawer_vscroll1;
	private LinearLayout _drawer_linear1;
	private TextView _drawer_textview2;
	private TextView _drawer_textview3;
	private TextView _drawer_textview5;
	private CheckBox _drawer_checkbox1;
	private CheckBox _drawer_checkbox2;
	private CheckBox _drawer_checkbox3;
	private TextView _drawer_textview4;
	private Button _drawer_button2;
	
	private SharedPreferences shrd;
	private Intent intenter = new Intent();
	private AlertDialog.Builder pop;
	private RequestNetwork network;
	private RequestNetwork.RequestListener _network_request_listener;
	private Vibrator vib;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.main);
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
		_fab = findViewById(R.id._fab);
		
		_drawer = findViewById(R.id._drawer);
		ActionBarDrawerToggle _toggle = new ActionBarDrawerToggle(MainActivity.this, _drawer, _toolbar, R.string.app_name, R.string.app_name);
		_drawer.addDrawerListener(_toggle);
		_toggle.syncState();
		
		LinearLayout _nav_view = findViewById(R.id._nav_view);
		
		textview1 = findViewById(R.id.textview1);
		hscroll1 = findViewById(R.id.hscroll1);
		console = findViewById(R.id.console);
		progressbar1 = findViewById(R.id.progressbar1);
		webview1 = findViewById(R.id.webview1);
		webview1.getSettings().setJavaScriptEnabled(true);
		webview1.getSettings().setSupportZoom(true);
		linear7 = findViewById(R.id.linear7);
		imageview1 = findViewById(R.id.imageview1);
		imageview5 = findViewById(R.id.imageview5);
		imageview6 = findViewById(R.id.imageview6);
		circleimageview1 = findViewById(R.id.circleimageview1);
		barrapesquisa = findViewById(R.id.barrapesquisa);
		imageview2 = findViewById(R.id.imageview2);
		imageview3 = findViewById(R.id.imageview3);
		imageview4 = findViewById(R.id.imageview4);
		url = findViewById(R.id.url);
		consoletext = findViewById(R.id.consoletext);
		_drawer_textview1 = _nav_view.findViewById(R.id.textview1);
		_drawer_circleimageview1 = _nav_view.findViewById(R.id.circleimageview1);
		_drawer_vscroll1 = _nav_view.findViewById(R.id.vscroll1);
		_drawer_linear1 = _nav_view.findViewById(R.id.linear1);
		_drawer_textview2 = _nav_view.findViewById(R.id.textview2);
		_drawer_textview3 = _nav_view.findViewById(R.id.textview3);
		_drawer_textview5 = _nav_view.findViewById(R.id.textview5);
		_drawer_checkbox1 = _nav_view.findViewById(R.id.checkbox1);
		_drawer_checkbox2 = _nav_view.findViewById(R.id.checkbox2);
		_drawer_checkbox3 = _nav_view.findViewById(R.id.checkbox3);
		_drawer_textview4 = _nav_view.findViewById(R.id.textview4);
		_drawer_button2 = _nav_view.findViewById(R.id.button2);
		shrd = getSharedPreferences("settings", Activity.MODE_PRIVATE);
		pop = new AlertDialog.Builder(this);
		network = new RequestNetwork(this);
		vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		
		webview1.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageStarted(WebView _param1, String _param2, Bitmap _param3) {
				final String _url = _param2;
				consoletext.setText("Console: Carregando URL ".concat(_url));
				url.setText(_url);
				progressbar1.setProgress((int)webview1.getProgress());
				progressbar1.setVisibility(View.VISIBLE);
				webview1.getSettings().setJavaScriptEnabled(_drawer_checkbox1.isChecked());
				circleimageview1.setImageBitmap(webview1.getFavicon());
				textview1.setText("balbBrowser - ".concat(webview1.getTitle()));
				if (_drawer_checkbox2.isChecked()) {
					webview1.getSettings().setLoadWithOverviewMode(true); webview1.getSettings().setUseWideViewPort(true); final WebSettings webSettings = webview1.getSettings(); final String newUserAgent; newUserAgent = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36"; webSettings.setUserAgentString(newUserAgent);
				}
				if (_drawer_checkbox3.isChecked()) {
					pop.setTitle("Aviso");
					pop.setMessage("Ao usar o Modo Mobile, há altas chances de vários sites não funcionarem corretamente, caso deseje continuar pressione em Continuar, caso contrário retorne, desative o modo e reinicie o App!");
					pop.setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface _dialog, int _which) {
							webview1.getSettings().setLoadWithOverviewMode(true); webview1.getSettings().setUseWideViewPort(true); final WebSettings webSettings = webview1.getSettings(); final String newUserAgent; newUserAgent = "Mozilla/5.0 (Android) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36"; webSettings.setUserAgentString(newUserAgent);
						}
					});
					pop.setNegativeButton("Mudar", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface _dialog, int _which) {
							webview1.stopLoading();
							webview1.clearCache(true);
							_drawer.openDrawer(GravityCompat.START);
						}
					});
				}
				super.onPageStarted(_param1, _param2, _param3);
			}
			
			@Override
			public void onPageFinished(WebView _param1, String _param2) {
				final String _url = _param2;
				if (FileUtil.isExistFile(FileUtil.getPackageDataDir(getApplicationContext()).concat("/History"))) {
					FileUtil.writeFile(FileUtil.getPackageDataDir(getApplicationContext()).concat("/History"), FileUtil.readFile(FileUtil.getPackageDataDir(getApplicationContext()).concat("/History")).concat("-/-/-".concat(_url)));
				}
				else {
					FileUtil.writeFile(FileUtil.getPackageDataDir(getApplicationContext()).concat("/History"), _url);
				}
				url.setText(_url);
				progressbar1.setVisibility(View.GONE);
				_drawer_circleimageview1.setImageBitmap(webview1.getFavicon());
				circleimageview1.setImageBitmap(webview1.getFavicon());
				_drawer_textview1.setText(webview1.getTitle());
				textview1.setText("balbBrowser - ".concat(webview1.getTitle()));
				if (_url.contains("google.com")) {
					consoletext.setText("Console: Idle");
				}
				else {
					if (_url.contains("search.brave.com")) {
						consoletext.setText("Console: Idle");
					}
					else {
						consoletext.setText("Console: Idle // URL Carregado com Sucesso ".concat(_url));
					}
				}
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
		
		imageview1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				_drawer.openDrawer(GravityCompat.START);
			}
		});
		
		imageview5.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				pop.setTitle("Ir para Home");
				pop.setMessage("Você realmente quer ir para Home?");
				pop.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface _dialog, int _which) {
						if (shrd.contains("mecanismo")) {
							if (shrd.getString("mecanismo", "").equals("search.brave.com")) {
								webview1.loadUrl("https://search.brave.com");
							}
							else {
								webview1.loadUrl("https://google.com");
							}
						}
						else {
							webview1.loadUrl("https://google.com");
						}
					}
				});
				pop.setNegativeButton("Não", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface _dialog, int _which) {
						
					}
				});
				pop.setNeutralButton("Menu", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface _dialog, int _which) {
						
					}
				});
				pop.create().show();
			}
		});
		
		imageview6.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				webview1.loadUrl(webview1.getUrl());
			}
		});
		
		circleimageview1.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View _view) {
				circleimageview1.setImageBitmap(webview1.getFavicon());
				SketchwareUtil.showMessage(getApplicationContext(), "Imagem recarregada!");
				return true;
			}
		});
		
		imageview2.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View _view) {
				vib.vibrate((long)(5));
				return true;
			}
		});
		
		imageview2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				intenter.setClass(getApplicationContext(), DownloadActivity.class);
				startActivity(intenter);
			}
		});
		
		imageview3.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View _view) {
				
				return true;
			}
		});
		
		imageview3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				intenter.setClass(getApplicationContext(), SettingsActivity.class);
				startActivity(intenter);
			}
		});
		
		imageview4.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				intenter.setClass(getApplicationContext(), HistoryActivity.class);
				startActivity(intenter);
			}
		});
		
		url.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View _view) {
				pop.setTitle("Ações");
				pop.setMessage("Deseja excluir ou copiar a URL de Pesquisa?");
				pop.setPositiveButton("Copiar", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface _dialog, int _which) {
						((ClipboardManager) getSystemService(getApplicationContext().CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText("clipboard", url.getText().toString()));
						SketchwareUtil.showMessage(getApplicationContext(), "Copiado para Área de Transferência!");
					}
				});
				pop.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface _dialog, int _which) {
						url.setText("");
					}
				});
				pop.create().show();
				return true;
			}
		});
		
		url.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
				final String _charSeq = _param1.toString();
				if (_charSeq.equals("\n")) {
					
				}
			}
			
			@Override
			public void beforeTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
				
			}
			
			@Override
			public void afterTextChanged(Editable _param1) {
				
			}
		});
		
		consoletext.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View _view) {
				intenter.putExtra("text", consoletext.getText().toString());
				intenter.putExtra("certificado", webview1.getCertificate().toString());
				intenter.setClass(getApplicationContext(), ConsoleActivity.class);
				startActivity(intenter);
				return true;
			}
		});
		
		consoletext.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				SketchwareUtil.showMessage(getApplicationContext(), "Segure para abrir Console em tela cheia!");
			}
		});
		
		_fab.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View _view) {
				SketchwareUtil.hideKeyboard(getApplicationContext());
				if (shrd.contains("apkpure")) {
					if (Boolean.valueOf(shrd.getString("apkpure", ""))) {
						webview1.loadUrl("https://m.apkpure.com/search?q=".concat(url.getText().toString().trim().replace(" ", "+")));
					}
				}
				else {
					shrd.edit().putString("apkpure", String.valueOf(true)).commit();
					webview1.loadUrl("https://m.apkpure.com/search?q=".concat(url.getText().toString().trim().replace(" ", "+")));
				}
				return true;
			}
		});
		
		_fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				SketchwareUtil.hideKeyboard(getApplicationContext());
				if (url.getText().toString().contains("https://")) {
					webview1.loadUrl(url.getText().toString());
				}
				else {
					if (url.getText().toString().contains("www.")) {
						webview1.loadUrl(url.getText().toString());
					}
					else {
						if (url.getText().toString().contains(".")) {
							webview1.loadUrl(url.getText().toString());
						}
						else {
							if (shrd.contains("mecanismo")) {
								if (shrd.getString("mecanismo", "").equals("search.brave.com")) {
									webview1.loadUrl("https://search.brave.com/search?q=".concat(url.getText().toString()));
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
							else {
								if (url.getText().toString().contains("https://") || url.getText().toString().contains("http://")) {
										webview1.loadUrl(url.getText().toString());
								}
								else {
										webview1.loadUrl("https://www.google.com/search?ie=UTF-8&client=ms-android-samsung&source=android-browser&q=".concat(url.getText().toString()));
								}
							}
						}
					}
				}
			}
		});
		
		_network_request_listener = new RequestNetwork.RequestListener() {
			@Override
			public void onResponse(String _param1, String _param2, HashMap<String, Object> _param3) {
				final String _tag = _param1;
				final String _response = _param2;
				final HashMap<String, Object> _responseHeaders = _param3;
				
			}
			
			@Override
			public void onErrorResponse(String _param1, String _param2) {
				final String _tag = _param1;
				final String _message = _param2;
				
			}
		};
	}
	
	private void initializeLogic() {
		getSupportActionBar().hide();
		progressbar1.setVisibility(View.GONE);
		if (SketchwareUtil.isConnected(getApplicationContext())) {
			if (!getIntent().hasExtra("NewURL")) {
				if (shrd.contains("mecanismo")) {
					if (shrd.getString("mecanismo", "").equals("search.brave.com")) {
						webview1.loadUrl("https://search.brave.com");
					}
					else {
						webview1.loadUrl("https://google.com");
					}
				}
				else {
					webview1.loadUrl("https://google.com");
				}
				if (shrd.contains("desingsimples")) {
					if (Boolean.valueOf(shrd.getString("desingsimples", ""))) {
						console.setVisibility(View.GONE);
						textview1.setVisibility(View.GONE);
					}
				}
				if (shrd.contains("desingbeta")) {
					if (Boolean.valueOf(shrd.getString("desingbeta", ""))) {
						intenter.setClass(getApplicationContext(), BrowserActivity.class);
						startActivity(intenter);
						finish();
					}
				}
			}
			else {
				webview1.loadUrl(getIntent().getStringExtra("NewURL"));
			}
		}
		else {
			pop.setTitle("Sem internet");
			pop.setIcon(R.drawable.ic_cloud_off_black);
			pop.setMessage("Ops... Parece que você está conexão com a internet! Tente se reconectar ou use outras ferramentas do Navegador.");
			pop.setPositiveButton("Aguardar", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface _dialog, int _which) {
					
				}
			});
			pop.setNegativeButton("Sair", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface _dialog, int _which) {
					finish();
				}
			});
			pop.setNeutralButton("Mais Opções", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface _dialog, int _which) {
					
				}
			});
			pop.create().show();
		}
		_drawer_button2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				intenter.setClass(getApplicationContext(), HtmleditormenuActivity.class);
				startActivity(intenter);
			}
		});
	}
	
	@Override
	public void onBackPressed() {
		if (webview1.canGoBack()) {
			webview1.goBack();
		}
		else {
			finishAffinity();
		}
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		if (Boolean.valueOf(shrd.getString("cache", ""))) {
			webview1.clearHistory();
			webview1.clearCache(true);
		}
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