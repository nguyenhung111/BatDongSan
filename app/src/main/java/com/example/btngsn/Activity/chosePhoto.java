package com.example.btngsn.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.btngsn.Adapter.PhotoAdapter;
import com.example.btngsn.R;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import gun0912.tedbottompicker.TedBottomPicker;
import gun0912.tedbottompicker.TedBottomSheetDialogFragment;

public class chosePhoto extends AppCompatActivity {
    private Button btnSlect;
    private RecyclerView recyclerView;
    private PhotoAdapter photoAdapter;
    ArrayList<Uri> uri;
    final int REQUEST_CHOOSE_PHOTO = 321;
    String realpath = "";
    String s = "";
    private String imagePath;
    private List<String> imagePathList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chose_photo);

        btnSlect = (Button) findViewById(R.id.btn_choose);
        uri = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.recyvePhoto);
        photoAdapter = new PhotoAdapter(this, uri);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(photoAdapter);

        ChoosePhoto();
    }

    private void requestPerrmison() {
        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {

                Toast.makeText(chosePhoto.this, "Permission Granted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                Toast.makeText(chosePhoto.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
            }
        };
        TedPermission.with(this)
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.READ_CONTACTS, Manifest.permission.ACCESS_FINE_LOCATION)
                .check();
    }

//    private void selectMulti() {
//        TedBottomPicker.with(chosePhoto.this)
//                .setPeekHeight(1600)
//                .showTitle(false)
//                .setCompleteButtonText("Done")
//                .setEmptySelectionText("No Select")
//                .showMultiImage(new TedBottomSheetDialogFragment.OnMultiImageSelectedListener() {
//                    @Override
//                    public void onImagesSelected(List<Uri> uriList) {
//                        // here is selected image uri list
//                        if (uriList != null && !uriList.isEmpty()) {
//                            photoAdapter.setData(uriList);
//                            for (int i = 0; i <= uriList.size(); i++) {
//                                String realpath = getRealPathFromURI(uriList.get(i));
//                                Log.d("realpath", realpath);
//                            }
//                        }
//                    }
//                });
//    }

    private void ChoosePhoto() {

        btnSlect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*"); //allows any image file type. Change * to specific extension to limit it
//**The following line is the important one!
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_CHOOSE_PHOTO); //SELECT_PICTURES is simply a global int used to check the calling intent in onActivityResult
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CHOOSE_PHOTO && resultCode == RESULT_OK  && data != null){

            imagePathList = new ArrayList<>();

            if(data.getClipData() != null){

                int count = data.getClipData().getItemCount();
                for (int i=0; i<count; i++){

                    Uri imageUri = data.getClipData().getItemAt(i).getUri();
                    getImageFilePath(imageUri);
                    photoAdapter.setData(Collections.singletonList(imageUri));
                }
            }
            else if(data.getData() != null){

                Uri imgUri = data.getData();
                getImageFilePath(imgUri);
            }
        }
    }

    public void getImageFilePath(Uri uri) {

        File file = new File(uri.getPath());
        String[] filePath = file.getPath().split(":");
        String image_id = filePath[filePath.length - 1];

        Cursor cursor = getContentResolver().query(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, MediaStore.Images.Media._ID + " = ? ", new String[]{image_id}, null);
        if (cursor!=null) {
            cursor.moveToFirst();
            imagePath = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            imagePathList.add(imagePath);
            Log.d("imagePath", imagePath);
            cursor.close();
        }
    }
}