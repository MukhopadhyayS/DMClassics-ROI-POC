USE [his]
GO

IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[MPF_Scheduled_CodingValueBacklog]') AND type in (N'P', N'PC'))
DROP PROCEDURE [dbo].[MPF_Scheduled_CodingValueBacklog]								   
GO

/*
***************************************************************
*  	COPYRIGHT MCKESSON 2012
*		ALL RIGHTS RESERVED
*
* 	The copyright to the computer program(s) herein
* 	is the property of McKesson. The program(s)
* 	may be used and/or copied only with the written
* 	permission of McKesson or in accordance with
* 	the terms and conditions stipulated in the     
* 	agreement/contract under which the program(s)
* 	have been supplied.
*****************************************************************
* Procedure Name: [HPF2HBI_SUBSET_CODING_VALUE_BACKLOG]
* Description:   
*
* NOTES: 
* Revision History:
*	Name					Date		Changes
*	---------				-------		-------------
* 	Srinivasan Ramaswamy	10/05/2012	Created
*   Iryna Politykina        02/28/2013  Add a condition if admitted date is NULL,
*										add LOS_Undefined column	
*	Bhanu Vanteru			03/12/2013	removed condition to check Encounters.abstractComplete is null. CR 377,929
*   Iryna Politykina		03/15/2013  set Facility Name to facilityCode , if facilityname is null or empty,set to 'NO FACILITY' if facilitycode is empty
*   Latonia Howard			04/18/2013  Fix per CR # 379227 :Patient type, service code etc. must be identified based on code set related to facility
*	Iryna Politykina		04/22/2013	add condition to check Encounters.abstractComplete is null. CR #379172 
*	MD Meersma				10/08/2013	Change localhost to use @@servername
****************************************************************
*/
/*

	UC5925 - View Latest Coding Value Backlog Measure

	Select encounters which are in coding state. 
	To do this if there is any assignment attached to a encounter and
		the workflow queue type of the assignment is of "Coding" Type.
	
	====================================================================================================================================================================================
	Columns						DATABASE	TABLE				DESCRIPTION																			NAME USED IN SCRIPT
	====================================================================================================================================================================================
	Encounter					HIS	 		Encounters 			Encounter Id																		Encounter		
 	Facility_Name				CABINET		Facility_File		Facility Name																		FacilityName
 	MRN							HIS			Encounters			MRN																					MRN
 	PT_TYPE						CABINET		PT_Type_File		Patient Type																		PatientTypeName	
 	Discharged					HIS			Encounters			Discharge Date																		DischargedDate
 	Name						HIS			Physicians			One Physician																		AttendingPhysician
 	Unit_Name					HIS			Unit_File			Unit Name																			MedicalUnitName
 	service						HIS			user_facility		Encounter Service																	EncounterService
 	service						HIS			user_facility		Physician Service																	PhysicanService
 	total_charges				HIS			Encounters			Charges																				Charges
 	userid						CABINET		Assignment			Workflow Queue Name																	WorkflowQueue	
	Name						HIS			Patient				Patient Name																		PatientName
 	CALCULATED FIELD			HIS			Encounters			Number of days since discharged (Today - Discharged Date from Encounters table)		DNFCDays
 	CALCULATED FIELD			HIS			Encounters			Length Of stay (Discharged Date - Admit Date)										LengthOfStay
	CALCULATED LOS_x ...		HIS			Encounters			LOS Days Bucket for 1, 2, 3 4-7,													LOS_1, LOS_2, LOS_3,LOS_4_7, 
																8-14, 15-21, 22-28, 29-60, 61-90, 91 days and more									LOS_8_14,LOS_15_21, LOS_22_28,																																									
																																					LOS_29_60, LOS_61_90,LOS_91_More 
	Discharged					HIS			Encounters			Discharge Date in mm/dd/yyyy hh:mm AM/PM format										DischargedDateText
	Discharged					HIS			Encounters			Discharge Date																		DischargeDateShortText
	======================================================================================================================================================================================
*/

CREATE PROCEDURE [dbo].[MPF_Scheduled_CodingValueBacklog]
	@HBI_TargetDir varchar(4000)  -- file_path column from WebData.dbo.wt_definition_info (HBI Server)
	AS

	BEGIN try

		DECLARE @xp_cmdshell tinyint, @Success int, @sqlcmd varchar(2000)

		-- show advanced options enable if disabled
		IF EXISTS(
			SELECT 
				value_in_use 
			FROM 
				master.sys.configurations 
			WHERE 
				name = 'show advanced options' AND
				value_in_use = 0
		)
		BEGIN
			EXEC sp_configure 'show advanced options', 1	
			reconfigure with override
		END

		-- xp_cmdshell enable if disabled
		SELECT
			@xp_cmdshell = 1
		IF EXISTS(
			SELECT
				value_in_use 
			FROM 
				master.sys.configurations 
			WHERE 
				name = 'xp_cmdshell' AND 
				value_in_use = 0
		)
		BEGIN
			SELECT @xp_cmdshell = 0
			EXEC sp_configure 'xp_cmdshell', 1
			reconfigure with override
		END
			
		-- Delete all the previous TXT files incase we do not generate all of them
		SET  @sqlcmd = 'del ' + @HBI_TargetDir + '\HBI_Scheduled_CodingValueBacklog.txt'
		EXEC @Success =  master..xp_cmdshell @sqlcmd, NO_OUTPUT

		IF OBJECT_ID('tempdb..##HBI_SUBSET_CODING_VALUE_BACKLOG') is not null
		DROP TABLE ##HBI_SUBSET_CODING_VALUE_BACKLOG

		-- Main code that selects the subset result
		SELECT 
			DISTINCT e.encounter AS Encounter, 
			COALESCE(NULLIF((f.facility_name), ''), COALESCE(NULLIF(e.facility, ''), 'NO FACILITY')) AS FacilityName, 
			e.mrn AS MRN, 
			COALESCE(NULLIF(pt.pttype_name, ''),'Undefined') AS PatientTypeName, 
			e.discharged AS DischargeDate, 
			COALESCE(
				(
					SELECT TOP 1 
						name 
					FROM 
						PHYSICIANS 
					WITH
						(NOLOCK) 
					WHERE 
						FOLDER = E.ENCOUNTER and 
						FACILITY = E.FACILITY and 
						type = 'Attending' 
					ORDER BY 
						NAME
				), 'Undefined'
			) AS AttendingPhysician, 
			COALESCE(
				(
					SELECT top 1 
						unit_name 
					FROM 
						UNIT_FILE 
					WITH 
						(nolock) 
					WHERE 
						UNIT_CODE = (
							SELECT TOP 1 
								unit 
							FROM 
								locator 
							WITH 
								(NOLOCK) 
							WHERE 
								folder = e.encounter AND 
								facility = e.facility 
							ORDER BY 
								occurred DESC
						)  AND SET_ID = f.UNIT_SET
				), 'Undefined'
			) AS MedicalUnitName,
            COALESCE(NULLIF(
	             (SELECT TOP 1 SERVICE_NAME FROM cabinet.dbo.SERVICE_FILE WHERE            SERVICE_CODE=e.service AND SERVICE_ACTIVE='Y' AND SET_ID=(SELECT TOP 1 SERVICE_SET            from cabinet.dbo.FACILITY_FILE WHERE FACILITY_CODE=e.FACILITY)  
	             ),''),'Undefined') as EncounterService,
	        COALESCE(NULLIF(
	           (SELECT TOP 1 service FROM cabinet.dbo.user_facility 	WITH (NOLOCK) WHERE
			    physician_code = (SELECT TOP 1 	code FROM physicians WITH (NOLOCK) WHERE folder = e.encounter AND facility = e.facility AND type = 'Attending' 	ORDER BY name) 
			    AND facility = e.facility),''), 'Undefined'
			    ) AS PhysicanService,
			COALESCE(	e.total_charges, 0) AS Charges,
			(
				SELECT TOP 1 
					userid 
				FROM
					cabinet.dbo.assignments a 
				WITH	
					(NOLOCK) JOIN 
					cabinet.dbo.wq_definition WITH (NOLOCK) ON a.userid = cabinet.dbo.wq_definition.queue JOIN
					cabinet.dbo.QueueTypeLov WITH (NOLOCK) ON cabinet.dbo.QueueTypeLov.QueueTypeLovID = cabinet.dbo.wq_definition.QueueTypeLovID
				WHERE
					a.encounter = e.encounter AND 
					a.facility = e.facility AND
					cabinet.dbo.QueueTypeLov.QueueTypeName = 'Coding'
				ORDER BY
					a.assigned DESC
			) AS WorkflowQueue, 
			p.name AS PatientName,
			datediff(dd,e.Discharged, getdate()) AS DNFCDays,
			LengthOfStay = CASE 
				  WHEN E.ADMITTED IS NULL THEN 0
				  ELSE
  					(CASE 
  						WHEN DATEDIFF(DAY, E.ADMITTED, E.DISCHARGED)= 0 THEN 1
						ELSE DATEDIFF(DAY, E.ADMITTED, E.DISCHARGED)
					END)
			   END,
			LOS_1 = (CASE WHEN (datediff(dd, e.admitted, e.Discharged)) = 1 THEN 1 ELSE 0 END),
			LOS_2 = (CASE WHEN (datediff(dd, e.admitted, e.Discharged)) = 2 THEN 1 ELSE 0 END),
			LOS_3 = (CASE WHEN (datediff(dd, e.admitted, e.Discharged)) = 3 THEN 1 ELSE 0 END),
			LOS_4_7 = (CASE WHEN (datediff(dd, e.admitted, e.Discharged)) BETWEEN 4 AND 7 THEN 1 ELSE 0 END),
			LOS_8_14 = (CASE WHEN (datediff(dd, e.admitted, e.Discharged)) BETWEEN 8 AND 14 THEN 1 ELSE 0 END),
			LOS_15_21 = (CASE WHEN (datediff(dd, e.admitted, e.Discharged)) BETWEEN 15 AND 21 THEN 1 ELSE 0 END),
			LOS_22_28 = (CASE WHEN (datediff(dd, e.admitted, e.Discharged)) BETWEEN 22 AND 28 THEN 1 ELSE 0 END),
			LOS_29_60 = (CASE WHEN (datediff(dd, e.admitted, e.Discharged)) BETWEEN 29 AND 60 THEN 1 ELSE 0 END),
			LOS_61_90 = (CASE WHEN (datediff(dd, e.admitted, e.Discharged)) BETWEEN 61 AND 90 THEN 1 ELSE 0 END),
			LOS_91_above = (CASE WHEN (datediff(dd, e.admitted, e.Discharged)) > 90 THEN 1 ELSE 0 END),
			his.dbo.fnHBIDateTime(E.DISCHARGED) as DischargedDateText,
			CONVERT(VARCHAR(10),e.discharged , 101 ) AS DischargeDateShortText,
			LOS_Undefined = (CASE WHEN e.admitted IS NULL THEN 1 ELSE 0 END)
			
		INTO
			##HBI_SUBSET_CODING_VALUE_BACKLOG			
		FROM 
			encounters e WITH (NOLOCK) 
			JOIN cabinet.dbo.facility_file f WITH (NOLOCK) ON e.facility = f.facility_code 
			LEFT OUTER JOIN	cabinet.dbo.pttype_file pt WITH (NOLOCK) ON e.pt_type = pt.pttype_code AND pt.set_id = f.pttype_set AND pt.pttype_active = 'Y' 
			JOIN patients p WITH (NOLOCK) ON e.mrn = p.mrn AND e.facility = p.facility
		WHERE
			e.discharged IS NOT NULL AND
			E.AbstractComplete is null AND
			EXISTS (
				SELECT TOP 1 
					QueueTypeName 
				FROM
					cabinet.dbo.assignments a WITH (NOLOCK) JOIN 
					cabinet.dbo.wq_definition WITH (NOLOCK) ON a.userid = cabinet.dbo.wq_definition.queue JOIN 
					cabinet.dbo.QueueTypeLov WITH (NOLOCK) ON cabinet.dbo.QueueTypeLov.QueueTypeLovID = cabinet.dbo.wq_definition.QueueTypeLovID
				WHERE
					a.encounter = e.encounter AND
					a.facility = e.facility and 
					cabinet.dbo.QueueTypeLov.QueueTypeName = 'Coding'
				ORDER BY
					a.assigned DESC
			)
			
			SET  @sqlcmd = ' bcp ##HBI_SUBSET_CODING_VALUE_BACKLOG out ' + rtrim(@HBI_TargetDir) + '\HBI_Scheduled_CodingValueBacklog.txt -c -T -S' + @@SERVERNAME

			EXEC @Success =  master..xp_cmdshell @sqlcmd --, NO_OUTPUT

			IF @Success = 1
				RAISERROR ('bcp of ##HBI_SUBSET_CODING_VALUE_BACKLOG was not successful', 10, 1)
	END TRY

	BEGIN CATCH  -- There was an error

		while @@TRANCOUNT > 0
		ROLLBACK TRANSACTION 

		-- Delete all the previous TXT files as an error has occurred 
		SET  @sqlcmd = 'del ' + @HBI_TargetDir + '\HBI_Scheduled_CodingValueBacklog.txt'
		EXEC @Success =  master..xp_cmdshell @sqlcmd, NO_OUTPUT

		DECLARE @ErrorMessage NVARCHAR(2048), @ErrorSeverity INT, @ErrorState INT, @ErrorProcedure nvarchar(252), @ErrorNumber int

		SELECT 
			@ErrorMessage = ERROR_MESSAGE() ,
			@ErrorSeverity = ERROR_SEVERITY(),
			@ErrorState = ERROR_STATE(),
			@ErrorProcedure = ERROR_PROCEDURE(),
			@ErrorNumber = ERROR_NUMBER()

		--if ERROR_NUMBER() <> 50000  -- do not log RAISERRORS caused by the sub stored procedures, they have CEL for this
		insert cabinet.dbo.SYSERRORS_GLOBAL (GlobalID, OCCURRED, ERROR_TYPE, ERROR_DESC, [ERROR_NUMBER], [ERROR_SEVERITY], [ERROR_STATE], [ERROR_PROCEDURE], [ERROR_LINE])
		values (-43, getdate(), 'SQL', ERROR_MESSAGE(), ERROR_NUMBER(), ERROR_SEVERITY(), ERROR_STATE(), ERROR_PROCEDURE(), ERROR_LINE())

		RAISERROR (@ErrorMessage, @ErrorSeverity, @ErrorState)

	END CATCH
GO

GRANT EXECUTE ON [dbo].[MPF_Scheduled_CodingValueBacklog] TO [IMNET] AS [dbo]
GO
