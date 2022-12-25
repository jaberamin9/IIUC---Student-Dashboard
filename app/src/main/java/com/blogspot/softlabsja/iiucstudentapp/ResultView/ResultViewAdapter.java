package com.blogspot.softlabsja.iiucstudentapp.ResultView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class ResultViewAdapter extends RecyclerView.Adapter<ResultViewAdapter.ViewHolder> {

    ArrayList<ResulteViewModels> mRegistrationModels;
    Context mContext;
    ResultViewTableAdapter adapter;

    public ResultViewAdapter(ArrayList<ResulteViewModels> mRegistrationModels, Context context) {
        this.mRegistrationModels = mRegistrationModels;
        this.mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View contactView = inflater.inflate(R.layout.result_view_item, parent, false);
        return new ResultViewAdapter.ViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        adapter = new ResultViewTableAdapter(mContext);
        holder.tableView.setAdapter(adapter);
        holder.tableView.setTableViewListener(new TableViewListener(holder.tableView));
        int pos, pos2;
        if (position == 0) {
            pos = 2;
            pos2 = 1;
        } else {
            pos = 1;
            pos2 = 0;
        }

        String html = "<html><head><doc</title></head><body><table>" + mRegistrationModels.get(position).getRV_Data() + "</table></body></html>";
        Document document = Jsoup.parse(html);
        Elements length = document.select("table > tbody");

        //----------------------for title (semesterNo)-------------------------------
        Elements nSize = length.get(pos2).select("tr > td");
        String s1 = nSize.get(0).text();
        String s2 = nSize.get(1).text();
        String toBeReplaced = s1.substring(s1.indexOf("S"), s1.indexOf(": ") + 2);
        s1 = s1.replace(toBeReplaced, "");
        String toBeReplaced2 = s2.substring(s2.indexOf("S"), s2.indexOf(": ") + 2);
        s2 = s2.replace(toBeReplaced2, "");
        holder.RV_semesterNo.setText(s1 + ":" + s2);
        //--------------------------------------------------------------------------

        //----------------------Footer 3 number Data-------------------------------
        Elements sizeF = document.select("table > tfoot > tr");
        Elements footerData_3 = sizeF.get(2).select("td");
        holder.RV_TRC.setText(footerData_3.get(0).text());
        holder.RV_TCC.setText(footerData_3.get(1).text());
        holder.RV_BC.setText(footerData_3.get(2).text());
        holder.RV_CGPA.setText(footerData_3.get(3).text());
        //--------------------------------------------------------------------------

        //----------------------for table data-------------------------------
        List<List<Cell>> list = new ArrayList<>();
        Elements size = length.get(pos).select("tr");
        int k = 0;
        for (int i = 0; i < size.size() + 2; i++) {
            List<Cell> cellList = new ArrayList<>();
            if (i <= size.size() - 1) {
                Elements size2 = size.get(i).select("td");
                for (int j = 1; j < size2.size(); j++) {
                    Object data = size2.get(j).text();
                    String id = j + "-" + i;
                    Cell cell;
                    cell = new Cell(id, data);
                    cellList.add(cell);
                }
            } else {
                Elements size2 = sizeF.get(k++).select("td");
                for (int j = 0; j < size2.size() + 1; j++) {
                    Object data;
                    if (j < 1) {
                        data = "";
                    } else {
                        data = size2.get(j - 1).text();
                    }
                    String id = j + "-" + i;
                    Cell cell;
                    cell = new Cell(id, data);
                    cellList.add(cell);
                }
            }
            list.add(cellList);
        }

        Elements size2 = document.select("table > thead > tr > th");
        List<ColumnHeader> columnHeaders = new ArrayList<>();
        for (int i = 1; i < size2.size(); i++) {
            String title = size2.get(i).text();
            ColumnHeader header = new ColumnHeader(String.valueOf(i), title);
            columnHeaders.add(header);
        }

        List<RowHeader> rowHeaderList = new ArrayList<>();
        for (int i = 0; i < size.size() + 2; i++) {
            RowHeader header = new RowHeader(String.valueOf(i), (i + 1) + "");
            rowHeaderList.add(header);
        }
        adapter.setAllItems(columnHeaders, rowHeaderList, list);
        //--------------------------------------------------------------------------

        //holder.tableView.setDrawingCacheEnabled(true);
//        final Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                // Do something after 5s = 5000ms
//                // Assume block needs to be inside a Try/Catch block.
//                try {
//                    holder.tableView.setDrawingCacheEnabled(true);
//                    Bitmap bitmap = holder.tableView.getDrawingCache();
//                    Bitmap newBmp = bitmap.copy(bitmap.getConfig(),true);
//                    holder.tableView.setDrawingCacheEnabled(false);
//
//                    String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath();
//                    OutputStream fOut = null;
//                    Integer counter = 0;
//                    File file = new File(path, "FitnessGirl" + counter + ".jpg"); // the File to save , append increasing numeric counter to prevent files from getting overwritten.
//                    fOut = new FileOutputStream(file);
//
//                    newBmp.compress(Bitmap.CompressFormat.JPEG, 85, fOut); // saving the Bitmap to a file compressed as a JPEG with 85% compression rate
//                    fOut.flush(); // Not really required
//                    fOut.close(); // do not forget to close the stream
//                    MediaStore.Images.Media.insertImage(mContext.getContentResolver(),file.getAbsolutePath(),file.getName(),file.getName());
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, 5000);

    }

    @Override
    public int getItemCount() {
        return mRegistrationModels.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView RV_semesterNo, RV_TRC, RV_TCC, RV_BC, RV_CGPA;
        TableView tableView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            RV_semesterNo = itemView.findViewById(R.id.RV_semesterNo);

            RV_TRC = itemView.findViewById(R.id.RV_TRC);
            RV_TCC = itemView.findViewById(R.id.RV_TCC);
            RV_BC = itemView.findViewById(R.id.RV_BC);
            RV_CGPA = itemView.findViewById(R.id.RV_CGPA);

            tableView = itemView.findViewById(R.id.RV_content_container);
        }
    }

    public static Bitmap getViewBitmap(View view) {
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null) {
            bgDrawable.draw(canvas);
        } else {
            canvas.drawColor(Color.TRANSPARENT);
        }
        view.draw(canvas);
        return returnedBitmap;
    }
}
