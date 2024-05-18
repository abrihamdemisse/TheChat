package com.AbrishPhoenix.TheChat;

import android.provider.*;
import java.util.*;
import android.content.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import android.widget.*;
import android.graphics.*;
import java.io.*;
import android.app.*;
import android.net.*;
import android.database.*;
import android.provider.*;
import android.webkit.*;
import android.view.*;

public class Helper {
	
	public static String getId(Context context) {
		String id = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
		if(id != null && !id.isEmpty()) {
			return id.toUpperCase();
		} else {
			
		}
		return "";
	}
	
	public static void showMessage(Context cxt, String msg) {
		View ctVT = LayoutInflater.from(cxt).inflate(R.layout.toast, null);
		final LinearLayout basee = (LinearLayout) ctVT.findViewById(R.id.basee);
		final TextView messagee = (TextView) ctVT.findViewById(R.id.messagee);
		// basee.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)10, (int)1, 0x33000000, 0xFFF50057));
		messagee.setText(msg);
		Toast T = Toast.makeText(cxt,"",0);
		T.setGravity(Gravity.CENTER, (int) 0, (int)00);
		T.setView(ctVT);
		T.show();
	}
	
	public static String generateKey() {
		return generateRandom(16);
	}
	
	public static String getHidden(String s) {
		String result = "";
		if(s.length() > 10) {
			result = s.substring(0, 5) + "********************" + s.substring(s.length() - 3);
		} else {
			result = s.substring(0, 3) + "**";
		}
		return result;
	}
	
	public static String getArranged(String reference) {
		String result = "";
		if(reference.length() > 20) {
			String topS = reference.substring(0, 11);
			String bottomS = reference.substring(reference.length() - 10, reference.length());
			result = topS + bottomS;
		} else {
			result = reference;
		}
		return result;
	}
    
    public static void saveFileInCache(Context context, Uri uri) {
        File cacheDir = new File(context.getCacheDir(), "Cache");
		if (!cacheDir.exists()) {
			cacheDir.mkdirs();
		}
		
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(uri);

            // Determine the MIME type of the file
            ContentResolver contentResolver = context.getContentResolver();
            String mimeType = contentResolver.getType(uri);

            // Generate a unique file name based on MIME type
            String fileExtension = MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType);
            String fileName = "temp_file_" + "." + fileExtension;

            // Save the content in the app's directory with the determined file type
            File file = new File(cacheDir, fileName);
            FileOutputStream outputStream = new FileOutputStream(file);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
            outputStream.close();
            inputStream.close();
        } catch (IOException e) {
            // e.printStackTrace();
        }
    }
    
    public static String getNameFromURI(Context context, Uri uri) {
    	Cursor c = context.getContentResolver().query(uri, null, null, null, null);
		c.moveToFirst();
		return c.getString(c.getColumnIndex(OpenableColumns.DISPLAY_NAME));
	}
	
	public static long getSizeFromURI(Context context, Uri uri) {
    	Cursor c = context.getContentResolver().query(uri, null, null, null, null);
		c.moveToFirst();
		return c.getLong(c.getColumnIndex(OpenableColumns.SIZE));
	}
	
	public static InputStream getInputStreamFromURI(Context context, Uri uri) {
        try{
            return context.getContentResolver().openInputStream(uri);
        }catch(Exception e){}
    	return null;
	}
	
	public static String generateRandom(int _length) {
		String doNotUseThisForSth = "";
		String randomLetterResult = "";
		String randgetbackstr = "";
		
		final ArrayList<String> letters = new ArrayList<>();
		
		letters.add("x");letters.clear();letters.add("a");letters.add("b");letters.add("c");letters.add("d");letters.add("e");letters.add("f");letters.add("g");letters.add("h");letters.add("i");
		letters.add("j");letters.add("k");letters.add("l");letters.add("m");letters.add("n");letters.add("o");letters.add("p");letters.add("q");letters.add("r");letters.add("s");letters.add("t");
		letters.add("u");letters.add("v");letters.add("w");letters.add("x");letters.add("y");letters.add("z");letters.add("1");letters.add("2");letters.add("3");letters.add("4");letters.add("5");
		letters.add("6");letters.add("7");letters.add("8");letters.add("9");letters.add("0");
		
		for(int _repeat49 = 0; _repeat49 < (int)(_length); _repeat49++) {
			if (SketchwareUtil.getRandom((int)(0), (int)(1)) == 0) {
				doNotUseThisForSth = letters.get((int)(SketchwareUtil.getRandom((int)(0), (int)(letters.size() - 1)))).toLowerCase();
			}
			else {
				doNotUseThisForSth = letters.get((int)(SketchwareUtil.getRandom((int)(0), (int)(letters.size() - 1)))).toUpperCase();
			}
			randgetbackstr = randgetbackstr.concat(doNotUseThisForSth);
		}
		return (randgetbackstr);
	}
	
	public static String getEmail(Context context) {
		return getId(context) + "@anonymous.com";
	}
	
	public static long getTime() {
		return Calendar.getInstance().getTimeInMillis();
	}
	
	
	public static String getDate(long l, String _format) {
		return new java.text.SimpleDateFormat(_format).format(l);
	}
	
	public static String getDate(String _format) {
		return getDate(getTime(), _format);
	}
	
	public static String getDate(String l, String _format) {
		return getDate((long)Double.parseDouble(l),_format);
	}
	
	public static void imageViewTheme(ImageView iv) {
		iv.setColorFilter(ColorHelper.getInverseColor(iv.getContext()), PorterDuff.Mode.SRC_IN);
	}
	public static void imageViewFilter(ImageView iv, int color) {
		iv.setColorFilter(color, PorterDuff.Mode.SRC_IN);
	}
	public static void imageViewTheme(ImageView... ivs) {
		for(ImageView iv : ivs) {
			imageViewTheme(iv);
		}
	}
	
	public static ArrayList<String> getArrayList(String st) {
		if(st.equals("")) return new ArrayList<>();
		return ((ArrayList<String>) new Gson().fromJson(st, new TypeToken<ArrayList<String>>(){}.getType()));
	}
	public static ArrayList<HashMap<String, Object>> getArrayListHashMap(String st) {
		if(st.equals("")) return new ArrayList<>();
		return ((ArrayList<HashMap<String, Object>>) new Gson().fromJson(st, new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType()));
	}
	public static HashMap<String, Object> getHashMap(String st) {
		if(st.equals("")) return new HashMap<>();
		return ((HashMap<String, Object>) new Gson().fromJson(st, new TypeToken<HashMap<String, Object>>(){}.getType()));
	}
	
	
	public static String getDownloadPath(Context cxt, String name, String format) {
		String path = "/storage/emulated/0/Download/TheChat";
		
		path = path + "/" + name + format;
		
		return path;
	}
	public static String getDownloadPath(Context cxt, String name) {
		String path = "/storage/emulated/0/Download/TheChat";
		
		path = path + "/" + name;
		
		return path;
	}
	
	public static void checkTheme(Context context, boolean isTransparent) {
		
		int nightModeFlags = context.getResources().getConfiguration().uiMode & android.content.res.Configuration.UI_MODE_NIGHT_MASK;
		
		if(isTransparent) {
			if (nightModeFlags == android.content.res.Configuration.UI_MODE_NIGHT_YES) {
				((android.app.Activity) context).setTheme(R.style.MyDarkTransparentTheme);
			} else {
				((android.app.Activity) context).setTheme(R.style.MyTransparentTheme);
			}
		} else {
			if (nightModeFlags == android.content.res.Configuration.UI_MODE_NIGHT_YES) {
				((android.app.Activity) context).setTheme(R.style.DarkAppTheme);
			} else {
				((android.app.Activity) context).setTheme(R.style.AppTheme);
			}
		}
		
	}
    
    public static void createFile(String path) {
		if (!FileUtil.isExistFile(path)) {
			FileUtil.makeDir(path);
		}
	}
    
    
    public static void clearAppData(Context context) {
        ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE)).clearApplicationUserData();
    }
    
    public static abstract class Callback {
        public void onFinished(String path) {}
    }
}
