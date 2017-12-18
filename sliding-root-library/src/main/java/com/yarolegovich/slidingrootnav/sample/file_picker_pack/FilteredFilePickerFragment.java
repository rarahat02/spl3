package com.yarolegovich.slidingrootnav.sample.file_picker_pack;


import android.support.annotation.NonNull;

import com.nononsenseapps.filepicker.FilePickerFragment;

import java.io.File;

public class FilteredFilePickerFragment extends FilePickerFragment
{

    // File extension to filter on
    private static final String EXTENSION = ".mp3";

    /**
     *
     * @param file
     * @return The file extension. If file has no extension, it returns null.
     */
    private String getExtension(@NonNull File file) {
        String path = file.getPath();
        int i = path.lastIndexOf(".");
        if (i < 0) {
            return null;
        }
        else
        {
            return path.substring(i);
        }
    }

    @Override
    protected boolean isItemVisible(final File file)
    {
        boolean ret = super.isItemVisible(file);
        if (ret && !isDir(file) && (mode == MODE_FILE || mode == MODE_FILE_AND_DIR)) {
            String ext = getExtension(file);
            return ext != null && EXTENSION.equalsIgnoreCase(ext);
        }
        return ret;
    }

    @Override
    protected int compareFiles(File lhs, File rhs) {
        if (lhs.isDirectory() && !rhs.isDirectory()) {
            return 1;
        } else if (rhs.isDirectory() && !lhs.isDirectory()) {
            return -1;
        }
        // This was the previous behaviour for all file-file comparisons. Now it's
        // only done if the files have the same extension, or no extension.
        else if (getExtension(lhs) != null && getExtension(lhs).equalsIgnoreCase(getExtension(rhs)) ||
                getExtension(lhs) == null && getExtension(rhs) == null) {
            return lhs.getName().compareToIgnoreCase(rhs.getName());
        }
        // Otherwise, we sort on extension placing files with no extension last.
        else if (getExtension(lhs) != null && getExtension(rhs) != null) {
            // Both have extension, just compare extensions
            return getExtension(lhs).compareToIgnoreCase(getExtension(rhs));
        } else if (getExtension(lhs) != null) {
            // Left has extension, place it first
            return -1;
        } else {
            // Right has extension, place it first
            return 1;
        }
    }
}