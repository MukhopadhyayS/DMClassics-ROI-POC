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
using System.ComponentModel;
using System.Drawing;
using System.Collections.Generic;

//NUnit
using NUnit.Framework;

//McK
using McK.EIG.ROI.Client.Patient.Model;
using McK.EIG.ROI.Client.Base.Model;
using System.Collections.ObjectModel;
using McK.EIG.ROI.Client.Admin.Model;


namespace McK.EIG.ROI.Client.Test.Patient.Model
{
    /// <summary>
    /// Test class for EncounterDetails
    /// </summary>
   [TestFixture]
   public class TestEncounterDetails
   {
       private EncounterDetails encounterDetails;

       [SetUp]
       public void Initialize()
       {
           encounterDetails = new EncounterDetails("123", "IP", "OP", "AWS", "10/10/2009", "11/10/2009", true, true, true, "clinic", "desposition", "class", "100", true);
       }

       [TearDown]
       public void Dispose()
       {
           encounterDetails = null;
       }

       /// <summary>
       /// Test method for Encounter ID.
       /// </summary>
       [Test]
       public void Test01EncounterId()
       {
           string inputId = "121";
           encounterDetails.EncounterId = inputId;
           string outputId = encounterDetails.EncounterId;
           Assert.AreEqual(inputId, outputId);
           Assert.AreEqual(inputId, encounterDetails.Name);
           Assert.IsNotNull(encounterDetails.Key);
       }

       /// <summary>
       /// Test method for facility.
       /// </summary>
       [Test]
       public void Test02Facility()
       {
           string inputFacility = "AWD";
           encounterDetails.Facility = inputFacility;
           string outputFacility = encounterDetails.Facility;
           Assert.AreEqual(inputFacility, outputFacility);
       }

       /// <summary>
       /// Test method for PatientType.
       /// </summary>
       [Test]
       public void Test03PatientType()
       {
           string inputPatientType = "AWD";
           encounterDetails.PatientType = inputPatientType;
           string outputPatientType = encounterDetails.PatientType;
           Assert.AreEqual(inputPatientType, outputPatientType);
       }

       /// <summary>
       /// Test method for PatientStatus.
       /// </summary>
       [Test]
       public void Test04PatientService()
       {
           string inputPatientStatus = "AWD";
           encounterDetails.PatientService = inputPatientStatus;
           string outputPatientStatus = encounterDetails.PatientService;
           Assert.AreEqual(inputPatientStatus, outputPatientStatus);
       }


       /// <summary>
       /// Test method for DateOfService.
       /// </summary>
       [Test]
       public void Test05AdmitDate()
       {
           DateTime inputDateOfService = DateTime.Now;
           encounterDetails.AdmitDate = inputDateOfService;
           DateTime outputDateOfService = encounterDetails.AdmitDate.Value;
           Assert.AreEqual(inputDateOfService, outputDateOfService);
       }
       
       /// <summary>
       /// Test method for IsDeficiency.
       /// </summary>
       [Test]
       public void Test06HasDeficiency()
       {
           bool inputvalue = true;
           encounterDetails.HasDeficiency = inputvalue;
           bool outputvalue = encounterDetails.HasDeficiency;
           Assert.AreEqual(inputvalue, outputvalue);
       }

       /// <summary>
       /// Test method for IsDeficiency.
       /// </summary>
       [Test]
       public void Test07IsVip()
       {
           bool inputIsVip = true;
           encounterDetails.IsVip = inputIsVip;
           bool outputIsVip = encounterDetails.IsVip;
           Assert.AreEqual(inputIsVip, outputIsVip);
       }

       /// <summary>
       /// Test method for IsDeficiency.
       /// </summary>
       [Test]
       public void Test08IsLocked()
       {
           bool inputIsLocked = true;
           encounterDetails.IsLocked = inputIsLocked;
           bool outputIsLocked = encounterDetails.IsLocked;
           Assert.AreEqual(inputIsLocked, outputIsLocked);
       }

       /// <summary>
       /// Test method for Documents.
       /// </summary>
       [Test]
       public void Test09Document()
       {
           SortedList<DocumentType, DocumentDetails> documents;
           documents = encounterDetails.Documents;
           Assert.IsInstanceOfType(typeof(SortedList<DocumentType, DocumentDetails>), documents);
       }

       /// <summary>
       /// Test method for Documents.
       /// </summary>
       [Test]
       public void Test10HasDisclosureDocumentsFalse()
       {
           DocumentType docType = new DocumentType();
           docType.IsDisclosure = false;
           DocumentDetails docDetails = new DocumentDetails();
           docDetails.DocumentType = docType;
           encounterDetails.Documents.Add(docType, docDetails);
           Assert.AreEqual(false, encounterDetails.HasDisclosureDocuments);
       }

       /// <summary>
       /// Test method for Documents.
       /// </summary>
       [Test]
       public void Test11HasDisclosureDocumentsTrue()
       {
           DocumentType docType = new DocumentType();
           docType.IsDisclosure = true;
           DocumentDetails docDetails = new DocumentDetails();
           docDetails.DocumentType = docType;           
           encounterDetails.Documents.Add(docType,docDetails);
           Assert.AreEqual(true, encounterDetails.HasDisclosureDocuments);
       }

       [Test]
       public void Test12Compare()
       {
           EncounterDetails obj1 = new EncounterDetails();
           obj1.EncounterId = "100";
           obj1.AdmitDate = DateTime.Now;
           EncounterDetails obj2 = new EncounterDetails();
           obj2.EncounterId = "101";
           obj2.AdmitDate = DateTime.Now;
           int result = EncounterDetails.Sorter.Compare(obj1, obj2);
           Assert.AreEqual(1, result);
       }

       [Test]
       public void Test13AddDocument()
       {
           
           DocumentDetails document = new DocumentDetails();
           document.DocumentType = DocumentType.CreateDocType(22, "Cold DataVault", "Clod", DateTime.Now.ToString(), "1", false, 4567);
           document.DocumentId = 32;
           encounterDetails.AddDocument(document);
           Assert.AreEqual(encounterDetails.Documents.Count, 1);
           encounterDetails.AddDocument(document);
           Assert.AreEqual(encounterDetails.Documents.Count, 1);
       }

       [Test]
       public void Test14Icon()
       {
           Assert.IsInstanceOfType(typeof(Image), encounterDetails.Icon);
       }
       
       /// <summary>
       /// Test method for PatientClinic.
       /// </summary>
       [Test]
       public void Test15PatientClinic()
       {
           string inputClinic = "AWD";
           encounterDetails.Clinic = inputClinic;
           string outputClinic = encounterDetails.Clinic;
           Assert.AreEqual(inputClinic, outputClinic);
       }

       /// <summary>
       /// Test method for PatientService.
       /// </summary>
       [Test]
       public void Test16PatientPatientService()
       {
           string inputPatientService = "AWD";
           encounterDetails.PatientService = inputPatientService;
           string outputPatientService = encounterDetails.PatientService;
           Assert.AreEqual(inputPatientService, outputPatientService);
       }

       /// <summary>
       /// Test method for Disposition.
       /// </summary>
       [Test]
       public void Test17PatientDisposition()
       {
           string inputDisposition = "AWD";
           encounterDetails.Disposition = inputDisposition;
           string outputDisposition = encounterDetails.Disposition;
           Assert.AreEqual(inputDisposition, outputDisposition);
       }

       /// <summary>
       /// Test method for FinancialClass.
       /// </summary>
       [Test]
       public void Test15PatientFinancialClass()
       {
           string inputFinancialClass = "AWD";
           encounterDetails.FinancialClass = inputFinancialClass;
           string outputFinancialClass = encounterDetails.FinancialClass;
           Assert.AreEqual(inputFinancialClass, outputFinancialClass);
       }

       /// <summary>
       /// Test method for PatientBalance.
       /// </summary>
       [Test]
       public void Test15PatientBalance()
       {
           string inputBalance = "AWD";
           encounterDetails.Balance = inputBalance;
           string outputBalance = encounterDetails.Balance;
           Assert.AreEqual(inputBalance, outputBalance);
       }


       /// <summary>
       /// Test method for PatientBalance.
       /// </summary>
       [Test]
       public void Test16CustomFields()
       {
           encounterDetails.CustomFields.Add("test", "test");
           Assert.AreEqual(encounterDetails.CustomFields.Count, 1);
       }

       /// <summary>
       /// Test method for Discharge Date.
       /// </summary>
       [Test]
       public void Test05DischargeDate()
       {
           DateTime dischargeDate = DateTime.Now;
           encounterDetails.DischargeDate = dischargeDate;
           Assert.AreEqual(dischargeDate, encounterDetails.DischargeDate);
       }

       /// <summary>
       /// Test method for Key
       /// </summary>
       [Test]
       public void Test18Key()
       {
           EncounterDetails encounterDetails = new EncounterDetails();
           encounterDetails.EncounterId = "Enc1";
           encounterDetails.AdmitDate = null;                                 
           Assert.IsNotNull(encounterDetails.Key);
       }

       /// <summary>
       /// Test method for IsSelfPay.
       /// </summary>
       [Test]
       public void Test19IsSelfPay()
       {
           bool inputIsSelfPay = true;
           encounterDetails.IsSelfPay = inputIsSelfPay;
           bool outputIsSelfPay = encounterDetails.IsSelfPay;
           Assert.AreEqual(inputIsSelfPay, outputIsSelfPay);
       }

       /// <summary>
       /// Test method for Encounter ID.
       /// </summary>
       [Test]
       public void Test20EncounterIdWithSelfPay()
       {
           string inputId = "121";
           encounterDetails.SelfPayEncounterID = inputId;
           string outputId = encounterDetails.SelfPayEncounterID;
           Assert.AreEqual(inputId, outputId);
           Assert.AreEqual(inputId, encounterDetails.Name);
           Assert.IsNotNull(encounterDetails.Key);
       }

       /// <summary>
       /// Test method for Encounter ID.
       /// </summary>
       [Test]
       public void Test21EncounterIdWithSelfPay()
       {
           EncounterDetails encounterDetails = new EncounterDetails();
           encounterDetails.IsSelfPay = false;
           encounterDetails.EncounterId = "Fralick5001";
           encounterDetails.SelfPayEncounterID = "Fralick4001";
           string encounter = encounterDetails.Name;
           Assert.AreEqual(encounter, encounterDetails.EncounterId);
       }
   }
}
