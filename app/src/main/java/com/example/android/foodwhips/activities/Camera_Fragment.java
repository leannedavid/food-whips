package com.example.android.foodwhips.activities;


import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.android.foodwhips.R;
import com.example.android.foodwhips.database.Contract;
import com.example.android.foodwhips.database.DBHelper;
import com.example.android.foodwhips.database.DatabaseUtils;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Ariel on 8/4/2017.
 */

public class Camera_Fragment extends android.support.v4.app.DialogFragment{

    private String id;
    private String recipe_photo_uri;
    private Button takePhoto;
    private Button choosePhoto;
    private Button cancelPhoto;
    private Uri imageUri;
    private ImageView recipePhoto;
    private SQLiteDatabase db;
    private DBHelper helper;
    private static final int SELECT_PHOTO = 100;
    private static final int CAMERA_REQUEST=101;

    public Camera_Fragment(){
    }

    public static Camera_Fragment newInstance(String idIn, String photoURI) {
        Camera_Fragment f = new Camera_Fragment();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putString("recipe_id", idIn);
        args.putString("photoURI", photoURI);
        Log.v("CAMERA FRAGMENT","PHOTO URI IS " + photoURI);
        f.setArguments(args);

        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.camera_fragment_layout, container, false);
        id = getArguments().getString("recipe_id");
        recipe_photo_uri = getArguments().getString("photoURI");
        helper = new DBHelper(getActivity());
        db = helper.getReadableDatabase();
        takePhoto = (Button) view.findViewById(R.id.take_a_photo);
        choosePhoto = (Button) view.findViewById(R.id.choose_from_gallery);
        cancelPhoto = (Button) view.findViewById(R.id.close_photo_fragment);
        recipePhoto = (ImageView) view.findViewById(R.id.user_recipe_photo);
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA},101);
        }


        if(recipe_photo_uri == null){
           Log.v("CAMERA FRAGMENT","NO URI SET IN DATABASE");
        }else{
            try{
                Uri selectedImage = Uri.parse(recipe_photo_uri);
                InputStream imageStream = null;
                String image_path = getRealPathFromURI(getActivity(), selectedImage);
                try {
                    imageStream = getActivity().getContentResolver().openInputStream(selectedImage);
                } catch (FileNotFoundException e) {
                    Log.v("CAMERA FRAGMENT", "ERROR READING IMAGE STREAM");
                }
                Bitmap yourSelectedImage = BitmapFactory.decodeStream(imageStream);
                try{
                    yourSelectedImage = modifyOrientation(yourSelectedImage, image_path);
                }catch(Exception e){
                    Log.v("CAMERA FRAGMENT","ERROR FLIPPING BITMAP: " + e.getMessage());
                }
                recipePhoto.setImageBitmap(yourSelectedImage);
            }catch(Exception e){
                Log.v("CAMERA FRAGMENT", e.getMessage());
            }
        }

        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor = DatabaseUtils.returnById(db, id);
                if(cursor != null && (cursor.getCount() > 0)){
                    if(cursor.moveToFirst()) {
                        String faveStatus = cursor.getString(cursor.getColumnIndex(Contract.FOODWHIPS_TABLE.COLUMN_NAME_FAVORITE));
                        if (faveStatus.equals("1")) {
                            //Choose photo from camera
                            ContentValues values = new ContentValues();
                            values.put(MediaStore.Images.Media.TITLE, "New Picture");
                            values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
                            imageUri = getActivity().getContentResolver().insert(
                                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                            startActivityForResult(intent, 1);
                            Log.v("CAMERA FRAGMENT","URI FOR CAMERA IS " + imageUri.toString());
                            Log.v("CAMERA FRAGMENT", "URI STORED IN DB IS " + imageUri.toString());
                            DatabaseUtils.updateRceipeBlob(db, id, imageUri.toString());
                            dismiss();
                        }else{
                            Toast.makeText(getActivity(), "Please add to favorites",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                }else{
                    Toast.makeText(getActivity(), "Please add to favorites",
                            Toast.LENGTH_LONG).show();
                }
                cursor.close();
            }
        });

        choosePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor = DatabaseUtils.returnById(db, id);
                if(cursor != null && (cursor.getCount() > 0)){
                    if(cursor.moveToFirst()) {
                        String faveStatus = cursor.getString(cursor.getColumnIndex(Contract.FOODWHIPS_TABLE.COLUMN_NAME_FAVORITE));
                        if (faveStatus.equals("1")) {
                            //Choose a photo from gallery
                            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                            photoPickerIntent.setType("image/*");
                            startActivityForResult(photoPickerIntent, SELECT_PHOTO);
                        }else{
                            Toast.makeText(getActivity(), "Please add to favorites",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                }else{
                    Toast.makeText(getActivity(), "Please add to favorites",
                            Toast.LENGTH_LONG).show();
                }
                cursor.close();
            }
        });

        cancelPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        if(resultCode == Activity.RESULT_OK){
            //pick image from gallery(sd card)
            if(requestCode==SELECT_PHOTO)
            {
                Uri selectedImage = imageReturnedIntent.getData();
                DatabaseUtils.updateRceipeBlob(db, id, selectedImage.toString());
                dismiss();
            }
            //pick image from camera
            else if (requestCode==CAMERA_REQUEST) {
                try {
                    Bitmap thumbnail = MediaStore.Images.Media.getBitmap(
                            getActivity().getContentResolver(), imageUri);
                    recipePhoto.setImageBitmap(thumbnail);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        Log.v("CAMERA FRAGMENT","PERMISSION FOR STORAGE GRANTED");  // Log printed
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(contentUri,  proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public static Bitmap modifyOrientation(Bitmap bitmap, String image_absolute_path) throws IOException {
        ExifInterface ei = new ExifInterface(image_absolute_path);
        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                return rotate(bitmap, 90);

            case ExifInterface.ORIENTATION_ROTATE_180:
                return rotate(bitmap, 180);

            case ExifInterface.ORIENTATION_ROTATE_270:
                return rotate(bitmap, 270);

            case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                return flip(bitmap, true, false);

            case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                return flip(bitmap, false, true);

            default:
                return bitmap;
        }
    }

    public static Bitmap rotate(Bitmap bitmap, float degrees) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degrees);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    public static Bitmap flip(Bitmap bitmap, boolean horizontal, boolean vertical) {
        Matrix matrix = new Matrix();
        matrix.preScale(horizontal ? -1 : 1, vertical ? -1 : 1);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }


}
