package com.mckesson.eig.roi.base.model;

import java.util.List;

import com.mckesson.eig.roi.base.model.ShutdownHook;

public class ShutdownThread extends Thread {
    private List<ShutdownHook> _hooks;

    public ShutdownThread(List<ShutdownHook> hooks) {
        super();
        _hooks = hooks;
    }

    public void run() {
        for(ShutdownHook hook : _hooks) {
            hook.shutdown();
        }
    }
}
