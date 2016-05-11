#region Copyright © 2010 McKesson Corporation and/or one of its subsidiaries. All Rights Reserved.
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
#endregion
using System;
using System.Collections.Generic;
using System.Text;

namespace McK.EIG.ROI.Client.Base.View.Common.Tree
{
	public enum DrawSelectionMode
	{
		None, Active, Inactive, FullRowSelect
	}

	public enum TreeSelectionMode
	{
		Single, Multiple, MultipleSameParent
	}

	public enum NodePosition
	{
		Inside, Before, After
	}

	public enum VerticalAlignment
	{
		Top, Bottom, Center
	}

	public enum IncrementalSearchMode
	{
		None, Standard, Continuous
	}

	[Flags]
    public enum GridlineStyles
    {
		None = 0, 
		Horizontal = 1, 
		Vertical = 2, 
		HorizontalAndVertical = 3
    }
}
