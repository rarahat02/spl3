package com.yarolegovich.slidingrootnav.sample.file_picker_pack;


import android.os.Environment;


import com.nononsenseapps.filepicker.AbstractFilePickerFragment;
import com.yarolegovich.slidingrootnav.sample.file_picker_pack.fastscroller.FastScrollerFilePickerActivity;

import java.io.File;

public class FilteredFilePickerActivity extends FastScrollerFilePickerActivity {

    /**
     * Need access to the fragment
     */
    CustomFilteredFilePickerFragment currentFragment;

    /**
     * Return a copy of the new fragment and set the variable above.
     */
    @Override
    protected AbstractFilePickerFragment<File> getFragment(
            final String startPath, final int mode, final boolean allowMultiple,
            final boolean allowDirCreate, final boolean allowExistingFile,
            final boolean singleClick) {

        // startPath is allowed to be null.
        // In that case, default folder should be SD-card and not "/"
        String path = (startPath != null ? startPath
                : Environment.getExternalStorageDirectory().getPath());

        currentFragment = new CustomFilteredFilePickerFragment();
        currentFragment.setArgs(path, mode, allowMultiple, allowDirCreate,
                allowExistingFile, singleClick);

 /*       Intent returnIntent = new Intent();
        returnIntent.putExtra("path",path);
        setResult(Activity.RESULT_OK,returnIntent);*/

        return currentFragment;

    }

    /**
     * Override the back-button.
     */
    /*@Override
    public void onBackPressed() {
        // If at top most level, normal behaviour
        if (currentFragment.) {
            super.onBackPressed();
        } else {
            // Else go up
            currentFragment.goUp();
        }
    }*/
}
