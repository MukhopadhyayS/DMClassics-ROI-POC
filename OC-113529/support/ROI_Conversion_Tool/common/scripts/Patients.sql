/*
***************************************************************
*
*  	COPYRIGHT MCKESSON 2013
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
* SQL script Name: C-XMLRequest
* Description:   
*   Displays the following for pre-migration and post migration requests to validate patients:
*	1.	Total number of patients
*	2.	Total number of Non-HPF patients
*
* NOTES: 
* Revision History:
*	Name					Date		Changes
*	---------				-------		-------------
* 	I.Politykina			05/12/2013	Created
****************************************************************
*/
SET QUOTED_IDENTIFIER ON;
SET NOCOUNT ON;


DECLARE @totalPatients int, @supplBefore int, @supplAfter int;
SELECT @totalPatients = COUNT(*) FROM his.dbo.PATIENTS  WITH (NOLOCK)
SELECT @supplBefore = COUNT(*)  FROM cabinet.dbo.ROI_Supplemental  WITH (NOLOCK) WHERE SupplementalXML.value('(/patient/@id)[1]','decimal') IS NOT NULL

;WITH NON_MPF_PATIENTS_BEFORE_CONVERSION AS
(  SELECT 
COALESCE((CASE CHARINDEX(',',SupplementalXML.value('(/patient/name)[1]','varchar(256)')) 
					WHEN 0 THEN LTRIM(PARSENAME(REPLACE(SupplementalXML.value('(/patient/name)[1]','varchar(256)'),',','.'),1)) 
					ELSE RTRIM(PARSENAME(REPLACE(SupplementalXML.value('(/patient/name)[1]','varchar(256)'),',','.'),2)) END), '') as LastName,
COALESCE(( CASE CHARINDEX(',',SupplementalXML.value('(/patient/name)[1]','varchar(256)')) 
			WHEN 0 THEN NULL 
			ELSE LTRIM(PARSENAME(REPLACE(SupplementalXML.value('(/patient/name)[1]','varchar(256)'),',','.'),1)) END), '') as FirstName,
COALESCE(SupplementalXML.value('(/patient/gender)[1]', 'varchar(1)' ), '') as gender,			
COALESCE(RTRIM(LTRIM(SupplementalXML.value('(/patient/ssn)[1]', 'varchar(20)' ))), '') as SSN,
COALESCE(RTRIM(LTRIM(SupplementalXML.value('(/patient/dob)[1]', 'varchar(20)' ))), '') as DateOfBirth
FROM cabinet.dbo.ROI_Supplemental  WITH (NOLOCK)
WHERE SupplementalXML.value('(/patient/@id)[1]', 'varchar(20)') IS NOT NULL
)
(
SELECT @supplAfter = COUNT(*) FROM cabinet.dbo.ROI_SupplementalPatients  suppPatients  WITH (NOLOCK)
JOIN NON_MPF_PATIENTS_BEFORE_CONVERSION nmpbc WITH (NOLOCK) ON 
nmpbc.LASTNAME = COALESCE(suppPatients.LASTNAME, '')   AND nmpbc.FIRSTNAME = COALESCE(suppPatients.FIRSTNAME, '')
AND  nmpbc.SSN = COALESCE(suppPatients.SSN, '')  AND  nmpbc.GENDER = COALESCE(suppPatients.GENDER, '' ) 
AND  nmpbc.DateOfBirth = COALESCE(suppPatients.DOB, '' ))



SELECT 'MPF Patients' AS 'Patient Type', @totalPatients AS 'Before Conversion', @totalPatients AS 'After Conversion'
, (CASE  WHEN @totalPatients = @totalPatients THEN 'Pass' ELSE 'Fail' END) AS 'Check'
UNION ALL
SELECT 'NON-MPF Patients' as 'Patient Type', 
@supplBefore  AS 'Number of Patients Before the Conversion',
@supplAfter AS 'Number of Patients After the Conversion', 
(CASE WHEN @supplBefore < >@supplAfter THEN 'Fail' ELSE 'Pass' END)  as 'CHECK'



