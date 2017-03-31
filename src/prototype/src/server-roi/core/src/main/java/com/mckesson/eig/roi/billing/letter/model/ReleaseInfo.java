/* 
BEGIN-COPYRIGHT-COMMENT Do not remove or modify this line!

* Copyright © 2010 McKesson Corporation and/or one of its subsidiaries. All Rights Reserved.
* Use of this software and related documentation is governed by a license agreement. 
* This material contains confidential, proprietary and trade secret information of 
* McKesson Information Solutions and is protected under United States
* and international copyright and other intellectual property laws. 
* Use, disclosure, reproduction, modification, distribution, or storage
* in a retrieval system in any form or by any means is prohibited without the 
* prior express written permission of McKesson Information Solutions.

END-COPYRIGHT-COMMENT  Do not remove or modify this line! 
*/

package com.mckesson.eig.roi.billing.letter.model;


/**
 * @author OFS
 * @date   Nov 02, 2009
 * @since  HPF 13.1 [ROI]; Feb 19, 2009
 */
public class ReleaseInfo {

    private String _totPages;
    private String _releaseCost;
    private String _releaseDt;
    private String _previouslyReleasedCost;
    private String _balanceDue;
    private String _totalCost;
    private String _totalPagesReleased;

    public String getTotPages() { return _totPages; }
    public void setTotPages(String pages) { _totPages = pages; }

    public String getReleaseCost() { return _releaseCost; }
    public void setReleaseCost(String cost) { _releaseCost = cost; }

    public String getReleaseDt() { return _releaseDt; }
    public void setReleaseDt(String dt) { _releaseDt = dt; }

    public String getPreviouslyReleasedCost() { return _previouslyReleasedCost; }
    public void setPreviouslyReleasedCost(String releasedCost) {
        _previouslyReleasedCost = releasedCost;
    }

    public String getBalanceDue() { return _balanceDue; }
    public void setBalanceDue(String due) { _balanceDue = due; }

    public String getTotalCost() { return _totalCost; }
    public void setTotalCost(String requestCost) { _totalCost = requestCost; }

    public String getTotalPagesReleased() { return _totalPagesReleased; }
    public void setTotalPagesReleased(String pagesReleased) { _totalPagesReleased = pagesReleased; }
}
