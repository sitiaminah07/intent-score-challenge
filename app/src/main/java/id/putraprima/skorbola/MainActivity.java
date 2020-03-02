package id.putraprima.skorbola;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private static final int GALLERY_REQUEST_CODE = 1 & 2;
    private static final String TAG = MainActivity.class.getCanonicalName();

    private static final String HOMETEAM_KEY = "home";
    private static final String AWAYTEAM_KEY = "away";
    private static final String HOMEIMAGE_KEY = "imageHome";
    private static final String AWAYIMAGE_KEY = "imageAway";

    private ImageView homeImage;
    private ImageView awayImage;
    private EditText homeTeam;
    private EditText awayTeam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        homeImage = findViewById(R.id.home_logo);
        awayImage = findViewById(R.id.away_logo);
        homeTeam = findViewById(R.id.home_team);
        awayTeam = findViewById(R.id.away_team);
    }

    //1. Validasi Input Home Team
    //2. Validasi Input Away Team
    public void handleNext(View view) {
        String home = homeTeam.getText().toString();
        String away = awayTeam.getText().toString();

        if ((home).equals("") || (away).equals("")) {
            Toast.makeText(this, "Nama Tim tidak boleh kosong", Toast.LENGTH_SHORT).show();
        }
        //5. Next Button Pindah Ke MatchActivity
        else {
            Intent intent = new Intent(this, MatchActivity.class);

            homeImage.buildDrawingCache();
            awayImage.buildDrawingCache();
            Bitmap imageHome=homeImage.getDrawingCache();
            Bitmap imageAway=awayImage.getDrawingCache();
            Bundle extra =new Bundle();
            extra.putParcelable(HOMEIMAGE_KEY,imageHome);
            extra.putParcelable(AWAYIMAGE_KEY,imageAway);
            intent.putExtras(extra);

            intent.putExtra(HOMETEAM_KEY, home);
            intent.putExtra(AWAYTEAM_KEY, away);

            startActivity(intent);
        }
    }

    //3. Ganti Logo Home Team
    //4. Ganti Logo Away Team
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == 0) {
            return;
        }
        if (requestCode == 1) {
            if (data != null) {
                try {
                    Uri imageUri = data.getData();
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                    homeImage.setImageBitmap(bitmap);
                } catch (IOException e) {
                    Toast.makeText(this, "Can't load image", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, e.getMessage());
                }
            }
        }
        if(requestCode==2){
            if (data != null) {
                try {
                    Uri imageUri = data.getData();
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                    awayImage.setImageBitmap(bitmap);
                } catch (IOException e) {
                    Toast.makeText(this, "Can't load image", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, e.getMessage());
                }
            }
        }
    }
    //3. Ganti Logo Home Team
    public void ImageHome(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 1);
    }
    //4. Ganti Logo Away Team
    public void ImageAway(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 2);
    }
}
