package nyc.c4q.androidtest_unit4final;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private ColorAdapter adapter;
    protected HashMap<String, String> colorDict;
    protected List<String> colorsList;
    private List<ColorModel> modelList= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        colorDict = new HashMap<>();
        colorDict.put("indigo", "#4b0082");
        colorDict.put("green", "#00ff00");
        colorDict.put("blue", "#0000ff");
        colorDict.put("red", "#ff0000");
        // TODO: adding all the colors and their values would be tedious, instead fetch it from the url below
        // https://raw.githubusercontent.com/operable/cog/master/priv/css-color-names.json

        colorsList = new ArrayList<>();
        String[] names = new String[] {"blue", "red", "purple", "indigo", "orange", "brown", "black", "green"};
        for(String n: names) colorsList.add(n);

        RecyclerView recyclerView = findViewById(R.id.rv);
        adapter = new ColorAdapter(colorsList, colorDict);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        try {
            JSONObject jsonObject=new JSONObject(Constant.VALUES);
            JSONArray colors=jsonObject.getJSONArray("colors");
            List<String> colorValues= new ArrayList<>();
            for (int i = 0; i < colors.length(); i++) {
                String rainbow=colors.get(i).toString();
                colorValues.add(rainbow);
                Log.d("mainactivity",rainbow);
                Retrofit retrofit= new Retrofit.Builder().baseUrl("https://raw.githubusercontent.com")
                        .addConverterFactory(GsonConverterFactory.create()).build();
                ColorService service=retrofit.create(ColorService.class);
                Call<ColorModel> getModel=service.getModel();
                getModel.enqueue(new Callback<ColorModel>() {
                    @Override
                    public void onResponse(Call<ColorModel> call, Response<ColorModel> response) {
                        modelList.add(response.body());
                        Log.d("mainactivity",modelList.get(0).getColorCode().get(0));

                    }

                    @Override
                    public void onFailure(Call<ColorModel> call, Throwable t) {

                    }
                });



            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // TODO: Add options menu with the item "Info" which is always visible
    // TODO: When "Info" menu item is clicked, display the fragment InfoFragment
    // TODO: If InfoFragment is already visible and I click "Info", remove InfoFragment from the view.
    // Link to creating options menu: https://github.com/C4Q/AC-Android/tree/v2/Android/Lecture-9-Menus-and-Navigation#anatomy-of-menu-xml
}
