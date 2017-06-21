package box.chronos.userk.brain.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.Locale;

import box.chronos.userk.brain.R;
import box.chronos.userk.brain.utils.constants.AppConstant;
import box.chronos.userk.brain.utils.position.GpsTracker;

import static box.chronos.userk.brain.utils.constants.AppConstant.TOAST_DURATION;

/**
 * Created by francesco on 17/03/2017.
 */

public class Utility {

    /**
     * Shows alert dialog with provided message.
     *
     * @param error   Error message to be displayed.
     * @param context Application context.
     */
    private Context _context;
    private String latitudeValue = "0.0";
    private String longitudeValue = "0.0";
    private String locationValue = "";
    private GpsTracker gps;
    private CharSequence text;

    // constructor
    public Utility(Context context) {
        this._context = context;
    }

    public static void showAlertDialog(Context context, String error) {
        try {
            final Dialog showDialog = new Dialog(context,
                    android.R.style.Theme_Translucent_NoTitleBar);

            // Setting dialog view
            Window window = showDialog.getWindow();
            window.setGravity(Gravity.TOP);
            window.getAttributes().windowAnimations = R.style.ActionDialogAnimation;
            window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            showDialog.setTitle(null);
            showDialog.setContentView(R.layout.custom_alert_dialog);
            showDialog.setCancelable(true);

            TextView TextInfo = (TextView) showDialog.findViewById(R.id.custom_alert_dialog_info);
            TextInfo.setText(error);
            showDialog.show();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    showDialog.dismiss();
                }
            }, TOAST_DURATION);
        } catch (Exception exp) {
            exp.printStackTrace();
        }
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        final float totalPixels = width * height;
        final float totalReqPixelsCap = reqWidth * reqHeight * 2;
        while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
            inSampleSize++;
        }

        return inSampleSize;
    }

    public static void HideKeyboard(Context context) {
        @SuppressWarnings("static-access")
        InputMethodManager inputMethodManager = (InputMethodManager) context
                .getSystemService(context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            if (((Activity) context).getCurrentFocus() != null) {
                inputMethodManager.hideSoftInputFromWindow(((Activity) context)
                        .getCurrentFocus().getWindowToken(), 0);
            }
        }
    }

    public static void ShowKeyboard(Context context) {
        InputMethodManager inputMethodManager = (InputMethodManager) context
                .getSystemService(context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            if (((Activity) context).getCurrentFocus() != null) {
                inputMethodManager.showSoftInput(((Activity) context)
                        .getCurrentFocus(), 0);
            }
        }
    }

    public void requestForGps(Context context) {

        if (gps == null)
            gps = new GpsTracker(context);

        if (gps.canGetLocation()) {

            locationValue = gps.getAddress();
            latitudeValue = String.valueOf(gps.getLatitude());
            longitudeValue = String.valueOf(gps.getLongitude());
        }
    }

    // using full image dialog for showing local image
    public static void dialogOpenLocalImage(final Context context, String imagePath) {
        final ProgressBar progress;
        final Dialog fullScreen = new Dialog(context, R.style.DialogAnimationUpDown);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.full_screen_view, null);
        Window dialog_window = fullScreen.getWindow();
        dialog_window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ImageView img_fullScreen = (ImageView) view.findViewById(R.id.img_fullScreen);
        final RelativeLayout rl_back = (RelativeLayout) view.findViewById(R.id.rl_back);

        rl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fullScreen.dismiss();
            }
        });

        Uri uri = Uri.fromFile(new File(imagePath));
        Picasso.with(context)
                .load(uri)
                .placeholder(R.drawable.place_holder) //this is optional the image to display while the url image is downloading
                .error(R.drawable.place_holder)//this is also optional if some error has occurred in downloading the image this image would be displayed
                .into(img_fullScreen);

        fullScreen.setContentView(view);
        fullScreen.show();
    }

    // using full image dialog
    public static void dialogOpen(final Context context, String imagePath) {
        final ProgressBar progress;
        final Dialog fullScreen = new Dialog(context, R.style.DialogAnimationUpDown);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.full_screen_view, null);
        Window dialog_window = fullScreen.getWindow();
        dialog_window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ImageView img_fullScreen = (ImageView) view.findViewById(R.id.img_fullScreen);
        final RelativeLayout rl_back = (RelativeLayout) view.findViewById(R.id.rl_back);

        rl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fullScreen.dismiss();
            }
        });
        Picasso.with(context)
                .load(imagePath)
                .placeholder(R.drawable.place_holder) //this is optional the image to display while the url image is downloading
                .error(R.drawable.place_holder)//this is also optional if some error has occurred in downloading the image this image would be displayed
                .into(img_fullScreen);

        fullScreen.setContentView(view);
        fullScreen.show();
    }

    // using full image dialog
    public static void dialogOpenForFileIMage(final Context context, File imagePath) {
        final ProgressBar progress;
        final Dialog fullScreen = new Dialog(context, R.style.DialogAnimationUpDown);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.full_screen_view, null);
        Window dialog_window = fullScreen.getWindow();
        dialog_window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ImageView img_fullScreen = (ImageView) view.findViewById(R.id.img_fullScreen);
        final RelativeLayout rl_back = (RelativeLayout) view.findViewById(R.id.rl_back);

        rl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fullScreen.dismiss();
            }
        });

        progress = (ProgressBar) view.findViewById(R.id.progress);
       /* Glide.with(context).load(imagePath).listener(new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                return false;
            }
            @Override
            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                progress.setVisibility(View.GONE);
                return false;
            }
        }).into(img_fullScreen);*/

        Picasso.with(context)
                .load(imagePath)
                .placeholder(R.drawable.place_holder) //this is optional the image to display while the url image is downloading
                .error(R.drawable.place_holder)//this is also optional if some error has occurred in downloading the image this image would be displayed
                .into(img_fullScreen);

        fullScreen.setContentView(view);
        fullScreen.show();
    }

    public static float getPricePercentage(float cost, float discount) {
        float finalValue = 0;
        try {
            finalValue = (cost - ((cost / 100) * discount));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return finalValue;
    }

    /*
    * getting screen width
    */
    public int getScreenWidth(Context context) {
        int columnWidth;
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();

        final Point point = new Point();
        try {
            display.getSize(point);
        } catch (java.lang.NoSuchMethodError ignore) { // Older device
            point.x = display.getWidth();
            point.y = display.getHeight();
        }
        columnWidth = point.x;
        return columnWidth;
    }

    // Reading file paths from SDCard
    public ArrayList<String> getFilePaths() {
        ArrayList<String> filePaths = new ArrayList<String>();

        File directory = new File(
                android.os.Environment.getExternalStorageDirectory()
                        + File.separator + AppConstant.PHOTO_ALBUM);

        // check for directory
        if (directory.isDirectory()) {
            // getting list of file paths
            File[] listFiles = directory.listFiles();

            // Check for count
            if (listFiles.length > 0) {

                // loop through all files
                for (int i = 0; i < listFiles.length; i++) {

                    // get file path
                    String filePath = listFiles[i].getAbsolutePath();

                    // check for supported file extension
                    if (IsSupportedFile(filePath)) {
                        // Add image path to array list
                        filePaths.add(filePath);
                    }
                }
            } else {
                // image directory is empty
                Toast.makeText(
                        _context,
                        AppConstant.PHOTO_ALBUM
                                + " is empty. Please load some images in it !",
                        Toast.LENGTH_LONG).show();
            }

        } else {
            AlertDialog.Builder alert = new AlertDialog.Builder(_context);
            alert.setTitle("Error!");
            alert.setMessage(AppConstant.PHOTO_ALBUM
                    + " directory path is not valid! Please set the image directory name AppConstant.java class");
            alert.setPositiveButton("OK", null);
            alert.show();
        }

        return filePaths;
    }

    // Check supported file extensions
    private boolean IsSupportedFile(String filePath) {
        String ext = filePath.substring((filePath.lastIndexOf(".") + 1),
                filePath.length());
        if (AppConstant.FILE_EXTN
                .contains(ext.toLowerCase(Locale.getDefault())))
            return true;
        else
            return false;
    }
}
