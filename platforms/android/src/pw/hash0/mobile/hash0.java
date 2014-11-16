/*
       Licensed to the Apache Software Foundation (ASF) under one
       or more contributor license agreements.  See the NOTICE file
       distributed with this work for additional information
       regarding copyright ownership.  The ASF licenses this file
       to you under the Apache License, Version 2.0 (the
       "License"); you may not use this file except in compliance
       with the License.  You may obtain a copy of the License at

         http://www.apache.org/licenses/LICENSE-2.0

       Unless required by applicable law or agreed to in writing,
       software distributed under the License is distributed on an
       "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
       KIND, either express or implied.  See the License for the
       specific language governing permissions and limitations
       under the License.
 */

package pw.hash0.mobile;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import org.apache.cordova.*;

public class hash0 extends CordovaActivity
{
    private static long LOCK_INTERVAL = 1000 * 60;
    private long startTime;
    private Handler mLockHandler;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        startTime = System.currentTimeMillis();

        // Set by <content src="index.html" /> in config.xml
        loadUrl(launchUrl);
    }

    @Override
    public void onResume() {
        super.onResume();

        long endTime = System.currentTimeMillis();
        long diff = endTime - startTime;

        Log.d("hash0", "diff: " + diff);

        if (diff > LOCK_INTERVAL) {
            loadUrl(launchUrl);
        }
        else {
            Runnable lockRunnable = new Runnable() {
                @Override
                public void run() {
                    Log.d("hash0", "lock it");
                    loadUrl(launchUrl);
                }
            };

            // Schedule to automatically lock after 60 seconds
            mLockHandler = new Handler();
            mLockHandler.postDelayed(lockRunnable, LOCK_INTERVAL - diff);
        }
    }
}
