package moshe.apps.shoppinglist;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.EditText;
public class ListItemEdit extends Activity{
	private Long mRowId;
	private EditText mListName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.list_item_edit);
      
        mRowId   = (savedInstanceState == null) ? null :
            (Long) savedInstanceState.getSerializable(DBAdapter.KEY_LIST_ID);
		if (mRowId == null) {
			Bundle extras = getIntent().getExtras();
			mRowId = extras != null ? (extras.getLong(DBAdapter.KEY_LIST_ID)>0?extras.getLong(DBAdapter.KEY_LIST_ID):null)
									: null;
		}
                
       
        //	Toast toast = Toast.makeText(getApplicationContext(),mRowId+ " ui" ,Toast.LENGTH_SHORT);
		if(mRowId != null){
			DBAdapter db=new DBAdapter(this);
			db.open();
			Cursor c =db.getListItem( mRowId ) ;          		
		
			mListName= (EditText) findViewById(R.id.txtListName);
			mListName.setText(c.getString(c.getColumnIndexOrThrow(DBAdapter.KEY_LISTNAME)));	    
		}
		
		Button saveButton = (Button) findViewById(R.id.btnListadd);		
		
		saveButton.setOnClickListener(new View.OnClickListener() {
        	 
            public void onClick(View view) {  
            	
                       	                        	           
                	
                	DBAdapter db = new DBAdapter(view.getContext());
                	db.open(); 
               		db.updateListItem(mRowId,
               				mListName.getText().toString()) ;
                	
                  	db.close();  
            	
            }
            
		});
       
		
    }//end of oncreate
}//end of activity
