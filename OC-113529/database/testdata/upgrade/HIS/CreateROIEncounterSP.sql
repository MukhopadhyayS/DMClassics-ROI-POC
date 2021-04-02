Use HIS
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[CreateROIEncounter]') and OBJECTPROPERTY(id, N'IsProcedure') = 1)
drop procedure [dbo].[CreateROIEncounter]
GO

SET QUOTED_IDENTIFIER ON 
GO
SET ANSI_NULLS ON 
GO



CREATE PROCEDURE CreateROIEncounter(
   	@Facility char(10),
	@prefix varchar(10),
    @longname varchar(20)
        )
AS
/************************************************************************/
/* Script: 		!Create CreateROIEncounter stored proc.sql				*/
/* Creation Date: 	11/14/08											*/
/* Copyright: 		McKesson											*/
/* Written by: 		RC													*/
/*																		*/
/* Purpose:		Creates Fac/MRN/Enc for ROI indexing					*/
/*																		*/
/* Reguirments:		None												*/
/*																		*/
/* Usage:	CreateROIEncounter Facility, MRN and Encounter prefix, Lastname*/
/*		EX CreateROIEncounter 'A', 'ROI', 'ROIDayFolder'				*/
/* Updates to the database:												*/
/*																		*/
/* Modifications to the database:	adds records in patients and enc	*/
/*																		*/
/* Updates to this script:												*/
/* Date 		Author 	Purpose											*/
/************************************************************************/

--CreateROIEncounter 'A', 'ROI', 'ROIDayFolder'
--Select * from his..patients where mrn like 'ROI%'
--Select * from his..encounters where mrn like 'ROI%'

declare 
@day char(2),
@month char(2),
@monthname varchar(10),
@year char(4),
@name char(40),
@mrn char(20),
@encounter char(20),
@datetime datetime,
@date varchar(8)

select @datetime = getdate()
select @date = convert(varchar(20),@datetime, 112)
select @monthname = DATENAME(month, @datetime)
select @day = substring(@date,7,2)
select @month = substring(@date,5,2)
select @year = substring(@date,1,4)
select @mrn = @prefix + @year + @month
select @encounter = @prefix + @year + @month + @day
select @name = @longname + ', ' + @year +  @month


If not exists (select * from patients where mrn = @mrn and facility = @facility)
	Begin
	Insert into patients (mrn, facility, name,ssn,dob,LOCKOUT,domain_id,epn) values (@mrn, @facility, @name, @mrn, @datetime, 'N','BO','')
 	--Insert into cust_patient (mrn, facility) values(@mrn, @facility)
	-- Uncomment if using the cust_patient table
	End

If not exists (select * from encounters where mrn = @mrn and facility = @facility and encounter = @encounter)
	Begin
	Insert into encounters (mrn,encounter,facility,ADMITTED,DISCHARGED,LOCKOUT) values (@mrn, @encounter, @facility, @datetime, @datetime, 'N')
 	--Insert into cust_encounter (encounter, facility) values(@encounter, @facility)
	-- Uncomment if using the cust_encounter table
	End

GO
SET QUOTED_IDENTIFIER OFF 
GO
SET ANSI_NULLS ON 
GO

GRANT  EXECUTE  ON [dbo].[CreateROIEncounter]  TO [IMNET]
GO

