
package com.michaelflisar.messagebar.demo;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.michaelflisar.messagebar.MessageBar;
import com.michaelflisar.messagebar.MessageBarActivity;
import com.michaelflisar.messagebar.messages.BaseMessage;
import com.michaelflisar.messagebar.messages.BaseMessage.OnMessageClickListener;
import com.michaelflisar.messagebar.messages.ConstantMessage;
import com.michaelflisar.messagebar.messages.TextMessage;
import com.michaelflisar.messagebar.messages.Timer2ButtonMessage;
import com.michaelflisar.messagebartest.R;

public class MainActivity extends MessageBarActivity
{
    // this message bar is not retained on screen rotation (see code of MessageBarActivity to see how this would be achieved)
    // short solution:
    // just call mExtraMessageBar.onRestoreInstanceState(bundle) and 
    // mExtraMessageBar.onSaveInstanceState(bundle) in the corresponding functions of this activity
    private MessageBar mExtraMessageBar = null;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // from here on you can use the message bar
        // the custom message classes are jsut for convenience...
        // using the BaseMessage class is always possible
        
        // HINTS:
        // for not showing buttons, set the button parameter to null
        // for not using images, set resId of image to -1
        
        // if constructor for your message does not exist, you have following two possibilities:
        // 1) add the constructor and make a pull request
        // 2) create the message using the BaseMessage...
                
        if (savedInstanceState == null)
        {
            addAll();
        }
    }
    
    private void addAll()
    {
        // 1) creating some messages
        TextMessage message1 = new TextMessage("text only");
        TextMessage message2 = new TextMessage("single button", "ok", null);
        TextMessage message3 = new TextMessage("single button with image", "ok", R.drawable.ic_launcher); 
        TextMessage message4 = new TextMessage("double button message (with button listener)", "ok", "cancel");
        
        Timer2ButtonMessage message5 = new Timer2ButtonMessage("single button with timer", "ok", null);
        BaseMessage message6 = new BaseMessage("base message", null, -1, null, -1, 10000, true, null);
        ConstantMessage message7 = new ConstantMessage("constant message", "dismiss");
        
        // 2) adding some button listeners
        message4.setClickListener(new OnMessageClickListener()
        {   
            @Override
            public void onButton2Click(Parcelable data)
            {
                Toast.makeText(MainActivity.this, "cancel pressed", 3000).show();
            }
            
            @Override
            public void onButton1Click(Parcelable data)
            {
                Toast.makeText(MainActivity.this, "ok pressed", 3000).show();
            }
        });

        // 3) adding all messages to the message bar queue (showing both ways to do it)
        message1.show(getMessageBar());
        message2.show(getMessageBar());
        
        getMessageBar().show(message3);
        getMessageBar().show(message4);
        getMessageBar().show(message5);
        getMessageBar().show(message6);
        
        getExtraMessageBar().show(message7);
    }
    
    private MessageBar getExtraMessageBar()
    {
        if (mExtraMessageBar == null)
            mExtraMessageBar = new MessageBar(findViewById(R.id.testTextViewContainer), true);
        
        return mExtraMessageBar;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        
        if (item.getItemId() == R.id.action1)
            addAll();
        
        return true;
        
    }

}
