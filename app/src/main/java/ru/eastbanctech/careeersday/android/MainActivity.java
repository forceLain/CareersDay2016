package ru.eastbanctech.careeersday.android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.recycler_view) RecyclerView recyclerView;
    private BulbAdapter adapter;
    private WebApi webApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        WebService webService = new WebService(this);
        webApi = webService.getWebApi();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new BulbAdapter();
        recyclerView.setAdapter(adapter);
        updateBulbList();
    }

    private void updateBulbList() {
        Call<Map<String, Boolean>> call = webApi.list();
        call.enqueue(new Callback<Map<String, Boolean>>() {
            @Override
            public void onResponse(Call<Map<String, Boolean>> call, Response<Map<String, Boolean>> response) {
                adapter.setBulbList(response.body());
            }

            @Override
            public void onFailure(Call<Map<String, Boolean>> call, Throwable t) {

            }
        });
    }

    private void updateBulb(String name, boolean value) {
        Call<Boolean> call = webApi.switchBulb(name, value);
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                updateBulbList();
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {

            }
        });
    }

    private class BulbAdapter extends RecyclerView.Adapter<BulbViewHolder> {

        private List<String> bulbList = new ArrayList<>();
        private Map<String, Boolean> bulbMap;

        @Override
        public BulbViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bulb_item, parent, false);
            return new BulbViewHolder(view);
        }

        @Override
        public void onBindViewHolder(BulbViewHolder holder, int position) {
            final String name = bulbList.get(position);
            holder.switchCompat.setText(name);
            Boolean isOn = bulbMap.get(name);
            holder.switchCompat.setChecked(isOn);
            holder.switchCompat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SwitchCompat switchCompat = (SwitchCompat) v;
                    boolean checked = switchCompat.isChecked();
                    updateBulb(name, checked);
                }
            });
        }

        @Override
        public int getItemCount() {
            return bulbList.size();
        }

        public void setBulbList(Map<String, Boolean> bulbMap) {
            this.bulbList.clear();
            if (bulbMap != null) {
                this.bulbList.addAll(bulbMap.keySet());
            }
            this.bulbMap = bulbMap;
            notifyDataSetChanged();
        }
    }

    static class BulbViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.bulb_switch) SwitchCompat switchCompat;

        public BulbViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
