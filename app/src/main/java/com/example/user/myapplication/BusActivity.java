package com.example.user.myapplication;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.user.myapplication.bus.Bus;
import com.example.user.myapplication.bus.BusController;
import com.example.user.myapplication.modelviews.BusModelView;
import com.example.user.myapplication.tools.MyBounceInterpolator;

import java.util.Date;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BusActivity extends AppCompatActivity {
    private EditText textFieldNumber;
    private EditText textFieldRoute;
    private ListView listView;
    private BusController busController;
    private int start = 0;
    private int howMany = 2;
    private Button btnBack;
    private Button btnForward;
    private BusModelView busModelView = new BusModelView();
    private String token;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_bus);
            btnBack = (Button) findViewById(R.id.buttonBack);
            btnBack.setText("<");
            btnForward = (Button) findViewById(R.id.buttonForward);
            btnForward.setText(">");
            this.textFieldNumber = findViewById(R.id.textFiledNumber);
            this.textFieldRoute = findViewById(R.id.textFieldRoute);
            this.listView = findViewById(R.id.listView);

            requests();

            token=getIntent().getStringExtra("token");

            busController = ViewModelProviders.of(this, new BusController.Factory(getApplicationContext())).get(BusController.class);


//            for(Bus bus :busController.getAllBusses()){
//                try {
//                    Integer.valueOf(bus.getRoute());
//                    busController.deleteBus(bus.getBusNumber());
//                }catch (NumberFormatException e){
//                    //
//                }
//

            displayBuses();

            if (this.start == 0)
                btnBack.setEnabled(false);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private void displayBuses() {
        busModelView.getBussesPaginated(start, howMany,token).enqueue(new Callback<List<Bus>>() {
            @Override
            public void onResponse(Call<List<Bus>> call, Response<List<Bus>> response) {
                System.out.println("M-am conectat");
                if (response.body() != null) {
                    List<Bus> busses = response.body();
                    if (busses != null) {
                        ArrayAdapter<Bus> arrayAdapter = new ArrayAdapter<Bus>(BusActivity.this, android.R.layout.simple_list_item_1, busses);
                        listView.setAdapter(arrayAdapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Bus>> call, Throwable t) {
                System.out.println("Nu m-am conectat");
                List<Bus> busses = busController.getBusses(howMany, start);
                ArrayAdapter<Bus> arrayAdapter = new ArrayAdapter<Bus>(BusActivity.this, android.R.layout.simple_list_item_1, busses);
                listView.setAdapter(arrayAdapter);
                if (busses.size() == 0)
                    btnForward.setEnabled(false);
            }
        });
    }


    public void emailButton(View view) {
        didTapButton(view,R.id.button3);

        String product = textFieldRoute.getText().toString();
        String number = textFieldNumber.getText().toString();
        Intent i = new Intent(Intent.ACTION_SEND);
        Random random = new Random();
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL, new String[]{"birsanvlad@gmail.com"});
        i.putExtra(Intent.EXTRA_SUBJECT, "Process commad");
        i.putExtra(Intent.EXTRA_TEXT, "Comand placed at: " +new Date()+ "\n The demand is for the route :" +product  + "with the ticket id :"+number+".\n Command No.: "+Math.random()+ "\n You will be contacted for the details");
        try {
            startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }

    public void fillList(View view) {
        didTapButton(view,R.id.buttonForward);
        start += howMany;
        busModelView.getBussesPaginated(start, howMany,token).enqueue(new Callback<List<Bus>>() {
            @Override
            public void onResponse(Call<List<Bus>> call, Response<List<Bus>> response) {
                if (response.body() != null) {
                    List<Bus> busses = response.body();
                    ArrayAdapter<Bus> arrayAdapter = new ArrayAdapter<Bus>(BusActivity.this, android.R.layout.simple_list_item_1, busses);
                    listView.setAdapter(arrayAdapter);
                    btnBack.setEnabled(true);
                    if (busses.size() == 0)
                        btnForward.setEnabled(false);
                }
            }

            @Override
            public void onFailure(Call<List<Bus>> call, Throwable t) {
                List<Bus> busses = busController.getBusses(howMany, start);
                btnBack.setEnabled(true);
                ArrayAdapter<Bus> arrayAdapter = new ArrayAdapter<Bus>(BusActivity.this, android.R.layout.simple_list_item_1, busses);
                listView.setAdapter(arrayAdapter);
                if (busses.size() == 0)
                    btnForward.setEnabled(false);
            }
        });
    }

    public void fillList2(View view) {
        didTapButton(view,R.id.buttonBack);
        start -= howMany;
        busModelView.getBussesPaginated(start, howMany,token).enqueue(new Callback<List<Bus>>() {
            @Override
            public void onResponse(Call<List<Bus>> call, Response<List<Bus>> response) {
                if (response.body() != null) {
                    List<Bus> busses = response.body();
                    ArrayAdapter<Bus> arrayAdapter = new ArrayAdapter<Bus>(BusActivity.this, android.R.layout.simple_list_item_1, busses);
                    listView.setAdapter(arrayAdapter);
                    btnForward.setEnabled(true);
                    if (start == 0)
                        btnBack.setEnabled(false);

                }
            }

            @Override
            public void onFailure(Call<List<Bus>> call, Throwable t) {
                List<Bus> busses = busController.getBusses(howMany, start);
                btnForward.setEnabled(true);
                ArrayAdapter<Bus> arrayAdapter = new ArrayAdapter<Bus>(BusActivity.this, android.R.layout.simple_list_item_1, busses);
                listView.setAdapter(arrayAdapter);
                if (start == 0)
                    btnBack.setEnabled(false);
            }
        });
    }


    public void addBus(View view) {
        didTapButton(view,R.id.buttonAdd);
        Bus bus = new Bus(Integer.parseInt(textFieldNumber.getText().toString()), textFieldRoute.getText().toString(), false);
        busModelView.addBus(bus,token).enqueue(new Callback<Bus>() {
            @Override
            public void onResponse(Call<Bus> call, Response<Bus> response) {
                if (response.body() != null) {
                    Bus bus = response.body();
                    busController.createBus(bus);
                    start = -1 * howMany;
                    fillList(view);
                    btnBack.setEnabled(false);
                }
            }

            @Override
            public void onFailure(Call<Bus> call, Throwable t) {
                Bus bus = new Bus(Integer.parseInt(textFieldNumber.getText().toString()), textFieldRoute.getText().toString(), false);
                busController.createBus(bus);
                start = 0;
                btnBack.setEnabled(false);
                List<Bus> busses = busController.getBusses(howMany, start);
                ArrayAdapter<Bus> arrayAdapter = new ArrayAdapter<Bus>(BusActivity.this, android.R.layout.simple_list_item_1, busses);
                listView.setAdapter(arrayAdapter);

            }
        });
    }


    public void displayChart(View view){
        didTapButton(view,R.id.button4);

        Intent chart = new Intent(this,ChartActivity.class);
        startActivity(chart);
    }

    public void requests() throws InterruptedException {
        Thread requestThread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                List<Bus> busses = busController.getAllBusses();
                for (Bus bus : busses) {
                    if (bus.getAdded().equals(false)) {
                        busModelView.addBus(bus,token).enqueue(new Callback<Bus>() {
                            @Override
                            public void onResponse(Call<Bus> call, Response<Bus> response) {
                                if (response.body() != null) {
                                    busController.updateBus(bus.getBusNumber(), true);
                                }
                            }

                            @Override
                            public void onFailure(Call<Bus> call, Throwable t) {

                            }
                        });
                    }
                }

            }
        });
        requestThread.start();
    }

    public void didTapButton(View view,int id) {
        Button button1 = (Button)findViewById(id);

        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);

        // Use bounce interpolator with amplitude 0.2 and frequency 20
        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 20);
        myAnim.setInterpolator(interpolator);
        myAnim.setDuration(3000);

        button1.startAnimation(myAnim);
    }
}
