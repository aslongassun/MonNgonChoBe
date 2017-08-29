package com.vmcop.simplefour.monanngon.tabfragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.vmcop.simplefour.monanngon.model.BeanPost;
import com.vmcop.simplefour.monanngon.DetailActivity;
import com.vmcop.simplefour.monanngon.R;
import com.vmcop.simplefour.monanngon.util.ImageAdapter;
import com.vmcop.simplefour.monanngon.util.Util;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class TabFragment extends Fragment{
    private ArrayList<BeanPost> beanPostArrayList;
    private GridView gridView;
    private String JSONFile;
    private String tabName;

    public TabFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new AsyncTask<Void, Void, Void>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected Void doInBackground(Void... voids) {
                Type listType = new TypeToken<ArrayList<BeanPost>>() {}.getType();
                beanPostArrayList = new GsonBuilder().create().fromJson(Util.loadJSONFromAsset(getContext().getResources().getAssets(), JSONFile + ".json"), listType);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                if (beanPostArrayList != null) {

                    gridView.setAdapter(new ImageAdapter(getContext(), beanPostArrayList, JSONFile));

                    gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        public void onItemClick(AdapterView<?> parent, View v,
                                                int position, long id) {
                            Intent intent = new Intent(getContext(), DetailActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putInt("position", position);
                            bundle.putString("jsonfile", JSONFile);
                            intent.putExtras(bundle);
                            startActivityForResult(intent, 0);
                            getActivity().overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                        }
                    });

                }
            }
        }.execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View resultInflater = inflater.inflate(R.layout.tab_fragment, container, false);
        gridView = (GridView) resultInflater.findViewById(R.id.gridview);
        return resultInflater;
    }

    public String getJSONFile() {
        return JSONFile;
    }

    public void setJSONFile(String JSONFile) {
        this.JSONFile = JSONFile;
    }

    public String getTabName() {
        return tabName;
    }

    public void setTabName(String tabName) {
        this.tabName = tabName;
    }
}
