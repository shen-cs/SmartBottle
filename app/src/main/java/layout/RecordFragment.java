package layout;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.shen.smartbottle.MainActivity;
import com.example.shen.smartbottle.R;
import com.example.shen.smartbottle.Record;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecordFragment extends Fragment {

private View rootView;
    public RecordFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_record, container, false);
        Button showConsumption = (Button) rootView.findViewById(R.id.show_consumption_btn);
        showConsumption.setOnClickListener(btnListener);
        return rootView;
    }

    private View.OnClickListener btnListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            BarChart chart = (BarChart) rootView.findViewById(R.id.chart);
            BarData data = new BarData(getXAxisValues() , getDataSet());
            chart.setData(data);
            chart.setDescription("(units: ml)");
            chart.setDescriptionTextSize(12);
            chart.animateY(2000);
            chart.invalidate();
        }
    };
    private List<IBarDataSet> getDataSet() {
        ArrayList<IBarDataSet> dataSets = null;

        ArrayList<BarEntry> valueSet1 = new ArrayList<>();
        for(int i = 0; i < MainActivity.records.size(); i++) {
            BarEntry entry = new BarEntry(MainActivity.records.get(i).getConsumption(), i);
            valueSet1.add(entry);
        }
//        ArrayList<BarEntry> valueSet2 = new ArrayList<>();
 //       BarEntry v2e1 = new BarEntry(150.000f, 0); // Jan
//        valueSet2.add(v2e1);
//        BarEntry v2e2 = new BarEntry(90.000f, 1); // Feb
//        valueSet2.add(v2e2);
//        BarEntry v2e3 = new BarEntry(120.000f, 2); // Mar
//        valueSet2.add(v2e3);
//        BarEntry v2e4 = new BarEntry(60.000f, 3); // Apr
//        valueSet2.add(v2e4);
//        BarEntry v2e5 = new BarEntry(20.000f, 4); // May
//        valueSet2.add(v2e5);
//        BarEntry v2e6 = new BarEntry(80.000f, 5); // Jun
//        valueSet2.add(v2e6);

        BarDataSet barDataSet1 = new BarDataSet(valueSet1, "Brand 1");
        barDataSet1.setColor(Color.rgb(0, 155, 0));
//        BarDataSet barDataSet2 = new BarDataSet(valueSet2, "Brand 2");
//        barDataSet2.setColor(Color.rgb(20, 50, 150));

        dataSets = new ArrayList<>();
        dataSets.add(barDataSet1);
        //dataSets.add(barDataSet2);
        return dataSets;
    }

    private List<String> getXAxisValues() {
        MainActivity.records = MainActivity.recordSource.getAllRecords();
        ArrayList<String> xAxis = new ArrayList<>();
        for(int i = 0; i < MainActivity.records.size(); i++) {
            xAxis.add(MainActivity.records.get(i).getTime());
        }
        return xAxis;
    }
}
