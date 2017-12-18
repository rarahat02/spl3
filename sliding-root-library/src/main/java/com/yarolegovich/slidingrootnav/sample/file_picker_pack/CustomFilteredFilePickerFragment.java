package com.yarolegovich.slidingrootnav.sample.file_picker_pack;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yarolegovich.slidingrootnav.sample.R;

//import com.example.rahat_pdm.newencryptiontest.R;
import com.nononsenseapps.filepicker.AbstractFilePickerFragment;
import com.nononsenseapps.filepicker.LogicHandler;

public class CustomFilteredFilePickerFragment extends FilteredFilePickerFragment {
    /**
     * @param parent Containing view
     * @param viewType which the ViewHolder will contain. Will be one of:
     * [VIEWTYPE_HEADER, VIEWTYPE_CHECKABLE, VIEWTYPE_DIR]. It is OK, and even expected, to use the same
     * layout for VIEWTYPE_HEADER and VIEWTYPE_DIR.
     * @return a view holder for a file or directory (the difference is presence of checkbox).
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        switch (viewType) {
            case LogicHandler.VIEWTYPE_HEADER:
                v = LayoutInflater.from(getActivity()).inflate(R.layout.longer_listitem_dir,
                        parent, false);
                return new AbstractFilePickerFragment.HeaderViewHolder(v);
            case LogicHandler.VIEWTYPE_CHECKABLE:
                v = LayoutInflater.from(getActivity()).inflate(R.layout.longer_listitem_checkable,
                        parent, false);
                return new AbstractFilePickerFragment.CheckableViewHolder(v);
            case LogicHandler.VIEWTYPE_DIR:
            default:
                v = LayoutInflater.from(getActivity()).inflate(R.layout.longer_listitem_dir,
                        parent, false);
                return new AbstractFilePickerFragment.DirViewHolder(v);
        }
    }
}