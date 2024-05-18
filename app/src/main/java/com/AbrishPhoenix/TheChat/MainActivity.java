package com.AbrishPhoenix.TheChat;

import com.AbrishPhoenix.TheChat.InitializeActivity;
import android.Manifest;
import android.animation.*;
import android.app.*;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.*;
import android.content.ClipData;
import android.content.ClipboardManager;
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
import android.widget.*;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.*;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.text.*;
import java.util.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.*;
import org.jetbrains.kotlin.*;
import org.json.*;

public class MainActivity extends AppCompatActivity {
	
	public final int REQ_CD_FP = 101;
	
	private Timer _timer = new Timer();
	
	private HashMap<String, Object> map = new HashMap<>();
	private String myState = "";
	private String chattingTo = "";
	private double ran = 0;
	private boolean amReady = false;
	private String linkedFilePath = "";
	private HashMap<String, Object> mapy = new HashMap<>();
	private String linkedFileLink = "";
	private HashMap<String, Object> map2 = new HashMap<>();
	private HashMap<String, Object> tempMap = new HashMap<>();
	private HashMap<String, Object> mapvar = new HashMap<>();
	private String videoPath = "";
	private String videoSize = "";
	private String videoDate = "";
	private String videoDuration = "";
	private double createdTimeMillisec = 0;
	private String formattedDate = "";
	InputStream linkedFileInputStream;
	Uri uri;
	
	private ArrayList<HashMap<String, Object>> messageList = new ArrayList<>();
	private ArrayList<String> onlines = new ArrayList<>();
	private ArrayList<String> readys = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> allVideosList = new ArrayList<>();
	
	private LinearLayout linear1;
	private LinearLayout linear14;
	private LinearLayout stateNoContainer;
	private LinearLayout stateYesContainer;
	private LinearLayout linear30;
	private ImageView imageview1;
	private TextView textview8;
	private LinearLayout linear31;
	private ImageView imageview5;
	private ImageView imageview4;
	private LinearLayout linear8;
	private LinearLayout linear18;
	private LinearLayout linear9;
	private LinearLayout linear12;
	private TextView textview3;
	private TextView onlinesText;
	private TextView textview5;
	private LinearLayout linear19;
	private TextView readyText;
	private TextView textview11;
	private LinearLayout linear10;
	private LinearLayout linear15;
	private LinearLayout linear11;
	private LinearLayout linear21;
	private LinearLayout linear13;
	private TextView textview7;
	private LinearLayout lookingFor;
	private ProgressBar progressbar1;
	private TextView textview13;
	private TextView textview6;
	private TextView myIdText;
	private TextView textview12;
	private LinearLayout linear20;
	private LinearLayout linear17;
	private LinearLayout linear22;
	private ImageView imageview3;
	private EditText edittext1;
	private Button notReady;
	private Button ready;
	private LinearLayout linear4;
	private LinearLayout linear29;
	private LinearLayout linear5;
	private LinearLayout linear16;
	private LinearLayout linear6;
	private LinearLayout linear7;
	private TextView textview1;
	private TextView withUid;
	private RecyclerView recyclerview1;
	private LinearLayout fileContainerNew;
	private LinearLayout linear23;
	private LinearLayout linear28;
	private LinearLayout linear27;
	private ProgressBar progressbar2;
	private ImageView downloadFileNew;
	private TextView fileFormatNew;
	private TextView fileNameNew;
	private TextView fileDescNew;
	private LinearLayout linear25;
	private LinearLayout sendLin;
	private EditText editMessage;
	private ImageView linkFileImg;
	private ImageView imageview2;
	
	private FirebaseAuth auth;
	private OnCompleteListener<AuthResult> _auth_create_user_listener;
	private OnCompleteListener<AuthResult> _auth_sign_in_listener;
	private OnCompleteListener<Void> _auth_reset_password_listener;
	private OnCompleteListener<Void> auth_updateEmailListener;
	private OnCompleteListener<Void> auth_updatePasswordListener;
	private OnCompleteListener<Void> auth_emailVerificationSentListener;
	private OnCompleteListener<Void> auth_deleteUserListener;
	private OnCompleteListener<Void> auth_updateProfileListener;
	private OnCompleteListener<AuthResult> auth_phoneAuthListener;
	private OnCompleteListener<AuthResult> auth_googleSignInListener;
	
	private AlertDialog.Builder d;
	private AlertDialog.Builder d2;
	private TimerTask tim;
	private SharedPreferences data;
	private Intent fp = new Intent(Intent.ACTION_GET_CONTENT);
	private AlertDialog cd;
	private AlertDialog.Builder d3;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		
        super.onCreate(_savedInstanceState);
        Helper.checkTheme(this, false);
        // _transparent();
		setContentView(R.layout.main);
		initialize(_savedInstanceState);
		FirebaseApp.initializeApp(this);
		MobileAds.initialize(this);
		
		
		if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
			ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, 1000);
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
		linear14 = findViewById(R.id.linear14);
		stateNoContainer = findViewById(R.id.stateNoContainer);
		stateYesContainer = findViewById(R.id.stateYesContainer);
		linear30 = findViewById(R.id.linear30);
		imageview1 = findViewById(R.id.imageview1);
		textview8 = findViewById(R.id.textview8);
		linear31 = findViewById(R.id.linear31);
		imageview5 = findViewById(R.id.imageview5);
		imageview4 = findViewById(R.id.imageview4);
		linear8 = findViewById(R.id.linear8);
		linear18 = findViewById(R.id.linear18);
		linear9 = findViewById(R.id.linear9);
		linear12 = findViewById(R.id.linear12);
		textview3 = findViewById(R.id.textview3);
		onlinesText = findViewById(R.id.onlinesText);
		textview5 = findViewById(R.id.textview5);
		linear19 = findViewById(R.id.linear19);
		readyText = findViewById(R.id.readyText);
		textview11 = findViewById(R.id.textview11);
		linear10 = findViewById(R.id.linear10);
		linear15 = findViewById(R.id.linear15);
		linear11 = findViewById(R.id.linear11);
		linear21 = findViewById(R.id.linear21);
		linear13 = findViewById(R.id.linear13);
		textview7 = findViewById(R.id.textview7);
		lookingFor = findViewById(R.id.lookingFor);
		progressbar1 = findViewById(R.id.progressbar1);
		textview13 = findViewById(R.id.textview13);
		textview6 = findViewById(R.id.textview6);
		myIdText = findViewById(R.id.myIdText);
		textview12 = findViewById(R.id.textview12);
		linear20 = findViewById(R.id.linear20);
		linear17 = findViewById(R.id.linear17);
		linear22 = findViewById(R.id.linear22);
		imageview3 = findViewById(R.id.imageview3);
		edittext1 = findViewById(R.id.edittext1);
		notReady = findViewById(R.id.notReady);
		ready = findViewById(R.id.ready);
		linear4 = findViewById(R.id.linear4);
		linear29 = findViewById(R.id.linear29);
		linear5 = findViewById(R.id.linear5);
		linear16 = findViewById(R.id.linear16);
		linear6 = findViewById(R.id.linear6);
		linear7 = findViewById(R.id.linear7);
		textview1 = findViewById(R.id.textview1);
		withUid = findViewById(R.id.withUid);
		recyclerview1 = findViewById(R.id.recyclerview1);
		fileContainerNew = findViewById(R.id.fileContainerNew);
		linear23 = findViewById(R.id.linear23);
		linear28 = findViewById(R.id.linear28);
		linear27 = findViewById(R.id.linear27);
		progressbar2 = findViewById(R.id.progressbar2);
		downloadFileNew = findViewById(R.id.downloadFileNew);
		fileFormatNew = findViewById(R.id.fileFormatNew);
		fileNameNew = findViewById(R.id.fileNameNew);
		fileDescNew = findViewById(R.id.fileDescNew);
		linear25 = findViewById(R.id.linear25);
		sendLin = findViewById(R.id.sendLin);
		editMessage = findViewById(R.id.editMessage);
		linkFileImg = findViewById(R.id.linkFileImg);
		imageview2 = findViewById(R.id.imageview2);
		auth = FirebaseAuth.getInstance();
		d = new AlertDialog.Builder(this);
		d2 = new AlertDialog.Builder(this);
		data = getSharedPreferences("data", Activity.MODE_PRIVATE);
		fp.setType("*/*");
		fp.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
		d3 = new AlertDialog.Builder(this);
		
		imageview5.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				cd = new AlertDialog.Builder(MainActivity.this).create();
				LayoutInflater cdLI = getLayoutInflater();
				View cdCV = (View) cdLI.inflate(R.layout.menuu, null);
				cd.requestWindowFeature(Window.FEATURE_NO_TITLE);
				cd.setView(cdCV);
				final LinearLayout logOut = (LinearLayout)
				cdCV.findViewById(R.id.logOut);
				cd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
				cd.show();
				logOut.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View _view) {
						d3.setTitle("Log out");
						d3.setMessage("Are you sure you want to log out? you will get a new ID by doing that.");
						d3.setPositiveButton("Log out", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface _dialog, int _which) {
								FirebaseHelper.deleteUserData();
								Helper.clearAppData(MainActivity.this);
							}
						});
						d3.setNegativeButton("No", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface _dialog, int _which) {
								
							}
						});
						d3.create().show();
					}
				});
			}
		});
		
		imageview4.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				((ClipboardManager) getSystemService(getApplicationContext().CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText("clipboard", myIdText.getText().toString()));
				Helper.showMessage(MainActivity.this, "ID copied.");
			}
		});
		
		myIdText.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View _view) {
				((ClipboardManager) getSystemService(getApplicationContext().CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText("clipboard", myIdText.getText().toString()));
				Helper.showMessage(MainActivity.this, "ID copied.");
				return true;
			}
		});
		
		imageview3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (amReady) {
					if (!edittext1.getText().toString().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
						_startNewChat(edittext1.getText().toString());
					}
					else {
						Helper.showMessage(MainActivity.this, "Bruh?");
					}
				}
				else {
					Helper.showMessage(MainActivity.this, "Be ready first.");
				}
			}
		});
		
		notReady.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				FirebaseHelper.addMeOnline(false);
				myState = "1";
				amReady = false;
				notReady.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)15, (int)5, ColorHelper.getPrimaryColor(MainActivity.this), ColorHelper.getPrimaryColor(MainActivity.this)));
				ready.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)15, (int)5, Color.TRANSPARENT, ColorHelper.getThirdBackgroundColor(MainActivity.this)));
				notReady.setTextColor(0xFFFFFFFF);
				ready.setTextColor(ColorHelper.getInverseColor(MainActivity.this));
				lookingFor.setVisibility(View.GONE);
				editMessage.setText("");
				_startLooking(false);
				if(!chattingTo.equals("")) {
					    FirebaseHelper.closeChat(chattingTo);
				}
			}
		});
		
		ready.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				FirebaseHelper.addMeOnline(true);
				myState = "2";
				amReady = true;
				notReady.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)15, (int)5, Color.TRANSPARENT, ColorHelper.getThirdBackgroundColor(MainActivity.this)));
				ready.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)15, (int)5, ColorHelper.getPrimaryColor(MainActivity.this), ColorHelper.getPrimaryColor(MainActivity.this)));
				notReady.setTextColor(ColorHelper.getInverseColor(MainActivity.this));
				ready.setTextColor(0xFFFFFFFF);
				_startLooking(true);
			}
		});
		
		textview1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				d2.setTitle("Leave chat");
				d2.setMessage("Are you sure?");
				d2.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface _dialog, int _which) {
						FirebaseHelper.closeChat(chattingTo);
					}
				});
				d2.create().show();
			}
		});
		
		recyclerview1.addOnScrollListener(new RecyclerView.OnScrollListener() {
			@Override
			public void onScrollStateChanged(RecyclerView recyclerView, int _scrollState) {
				super.onScrollStateChanged(recyclerView, _scrollState);
				
			}
			
			@Override
			public void onScrolled(RecyclerView recyclerView, int _offsetX, int _offsetY) {
				super.onScrolled(recyclerView, _offsetX, _offsetY);
				
			}
		});
		
		downloadFileNew.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				FirebaseStorageHelper.cancelRequest();
				fileContainerNew.setVisibility(View.GONE);
				linkedFilePath = "";
				linkedFileLink = "";
				linkedFileInputStream = null;
				uri = null;
				fileNameNew.setText("");
				fileDescNew.setText("");
				fileFormatNew.setText("");
				if (editMessage.getText().toString().equals("")) {
					linkFileImg.setVisibility(View.VISIBLE);
				}
				imageview2.setVisibility(View.VISIBLE);
				progressbar2.setVisibility(View.GONE);
				try{
					map2.clear();
				}catch(Exception e){
					 
				}
			}
		});
		
		editMessage.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
				final String _charSeq = _param1.toString();
				if (_charSeq.equals("")) {
					if (linkedFilePath.equals("")) {
						linkFileImg.setVisibility(View.VISIBLE);
					}
				}
				else {
					linkFileImg.setVisibility(View.GONE);
				}
			}
			
			@Override
			public void beforeTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
				
			}
			
			@Override
			public void afterTextChanged(Editable _param1) {
				
			}
		});
		
		linkFileImg.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
				intent.addCategory(Intent.CATEGORY_OPENABLE);
				intent.setType("*/*"); // Allow any file type
				startActivityForResult(intent, 7997);
			}
		});
		
		imageview2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (!editMessage.getText().toString().equals("") || fileContainerNew.getVisibility() == View.VISIBLE) {
					imageview2.setVisibility(View.GONE);
					if (linkedFilePath.equals("")) {
						if (!linkedFileLink.equals("")) {
							map2 = new HashMap<>();
							map2.put("file_name", fileNameNew.getText().toString());
							map2.put("file_dec", fileDescNew.getText().toString());
							map2.put("file_format", fileFormatNew.getText().toString());
							map2.put("file_link", linkedFileLink);
						}
						map = new HashMap<>();
						map.put("who", "You");
						map.put("text", editMessage.getText().toString().trim());
						map.put("time", String.valueOf(Helper.getTime()));
						if (0 < map2.size()) {
							map.put("file", new Gson().toJson(map2));
						}
						messageList.add(map);
						recyclerview1.getAdapter().notifyItemInserted(messageList.size());
						recyclerview1.smoothScrollToPosition((int)messageList.size() - 1);
						HashMap<String, Object> mao = new HashMap<>();
						mao.put("text", map.get("text").toString());
						if(map.containsKey("file")) {
							    mao.put("file", map.get("file").toString());
						}
						// FirebaseHelper.sendMessage(MainActivity.this, "{" + "\"text\":" + "\"" + editMessage.getText().toString().trim() + "\"" + (!linkedFileLink.equals("") ? (",\"file\":" + "\"" + (new com.google.gson.Gson().toJson(map2)) + "\"") : "") + "}", chattingTo);
						FirebaseHelper.sendMessage(MainActivity.this, new com.google.gson.Gson().toJson(mao), chattingTo);
						downloadFileNew.performClick();
						editMessage.setText("");
						fileFormatNew.setText("");
						fileNameNew.setText("");
						fileDescNew.setText("");
						linkedFilePath = "";
						linkedFileLink = "";
						map2.clear();
						imageview2.setVisibility(View.VISIBLE);
						progressbar2.setVisibility(View.GONE);
					}
					else {
						progressbar2.setVisibility(View.VISIBLE);
						FirebaseStorageHelper.uploadFile(MainActivity.this, uri, linkedFileInputStream, new FirebaseStorageHelper.Listener() {
							    @Override
							    public void onProgress(final double percent) {
								        fileDescNew.setText("Uploading " + String.valueOf((int)percent) + "%");
								    }
							    
							    @Override
							    public void onSuccess(final String _downloadLink) {
								        linkedFileLink = _downloadLink;
								        fileDescNew.setText(_calculate(Helper.getSizeFromURI(MainActivity.this,uri)));
								        linkedFilePath = "";
								        linkedFileInputStream = null;
								        uri = null;
								        imageview2.performClick();
								    }
							    
							    @Override
							    public void onFailure(final String msg) {
								        Helper.showMessage(MainActivity.this, msg);
								        downloadFileNew.performClick();
								        imageview2.setVisibility(View.VISIBLE);
								    }
						});
					}
				}
				else {
					
				}
			}
		});
		
		auth_updateEmailListener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		auth_updatePasswordListener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		auth_emailVerificationSentListener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		auth_deleteUserListener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		auth_phoneAuthListener = new OnCompleteListener<AuthResult>() {
			@Override
			public void onComplete(Task<AuthResult> task) {
				final boolean _success = task.isSuccessful();
				final String _errorMessage = task.getException() != null ? task.getException().getMessage() : "";
				
			}
		};
		
		auth_updateProfileListener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		auth_googleSignInListener = new OnCompleteListener<AuthResult>() {
			@Override
			public void onComplete(Task<AuthResult> task) {
				final boolean _success = task.isSuccessful();
				final String _errorMessage = task.getException() != null ? task.getException().getMessage() : "";
				
			}
		};
		
		_auth_create_user_listener = new OnCompleteListener<AuthResult>() {
			@Override
			public void onComplete(Task<AuthResult> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		_auth_sign_in_listener = new OnCompleteListener<AuthResult>() {
			@Override
			public void onComplete(Task<AuthResult> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		_auth_reset_password_listener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				
			}
		};
	}
	
	private void initializeLogic() {
		_navigationBarColor(ColorHelper.getStringSecondBackgroundColor(MainActivity.this));
		recyclerview1.setLayoutManager(new LinearLayoutManager(this));
		recyclerview1.setAdapter(new Recyclerview1Adapter(messageList));
		recyclerview1.getAdapter().notifyDataSetChanged();
		FirebaseHelper.addMeOnline();
		myState = "1";
		FirebaseHelper.Listener listener = new FirebaseHelper.Listener() {
				@Override
				public void onNewMessage(String _msg) {
						if(!chattingTo.equals("") && _msg != null && !_msg.equals("")) {
								String[] users = {FirebaseAuth.getInstance().getCurrentUser().getUid(), chattingTo};
								Arrays.sort(users);
								String reference = users[0] + "" + users[1];
								
								Logger.log(MainActivity.this, _msg + " with " + Helper.getArranged(reference));
								try{
										String newMessage = SecHelper.decrypt(SecHelper.decryptedStringKey(_msg, Helper.getArranged(reference)));
										
										Logger.log(MainActivity.this, newMessage);
										
										HashMap<String, Object> hm = Helper.getHashMap(newMessage.trim());
										
										
										map = new HashMap<>();
										map.put("who", "User");
										map.put("text", hm.get("text").toString().trim());
										if(hm.containsKey("file")) {
												map.put("file", hm.get("file").toString().trim());
										}
										map.put("time", String.valueOf(Helper.getTime()));
										messageList.add(map);
										recyclerview1.getAdapter().notifyItemInserted(messageList.size());
										recyclerview1.smoothScrollToPosition((int)messageList.size() - 1);
								}catch(Exception e){
										Logger.log(MainActivity.this, e.toString());
										Helper.showMessage(MainActivity.this, e.getMessage());
								}
						}
				}
				
				@Override
				public void onNewChat(String uid) {
						if(myState.equals("2")) {
								try{
										tim.cancel();
								}catch(Exception e){
										
								}
								stateNoContainer.setVisibility(View.GONE);
								stateYesContainer.setVisibility(View.VISIBLE);
								chattingTo = uid;
					            try{
						                messageList.clear();
						                recyclerview1.getAdapter().notifyDataSetChanged();
						            }catch(Exception e){}
								// FirebaseHelper.startChat(chattingTo);
					            withUid.setText(Helper.getHidden(chattingTo));
								
								FirebaseHelper.addMeOnline(false);
								myState = "1";
								Helper.showMessage(MainActivity.this, "Connected successfully!");
						}
				}
				
				@Override
				public void onChatClosed() {
						if(!chattingTo.equals("")) {
								stateNoContainer.setVisibility(View.VISIBLE);
								stateYesContainer.setVisibility(View.GONE);
								chattingTo = "";
								for(int j = 0; j < messageList.size(); j++) {
										if(messageList.get(j).containsKey("file")) {
												FirebaseStorageHelper.removeFile(MainActivity.this, Helper.getHashMap(messageList.get(j).get("file").toString().trim()).get("file_link").toString(), null);
										}
								}
								messageList.clear();
								recyclerview1.getAdapter().notifyDataSetChanged();
								notReady.performClick();
								withUid.setText("");
								Helper.showMessage(MainActivity.this, "Chat closed.");
								downloadFileNew.performClick();
								AdFather.showInterstitialAd(MainActivity.this, new AdFather.AdFatherCallBack() {});
						}
				}
				
				@Override
				public void onReturned(String uid, String statee) {
						if(statee.equals("0")) {
								if(onlines.contains(uid)) {
										onlines.remove(uid);
								}
								if(readys.contains(uid)) {
										readys.remove(uid);
								}
						} else {
								if(!onlines.contains(uid)) {
										onlines.add(uid);
								}
								
								if(statee.equals("1")) {
										if(readys.contains(uid)) {
												readys.remove(uid);
										}
								}
								
								if(statee.equals("2")) {
										if(!readys.contains(uid)) {
												readys.add(uid);
										}
										
								}
						}
						onlinesText.setText(String.valueOf(onlines.size()));
						readyText.setText(String.valueOf(readys.size()));
				}
		};
		
		FirebaseHelper.newMessageListener(this, listener);
		
		FirebaseHelper.newChatListener(this, listener);
		
		FirebaseHelper.onlineListener(this, listener);
		
		Helper.imageViewTheme(new ImageView[] {imageview3, imageview2, linkFileImg, downloadFileNew});
		Helper.imageViewFilter(imageview4, 0xFFFFFFFF);
		Helper.imageViewFilter(imageview5, 0xFFFFFFFF);
		myIdText.setText(FirebaseAuth.getInstance().getCurrentUser().getUid());
		notReady.performClick();
	}
	
	@Override
	protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
		super.onActivityResult(_requestCode, _resultCode, _data);
		if (_requestCode == 1577) {
				if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
						_startDownload(tempMap);
				}
		}
		if ((_requestCode == 7997) && (_resultCode == Activity.RESULT_OK)) {
				try{
						uri = _data.getData();
						
						linkedFileInputStream = Helper.getInputStreamFromURI(this, uri);
						
						if(linkedFileInputStream == null) {
								    Helper.showMessage(this, "Something went wrong, try other file.");
								    return;
						}
						linkedFilePath = uri.toString();;
						fileNameNew.setText(Helper.getNameFromURI(this, uri));
						fileDescNew.setText(_calculate(Helper.getSizeFromURI(this, uri)));
						fileFormatNew.setText(fileNameNew.getText().toString().substring(fileNameNew.getText().toString().lastIndexOf(".")));
						fileContainerNew.setVisibility(View.VISIBLE);
						linkFileImg.setVisibility(View.GONE);
				}catch(Exception e){
						Helper.showMessage(this, e.getMessage());
				}
		}
		switch (_requestCode) {
			case REQ_CD_FP:
			if (_resultCode == Activity.RESULT_OK) {
				ArrayList<String> _filePath = new ArrayList<>();
				if (_data != null) {
					if (_data.getClipData() != null) {
						for (int _index = 0; _index < _data.getClipData().getItemCount(); _index++) {
							ClipData.Item _item = _data.getClipData().getItemAt(_index);
							_filePath.add(FileUtil.convertUriToFilePath(getApplicationContext(), _item.getUri()));
						}
					}
					else {
						_filePath.add(FileUtil.convertUriToFilePath(getApplicationContext(), _data.getData()));
					}
				}
				linkedFilePath = _filePath.get((int)(0));
				fileNameNew.setText(Uri.parse(linkedFilePath).getLastPathSegment());
				fileDescNew.setText(_calculate(FileUtil.getFileLength(linkedFilePath)));
				fileFormatNew.setText(fileNameNew.getText().toString().substring(fileNameNew.getText().toString().lastIndexOf(".")));
				fileContainerNew.setVisibility(View.VISIBLE);
				linkFileImg.setVisibility(View.GONE);
			}
			else {
				
			}
			break;
			default:
			break;
		}
	}
	
	@Override
	public void onBackPressed() {
		d.setTitle("Exit");
		d.setMessage("Are you sure you want to exit?");
		d.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface _dialog, int _which) {
				finishAffinity();
			}
		});
		d.setNegativeButton("No", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface _dialog, int _which) {
				
			}
		});
		d.create().show();
	}
	public void _startNewChat(final String _uid) {
		if (_uid == null) {
			if (1 < readys.size()) {
				ran = SketchwareUtil.getRandom((int)(0), (int)(readys.size() - 1));
				if (readys.get((int)(ran)).equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
					_startNewChat(null);
				}
				else {
					_startNewChat(readys.get((int)(ran)));
				}
			}
			else {
				Helper.showMessage(MainActivity.this, "It seems that you're the only one online.");
				_startLooking(true);
			}
		}
		else {
			lookingFor.setVisibility(View.GONE);
			FirebaseHelper.onlineState(this, _uid, new FirebaseHelper.Listener() {
					@Override
					public void onReturned(String state) {
							if(state.equals("2")) {
									if(!myState.equals("2")) return;
						            try{
							                FirebaseHelper.startChat(_uid);
							                // SketchwareUtil.showMessage(MainActivity.this, "Chat start called");
							            }catch(Exception e) {
							                Helper.showMessage(MainActivity.this, e.getMessage());
							            }
							} else {
									Helper.showMessage(MainActivity.this, "User is not ready.");
							}
					}
			});
			
		}
	}
	
	
	public void _startLooking(final boolean _truee) {
		if (_truee) {
			lookingFor.setVisibility(View.VISIBLE);
			try{
				tim.cancel();
			}catch(Exception e){
				 
			}
			tim = new TimerTask() {
				@Override
				public void run() {
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							_startNewChat(null);
						}
					});
				}
			};
			_timer.schedule(tim, (int)(SketchwareUtil.getRandom((int)(1000), (int)(5000))));
		}
		else {
			lookingFor.setVisibility(View.GONE);
			try{
				tim.cancel();
			}catch(Exception e){
				 
			}
		}
	}
	
	
	public void _navigationBarColor(final String _color) {
		if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) { getWindow().setNavigationBarColor(Color.parseColor(_color)); }
	}
	
	
	public String _calculate(final double _byteee) {
		long kilobyte = 1024;
		long megabyte = kilobyte * 1024;
		long gigabyte = megabyte * 1024;
		long terabyte = gigabyte * 1024;
		
		if ((_byteee >= 0) && (_byteee < kilobyte)) {
			    return _byteee + " B";
			
		} else if ((_byteee >= kilobyte) && (_byteee < megabyte)) {
			    return (new DecimalFormat("0.0").format(_byteee / kilobyte)) + " KB";
			
		} else if ((_byteee >= megabyte) && (_byteee < gigabyte)) {
			        return (new DecimalFormat("0.0").format(_byteee / megabyte)) + " MB";
			
		} else if ((_byteee >= gigabyte) && (_byteee < terabyte)) {
			        return (new DecimalFormat("0.0").format(_byteee / gigabyte)) + " GB";
			
		} else if (_byteee >= terabyte) {
			        return (new DecimalFormat("0.0").format(_byteee / terabyte)) + " TB";
			
		} else {
			        return new DecimalFormat("0.0").format(_byteee) + " Bytes";
		}
	}
	
	
	public void _RadiusGradient4(final View _view, final String _color1, final String _color2, final double _lt, final double _rt, final double _rb, final double _lb, final double _border, final String _color3) {
		int[] colors = { Color.parseColor(_color1), Color.parseColor(_color2) }; android.graphics.drawable.GradientDrawable gd = new android.graphics.drawable.GradientDrawable(android.graphics.drawable.GradientDrawable.Orientation.TOP_BOTTOM, colors);
		gd.setCornerRadii(new float[]{(int)_lt,(int)_lt,(int)_rt,(int)_rt,(int)_rb,(int)_rb,(int)_lb,(int)_lb});
		gd.setStroke((int) _border, Color.parseColor(_color3));
		_view.setBackground(gd);
	}
	
	
	public void _startDownload(final HashMap<String, Object> _mappy) {
		tempMap = _mappy;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
				if (ActivityCompat.checkSelfPermission(this,android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
						requestPermissions(new String[] {android.Manifest.permission.POST_NOTIFICATIONS}, 1577);
				        Helper.showMessage(MainActivity.this, "Allow notification permission.");
				        return;
				}
		}
		
		/*
try{
	DownloadService.initialize(null, (int)Helper.getTime(), tempMap.get("file_name").toString(), Helper.getDownloadPath(MainActivity.this, tempMap.get("file_name").toString()), tempMap.get("file_link").toString());
	Intent serviceIntent = new Intent(MainActivity.this, DownloadService.class);
	if (Build.VERSION.SDK_INT >= 26) {
		androidx.core.content.ContextCompat.startForegroundService(MainActivity.this, serviceIntent);
	} else {
		startService(serviceIntent);
	}
}catch(Exception e){
    Logger.log(this, e.toString());
}
*/
		
		FirebaseStorageHelper.downloadFile(this, tempMap.get("file_link").toString(), Helper.getDownloadPath(MainActivity.this, tempMap.get("file_name").toString()), new FirebaseStorageHelper.Listener() {
			    @Override
			    public void onProgress(final double percent) {
				        // Helper.showMessage(MainActivity.this, "Downloading " + (int)percent + "%");
				    }
				
			    
			    @Override
			    public void onSuccess() {
				        Helper.showMessage(MainActivity.this, "Download completed!");
				    }
				
			    @Override
			    public void onFailure(final String msg) {
				        Helper.showMessage(MainActivity.this, msg);
				    }
		});
	}
	
	public class Recyclerview1Adapter extends RecyclerView.Adapter<Recyclerview1Adapter.ViewHolder> {
		
		ArrayList<HashMap<String, Object>> _data;
		
		public Recyclerview1Adapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}
		
		@Override
		public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			LayoutInflater _inflater = getLayoutInflater();
			View _v = _inflater.inflate(R.layout.list, null);
			RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			_v.setLayoutParams(_lp);
			return new ViewHolder(_v);
		}
		
		@Override
		public void onBindViewHolder(ViewHolder _holder, final int _position) {
			View _view = _holder.itemView;
			
			final LinearLayout linear3 = _view.findViewById(R.id.linear3);
			final LinearLayout linear1 = _view.findViewById(R.id.linear1);
			final com.AbrishPhoenix.TheChat.MaxLinearLayout linear5 = _view.findViewById(R.id.linear5);
			final TextView who = _view.findViewById(R.id.who);
			final LinearLayout linear2 = _view.findViewById(R.id.linear2);
			final LinearLayout fileContainer = _view.findViewById(R.id.fileContainer);
			final TextView msg = _view.findViewById(R.id.msg);
			final TextView time = _view.findViewById(R.id.time);
			final LinearLayout linear27 = _view.findViewById(R.id.linear27);
			final LinearLayout linear26 = _view.findViewById(R.id.linear26);
			final ImageView downloadFile = _view.findViewById(R.id.downloadFile);
			final TextView fileFormat = _view.findViewById(R.id.fileFormat);
			final TextView fileName = _view.findViewById(R.id.fileName);
			final TextView fileDesc = _view.findViewById(R.id.fileDesc);
			
			RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			_view.setLayoutParams(_lp);
			who.setText(_data.get((int)_position).get("who").toString());
			time.setText(Helper.getDate(Long.parseLong(_data.get((int)_position).get("time").toString()), "hh:mm a"));
			if (_data.get((int)_position).containsKey("text") && !_data.get((int)_position).get("text").toString().equals("")) {
				msg.setVisibility(View.VISIBLE);
				msg.setText(_data.get((int)_position).get("text").toString());
			}
			else {
				msg.setVisibility(View.GONE);
			}
			Helper.imageViewTheme(downloadFile);
			if (who.getText().toString().equals("You")) {
				who.setVisibility(View.GONE);
				linear1.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
				_RadiusGradient4(linear5, ColorHelper.getStringPrimaryColor(MainActivity.this), ColorHelper.getStringPrimaryColor(MainActivity.this), 25, 25, 10, 25, 1, ColorHelper.getStringAttrs(MainActivity.this, R.attr.stokeColor));
				who.setTextColor(0xFFFFFFFF);
				msg.setTextColor(0xFFFFFFFF);
				time.setTextColor(0xFFFFFFFF);
			}
			else {
				who.setVisibility(View.VISIBLE);
				linear1.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
				_RadiusGradient4(linear5, ColorHelper.getStringSecondBackgroundColor(MainActivity.this), ColorHelper.getStringSecondBackgroundColor(MainActivity.this), 25, 25, 25, 10, 1, ColorHelper.getStringAttrs(MainActivity.this, R.attr.stokeColor));
				who.setTextColor(ColorHelper.getPrimaryColor(MainActivity.this));
				msg.setTextColor(ColorHelper.getInverseColor(MainActivity.this));
				time.setTextColor(ColorHelper.getInverseColor(MainActivity.this));
			}
			if (_data.get((int)_position).containsKey("file")) {
				if (_data.get((int)_position).get("file").toString().equals("")) {
					fileContainer.setVisibility(View.GONE);
				}
				else {
					fileContainer.setVisibility(View.VISIBLE);
					mapy = new Gson().fromJson(_data.get((int)_position).get("file").toString(), new TypeToken<HashMap<String, Object>>(){}.getType());
					fileName.setText(mapy.get("file_name").toString());
					fileFormat.setText(mapy.get("file_format").toString());
					fileDesc.setText(mapy.get("file_dec").toString());
					downloadFile.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View _view) {
							_startDownload(Helper.getHashMap(_data.get((int)_position).get("file").toString()));
						}
					});
				}
			}
			else {
				fileContainer.setVisibility(View.GONE);
			}
			linear5.setOnLongClickListener(new View.OnLongClickListener() {
				@Override
				public boolean onLongClick(View _view) {
					((ClipboardManager) getSystemService(getApplicationContext().CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText("clipboard", msg.getText().toString()));
					Helper.showMessage(MainActivity.this, "Text copied.");
					return true;
				}
			});
		}
		
		@Override
		public int getItemCount() {
			return _data.size();
		}
		
		public class ViewHolder extends RecyclerView.ViewHolder {
			public ViewHolder(View v) {
				super(v);
			}
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