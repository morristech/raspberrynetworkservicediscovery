/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.tunjid.raspberrynetworkservicediscovery;

import android.content.Context;
import android.net.nsd.NsdManager;
import android.net.nsd.NsdServiceInfo;
import android.support.annotation.NonNull;

/**
 * Utility class wrapping {@link NsdManager} methods
 */
public class NsdHelper {

    private static final String SERVICE_TYPE = "_http._tcp.";
    private static final String SERVICE_NAME = "Raspberry NSD";

    private NsdManager nsdManager;

    private NsdManager.DiscoveryListener mDiscoveryListener;
    private NsdManager.RegistrationListener mRegistrationListener;

    public NsdHelper(Context context) {
        nsdManager = (NsdManager) context.getSystemService(Context.NSD_SERVICE);
    }

    public void initializeDiscoveryListener(NsdManager.DiscoveryListener listener) {
        mDiscoveryListener = listener;
    }

    public void initializeRegistrationListener(@NonNull NsdManager.RegistrationListener listener) {
        mRegistrationListener = listener;
    }

    public void registerService(int port) {
        NsdServiceInfo serviceInfo = new NsdServiceInfo();
        serviceInfo.setPort(port);
        serviceInfo.setServiceName(SERVICE_NAME);
        serviceInfo.setServiceType(SERVICE_TYPE);

        nsdManager.registerService(serviceInfo, NsdManager.PROTOCOL_DNS_SD, mRegistrationListener);
    }

    public void discoverServices() {
        nsdManager.discoverServices(SERVICE_TYPE, NsdManager.PROTOCOL_DNS_SD, mDiscoveryListener);
    }

    public NsdManager getNsdManager() {
        return nsdManager;
    }

    public void tearDown() {

        try { // May or may not be registered, throws IllegalArgumentException if it isn't
            nsdManager.unregisterService(mRegistrationListener);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        try { // May or may not be looking for services, throws IllegalArgumentException if it isn't
            nsdManager.stopServiceDiscovery(mDiscoveryListener);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }
}
