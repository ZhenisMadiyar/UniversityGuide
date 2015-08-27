package madiyarzhenis.kz.universityguide.setting;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import madiyarzhenis.kz.universityguide.R;

public class TestUniver extends AppCompatActivity {

    RelativeLayout relativeLayoutQuestion;
    Button buttonYes, buttonNo;
    TextView textViewQuestion;
    String[] questions;
    int countYes;
    int countNo;
    int pos = 0;
    ImageButton resultBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);

        questions = getResources().getStringArray(R.array.test_questions);

        relativeLayoutQuestion = (RelativeLayout) findViewById(R.id.relativeLayoutQuestion);
        textViewQuestion = (TextView) findViewById(R.id.textViewQuestion);
        textViewQuestion.setText(questions[pos]);
        buttonYes = (Button) findViewById(R.id.buttonA);
        buttonNo = (Button) findViewById(R.id.buttonB);
        resultBtn = (ImageButton) findViewById(R.id.imageButtonResult);

        resultBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TestUniver.this, Result.class);
                intent.putExtra("countYes", countYes);
                intent.putExtra("countNo", countNo);
                startActivity(intent);
            }
        });

        buttonYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                countYes++;
                pos++;
                Log.i("pos", pos + "");
                Log.i("array length", questions.length + "");
                if (pos >= questions.length) {
                    buttonNo.setVisibility(View.GONE);
                    buttonYes.setVisibility(View.GONE);
                    resultBtn.setVisibility(View.VISIBLE);
                } else {
                    textViewQuestion.setText(questions[pos]);
//                    YoYo.with(Techniques.Pulse)//ZoomIn
//                            .duration(700)
//                            .playOn(textViewQuestion);
                }
            }
        });

        buttonNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                countNo++;
                pos++;
                if (pos >= questions.length) {
                    buttonNo.setVisibility(View.GONE);
                    buttonYes.setVisibility(View.GONE);
                    resultBtn.setVisibility(View.VISIBLE);
                } else {
                    textViewQuestion.setText(questions[pos]);
//                    YoYo.with(Techniques.DropOut)
//                            .duration(700)
//                            .playOn(textViewQuestion);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
