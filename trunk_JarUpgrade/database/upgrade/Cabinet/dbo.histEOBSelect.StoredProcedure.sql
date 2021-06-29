USE [cabinet]
GO

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[histEOBSelect]') AND type in (N'P'))
DROP PROCEDURE [dbo].[histEOBSelect]
GO

CREATE PROCEDURE [dbo].[histEOBSelect]
	@RefId int = NULL,
	@ERAID int = NULL
AS

SET NOCOUNT ON

IF NOT @RefId = NULL
	BEGIN
	SELECT RefId, CLP01, ISACtrl, STCtrl, GroupCtrl, LX, Status, PtName, Encounter, MRN, Facility, NPI, ERAID
	FROM hist_EOB
	WHERE RefId = @RefId
	END
ELSE
	IF NOT @ERAID IS NULL
		BEGIN
		SELECT RefId, CLP01, ISACtrl, STCtrl, GroupCtrl, LX, Status, PtName, Encounter, MRN, Facility, NPI, ERAID
		FROM hist_EOB
		WHERE ERAID = @ERAID
		END
	ELSE
		SELECT RefId, CLP01, ISACtrl, STCtrl, GroupCtrl, LX, Status, PtName, Encounter, MRN, Facility, NPI, ERAID
		FROM hist_EOB
GO
	
GRANT EXECUTE ON [dbo].[histEOBSelect] TO [IMNET] AS [dbo]
GO
