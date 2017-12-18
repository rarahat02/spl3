package com.yarolegovich.slidingrootnav.sample;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.irozon.sneaker.Sneaker;

import org.apache.commons.io.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import se.simbio.encryption.Encryption;


public class EncryptionClass
{

    static SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String ENCRYPTED_FILE_PATH = "path";

    private static final String PRIVATE_KEY ="rahat ashfuihwef erewrwer rtretert tretretre rerewrew trewtewrwerewrwere gtretrt";

    private static String message = "";

        // file - string - encrypted string - byte[] ||

    public static String encrypt(final Context context, final String path, String artistCodeWithFileCode)
    {
        //return asyncEncryption(context, path);
        String msg = "";
        sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        try
        {

            String salt = "YourSalt";
            byte[] iv = new byte[16];
            Encryption encryption = Encryption.getDefault(PRIVATE_KEY, salt, iv);

            String fileInBase64StringFormat = getStringFromFile(path);


            String emptyAppId = "12345678901234567890";

            String addtionalString = artistCodeWithFileCode + emptyAppId;


            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(fileInBase64StringFormat);
            stringBuilder.append(addtionalString);
            String fileInStringFormat = stringBuilder.toString();




            String encryptedString = encryption.encryptOrNull(fileInStringFormat);
            byte[] decoded = Base64.decode(encryptedString, 0);


            File f = new File(path);
            File outputFile;
            FileOutputStream outputStream;
            try {

               // outputFile = new File(Environment.getExternalStorageDirectory(), f.getName().substring(0, (f.getName().length() - 4)) + "__Encrypted.mp3");


                outputFile = new File(getDestinationPath(context), f.getName().substring(0, (f.getName().length() - 4)) + ".enc");  //"__Encrypted.mp3");
                outputStream = new FileOutputStream(outputFile);
                outputStream.write(decoded);
                outputStream.close();


                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString(ENCRYPTED_FILE_PATH, outputFile.getAbsolutePath());
                editor.commit();



            } catch (IOException e)
            {
                e.printStackTrace();
                msg = e.toString();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            msg = e.toString();
        }
        return msg;
    }


    public static String getDestinationPath(final Context context)
    {

        File dir = new File(Environment.getExternalStorageDirectory() + "/AEncrypted Audio Files");
        if(dir.exists() && dir.isDirectory())
        {
            //Toast.makeText(context, " File saved to existing Directory", Toast.LENGTH_SHORT).show();

            Sneaker.with((Activity) context)
                    .setTitle("Encryption Successful !!!")
                    .setMessage("File saved to existing \"Internal Storage/AEncrypted Audio Files\" Directory")
                    .setHeight(200)
                    .setDuration(5000)
                    .sneakSuccess();

            // do something here
            return dir.getPath();
        }


        dir.mkdirs();
        //Toast.makeText(context, "New Directory created & File Saved", Toast.LENGTH_SHORT).show();
        Sneaker.with((Activity) context)
                .setTitle("Encryption Successful !!!")
                .setMessage("New Directory \"Internal Storage/AEncrypted Audio Files\" created & File Saved")
                .setHeight(200)
                .setDuration(5000)
                .sneakSuccess();


        return dir.getPath();

    }




    public static String decrypt(final Context context, final String path)      // verrify song's data
    {

        //return asyncDecry(context, path);
        try
        {
            String salt = "YourSalt";
            byte[] iv = new byte[16];
            Encryption encryption = Encryption.getDefault(PRIVATE_KEY, salt, iv);
            String fileInStringFormat = getStringFromFile(path);

            String decryptedString = encryption.decryptOrNull(fileInStringFormat);


            String audioString = decryptedString.substring(0, decryptedString.length()-40);

            decryptedString = decryptedString.substring(decryptedString.length() - 40);     // 40

            String artistCode = decryptedString.substring(0, decryptedString.length()-32);  // 8
            String fileId = decryptedString.substring(8, decryptedString.length()-20);      // 12
            String appId = decryptedString.substring(20, decryptedString.length());      // 20


            Sneaker.with((Activity) context)
                    .setTitle("Information !!!")
                    .setMessage("Artist code is " + artistCode + "\n" +
                            "File id is " + fileId + "\n" +
                            "App Id is " + appId)
                    .setHeight(200)
                    .setDuration(10000)
                    .sneakWarning();


                            /*decrypting the file to device named file_De.mp3*/



           /* byte[] decoded = Base64.decode(audioString, 0);


            File f = new File(path);
            File outputFile;
            FileOutputStream outputStream;

            try
            {
                outputFile = new File(Environment.getExternalStorageDirectory(), f.getName().substring(0, (f.getName().length() - 4)) + "____De.mp3");

                outputStream = new FileOutputStream(outputFile);
                outputStream.write(decoded);

                outputStream.close();

                message = "";


            } catch (IOException e)
            {
                e.printStackTrace();
                message = e.toString();
            }*/
        }
        catch (Exception e)
        {
            e.printStackTrace();
            message = e.toString();
        }



        return message;
    }




    public static String decryptToTemp(final Context context, final String path)
    {

        //return asyncDecry(context, path);
        try
        {

            String salt = "YourSalt";
            byte[] iv = new byte[16];
            Encryption encryption = Encryption.getDefault(PRIVATE_KEY, salt, iv);
            String fileInStringFormat = getStringFromFile(path);

            String decryptedString = encryption.decryptOrNull(fileInStringFormat);

            if(decryptedString.contains("rahat"))  // file is virgin
            {
                //decryptedString = decryptedString.substring(decryptedString.length() - 5);
                decryptedString = decryptedString.substring(0, decryptedString.length()-5);
                Toast.makeText(context, "virgin rahat", Toast.LENGTH_SHORT).show();
            }
            else if(decryptedString.contains("khaoaMal"))
            {
                // decryptedString = decryptedString.substring(decryptedString.length() - 28);
                //
                // String addtionalString = "khaoaMal";
                // String customerAppString = "12345678901234567890";
                //
                // StringBuilder stringBuilder = new StringBuilder();
                // stringBuilder.append(fileInStringFormat);
                // stringBuilder.append(addtionalString);
                // stringBuilder.append(customerAppString);
                // fileInStringFormat = stringBuilder.toString();
                // reEncrypt();


            }
            byte[] decoded = Base64.decode(decryptedString, 0);

            File f = new File(path);
            File outputFile;
            FileOutputStream outputStream;

            File tempMp3 = File.createTempFile(f.getName(), "mp3",context.getCacheDir());
            //tempMp3.deleteOnExit();
            tempMp3.delete();

            FileOutputStream fos = new FileOutputStream(tempMp3);
            fos.write(decoded);
            fos.close();
            return tempMp3.getPath();
            //return context.getCacheDir().getPath();
        }
        catch (Exception e)
        {
            e.printStackTrace();

        }



        return context.getCacheDir().getPath();
    }



























    private static String  asyncDecry(final Context context, final String path)
    {
        try
        {
            String salt = "YourSalt";
            byte[] iv = new byte[16];
            Encryption encryption = Encryption.getDefault(PRIVATE_KEY, salt, iv);
            String fileInStringFormat = getStringFromFile(path);

            //encrypted = encryption.encryptOrNull(fileText);
            encryption.decryptAsync(fileInStringFormat, new Encryption.Callback() {
                @Override
                public void onSuccess(String decryptedString) {


                    message = "decryption successful";


                    File f = new File(path);


                    File outputFile;
                    FileOutputStream outputStream;
                    try {
                        outputFile = new File(f.getParent(), f.getName().substring(0, (f.getName().length() - 4)) + "____De.mp3");

                        outputStream = new FileOutputStream(outputFile);
                        outputStream.write(decryptedString.getBytes());
                        outputStream.close();

                        //String decrypted = encryption.decryptOrNull(encrypted);


                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(Exception e) {
                    message = "Oh no! an error has occurred: " + e;
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

        return message;
    }

    private static String  asyncEncryption(final Context context, final String path)
    {
        try
        {

            String salt = "YourSalt";
            byte[] iv = new byte[16];
            Encryption encryption = Encryption.getDefault(PRIVATE_KEY, salt, iv);

            String fileInStringFormat = getStringFromFile(path);


            encryption.encryptAsync(fileInStringFormat, new Encryption.Callback() {
                @Override
                public void onSuccess(String encryptedString) {

                    message = "encryption successful";

                    File f = new File(path);


                    File outputFile;
                    FileOutputStream outputStream;
                    try {
                        outputFile = new File(f.getParent(), f.getName().substring(0, (f.getName().length() - 4)) + "__Encrypted.mp3");

                        outputStream = new FileOutputStream(outputFile);
                        outputStream.write(encryptedString.getBytes());
                        outputStream.close();

                        //String decrypted = encryption.decryptOrNull(encrypted);


                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(Exception e) {
                    // if an error occurs you will get the exception here
                    //log("Oh no! an error has occurred: " + e);
                    message = "Oh no! an error has occurred: " + e;


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

        return message;
    }





    private static String getStringFromFile (String filePath) throws Exception
    {
       File fl = new File(filePath);
        byte[] bytes = org.apache.commons.io.FileUtils.readFileToByteArray(fl);
        String ret = Base64.encodeToString(bytes, 0);
        //String ret = Base64.encodeToString(bytes, Base64.NO_WRAP);


        return ret;
    }








    public static String purchase(final Context context, final String path, String deviceID)
    {

        try
        {
            String salt = "YourSalt";
            byte[] iv = new byte[16];
            Encryption encryption = Encryption.getDefault(PRIVATE_KEY, salt, iv);
            String fileInStringFormat = getStringFromFile(path);

            String decryptedString = encryption.decryptOrNull(fileInStringFormat);


/*            String audioString = decryptedString.substring(0, decryptedString.length()-25);

            decryptedString = decryptedString.substring(decryptedString.length() - 25);

            String artistCode = decryptedString.substring(0, decryptedString.length()-20);*/



            String audioString = decryptedString.substring(0, decryptedString.length()-40);

            decryptedString = decryptedString.substring(decryptedString.length() - 40);     // 40

            String artistCode = decryptedString.substring(0, decryptedString.length()-32);  // 8
            String fileId = decryptedString.substring(8, decryptedString.length()-20);      // 12
            //String appId = decryptedString.substring(20, decryptedString.length());      // 20







            //String appId = decryptedString.substring(5, decryptedString.length());

            //String extraCharsForMacAdress = "000";
            String extraCharsForMacAdress = "00000";

            String toBeEncryptedString = audioString + artistCode + fileId + extraCharsForMacAdress + deviceID;







            Encryption reEncryption = Encryption.getDefault(PRIVATE_KEY, salt, iv);

            String encryptedString = reEncryption.encryptOrNull(toBeEncryptedString);

            byte[] decoded = Base64.decode(encryptedString, 0);




            File f = new File(path);
            File outputFile;
            FileOutputStream outputStream;

            try
            {
                outputFile = new File(Environment.getExternalStorageDirectory()+ "/AEncrypted Audio Files", f.getName().substring(0, (f.getName().length() - 4)) + ".reg");//"____Registered.mp3");

                outputStream = new FileOutputStream(outputFile);
                outputStream.write(decoded);
/*                        if(decryptedString != null)
                    outputStream.write(decryptedString.getBytes());
                else message = "null :/";*/
                outputStream.close();

                message = "";

                //message = "decryption successful";
                //String decrypted = encryption.decryptOrNull(encrypted);


            } catch (IOException e)
            {
                e.printStackTrace();
                message = e.toString();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            message = e.toString();
        }

        return  message;

    }










    public static String syncOneFile(int purpose, final String path, String deviceID, String[] purchasedFileIdWithArtistIdArray)
    {

        try
        {
            String salt = "YourSalt";
            byte[] iv = new byte[16];

            Encryption encryption = Encryption.getDefault(PRIVATE_KEY, salt, iv);
            String fileInStringFormat = getStringFromFile(path);

            String decryptedString = encryption.decryptOrNull(fileInStringFormat);

            String audioString = decryptedString.substring(0, decryptedString.length()-40);

            decryptedString = decryptedString.substring(decryptedString.length() - 40);     // 40

            String artistCode = decryptedString.substring(0, decryptedString.length()-32);  // 8
            String thisFileId = decryptedString.substring(8, decryptedString.length()-20);      // 12
            //String appId = decryptedString.substring(20, decryptedString.length());      // 20

            String extraCharsForMacAdress = "00000";



            String toBeEncryptedString;

            if(purpose == ConstantClass.PREVIOUSLY_PURCHASED_FILE)
            {

                for (String purchasedfileIdWithArtistId : purchasedFileIdWithArtistIdArray)
                {
                    if(purchasedfileIdWithArtistId.equals(artistCode + thisFileId))
                    {

                        toBeEncryptedString = audioString + artistCode + thisFileId + extraCharsForMacAdress + deviceID;

                        Encryption reEncryption = Encryption.getDefault(PRIVATE_KEY, salt, iv);

                        String encryptedString = reEncryption.encryptOrNull(toBeEncryptedString);

                        byte[] decoded = Base64.decode(encryptedString, 0);


                        Log.v("reg", "File syncing under encryption class: File id " + thisFileId);

                        File f = new File(path);
                        File outputFile;
                        FileOutputStream outputStream;

                        try
                        {
                            outputFile = new File(Environment.getExternalStorageDirectory()+ "/AEncrypted Audio Files", f.getName().substring(0, (f.getName().length() - 4)) + ".reg");//"____Registered.mp3");

                            outputStream = new FileOutputStream(outputFile);
                            outputStream.write(decoded);

                            outputStream.close();

                            message = "";


                        } catch (IOException e)
                        {
                            e.printStackTrace();
                            message = e.toString();
                        }

                        break;

                    }
                    else
                    {

                    }
                }
            }
/*            if(purpose == ConstantClass.NEWLY_DOWNLOADED_FILE)
            {
                for (String purchasedfileId : purchasedFileIdArray)
                {
                    if(purchasedfileId.equals(thisFileId))
                    {
                        toBeEncryptedString = audioString + artistCode + purchasedfileId + extraCharsForMacAdress + deviceID;

                        Encryption reEncryption = Encryption.getDefault(PRIVATE_KEY, salt, iv);

                        String encryptedString = reEncryption.encryptOrNull(toBeEncryptedString);

                        byte[] decoded = Base64.decode(encryptedString, 0);




                        File f = new File(path);
                        File outputFile;
                        FileOutputStream outputStream;

                        try
                        {
                            outputFile = new File(Environment.getExternalStorageDirectory()+ "/AEncrypted Audio Files", f.getName().substring(0, (f.getName().length() - 4)) + ".reg");//"____Registered.mp3");

                            outputStream = new FileOutputStream(outputFile);
                            outputStream.write(decoded);

                            outputStream.close();

                            message = "";


                        } catch (IOException e)
                        {
                            e.printStackTrace();
                            message = e.toString();
                        }

                        break;

                    }
                    else
                    {

                    }
                }
            }*/
        }
        catch (Exception e)
        {
            e.printStackTrace();
            message = e.toString();
        }

        return  message;

    }











}
