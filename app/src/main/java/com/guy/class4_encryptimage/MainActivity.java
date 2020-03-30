package com.guy.class4_encryptimage;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private View main_LAY_root;
    private ImageView main_IMG_picture1;
    private ImageView main_IMG_picture2;
    private Button main_BTN_upload;
    private Button main_BTN_download;


    /*
    https://he.wikipedia.org/wiki/Base64
    https://stackoverflow.com/questions/4715044/android-how-to-convert-whole-imageview-to-bitmap
    https://stackoverflow.com/a/41028490/7147289
    https://he.wizcase.com/blog/%D7%94%D7%9E%D7%93%D7%A8%D7%99%D7%9A-%D7%94%D7%A9%D7%9C%D7%9D-%D7%9C%D7%AA%D7%A7%D7%A0%D7%99-%D7%94%D7%A6%D7%A4%D7%A0%D7%94-%D7%9E%D7%AA%D7%A7%D7%93%D7%9E%D7%99%D7%9D-aes/
    https://android-developers.googleblog.com/2016/06/security-crypto-provider-deprecated-in.html
    https://stackoverflow.com/questions/9671546/asynctask-android-example
    https://stackoverflow.com/a/48682647/7147289
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        main_LAY_root = findViewById(R.id.main_LAY_root);
        main_IMG_picture1 = findViewById(R.id.main_IMG_picture1);
        main_IMG_picture2 = findViewById(R.id.main_IMG_picture2);
        main_BTN_upload = findViewById(R.id.main_BTN_upload);
        main_BTN_download = findViewById(R.id.main_BTN_download);


        main_BTN_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    upload();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        main_BTN_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    download();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void upload() throws Exception {
        Bitmap bitmap = ImageUtil.getBitmapFromImageView(main_IMG_picture1);

        String base64 = ImageUtil.convertBitmapToBase64(bitmap);

        String encBase64 = CryptoUtil.encrypt(base64);

        MyFireBaseRTDB.postPicToServer(encBase64);
    }


    private void download() throws Exception {
        MyFireBaseRTDB.getPicFromServer(new MyFireBaseRTDB.CallBack_StringReturn() {
            @Override
            public void stringReady(final String encBase64) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String decBase64 = CryptoUtil.decrypt(encBase64);
                            Bitmap bitmapAfter = ImageUtil.convertBase64ToBitmap(decBase64);
                            main_IMG_picture2.setImageBitmap(bitmapAfter);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }
}