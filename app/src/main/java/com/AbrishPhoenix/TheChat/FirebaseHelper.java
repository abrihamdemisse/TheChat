package com.AbrishPhoenix.TheChat;

import android.app.*;
import android.content.*;
import com.google.firebase.database.*;
import com.google.firebase.auth.*;
import android.app.Notification.*;
import java.util.*;

public class FirebaseHelper {
	
	public static DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
	
	public static void addMeOnline() {
		addMeOnline(false);
		mDatabase.child("Onlines").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).onDisconnect().setValue("0");
	}
	
	public static void addMeOnline(boolean isReady) {
		mDatabase.child("Onlines").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue((isReady) ? "2" : "1");
	}
	
	public static void onlineState(final Context context, final String uid, final Listener listener) {
		mDatabase.child("Onlines").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot dataSnapshot) {
				if (dataSnapshot.exists()) {
					listener.onReturned(dataSnapshot.getValue().toString());
				} else {
					listener.onReturned("0");
				}
			}
			
			@Override
			public void onCancelled(DatabaseError databaseError) {
				listener.onError(databaseError.toString());
			}
		});
	}
	
	public static void startChat(final String to) {
		mDatabase.child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("new_chat").setValue(to);
		mDatabase.child("Users").child(to).child("new_chat").setValue(FirebaseAuth.getInstance().getCurrentUser().getUid());
	}
	public static void closeChat(final String to) {
		mDatabase.child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("new_chat").setValue("");
		mDatabase.child("Users").child(to).child("new_chat").setValue("");
	}
    
    public static void deleteUserData() {
        mDatabase.child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).removeValue();
        mDatabase.child("Onlines").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).removeValue();
    }
	
	public static void onlineListener(final Context context, final Listener listener) {
		DatabaseReference ref = mDatabase.child("Onlines");
		
		ref.addChildEventListener(new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				handle(_param1, _param2);
			}
			
			@Override
			public void onChildChanged(DataSnapshot _param1, String _param2) {
				handle(_param1, _param2);
			}
			
			@Override
			public void onChildMoved(DataSnapshot _param1, String _param2) {
				
			}
			
			@Override
			public void onChildRemoved(DataSnapshot _param1) {
                
			}
			
			@Override
			public void onCancelled(DatabaseError _param1) {
				final int _errorCode = _param1.getCode();
				final String _errorMessage = _param1.getMessage();
                listener.onError(_errorMessage);
				
			}
			
			public void handle(DataSnapshot _param1, String _param2) {
				final String _childKey = _param1.getKey();
				
				String str = "";
				try{
					str = _param1.getValue().toString();
				}catch(Exception e){}
				if(str.equals("")) return;
				
				listener.onReturned(_childKey, str);
			}
		});
		
	}
	
	public static void newMessageListener(final Context context, final Listener listener) {
		DatabaseReference ref = mDatabase.child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("last_message");
		ref.setValue("");
		
		ref.addValueEventListener(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot dataSnapshot) {
				handle(dataSnapshot);
			}
			
			@Override
			public void onCancelled(DatabaseError databaseError) {
				// Handle any errors that occur
                listener.onError(databaseError.toString());
			}
			
			public void handle(DataSnapshot _param1) {
                // Logger.log(context, "New message called top");
				String str = "";
				try{
					str = _param1.getValue(String.class);
				}catch(Exception e){
					// Logger.log(context, e.getMessage());
				}
				if(str == null|| str.equals("")) return;
                
                // Logger.log(context, "New message called bottom");
				
				listener.onNewMessage(str);
			}
		});
	}
	
	public static void newChatListener(final Context context, final Listener listener) {
		DatabaseReference ref = mDatabase.child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("new_chat");
		ref.setValue("");
		
		ref.addValueEventListener(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot dataSnapshot) {
				handle(dataSnapshot);
			}
			
			@Override
			public void onCancelled(DatabaseError databaseError) {
				// Handle any errors that occur
                listener.onError(databaseError.toString());
			}
			
			public void handle(DataSnapshot _param1) {
                // Logger.log(context, "New chat called top");
				String str = "";
				try{
					str = _param1.getValue(String.class);
				}catch(Exception e){
					// Logger.log(context, e.getMessage());
				}
				if(str.equals("")) {
					listener.onChatClosed();
					return;
				}
				// Logger.log(context, "New chat called bottom with " + str);
				listener.onNewChat(str);
			}
		});
	}
	
	
	public static void sendMessage(Context context, String text, String to) {
		String[] users = {FirebaseAuth.getInstance().getCurrentUser().getUid(), to};
		Arrays.sort(users);
		String reference = users[0] + "" + users[1];
		Logger.log(context, text);
		mDatabase.child("Users").child(to).child("last_message").setValue(SecHelper.encryptedStringKey(SecHelper.encrypt(text), Helper.getArranged(reference)));
	}
	
	
	public static void doesNodeExists(final Context context, String nodePath, final Listener listener) {
		FirebaseDatabase.getInstance().getReference(nodePath).addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot dataSnapshot) {
				if (dataSnapshot.exists()) {
					listener.onNodeExists(true);
				} else {
					listener.onNodeExists(false);
				}
			}
			
			@Override
			public void onCancelled(DatabaseError databaseError) {
				listener.onError(databaseError.toString());
			}
		});
	}
	
	public static abstract class Listener {
		public void onNodeExists(boolean exists) {}
		public void onError(String error) {}
		public void onReturned(String onlineState) {}
		
		public void onNewChat(String uid) {}
		public void onChatClosed() {}
		public void onNewMessage(String msg) {}
		public void onReturned(String ons, String sf) {}
	}
	
}
