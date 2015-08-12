package madiyarzhenis.kz.universityguide;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

import com.indris.material.RippleView;

import madiyarzhenis.kz.universityguide.city_list.CityActivity;

/**
 * Created by Admin on 14.07.2015.
 */
public class MainActivity  extends ActionBarActivity {
    RippleView testButton, listButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        testButton = (RippleView) findViewById(R.id.btnTest);
        testButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
            }
        });


        listButton = (RippleView) findViewById(R.id.btnUniver);
        listButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, CityActivity.class);
                startActivity(intent);
            }
        });
    }
}
