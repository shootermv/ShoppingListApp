package moshe.apps.shoppinglist;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;



import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.ResourceCursorAdapter;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class Lists  extends Activity {
	private static final int DELETE_ID = Menu.FIRST + 1;
	private static final int EDIT_ID = Menu.FIRST + 2;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lists_list);
       	
       	/*
    	DBAdapter db1 = new DBAdapter(this);
    	db1.open();
    	db1.insertNewList("bb list");
    	db1.close();
    	*/
    	       
        
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


            
            
            
            tvListNameText.setText(cur.getString(cur.getColumnIndex(DBAdapter.KEY_LISTNAME)));
         
            
            final int  rowID = cur.getInt(cur.getColumnIndex(DBAdapter.KEY_ROWID));
            //checkbox click
          /*  cbListCheck.setOnClickListener( new View.OnClickListener() {  
                public void onClick(View v) {  
                    CheckBox cb = (CheckBox) v ;  
                    SaveDone(cb.isChecked(), v.getContext(), rowID);
                  }  
                }); */
         
        }
        /*
         private void SaveDone(boolean isDone,Context contex,int  rowID){
 	        DBAdapter db = new DBAdapter(contex);
 		    
 	    	db.open();         
             
             
             db.updateIsDone( rowID,isDone);
             db.close();	 
        	 
           
         }*/

    } 
    //end
    
    
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, DELETE_ID, 0, R.string.menu_delete);
        menu.add(0, EDIT_ID, 0, R.string.menu_edit);
      
    } 
    
    
    
}