package edu.columbia.jonathan.project_bestnote;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Jonathan on 4/19/15.
 */
public class MainPage extends Fragment {

    public Uri fileUri;
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    public static final int MEDIA_TYPE_IMAGE = 1;
    public File dir;
    public File[] fileList;
    View mainView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mainView = inflater.inflate(R.layout.main_page, container, false);
        TextView textNotes = (TextView) mainView.findViewById(R.id.textNotes);
        TextView picNotes = (TextView) mainView.findViewById(R.id.picNotes);
        ImageView textLogo = (ImageView) mainView.findViewById(R.id.textLogo);
        ImageView picLogo = (ImageView) mainView.findViewById(R.id.picLogo);

        // Font path
        String fontPath = "fonts/indieFlower.ttf";
        // Loading Font Face
        Typeface indieFlower = Typeface.createFromAsset(getActivity().getAssets(), fontPath);
        // Applying font
        textNotes.setTypeface(indieFlower);
        picNotes.setTypeface(indieFlower);

        textLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent showText = new Intent(MainPage.this.getActivity(), TextNotes.class);
                mainView.getContext().startActivity(showText);
                Toast t = Toast.makeText(MainPage.this.getActivity(),
                        getString(R.string.viewText), Toast.LENGTH_SHORT);
                t.show();
//                getActivity().finish();
            }
        });
        picLogo.setOnClickListener(new View.OnClickListener() {
            @Override
        public void onClick(View v) {
                dir = new File(Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_PICTURES), "BestNote");
                fileList = dir.listFiles();
                if (fileList != null) {
                    Intent showPic = new Intent(MainPage.this.getActivity(), PictureNote.class);
                    startActivity(showPic);
                    Toast t = Toast.makeText(MainPage.this.getActivity(),
                            getString(R.string.viewPicture), Toast.LENGTH_SHORT);
                    t.show();
                } else {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                    startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
                }
            }
        });

        return mainView;
    }

    /** Create a file Uri for saving an image or video */
    private static Uri getOutputMediaFileUri(int type){
        return Uri.fromFile(getOutputMediaFile(type));
    }

    /** Create a File for saving an image or video */
    private static File getOutputMediaFile(int type){
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "BestNote");
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                Log.d("BestNote", "failed to create directory");
                return null;
            }
        }
        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE){
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "IMG_"+ timeStamp + ".jpg");
        } else {
            return null;
        }
        return mediaFile;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                // Image captured and saved to fileUri specified in the Intent
                Toast.makeText(MainPage.this.getActivity(), getString(R.string.cameraFirst), Toast.LENGTH_LONG).show();
            } else if (resultCode == Activity.RESULT_CANCELED) {
                // User cancelled the image capture
                Toast.makeText(MainPage.this.getActivity(), getString(R.string.cancelled), Toast.LENGTH_SHORT).show();
            } else {
                // Image capture failed, advise user
                Toast.makeText(MainPage.this.getActivity(), getString(R.string.failed), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
