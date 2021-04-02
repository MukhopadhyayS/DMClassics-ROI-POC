/*************************************************************************************************************************************
*
* Creation Date		:
* Copyright		: COPYRIGHT MCKESSON 2009
*			: ALL RIGHTS RESERVED
*
* 			: The copyright to the computer program(s) herein
* 			: is the property of McKesson. The program(s)
* 			: may be used and/or copied only with the written
* 			: permission of McKesson or in accordance with
* 			: the terms and conditions stipulated in the
* 			: agreement/contract under which the program(s)
* 			: have been supplied.
*
* Written by		: OFS
*
* Database		:  Cabinet
*

*
* Data Modifications	: <none>
*
* Updates		:
* 			Name			Date		Purpose
*			---------		-------		-------------
*			OFS		        4/28/2009	Output Test data for Testcases
*
*************************************************************************************************************************************/


truncate table output_listofvalue
go
set identity_insert output_listofvalue on

insert into dbo.Output_ListOfValue ( oslov_seq,   oslov_Created_Dt,oslov_Created_By_Seq,    oslov_Modified_Dt,oslov_Modified_By_Seq,oslov_Record_Version,
	oslov_Organization_Seq,    oslov_parent_seq,oslov_child_Seq,oslov_Name,oslov_Data) values     
	( 1, getdate(),1,getdate(),1,0,1,0,0,'ROI','<eig:sourceTypeDef xmlns:eig="urn:eig.mckesson.com"><eig:type>ROI</eig:type><eig:enabled>true</eig:enabled></eig:sourceTypeDef>')
go
insert into dbo.Output_ListOfValue ( oslov_seq,    oslov_Created_Dt,oslov_Created_By_Seq,    
			oslov_Modified_Dt,oslov_Modified_By_Seq,oslov_Record_Version,oslov_Organization_Seq,    
			oslov_parent_seq,oslov_child_Seq,oslov_Name,oslov_Data) values     
(2, getdate(),1,getdate(),1,0,1,1,0,'ROI','<eig:sourceDef xmlns:eig="urn:eig.mckesson.com"><eig:id>0</eig:id><eig:name>ROI</eig:name><eig:type>ROI</eig:type><eig:description>Source for retrieving ROI documents</eig:description><eig:properties><eig:property><eig:name>protocol</eig:name><eig:value>http</eig:value></eig:property><eig:property><eig:name>server</eig:name><eig:value>eigdev187</eig:value></eig:property><eig:property><eig:name>port</eig:name><eig:value>8080</eig:value></eig:property></eig:properties></eig:sourceDef>')

go

insert into dbo.Output_ListOfValue (  oslov_seq,   oslov_Created_Dt,oslov_Created_By_Seq,    oslov_Modified_Dt,
		oslov_Modified_By_Seq,oslov_Record_Version,oslov_Organization_Seq,    oslov_parent_seq,oslov_child_Seq,oslov_Name,oslov_Data) 
		values     ( 3,getdate(),1,getdate(),1,0,1,0,0,'HPF','<eig:sourceTypeDef xmlns:eig="urn:eig.mckesson.com"><eig:type>MPF</eig:type><eig:enabled>true</eig:enabled></eig:sourceTypeDef>')
go
insert into dbo.Output_ListOfValue ( oslov_seq,    oslov_Created_Dt,oslov_Created_By_Seq,    oslov_Modified_Dt,oslov_Modified_By_Seq,oslov_Record_Version,
			oslov_Organization_Seq,    oslov_parent_seq,oslov_child_Seq,oslov_Name,oslov_Data) values     
			( 4,getdate(),1,getdate(),1,0,1,3,0,'HPF','<eig:sourceDef xmlns:eig="urn:eig.mckesson.com"><eig:id>0</eig:id><eig:name>MPF</eig:name><eig:type>MPF</eig:type><eig:description>Source for retrieving MPF documents</eig:description><eig:properties><eig:property><eig:name>password</eig:name><eig:value>password</eig:value></eig:property><eig:property><eig:name>userName</eig:name><eig:value>username</eig:value></eig:property><eig:property><eig:name>protocol</eig:name><eig:value>http</eig:value></eig:property><eig:property><eig:name>server</eig:name><eig:value>MPFWeb</eig:value></eig:property><eig:property><eig:name>port</eig:name><eig:value>80</eig:value></eig:property></eig:properties></eig:sourceDef>')

go			
insert into dbo.Output_ListOfValue ( oslov_seq,  oslov_Created_Dt,oslov_Created_By_Seq,    oslov_Modified_Dt,oslov_Modified_By_Seq,oslov_Record_Version,
oslov_Organization_Seq,    oslov_parent_seq,oslov_child_Seq,oslov_Name,oslov_Data) values     
( 5, getdate(),1,getdate(),1,0,1,0,0,'WATERMARK_JOB','<eig:jobTransformTypeDef xmlns:eig="urn:eig.mckesson.com">  <eig:type>WATERMARK_JOB</eig:type>  <eig:enabled>true</eig:enabled></eig:jobTransformTypeDef>')
go
insert into dbo.Output_ListOfValue ( oslov_seq, oslov_Created_Dt,oslov_Created_By_Seq,    oslov_Modified_Dt,oslov_Modified_By_Seq,
oslov_Record_Version,oslov_Organization_Seq,    oslov_parent_seq,oslov_child_Seq,oslov_Name,oslov_Data) values     
( 6, getdate(),1,getdate(),1,0,1,5,0,'WATERMARK_JOB','<eig:jobTransformDef xmlns:eig="urn:eig.mckesson.com">  <eig:id>0</eig:id>  <eig:name>WATERMARK_JOB</eig:name>  <eig:type>WATERMARK_JOB</eig:type>  <eig:description>Watermark PDF Transformer</eig:description></eig:jobTransformDef>')

go
insert into dbo.Output_ListOfValue (  oslov_seq ,  oslov_Created_Dt,oslov_Created_By_Seq,    oslov_Modified_Dt,oslov_Modified_By_Seq,oslov_Record_Version,oslov_Organization_Seq,    oslov_parent_seq,oslov_child_Seq,oslov_Name,oslov_Data) values     ( 7, getdate(),1,getdate(),1,0,1,0,0,'PAGE_SEPARATOR_JOB','<eig:jobTransformTypeDef xmlns:eig="urn:eig.mckesson.com">  <eig:type>PAGE_SEPARATOR_JOB</eig:type>  <eig:enabled>true</eig:enabled></eig:jobTransformTypeDef>')
go
insert into dbo.Output_ListOfValue (  oslov_seq,   oslov_Created_Dt,oslov_Created_By_Seq,    oslov_Modified_Dt,oslov_Modified_By_Seq,oslov_Record_Version,oslov_Organization_Seq,    oslov_parent_seq,oslov_child_Seq,oslov_Name,oslov_Data) values     ( 8,getdate(),1,getdate(),1,0,1,7,0,'PAGE_SEPARATOR_JOB','<eig:jobTransformDef xmlns:eig="urn:eig.mckesson.com">  <eig:id>0</eig:id>  <eig:name>PAGE_SEPARATOR_JOB</eig:name>  <eig:type>PAGE_SEPARATOR_JOB</eig:type>  <eig:description>Page separator PDF Transformer</eig:description></eig:jobTransformDef>')
go

insert into dbo.Output_ListOfValue (  oslov_seq ,    oslov_Created_Dt,oslov_Created_By_Seq,    oslov_Modified_Dt,oslov_Modified_By_Seq,oslov_Record_Version,oslov_Organization_Seq,    oslov_parent_seq,oslov_child_Seq,oslov_Name,oslov_Data) values     (9, getdate(),1,getdate(),1,0,1,0,0,'HEADER_FOOTER_JOB','<eig:jobTransformTypeDef xmlns:eig="urn:eig.mckesson.com">
  <eig:type>HEADER_FOOTER_JOB</eig:type>
  <eig:enabled>true</eig:enabled>
</eig:jobTransformTypeDef>')
go
insert into dbo.Output_ListOfValue (  oslov_seq ,    oslov_Created_Dt,oslov_Created_By_Seq,    oslov_Modified_Dt,oslov_Modified_By_Seq,oslov_Record_Version,oslov_Organization_Seq,    oslov_parent_seq,oslov_child_Seq,oslov_Name,oslov_Data) values     (10, getdate(),1,getdate(),1,0,1,9,0,'HEADER_FOOTER_JOB','<eig:jobTransformDef xmlns:eig="urn:eig.mckesson.com">
  <eig:id>0</eig:id>
  <eig:name>HEADER_FOOTER_JOB</eig:name>
  <eig:type>HEADER_FOOTER_JOB</eig:type>
  <eig:description>Header_Footer PDF Transformer</eig:description>
</eig:jobTransformDef>')
go
insert into dbo.Output_ListOfValue ( oslov_seq ,     oslov_Created_Dt,oslov_Created_By_Seq,    oslov_Modified_Dt,oslov_Modified_By_Seq,oslov_Record_Version,oslov_Organization_Seq,    oslov_parent_seq,oslov_child_Seq,oslov_Name,oslov_Data) values     (11, getdate(),1,getdate(),1,0,1,0,0,'WATERMARK_PART','<eig:partTransformTypeDef xmlns:eig="urn:eig.mckesson.com">
  <eig:type>WATERMARK_PART</eig:type>
  <eig:enabled>true</eig:enabled>
</eig:partTransformTypeDef>')
go
insert into dbo.Output_ListOfValue (  oslov_seq ,    oslov_Created_Dt,oslov_Created_By_Seq,    oslov_Modified_Dt,oslov_Modified_By_Seq,oslov_Record_Version,oslov_Organization_Seq,    oslov_parent_seq,oslov_child_Seq,oslov_Name,oslov_Data) values     ( 12,getdate(),1,getdate(),1,0,1,11,0,'WATERMARK_PART','<eig:partTransformDef xmlns:eig="urn:eig.mckesson.com">
  <eig:id>0</eig:id>
  <eig:name>WATERMARK_PART</eig:name>
  <eig:type>WATERMARK_PART</eig:type>
  <eig:description>Watermark PDF Transformer</eig:description>
  <eig:properties>
    <eig:property>
      <eig:name>transformType</eig:name>
      <eig:value>WATERMARK</eig:value>
    </eig:property>
  </eig:properties>
</eig:partTransformDef>')
go
insert into dbo.Output_ListOfValue ( oslov_seq ,     oslov_Created_Dt,oslov_Created_By_Seq,    oslov_Modified_Dt,oslov_Modified_By_Seq,oslov_Record_Version,oslov_Organization_Seq,    oslov_parent_seq,oslov_child_Seq,oslov_Name,oslov_Data) values     (13, getdate(),1,getdate(),1,0,1,0,0,'PAGE_SEPARATOR_PART','<eig:partTransformTypeDef xmlns:eig="urn:eig.mckesson.com">
  <eig:type>PAGE_SEPARATOR_PART</eig:type>
  <eig:enabled>true</eig:enabled>
</eig:partTransformTypeDef>')
go
insert into dbo.Output_ListOfValue (  oslov_seq ,    oslov_Created_Dt,oslov_Created_By_Seq,    oslov_Modified_Dt,oslov_Modified_By_Seq,oslov_Record_Version,oslov_Organization_Seq,    oslov_parent_seq,oslov_child_Seq,oslov_Name,oslov_Data) values     ( 14,getdate(),1,getdate(),1,0,1,13,0,'PAGE_SEPARATOR_PART','<eig:partTransformDef xmlns:eig="urn:eig.mckesson.com">
  <eig:id>0</eig:id>
  <eig:name>PAGE_SEPARATOR_PART</eig:name>
  <eig:type>PAGE_SEPARATOR_PART</eig:type>
  <eig:description>Page separator PDF Transformer</eig:description>
  <eig:properties>
    <eig:property>
      <eig:name>transformType</eig:name>
      <eig:value>PAGE_SEPARATOR</eig:value>
    </eig:property>
  </eig:properties>
</eig:partTransformDef>')
go
insert into dbo.Output_ListOfValue ( oslov_seq ,     oslov_Created_Dt,oslov_Created_By_Seq,    oslov_Modified_Dt,oslov_Modified_By_Seq,oslov_Record_Version,oslov_Organization_Seq,    oslov_parent_seq,oslov_child_Seq,oslov_Name,oslov_Data) values     ( 15,getdate(),1,getdate(),1,0,1,0,0,'HEADER_FOOTER_PART','<eig:partTransformTypeDef xmlns:eig="urn:eig.mckesson.com">
  <eig:type>HEADER_FOOTER_PART</eig:type>
  <eig:enabled>true</eig:enabled>
</eig:partTransformTypeDef>')
go
insert into dbo.Output_ListOfValue (  oslov_seq ,    oslov_Created_Dt,oslov_Created_By_Seq,    oslov_Modified_Dt,oslov_Modified_By_Seq,oslov_Record_Version,oslov_Organization_Seq,    oslov_parent_seq,oslov_child_Seq,oslov_Name,oslov_Data) values     ( 16,getdate(),1,getdate(),1,0,1,15,0,'HEADER_FOOTER_PART','<eig:partTransformDef xmlns:eig="urn:eig.mckesson.com">
  <eig:id>0</eig:id>
  <eig:name>HEADER_FOOTER_PART</eig:name>
  <eig:type>HEADER_FOOTER_PART</eig:type>
  <eig:description>Header Footer PDF Transformer</eig:description>
  <eig:properties>
    <eig:property>
      <eig:name>transformType</eig:name>
      <eig:value>HEADER_FOOTER</eig:value>
    </eig:property>
  </eig:properties>
</eig:partTransformDef>')
go
insert into dbo.Output_ListOfValue ( oslov_seq ,      oslov_Created_Dt,oslov_Created_By_Seq,    oslov_Modified_Dt,oslov_Modified_By_Seq,oslov_Record_Version,oslov_Organization_Seq,    oslov_parent_seq,oslov_child_Seq,oslov_Name,oslov_Data) values     ( 17, getdate(),1,getdate(),1,0,1,0,0,'FILE','<eig:destTypeDef xmlns:eig="urn:eig.mckesson.com">
  <eig:type>FILE</eig:type>
  <eig:enabled>true</eig:enabled>
</eig:destTypeDef>')
go
insert into dbo.Output_ListOfValue ( oslov_seq ,       oslov_Created_Dt,oslov_Created_By_Seq,    oslov_Modified_Dt,oslov_Modified_By_Seq,oslov_Record_Version,oslov_Organization_Seq,    oslov_parent_seq,oslov_child_Seq,oslov_Name,oslov_Data) values     ( 18, getdate(),1,getdate(),1,0,1,0,0,'FAX','<eig:destTypeDef xmlns:eig="urn:eig.mckesson.com">
  <eig:type>FAX</eig:type>
  <eig:enabled>true</eig:enabled>
</eig:destTypeDef>')
go
insert into dbo.Output_ListOfValue ( oslov_seq ,      oslov_Created_Dt,oslov_Created_By_Seq,    oslov_Modified_Dt,oslov_Modified_By_Seq,oslov_Record_Version,oslov_Organization_Seq,    oslov_parent_seq,oslov_child_Seq,oslov_Name,oslov_Data) values     ( 19, getdate(),1,getdate(),1,0,1,0,0,'PRINT','<eig:destTypeDef xmlns:eig="urn:eig.mckesson.com">
  <eig:type>PRINT</eig:type>
  <eig:enabled>true</eig:enabled>
</eig:destTypeDef>')
go

insert into dbo.Output_ListOfValue ( oslov_seq ,      oslov_Created_Dt,oslov_Created_By_Seq,    oslov_Modified_Dt,oslov_Modified_By_Seq,oslov_Record_Version,oslov_Organization_Seq,    oslov_parent_seq,oslov_child_Seq,oslov_Name,oslov_Data) values     ( 20, getdate(),1,getdate(),1,0,1,19,0,'Test Print','<eig:destInfo xmlns:eig="urn:eig.mckesson.com">
<eig:id>0</eig:id><eig:name>Test Print</eig:name>
<eig:type>PRINT</eig:type><eig:description />
<eig:properties><eig:property><eig:name>printerName</eig:name>
<eig:value>PDFCreator</eig:value></eig:property>
<eig:property><eig:name>color</eig:name><eig:value>false</eig:value></eig:property><eig:property><eig:name>duplex</eig:name><eig:value>true</eig:value></eig:property><eig:property><eig:name>scaling</eig:name><eig:value>100</eig:value></eig:property><eig:property><eig:name>copies</eig:name><eig:value>1</eig:value></eig:property><eig:property><eig:name>collate</eig:name><eig:value>true</eig:value></eig:property><eig:property><eig:name>pageOrientation</eig:name><eig:value>Landscape</eig:value></eig:property>
<eig:property><eig:name>pageSeparator</eig:name><eig:value>true</eig:value></eig:property>
</eig:properties><eig:propertyDefs /><eig:jobOptionDefs /></eig:destInfo>')
go

insert into dbo.Output_ListOfValue ( oslov_seq ,      oslov_Created_Dt,oslov_Created_By_Seq,    oslov_Modified_Dt,oslov_Modified_By_Seq,oslov_Record_Version,oslov_Organization_Seq,    oslov_parent_seq,oslov_child_Seq,oslov_Name,oslov_Data) values     ( 21, getdate(),1,getdate(),1,0,1,18,0,'Test Fax','<eig:destInfo xmlns:eig="urn:eig.mckesson.com">
<eig:id>0</eig:id><eig:name>ROI Fax</eig:name>
<eig:type>FAX</eig:type>
</eig:destInfo>')
go

insert into dbo.Output_ListOfValue ( oslov_seq ,      oslov_Created_Dt,oslov_Created_By_Seq,    oslov_Modified_Dt,oslov_Modified_By_Seq,oslov_Record_Version,oslov_Organization_Seq,    oslov_parent_seq,oslov_child_Seq,oslov_Name,oslov_Data) values     ( 22, getdate(),1,getdate(),1,0,1,17,0,'Test File','<eig:destInfo xmlns:eig="urn:eig.mckesson.com">

<eig:id>0</eig:id>
<eig:name>Test File</eig:name>
<eig:type>FILE</eig:type>
<eig:description />
<eig:properties>
<eig:property>
<eig:name>password</eig:name><eig:value />
</eig:property>

<eig:property>
<eig:name>directoryName</eig:name><eig:value>\\server\output</eig:value>
</eig:property>

<eig:property><eig:name>purgeTime</eig:name>
<eig:value>8</eig:value></eig:property>
<eig:property>

<eig:name>dvdSizeMb</eig:name><eig:value>4812</eig:value></eig:property>
<eig:property><eig:name>cdSizeMb</eig:name><eig:value>703</eig:value>
</eig:property><eig:property><eig:name>passwordRequired</eig:name>
<eig:value>true</eig:value></eig:property><eig:property>
<eig:name>mediaType</eig:name><eig:value>CD</eig:value></eig:property>
</eig:properties><eig:propertyDefs /><eig:jobOptionDefs /></eig:destInfo>')
go

insert into dbo.Output_ListOfValue ( oslov_seq ,oslov_Created_Dt,oslov_Created_By_Seq,oslov_Modified_Dt,oslov_Modified_By_Seq,oslov_Record_Version,oslov_Organization_Seq,oslov_parent_seq,oslov_child_Seq,oslov_Name,oslov_Data) values( 23, getdate(),1,getdate(),1,1,1,0,0,'OutputConfiguration','<property>
<name>DisplayPatientName</name><value>Yes</value></property><property><name>SaveCompletedJobsInHrs</name><value>4</value></property><property><name>DisplayEncounter</name><value>No</value></property><property><name>SystemRetryAttemptsInSec</name><value>60</value></property><property><name>MRNLocation</name><value>Header</value></property><property><name>DisplayHeader</name><value>Yes</value></property><property><name>DisplayWatermark</name><value>Yes</value></property><property><name>DisplayMRN</name><value>Yes</value></property><property><name>DisplayRelativePageNumber</name><value>No</value></property><property><name>MRNMasking</name><value>4</value></property><property><name>EncounterLocation</name><value>Header</value></property><property><name>RelativePageNumberLocation</name><value>Header</value></property><property><name>DisplayFooter</name><value>Yes</value></property><property><name>AbsolutePageNumberLocation</name><value>Header</value></property><property><name>PatientNameLocation</name><value>Header</value></property><property><name>WaterMarkText</name><value>Test</value></property><property><name>DisplayAbsolutePageNumber</name><value>Yes</value></property>')
go

set identity_insert output_listofvalue off
--ROI_RequestMain
set identity_insert ROI_RequestMain on
go

insert into ROI_RequestMain
	(
	ROI_RequestMain_Seq
	,[RequestMainXML]
	)
Values
	(
	-1
	,'<request Authdoc="Coldform  00000100001">
		<status>Auth-Received</status>
		<receipt-date>04/08/2009</receipt-date>
		<status-reason>Auth Received</status-reason>
		<request-reason>Authorization Request Received</request-reason>
		<request-reason-attribute>TPO</request-reason-attribute>
		<balance-due>0.00</balance-due>
		<status-changed>04/08/2009</status-changed>
	  </request>'
	)

SET NOCOUNT off

set identity_insert ROI_RequestMain off
go
