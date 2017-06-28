package dowload.com.numberprogress;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    HProgress HProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        HProgress = (dowload.com.numberprogress.HProgress) findViewById(R.id.hprogress);
    }

    int i = 0;

    public void onPresss(View view) {
        if (i <100) {
            HProgress.setProgress(++i);
        }
    }

    public void onPresss1(View view) {
        if (i >1) {
            HProgress.setProgress(--i);
        }
    }
}
