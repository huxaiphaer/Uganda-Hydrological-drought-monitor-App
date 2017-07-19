package datastructs.example.huzy_kamz.ugandagraphical;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.gson.reflect.TypeToken;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.List;

import model.DataModel;
import model.YearsModel;


public class MainActivity extends AppCompatActivity {

    private Spinner selectPoint, selectMonth, sortby, selectYear;
    private TextView yeartxt, monthtxt;
    private int a, b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        selectPoint = (Spinner) findViewById(R.id.spinner_sortby);
        selectMonth = (Spinner) findViewById(R.id.spinner_select_month);
        sortby = (Spinner) findViewById(R.id.spinner_s);
        selectYear = (Spinner) findViewById(R.id.spinner_year);
        yeartxt = (TextView) findViewById(R.id.year_txt);
        monthtxt = (TextView) findViewById(R.id.month_txt);


        //Elements of Sort By
        final String sort[] = {"Months", "Year"};
        ArrayAdapter<String> srtAdap = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_dropdown_item, sort);
        srtAdap.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sortby.setAdapter(srtAdap);
        sortby.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    selectMonth.setVisibility(View.VISIBLE);
                    selectYear.setVisibility(View.GONE);
                    yeartxt.setVisibility(View.GONE);
                    monthtxt.setVisibility(View.VISIBLE);

                } else {

                    selectMonth.setVisibility(View.GONE);
                    selectYear.setVisibility(View.VISIBLE);
                    monthtxt.setVisibility(View.GONE);
                    yeartxt.setVisibility(View.VISIBLE);


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        final String months[] = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        ArrayAdapter<String> YearAdap = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_dropdown_item, months);
        YearAdap.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectMonth.setAdapter(YearAdap);


        selectMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int a = (selectPoint.getSelectedItemPosition()) + 1;
                int b = (selectMonth.getSelectedItemPosition()) + 1;
                int Logic = sortby.getSelectedItemPosition();
                Toast.makeText(MainActivity.this, "Point : " + a + " Year : " + b, Toast.LENGTH_LONG).show();
                if (Logic == 0) {
                    PopulatingGraphByMonths(a, b, Logic);
                } else {
                    String yr = selectYear.getSelectedItem().toString();
                    int YrInt = Integer.parseInt(yr);
                    PopulatingGraphByMonths(a, YrInt, Logic);
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        final String sortByItems[] = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11",
                "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28",
                "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43",
                "44", "45", "46", "47", "48", "49"};
        ArrayAdapter<String> resdap = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, sortByItems);
        resdap.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Attach the Adapter.
        selectPoint.setAdapter(resdap);

        selectPoint.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int a = (selectPoint.getSelectedItemPosition()) + 1;
                int b = (selectMonth.getSelectedItemPosition()) + 1;
                int Logic = sortby.getSelectedItemPosition();
                Toast.makeText(MainActivity.this, "Point : " + a + " Year : " + b, Toast.LENGTH_LONG).show();
                if (Logic == 0) {
                    PopulatingGraphByMonths(a, b, Logic);
                } else {
                    String yr = selectYear.getSelectedItem().toString();
                    int YrInt = Integer.parseInt(yr);
                    PopulatingGraphByMonths(a, YrInt, Logic);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        PopulateYears();
    }


    //populating spinner
    public void PopulateYears() {
        final ProgressDialog pd = new ProgressDialog(MainActivity.this);
        pd.setMessage("Loading Years ...");
        pd.show();
        String url_ = Config.mobile + "/Mobile/Default.aspx?DataFormat=GetYears";
        Ion.with(MainActivity.this)
                .load(url_)
                .progressDialog(pd)
                .as(new TypeToken<List<YearsModel>>() {
                })
                .setCallback(new FutureCallback<List<YearsModel>>() {

                    @Override
                    public void onCompleted(Exception e, final List<YearsModel> itemList) {
                        try {
                            if (itemList != null) {

                                final List<String> items_ = new ArrayList<String>();

                                for (int a = 0; a < itemList.size(); a++) {
                                    String Items = "" + itemList.get(a).getYearID();
                                    items_.add(Items);
                                    ArrayAdapter<String> yearAdap = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_dropdown_item, items_);
                                    yearAdap.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    selectYear.setAdapter(yearAdap);
                                    selectYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                            int a = (selectPoint.getSelectedItemPosition()) + 1;
                                            int b = (selectMonth.getSelectedItemPosition()) + 1;
                                            int Logic = sortby.getSelectedItemPosition();
                                            if (Logic == 0) {
                                                PopulatingGraphByMonths(a, b, Logic);
                                            } else {
                                                String yr = selectYear.getSelectedItem().toString();
                                                int YrInt = Integer.parseInt(yr);
                                                PopulatingGraphByMonths(a, YrInt, Logic);
                                            }

                                        }

                                        @Override
                                        public void onNothingSelected(AdapterView<?> adapterView) {

                                        }
                                    });
                                }


                                pd.dismiss();

                            } else {
                                Toast.makeText(getApplicationContext(), "No internet connection", Toast.LENGTH_SHORT).show();
                                pd.dismiss();
                            }

                        } catch (Exception ex) {

                            pd.dismiss();
                        }
                    }
                });

    }


    public void PopulatingGraphByMonths(int point, int monthYear, int Logic) {

        final LineChart lineChart = (LineChart) findViewById(R.id.chart);
        final ProgressDialog pd;
        pd = new ProgressDialog(MainActivity.this);
        pd.setMessage("Loading Data ...");
        pd.show();
        String url = "";
        if (Logic == 0) {
            url = Config.mobile + "/Mobile/Default.aspx?DataFormat=ColumnDataAnalysisByMonth&POINT=" + point + "&MONTH=" + monthYear;
        } else {
            url = Config.mobile + "/Mobile/Default.aspx?DataFormat=GetDataByYear&POINT=" + point + "&YEAR=" + monthYear;
        }

        Ion.with(MainActivity.this)
                .load(url)
                .progressDialog(pd)
                .as(new TypeToken<List<DataModel>>() {
                })
                .setCallback(new FutureCallback<List<DataModel>>() {

                    @Override
                    public void onCompleted(Exception e, List<DataModel> itemList) {
                        try {
                            if (itemList != null) {
                                for (int i = 0; i < itemList.size(); i++) {
                                    double A = itemList.get(i).getPoints();

                                    float fp1 = (float) A;
                                    String Months = itemList.get(i).getMONTHS();
                                    System.out.println("Months : " + Months + " Point  : " + fp1);
                                    //Line Data Set
                                    ArrayList<Entry> entries = new ArrayList<>();
                                    ArrayList<String> labels = new ArrayList<String>();
                                    for (int y = 0; y < itemList.size(); y++) {
                                        float fp = (float) itemList.get(y).getPoints();
                                        // turn your data into Entry objects
                                        entries.add(new Entry(fp, y));
                                        // lineChart.invalidate();
                                    }
                                    for (DataModel data : itemList) {
                                        String X = "" + data.getMONTHS();
                                        labels.add(X);
                                    }
                                    LineDataSet dataset = new LineDataSet(entries, "Water Content Points");
                                    LineData data = new LineData(labels, dataset);
                                    dataset.setColors(ColorTemplate.COLORFUL_COLORS); //
                                    dataset.setDrawCubic(true);
                                    dataset.setDrawFilled(true);
                                    lineChart.getAxisRight().setStartAtZero(false);
                                    lineChart.getAxisLeft().setStartAtZero(false);
                                    lineChart.setData(data);
                                    lineChart.animateY(5000);
                                    pd.dismiss();

                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "No internet connection", Toast.LENGTH_SHORT).show();
                                pd.dismiss();
                            }

                        } catch (Exception ex) {

                            pd.dismiss();
                        }
                    }
                });

    }


}
