/*************************************************************************************************\
*  	COPYRIGHT MCKESSON 2004
*		ALL RIGHTS RESERVED
*
* 	The copyright to the computer program(s) herein
* 	is the property of MCKESSON. The program(s)
* 	may be used and/or copied only with the written
* 	permission of MCKESSON or in accordance with
* 	the terms and conditions stipulated in the     
* 	agreement/contract under which the program(s)
* 	have been supplied.
**************************************************************************************************
* Script Name: action_table.tbl
* Script Type: TBL (For purpose of CREATE, DROP, ALTER, GRANT to table, index or constraint.)
*
* Description: Update data in Action_table table to reflect the new Action type of MG for HR Merge
*
*		Name			Type		Description
*		---------		-------		----------------
* Params In:	N/A
*				
* Params Out: 	N/A
*
* Result Set: 	
*
* Database: Audit
* Tables Used:	
*
* NOTES: 
* Revision History:
*	Name			Date		Changes/Version&Build/Track#
*	---------		-------		----------------------------
*	
*	RC			09/14/2009	Insert Action for ROI 13.5 in E_P_R_S
*	
\*************************************************************************************************/

DELETE FROM Audit.dbo.action_table
WHERE action IN ('1','2','3','4','5','6','7','8','9')
AND facility = 'E_P_R_S'

INSERT action_table (Action, Action_Desc, Track, Display, Facility)

SELECT '1', 'ROI Log', 'Y', 'Y', 'E_P_R_S'
UNION
SELECT '2', 'ROI Edit', 'Y', 'Y', 'E_P_R_S'
UNION
SELECT '3', 'ROI Delete', 'Y', 'Y', 'E_P_R_S'
UNION
SELECT '4', 'ROI Deny', 'Y', 'Y', 'E_P_R_S'
UNION
SELECT '5', 'ROI Cancel', 'Y', 'Y', 'E_P_R_S'
UNION
SELECT '6', 'ROI Pend', 'Y', 'Y', 'E_P_R_S'
UNION
SELECT '7', 'ROI Post', 'Y', 'Y', 'E_P_R_S'
UNION
SELECT '8', 'ROI View', 'Y', 'Y', 'E_P_R_S'
GO
IF NOT EXISTS (Select 1 FROM  [audit].[dbo].[action_table] WHERE [ACTION] = 'RP' AND [FACILITY] = 'E_P_R_S')
BEGIN
INSERT INTO [audit].[dbo].[action_table]
           ([ACTION]
           ,[ACTION_DESC]
           ,[TRACK]
           ,[DISPLAY]
           ,[FACILITY])
     VALUES
           ('RP'
           ,'Printed'
           ,'Y'
           ,'Y'
           ,'E_P_R_S')
END
GO
IF NOT EXISTS (Select 1 FROM  [audit].[dbo].[action_table] WHERE [ACTION] = 'RF' AND [FACILITY] = 'E_P_R_S')
BEGIN
INSERT INTO [audit].[dbo].[action_table]
           ([ACTION]
           ,[ACTION_DESC]
           ,[TRACK]
           ,[DISPLAY]
           ,[FACILITY])
     VALUES
           ('RF'
           ,'Faxed'
           ,'Y'
           ,'Y'
           ,'E_P_R_S')
END
GO
IF NOT EXISTS (Select 1 FROM  [audit].[dbo].[action_table] WHERE [ACTION] = 'RD' AND [FACILITY] = 'E_P_R_S')
BEGIN
INSERT INTO [audit].[dbo].[action_table]
           ([ACTION]
           ,[ACTION_DESC]
           ,[TRACK]
           ,[DISPLAY]
           ,[FACILITY])
     VALUES
           ('RD'
           ,'Export2PDF'
           ,'Y'
           ,'Y'
           ,'E_P_R_S')
END
GO
IF NOT EXISTS (Select 1 FROM  [audit].[dbo].[action_table] WHERE [ACTION] = 'RS' AND [FACILITY] = 'E_P_R_S')
BEGIN
INSERT INTO [audit].[dbo].[action_table]
           ([ACTION]
           ,[ACTION_DESC]
           ,[TRACK]
           ,[DISPLAY]
           ,[FACILITY])
     VALUES
           ('RS'
           ,'Status'
           ,'Y'
           ,'Y'
           ,'E_P_R_S')
END
GO



