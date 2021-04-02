package com.mckesson.eig.roi.billing.model;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class ReleaseHistoryList implements Serializable {

    List<ReleaseHistoryPatient> releasedPatients;
    List<ReleaseHistoryItem> history;

    public ReleaseHistoryList() {}

    public ReleaseHistoryList(List<ReleaseHistoryPatient> releasedPatients,
                              List<ReleaseHistoryItem> history) {

        this.releasedPatients = releasedPatients;
        this.history = history;
    }

    public List<ReleaseHistoryPatient> getReleasedPatients() {
        return releasedPatients;
    }

    public void setReleasedPatients(List<ReleaseHistoryPatient> releasedPatients) {
        this.releasedPatients = releasedPatients;
    }

    public List<ReleaseHistoryItem> getHistory() {
        return history;
    }

    public void setHistory(List<ReleaseHistoryItem> history) {
        this.history = history;
    }

}
