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
using System.Collections;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Globalization;

using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.Controller;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Web_References.ROIAdminWS;

namespace McK.EIG.ROI.Client.Admin.Controller
{
    public partial class ROIAdminController
   {
       
       #region Methods

       /// <summary>
       /// This method will create a Configure Notes.
       /// </summary>
       /// <param name="configureNotes"></param>
       /// <returns></returns>
       public long CreateConfigureNotes(NotesDetails notesDetails)
       {
           ROIAdminValidator validator = new ROIAdminValidator();
           if (!validator.ValidateCreate(notesDetails))
           {
               throw validator.ClientException;
           }

           Note serverNotes = MapModel(notesDetails);
           object[] requestParams = new object[] { serverNotes };
           object response = ROIHelper.Invoke(roiAdminService, "createNote", requestParams);
           long configureNotesId = Convert.ToInt64(response, System.Threading.Thread.CurrentThread.CurrentCulture);

           return configureNotesId;
       }

       /// <summary>
       /// This method will update the existing ConfigureNotes.
       /// </summary>
       /// <param name="configureNotes"></param>
       /// <returns></returns>
       public NotesDetails UpdateConfigureNotes(NotesDetails notesDetails)
       {
           ROIAdminValidator validator = new ROIAdminValidator();
           if (!validator.ValidateUpdate(notesDetails))
           {
               throw validator.ClientException;
           }

           Note serverNote = MapModel(notesDetails);
           object[] requestParams = new object[] { serverNote };
           ROIHelper.Invoke(roiAdminService, "updateNote", requestParams);
           

           NotesDetails clientNoteDetails = MapModel((Note)requestParams[0]);

           return clientNoteDetails;
       }

       /// <summary>
       /// Deletes the specified note.
       /// </summary>
       /// <param name="noteId"></param>
       public void DeleteNote(long noteId)
       {
           object[] requestParams = new object[] { noteId };
           ROIHelper.Invoke(roiAdminService, "deleteNote", requestParams);
       }

       /// <summary>
       /// Returns a list of configurenotes.
       /// </summary>       
       /// <returns>List of Notes</returns>
       public Collection<NotesDetails> RetrieveAllConfigureNotes()
       {
           object response = ROIHelper.Invoke(roiAdminService, "retrieveAllNotes", new object[0]);
           Collection<NotesDetails> notes= MapModel((Note[])response);
           return notes;
       }


       /// <summary>
       /// Returns notes details  for the given notes id.
       /// </summary>
       /// <param name="noteId"></param>
       /// <returns></returns>
       public NotesDetails GetNote(long noteId)
       {
           object[] requestParams = new object[] { noteId };
           object response = ROIHelper.Invoke(roiAdminService, "retrieveNote", requestParams);
           NotesDetails noteDetails = MapModel((Note)response);
           return noteDetails;
       }

       #endregion

       #region Model Mapping

       /// <summary>
       /// Convert server notes to client notes.
       /// </summary>
       /// <param name="serverNote"></param>
       /// <returns></returns>
       private static NotesDetails MapModel(Note serverNote)
       {
           NotesDetails clientNotes = new NotesDetails();

           clientNotes.Id   = serverNote.noteId; 
           clientNotes.Name = serverNote.name;
           clientNotes.DisplayText     = serverNote.description;
           clientNotes.RecordVersionId = serverNote.recordVersion;

           return clientNotes;
       }

       /// <summary>
       /// Convert client notes to server notes.
       /// </summary>
       /// <param name="clientNote"></param>
       /// <returns></returns>
       private static  Note MapModel(NotesDetails clientNote)
       {
           Note serverNote = new Note();

           serverNote.noteId = clientNote.Id;
           serverNote.name  = clientNote.Name;
           serverNote.description = clientNote.DisplayText;
           serverNote.recordVersion = clientNote.RecordVersionId;

           return serverNote;
       }

       /// <summary>
       /// Convert server notes list to client configure notes.
       /// </summary>
       /// <param name="serverNotes"></param>
       /// <returns>List of configure notes</returns>
       public static Collection<NotesDetails> MapModel(Note[] serverNotes)
       {
           if (serverNotes == null)
           {
               throw new ROIException(ROIErrorCodes.ArgumentIsNull);
           }

           Collection<NotesDetails> clientNotes = new Collection<NotesDetails>();
           foreach (Note serverNote in serverNotes)
           {
               clientNotes.Add(MapModel(serverNote));
           }

           return clientNotes;
       }

       #endregion
   }
}
