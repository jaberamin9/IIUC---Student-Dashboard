package com.blogspot.softlabsja.iiucstudentapp.Table;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blogspot.softlabsja.iiucstudentapp.R;
import com.blogspot.softlabsja.iiucstudentapp.Table.model.Cell;
import com.blogspot.softlabsja.iiucstudentapp.Table.model.ColumnHeader;
import com.blogspot.softlabsja.iiucstudentapp.Table.model.RowHeader;
import com.evrencoskun.tableview.adapter.AbstractTableAdapter;
import com.evrencoskun.tableview.adapter.recyclerview.holder.AbstractViewHolder;

public class MyTableViewAdapter extends AbstractTableAdapter<ColumnHeader, RowHeader, Cell> {

    class MyCellViewHolder extends AbstractViewHolder {

        final LinearLayout cell_container;
        final TextView cell_textview;

        public MyCellViewHolder(View itemView) {
            super(itemView);
            cell_container = itemView.findViewById(R.id.cell_container);
            cell_textview = itemView.findViewById(R.id.cell_data);

        }
    }

    @Override
    public int getColumnHeaderItemViewType(int position) {
        return 0;
    }

    @Override
    public int getRowHeaderItemViewType(int position) {
        return 0;
    }

    @Override
    public int getCellItemViewType(int position) {
        return 0;
    }

    @NonNull
    @Override
    public AbstractViewHolder onCreateCellViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Get cell xml layout
        View layout = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.table_view_cell_layout, parent, false);
        // Create a Custom ViewHolder for a Cell item.
        return new MyCellViewHolder(layout);
    }

    @Override
    public void onBindCellViewHolder(@NonNull AbstractViewHolder holder, @Nullable Cell cellItemModel, int columnPosition, int rowPosition) {
        Cell cell = cellItemModel;

        // Get the holder to update cell item text
        MyCellViewHolder viewHolder = (MyCellViewHolder) holder;
        viewHolder.cell_textview.setText(String.valueOf(cell.getData()));

        // If your TableView should have auto resize for cells & columns.
        // Then you should consider the below lines. Otherwise, you can ignore them.

        // It is necessary to remeasure itself.
        viewHolder.cell_container.getLayoutParams().width = LinearLayout.LayoutParams.WRAP_CONTENT;
        viewHolder.cell_textview.requestLayout();
    }


    class MyColumnHeaderViewHolder extends AbstractViewHolder {

        final LinearLayout column_header_container;
        final TextView cell_textview;

        public MyColumnHeaderViewHolder(View itemView) {
            super(itemView);
            column_header_container = itemView.findViewById(R.id.column_header_container);
            cell_textview = itemView.findViewById(R.id.column_header_textView);
        }
    }

    @NonNull
    @Override
    public AbstractViewHolder onCreateColumnHeaderViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Get Column Header xml Layout
        View layout = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.table_view_column_header_layout, parent, false);

        // Create a ColumnHeader ViewHolder
        return new MyColumnHeaderViewHolder(layout);
    }

    @Override
    public void onBindColumnHeaderViewHolder(@NonNull AbstractViewHolder holder, @Nullable ColumnHeader columnHeaderItemModel, int columnPosition) {
        ColumnHeader columnHeader = columnHeaderItemModel;

        // Get the holder to update cell item text
        MyColumnHeaderViewHolder columnHeaderViewHolder = (MyColumnHeaderViewHolder) holder;
        columnHeaderViewHolder.cell_textview.setText(String.valueOf(columnHeader.getData()));

        // If your TableView should have auto resize for cells & columns.
        // Then you should consider the below lines. Otherwise, you can ignore them.

        // It is necessary to remeasure itself.
        columnHeaderViewHolder.column_header_container.getLayoutParams().width = LinearLayout.LayoutParams.WRAP_CONTENT;
        columnHeaderViewHolder.cell_textview.requestLayout();
    }

    class MyRowHeaderViewHolder extends AbstractViewHolder {

        final TextView cell_textview, row_header_textview;

        public MyRowHeaderViewHolder(View itemView) {
            super(itemView);
            cell_textview = itemView.findViewById(R.id.cell_data);
            row_header_textview = itemView.findViewById(R.id.row_header_textview);
        }
    }

    @NonNull
    @Override
    public AbstractViewHolder onCreateRowHeaderViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Get Row Header xml Layout
        View layout = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.table_view_row_header_layout, parent, false);

        // Create a Row Header ViewHolder
        return new MyRowHeaderViewHolder(layout);
    }

    @Override
    public void onBindRowHeaderViewHolder(@NonNull AbstractViewHolder holder, @Nullable RowHeader rowHeaderItemModel, int rowPosition) {
        RowHeader rowHeader = rowHeaderItemModel;

        // Get the holder to update row header item text
        MyRowHeaderViewHolder rowHeaderViewHolder = (MyRowHeaderViewHolder) holder;
        rowHeaderViewHolder.row_header_textview.setText(String.valueOf(rowHeader.getData()));
    }

    @NonNull
    @Override
    public View onCreateCornerView(@NonNull ViewGroup parent) {
        // Get Corner xml layout
        return LayoutInflater.from(parent.getContext())
                .inflate(R.layout.table_view_corner_layout, parent, false);
    }
}
