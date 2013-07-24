package com.android.michaelflisar.messagebar.messages;


public class Timer2ButtonMessage extends BaseMessage
{
    private static final int DEFAULT_TIMER_BUTTON_HIDE_DELAY = 10000;
    
    public Timer2ButtonMessage(String message, String button1Text, String button2Text)
    {
        super(message, button1Text, -1, button2Text, -1, DEFAULT_TIMER_BUTTON_HIDE_DELAY, true, null);
    }
    
    public Timer2ButtonMessage(String message, String button1Text, String button2Text, int resButton1Icon, int resButton2Icon)
    {
        super(message, button1Text, resButton1Icon, button2Text, resButton2Icon, DEFAULT_TIMER_BUTTON_HIDE_DELAY, true, null);
    }
    
    public Timer2ButtonMessage setHideDelay(int delayInSeconds)
    {
        mHideDelay = delayInSeconds * 1000;
        return this;
    }
}
