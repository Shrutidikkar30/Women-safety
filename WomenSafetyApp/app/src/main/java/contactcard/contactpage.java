package contactcard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.MenuItemCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.womensafetyapp.R;

import java.util.ArrayList;

public class contactpage extends AppCompatActivity {

    private static final int PERMISSION_CODE = 100;
    DatabaseHelper db;
    Button add_data;
    EditText add_name;
    ListView userlist;

    ArrayList<String> listItems;
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactpage);

        db = new DatabaseHelper(this);

        listItems=new ArrayList<>();

        add_data = findViewById(R.id.add_data);
        add_name = findViewById(R.id.add_name);
        userlist = findViewById(R.id.user_list);

        viewData();

        if (ContextCompat.checkSelfPermission(contactpage.this, Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED);
        {
            ActivityCompat.requestPermissions(contactpage.this,new String[]{Manifest.permission.CALL_PHONE},PERMISSION_CODE);

        }
        userlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String text=userlist.getItemAtPosition(position).toString();
                String phoneno=text;
                Intent i=new Intent(Intent.ACTION_CALL);
                i.setData(Uri.parse("tel:"+phoneno));
                startActivity(i);
                Toast.makeText(contactpage.this," Called-- "+text,Toast.LENGTH_SHORT).show();
            }
        });

        add_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = add_name.getText().toString();
                if (!name.equals("") && db.insertData(name)) {
                    Toast.makeText(contactpage.this, "Contact Added", Toast.LENGTH_SHORT).show();
                    add_name.setText("");
                    listItems.clear();
                    viewData();
                } else {
                    Toast.makeText(contactpage.this, "Contact not Added", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void viewData() {
        Cursor cursor=db.viewData();

        if(cursor.getCount()==0)
        {
            Toast.makeText(this,"No data to show",Toast.LENGTH_SHORT).show();
        }
        else
        {
            while (cursor.moveToNext())
            {
                listItems.add(cursor.getString(1));
            }
        }
        adapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,listItems);
        userlist.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater=getMenuInflater();

        inflater.inflate(R.menu.menu,menu);

        MenuItem searchItem=menu.findItem(R.id.item_search);
        SearchView searchView=(SearchView) MenuItemCompat.getActionView(searchItem);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<String> userslist=new ArrayList<>();

                for (String user:listItems)
                {
                    if (user.toLowerCase().contains(newText.toLowerCase()))
                    {
                        userslist.add(user);
                    }
                }

                ArrayAdapter<String> adapter=new ArrayAdapter<String>(contactpage.this,android.R.layout.simple_list_item_1,userslist);
                userlist.setAdapter(adapter);

                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }
}