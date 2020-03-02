package id.putraprima.skorbola;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;

public class MatchActivity extends AppCompatActivity implements Serializable {
    private TextView homeText;
    private TextView awayText;
    private TextView homeScoreName;
    private TextView awayScoreName;
    private ImageView homeGambar;
    private ImageView awayGambar;
    private TextView scoreH;
    private TextView scoreA;
    private int scoreHome;
    private int scoreAway;
    String returnHome ;
    String returnAway ;
    String result,message,scoreName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);
        //TODO
        homeText = findViewById(R.id.txt_home);
        awayText = findViewById(R.id.txt_away);
        homeGambar =findViewById(R.id.home_logo);
        awayGambar =findViewById(R.id.away_logo);
        scoreH = findViewById(R.id.score_home);
        scoreA = findViewById(R.id.score_away);
        homeScoreName = findViewById(R.id.score_home_name);
        awayScoreName = findViewById(R.id.score_away_name);

        scoreHome=0;
        scoreH.setText("0");
        scoreAway=0;
        scoreA.setText("0");
        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            // TODO: display value here
            Bundle extra = getIntent().getExtras();

            Bitmap bmp = extra.getParcelable("imageHome");
            Bitmap bmp2 = extra.getParcelable("imageAway");

            homeGambar.setImageBitmap(bmp);
            awayGambar.setImageBitmap(bmp2);

            homeText.setText(extras.getString("home"));
            awayText.setText(extras.getString("away"));
        }
    }

    public void addScoreHome(View view){
        Intent intent = new Intent(this, ScorerActivity.class);
        startActivityForResult(intent, 1);
    }
    public void addScoreAway(View view){
        Intent intent = new Intent(this, ScorerActivity.class);
        startActivityForResult(intent, 2);

    }
    public void cekHasil(View view){
        if (scoreHome > scoreAway){
            result = scoreHome + " - " + scoreAway;
            message = "Tim " + homeText.getText().toString() + " adalah Pemenang ";
            scoreName = homeScoreName.getText().toString();
        } else if(scoreHome < scoreAway){
            result = scoreHome + " - " + scoreAway;
            message = "Tim " + awayText.getText().toString() + " adalah Pemenang";
            scoreName = awayScoreName.getText().toString();
        } else {
            result = scoreHome + " - " + scoreAway;
            message = "DRAW";
            scoreName = "";
        }
        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra("result", result);
        intent.putExtra("messages", message);
        intent.putExtra("score", scoreName);
        startActivity(intent);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                scoreHome += 1;
                scoreH.setText(String.valueOf(scoreHome));
                returnHome = data.getStringExtra("scoreName");

                String pencetakHome = returnHome;
                String pencetakBaruHome = homeScoreName.getText().toString();
                homeScoreName.setText(String.valueOf(pencetakBaruHome + " \n " + pencetakHome));

            }
        } else if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                scoreAway += 1;
                scoreA.setText(String.valueOf(scoreAway));
                returnAway = data.getStringExtra("scoreName");

                String pencetakAway = returnAway;
                String pencetakBaruAway = awayScoreName.getText().toString();
                awayScoreName.setText(String.valueOf(pencetakBaruAway + " \n " + pencetakAway));
            }
        }
    }
}
