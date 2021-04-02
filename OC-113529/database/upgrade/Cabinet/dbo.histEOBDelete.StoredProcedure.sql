USE [cabinet]
GO

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[histEOBDelete]') AND type in (N'P'))
DROP PROCEDURE [dbo].[histEOBDelete]
GO

CREATE PROCEDURE [dbo].[histEOBDelete]
(
	@Original_RefId int,
	@Original_CLP01 varchar(38),
	@Original_ISACtrl char(9),
	@Original_STCtrl varchar(9),
	@Original_GroupCtrl varchar(9),
	@Original_LX varchar(6),
	@Original_Status varchar(20),
	@Original_PtName varchar(97),
	@Original_Encounter varchar(38),
	@Original_MRN varchar(50),
	@Original_Facility varchar(50),
	@Original_NPI varchar(80),
	@Original_ERAID int
)
AS

SET NOCOUNT OFF

DELETE FROM [hist_EOB] 
WHERE (([RefId] = @Original_RefId) AND 
	((@Original_CLP01 IS NULL AND [CLP01] IS NULL) OR ([CLP01] = @Original_CLP01)) AND 
	([ISACtrl] = @Original_ISACtrl) AND 
	((@Original_STCtrl IS NULL AND [STCtrl] IS NULL) OR ([STCtrl] = @Original_STCtrl)) AND 
	((@Original_GroupCtrl IS NULL AND [GroupCtrl] IS NULL) OR ([GroupCtrl] = @Original_GroupCtrl)) AND 
	((@Original_LX IS NULL AND [LX] IS NULL) OR ([LX] = @Original_LX)) AND 
	((@Original_Status IS NULL AND [Status] IS NULL) OR ([Status] = @Original_Status)) AND 
	((@Original_PtName IS NULL AND [PtName] IS NULL) OR ([PtName] = @Original_PtName)) AND 
	((@Original_Encounter IS NULL AND [Encounter] IS NULL) OR ([Encounter] = @Original_Encounter)) AND 
	((@Original_MRN IS NULL AND [MRN] IS NULL) OR ([MRN] = @Original_MRN)) AND 
	((@Original_Facility IS NULL AND [Facility] IS NULL) OR ([Facility] = @Original_Facility)) AND 
	((@Original_NPI IS NULL AND [NPI] IS NULL) OR ([NPI] = @Original_NPI)) AND 
	([ERAID] = @Original_ERAID))
GO	

GRANT EXECUTE ON [dbo].[histEOBDelete] TO [IMNET] AS [dbo]
GO
