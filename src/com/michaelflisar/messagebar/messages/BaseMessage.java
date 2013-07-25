
package com.michaelflisar.messagebar.messages;

import com.michaelflisar.messagebar.MessageBar;

import android.os.Parcel;
import android.os.Parcelable;

public class BaseMessage implements Parcelable
{
    public interface OnMessageClickListener 
    {
        void onButton1Click(Parcelable data);
        void onButton2Click(Parcelable data);
    }

    protected static final int DEFAULT_HIDE_DELAY = 5000;
    
    protected String mMessage = null;
    protected String mButton1Text = null;
    protected int mResButton1Icon = -1;
    protected String mButton2Text = null;
    protected int mResButton2Icon = -1;
    protected int mHideDelay = DEFAULT_HIDE_DELAY;
    protected boolean mCountdownEnabled = false;
    protected Parcelable mData = null;
    
    protected OnMessageClickListener mClickListener = null;
    
    public BaseMessage(String message, String button1Text, int resButton1Icon, String button2Text, int resButton2Icon, int hideDelay, boolean countdownEnabled, Parcelable data)
    {
        init(message, button1Text, resButton1Icon, button2Text, resButton2Icon, hideDelay, countdownEnabled, data);
    }
    
    public void show(MessageBar messageBar)
    {
        messageBar.show(this);
    }

    private void init(String message, String button1Text, int resButton1Icon, String button2Text, int resButton2Icon, int hideDelay, boolean countdownEnabled, Parcelable data)
    {
        mMessage = message;
        mButton1Text = button1Text;
        mResButton1Icon = resButton1Icon;
        mButton2Text = button2Text;
        mResButton2Icon = resButton2Icon;
        mHideDelay = hideDelay;
        mCountdownEnabled = countdownEnabled;
        mData = data;
    }
    
    protected BaseMessage(Parcel p)
    {
        mMessage = p.readString();
        mButton1Text = p.readString();
        mResButton1Icon = p.readInt();
        mButton2Text = p.readString();
        mResButton2Icon = p.readInt();
        mHideDelay = p.readInt();
        mCountdownEnabled = p.readInt() == 1 ? true : false;
        mData = p.readParcelable(getClass().getClassLoader());
    }

    public void writeToParcel(Parcel out, int flags)
    {
        out.writeString(mMessage);
        out.writeString(mButton1Text);
        out.writeInt(mResButton1Icon);
        out.writeString(mButton2Text);
        out.writeInt(mResButton2Icon);
        out.writeInt(mHideDelay);
        out.writeInt(mCountdownEnabled ? 1 : 0);
        out.writeParcelable(mData, 0);
    }

    @Override
    public int describeContents()
    {
        return 0;
    }
    
    public BaseMessage setData(Parcelable data)
    {
        mData = data;
        return this;
    }
    
    public BaseMessage setConstant()
    {
        mHideDelay = -1;
        return this;
    }
    
    public BaseMessage setClickListener(OnMessageClickListener listener)
    {
        mClickListener = listener;
        return this;
    }
    
    public void clickButton1()
    {
        if (mClickListener != null)
            mClickListener.onButton1Click(mData);
    }
    
    public void clickButton2()
    {
        if (mClickListener != null)
            mClickListener.onButton2Click(mData);
    }

    public String getMessage()
    {
        return mMessage;
    }

    public String getButton1Text()
    {
        return mButton1Text;
    }

    public int getResButton1Icon()
    {
        return mResButton1Icon;
    }

    public String getButton2Text()
    {
        return mButton2Text;
    }

    public int getResButton2Icon()
    {
        return mResButton2Icon;
    }

    public int getHideDelay()
    {
        return mHideDelay;
    }

    public Parcelable getData()
    {
        return mData;
    }

    public OnMessageClickListener getClickListener()
    {
        return mClickListener;
    }

    public boolean isCountdownEnabled()
    {
        return mCountdownEnabled;
    }
}
