package jp.co.jri.internship.fintech_sample1;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;


public class Tab3Fragment extends Fragment {
    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {

        // レイアウト（tab2_fragment.xml）を作成する
        View v = inflater.inflate(R.layout.tab3_fragment, container, false);

        // base
        TextView titleText;
        Button prevButton, nextButton;
        CalendarAdapter mCalendarAdapter = new CalendarAdapter(getActivity());;
        GridView calendarGridView;

        titleText = v.findViewById(R.id.titleText);
        prevButton = v.findViewById(R.id.prevButton);
        prevButton.setOnClickListener(
                new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCalendarAdapter.prevMonth();
                titleText.setText(mCalendarAdapter.getTitle());
            }
        });

        nextButton = v.findViewById(R.id.nextButton);
        nextButton.setOnClickListener(
                new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCalendarAdapter.nextMonth();
                titleText.setText(mCalendarAdapter.getTitle());
            }
        });

        calendarGridView = v.findViewById(R.id.calendarGridView);
//        mCalendarAdapter = new CalendarAdapter(getActivity());
        calendarGridView.setAdapter(mCalendarAdapter);
        titleText.setText(mCalendarAdapter.getTitle());

        return v;
    }
}