package com.android.michaelflisar.messagebar.messages;


public class TextMessage extends BaseMessage
{
    public TextMessage(String message)
    {
        super(message, null, -1, null, -1, DEFAULT_HIDE_DELAY, false, null);
    }
    
    public TextMessage(String message, String button1Text)
    {
        super(message, button1Text, -1, null, -1, DEFAULT_HIDE_DELAY, false, null);
    }
    
    public TextMessage(String message, String button1Text, int resButton1Icon)
    {
        super(message, button1Text, resButton1Icon, null, -1, DEFAULT_HIDE_DELAY, false, null);
    }
    
    public TextMessage(String message, String button1Text, String button2Text)
    {
        super(message, button1Text, -1, button2Text, -1, DEFAULT_HIDE_DELAY, false, null);
    }
    
    public TextMessage(String message, String button1Text, String button2Text, int resButton1Icon, int resButton2Icon)
    {
        super(message, button1Text, resButton1Icon, button2Text, resButton2Icon, DEFAULT_HIDE_DELAY, false, null);
    }
}
