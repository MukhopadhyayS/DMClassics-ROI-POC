package com.mckesson.eig.roi.base.model;

import java.util.List;

import com.mckesson.eig.roi.base.model.ShutdownHook;
import com.mckesson.eig.roi.base.model.ShutdownThread;

public class ShutdownBean {

    public ShutdownBean(List<ShutdownHook> hooks) {
          Runtime.getRuntime().addShutdownHook(
                  new ShutdownThread(hooks));
    }
}
