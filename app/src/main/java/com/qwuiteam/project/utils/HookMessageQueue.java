package com.qwuiteam.project.utils;

import android.os.Handler;
import android.os.Message;
import android.os.MessageQueue;
import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class HookMessageQueue {

    private static Handler mHandler;
    private static MessageQueue mQueue;

    public static Handler hookActivityThreadHandler() {//获取主线程Handler
        if (null == mHandler) {
            try {
                Class<?> activityThreadClass = Class.forName("android.app.ActivityThread");
                Method currentActivityThreadMethod = activityThreadClass.getDeclaredMethod("currentActivityThread");
                Object activityThread = currentActivityThreadMethod.invoke(null);
                Field mH = activityThreadClass.getDeclaredField("mH");
                mH.setAccessible(true);
                mHandler = (Handler) mH.get(activityThread);
                return mHandler;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        } else {
            return mHandler;
        }
    }
    public static MessageQueue hookMainMessageQueue(Handler handler) {//通过Handler获取主线程messageQuque
        if (null == handler) {
            return null;
        }
        if (null == mQueue) {
            try {
                Class<?> handlerClass = Class.forName("android.os.Handler");
                Field mQueueFiele = handlerClass.getDeclaredField("mQueue");
                mQueueFiele.setAccessible(true);
                mQueue = (MessageQueue) mQueueFiele.get(handler);
                return mQueue;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        } else {
            return mQueue;
        }
    }

    public static void PrintAllMessage(){
        MessageQueue queue = hookMainMessageQueue(hookActivityThreadHandler());
        if(queue==null)return ;

        Message message=null;
        if(hookMainMessageQueue(hookActivityThreadHandler())!=null){
            try{
                Class<?>messageQueueClass=Class.forName("android.os.MessageQueue");
                Field mMessageField=messageQueueClass.getDeclaredField("mMessages");
                mMessageField.setAccessible(true);
                message=(Message) mMessageField.get(queue);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        synchronized (queue){
            Message p =message;
            while(p!=null){
                Log.i("messageInfo",p.toString());
                p=getNextMessage(p);
            }
        }
    }
    private static Message getNextMessage(Message msg){
        Message nextMessage;
        try {
            Class<?> messageClass = Class.forName("android.os.Message");
            Field mNextField = messageClass.getDeclaredField("next");
            mNextField.setAccessible(true);
            nextMessage = (Message) mNextField.get(msg);
            return nextMessage;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
