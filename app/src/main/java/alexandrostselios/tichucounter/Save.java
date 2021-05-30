package alexandrostselios.tichucounter;

import android.app.Activity;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.os.Bundle;

import java.io.IOException;

public class Save extends Activity {


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_menu_save);
        finish();
    }
}