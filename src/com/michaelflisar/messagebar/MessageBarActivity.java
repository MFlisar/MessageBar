
package com.michaelflisar.messagebar;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;

public class MessageBarActivity extends Activity
{
    private MessageBar mMessageBar = null;

    @Override
    public void setContentView(int layoutResId)
    {
        setContentView(getLayoutInflater().inflate(layoutResId, null));
    }

    @Override
    public void setContentView(View layout)
    {
        setContentView(layout, null);
    }

    @Override
    public void setContentView(View layout, LayoutParams params)
    {
        super.setContentView(layout, params == null ? new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT) : params);
        mMessageBar = new MessageBar(layout, true);
    }

    @Override
    protected void onRestoreInstanceState(Bundle inState)
    {
        super.onRestoreInstanceState(inState);
        if (inState.containsKey("mMessageBar"))
            mMessageBar.onRestoreInstanceState(inState.getBundle("mMessageBar"));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        if (mMessageBar != null)
            outState.putBundle("mMessageBar", mMessageBar.onSaveInstanceState());
    }

    protected MessageBar getMessageBar()
    {
        return mMessageBar;
    }
}
