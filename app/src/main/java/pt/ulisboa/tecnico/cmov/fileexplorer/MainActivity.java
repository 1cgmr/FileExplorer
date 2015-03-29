package pt.ulisboa.tecnico.cmov.fileexplorer;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.GridView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends ActionBarActivity implements OnClickListener {

    private Button internalStorage;
    private Button externalStorage;

    private File mydir;

    private GridView gridView;
    private GridviewAdapter mAdapter;
    private ArrayList<String> listFiles;


    @Override
    public void onCreate(final Bundle icicle) {
        super.onCreate(icicle);
        this.setContentView(R.layout.activity_main);

        this.internalStorage = (Button) findViewById(R.id.main_internal_storage_button);
        this.internalStorage.setOnClickListener(this);

        this.externalStorage = (Button) findViewById(R.id.main_external_storage_button);
        this.externalStorage.setOnClickListener(this);

        Context context = this.getApplicationContext();
        mydir = context.getDir("pasta", Context.MODE_PRIVATE);

        File fileWithinMyDir = new File(mydir, "test777.txt");
        FileOutputStream out = null;
        Log.d("ABC", "resteDebugg");

        try {
            out = new FileOutputStream(fileWithinMyDir);
            String string = "teste de esctirebj erggrtg";
            byte[] b = string.getBytes();
            out.write(b);
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //========================================================
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileWithinMyDir));
            Toast.makeText(this, br.readLine(), Toast.LENGTH_LONG).show();
            br.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //================add files do grid============================
        listFiles = new ArrayList<String>();
        for(final File fileEntry : mydir.listFiles()){
            Log.d("ABC", ""+ fileEntry.getName()+ ", length(): " + fileEntry.length() );
            listFiles.add(fileEntry.getName());
        }

        mAdapter = new GridviewAdapter(this,listFiles);
        this.gridView = (GridView) findViewById(R.id.gridView);
        gridView.setAdapter(mAdapter);

        gridView.setOnItemClickListener(new OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                    long arg3) {
                Toast.makeText(MainActivity.this, mAdapter.getItem(position), Toast.LENGTH_SHORT).show();
            }
        });

    }











    public void onClick(View v) {
        if (v.equals(internalStorage)) {
            startActivity(new Intent(this, InternalStorage.class));
        } else if (v.equals(externalStorage)) {
            startActivity(new Intent(this, ExternalStorage.class));
        }
    }
}