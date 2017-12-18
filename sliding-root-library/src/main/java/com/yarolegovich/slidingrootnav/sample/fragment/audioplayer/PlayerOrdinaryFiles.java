package com.yarolegovich.slidingrootnav.sample.fragment.audioplayer;

import android.Manifest;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.provider.MediaStore;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.yarolegovich.slidingrootnav.sample.R;
import com.yarolegovich.slidingrootnav.sample.widget.FastScroller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.os.Build.VERSION.SDK_INT;

public class PlayerOrdinaryFiles extends Fragment
{

    private Snackbar snackbar;
    private static ProgressBar progressBar;

    private boolean mProgrammaticScroll = true;
    private LinearLayoutManager mLayoutManager;


    private boolean asynctaskIsOn = false;
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    public static final String Broadcast_PLAY_NEW_AUDIO = "com.valdioveliu.valdio.audioplayer.PlayNewAudio";

    CoordinatorLayout frameLayout;

    boolean isAudioPlaying = false;

    private MediaPlayerService player;
    boolean serviceBound = false;
    ArrayList<Audio> audioList;


    ImageButton playButton;

    TextView errorMsg;

    //private Views mViews;

    int imageIndex = 0;

    private static final String EXTRA_TEXT = "text";




    public static PlayerOrdinaryFiles newInstance()
    {
        PlayerOrdinaryFiles fragment = new PlayerOrdinaryFiles();
        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {

        frameLayout = (CoordinatorLayout) inflater.inflate(R.layout.player_ordinary, container, false);

        progressBar = (ProgressBar) frameLayout.findViewById(R.id.progressBarLoadingFiles);

        errorMsg = (TextView) frameLayout.findViewById(R.id.msg_view);



        if (checkAndRequestPermissions()) {
            loadAudioList();
        }

        return frameLayout;
    }

/*    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player_ordinary);

        playButton = (ImageButton) findViewById(R.id.play_pause);


        if (checkAndRequestPermissions()) {
            loadAudioList();
        }
    }*/





    private void loadAudioList() {
        loadAudio();

    }

    private boolean checkAndRequestPermissions()
    {
        if (SDK_INT >= Build.VERSION_CODES.M)
        {
            int permissionReadPhoneState = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_PHONE_STATE);
            int permissionStorage = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE);
            List<String> listPermissionsNeeded = new ArrayList<>();

            if (permissionReadPhoneState != PackageManager.PERMISSION_GRANTED)
            {
                listPermissionsNeeded.add(Manifest.permission.READ_PHONE_STATE);
            }

            if (permissionStorage != PackageManager.PERMISSION_GRANTED)
            {
                listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
            }

            if (!listPermissionsNeeded.isEmpty())
            {
                ActivityCompat.requestPermissions(getActivity(), listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
                return false;
            }
            else
            {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {

        String TAG = "LOG_PERMISSION";
        Log.d(TAG, "Permission callback called-------");
        switch (requestCode) {
            case REQUEST_ID_MULTIPLE_PERMISSIONS: {

                Map<String, Integer> perms = new HashMap<>();
                // Initialize the map with both permissions
                perms.put(Manifest.permission.READ_PHONE_STATE, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.READ_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                // Fill with actual results from user
                if (grantResults.length > 0) {
                    for (int i = 0; i < permissions.length; i++)
                        perms.put(permissions[i], grantResults[i]);
                    // Check for both permissions

                    if (perms.get(Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED
                            && perms.get(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                            ) {
                        Log.d(TAG, "Phone state and storage permissions granted");
                        // process the normal flow
                        //else any one or both the permissions are not granted
                        loadAudioList();
                    } else {
                        Log.d(TAG, "Some permissions are not granted ask again ");
                        //permission is denied (this is the first time, when "never ask again" is not checked) so ask again explaining the usage of permission
//                      //shouldShowRequestPermissionRationale will return true
                        //show the dialog or snackbar saying its necessary and try again otherwise proceed with setup.
                        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) ||
                                ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.READ_PHONE_STATE)) {
                            showDialogOK("Phone state and storage permissions required for this app",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            switch (which) {
                                                case DialogInterface.BUTTON_POSITIVE:
                                                    checkAndRequestPermissions();
                                                    break;
                                                case DialogInterface.BUTTON_NEGATIVE:
                                                    // proceed with logic by disabling the related features or quit the app.
                                                    break;
                                            }
                                        }
                                    });
                        }
                        //permission is denied (and never ask again is  checked)
                        //shouldShowRequestPermissionRationale will return false
                        else {
                            Toast.makeText(getActivity(), "Go to settings and enable permissions", Toast.LENGTH_LONG)
                                    .show();
                            //proceed with logic by disabling the related features or quit the app.
                        }
                    }
                }
            }
        }

    }

    private void showDialogOK(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(getActivity())
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", okListener)
                .create()
                .show();
    }



    private void initRecyclerView()
    {

        if (audioList != null && audioList.size() > 0)
        {
            final RecyclerView recyclerView = (RecyclerView) frameLayout.findViewById(R.id.recyclerview);

            RecyclerView_Adapter adapter = new RecyclerView_Adapter(audioList, getActivity().getApplication());
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

            FastScroller fastScroller = (FastScroller) frameLayout.findViewById(R.id.fastscroller);
            fastScroller.setRecyclerView(recyclerView);

            recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));


            recyclerView.addOnItemTouchListener(new CustomTouchListener(getActivity(), new onItemClickListener()
            {
                @Override
                public void onClick(View view, int index)
                {
                    playAudio(index);

                    if(snackbar != null)
                    {
                        snackbar.dismiss();
                    }

                    snackbar = Snackbar
                            .make(frameLayout, "Playing " + audioList.get(index).getTitle(), Snackbar.LENGTH_INDEFINITE)
                            .setAction("Pause", new View.OnClickListener() {
                                @Override
                                public void onClick(View view)
                                {
                                    getActivity().unbindService(serviceConnection);
                                    player.stopSelf();
                                }
                            });
                    snackbar.setActionTextColor(Color.RED);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(Color.DKGRAY);
                    TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                    textView.setTextColor(Color.YELLOW);
                    snackbar.show();

                    //Toast.makeText(getActivity(), "Playing " + audioList.get(index).getTitle(), Toast.LENGTH_LONG).show();
                }
            }));

        }
        else
        {
            errorMsg.setText("No files found on device");
            errorMsg.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


/*    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putBoolean("serviceStatus", serviceBound);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        serviceBound = savedInstanceState.getBoolean("serviceStatus");
    }*/


    //Binding this Client to the AudioPlayer Service
    private ServiceConnection serviceConnection = new ServiceConnection()
    {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            MediaPlayerService.LocalBinder binder = (MediaPlayerService.LocalBinder) service;
            player = binder.getService();
            serviceBound = true;
            isAudioPlaying = player.mediaPlayer.isPlaying();


        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            serviceBound = false;
        }
    };


    private void playAudio(int audioIndex)
    {
        //Check is service is active
        if (!serviceBound) {
            //Store Serializable audioList to SharedPreferences
            StorageUtil storage = new StorageUtil(getActivity().getApplicationContext());
            storage.storeAudio(audioList);
            storage.storeAudioIndex(audioIndex);

            Intent playerIntent = new Intent(getContext(), MediaPlayerService.class);
            getActivity().startService(playerIntent);
            getActivity().bindService(playerIntent, serviceConnection, Context.BIND_AUTO_CREATE);

        } else
        {
            //Store the new audioIndex to SharedPreferences
            StorageUtil storage = new StorageUtil(getActivity().getApplicationContext());
            storage.storeAudioIndex(audioIndex);

            //Service is active
            //Send a broadcast to the service -> PLAY_NEW_AUDIO
            Intent broadcastIntent = new Intent(Broadcast_PLAY_NEW_AUDIO);
            getActivity().sendBroadcast(broadcastIntent);
        }
    }



    private void loadAudio()
    {


        //loadAudioForAsync();
        new AudioLoadTask().execute();


    }
    public class AudioLoadTask extends AsyncTask<Void, Integer, Integer> {


        @Override
        protected void onPreExecute()
        {

        }

        @Override
        protected Integer doInBackground(Void... params)
        {
            return loadAudioForAsync();
            //return null;
        }

        @Override
        protected void onProgressUpdate(Integer... progress)
        {
            progressBar.setProgress(progress[0]);
        }

        @Override
        protected void onPostExecute(Integer result)
        {
            super.onPostExecute(result);

            progressBar.setVisibility(View.GONE);

            initRecyclerView();

            //Toast.makeText(getActivity().getApplicationContext(), result.toString() + " files found" , Toast.LENGTH_LONG).show();

        }

    }

    private int loadAudioForAsync()
    {
        int ret = 0;

        audioList = new ArrayList<>();








        //ArrayList<HashMap<String,String>> songList = getAllRegisteredFiles(System.getenv("SECONDARY_STORAGE"));
        //ArrayList<HashMap<String,String>> songList = getAllRegisteredFiles(System.getenv("EXTERNAL_STORAGE"));

        ArrayList<HashMap<String,String>> songList = getAllRegisteredFiles(Environment.getExternalStorageDirectory() + "/AEncrypted Audio Files");

        if(songList != null)
        {
            int registeredFileNumber = 0;

            for(int i=0; i <songList.size(); i++)
            {
                String fileName = songList.get(i).get("file_name");
                String filePath = songList.get(i).get("file_path");
                audioList.add(new Audio(filePath, fileName, "", "", filePath));

                registeredFileNumber++;

                Log.v("file1",  registeredFileNumber + " " + fileName + " " + filePath);

            }
            ret = registeredFileNumber;
        }
        Log.v("orgy", "data \t" +  audioList.get(audioList.size()-1).getData());
        //Log.v("file", "onDataChange: " + registeredFileNumber);
























        ContentResolver contentResolver = getActivity().getContentResolver();

        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String selection = MediaStore.Audio.Media.IS_MUSIC + "!= 0";
        String sortOrder = MediaStore.Audio.Media.TITLE + " ASC";
        Cursor cursor = contentResolver.query(uri, null, selection, null, sortOrder);


        if (cursor != null && cursor.getCount() > 0)
        {
            //audioList = new ArrayList<>();
            while (cursor.moveToNext()) {
                String data = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
                String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
                String album = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
                String artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));

                //String audioUri = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI.getPath()));

                Uri audioUri=
                        ContentUris
                                .withAppendedId(uri,
                                        cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.AudioColumns._ID)));
                String path = getRealPathFromURI(audioUri);

                // Save to audioList
                audioList.add(new Audio(data, title, album, artist, path));
                Log.v("orgi", "data \t" + data + "\t title" +  title + "\t album"  + album +  "\t artist " + artist + "\t path" + path);
            }
        }


        cursor.close();


        return ret;

    }


    private ArrayList<HashMap<String,String>>  getAllRegisteredFiles(String rootPath)
    {
        ArrayList<HashMap<String,String>> fileList = new ArrayList<>();


        try {
            File rootFolder = new File(rootPath);
            File[] files = rootFolder.listFiles();
            for (File file : files)
            {
                if (file.isDirectory())
                {
                    if (getAllRegisteredFiles(file.getAbsolutePath()) != null)
                    {
                        fileList.addAll(getAllRegisteredFiles(file.getAbsolutePath()));
                    } else
                        break;

                }
                else if (file.getName().endsWith(".reg") /*|| file.getName().endsWith(".enc")*/)
                {
                    HashMap<String, String> song = new HashMap<>();
                    song.put("file_path", file.getAbsolutePath());
                    song.put("file_name", file.getName());
                    fileList.add(song);
                }
            }
            return fileList;
        } catch (Exception e)
        {
            Log.v("loop", e.toString());
            return null;
        }
    }


    public String getRealPathFromURI(Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Audio.Media.DATA };
            cursor = getActivity().getContentResolver().query(contentUri,  proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }


/*    @Override
    public void onDestroy() {
        super.onDestroy();
        if (serviceBound) {
            getActivity().unbindService(serviceConnection);
            //service is active
            player.stopSelf();
        }
    }*/



}