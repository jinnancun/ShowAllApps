package com.example.itdev1.showallapps;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.Collections;
import java.util.List;

public class MainActivity extends Activity {
    /**
     * Information that is returned from resolving an intent
     * against an IntentFilter. This partially corresponds to
     * information collected from the AndroidManifest.xml's
     * &lt;intent&gt; tags.
     */
    private List<ResolveInfo> mAllApps;
    /**
     * Class for retrieving various kinds of information related to the application
     * packages that are currently installed on the device.
     */
    private PackageManager mPackageManager;
    private GridItemAdapter mAdapter;
    private GridView mGridView;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;
        mGridView = (GridView) findViewById(R.id.myGrid);
        mPackageManager = getPackageManager();
        bindAllApps();
        mAdapter = new GridItemAdapter(mContext, mAllApps);
        mGridView.setAdapter(mAdapter);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                ActivityInfo activity = mAllApps.get(position).activityInfo;
                ComponentName name = new ComponentName(activity.applicationInfo.packageName,
                        activity.name);

                Intent i = new Intent(Intent.ACTION_MAIN);
                i.addCategory(Intent.CATEGORY_LAUNCHER);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                        Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                i.setComponent(name);

                startActivity(i);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // TODO MODEL
    public void bindAllApps()
    {
        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);

        mAllApps = mPackageManager.queryIntentActivities(mainIntent, 0);
        Collections.sort(mAllApps, new ResolveInfo.DisplayNameComparator(mPackageManager));

        for(int i = 0; i < mAllApps.size(); i++)
        {
            if(mAllApps.get(i).loadLabel(mContext.getPackageManager()).toString().equalsIgnoreCase("App Master"))
            {
                mAllApps.remove(i);
                break;
            }
        }

        System.out.println("For GitHub Test Only.");
    }
}