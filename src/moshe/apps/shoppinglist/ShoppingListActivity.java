package moshe.apps.shoppinglist;




import android.app.ListActivity;
import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;

import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.AdapterContextMenuInfo;

import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ResourceCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class ShoppingListActivity extends ListActivity {
    /** Called when the activity is first created. */
	public static final int INSERT_ID = Menu.FIRST;
	private static final int ACTIVITY_CREATE=0;
	private static final int ACTIVITY_EDIT=1;
	private static final int DELETE_ID = Menu.FIRST + 1;
	private static final int EDIT_ID = Menu.FIRST + 2;
	private Long mListId;
	private boolean isFirstTime=true;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        
        mListId   = (savedInstanceState == null) ? null :
            (Long) savedInstanceState.getSerializable(DBAdapter.KEY_LISTID);
		if (mListId == null) {
			Bundle extras = getIntent().getExtras();
			mListId = extras != null ? extras.getLong(DBAdapter.KEY_LISTID)
									: null;
			
			
			
		}       
        
        
        //setContentView(R.layout.main);
        
        
        //insert some record into DB:
    	
    	//---add 2 titles---
    	
    	
    	/*	long id;
    	DBAdapter db = new DBAdapter(this);
    	id = db.insertTitle(
    	"0470285818",
    	"Momo",
    	"Wrox");
    	id = db.insertTitle(
    	"047017661X",
    	"Professional Windows Vista Gadgets Programming",
    	"Wrox");
    	db.close();
    	
       
       // db = new DBAdapter(this);
      
      //---get a title---
      db.open();
      Cursor c = db.getTitle(1);
      if (c.moveToFirst())
      DisplayTitle(c); 
      else
      Toast.makeText(this, "No title found",
      Toast.LENGTH_LONG).show();
      db.close();
      */


        
        try {
        	/*
        	DBAdapter db = new DBAdapter(this);
        	db.open();
        	db.insertNewList("usual list");
        	db.close();
        	*/
		/*	DBAdapter db = new DBAdapter(this);
			db.open();
           db.updateTitle(2,"done",
					"Sugar",
					"Wrox") ; */
			//db.deleteTitle(1);
			
	    	//long id = db.insertTitle(
	    	    //	"047017661X",
	    	    //	"Milk",
	    	    //	"Wrox");
	    	
	    //	Toast.makeText(this,"one record inserted",
	    //	Toast.LENGTH_LONG).show();
		
		//	db.close();	
        	
        	
        	if(mListId != null){
        	  setContentView(R.layout.items_list);
        	  fillData();
        	  fillListsSpinner();
        	}

        	
        	
        	
        	registerForContextMenu(getListView());
        	
        	
		} catch (SQLException e) {
		
			e.printStackTrace();
		}
        
      
        

    }//onCreate end
    public void fillListsSpinner(){
    	
    	    DBAdapter db=new DBAdapter(this);
    	    db.open();
    	

    	    
    	    
    	    
    	    
    	    
    	    Cursor listsCursor;
    		Spinner listsSpinner = (Spinner) findViewById(R.id.changeList);
    		listsCursor = db.getAllLists();

    		startManagingCursor( listsCursor);
    		/*Create an array to specify the fields we want to display in the list (only the 'colourName' column in this case) */
            
    		
    		
    		String[] from = new String[]{DBAdapter.KEY_LISTNAME}; 
    		
    		int[] to = new int[]{R.id.tvListName};

    		 
    		SimpleCursorAdapter listsAdapter =
    		new SimpleCursorAdapter(this, R.layout.spinner_row, listsCursor, from, to);
            
    		 
    		listsSpinner.setAdapter(listsAdapter);
    		db.close();    		 	    	    	 
            
    		
    		
    		
    		
    		
    		
    		
    		
    		/*
    		
    		SpinnerAdapter listss = new SpinnerAdapter(this, listsCursor);
	    	
    		listsSpinner.setAdapter(listss);        
	        */
	
	    	db.close();
	    	
	    	
	    	//attach handler
	    	listsSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
	    	    @Override
	    	    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
	    	    	Cursor c = (Cursor) (parentView.getAdapter().getItem(position)); 
	    	    	long _id=c.getLong(c.getColumnIndex(DBAdapter.KEY_LISTID));
	    	    	
	    	    	if(isFirstTime ==true){
	    	    	 
	    	    	  fillData();
	    	    	  isFirstTime =false;
	    	    	}
	    	    	else{
	    	    		mListId =_id;
	    	    		fillData();
	    	    	}
	    	    	
	    	    }

	    	    @Override
	    	    public void onNothingSelected(AdapterView<?> parentView) {
	    	        // your code here
	    	    }

	    	});
    		
    		
    		
    		
    		/*
    		listsSpinner.setOnItemSelectedListener(
                    new OnItemSelectedListener() {
                        public void onItemSelected(
                                AdapterView<?> parent, View view, int position, long id) {
                            showToast("Spinner2: position=" + position + " id=" + id);
                        }

                        public void onNothingSelected(AdapterView<?> parent) {
                            showToast("Spinner2: unselected");
                        }
                    });
           */
    }
    
    
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
       menu.add(0, DELETE_ID, 0, R.string.menu_delete);
       menu.add(0, EDIT_ID, 0, R.string.menu_edit);
      
    }
    
    @Override
    public boolean onContextItemSelected(MenuItem item) {
    	int res=item.getItemId();
    	AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
        switch(res) {
        
            case DELETE_ID:                
                DBAdapter db = new DBAdapter(this);
                db.open();
                db.deleteItem(info.id);
                db.close();
                fillData();
                return true;
                
            case EDIT_ID:
            	//DBAdapter db = new DBAdapter(this);
            	Intent i = new Intent(this, ItemEdit.class);
            	i.putExtra(DBAdapter.KEY_ROWID, info.id);
                startActivityForResult(i, ACTIVITY_EDIT);
            	return true;
            	
        }
        return super.onContextItemSelected(item);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	boolean result = super.onCreateOptionsMenu(menu);
        menu.add(0, INSERT_ID, 0, R.string.menu_insert);
        return result;
    }
    
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case INSERT_ID:
        	createItem();
            return true;
        }
       
        return super.onOptionsItemSelected(item);
    }
    
    private void fillData() {
		try{
	    	
	        DBAdapter db = new DBAdapter(this);
		    
	    	db.open();
	    	Cursor c = db.getAllItems(mListId);
	    	/*old code using SimpleCursorAdapter
	    	startManagingCursor(c);
	
	    	
	        String[] from = new String[] { DBAdapter.KEY_TITLE ,DBAdapter.KEY_ISDONE};//
	        int[] to = new int[] { R.id.text1, R.id.chkIsDone }; 	
	        
	        // Now create an array adapter and set it to display using our row
	        SimpleCursorAdapter notes =
	            new SimpleCursorAdapter(this, R.layout.list_item, c, from, to);
	        */
	    	
	    	MyAdapter notes = new MyAdapter(this, c);
	    	
	        setListAdapter(notes);        
	        
	
	    	db.close();
		}
		catch(Exception e)
		{
			
			
		}

    }
    private class SpinnerAdapter extends ResourceCursorAdapter {                               

        public SpinnerAdapter(Context context, Cursor cur) {
            super(context, R.layout.spinner_row, cur);
        }

        @Override
        public View newView(Context context, Cursor cur, ViewGroup parent) {
            LayoutInflater li = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            return li.inflate(R.layout.spinner_row, parent, false);
        }        
        @Override
        public void bindView(View view, Context context, Cursor cur) {
        	TextView tv= (TextView)view.findViewById(R.id.tvListName);
        	 final long  listID = cur.getInt(cur.getColumnIndex(DBAdapter.KEY_LISTID));
        	 tv.setText(cur.getString(cur.getColumnIndex(DBAdapter.KEY_LISTNAME)));
        /*	tv.setOnClickListener( new View.OnClickListener() {  
                public void onClick(View v) {  
                   // CheckBox cb = (CheckBox) v ;  
                   // SaveDone(cb.isChecked(), v.getContext(), listID);
                	Toast toast = Toast.makeText(v.getContext(), String.valueOf(listID), Toast.LENGTH_SHORT);
                	toast.show();
                  }  
            }); */
        }
    }
    private class MyAdapter extends ResourceCursorAdapter {


        public MyAdapter(Context context, Cursor cur) {
            super(context, R.layout.list_item, cur);
        }

        @Override
        public View newView(Context context, Cursor cur, ViewGroup parent) {
            LayoutInflater li = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            return li.inflate(R.layout.list_item, parent, false);
        }
        @Override
        public void bindView(View view, Context context, Cursor cur) {
        	TextView tvQuantityText = (TextView)view.findViewById(R.id.txtQuantity);
            TextView tvListText = (TextView)view.findViewById(R.id.text1);
            CheckBox cbListCheck = (CheckBox)view.findViewById(R.id.chkIsDone);

            
            
            tvListText.setText(cur.getString(cur.getColumnIndex(DBAdapter.KEY_TITLE)));
            tvQuantityText.setText(cur.getString(cur.getColumnIndex(DBAdapter.KEY_QUANTITY)));
            cbListCheck.setChecked((cur.getInt(cur.getColumnIndex(DBAdapter.KEY_ISDONE))==0? false:true));
            
            final int  rowID = cur.getInt(cur.getColumnIndex(DBAdapter.KEY_ROWID));
            //checkbox click
            cbListCheck.setOnClickListener( new View.OnClickListener() {  
                public void onClick(View v) {  
                    CheckBox cb = (CheckBox) v ;  
                    SaveDone(cb.isChecked(), v.getContext(), rowID);
                  }  
                }); 
         
        }
         private void SaveDone(boolean isDone,Context contex,int  rowID){
 	        DBAdapter db = new DBAdapter(contex);
 		    
 	    	db.open();         
             
             
             db.updateIsDone( rowID,isDone);
             db.close();	 
        	 
           
         }

    }    
    /*
    private void createItem() {
    	
        DBAdapter db = new DBAdapter(this);
	    
    	db.open();       
    	long id = db.insertTitle(
    	    	"047017661X",
    	    	"Sugar",
    	    	"Wrox");
    	db.close();
        fillData();
    }*/

    public void DisplayTitle(Cursor c)
    {
	    Toast.makeText(this,
	    "id: " + c.getString(0) + "\n" +
	    "ISBN: " + c.getString(1) + "\n" +
	    "TITLE: " + c.getString(2) + "\n" +
	    "PUBLISHER: " + c.getString(3),
	    Toast.LENGTH_LONG).show();
    }    
    
    private void createItem() {
    	try{
          Intent i = new Intent(this, ItemEdit.class);
          i.putExtra(DBAdapter.KEY_LIST_ID, mListId);
           startActivityForResult(i, ACTIVITY_CREATE);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}     
    }
   /*
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Intent i = new Intent(this, NoteEdit.class);
        i.putExtra(NotesDbAdapter.KEY_ROWID, id);
        startActivityForResult(i, ACTIVITY_EDIT);
    }
   */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        fillData();
    }
}