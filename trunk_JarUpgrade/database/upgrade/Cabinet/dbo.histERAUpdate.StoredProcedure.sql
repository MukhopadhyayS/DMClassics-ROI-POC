USE [cabinet]
GO

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[histERAUpdate]') AND type in (N'P'))
DROP PROCEDURE [dbo].[histERAUpdate]
GO

CREATE PROCEDURE [dbo].[histERAUpdate]
(
	@ERAFileName varchar(255),
	@ProcDate smalldatetime,
	@Status varchar(20),
	@Original_ERAID int,
	@Original_ERAFileName varchar(255),
	@Original_ProcDate smalldatetime,
	@Original_Status varchar(20),
	@ERAID int
)
AS

SET NOCOUNT OFF

UPDATE [hist_ERA] SET [ERAFileName] = @ERAFileName, 
	[ProcDate] = @ProcDate, [Status] = @Status 
WHERE (([ERAID] = @Original_ERAID) AND 
	((@Original_ERAFileName IS NULL AND [ERAFileName] IS NULL) OR ([ERAFileName] = @Original_ERAFileName)) AND 
	([ProcDate] = @Original_ProcDate) AND 
	((@Original_Status IS NULL AND [Status] IS NULL) OR ([Status] = @Original_Status)))
	
SELECT ERAID, ERAFileName, ProcDate, Status FROM hist_ERA WHERE (ERAID = @ERAID)
GO
	
GRANT EXECUTE ON [dbo].[histERAUpdate] TO [IMNET] AS [dbo]
GO
