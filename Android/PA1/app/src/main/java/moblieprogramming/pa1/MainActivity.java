package moblieprogramming.pa1;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    int[] arr;
    int count = 0, loop;
    boolean start = false;

    ImageButton imageButton;
    TextView textView;
    Button startButton;

    Random rand = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView)findViewById(R.id.subTitle);
        startButton = (Button) findViewById(R.id.startButton);
        startButton.setActivated(true);
    }

    public void onClick(View v) {
        if(start) {
            String buttonName = v.getResources().getResourceName(v.getId());
            int buttonId = Integer.parseInt(buttonName.substring(buttonName.length() - 1));

            ImageButton view = (ImageButton) v;
            view.setAlpha(0.2f);

            if (arr[buttonId-1] == 1) {
                count++;
                view.setImageResource(R.drawable.red);

                if (count == loop) {
                    textView.setText("성공!");
                    setRestart();
                }
            } else {
                view.setImageResource(R.drawable.green);
                textView.setText("실패!");
                setRestart();
            }
        }
    }

    public void setRestart() {
        start = false;
        startButton.setActivated(true);
        startButton.setClickable(true);
        startButton.setText("RESTART");
    }

    public void initButtonClicked(View view) {
        count = 0;
        arr = new int[9];
        loop = rand.nextInt(3)+1;

        for (int i=0; i<loop; i++) {
            int num = rand.nextInt(9);

            if (arr[num] == 1) {
                i--;
                continue;
            }
            arr[num] = 1;
        }

        for (int i=0; i<9; i++) {
            String imageID = "img"+(i+1);
            int resID = getResources().getIdentifier(imageID, "id", getPackageName());
            // findViewById의 인자는 int형이다!
            // string으로 찾기 위해서는 getIdentifier 함수를 이용해서 변환해줘야 한다.

            imageButton = (ImageButton) findViewById(resID);

            if (arr[i] == 1) {
                imageButton.setImageResource(R.drawable.red);
            } else {
                imageButton.setImageResource(R.drawable.green);
            }
            imageButton.setClickable(true);
            imageButton.setAlpha(1.0f);
        }
        startButton.setText("PLAYING GAME");
        startButton.setClickable(false);
        startButton.setActivated(false);
        startButton.setBackground(getResources().getDrawable(R.drawable.border_radius_wait));

        startGame();
    }

    public void startGame() {
        textView.setText("0.5초 후 이미지가 사라집니다!");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        for (int i=0; i<9; i++) {
                            String imageID = "img"+(i+1);
                            int resID = getResources().getIdentifier(imageID, "id", getPackageName());
                            imageButton = (ImageButton) findViewById(resID);
                            imageButton.setImageResource(R.drawable.black);
                            imageButton.setClickable(true);

                            textView.setText("짱구를 클릭해주세요!");
                            start = true;
                        }
                    }
                });
            }
        }, 500);
    }
}
