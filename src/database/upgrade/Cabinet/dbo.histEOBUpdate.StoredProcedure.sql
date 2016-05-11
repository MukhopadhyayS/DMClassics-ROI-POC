USE [cabinet]
GO

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[histEOBUpdate]') AND type in (N'P'))
DROP PROCEDURE [dbo].[histEOBUpdate]
GO

CREATE PROCEDURE [dbo].[histEOBUpdate]
(
	@RefId int,
	@CLP01 varchar(38),
	@ISACtrl char(9),
	@STCtrl varchar(9),
	@GroupCtrl varchar(9),
	@LX varchar(6),
	@Status varchar(20),
	@PtName varchar(97),
	@Encounter varchar(38),
	@MRN varchar(50),
	@Facility varchar(50),
	@NPI varchar(80),
	@ERAID int,
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

UPDATE [hist_EOB] SET [RefId] = @RefId, [CLP01] = @CLP01, [ISACtrl] = @ISACtrl, [STCtrl] = @STCtrl, [GroupCtrl] = @GroupCtrl, [LX] = @LX, [Status] = @Status, [PtName] = @PtName, [Encounter] = @Encounter, [MRN] = @MRN, [Facility] = @Facility, [NPI] = @NPI, [ERAID] = @ERAID 
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
	
SELECT RefId, CLP01, ISACtrl, STCtrl, GroupCtrl, LX, Status, PtName, Encounter, MRN, Facility, NPI, ERAID FROM hist_EOB WHERE (RefId = @RefId)
GO
	
GRANT EXECUTE ON [dbo].[histEOBUpdate] TO [IMNET] AS [dbo]
GO
