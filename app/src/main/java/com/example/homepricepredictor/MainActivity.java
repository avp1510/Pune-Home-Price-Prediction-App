package com.example.homepricepredictor;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText location,sqft,bath,bhk;
    Button predict;
    TextView result;
    String url="https://pune-price-prediction.herokuapp.com/predict";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final AutoCompleteTextView loc= (AutoCompleteTextView) findViewById(R.id.location);
        //Button pred= (Button) findViewById(R.id.button);
        //TextView SQFT=(TextView) findViewById(R.id.sqft);
        //TextView Bathroom=(TextView) findViewById(R.id.bath);
        //TextView BHK=(TextView) findViewById(R.id.bhk);
        //ImageView down=(ImageView) findViewById(R.id.dropdown);
        loc.setThreshold(2);


        ArrayAdapter<String>adapter=new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line,locations);
        loc.setAdapter(adapter);
        location=findViewById(R.id.location);
        sqft=findViewById(R.id.sqft);
        bath=findViewById(R.id.bath);
        bhk=findViewById(R.id.bhk);
        predict=findViewById(R.id.button);
        result=findViewById(R.id.textView2);

        predict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject=new JSONObject(response);
                            String data=jsonObject.getString("Cost");
                            float f=Float.parseFloat(data);
                            f= Math.round((float) (f+18.5));
                            result.setText("₹"+f+" Lakhs");
                        }
                        catch (JSONException e){
                            e.printStackTrace();
                        }


                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(MainActivity.this,error.getMessage(),Toast.LENGTH_SHORT).show();

                            }
                        }){
                    @Override
                    protected Map<String,String> getParams(){
                        Map<String,String> params =new HashMap<String,String>();
                        params.put("location",location.getText().toString());
                        params.put("sqft",sqft.getText().toString());
                        params.put("bath",bath.getText().toString());
                        params.put("bhk",bath.getText().toString());

                        return params;

                    }

                };
                RequestQueue queue= Volley.newRequestQueue(MainActivity.this);
                queue.add(stringRequest);


            }
        });



    }
    private static final String[] locations=new String[]{"alandi road", "ambegaon budruk", "anandnagar", "aundh", "aundh road", "balaji nagar", "baner", "baner road", "bhandarkar road", "bhavani peth", "bibvewadi", "bopodi", "budhwar peth", "bund garden road", "camp", "chandan nagar", "dapodi", "deccan gymkhana", "dehu road", "dhankawadi", "dhayari phata", "dhole patil road", "erandwane", "fatima nagar", "fergusson college road", "ganesh peth", "ganeshkhind", "ghorpade peth", "ghorpadi", "gokhale nagar", "gultekdi", "guruwar peth", "hadapsar", "hadapsar industrial estate", "hingne khurd", "jangali maharaj road", "kalyani nagar", "karve nagar", "karve road", "kasba peth", "katraj", "khadaki", "khadki", "kharadi", "kondhwa", "kondhwa budruk", "kondhwa khurd", "koregaon park", "kothrud", "law college road", "laxmi road", "lulla nagar", "mahatma gandhi road", "mangalwar peth", "manik bagh", "market yard", "model colony", "mukund nagar", "mundhawa", "nagar road", "nana peth", "narayan peth", "narayangaon", "navi peth", "padmavati", "parvati darshan", "pashan", "paud road", "pirangut", "prabhat road", "pune railway station", "rasta peth", "raviwar peth", "sadashiv peth", "sahakar nagar", "salunke vihar", "sasson road", "satara road", "senapati bapat road", "shaniwar peth", "shivaji nagar", "shukrawar peth", "sinhagad road", "somwar peth", "swargate", "tilak road", "uruli devachi", "vadgaon budruk", "viman nagar", "vishrant wadi", "wadgaon sheri", "wagholi", "wakadewadi", "wanowrie", "warje", "yerawada"};

}

