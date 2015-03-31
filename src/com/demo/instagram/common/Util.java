package com.demo.instagram.common;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.demo.instagram.R;

/**
 * 
 * Purpose: This class is use for common function declaration and implementation
 * 
 * @author Vandit Patel
 * @version 1.0
 * @date 18/02/15
 */
public class Util {

	/**
	 * Static method to show Same Design Dialog By passing context and specific
	 * message
	 * 
	 * @param context
	 * @param msg
	 * @param hasFinished
	 */

	public static void displayDialog(final Context context, final String msg,
			final boolean hasFinished) {
		final AlertDialog.Builder dialog = new AlertDialog.Builder(context);
		dialog.setCancelable(false);
		dialog.setTitle(context.getString(R.string.app_name));
		dialog.setMessage(msg);
		dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				dialog.dismiss();
				if (hasFinished) {
					((Activity) context).finish();
				}
			}
		});
		dialog.show();
	}
}
