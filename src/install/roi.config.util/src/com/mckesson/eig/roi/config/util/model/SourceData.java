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

package com.mckesson.eig.roi.config.util.model;

import java.io.Serializable;


/**
 *
 * @author OFS
 * @date   Mar 3, 2009
 * @since  HPF 13.1 [ROI]; Mar 3, 2009
 */
public class SourceData
implements Serializable {

    private String _server;
    private String _protocol;
    private String _port;
    private String _userName;
    private String _password;

    public String getServer() { return _server; }
    public void setServer(String server) { _server = server; }

    public String getProtocol() { return _protocol; }
    public void setProtocol(String protocol) { _protocol = protocol; }

    public String getPort() { return _port; }
    public void setPort(String port) { _port = port; }

    public String getUserName() { return _userName; }
    public void setUserName(String name) { _userName = name; }

    public String getPassword() { return _password; }
    public void setPassword(String password) { _password = password; }

}
