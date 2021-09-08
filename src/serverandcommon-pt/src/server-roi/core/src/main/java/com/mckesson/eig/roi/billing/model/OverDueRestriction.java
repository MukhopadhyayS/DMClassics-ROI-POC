package com.mckesson.eig.roi.billing.model;

/**
 *
 * @author OFS
 * @date   Sep 13, 2011
 * @since  Sep 13, 2011
 */
public enum OverDueRestriction {

    GREATER("greater"), BETWEEN("between");

    private final String _overdueRestriction;

    private OverDueRestriction(String overdueRestriction) {
        _overdueRestriction = overdueRestriction;
    }

    @Override
    public String toString() {
        return _overdueRestriction;
    }
}
