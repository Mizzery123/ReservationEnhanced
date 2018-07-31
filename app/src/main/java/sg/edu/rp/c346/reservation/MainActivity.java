package sg.edu.rp.c346.reservation;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    EditText etDay;
    EditText etTime;
    Button btnReserveButton;
    Button btnResetButton;
    EditText etname;
    EditText etmobile;
    EditText etsize;
    CheckBox chkSmoke;
    String checked="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etDay = findViewById(R.id.editTextDay);
        etTime = findViewById(R.id.editTextTime);
        btnReserveButton = findViewById(R.id.reserveButton);
        btnResetButton = findViewById(R.id.resetButton);
        etname = findViewById(R.id.enterName);
        etmobile = findViewById(R.id.enterMobile);
        etsize = findViewById(R.id.enterSize);
        chkSmoke = findViewById(R.id.smokeBox);

        etDay.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        etDay.setText("Date: " + dayOfMonth + "/" + (monthOfYear+1) + "/" + year);
                    }
                };

                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int dayofmonth = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog myDateDialog = new DatePickerDialog(MainActivity.this, myDateListener, year, month, dayofmonth);
                myDateDialog.show();
            }
        });

        etTime.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                TimePickerDialog.OnTimeSetListener myTimeListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                        etTime.setText("Time: " + hourOfDay + ":" + minute);
                    }
                };

                Calendar cal = Calendar.getInstance();
                int minute = cal.get(Calendar.MINUTE);
                int hour = cal.get(Calendar.HOUR);

                TimePickerDialog myTimeDialog = new TimePickerDialog(MainActivity.this, myTimeListener, hour, minute ,true);
                myTimeDialog.show();
            }
        });




        btnReserveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater =
                        (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View viewDialog = inflater.inflate(R.layout.input, null);



                if(chkSmoke.isChecked()){
                    checked = "Smoking: Yes";
                }
                else{
                    checked = "Smoking: No";
                }

                AlertDialog.Builder myBuilder = new AlertDialog.Builder(MainActivity.this);
                myBuilder.setView(viewDialog); // Set the view of the dialog
                myBuilder.setTitle("Confirm Your Order");
                myBuilder.setMessage("New Reservation \n" +
                        "Name: " + etname.getText().toString()+ "\n" + checked + "\nSize: " +
                etsize.getText().toString()+ "\n" + etDay.getText().toString()
                + "\n" + etTime.getText().toString());




                myBuilder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which){

                    }
                });
                myBuilder.setNegativeButton("Cancel", null);
                AlertDialog myDialog = myBuilder.create();
                myDialog.show();

            }
        });




        btnResetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                etDay.setText("");
                etTime.setText("");
                etname.setText("");
                etmobile.setText("");
                etsize.setText("");
                chkSmoke.setChecked(false);


            }
        });
    }
    
    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor prefEdit = prefs.edit();
        prefEdit.putString("name", etname.getText().toString());
        prefEdit.putString("mobile", etmobile.getText().toString());
        prefEdit.putString("size", etsize.getText().toString());
        prefEdit.putBoolean("checkboxss", chkSmoke.isChecked());
        prefEdit.putString("date", etDay.getText().toString());
        prefEdit.putString("time", etTime.getText().toString());
        prefEdit.commit();
    }

    @Override
    protected void onResume(){
        super.onResume();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String name = prefs.getString("name","");
        String mobile = prefs.getString("mobile","");
        String size = prefs.getString("size","");
        boolean cb = prefs.getBoolean("checkboxss",false);
        String date = prefs.getString("date","");
        String time = prefs.getString("time", "");

        etname.setText(name);
        etmobile.setText(mobile);
        etsize.setText(size);
        chkSmoke.setChecked(cb);
        etDay.setText(date);
        etTime.setText(time);
    }
}