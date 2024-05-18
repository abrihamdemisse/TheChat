package com.AbrishPhoenix.TheChat;

import android.app.*;
import android.content.*;
import com.google.firebase.database.*;
import com.google.firebase.auth.*;
import java.util.*;
import android.net.*;
import java.io.*;
import android.app.Notification.*;
import java.net.*;

import com.google.firebase.storage.*;
import com.google.android.gms.tasks.*;

public class FirebaseStorageHelper {
	
	public static String filePath = "";
    public static InputStream inputStream = null;
    public static Uri uri = null;
	public static File fl = null;
	public static Context context;
	public static boolean isRunning = false;
	public static UploadTask uploadTask;
	public static FileDownloadTask downloadTask;
    public static int notiId = 0;
	
	public static Listener listener = new Listener() {};
	
	
	public static OnCanceledListener _UserStorage_cancel_listener = new OnCanceledListener() {
		@Override
		public void onCanceled() {
			// Handle cancellation event
			_UserStorage_failure_listener.onFailure(new Exception("User cancelled the process."));
		}
	};
	
	public static OnProgressListener _UserStorage_upload_progress_listener = new OnProgressListener<UploadTask.TaskSnapshot>() {
		@Override
		public void onProgress(UploadTask.TaskSnapshot _param1) {
			double _progressValue = (100.0 * _param1.getBytesTransferred()) / Helper.getSizeFromURI(context, uri);
			listener.onProgress(_progressValue);
		}
	};
	
	public static OnProgressListener _UserStorage_download_progress_listener = new OnProgressListener<FileDownloadTask.TaskSnapshot>() {
		@Override
		public void onProgress(FileDownloadTask.TaskSnapshot _param1) {
			double _progressValue = (100.0 * _param1.getBytesTransferred()) / _param1.getTotalByteCount();
            updateNotification((int)_progressValue);
			listener.onProgress(_progressValue);
		}
	};
	
	public static OnCompleteListener<Uri> _UserStorage_upload_success_listener = new OnCompleteListener<Uri>() {
		@Override
		public void onComplete(Task<Uri> _param1) {
			final String _downloadUrl = _param1.getResult().toString();
			isRunning = false;
			context = null;
			uploadTask = null;
			downloadTask = null;
			listener.onSuccess(_downloadUrl);
			
			/*
			if (task.isSuccessful()) {
			String _downloadUrl = task.getUploadSessionUri().toString();
			isRunning = false;
			context = null;
			uploadTask = null;
			downloadTask = null;
			listener.onSuccess(_downloadUrl);
			} else {
			// Handle the failure case
			Exception e = task.getException();
			// Handle the exception accordingly
			listener.onFailure(e.getMessage());
			}
			*/
		}
	};
	
	
	public static OnSuccessListener _UserStorage_download_success_listener = new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
		@Override
		public void onSuccess(FileDownloadTask.TaskSnapshot _param1) {
			final long _totalByteCount = _param1.getTotalByteCount();
			if(fl != null) {
				// Utils.removeImageFromCache(context, filePath);
			}
			isRunning = false;
			context = null;
			uploadTask = null;
			downloadTask = null;
            updateNotification(100);
			listener.onSuccess();
		}
	};
	
	public static OnSuccessListener _UserStorage_delete_success_listener = new OnSuccessListener() {
		@Override
		public void onSuccess(Object _param1) {
			isRunning = false;
			context = null;
			uploadTask = null;
			downloadTask = null;
			listener.onSuccess();
		}
	};
	
	public static OnFailureListener _UserStorage_failure_listener = new OnFailureListener() {
		@Override
		public void onFailure(Exception _param1) {
			final String _message = _param1.toString();
			isRunning = false;
			context = null;
			uploadTask = null;
			downloadTask = null;
            updateNotification(-1);
			listener.onFailure(_message);
		}
	};
	
	
	public static void uploadFile(Context _context, Uri _uri, InputStream _inputStream, final Listener _listener) {
		if(_listener != null) {
			listener = _listener;
		}
		context = _context;
		isRunning = true;
		/*
		if(uploadFileType.equalsIgnoreCase("Images")) {
		fl = Utils.saveImageToCache(context, (bt == null ? FileUtil.decodeSampleBitmapFromPath(_filePath, 1024, 1024) : bt));
		if(fl != null) {
		filePath = fl.getAbsolutePath();
		} else {
		context = null;
		isRunning = false;
		uploadTask = null;
		downloadTask = null;
		listener.onFailure("Something went wrong.");
		}
		} else {
		filePath = _filePath;
		}
		*/
		
		inputStream = _inputStream;
		uri = _uri;
		
		final StorageReference fileRef = FirebaseStorage.getInstance().getReference( /* FirebaseAuth.getInstance().getCurrentUser().getUid() + "/" + uploadFileType + "/" + uploadType */ );
		// UploadTask uploadTask = fileRef.putFile(Uri.parse(filePath));
		
		/*
		uploadTask.continueWithTask(task -> {
		if (!task.isSuccessful()) {
		failureListener.onFailure(task.getException());
		}
		return fileRef.getDownloadUrl();
		}).addOnCompleteListener(task -> {
		if (task.isSuccessful()) {
		Uri downloadUri = task.getResult();
		successListener.onUploadSuccess(downloadUri.toString());
		} else {
		failureListener.onFailure(task.getException());
		}
		});
		
		*/
		
		
		final String fileName = "upload_at_" + Helper.getDate("MMM_d_YYYY_hh:mm:ss_a_") + Helper.getNameFromURI(context, uri);
		
        final StorageReference fileRefFinal = fileRef.child(fileName);
        
		uploadTask = fileRefFinal.putStream(inputStream);
		
		uploadTask.addOnFailureListener(_UserStorage_failure_listener)
		.addOnProgressListener(_UserStorage_upload_progress_listener)
		.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
			@Override
			public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
				fileRefFinal.getDownloadUrl()
				.addOnCompleteListener(_UserStorage_upload_success_listener);
			}
		})
		.addOnCanceledListener(_UserStorage_cancel_listener)
		.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
			@Override
			public Task<Uri> then(Task<UploadTask.TaskSnapshot> task) throws Exception {
				return fileRef.child(fileName).getDownloadUrl();
			}
		});
	}
	
	public static void downloadFile(Context _context, String downloadLink, String filePath, final Listener _listener) {
		if(_listener != null) {
			listener = _listener;
		}
		context = _context;
		isRunning = true;
		try{
            /*
            
            // Helper.createFile(filePath);
			downloadTask = FirebaseStorage.getInstance().getReferenceFromUrl(downloadLink).getFile(new java.io.File(filePath));
			downloadTask.addOnSuccessListener(_UserStorage_download_success_listener)
            .addOnProgressListener(_UserStorage_download_progress_listener)
			.addOnCanceledListener(_UserStorage_cancel_listener)
			.addOnFailureListener(_UserStorage_failure_listener);
            notiId = (int)Helper.getTime();
            updateNotification(0);
            
            */
            
            
            FirebaseStorage.getInstance().getReferenceFromUrl(downloadLink).getFile(new java.io.File(filePath))
            .addOnSuccessListener(_UserStorage_download_success_listener)
            .addOnFailureListener(_UserStorage_failure_listener)
            .addOnProgressListener(_UserStorage_download_progress_listener);
		}catch(Exception e){
			context = null;
			isRunning = false;
			uploadTask = null;
			downloadTask = null;
			listener.onFailure(e.toString());
		}
	}
    
    public static void updateNotification(int progress) {
		try{
			((NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE)).notify(notiId, showNotification(progress).build());
		}catch(Exception e){}
	}
    
    public static Builder showNotification(int progress) {
		return new Builder(context, TheChatApplication.CHANNEL_ID)
		.setSmallIcon(R.drawable.image_icon_27)
		.setContentTitle((progress != 100 && progress != -1) ? ("Downloading " + String.valueOf(progress)) + "%" : (progress == -1) ? "Download failed!" : "Download completed!")
		// .setContentIntent((downloadProgress != 100) ? null : PendingIntent.getActivity(context, 0, new Intent(context, DownloadManagerActivity.class), 0))
		.setContentText("To \"Download/TheChat\" folder")
		.setPriority(androidx.core.app.NotificationCompat.PRIORITY_DEFAULT)
		.setOnlyAlertOnce(true)
		.setAutoCancel((progress != 100 && progress != -1) ? true : false)
		.setOngoing((progress != 100 && progress != -1) ? true : false)
		.setProgress((int)((progress != 100 && progress != -1) ? 100 : 0),(int)((progress != 100 && progress != -1) ? progress : 0), false);
	}
	
	public static void removeFile(Context _context, String downloadLink, final Listener _listener) {
		if(_listener != null) {
			listener = _listener;
		}
		context = _context;
		isRunning = true;
		try{
			FirebaseStorage.getInstance().getReferenceFromUrl(downloadLink).delete().addOnSuccessListener(_UserStorage_delete_success_listener).addOnFailureListener(_UserStorage_failure_listener);
		}catch(Exception e){
			context = null;
			isRunning = false;
			listener.onFailure(e.getMessage());
		}
	}
	
	public static boolean isRunning() {
		return isRunning && context != null;
	}
	
	public static void cancelRequest() {
		context = null;
		isRunning = false;
		if(uploadTask != null) {
			uploadTask.cancel();
		}
		if(downloadTask != null) {
			downloadTask.cancel();
		}
		uploadTask = null;
		downloadTask = null;
	}
	
	
	public static abstract class Listener {
		public void onProgress(final double percent) {}
		public void onSuccess(final String _downloadUrl) {}
		public void onSuccess() {}
		public void onFailure(final String msg) {}
	}
	
}
