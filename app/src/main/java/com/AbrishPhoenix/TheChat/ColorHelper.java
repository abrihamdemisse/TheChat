package com.AbrishPhoenix.TheChat;

import android.content.Context;
import android.graphics.Color;
import android.content.res.TypedArray;

public class ColorHelper {
	
	public static int returnPrimaryColor = 0xFF00ACFF;
	public static int returnBackgroundColor = 0xFFFFFFFF;
	public static int returnSecondBackgroundColor = 0xFFEEEEEE;
	public static int returnThirdBackgroundColor = 0xFFFFFFFF;
	public static int returnInverseColor = 0xFF000000;
	
	public static int getPrimaryColor(Context cxt) {
		return returnPrimaryColor;
	}
	public static int getBackgroundColor(Context cxt) {
		return getAttrs(cxt, R.attr.backgroundColor);
	}
	public static int getSecondBackgroundColor(Context cxt) {
		return getAttrs(cxt, R.attr.secondBackgroundColor);
	}
	public static int getThirdBackgroundColor(Context cxt) {
		return getAttrs(cxt, R.attr.thirdBackgroundColor);
	}
	public static int getInverseColor(Context cxt) {
		return getAttrs(cxt, R.attr.inverseColor);
	}
    
    public static boolean isDarkMode(Context cxt) {
        if(getBackgroundColor(cxt) == 0xFF000000) return true;
        return false;
    }
	
	public static int getAttrs(Context cxt, int i) {
        int[] attrs = {i};
		TypedArray ta = cxt.obtainStyledAttributes(attrs);
		int color = ta.getColor(0, Color.BLACK); // Default value is black
		ta.recycle();
		
		return color;
	}
	
	//String
	
	public static String getStringPrimaryColor(Context cxt) {
		return String.format("#%08X", (0xFFFFFFFF & getPrimaryColor(cxt)));
	}
	public static String getStringBackgroundColor(Context cxt) {
		return String.format("#%08X", (0xFFFFFFFF & getBackgroundColor(cxt)));
	}
	public static String getStringSecondBackgroundColor(Context cxt) {
		return String.format("#%08X", (0xFFFFFFFF & getSecondBackgroundColor(cxt)));
	}
	public static String getStringThirdBackgroundColor(Context cxt) {
		return String.format("#%08X", (0xFFFFFFFF & getThirdBackgroundColor(cxt)));
	}
	public static String getStringInverseColor(Context cxt) {
		return String.format("#%08X", (0xFFFFFFFF & getInverseColor(cxt)));
	}
    
    public static String getStringAttrs(Context cxt, int i) {
		return String.format("#%08X", (0xFFFFFFFF & getAttrs(cxt, i)));
	}
}
