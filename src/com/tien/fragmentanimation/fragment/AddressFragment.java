/**
 * 
 */
package com.tien.fragmentanimation.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.tien.fragmentanimation.R;

/**
 * TODO
 * @author wangtianfei01
 *
 */
public class AddressFragment extends Fragment {
    
    private ListView addressLV;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.address_view, null);
        
        addressLV = (ListView) view;
        return view;
    }
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.address_listview_item, R.id.address_tv, new String[]{"Paris","Lyon","London","Lille","Marseille","Strasbourg"});
        addressLV.setAdapter(adapter);
    }
    
}
