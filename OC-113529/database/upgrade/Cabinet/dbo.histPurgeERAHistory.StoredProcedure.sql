USE [cabinet]
GO

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[histPurgeERAHistory]') AND type in (N'P'))
DROP PROCEDURE [dbo].[histPurgeERAHistory]
GO

-- =============================================
-- Author:		Ray Firth
-- Create date: 4/11/2008
-- Description:	Purges old data from the ERA history
-- =============================================
CREATE PROCEDURE [dbo].[histPurgeERAHistory] 
	@ReferenceDate smalldatetime
AS
BEGIN	
	DELETE hist_EOB WHERE ERAID IN (SELECT ERAID FROM hist_ERA WHERE ProcDate < @ReferenceDate)
	DELETE hist_ERA WHERE ProcDate < @ReferenceDate
END
GO
	
GRANT EXECUTE ON [dbo].[histPurgeERAHistory] TO [IMNET] AS [dbo]
GO
