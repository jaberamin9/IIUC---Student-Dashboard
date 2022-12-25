package com.blogspot.softlabsja.iiucstudentapp.CourseStatus;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.blogspot.softlabsja.iiucstudentapp.R;
import com.blogspot.softlabsja.iiucstudentapp.Table.TableViewListener;
import com.blogspot.softlabsja.iiucstudentapp.Table.model.Cell;
import com.blogspot.softlabsja.iiucstudentapp.Table.model.ColumnHeader;
import com.blogspot.softlabsja.iiucstudentapp.Table.model.RowHeader;
import com.evrencoskun.tableview.TableView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class CourseStatusAdapter extends RecyclerView.Adapter<CourseStatusAdapter.ViewHolder> {

    ArrayList<CourseStatusModel> courseStatusModels;
    Context mContext;
    CourseStatusTableAdapter adapter;

    public CourseStatusAdapter(ArrayList<CourseStatusModel> courseStatusModels, Context context) {
        this.courseStatusModels = courseStatusModels;
        this.mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View contactView = inflater.inflate(R.layout.course_status_item, parent, false);
        return new CourseStatusAdapter.ViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        adapter = new CourseStatusTableAdapter(mContext);
        holder.tableView.setAdapter(adapter);
        holder.tableView.setTableViewListener(new TableViewListener(holder.tableView));


        String html = "<html><head><doc</title></head><body><table>" + courseStatusModels.get(position).getData() + "</table></body></html>";
        Document document = Jsoup.parse(html);
        Elements length = document.select("table > tbody > tr");
        if (length.size() > 1) {

            if (position == 5) {
                holder.progress_cradite.setText("");
                holder.progress_c.setText("");

                String html2 = "<html><head><doc</title></head><body><table>" + courseStatusModels.get(position).getData() + "</table></body></html>";
                Document document2 = Jsoup.parse(html2);
                Elements length2 = document2.select("table > tbody > tr");

                List<List<Cell>> list = new ArrayList<>();
                for (int i = 0; i < length2.size(); i++) {
                    List<Cell> cellList = new ArrayList<>();
                    Elements size2 = length2.get(i).select("td");
                    for (int j = 1; j < size2.size(); j++) {
                        Object data = size2.get(j).text();
                        String id = j + "-" + i;
                        Cell cell;
                        cell = new Cell(id, data);
                        cellList.add(cell);
                    }
                    list.add(cellList);
                }


                Elements rowSize = courseStatusModels.get(position).getHeadData();
                List<ColumnHeader> columnHeaders = new ArrayList<>();
                for (int i = 1; i < rowSize.size(); i++) {
                    String title = rowSize.get(i).text();
                    ColumnHeader header = new ColumnHeader(String.valueOf(i), title);
                    columnHeaders.add(header);
                }

                List<RowHeader> rowHeaderList = new ArrayList<>();
                for (int i = 0; i < length2.size() - 1; i++) {
                    RowHeader header = new RowHeader(String.valueOf(i), (i + 1) + "");
                    rowHeaderList.add(header);
                }
                adapter.setAllItems(columnHeaders, rowHeaderList, list);
            } else {
                String html2 = "<html><head><doc</title></head><body><table>" + courseStatusModels.get(position).getData() + "</table></body></html>";
                Document document2 = Jsoup.parse(html2);
                Elements length2 = document2.select("table > tbody > tr");

                List<List<Cell>> list = new ArrayList<>();
                for (int i = 0; i < length2.size() - 1; i++) {
                    List<Cell> cellList = new ArrayList<>();
                    Elements size2 = length2.get(i).select("td");
                    for (int j = 1; j < size2.size(); j++) {
                        Object data = size2.get(j).text();
                        String id = j + "-" + i;
                        Cell cell;
                        cell = new Cell(id, data);
                        cellList.add(cell);
                    }
                    list.add(cellList);
                }

                if (position != 5) {
                    holder.progress_c.setText(length2.get(length2.size() - 1).select("td").get(1).text());
                }

                Elements rowSize = courseStatusModels.get(position).getHeadData();
                List<ColumnHeader> columnHeaders = new ArrayList<>();
                for (int i = 1; i < rowSize.size(); i++) {
                    String title = rowSize.get(i).text();
                    ColumnHeader header = new ColumnHeader(String.valueOf(i), title);
                    columnHeaders.add(header);
                }

                List<RowHeader> rowHeaderList = new ArrayList<>();
                for (int i = 0; i < length2.size() - 1; i++) {
                    RowHeader header = new RowHeader(String.valueOf(i), (i + 1) + "");
                    rowHeaderList.add(header);
                }
                adapter.setAllItems(columnHeaders, rowHeaderList, list);
            }
        } else {
            holder.tableView.setVisibility(View.GONE);
            holder.progress_text_ll.setVisibility(View.GONE);
            holder.progress_ll.setVisibility(View.GONE);
        }

        if (position == 0) {
            holder.progress_text.setText("On Going Courses");
        } else if (position == 1) {
            holder.progress_text.setText("Completed Courses");
        } else if (position == 2) {
            holder.progress_text.setText("Result Up-Coming");
        } else if (position == 3) {
            holder.progress_text.setText("Waiting Course");
        } else if (position == 4) {
            holder.progress_text.setText("Previous Failed Courses");
        } else if (position == 5) {
            holder.progress_text.setText("Pending/Missing Courses");
        }
    }

    @Override
    public int getItemCount() {
        return courseStatusModels.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView progress_text, progress_cradite, progress_c;
        TableView tableView;
        LinearLayout progress_ll;
        RelativeLayout progress_text_ll;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            progress_text = itemView.findViewById(R.id.progress_text);
            progress_cradite = itemView.findViewById(R.id.progress_cradite);
            progress_c = itemView.findViewById(R.id.progress_c);

            tableView = itemView.findViewById(R.id.CS_content_container);

            progress_ll = itemView.findViewById(R.id.progress_ll);
            progress_text_ll = itemView.findViewById(R.id.progress_text_ll);
        }
    }
}
