USE [cabinet]
GO

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[histERASelect]') AND type in (N'P'))
DROP PROCEDURE [dbo].[histERASelect]
GO

CREATE PROCEDURE [dbo].[histERASelect]
	@ERAID int = NULL
AS

SET NOCOUNT ON

IF @ERAID IS NULL
	BEGIN
		SELECT  ERAID, ERAFileName, ProcDate, Status
		FROM  hist_ERA
	END
ELSE
	BEGIN
		SELECT  ERAID, ERAFileName, ProcDate, Status
		FROM   hist_ERA
		WHERE ERAID = @ERAID
	END
GO
	
GRANT EXECUTE ON [dbo].[histERASelect] TO [IMNET] AS [dbo]
GO
