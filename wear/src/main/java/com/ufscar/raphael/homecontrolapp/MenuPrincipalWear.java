package com.ufscar.raphael.homecontrolapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.support.wearable.view.BoxInsetLayout;
import android.support.wearable.view.WearableListView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Locale;



public class MenuPrincipalWear extends WearableActivity implements WearableListView.ClickListener  {

    private static final SimpleDateFormat AMBIENT_DATE_FORMAT =
            new SimpleDateFormat("HH:mm", Locale.US);

    private BoxInsetLayout mContainerView;
    private TextView mTextView;
    private TextView mClockView;

    // Sample dataset for the list
    String[] elements = { "Rele 1 - Quarto", "Rele 2 - Sala","Umidade","Temperatura"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal_wear);
        setAmbientEnabled();

        mContainerView = (BoxInsetLayout) findViewById(R.id.container_menu_principal);
        //mTextView = (TextView) findViewById(R.id.text);
        //mClockView = (TextView) findViewById(R.id.clock);

        // Get the list component from the layout of the activity
        WearableListView listView =
                (WearableListView) findViewById(R.id.wearable_list);

        // Assign an adapter to the list
        listView.setAdapter(new Adapter(this, elements));

        // Set a click listener
        listView.setClickListener(this);

    }

    // WearableListView click listener
    @Override
    public void onClick(WearableListView.ViewHolder v) {
        Integer tag = (Integer) v.itemView.getTag();
        // use this data to complete some action ...
        //Toast.makeText(getApplicationContext(), "Item: " + tag , Toast.LENGTH_LONG).show();


        switch (tag){
            case 0:
                Intent intentRele = new Intent(this, ReleLigaDesliga.class);
                intentRele.putExtra("RELE",1);
                startActivity(intentRele);
                break;
            case 1:
                Intent intentRele2 = new Intent(this, ReleLigaDesliga.class);
                intentRele2.putExtra("RELE",2);
                startActivity(intentRele2);
                break;
            case 2:
                Intent intentUmi = new Intent(this,UmidadeTemperatura.class);
                intentUmi.putExtra("UMI_TEMP",0);//0-Umidade, 1-Temperatura
                startActivity(intentUmi);
                break;
            case 3:
                Intent intentTemp = new Intent(this,UmidadeTemperatura.class);
                intentTemp.putExtra("UMI_TEMP",1);//0-Umidade, 1-Temperatura
                startActivity(intentTemp);
                break;
        }

    }

    @Override
    public void onTopEmptyRegionClick() {
    }


    @Override
    public void onEnterAmbient(Bundle ambientDetails) {
        super.onEnterAmbient(ambientDetails);
        updateDisplay();
    }

    @Override
    public void onUpdateAmbient() {
        super.onUpdateAmbient();
        updateDisplay();
    }

    @Override
    public void onExitAmbient() {
        updateDisplay();
        super.onExitAmbient();
    }

    private void updateDisplay() {
    /*
        if (isAmbient()) {

            mContainerView.setBackgroundColor(getResources().getColor(android.R.color.black));
            mTextView.setTextColor(getResources().getColor(android.R.color.white));
            mClockView.setVisibility(View.VISIBLE);

            mClockView.setText(AMBIENT_DATE_FORMAT.format(new Date()));

        } else {

            mTextView.setTextColor(getResources().getColor(android.R.color.black));
            mClockView.setVisibility(View.GONE);

        }

        */
    }



    private static final class Adapter extends WearableListView.Adapter {
        private String[] mDataset;
        private final Context mContext;
        private final LayoutInflater mInflater;

        // Provide a suitable constructor (depends on the kind of dataset)
        public Adapter(Context context, String[] dataset) {
            mContext = context;
            mInflater = LayoutInflater.from(context);
            mDataset = dataset;
        }

        // Provide a reference to the type of views you're using
        public static class ItemViewHolder extends WearableListView.ViewHolder {
            private TextView textView;
            public ItemViewHolder(View itemView) {
                super(itemView);
                // find the text view within the custom item's layout
                textView = (TextView) itemView.findViewById(R.id.name);
            }
        }

        // Create new views for list items
        // (invoked by the WearableListView's layout manager)
        @Override
        public WearableListView.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                              int viewType) {
            // Inflate our custom layout for list items
            return new ItemViewHolder(mInflater.inflate(R.layout.list_item, null));
        }

        // Replace the contents of a list item
        // Instead of creating new views, the list tries to recycle existing ones
        // (invoked by the WearableListView's layout manager)
        @Override
        public void onBindViewHolder(WearableListView.ViewHolder holder,
                                     int position) {
            // retrieve the text view
            ItemViewHolder itemHolder = (ItemViewHolder) holder;
            TextView view = itemHolder.textView;
            // replace text contents
            view.setText(mDataset[position]);
            // replace list item's metadata
            holder.itemView.setTag(position);
        }

        // Return the size of your dataset
        // (invoked by the WearableListView's layout manager)
        @Override
        public int getItemCount() {
            return mDataset.length;
        }
    }
}



