package jp.co.jri.internship.fintech_sample1;

import android.content.Context;
import android.graphics.Color;
import android.renderscript.Sampler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.AbsListView;
import android.widget.TextView;
import jp.co.jri.internship.fintech_sample1.DateManager;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
public class CalendarAdapter extends BaseAdapter {
    private List<Date> dateArray = new ArrayList();
    private Context mContext;
    private DateManager mDateManager;
    private LayoutInflater mLayoutInflater;

    private int month;
    private int year;

    //カスタムセルを拡張したらここでWigetを定義
    private static class ViewHolder {
        public TextView dateText;
        public TextView record;
    }

    public CalendarAdapter(Context context){
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
        mDateManager = new DateManager();
        dateArray = mDateManager.getDays();
    }

    @Override
    public int getCount() {
        return dateArray.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {

            convertView = mLayoutInflater.inflate(R.layout.calendar_cell, null);
            holder = new ViewHolder();
            holder.dateText = convertView.findViewById(R.id.dateText);
            holder.record = convertView.findViewById(R.id.record);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }

        //セルのサイズを指定
        float dp = mContext.getResources().getDisplayMetrics().density;
        AbsListView.LayoutParams params = new AbsListView.LayoutParams(parent.getWidth()/7 - (int)dp, (parent.getHeight() - (int)dp * mDateManager.getWeeks() ) / mDateManager.getWeeks());
        convertView.setLayoutParams(params);

        //日付のみ表示させる
        SimpleDateFormat dateFormat = new SimpleDateFormat("d", Locale.US);
        holder.dateText.setText(dateFormat.format(dateArray.get(position)));

        String nowDate;
        if (Integer.valueOf(dateFormat.format(dateArray.get(position))) <= 9) nowDate = "2022/07/0" + String.valueOf(dateFormat.format(dateArray.get(position)));
        else nowDate = "2022/07/" + String.valueOf(dateFormat.format(dateArray.get(position)));

        int plus = 0;
        int minus = 0;
        int flag = 0;
        CsvReader parser = new CsvReader();
        String filename = "LocalFintechDateBase.txt";
        File file = mContext.getFileStreamPath(filename);
        parser.readerFintechDataBase(mContext.getApplicationContext(), file.exists());

        // text.txt(Androidローカルファイル)から1行取り出し、表示用のList(fintechDataList)に入れる
        // 上記をtext.txtが終わるまで繰り返す
        for (FintechData fdata : parser.fintechObjects) {
            // transDateがstartDateから、endDateまでの日付のデータのみListに入れる
            if (fdata.getTransDate().compareTo(nowDate) == 0) {
                holder.record.setText("\n " + fdata.getAmount());
                flag = 1;
//                fintechDataList.add(fdata);
//                // 収支の合計を集計する
//                if (fdata.getAmount() >= 0) {
//                    sumIncome = sumIncome + fdata.getAmount();   // 収入の合計を求める
//                } else {
//                    sumExpense = sumExpense + fdata.getAmount(); // 支出の合計を求める
//                }
//                // 最大支出を求める
//                if (maxAmount >= fdata.getAmount()) {
//                    maxAmount = fdata.getAmount();
//                    maxTransDate = fdata.getTransDate();
//                    maxSupplier = fdata.getSupplier();
//                    maxContent = fdata.getContent();
//                    maxUse = fdata.getUse();
//                }
            } else {
                if (flag == 1) break;
                holder.record.setText(" ");
            }
        }

        //当月以外のセルをグレーアウト
        if (mDateManager.isCurrentMonth(dateArray.get(position))){
            convertView.setBackgroundColor(Color.WHITE);
        }else {
            convertView.setBackgroundColor(Color.LTGRAY);
        }

        //日曜日を赤、土曜日を青に
        int colorId;
        switch (mDateManager.getDayOfWeek(dateArray.get(position))){
            case 1:
                colorId = Color.RED;
                break;
            case 7:
                colorId = Color.BLUE;
                break;

            default:
                colorId = Color.BLACK;
                break;
        }
        holder.dateText.setTextColor(colorId);

        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    //表示月を取得
    public String getTitle(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM", Locale.US);
        return format.format(mDateManager.mCalendar.getTime());
    }

    //翌月表示
    public void nextMonth(){
        mDateManager.nextMonth();
        dateArray = mDateManager.getDays();
        this.notifyDataSetChanged();
    }

    //前月表示
    public void prevMonth(){
        mDateManager.prevMonth();
        dateArray = mDateManager.getDays();
        this.notifyDataSetChanged();
    }
}
