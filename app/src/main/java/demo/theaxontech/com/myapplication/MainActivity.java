package demo.theaxontech.com.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import demo.theaxontech.com.myapplication.networking_library.MySingleton;

public class MainActivity extends AppCompatActivity {

    RequestQueue queue;
    public static final String TAG = "MyNetTag";
    TextView textViewResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewResponse = findViewById(R.id.textViewResponse);

        // Request a string response from the provided URL
        String url ="http://dev.theaxontech.com/ClapperApiNew/api/ApiAdmin/ProductListIos?subCatID=8";

        //GET REQUEST EXAMPLE
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        textViewResponse.setText("Response is: "+ response.substring(0,500));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               textViewResponse.setText("That didn't work!");
            }
        });

        stringRequest.setTag(TAG);

        // Add a request (in this example, called stringRequest) to your RequestQueue.
        //MySingleton.getInstance(this).addToRequestQueue(stringRequest);

        // Get a RequestQueue
        queue = MySingleton.getInstance(this.getApplicationContext()).getRequestQueue();
        queue.add(stringRequest);

    }//onCreate end

    @Override
    protected void onStop () {

        super.onStop();
        if (queue != null) {
            queue.cancelAll(TAG);
        }
    }

    //POST REQUEST EXAMPLE
    void postRequest(String url) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("name", "Alif");
                params.put("domain", "http://itsalif.info");

                return params;
            }
        };

        stringRequest.setTag(TAG);
        queue = MySingleton.getInstance(this.getApplicationContext()).getRequestQueue();
        queue.add(stringRequest);

    }

    //DELETE REQUEST EXAMPLE
    void deleteRequest(String url) {

        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error.

                    }
                }
        );

        stringRequest.setTag(TAG);
        queue = MySingleton.getInstance(this.getApplicationContext()).getRequestQueue();
        queue.add(stringRequest);
    }

}
