package box.chronos.userk.chronos.serverRequest;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import box.chronos.userk.chronos.callbacks.IAsyncResponse;
import box.chronos.userk.chronos.utils.AppController;
import box.chronos.userk.chronos.utils.ConnectionUtility;

/**
 * Created by francesco on 20/03/2017.
 */

public class RestInteraction<K> {

    private Context mContext;
    private ProgressHUD mProgressHUD;
    public IAsyncResponse iAsyncResponse;
    public IAsyncResponseWithViews iAsyncResponseWithViews;

    public RestInteraction(Context context) {
        mContext = context;
    }

    public ImageView imageView;
    public TextView tv_likeCount;
    public K model;
    //public Notifications onModel model1;

    public void setCallBack(IAsyncResponse signup_Email) {
        iAsyncResponse = signup_Email;
    }

    public void setCallBackWithViews(IAsyncResponseWithViews signup_Email) { iAsyncResponseWithViews = signup_Email; }

    public void SetDataViewsToKeepStateData(ImageView imageView, TextView tv_likeCount, K model) {
        this.imageView = imageView;
        this.tv_likeCount = tv_likeCount;
        this.model = model;
    }

    public void makeServiceRequest(final String serviceURL,
                                   final Map<String, String> params, final String serviceTAG,
                                   String actionDialog) {
        if (ConnectionUtility.isConnectedToNetwork(mContext)) {
            Log.e(serviceTAG, serviceURL + params);
            getProgressDialog(mContext, actionDialog);
            StringRequest jsonObjReq = new StringRequest(Request.Method.POST, serviceURL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    if (mProgressHUD != null)
                        mProgressHUD.dismiss();

                    Log.e(serviceURL + " Url Response== ", response);

                    iAsyncResponse.onRestInteractionResponse(response);
                    if (iAsyncResponseWithViews != null)
                        iAsyncResponseWithViews.onRestIntractionResponseWithViews(response, RestInteraction.this.imageView, RestInteraction.this.tv_likeCount, RestInteraction.this.model);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    if (mProgressHUD != null)
                        mProgressHUD.dismiss();

                    Log.e(serviceURL + " Url Response== ", String.valueOf(error));

                    NetworkResponse networkResponse = error.networkResponse;
                    if (networkResponse != null) {
                        Log.e("Volley", "Error. HTTP Status Code:" + networkResponse.statusCode);
                        iAsyncResponse.onRestInteractionError(error.getMessage());
                    }

                    if (error instanceof TimeoutError) {
                        Log.e("Volley", "TimeoutError");
                        iAsyncResponse.onRestInteractionError("TimeoutError! Please try again");
                    } else if (error instanceof NoConnectionError) {
                        Log.e("Volley", "NoConnectionError");
                        iAsyncResponse.onRestInteractionError("NoConnectionError! Please try again");
                    } else if (error instanceof AuthFailureError) {
                        Log.e("Volley", "AuthFailureError");
                        iAsyncResponse.onRestInteractionError("AuthFailureError! Please try again");
                    } else if (error instanceof ServerError) {
                        Log.e("Volley", "ServerError");
                        iAsyncResponse.onRestInteractionError("ServerError! Please try again");
                    } else if (error instanceof NetworkError) {
                        Log.e("Volley", "NetworkError");
                        iAsyncResponse.onRestInteractionError("NetworkError! Please try again");
                    } else if (error instanceof ParseError) {
                        Log.e("Volley", "ParseError");
                        iAsyncResponse.onRestInteractionError("ParseError! Please try again");
                    }
                }
            }) {

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Content-Type",
                            "application/x-www-form-urlencoded; charset=utf-8");
                    // headers.put("apiKey", "xxxxxxxxxxxxxxx");
                    return headers;
                }

                @Override
                protected Map<String, String> getParams()
                        throws AuthFailureError {
                    return params;
                }
            };

            jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(30000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            // Adding request to request queue
            AppController.getInstance().addToRequestQueue(jsonObjReq, serviceTAG);
        } else {
            //Utility.showAlertDialog(mContext, "Network not available");
            Toast.makeText(mContext, "Network not available", Toast.LENGTH_SHORT).show();
        }
    }

    public ProgressHUD getProgressDialog(Context mContext, String actionDialog) {

        if (actionDialog.equalsIgnoreCase("Dialog")) {

            mProgressHUD = ProgressHUD.show(mContext, "Please wait...", true, true, null);
        }
        return mProgressHUD;
    }

    /********
     * single dialog request
     *************/
    public ProgressHUD makeServiceRequestReturnDialog(String serviceURL,
                                                      final Map<String, String> params, final String serviceTAG,
                                                      String actionDialog) {

        if (ConnectionUtility.isConnectedToNetwork(mContext)) {

            getProgressDialog(mContext, actionDialog);
            StringRequest jsonObjReq = new StringRequest(Request.Method.POST,
                    serviceURL, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {

                    if (mProgressHUD != null)
                        mProgressHUD.dismiss();
                    iAsyncResponse.onRestInteractionResponse(response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    if (mProgressHUD != null)
                        mProgressHUD.dismiss();
                    iAsyncResponse.onRestInteractionError(error
                            .getMessage());
                }
            }) {

                @Override
                protected Map<String, String> getParams()
                        throws AuthFailureError {
                    return params;
                }
            };

            jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(5000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            AppController.getInstance().addToRequestQueue(jsonObjReq,
                    serviceTAG);
        } else {

            //Utility.showAlertDialog(mContext, "Network not available");
            Toast.makeText(mContext, "Network not available", Toast.LENGTH_SHORT).show();

        }
        return mProgressHUD;
    }

    public void makeServiceRequestGET(String serviceURL,
                                      List<NameValuePair> params, final String serviceTAG,
                                      String actionDialog) {

        if (ConnectionUtility.isConnectedToNetwork(mContext)) {

            mProgressHUD = getProgressDialog(mContext, actionDialog);

            String paramString = URLEncodedUtils.format(params, "utf-8");
            serviceURL += "?" + paramString;

            Log.v("Urltocheck", "" + serviceURL);

            final String finalServiceURL = serviceURL;
            StringRequest jsonObjReq = new StringRequest(Request.Method.GET,
                    serviceURL, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    //mProgressHUD.dismiss();

                    if (mProgressHUD != null)
                        mProgressHUD.dismiss();

                    Log.e(finalServiceURL + " Url Response== ", response);
                    iAsyncResponse.onRestInteractionResponse(response);
                   /* if (mProgressHUD.isShowing())
                        mProgressHUD.dismiss();*/
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //	mProgressHUD.dismiss();
                    if (mProgressHUD != null)
                        mProgressHUD.dismiss();
                    Log.e(finalServiceURL + " Url Error== ", String.valueOf(error));

                    NetworkResponse networkResponse = error.networkResponse;
                    if (networkResponse != null) {
                        Log.e("Volley", "Error. HTTP Status Code:" + networkResponse.statusCode);
                        iAsyncResponse.onRestInteractionError(error
                                .getMessage());
                    }

                    if (error instanceof TimeoutError) {
                        Log.e("Volley", "TimeoutError");
                        iAsyncResponse.onRestInteractionError("TimeoutError! Please try again");
                    } else if (error instanceof NoConnectionError) {
                        Log.e("Volley", "NoConnectionError");
                        iAsyncResponse.onRestInteractionError("NoConnectionError! Please try again");
                    } else if (error instanceof AuthFailureError) {
                        Log.e("Volley", "AuthFailureError");
                        iAsyncResponse.onRestInteractionError("AuthFailureError! Please try again");
                    } else if (error instanceof ServerError) {
                        Log.e("Volley", "ServerError");
                        iAsyncResponse.onRestInteractionError("ServerError! Please try again");
                    } else if (error instanceof NetworkError) {
                        Log.e("Volley", "NetworkError");
                        iAsyncResponse.onRestInteractionError("NetworkError! Please try again");
                    } else if (error instanceof ParseError) {
                        Log.e("Volley", "ParseError");
                        iAsyncResponse.onRestInteractionError("ParseError! Please try again");
                    }

                   /* if (mProgressHUD.isShowing())
                        mProgressHUD.dismiss();*/
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Content-Type",
                            "application/x-www-form-urlencoded; charset=utf-8");
                    // headers.put("apiKey", "xxxxxxxxxxxxxxx");
                    return headers;
                }

            };


            jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(10000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            jsonObjReq.setShouldCache(false);
            // Adding request to request queue
            AppController.getInstance().addToRequestQueue(jsonObjReq,
                    serviceTAG);
        } else {
            //Utility.showAlertDialog((Activity) mContext, "Please connect to internet connection");
            Toast.makeText(mContext, "Please connect to internet connection", Toast.LENGTH_SHORT).show();
        }
    }
}
