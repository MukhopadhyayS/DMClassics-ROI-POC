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
using System.Text;
using System.ComponentModel;

using McK.EIG.ROI.Client.Base.View;
using McK.EIG.Common.Utility.View;

namespace McK.EIG.ROI.Client.Admin.View.Configuration.ExternalSources
{
    public class ExternalSourcesODP : ROIBasePane, ITransientDataApprover
   {
       static ExternalSourcesTabUI externalsourceslistUI;
       private bool isDirty;
       private EventHandler DataDirty;
       private EventHandler dirtyDataHandler;

       internal void OnDataDirty(object sender, EventArgs e)
       {
           if (DataDirty != null)
           {
               DataDirty(sender, e);
           }
       }

       /// <summary>
       /// Event subscriptions.
       /// </summary>
       protected override void Subscribe()
       {
           TransientDataValidator.AddApprover(this);
           dirtyDataHandler = new EventHandler(MarkDirty);
           DataDirty += dirtyDataHandler;
       }

       /// <summary>
       /// Event Unsubscriptions.
       /// </summary>
       protected override void Unsubscribe()
       {
           TransientDataValidator.RemoveApprover(this);
           DataDirty -= dirtyDataHandler;
       }

       private void MarkDirty(object sender, EventArgs e)
       {
           isDirty = true;
           externalsourceslistUI.EnableSaveButton(externalsourceslistUI.EnableSave);
           externalsourceslistUI.EnableUndoButton(true);

           AdminEvents.OnODPDataChange(sender, e);
       }

       #region ITransientDataApprover Members
       /// <summary>
       /// Check wether the data is modified and display the
       /// confirm data loss dialog box wether for further processing
       /// </summary>
       /// <param name="ae">Event raised in the application</param>
       /// <returns>Return true if user click OK button in dialog box.</returns>
       public bool Approve(ApplicationEventArgs ae)
       {
           if (!IsDirty)
           {
               return true;
           }

           TransientResult result = (TransientResult)ROIViewUtility.DoSaveAndProceed(Context);

           if (result == TransientResult.SaveAndProceed)
           {
               IsDirty = false;
               if (!((ExternalSourcesTabUI)View).Save())
               {
                   IsDirty = true;
               }
               else
               {
                   return true;
               }
           }
           else if (result == TransientResult.Proceed)
           {
               ((ExternalSourcesTabUI)this.View).CancelSelection();
               IsDirty = false;
               return true;
           }

           return false;
       }

       #endregion

       

       /// <summary>
       /// Sets true if dirty data available.
       /// </summary>
       public bool IsDirty
       {
           get { return isDirty; }
           set
           {
               isDirty = value;
               if (!isDirty)
               {
                   EnableEvents();
               }
           }
       }

       public void EnableEvents()
       {
           externalsourceslistUI.EnableEvents();
       }

      /// <summary>
      /// Initilize the view of ExternalSourcesODPUI.
      /// </summary>
      protected override void InitView()
      {
          externalsourceslistUI = new ExternalSourcesTabUI();
      }

      /// <summary>
      ///  Returns the view of ExternalSourcesODP.
      /// </summary>
      public override Component View
      {
          get { return externalsourceslistUI; }
      }       
    }
}
