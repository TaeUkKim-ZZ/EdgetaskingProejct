package com.prizmcore.edgetasking;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.view.Window;
import android.view.View;
import android.widget.AdapterView;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class edgelist extends Activity {

    ListView listview;
    ArrayAdapter<Adapter> abAdapter;
    final ArrayList<Adapter> abList = new ArrayList<Adapter>();
    String check[] = new String[10001];
    int checkint;
    int detect = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.list);

        listview = (ListView)findViewById(R.id.id_list);
        listview.setDivider(null);




        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> pkgAppsList = getPackageManager().queryIntentActivities( mainIntent, 0);




        for(int i = 0; i < pkgAppsList.size(); i++){
            ResolveInfo app = pkgAppsList.get(i);
            Log.d("check", Integer.toString(i));
            Log.d("check1", app.activityInfo.packageName);
            for(int j = 0; j < checkint; j++)
            {
                Log.d("GROUP3", check[j]);
                if(app.activityInfo.packageName.equals(check[j]))
                {
                    detect = 1;
                }
            }
            if(detect == 0)
            {
                Adapter thing = new Adapter( app.activityInfo.loadIcon(getPackageManager()), app.activityInfo.packageName);
                abList.add(thing);
                check[checkint] = app.activityInfo.packageName;
                checkint++;

            }
            detect = 0;

        }


        abAdapter = new ABArrayAdapter(this, R.layout.app, abList);
        listview.setAdapter(abAdapter);



        listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                finish();
            }
        });



    }

    @Override
    protected void onStop() {
        super.onStop();
        startService(new Intent(this, EdgeService.class));
    }

    @Override
    protected void onPause() {
        super.onPause();
        startService(new Intent(this, EdgeService.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        startService(new Intent(this, EdgeService.class));
    }


}
