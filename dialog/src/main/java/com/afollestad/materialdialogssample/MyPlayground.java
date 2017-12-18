package com.afollestad.materialdialogssample;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.StackingBehavior;
import com.afollestad.materialdialogs.internal.MDTintHelper;
import com.afollestad.materialdialogs.internal.ThemeSingleton;
import com.yarolegovich.slidingrootnav.sample.ConstantClass;
import com.yarolegovich.slidingrootnav.sample.OpenFileExplorer;

/**
 * Created by rarahat on 7/14/17.
 */

public class MyPlayground
{

    static SharedPreferences sharedpreferences;

    private  static Toast toast;
    private static Thread thread;
    private Handler handler;

    private static TextView fileName, truncatedFileName, artistID;


    private static EditText artistPasswordInput;
    private static View positiveActionArtistFinisher;


    private static EditText customerPasswordInput;
    private static EditText customerPasswordInputConfirmer;
    private static EditText customerPhoneNoInput;
    private static View positiveActionCustomerCredential;


    public static final String CustomerPhoneNumber = "phoneNoCustomer";
    public static final String CustomerPassword = "customerPassword";

    private static void showToast(Context context, String message) {
        if (toast != null) {
            toast.cancel();
            toast = null;
        }
        toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        toast.show();
    }

    private static void startThread(Runnable run) {
        if (thread != null) {
            thread.interrupt();
        }
        thread = new Thread(run);
        thread.start();
    }



    public  static void downloadAndPurchasePermissionDialog(Context context, String fileId,
                                                            String songName, String artistName)
    {
        new MaterialDialog.Builder(context)
                .title("To Download and Purchase " + songName + " of " + artistName)
                .content("Write your Password to continue")
                .inputType(
                        InputType.TYPE_CLASS_TEXT
                                | InputType.TYPE_TEXT_VARIATION_PERSON_NAME
                                | InputType.TYPE_TEXT_FLAG_CAP_WORDS)
                .inputRange(4, 4)
                .positiveText(R.string.submit)
                .input(
                        R.string.input_hint,
                        R.string.input_demo_customer_password,
                        false,
                        (dialog, input) ->
                                verifyPasswordToDownloadAndPurchase(context, input.toString()))
                .checkBoxPromptRes(R.string.example_prompt, true, null)
                .show();



    }


    public  static void purchasePermissionDialog(Context context)
    {
        new MaterialDialog.Builder(context)
                .title("Purchase a file from your device")
                .content("Write your Password to continue")
                .inputType(
                        InputType.TYPE_CLASS_TEXT
                                | InputType.TYPE_TEXT_VARIATION_PERSON_NAME
                                | InputType.TYPE_TEXT_FLAG_CAP_WORDS)
                .inputRange(4, 4)
                .positiveText(R.string.submit)
                .input(
                        R.string.input_hint,
                        R.string.input_demo_customer_password,
                        false,
                        (dialog, input) ->
                                verifyPasswordToPurchase(context, input.toString()))
                .checkBoxPromptRes(R.string.example_prompt, true, null)
                .show();



    }


    public  static void syncPermissionDialog(Context context)
    {
        new MaterialDialog.Builder(context)
                .title("Sync Your Device")
                .content("Write your Password to continue")
                .inputType(
                        InputType.TYPE_CLASS_TEXT
                                | InputType.TYPE_TEXT_VARIATION_PERSON_NAME
                                | InputType.TYPE_TEXT_FLAG_CAP_WORDS)
                .inputRange(4, 4)
                .positiveText(R.string.submit)
                .input(
                        R.string.input_hint,
                        R.string.input_demo_customer_password,
                        false,
                        (dialog, input) ->
                                verifyPasswordToSync(context, input.toString()))
                .checkBoxPromptRes(R.string.example_prompt, true, null)
                .show();



    }
    private static void verifyPasswordToSync(Context context, String inputPassword)
    {
        if(inputPassword.equals(retriveCustomerPassword(context)))
            com.yarolegovich.slidingrootnav.sample.OpenFileExplorer.backgroundOpenExplorer(context);
        else
            Toast.makeText(context, "Password NOT correct", Toast.LENGTH_SHORT).show();
    }

    private static void verifyPasswordToPurchase(Context context, String inputPassword)
    {
        if(inputPassword.equals(retriveCustomerPassword(context)))
            com.yarolegovich.slidingrootnav.sample.OpenFileExplorer.openExplorer(context, "empty jinish", ConstantClass.BUY_PURPOSE);
        else
            Toast.makeText(context, "Password NOT correct", Toast.LENGTH_SHORT).show();
    }

    private static void verifyPasswordToDownloadAndPurchase(Context context, String inputPassword)
    {
        if(inputPassword.equals(retriveCustomerPassword(context)))
        {

        }
            //com.yarolegovich.slidingrootnav.sample.OpenFileExplorer.openExplorer(context, "empty jinish", ConstantClass.BUY_PURPOSE);
        else
            Toast.makeText(context, "Password NOT correct", Toast.LENGTH_SHORT).show();
    }
    private static void saveCustomerInfoAll(Context context, String customerFbId,
                                            String customerFbName, String customerPassword,
                                            String phoneNo)
    {

        sharedpreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(CustomerPhoneNumber, phoneNo);
        editor.putString(CustomerPassword, customerPassword);
        editor.commit();


        final TelephonyManager tm =(TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);

        FirebaseInitialOperation.createCustomer(customerFbId, customerFbName, phoneNo, 0, tm.getDeviceId());


    }

    private static String retriveCustomerPassword(Context context)
    {
        sharedpreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        return sharedpreferences.getString(CustomerPassword, null);
    }
    private static String retriveCustomerPhoneNumber(Context context)
    {
        sharedpreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        return sharedpreferences.getString(CustomerPhoneNumber, null);
    }




    public  static void encryptDialog(Context context)
    {
        new MaterialDialog.Builder(context)
                .title("Give ARTIST CODE")
                .content("Write your registration code")
                .inputType(
                        InputType.TYPE_CLASS_TEXT
                                | InputType.TYPE_TEXT_VARIATION_PERSON_NAME
                                | InputType.TYPE_TEXT_FLAG_CAP_WORDS)
                .inputRange(5, 5)
                .positiveText(R.string.submit)
                .input(
                        R.string.input_hint,
                        R.string.input_demo_customer_password,
                        false,
                        (dialog, input) -> startArtistCodeVerifierDialog(context, input.toString()))
                .checkBoxPromptRes(R.string.example_prompt, true, null)
                .show();



    }

    private static void startArtistCodeVerifierDialog(Context context, String message) {

        // logic must be changed
        if(message.length() == 5)
        {
            //showToast(context, "right");
            //asyncProgressDialog(context, message);
            com.yarolegovich.slidingrootnav.sample.OpenFileExplorer.openExplorer(context, message, ConstantClass.ENCRYPTION_PURPOSE);

        }
        else
        {
            encryptDialog(context);
        }

    }
    private static void asyncProgressDialog(Context context, String artistCode)
    {
        new MaterialDialog.Builder(context)
                .title(R.string.progress_dialog)
                .content(R.string.please_wait)
                .progress(true, 0)
                .showListener(
                        dialogInterface ->
                        {
                            final MaterialDialog dialog = (MaterialDialog) dialogInterface;
                            startThread(
                                    () ->
                                    {
                                        while (dialog.getCurrentProgress() != dialog.getMaxProgress()
                                                && !Thread.currentThread().isInterrupted())
                                        {
                                            if (dialog.isCancelled())
                                            {
                                                break;
                                            }

                                            try
                                            {
                                                Thread.sleep(2000);
                                                //thread = null;
                                                dialog.setContent("Done");
                                                FileChooserPermissionDialog(context, artistCode);
                                            }
                                            catch (InterruptedException e)
                                            {
                                                break;
                                            }
                                            dialog.incrementProgress(1);
                                        }
/*                                        ((Activity) context).runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                thread = null;
                                                dialog.setContent("Done");
                                                FileChooserPermissionDialog(context, artistCode);
                                            }
                                        });*/

/*                                        runOnUiThread(
                                                () -> {
                                                    thread = null;
                                                    dialog.setContent("Done");
                                                });*/
                                    });
                        })
                //.progressIndeterminateStyle(false)
                .show();
    }


    public  static void FileChooserPermissionDialog(Context context, String artistCode)
    {
        new MaterialDialog.Builder(context)
                .title("Are you sure you want to encrypt a file for publishing purpose?")
                .content("You must be aware of the app's distribution policy" +
                        ", terms of use, privacy policy and copyright policy")
                .positiveText("Confirm & Continue")
                .negativeText("Go Back")
                .btnStackedGravity(GravityEnum.END)
                .stackingBehavior(
                        StackingBehavior
                                .ALWAYS) // this generally should not be forced, but is used for demo purposes
                .show();
    }




/*    private static void changeRecognizerTrue()
    {
        recognizer = true;
    }
    private static void changeRecognizerFalse()
    {
        recognizer = false;
    }*/




    //@OnClick(R.id.customView)
    public static void showFinisherDialog(Context context, String originalFileCode, String truncatedFileCode, String artistCode, String password)
    {

        MaterialDialog dialog =
                new MaterialDialog.Builder(context)
                        .title("Confirm Your Info Before Choosing File")
                        .customView(R.layout.dialog_customview, true)
                        .positiveText("Confirm")
                        .negativeText("Cancel")
                        .onPositive(
                                (dialog1, which) ->
                                {
                                    if(artistPasswordInput.getText().toString().equals(password))
                                    {
                                        showToast(context, "Password accepted");
                                        //showToast(context, artistCode + truncatedFileCode);

                                        OpenFileExplorer.openExplorer(context, artistCode + truncatedFileCode, ConstantClass.ENCRYPTION_PURPOSE);

                                    }

                                })

                        .build();


        fileName = (TextView) dialog.getCustomView().findViewById(R.id.file_name);
        truncatedFileName = (TextView) dialog.getCustomView().findViewById(R.id.truncated_file_name);
        artistID = (TextView) dialog.getCustomView().findViewById(R.id.artist_id);

        fileName.setText(originalFileCode);
        truncatedFileName.setText(truncatedFileCode);
        artistID.setText(artistCode);


        positiveActionArtistFinisher = dialog.getActionButton(DialogAction.POSITIVE);
        //noinspection ConstantConditions
        artistPasswordInput = (EditText) dialog.getCustomView().findViewById(R.id.password);
        artistPasswordInput.setText(password);

        artistPasswordInput.addTextChangedListener(
                new TextWatcher()
                {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count)
                    {
                        //positiveActionArtistFinisher.setEnabled(s.toString().trim().length() > 3);
                        positiveActionArtistFinisher.setEnabled(s.toString().equals(password));
/*                        if(!s.toString().equals("2222"))
                        {
                            positiveActionArtistFinisher.setEnabled(false);

                        }*/
                    }

                    @Override
                    public void afterTextChanged(Editable s)
                    {


                    }
                });


/*        if(artistPasswordInput.getText().toString().equals("2222"))
        {
            showToast(context, "Password is ok ");
        }*/

        // Toggling the show password CheckBox will mask or unmask the password input EditText
        CheckBox checkbox = (CheckBox) dialog.getCustomView().findViewById(R.id.showPassword);
        checkbox.setOnCheckedChangeListener(
                (buttonView, isChecked) -> {
                    artistPasswordInput.setInputType(
                            !isChecked ? InputType.TYPE_TEXT_VARIATION_PASSWORD : InputType.TYPE_CLASS_TEXT);
                    artistPasswordInput.setTransformationMethod(
                            !isChecked ? PasswordTransformationMethod.getInstance() : null);
                });

        int widgetColor = ThemeSingleton.get().widgetColor;

        MDTintHelper.setTint(
                checkbox, widgetColor == 0 ? ContextCompat.getColor(context, R.color.accent) : widgetColor);

        MDTintHelper.setTint(
                artistPasswordInput,
                widgetColor == 0 ? ContextCompat.getColor(context, R.color.accent) : widgetColor);

        //showToast(context, "recognizer " + recognizer);

        dialog.show();
        positiveActionArtistFinisher.setEnabled(false); // disabled by default


        //return recognizer;
    }




    public static void getCustomerInitialInfoDialog(Context context, String customerFbId, String customerFbName)
    {

        MaterialDialog dialog =
                new MaterialDialog.Builder(context)
                        .title("Start using the app by submitting your info")
                        .customView(R.layout.dialog_customview_customer_info_collect, true)
                        .positiveText("Confirm")
                        .negativeText("Cancel")
                        .onPositive(
                                (dialog1, which) ->
                                {
                                    if(customerPasswordInput.getText().toString().length() > 3 &&
                                            customerPasswordInputConfirmer.getText().toString().equals(customerPasswordInput.getText().toString()) &&
                                            customerPhoneNoInput.getText().toString().length() == 11)
                                    {
                                        showToast(context, "Password accepted");
                                        saveCustomerInfoAll(context,
                                                customerFbId, customerFbName,
                                                customerPasswordInput.getText().toString(),
                                                customerPhoneNoInput.getText().toString());
                                        //showToast(context, artistCode + truncatedFileCode);

                                        //OpenFileExplorer.openExplorer(context, artistCode + truncatedFileCode, ConstantClass.ENCRYPTION_PURPOSE);

                                    }

                                })

                        .onNegative((dialog1, which) ->
                        {
                            getCustomerInitialInfoDialog(context,customerFbId, customerFbName);
                        })
                        .canceledOnTouchOutside(false)
                        .build();





        positiveActionCustomerCredential = dialog.getActionButton(DialogAction.POSITIVE);
        //noinspection ConstantConditions


        customerPhoneNoInput = (EditText) dialog.getCustomView().findViewById(R.id.user_phone_no);
        customerPasswordInput = (EditText) dialog.getCustomView().findViewById(R.id.password1st);

        customerPasswordInputConfirmer = (EditText) dialog.getCustomView().findViewById(R.id.password2nd);


        customerPasswordInput.addTextChangedListener(
                new TextWatcher()
                {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count)
                    {
                        positiveActionCustomerCredential.setEnabled(s.toString().trim().length() > 3);
                        //positiveActionArtistFinisher.setEnabled(s.toString().equals(password));
/*                        if(!s.toString().equals("2222"))
                        {
                            positiveActionArtistFinisher.setEnabled(false);

                        }*/
                    }

                    @Override
                    public void afterTextChanged(Editable s)
                    {


                    }
                });


        customerPasswordInputConfirmer.addTextChangedListener(
                new TextWatcher()
                {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count)
                    {
                        positiveActionCustomerCredential.setEnabled(s.toString().trim().length() > 3);
                        //positiveActionArtistFinisher.setEnabled(s.toString().equals(password));
/*                        if(!s.toString().equals("2222"))
                        {
                            positiveActionArtistFinisher.setEnabled(false);

                        }*/
                    }

                    @Override
                    public void afterTextChanged(Editable s)
                    {


                    }
                });


/*        if(artistPasswordInput.getText().toString().equals("2222"))
        {
            showToast(context, "Password is ok ");
        }*/

        // Toggling the show password CheckBox will mask or unmask the password input EditText
        CheckBox checkbox = (CheckBox) dialog.getCustomView().findViewById(R.id.showPassword);
        checkbox.setOnCheckedChangeListener(
                (buttonView, isChecked) ->
                {
                    customerPasswordInput.setInputType(
                            !isChecked ? InputType.TYPE_TEXT_VARIATION_PASSWORD : InputType.TYPE_CLASS_TEXT);
                    customerPasswordInput.setTransformationMethod(
                            !isChecked ? PasswordTransformationMethod.getInstance() : null);
                    customerPasswordInputConfirmer.setInputType(
                            !isChecked ? InputType.TYPE_TEXT_VARIATION_PASSWORD : InputType.TYPE_CLASS_TEXT);
                    customerPasswordInputConfirmer.setTransformationMethod(
                            !isChecked ? PasswordTransformationMethod.getInstance() : null);
                });

        int widgetColor = ThemeSingleton.get().widgetColor;

        MDTintHelper.setTint(
                checkbox, widgetColor == 0 ? ContextCompat.getColor(context, R.color.accent) : widgetColor);

        MDTintHelper.setTint(
                customerPasswordInput,
                widgetColor == 0 ? ContextCompat.getColor(context, R.color.accent) : widgetColor);
        MDTintHelper.setTint(
                customerPasswordInputConfirmer,
                widgetColor == 0 ? ContextCompat.getColor(context, R.color.accent) : widgetColor);

        //showToast(context, "recognizer " + recognizer);

        dialog.show();
        positiveActionCustomerCredential.setEnabled(false); // disabled by default


        //return recognizer;
    }





}
