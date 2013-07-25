
package com.michaelflisar.messagebar.demo;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import com.michaelflisar.messagebar.MessageBar;
import com.michaelflisar.messagebar.MessageBarActivity;
import com.michaelflisar.messagebar.messages.BaseMessage;
import com.michaelflisar.messagebar.messages.BaseMessage.OnMessageClickListener;
import com.michaelflisar.messagebar.messages.ConstantMessage;
import com.michaelflisar.messagebar.messages.TextMessage;
import com.michaelflisar.messagebar.messages.Timer2ButtonMessage;
import com.michaelflisar.messagebartest.R;

public class MainActivity extends MessageBarActivity implements OnClickListener
{
    // this message bar is not retained on screen rotation (see code of MessageBarActivity to see how this would be achieved)
    // short solution:
    // just call mExtraMessageBar.onRestoreInstanceState(bundle) and
    // mExtraMessageBar.onSaveInstanceState(bundle) in the corresponding functions of this activity
    private MessageBar mExtraMessageBar = null;

    private OnMessageClickListener mListener = null;

    private CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkBox = (CheckBox) findViewById(R.id.cb);

        // from here on you can use the message bar
        // the custom message classes are jsut for convenience...
        // using the BaseMessage class is always possible

        // HINTS:
        // for not showing buttons, set the button parameter to null
        // for not using images, set resId of image to -1

        // if constructor for your message does not exist, you have following two possibilities:
        // 1) add the constructor and make a pull request
        // 2) create the message using the BaseMessage...

        mListener = new OnMessageClickListener()
        {
            @Override
            public void onButton2Click(Parcelable data)
            {
                Toast.makeText(MainActivity.this, "button 2 pressed", 3000).show();
            }

            @Override
            public void onButton1Click(Parcelable data)
            {
                Toast.makeText(MainActivity.this, "button 1 pressed", 3000).show();
            }
        };
    }

    private MessageBar getExtraMessageBar()
    {
        if (mExtraMessageBar == null)
            mExtraMessageBar = new MessageBar(findViewById(R.id.linearLayout1), true);

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
    public boolean onOptionsItemSelected(MenuItem item)
    {

        if (item.getItemId() == R.id.link)
        {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://michaelflisar.github.io/MessageBar/"));
            startActivity(browserIntent);
        }
        else if (item.getItemId() == R.id.clear)
        {
            getMessageBar().clear();
            getExtraMessageBar().clear();
        }
        return true;
    }

    @Override
    public void onClick(View v)
    {
        final MessageBar mb = checkBox.isChecked() ? getExtraMessageBar() : getMessageBar();
        final List<BaseMessage> messages = new ArrayList<BaseMessage>();

        ArrayList<String> items = new ArrayList<String>();
        items.add("add all messages");
        items.add("add text message");
        items.add("add text message with button");
        items.add("add test message with image button");
        items.add("add 2 button message with listener");
        items.add("add constant message");
        items.add("add timer message");
        
        ListView lv = new ListView(this);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Make your selection");
        builder.setView(lv);
        lv.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items));
        lv.setOnItemClickListener(new OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long arg3)
            {
                int i = 1;
                if (pos == 0 || pos == i++)
                    messages.add(new TextMessage("text only"));
                if (pos == 0 || pos == i++)
                    messages.add(new TextMessage("single button", "ok", null));
                if (pos == 0 || pos == i++)
                    messages.add(new TextMessage("single button", "ok", R.drawable.ic_launcher));
                if (pos == 0 || pos == i++)
                {
                    TextMessage message = new TextMessage("double button (with listener)", "yes", "no");
                    message.setClickListener(mListener);
                    messages.add(message);
                }
                if (pos == 0 || pos == i++)
                    messages.add(new ConstantMessage("constant message", "dismiss"));
                if (pos == 0 || pos == i++)
                    messages.add(new Timer2ButtonMessage("text with timer", "ok", null));
            }
        });
        builder.setPositiveButton("back", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                for (int i = 0; i < messages.size(); i++)
                    messages.get(i).show(mb); // alternatively: mb.show(messages.get(i));
                dialog.dismiss();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

}
