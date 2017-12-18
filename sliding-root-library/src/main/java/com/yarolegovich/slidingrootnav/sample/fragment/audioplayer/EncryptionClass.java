package com.yarolegovich.slidingrootnav.sample.fragment.audioplayer;

/**
 * Created by Rahat-PDM on 5/30/2017.
 */


import android.content.Context;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import se.simbio.encryption.Encryption;


public class EncryptionClass
{

    private static final String PRIVATE_KEY = "rahat ashfuihwef erewrwer rtretert tretretre rerewrew trewtewrwerewrwere gtretrt";

    private static final String extraCharsForMacAdress = "00000";
    private static String message = "";

    // file - string - encrypted string - byte[] ||

    public static String encrypt(final Context context, final String path, String artistCode) {

        //return asyncEncryption(context, path);

        try
        {

            String salt = "YourSalt";
            byte[] iv = new byte[16];
            Encryption encryption = Encryption.getDefault(PRIVATE_KEY, salt, iv);

            String fileInBase64StringFormat = getStringFromFile(path);





            String emptyAppId = "12345678901234567890";

            String addtionalString = artistCode + emptyAppId;
            //String addtionalString = "rahat";  // artist app case; file become virgin


            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(fileInBase64StringFormat);
            stringBuilder.append(addtionalString);
            String fileInStringFormat = stringBuilder.toString();



            // onek porer case, khaoaMal state, ei app e thakbe na
            // String addtionalString = "khaoaMal";
            // String customerAppString = "12345678901234567890";
            //
            // StringBuilder stringBuilder = new StringBuilder();
            // stringBuilder.append(fileInStringFormat);
            // stringBuilder.append(addtionalString);
            // stringBuilder.append(customerAppString);
            // fileInStringFormat = stringBuilder.toString();





            String encryptedString = encryption.encryptOrNull(fileInStringFormat);
            byte[] decoded = Base64.decode(encryptedString, 0);



/*            String mainString = "abcr$0016";
            String encryptedString = encryption.encryptOrNull(mainString);
            testVariable = encryptedString;


            message = mainString  +  "       " + encryptedString;*/


            File f = new File(path);
            File outputFile;
            FileOutputStream outputStream;
            try {

                outputFile = new File(Environment.getExternalStorageDirectory(), f.getName().substring(0, (f.getName().length() - 4)) + "__Encrypted.mp3");

                outputStream = new FileOutputStream(outputFile);
                outputStream.write(decoded);
                outputStream.close();

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
        return message;
    }






    public static String decrypt(final Context context, final String path)
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

            try
            {
                outputFile = new File(Environment.getExternalStorageDirectory(), f.getName().substring(0, (f.getName().length() - 4)) + "____De.mp3");

                outputStream = new FileOutputStream(outputFile);
                outputStream.write(decoded);
/*                        if(decryptedString != null)
                    outputStream.write(decryptedString.getBytes());
                else message = "null :/";*/
                outputStream.close();

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



        return message;
    }




    public static String decryptToTemp(final Context context, final String path)
    {

        //return asyncDecry(context, path);


        final TelephonyManager tm =(TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);


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


            if(!appId.equals(extraCharsForMacAdress + tm.getDeviceId()))
            {

                Toast.makeText(context, " File is not registered", Toast.LENGTH_SHORT).show();

                return "File is not registered";
            }

            Toast.makeText(context, "Artist code is " + artistCode + "\n"
                    + "App id is " + appId, Toast.LENGTH_SHORT).show();



            byte[] decoded = Base64.decode(audioString, 0);

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


        return null;
    }

    /*public static FileInputStream decryptToTemp(final Context context, final String path)
    {

        //return asyncDecry(context, path);
        try
        {
            String key = "rahat ashfuihwef erewrwer rtretert tretretre rerewrew trewtewrwerewrwere gtretrt";
            String salt = "YourSalt";
            byte[] iv = new byte[16];
            Encryption encryption = Encryption.getDefault(key, salt, iv);
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

            File tempMp3 = File.createTempFile("demo", "mp3", context.getCacheDir());
            //tempMp3.deleteOnExit();
            //tempMp3.delete();

            FileOutputStream fos = new FileOutputStream(tempMp3);
            fos.write(decoded);
            fos.close();
            //return tempMp3.getPath();
            //return context.getCacheDir().getPath();



            FileInputStream fis = new FileInputStream(tempMp3);
            return  fis;
        }
        catch (Exception e)
        {
            e.printStackTrace();

        }

        return null;
        //return context.getCacheDir().getPath();
    }
*/


























    private static String  asyncDecry(final Context context, final String path)
    {
        try
        {
            String key = "YourKey";
            String salt = "YourSalt";
            byte[] iv = new byte[16];
            Encryption encryption = Encryption.getDefault(key, salt, iv);
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

            String key = "YourKey";
            String salt = "YourSalt";
            byte[] iv = new byte[16];
            Encryption encryption = Encryption.getDefault(key, salt, iv);

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
        byte[] bytes = FileUtils.readFileToByteArray(fl);
        String ret = Base64.encodeToString(bytes, 0);
        //String ret = Base64.encodeToString(bytes, Base64.NO_WRAP);


        return ret;
    }
}
