
package com.android.michaelflisar.messagebar;

import java.util.LinkedList;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Parcelable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.michaelflisar.messagebar.messages.BaseMessage;

public class MessageBar
{
    private static final int ANIMATION_DURATION = 600;

    private LinkedList<BaseMessage> mMessages = new LinkedList<BaseMessage>();
    private ViewGroup mContainer = null;
    private TextView mTextView;
    private TextView mButton1;
    private TextView mButton2;

    private CountDownTimer mTimer = null;
    private Handler mHandler;
    private AlphaAnimation mFadeInAnimation;
    private AlphaAnimation mFadeOutAnimation;

    private BaseMessage mCurrentMessage = null;

    private final Runnable mHideRunnable = new Runnable()
    {
        @Override
        public void run()
        {
            if (mCurrentMessage != null)
                mContainer.startAnimation(mFadeOutAnimation);
        }
    };

    public MessageBar(Activity activity, boolean checkForRelativeContainer)
    {
        ViewGroup container = (ViewGroup) activity.findViewById(android.R.id.content);
        init(container, checkForRelativeContainer, -1);
    }

    public MessageBar(Activity activity, boolean checkForRelativeContainer, int resMessageBar)
    {
        ViewGroup container = (ViewGroup) activity.findViewById(android.R.id.content);
        init(container, checkForRelativeContainer, resMessageBar);
    }

    public MessageBar(View container, boolean checkContainer)
    {
        init(container, checkContainer, -1);
    }

    public MessageBar(View container, boolean checkContainer, int resMessageBar)
    {
        init(container, checkContainer, resMessageBar);
    }

    private void init(View parent, boolean checkContainer, int resMessageBar)
    {
        if (checkContainer)
        {
            if (!(parent instanceof FrameLayout) &&
                    !(parent instanceof RelativeLayout))
            {
                // wrap the container in a frame layout
                FrameLayout frameLayout = new FrameLayout(parent.getContext());
                frameLayout.setLayoutParams(parent.getLayoutParams());

                ViewGroup parentOfParent = ((ViewGroup) parent.getParent());
                int index = parentOfParent.indexOfChild(parent);

                parentOfParent.removeView(parent);
                frameLayout.addView(parent);
                parentOfParent.addView(frameLayout, index);

                parent = frameLayout;
            }
        }
        else if (!(parent instanceof ViewGroup))
            throw new RuntimeException(getClass() + ": parent view has to be a ViewGroup!");

        if ((mContainer = (ViewGroup) parent.findViewById(R.id.mbContainer)) == null)
        {
            int res = resMessageBar;
            if (res == -1)
            {
                if (parent instanceof FrameLayout)
                    res = R.layout.messagebar_frame_layout;
                else
                    res = R.layout.messagebar_relative_layout;
            }
            LayoutInflater.from(parent.getContext()).inflate(res, (ViewGroup) parent);
            mContainer = (ViewGroup) parent.findViewById(R.id.mbContainer);
        }

        mContainer.setVisibility(View.GONE);

        mTextView = (TextView) mContainer.findViewById(R.id.mbMessage);
        mButton1 = (TextView) mContainer.findViewById(R.id.mbButton1);
        mButton2 = (TextView) mContainer.findViewById(R.id.mbButton2);

        mFadeInAnimation = new AlphaAnimation(0.0f, 1.0f);
        mFadeOutAnimation = new AlphaAnimation(1.0f, 0.0f);
        mFadeOutAnimation.setDuration(ANIMATION_DURATION);
        mFadeOutAnimation.setAnimationListener(new Animation.AnimationListener()
        {
            @Override
            public void onAnimationStart(Animation animation)
            {
            }

            @Override
            public void onAnimationEnd(Animation animation)
            {
                BaseMessage nextMessage = mMessages.poll();
                if (nextMessage != null)
                    show(nextMessage, false);
                else
                {
                    mCurrentMessage = null;
                    mContainer.setVisibility(View.GONE);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation)
            {
            }
        });

        mHandler = new Handler();
    }

    public void onRestoreInstanceState(Bundle state)
    {
        BaseMessage currentMessage = state.getParcelable(getClass().getName() + ".currentMessage");
        if (currentMessage != null)
        {
            show(currentMessage, true);
            Parcelable[] messages = state.getParcelableArray(getClass().getName() + ".message");
            for (Parcelable p : messages)
            {
                mMessages.add((BaseMessage) p);
            }
        }
    }

    public Bundle onSaveInstanceState()
    {
        Bundle b = new Bundle();

        b.putParcelable(getClass().getName() + ".currentMessage", mCurrentMessage);

        final int count = mMessages.size();
        final BaseMessage[] messages = new BaseMessage[count];
        int i = 0;
        for (BaseMessage message : mMessages)
        {
            messages[i++] = message;
        }

        b.putParcelableArray(getClass().getName() + ".message", messages);

        return b;
    }

    public void show(BaseMessage message)
    {
        if (isShowing())
            mMessages.add(message);
        else
            show(message, false);
    }

    private void show(final BaseMessage message, boolean immediately)
    {
        mCurrentMessage = message;
        mContainer.setVisibility(View.VISIBLE);

        mTextView.setText(message.getMessage());
        if (message.getButton1Text() != null)
        {
            mButton1.setVisibility(View.VISIBLE);
            mButton1.setText(message.getButton1Text());

            if (message.getResButton1Icon() > 0)
                mButton1.setCompoundDrawablesWithIntrinsicBounds(message.getResButton1Icon(), 0, 0, 0);
            else
                mButton1.setCompoundDrawables(null, null, null, null);

            mButton1.setOnClickListener(new OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    hideCurrentMessage();
                    if (message.getClickListener() != null)
                        message.getClickListener().onButton1Click(message.getData());
                }
            });
        }
        if (message.getButton2Text() != null)
        {
            mButton2.setVisibility(View.VISIBLE);
            mButton2.setText(message.getButton2Text());

            if (message.getResButton2Icon() > 0)
                mButton2.setCompoundDrawablesWithIntrinsicBounds(message.getResButton2Icon(), 0, 0, 0);
            else
                mButton2.setCompoundDrawables(null, null, null, null);

            mButton2.setOnClickListener(new OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    hideCurrentMessage();
                    if (message.getClickListener() != null)
                        message.getClickListener().onButton2Click(message.getData());
                }
            });
        }
        if (mTimer != null)
            mTimer.cancel();
        mTimer = null;
        if (message.isCountdownEnabled() && message.getHideDelay() > 0)
        {
            mTimer = new CountDownTimer(message.getHideDelay(), 1000)
            {
                @Override
                public void onTick(long millisUntilFinished)
                {
                    mTextView.setText(message.getMessage() + " (" + String.valueOf(millisUntilFinished / 1000) + ")");
                }

                @Override
                public void onFinish()
                {
                    mHideRunnable.run();
                    mTextView.setText(message.getMessage() + " (" + String.valueOf(0) + ")");           
                }
            };
        }

        mButton1.setVisibility(message.getButton1Text() != null ? View.VISIBLE : View.GONE);
        mButton2.setVisibility(message.getButton2Text() != null ? View.VISIBLE : View.GONE);
        
        // TODO: this should not be done, when custom message bar resource is used...
        mTextView.setGravity((message.getButton1Text() != null || message.getButton2Text() != null) ? Gravity.LEFT | Gravity.CENTER_VERTICAL : Gravity.CENTER);
        
        if (immediately)
            mFadeInAnimation.setDuration(0);
        else
            mFadeInAnimation.setDuration(ANIMATION_DURATION);
        mContainer.startAnimation(mFadeInAnimation);

        if (mTimer != null)
            mTimer.start();
        else if (message.getHideDelay() >= 0)
            mHandler.postDelayed(mHideRunnable, message.getHideDelay());
    }

    public void hideCurrentMessage()
    {
        if (mTimer != null)
            mTimer.cancel();
        mHandler.removeCallbacksAndMessages(null);
        if (mCurrentMessage != null)
            mHandler.postDelayed(mHideRunnable, 0);
    }

    public void clear()
    {
        if (mTimer != null)
            mTimer.cancel();
        mMessages.clear();
        mHideRunnable.run();
    }

    public boolean isShowing()
    {
        return mCurrentMessage != null;
    }
}
