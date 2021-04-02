                 IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[WFE_ROI_ProcessAutomatedRequestDeny]') AND type in (N'P', N'PC'))
DROP PROCEDURE [dbo].[WFE_ROI_ProcessAutomatedRequestDeny]
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

/***************************************************************************************************************
* Stored Procedure: WFE_ROI_ProcessAutomatedRequestDeny
* Creation Date		: 12/16/2009
* Copyright			: COPYRIGHT MCKESSON 2009
*					: ALL RIGHTS RESERVED
*
* 					: The copyright to the computer program(s) herein
* 					: is the property of McKesson. The program(s)
* 					: may be used and/or copied only with the written
* 					: permission of McKesson or in accordance with
* 					: the terms and conditions stipulated in the     
* 					: agreement/contract under which the program(s)
* 					: have been supplied.
*					
* Written by:	RC	
*									
* Purpose:	Create a entry in ROI_RequestMain, Audit_trail and optionally an
*			assignment in the Roi_Log_Queue from sysparms
*				
* Database:	CABINET
*									
* Input Parameters	: 
*	Name				Type		Description
* 	---------			-------		----------------
*	@Assignment_Id		numeric		unique identifier in the assignments table
*	@Encounter			char(20)	identifier for a patient visit
*	@Facility			char(20)	unique identifier for the hospital
*	@Queue_Id			smallint	unique identifier for a queue in Workflow Not used in this script
*	@Queue_Name			char(50))	common name for the queue in Workflow
*	@Debug				bit			optional parameter used to run interactivly
*									
* Output Parameters	: <none>
*									
* Return Status		: 		
*	Name				Value		Description
* 	---------			-------		----------------
*	NA 					0			Indicates a false test
*	NA					1			Indicates a true test	
*									
* Called By			: Workflow Agent
*									
* Calls				: NA
*			
* Data Modifications: Creates record in ROI_RequestMain
*									
* Updates			: 
*	Name				Date		Purpose
*	---------			-------		------------- 						
*	RC 					01/22/2009	Created new stored procedure.						
*	RC					07/22/2009	Added entry into SerachLOV table	
*   RC                  07/27/2009  Added new key to identify imnet which created the request	
*	RC					08/04/2009	Added patient information and entries for SearchLOV table
*	RC					08/06/2009	Added logic to omit encounter data if document is global
*									Added logic to handle imnet of duid in Ref_ID
*									Removed double quotes around patient name
*   RC                  12/16/2009  Modified this sp to handle ROI deny
*									
***************************************************************************************************************************************/

Create PROCEDURE [dbo].[WFE_ROI_ProcessAutomatedRequestDeny](
	@Assignment_Id numeric,
	@Encounter varchar(20),
	@Facility varchar(20),
	@Queue_Id smallint,
	@Queue_Name varchar(50),
	@Debug bit = 0)
  AS

--Trim the input values

--Declare the local variables
BEGIN TRY	--Start the error handeling
	Declare @l_ROI_RequestMain_Seq int
	Declare @l_Imnet varchar(64)
	Declare @l_Roi_Log_Queue varchar(50)
	Declare @l_XML varchar(max)
	Declare @l_imagedate varchar(10)
	Declare @l_facility varchar(10)
	Declare @l_mrn varchar(20)
	Declare @l_name varchar(40)
	Declare @l_sex varchar(6)
	Declare @l_epn varchar(50)
	Declare @l_ssn varchar(27)
	Declare @l_plockout varchar(5)
	Declare @l_dob varchar(10)
	Declare @l_encounter varchar(20)
	Declare @l_elockout varchar(5)
	Declare @l_vip varchar(5)
	Declare @l_pt_type varchar(10)
	Declare @l_service varchar(10)
	Declare @l_admitted varchar(10)
	Declare @l_discharged varchar(10)

	--Retrieve the IMNET for this assignment
	Select 
		@l_Imnet =  Ref_ID 
	FROM 
		[cabinet].[dbo].[Assignments] WITH (NOLOCK) 
	WHERE 
		Assignment_Id = @Assignment_Id

	If @Debug = 1
	Begin
		Select @l_Imnet AS 'Ref_ID from assignments'
	END

	--use the length of the imnet to determine if it is really an imnet or is it a duid
	If Len(@l_Imnet) > 22
	BEGIN
		Select 
			@l_Imnet = RTRIM(IMNET)  
		FROM 
			[cabinet].[dbo].[Cabinet] WITH (NOLOCK)
		WHERE 
			DUID = @l_Imnet
		AND 
			Deleted = 'N'
		AND 
			Page = 1
	END

	If @Debug = 1
	Begin
		Select @l_Imnet AS 'This is the true Imnet id'
	END

	--fill the patient based variables
	Select 
		@l_imagedate = RTRIM(ISNULL(convert(char(10), cc.imagedate, 101),''))
		,@l_mrn = RTRIM(ISNULL(cc.mrn,''))
		,@l_name = RTRIM(ISNULL(hp.name,''))
		,@l_facility = RTRIM(ISNULL(cc.facility,''))
		,@l_encounter = RTRIM(ISNULL(cc.encounter,''))
		,@l_sex = CASE
					WHEN hp.sex = 'F' THEN 'Female'
					WHEN hp.sex = 'M' THEN 'Male'
					ELSE ''
					END--RTRIM(ISNULL(hp.sex,''))
		,@l_epn = RTRIM(ISNULL(hp.epn,'')) 
		,@l_ssn = RTRIM(ISNULL(hp.ssn,'')) 
		,@l_plockout = CASE hp.lockout WHEN '1' THEN 'True' ELSE 'False' END 
		,@l_dob = RTRIM(ISNULL(convert(char(10), hp.dob, 101),'')) 
	FROM 
		Cabinet.dbo.Cabinet cc
	INNER JOIN 
		HIS.dbo.Patients hp
			ON  cc.mrn = hp.mrn and cc.facility = hp.facility
	WHERE 
		cc.Imnet = @l_Imnet 

	IF @Debug = 1
	BEGIN
		Select 
		@l_imnet as 'imnet', @l_mrn as 'mrn',@l_facility as 'facility',@l_name as 'name' ,@l_sex as ' sex' ,@l_epn as 'epn' ,@l_ssn as 'ssn',@l_vip as 'vip',@l_plockout as 'plockout',@l_dob as 'dob'
	END

	--get the logged queue or set to none if the logged queue is not specified
	Set @l_Roi_Log_Queue = ISNULL((select Roi_Log_Queue from [cabinet].[dbo].[sysparms] WITH (NOLOCK)),'<none>')


	BEGIN TRANSACTION Tran_WFE_ROI_PAR01  -- Start the transaction
	--build the xml data string
	Select	@l_xml =	'<request Authdoc="' + RTRIM(@l_Imnet) + '"> 
					<status>Auth-Received</status>
					<receipt-date>'+ CONVERT ( char(10) , getdate()  , 101 ) +'</receipt-date>
					<status-reason>Auth Received Deny</status-reason>
					<request-reason>Authorization Request Denied</request-reason>
					<requestreason>System created request via Imnet = '	+ @l_Imnet + '</requestreason>
						<item source="hpf" type="patient">
							<patient mrn="' + @l_mrn + '" facility="' + @l_facility + '">
							<name>' + @l_name + '</name>
							<gender>' + @l_sex + '</gender>
							<epn>' + @l_epn + '</epn>
							<ssn>' + @l_ssn + '</ssn>
							<supplemental-id>0</supplemental-id>
							<patientLocked>' + @l_plockout + '</patientLocked>
							<dob>' + @l_dob + '</dob>'

	If LEN(@l_encounter) > 0 --this is not a global document
	BEGIN 
		--fill the encounter based varibles
		Select 
			@l_vip = CASE he.vip WHEN '1' THEN 'True' ELSE 'False' END
			,@l_pt_type = RTRIM(ISNULL(he.pt_type,''))
			,@l_service = RTRIM(ISNULL(he.service,''))
			,@l_elockout = CASE he.lockout WHEN 'Y' THEN 'True' ELSE 'False' END
			,@l_admitted = RTRIM(ISNULL(convert(char(10), he.admitted, 101),''))
			,@l_discharged = RTRIM(ISNULL(convert(char(10), he.discharged, 101),''))
		FROM HIS.dbo.Encounters he
		WHERE encounter = @l_encounter and facility = @l_facility 

		--add the data to the xml
		Select @l_xml = @l_xml +'<encounterLocked>' + @l_elockout + '</encounterLocked>
								<is-vip>' + @l_vip + '</is-vip>
								<encounter id="' + @l_encounter + '">
								<facility>' + @l_facility + '</facility>
								<patientType>' + @l_pt_type + '</patientType>
								<patientService>' + @l_service + '</patientService>
								<is-vip>' + @l_vip + '</is-vip>
								<is-locked>' + @l_elockout + '</is-locked>
								<admitDate>' + @l_admitted + '</admitDate>
								<discharge-date>' + @l_discharged + '</discharge-date>
								</encounter>'
	END
	Select @l_xml = @l_xml +	'</patient>
						</item>
				 </request>'
		--put the data in the table and get the resulting sequence number
		Insert [cabinet].[dbo].[ROI_RequestMain] (RequestMainXML) Values(@l_XML)
		select @l_ROI_RequestMain_Seq = @@Identity

		--insert the request and the roi status in the searchlov table
		INSERT Roi_SearchLOV
				   ([Organization_Seq]
				   ,[Parent]
				   ,[Child]
				   ,[Application]
				   ,[Item]
				   ,[Value])

		Select
					1 
					,1 
					,@l_ROI_RequestMain_Seq
					,'ROI' 
					,'request.request-reason' 
					,'Authorization Request Denied'
--
		INSERT Roi_SearchLOV
				   ([Organization_Seq]
				   ,[Parent]
				   ,[Child]
				   ,[Application]
				   ,[Item]
				   ,[Value])
		Select     1
				   ,1
				   ,@l_ROI_RequestMain_Seq
				   ,'ROI' 
				   ,'request.id' 
				   ,@l_ROI_RequestMain_Seq 
		UNION
		Select
					1 
					,1 
					,@l_ROI_RequestMain_Seq
					,'ROI' 
					,'request.status' 
					,1

--			
		--insert the remaining search values into the table		
		INSERT Roi_SearchLOV
				   ([Organization_Seq]
				   ,[Parent]
				   ,[Child]
				   ,[Application]
				   ,[Item]
				   ,[Value])
		Select     1
				   ,1
				   ,@l_ROI_RequestMain_Seq
				   ,'HPF' 
				   ,'patient.mrn' 
				   ,@l_mrn 
		UNION 
		Select     1
				   ,1
				   ,@l_ROI_RequestMain_Seq
				   ,'HPF' 
				   ,'patient.name' 
				   ,@l_name 
		UNION
		Select     1
				   ,1
				   ,@l_ROI_RequestMain_Seq
				   ,'HPF' 
				   ,'patient.lastname' 
				   ,RTRIM(Substring(@l_Name,1, Charindex(',', @l_Name)-1))
		UNION
		Select     1
				   ,1
				   ,@l_ROI_RequestMain_Seq
				   ,'HPF' 
				   ,'patient.firstname' 
				   ,RTRIM(Substring(@l_Name, Charindex(',', @l_Name)+2,40))
		UNION
		Select     1
				   ,1
				   ,@l_ROI_RequestMain_Seq
				   ,'HPF' 
				   ,'patient.ssn' 
				   ,@l_ssn 
		UNION
		Select     1
				   ,1
				   ,@l_ROI_RequestMain_Seq
				   ,'HPF' 
				   ,'patient.epn' 
				   ,@l_epn 
		UNION
		Select     1
				   ,1
				   ,@l_ROI_RequestMain_Seq
				   ,'HPF' 
				   ,'patient.facility' 
				   ,@l_facility 
		UNION
		Select     1
				   ,1
				   ,@l_ROI_RequestMain_Seq
				   ,'HPF' 
				   ,'request.status' 
				   , 'Auth-Received' 
		UNION
		Select     1
				   ,1
				   ,@l_ROI_RequestMain_Seq
				   ,'ROI' 
				   ,'request.receipt-date'  
				   ,@l_imagedate 
		UNION
		Select     1
				   ,1
				   ,@l_ROI_RequestMain_Seq
				   ,'HPF' 
				   ,'patient.dob'  
				   ,@l_DOB 

		IF LEN(@Encounter) > 0
		BEGIN
				INSERT Roi_SearchLOV
						   ([Organization_Seq]
						   ,[Parent]
						   ,[Child]
						   ,[Application]
						   ,[Item]
						   ,[Value])
				Select     1
						   ,1
						   ,@l_ROI_RequestMain_Seq
						   ,'HPF' 
						   ,'patient.is-vip' 
						   ,@l_vip 
		END

		--display the data if debug is on
		If @Debug = 1
		Begin
			Select
					@l_ROI_RequestMain_Seq AS 'ROI_RequestMain_Seq'		,
					@l_Imnet AS 'Imnet'									,
					@l_Roi_Log_Queue AS 'Roi_Log_Queue'
			Select * from [cabinet].[dbo].[ROI_RequestMain] WTIH (NOLOCK) 
				Where ROI_RequestMain_Seq = @l_ROI_RequestMain_Seq
			Select * from ROI_SearchLOV WTIH (NOLOCK) 
				Where child = @l_ROI_RequestMain_Seq order by item
			Select 
			@l_mrn,@l_name ,@l_sex ,@l_epn ,@l_ssn,@l_vip,@l_plockout,@l_sex
			,@l_dob,@Encounter,@Facility,@l_pt_type,@l_service,@l_vip,@l_elockout,@l_admitted,@l_discharged
		End

		--pass a notification on if there is a queue assigned for notification
		IF @l_Roi_Log_Queue != '<none>'
		BEGIN
				--Create an assignment in the ROI Notification queue 
				INSERT INTO [cabinet].[dbo].[Assignments_view] 
					(
					USERID
					, ENCOUNTER
					, PURPOSE
					, ASSIGNED
					, FACILITY
					, Ref_ID
					) 
				VALUES
					( 
					@l_Roi_Log_Queue
					, @Encounter
					, 'A request of information was automatically created due to document capture.  The request is ' + Cast(@l_ROI_RequestMain_Seq as varchar(12))
					, Getdate()
					, @Facility
					, @l_IMNET
					) 
				--show what was done if defug is on
				If @Debug = 1
				BEGIN
					SELECT * FROM [cabinet].[dbo].[assignments] WITH (NOLOCK) 
						WHERE Ref_ID = @l_Imnet and USERID = @l_Roi_Log_Queue
				END
		END
		--log the activity in the audit_trail
		INSERT [Audit].[dbo].[audit_trail] (UserInstanceID, Action, Remark, Occurred, Facility)
		VALUES (1,'1','Request Created - ' + Cast(@l_ROI_RequestMain_Seq as varchar(12)), getdate(), 'E_P_R_S')

	COMMIT TRANSACTION Tran_WFE_ROI_PAR01 
	SELECT 0, 'Stored procedure returned False'
	RETURN
END TRY
BEGIN CATCH  -- There was an error
	IF @@TRANCOUNT > 0
		ROLLBACK TRANSACTION Tran_WFE_ROI_PAR01;  --clean up the open transaction

	DECLARE @ErrorMessage NVARCHAR(4000);
	DECLARE @ErrorSeverity INT;
	DECLARE @ErrorState INT;

	SELECT 
		@ErrorMessage = ERROR_MESSAGE()
		,@ErrorSeverity = ERROR_SEVERITY()
		,@ErrorState = ERROR_STATE();

		 --Use RAISERROR inside the CATCH block to return error
		 --information about the original error that caused
		 --execution to jump to the CATCH block.
	RAISERROR 
		(
			@ErrorMessage -- Message text.
			,@ErrorSeverity -- Severity.
			,@ErrorState -- State.
		);
	RETURN
END CATCH;

GO
GRANT EXECUTE ON [dbo].[WFE_ROI_ProcessAutomatedRequestDeny] TO [IMNET]
