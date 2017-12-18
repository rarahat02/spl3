package com.yarolegovich.slidingrootnav.sample;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import com.rarahat02.angads25.filepicker.controller.DialogSelectionListener;
import com.rarahat02.angads25.filepicker.model.DialogConfigs;
import com.rarahat02.angads25.filepicker.model.DialogProperties;
import com.rarahat02.angads25.filepicker.view.FilePickerDialog;
import com.irozon.sneaker.Sneaker;

import java.io.File;

/**
 * Created by rarahat on 7/15/17.
 */

public class OpenFileExplorer
{

    private static FilePickerDialog dialog;
    private static DialogProperties properties;



    //public static void backgroundOpenExplorer(final Context context, final String[] purchased_File_IDs)
    public static void backgroundOpenExplorer(Context context)
    {
        String folderPathWhereIamSearching =  Environment.getExternalStorageDirectory() + "/AEncrypted Audio Files";

        File root = new File(folderPathWhereIamSearching);

        final TelephonyManager tm =(TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);


        new AudioFileSyncUpdateTask(root, context, tm).execute();
        //searchFolderRecursively(root, context, tm);

    }
    public static void searchFolderRecursively(File root, Context context, final TelephonyManager tm, String[] purchasedFileIdArray)
    {

        File[] list = root.listFiles();

        for (File file : list)
        {
            if (file.isFile())
            {
                if(file.getName().endsWith(".reg"))     // amar arekta device
                {
                    Log.v("reg", "File syncing" + file.getName());

                    String errorVerifier = EncryptionClass.syncOneFile(ConstantClass.PREVIOUSLY_PURCHASED_FILE, file.getPath(), tm.getDeviceId(), purchasedFileIdArray);
                    if(errorVerifier != "")
                    {
                        Log.v("reg", "File syncing failed for " + file.getName());

                        /*                        Sneaker.with((Activity) context)
                                .setTitle("Syncing Failed !!!")
                                .setMessage(errorVerifier)
                                .setHeight(200)
                                .setDuration(3000)
                                .sneakError();*/
                    }
                }

                else if(file.getName().endsWith(".enc"))    // device hariye gese
                {
                    Log.v("enc", "File: " + file.getName());
                }

                else
                {
                    Log.v("other", "File: " + file.getAbsoluteFile());
                }


            }
            else
            {
                Log.v("dirRahat", "Dir: " + file.getAbsoluteFile());
                searchFolderRecursively(file, context, tm, purchasedFileIdArray);
            }
        }

    }



public static class AudioFileSyncUpdateTask extends AsyncTask<Void, Integer, Integer>
{

    File root;
    Context context;
    TelephonyManager tm;


    public AudioFileSyncUpdateTask(File root, Context context, final TelephonyManager tm)
    {
        this.root = root;
        this.context = context;
        this.tm = tm;

    }
    @Override
    protected void onPreExecute()
    {

    }

    @Override
    protected Integer doInBackground(Void... params)
    {

        // check server database if
        // customer has new space for another new IMEI
        // if yes, retrieve all purchased_file_ids_of_this_customer

        String[] purchasedFileIdWithArtistIdArray = {"Rahat001amar sonar01", "Rahat001joy bangla01"};

        searchFolderRecursively(root, context, tm, purchasedFileIdWithArtistIdArray);
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... progress)
    {
        //progressBar.setProgress(progress[0]);
    }

    @Override
    protected void onPostExecute(Integer result)
    {
        super.onPostExecute(result);

        //progressBar.setVisibility(View.GONE);
        //Toast.makeText(ccontext.getApplicationContext(), result.toString() + " files found" , Toast.LENGTH_LONG).show();
    }

}



    public static void openExplorer(final Context context, final String artistCodeWithFileCode, int purpose)
    {
        properties = new DialogProperties();

        properties.selection_mode = DialogConfigs.SINGLE_MODE;
        properties.selection_type = DialogConfigs.FILE_SELECT;
        properties.root = new File("/");
        properties.error_dir = new File("/storage");
        properties.offset = new File("/storage");


        if(purpose == ConstantClass.ENCRYPTION_PURPOSE)
        {
            properties.extensions = new String[]{".mp3"};
            dialog = new FilePickerDialog(context,properties);

            viewExplorerForEncryption(context, dialog, artistCodeWithFileCode);
        }
        if(purpose == ConstantClass.VERIFY_PURPOSE)
        {
            //properties.extensions = new String[]{".mp3"};
            properties.extensions = new String[]{".reg", ".enc"};
            dialog = new FilePickerDialog(context,properties);

            viewExplorerForVerify(context, dialog);
        }
        if(purpose == ConstantClass.BUY_PURPOSE)
        {
            properties.extensions = new String[]{".enc"};
            dialog = new FilePickerDialog(context,properties);

            viewExplorerForPurchase(context, dialog);
        }

    }


    public static void viewExplorerForEncryption(final Context context, FilePickerDialog dialog, final String artistCodeWithFileCode)
    {
        dialog.setTitle("Select a File From Your Phone");

        dialog.setDialogSelectionListener(new DialogSelectionListener() {
            @Override
            public void onSelectedFilePaths(String[] files)
            {
                for (String path : files)
                {

                    //EncryptionClass.encrypt(context, path, artistCode);
                    //Toast.makeText(context, EncryptionClass.encrypt(context, path, artistCode), Toast.LENGTH_SHORT).show();

                    String errrorInfo = EncryptionClass.encrypt(context, path, artistCodeWithFileCode);
                    if(!errrorInfo.equals(""))
                    {
                        Sneaker.with((Activity) context)
                                .setTitle("Encryption Failed !!!")
                                .setMessage(errrorInfo)
                                .setHeight(200)
                                .setDuration(3000)
                                .sneakError();
                    }
                    else
                    {

                    }


                }
            }
        });

        dialog.show();
    }

    public static void viewExplorerForVerify(final Context context, FilePickerDialog dialog)
    {

        dialog.setTitle("Select a File");

        dialog.setDialogSelectionListener(new DialogSelectionListener() {
            @Override
            public void onSelectedFilePaths(String[] files)
            {
                for (String path : files)
                {

                    //EncryptionClass.encrypt(context, path, artistCode);

                    String errorVerifier = EncryptionClass.decrypt(context, path);
                    if(errorVerifier != "")
                        Toast.makeText(context, errorVerifier, Toast.LENGTH_LONG).show();


                }
            }
        });

        dialog.show();
    }



    public static void viewExplorerForPurchase(final Context context, FilePickerDialog dialog)
    {

        final TelephonyManager tm =(TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        Toast.makeText(context, tm.getDeviceId(), Toast.LENGTH_LONG).show();

        dialog.setTitle("Select a File");

        dialog.setDialogSelectionListener(new DialogSelectionListener() {
            @Override
            public void onSelectedFilePaths(String[] files)
            {
                for (String path : files)
                {

                    //if(getMacAddr() != null && !getMacAddr().equals("02:00:00:00:00:00"))
                    if(tm.getDeviceId() != null)
                    {

                        //String dummyMACad = "12345678901234567";
                        String errorVerifier = EncryptionClass.purchase(context, path, tm.getDeviceId());
                        if(errorVerifier != "")
                        {
                            Sneaker.with((Activity) context)
                                    .setTitle("Purchase Failed !!!")
                                    .setMessage(errorVerifier)
                                    .setHeight(200)
                                    .setDuration(3000)
                                    .sneakError();
                        }
                        else
                        {
                            Sneaker.with((Activity) context)
                                    .setTitle("Purchase Successful !!!")
                                    .setMessage("Thanks")
                                    .setHeight(200)
                                    .setDuration(3000)
                                    .sneakError();

                        }
                    }
                    else
                    {
                        Sneaker.with((Activity) context)
                                .setTitle("Device ID Gain Failed !!!")
                                .setMessage("Please Try Again")
                                .setHeight(200)
                                .setDuration(3000)
                                .sneakError();
                    }


                }
            }
        });

        dialog.show();
    }
}
