USE [cabinet]
GO

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[histERAInsert]') AND type in (N'P'))
DROP PROCEDURE [dbo].[histERAInsert]
GO

CREATE PROCEDURE [dbo].[histERAInsert]
(
	@ERAFileName varchar(255),
	@ProcDate smalldatetime,
	@Status varchar(20)
)
AS

SET NOCOUNT OFF

INSERT INTO [hist_ERA] ([ERAFileName], [ProcDate], [Status]) VALUES (@ERAFileName, @ProcDate, @Status)
	
SELECT ERAID, ERAFileName, ProcDate, Status FROM hist_ERA WHERE (ERAID = SCOPE_IDENTITY())
GO

	
GRANT EXECUTE ON [dbo].[histERAInsert] TO [IMNET] AS [dbo]
GO
