package com.pharmacy.pharmacy;

import com.backendless.push.BackendlessBroadcastReceiver;

/**
 * Created by Mohamed Hesham on 2017-06-12.
 */

public class MyPushReceiver extends BackendlessBroadcastReceiver
{
    @Override
    public Class getServiceClass()
{
    return MyPushService.class;
}
}