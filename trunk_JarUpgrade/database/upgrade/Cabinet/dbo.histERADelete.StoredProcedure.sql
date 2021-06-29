USE [cabinet]
GO

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[histERADelete]') AND type in (N'P'))
DROP PROCEDURE [dbo].[histERADelete]
GO

CREATE PROCEDURE [dbo].[histERADelete]
(
	@Original_ERAID int,
	@Original_ERAFileName varchar(255),
	@Original_ProcDate smalldatetime,
	@Original_Status varchar(20)
)
AS

SET NOCOUNT OFF

DELETE FROM [hist_ERA] 
WHERE (([ERAID] = @Original_ERAID) AND 
((@Original_ERAFileName IS NULL AND [ERAFileName] IS NULL) OR ([ERAFileName] = @Original_ERAFileName)) AND 
([ProcDate] = @Original_ProcDate) AND 
((@Original_Status IS NULL AND [Status] IS NULL) OR ([Status] = @Original_Status)))
GO
	
GRANT EXECUTE ON [dbo].[histERADelete] TO [IMNET] AS [dbo]
GO
