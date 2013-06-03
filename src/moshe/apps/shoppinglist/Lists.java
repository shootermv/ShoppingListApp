package moshe.apps.shoppinglist;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;



import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ResourceCursorAdapter;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.AdapterContextMenuInfo;

public class Lists  extends Activity {
	private static final int DELETE_ID = Menu.FIRST + 1;
	private static final int EDIT_ID = Menu.FIRST + 2;
	private static final int LIST_EDIT=1;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lists_list);
        
        
        
       
        FillListView();
        
        

        Button btnAddNewList=(Button)findViewById(R.id.btnAddNewList);
        
        btnAddNewList.setOnClickListener( new View.OnClickListener() { 
        	
        	//clicking add list button
        	public void onClick(View v) {
            	Intent i = new Intent(v.getContext(), ListItemEdit.class);
            	Long list_id= null;
            	i.putExtra(DBAdapter.KEY_LIST_ID,list_id);
                startActivityForResult(i, LIST_EDIT);
        	}
        });
        

        registerForContextMenu( (ListView)findViewById(R.id.listview));
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
            ContextMenuInfo menuInfo) {
    	
        super.onCreateContextMenu(menu, v, menuInfo);
        AdapterView.AdapterContextMenuInfo info;
        try {
            info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        } catch (ClassCastException e) {
            // If the menu object can't be cast, logs an error.
            //Log.e("MENU", "bad menuInfo", e);
            return;
        }     
    	getMenuInflater().inflate(R.menu.stock_item_menu, menu);
    	menu.setHeaderTitle("Select an Option");
    	//menu.setHeaderIcon(R.drawable.logo);
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        FillListView();
    } 
    
    //fill listView
    private void FillListView(){
    	
        ListView lv= (ListView)findViewById(R.id.listview);
        
        
        DBAdapter db = new DBAdapter(this);
        db.open();
       
        Cursor c=db.getAllLists();
        MyAdapter adapter = new MyAdapter(this, c);
        try{
          lv.setAdapter(adapter);
        }
        catch(Exception e){
        	e.printStackTrace();        
        }
        db.close();  	
    	
    }
    
    
    //start
    private class MyAdapter extends ResourceCursorAdapter {

        public MyAdapter(Context context, Cursor cur) {
            super(context, R.layout.list_item, cur);
        }

        @Override
        public View newView(Context context, Cursor cur, ViewGroup parent) {
            LayoutInflater li = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            return li.inflate(R.layout.lists_list_item, parent, false);
        }

        @Override
        public void bindView(View view, Context context, Cursor cur) {
        	TextView tvListNameText = (TextView)view.findViewById(R.id.itemName);
            Button btnGoToList= (Button)view.findViewById(R.id.btnGoToList);
        	
            tvListNameText.setText(cur.getString(cur.getColumnIndex(DBAdapter.KEY_LISTNAME)));
            
          
            //button go to list click
            btnGoToList.setOnClickListener( new View.OnClickListener() {  
                  public void onClick(View v) {  
                	 
                	Intent i = new Intent(v.getContext(), ShoppingListActivity.class);

                    ListView lv= (ListView)findViewById(R.id.listview);
                    int p  = lv.getPositionForView(v);
                    
                    Cursor c = (Cursor) (lv.getAdapter().getItem(p));
                    final long  rowID = c.getInt(c.getColumnIndex(DBAdapter.KEY_LISTID));
                    
                	i.putExtra(DBAdapter.KEY_LISTID, rowID);
                    startActivityForResult(i, (int) (long)rowID);
                    

                
                   }
                }); //end of btngo clik
         
            
            tvListNameText.setOnClickListener( new View.OnClickListener() {  
                public void onClick(View v) {
                	
                	

                    ListView lv= (ListView)findViewById(R.id.listview);
                    int p  = lv.getPositionForView(v);
                    
                    Cursor c = (Cursor) (lv.getAdapter().getItem(p));
                    final long  rowID = c.getInt(c.getColumnIndex(DBAdapter.KEY_LISTID));
                    
                	//Toast toast = Toast.makeText(v.getContext(),rowID+ "ui" ,Toast.LENGTH_SHORT);
                	//toast.show();                   
                	openContextMenu(v);
                }
                
            });
        }


    } 
    //end
    
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
        }
    }
 
    
    @Override
    public boolean onContextItemSelected(MenuItem item) {
    	int res=item.getItemId();
    	AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
        switch(res) {
       /*
            case DELETE_ID:                
                DBAdapter db = new DBAdapter(this);
                db.open();
                db.deleteItem(info.id);
                db.close();
                fillData();
                return true;
               */
            case R.id.edit_item:
            	Intent i = new Intent(this, ListItemEdit.class);
            	i.putExtra(DBAdapter.KEY_LIST_ID, info.id);
                startActivityForResult(i, LIST_EDIT);
               
            //	ListView lv= (ListView)findViewById(R.id.listview);
            //	Toast toast = Toast.makeText(lv.getContext(),info.id+ "ui" ,Toast.LENGTH_SHORT);
            //	toast.show();           	
            	return true;
            case R.id.remove_item:
                DBAdapter db = new DBAdapter(this);
                db.open();
                db.deleteList(info.id);
                db.close();
                FillListView();
               
            	
            	return true;
            	
        }
        return super.onContextItemSelected(item);
    }
       
    
}
