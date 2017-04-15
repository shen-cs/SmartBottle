package layout;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.shen.smartbottle.MainActivity;
import com.example.shen.smartbottle.R;
import com.example.shen.smartbottle.Record;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {
    private ArrayAdapter<Record> adapter;
    private View rootview;
    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        records = new ArrayList<>();
//        records.add(new Record(1, 2016, 9, 2, "16:55", 200));
//        records.add(new Record(2, 2016, 9, 2, "16:57", 500));
//        records.add(new Record(3, 2016, 9, 2, "16:59", 204));
//        records.add(new Record(4, 2016, 9, 2, "17:05", 200));
//        records.add(new Record(5, 2016, 9, 2, "17:55", 300));
        adapter = new ArrayAdapter<Record>(getContext(), android.R.layout.simple_list_item_2, android.R.id.text1, MainActivity.records) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                 View view = super.getView(position, convertView, parent);
                TextView date = (TextView) view.findViewById(android.R.id.text1);
                TextView consumption = (TextView) view.findViewById(android.R.id.text2);
                date.setText(MainActivity.records.get(position).getDate());
                consumption.setText(String.valueOf(MainActivity.records.get(position).getConsumption()));
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.setMargins(0, 0, 0, 8);
                date.setLayoutParams(params);
                return view;
            }
        };
        // Inflate the layout for this fragment
        rootview = inflater.inflate(R.layout.fragment_profile, container, false);
        ListView listview = (ListView) rootview.findViewById(R.id.list_view_records);
        listview.setOnItemClickListener(listviewClickListner);
        listview.setAdapter(adapter);
        return rootview;
    }

    private AdapterView.OnItemClickListener listviewClickListner = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Record record = MainActivity.records.get(position);
            MainActivity.recordSource.deleteRecord(record);
            adapter.remove(record);
            adapter.notifyDataSetChanged();
        }
    };
}
