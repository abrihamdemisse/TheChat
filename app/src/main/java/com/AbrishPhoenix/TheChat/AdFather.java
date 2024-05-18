package com.AbrishPhoenix.TheChat;

import android.content.*;
import android.util.*;
import android.app.*;
import java.util.*;


import com.google.android.gms.ads.*;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.VideoController;
import com.google.android.gms.ads.VideoOptions;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.nativead.MediaView;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdOptions;
import com.google.android.gms.ads.nativead.NativeAdView;


public class AdFather {
	
	public static ArrayList<InterstitialAd> interstitialAdList = new ArrayList<>();
	public static ArrayList<RewardedAd> rewardedAdList = new ArrayList<>();
	
	public static boolean userEarnedReward = false;
	
	public static abstract class AdFatherCallBack {
		public void onRewardedRewarded() {
			
		}
		public void onAdDismissed() {
			
		}
		public void onAdLoadError(LoadAdError error) {
			
		}
		public void onAdError(AdError e) {
			
		}
		public void onNoAd() {
			
		}
		public void onAdShown() {
			
		}
		public void onNoLuck() {
			
		}
	}
	
	public static class Units {
		public static final String BANNER_AD_UNIT = "ca-app-pub-3940256099942544/6300978111";
		public static final String INTERSTITIAL_AD_UNIT = "ca-app-pub-3940256099942544/1033173712";
		public static final String REWARDED_AD_UNIT = "ca-app-pub-3940256099942544/5224354917";
	}
	
	
	public static boolean initialized = false;
	
	public static AdRequest rewardedAdRequest;
	public static AdRequest interstitialAdRequest;
	
	public static int interstitialAdLimit = 2;
	public static int rewardedAdLimit = 2;
	public static AdFatherCallBack callback = new AdFatherCallBack() {};
	
	public static void  initializeAds(final Context context) {
		initialized = true;
		addNewInterstitialAd(context);
		addNewRewardedAd(context);
	}
	
	public static void addNewInterstitialAd(final Context context) {
		if(interstitialAdList.size() < interstitialAdLimit) {
			interstitialAdRequest = new AdRequest.Builder().build();
			
			InterstitialAd.load(context, Units.INTERSTITIAL_AD_UNIT, interstitialAdRequest, new InterstitialAdLoadCallback() {
				@Override
				public void onAdLoaded(InterstitialAd _interstitialAd) {
					interstitialAdList.add(_interstitialAd);
					addNewInterstitialAd(context);
				}
				@Override
				public void onAdFailedToLoad(LoadAdError loadAdError) {
					// interstitialAd = null;
					// if(loadAdError.getCode() == AdRequest.ERROR_CODE_NETWORK_ERROR) return;
					// initializeInterstitial(context);
					// Utils.showMessage(context, "LoadInterstitialAd error : " + loadAdError.toString());
				}
			});
		}
	}
	
	public static void addNewRewardedAd(final Context context) {
		if(rewardedAdList.size() < rewardedAdLimit) {
			rewardedAdRequest = new AdRequest.Builder().build();
			
			RewardedAd.load(context, Units.REWARDED_AD_UNIT, rewardedAdRequest, new RewardedAdLoadCallback() {
				@Override
				public void onAdLoaded(RewardedAd _rewardedAd) {
					rewardedAdList.add(_rewardedAd);
					addNewRewardedAd(context);
				}
				@Override
				public void onAdFailedToLoad(LoadAdError loadAdError) {
					// interstitialAd = null;
					// if(loadAdError.getCode() == AdRequest.ERROR_CODE_NETWORK_ERROR) return;
					// initializeInterstitial(context);
					// Utils.showMessage(context, "LoadRewardedAd error : " + loadAdError.toString());
				}
			});
		}
	}
	
	public static void showRewardedAd(final Context context, final AdFatherCallBack c) {
		callback = c;
		if(callback == null) {
			callback = new AdFatherCallBack() {};
		}
		
		if(!isRewardReady()) {
			addNewRewardedAd(context);
			callback.onNoAd();
			return;
		}
		
		FullScreenContentCallback fc = new FullScreenContentCallback() {
			@Override
			public void onAdShowedFullScreenContent() {
				callback.onAdShown();
			}
			@Override
			public void onAdFailedToShowFullScreenContent(AdError adError) {
				callback.onAdError(adError);
			}
			@Override
			public void onAdDismissedFullScreenContent() {
				callback.onAdDismissed();
				rewardedAdList.remove(0);
				addNewRewardedAd(context);
				if(userEarnedReward) {
					userEarnedReward = false;
					callback.onRewardedRewarded();
				}
			}
		};
		
		OnUserEarnedRewardListener listener = new OnUserEarnedRewardListener() {
			@Override
			public void onUserEarnedReward(RewardItem rewardItem) {
				userEarnedReward = true;
			}
		};
		
		showRewardedAd(context, fc, listener);
	}
	
	public static void showRewardedAd(Context context, FullScreenContentCallback fc, OnUserEarnedRewardListener listener) {
		rewardedAdList.get(0).setFullScreenContentCallback(fc);
		rewardedAdList.get(0).show((Activity)context, listener);
	}
	
	
	
	public static void showInterstitialAd(final Context context, final AdFatherCallBack c) {
		callback = c;
		if(callback == null) {
			callback = new AdFatherCallBack() {};
		}
		
		if(!isInterstitialReady()) {
			addNewInterstitialAd(context);
			callback.onNoAd();
			return;
		}
		
		if(!Luck.getLucky()) {
			callback.onNoLuck();
			return;
		}
		
		FullScreenContentCallback fc = new FullScreenContentCallback() {
			@Override
			public void onAdShowedFullScreenContent() {
				callback.onAdShown();
			}
			@Override
			public void onAdFailedToShowFullScreenContent(AdError adError) {
				callback.onAdError(adError);
			}
			@Override
			public void onAdDismissedFullScreenContent() {
				callback.onAdDismissed();
				interstitialAdList.remove(0);
				addNewInterstitialAd(context);
			}
		};
		showInterstitialAd(context, fc);
	}
	
	public static void showInterstitialAd(Context context, FullScreenContentCallback fc) {
		interstitialAdList.get(0).setFullScreenContentCallback(fc);
		interstitialAdList.get(0).show((Activity)context);
	}
	
	
	public static boolean isRewardReady() {
		if(rewardedAdList.size() != 0) {
			return true;
		}
		return false;
	}
	
	public static boolean isInterstitialReady() {
		if(interstitialAdList.size() != 0) {
			return true;
		}
		return false;
	}
	
	public static boolean isThereAd() {
		if(rewardedAdList.size() != 0) {
			return true;
		}
		if(interstitialAdList.size() != 0) {
			return true;
		}
		return false;
	}
	
	public static void destroy(Context context) {
		/*
		for(RewardedAd ad : rewardedAdList) {
		ad.destroy(context);
		}
		for(InterstitialAd ad : interstitialAdList) {
		ad.destroy(context);
		}
		*/
		try{
			interstitialAdList.clear();
		}catch(Exception e){}
		
		try{
			rewardedAdList.clear();
		}catch(Exception e){}
	}
	
	
	
	public static class Luck {
		
		public static long innerLastSaw = 0;
		public static boolean dontShowAds = false;
		
		public static boolean getLucky() {
			return getLucky((long)((innerLastSaw != 0) ? innerLastSaw : -1));
		}
		
		public static boolean getLucky(final long lastSaw) {
			if(dontShowAds) return false;
			
			if(lastSaw != -1) {
                // ad shown each 2 min
				if((Helper.getTime() - lastSaw) >= 120000) {
					innerLastSaw = Helper.getTime();
					return true;
				} else {
					return false;
				}
			} else {
				boolean v = afterProcess();
				if(v) {
					innerLastSaw = Helper.getTime();
				}
				return v;
			}
			
		}
		
		public static void resetLuck() {
			innerLastSaw = 1;
		}
		
		public static boolean afterProcess() {
			return SketchwareUtil.getRandom(0, 3) == SketchwareUtil.getRandom(0, 3);
		}
		
	}
}
