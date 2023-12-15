package jp.co.jri.internship.fintech_sample1;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Context;
import android.content.Intent;

import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.*;


public class Tab1Fragment extends Fragment {

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {

        // 表示ボタン
        View v = inflater.inflate(R.layout.tab1_fragment, container, false);
        v.findViewById(R.id.btSearch_frag).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        clickBtSearch(v, inflater, container);
//                        int sumExpense = 1000;
//                        TextView tvExpense = (TextView) v.findViewById(R.id.tvExpense_frag);
//                        tvExpense.setText(String.format("%,d", sumExpense));
                    }
                }
        );

        return v;
    }

        // mainから移行

        @SuppressLint("DefaultLocale")
        public void clickBtSearch(View view, LayoutInflater inflater, ViewGroup container) {

            // 収支の集計領域を準備する
            int sumIncome = 0;  // 収入の集計領域
            int sumExpense = 0; // 支出の集計領域

            // 最大支出の格納領域を準備する
            int maxAmount = 0;      // 最大支出の金額
            String maxTransDate = null;    // 最大支出の取引日付
            String maxSupplier = null;     // 最大支出の取引先
            String maxContent = null;      // 最大支出の内容
            String maxUse = null;          // 最大支出の用途

            // 対象期間の始まりと終わりを入力する
//            View tmp_view = inflater.inflate(R.layout.tab1_fragment, container, false);
            EditText etStart = view.findViewById(R.id.etStart_frag);     // Viewにプログラムでの変数名を割り当てる
            EditText etEnd = view.findViewById(R.id.etEnd_frag);       // Viewにプログラムでの変数名を割り当てる
            String startDate = etStart.getText().toString();   // EditTextに入力した内容を文字列にして変数に渡す
            String endDate = etEnd.getText().toString();     // EditTextに入力した内容を文字列にして変数に渡す

            //キーボードの制御
            etStart.setOnFocusChangeListener((v, hasFocus) -> {
                //EditTextのフォーカスが外れた場合
                if (!hasFocus) {
                    //ソフトキーボードを非表示にする
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
            });
            etStart.clearFocus();
            etEnd.setOnFocusChangeListener((v, hasFocus) -> {
                //EditTextのフォーカスが外れた場合
                if (!hasFocus) {
                    //ソフトキーボードを非表示にする
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
            });
            etEnd.clearFocus();

            // 表示用のList(fintechDataList)を用意する
            List<FintechData> fintechDataList = new ArrayList<>();

            // LocalFintechDateBase.txt(Androidローカルファイル)が存在しない場合、FintechDataBase.csvからデータを読み込み、
            // LocalFintechDateBase.txtに書き出したのち、List(fintechDataList)を作成する
            // LocalFintechDateBase.txtが存在する場合、ローカルファイルからList(fintechDataList)を作成する
            CsvReader parser = new CsvReader();
            String filename = "LocalFintechDateBase.txt";
            File file = getActivity().getFileStreamPath(filename);
            parser.readerFintechDataBase(getActivity().getApplicationContext(), file.exists());

            // text.txt(Androidローカルファイル)から1行取り出し、表示用のList(fintechDataList)に入れる
            // 上記をtext.txtが終わるまで繰り返す
            for (FintechData fdata : parser.fintechObjects) {
                // transDateがstartDateから、endDateまでの日付のデータのみListに入れる
                if (fdata.getTransDate().compareTo(startDate) >= 0) {
                    if (fdata.getTransDate().compareTo(endDate) <= 0) {
                        fintechDataList.add(fdata);
                        // 収支の合計を集計する
                        if (fdata.getAmount() >= 0) {
                            sumIncome = sumIncome + fdata.getAmount();   // 収入の合計を求める
                        } else {
                            sumExpense = sumExpense + fdata.getAmount(); // 支出の合計を求める
                        }
                        // 最大支出を求める
                        if (maxAmount >= fdata.getAmount()) {
                            maxAmount = fdata.getAmount();
                            maxTransDate = fdata.getTransDate();
                            maxSupplier = fdata.getSupplier();
                            maxContent = fdata.getContent();
                            maxUse = fdata.getUse();
                        }
                    }
                }
            }

//            System.out.println(sumIncome);

            // Adapterに表示用のList(fintechDataList)を受け渡す
            List<Map<String, ?>> listData = fintechDataToMapList(fintechDataList);  // Adapterに渡す形式のlist型変数の宣言と初期化
            SimpleAdapter adapter = new SimpleAdapter(                  // ()内で指定した内容のAdapterを生成する
//                MainActivity.this,
                    getActivity(),
                    listData,                                           // ListView用に自作したレイアウトにFintechDataのどの項目を表示するかを指定する
                    R.layout.custom_list_layout,                        // 自作したレイアウト名
                    new String[]{"transDate", "content", "amount"},     // 表示するFintechDataの項目を指定
                    new int[]{R.id.tvList1, R.id.tvList2, R.id.tvList3} // 自作したレイアウトのViewのidを指定
            );

            // Adapterの内容をlistViewに表示する
            ListView lvHistoricalData = (ListView) view.findViewById(R.id.lvHistoricalData_frag); //Viewにプログラムでの変数名を割り当てる
            lvHistoricalData.setAdapter(adapter);   //Adapterの内容をlvHistoricalDataに表示する

            // 収入の合計を出力する
            TextView tvIncome = (TextView) view.findViewById(R.id.tvIncome_frag);
            tvIncome.setText(String.format("%,d", sumIncome));

            // 支出の合計を出力する
            TextView tvExpense = (TextView) view.findViewById(R.id.tvExpense_frag);
            tvExpense.setText(String.format("%,d", sumExpense));

            // 最大支出を表示する（各要素を紐づけ→値を反映）
            TextView tvMaxTransDate = (TextView) view.findViewById(R.id.tvMaxTransDate_frag);
            TextView tvMaxSupplier = (TextView) view.findViewById(R.id.tvMaxSupplier_frag);
            TextView tvMaxContent = (TextView) view.findViewById(R.id.tvMaxContent_frag);
            TextView tvMaxUse = (TextView) view.findViewById(R.id.tvMaxUse_frag);
            TextView tvMaxAmount = (TextView) view.findViewById(R.id.tvMaxAmount_frag);

            tvMaxTransDate.setText(maxTransDate);
            tvMaxSupplier.setText(maxSupplier);
            tvMaxContent.setText(maxContent);
            tvMaxUse.setText(maxUse);
            tvMaxAmount.setText(String.format("%,d", maxAmount));

            System.out.println(maxAmount);
        }

        // Adapterに渡す形式のlist型変数の宣言と初期化
        private List<Map<String, ?>> fintechDataToMapList(List<FintechData> fintechDataList) {
            List<Map<String, ?>> data = new ArrayList<>();
            for (FintechData fintechData : fintechDataList) {
                data.add(fintechDataToMap(fintechData));
            }
            return data;
        }

        // Adapterに渡す形式のlist型変数の宣言と初期化（詳細）
        @SuppressLint("DefaultLocale")
        private Map<String, ?> fintechDataToMap(FintechData fintechData) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", fintechData.getId());
            map.put("transDate", fintechData.getTransDate());
            map.put("transTime", fintechData.getTransTime());
            map.put("service", fintechData.getService());
            map.put("category", fintechData.getCategory());
            map.put("supplier", fintechData.getSupplier());
            map.put("content", fintechData.getContent());
            map.put("use", fintechData.getUse());
            map.put("amount", String.format("%,d", fintechData.getAmount()));
            map.put("balance", String.format("%,d", fintechData.getBalance()));
            return map;
        }

        }
