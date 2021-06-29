USE [cabinet]
GO

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[histERAFilePurgeList]') AND type in (N'P'))
DROP PROCEDURE [dbo].[histERAFilePurgeList]
GO

-- =============================================
-- Author:		Ray Firth
-- Create date: 4/15/2008
-- Description:	Gets list of era files to delete from xml storage for history purge
-- =============================================
CREATE PROCEDURE [dbo].[histERAFilePurgeList] 
	@ReferenceDate smalldatetime 
AS
BEGIN	
	SET NOCOUNT ON

	SELECT ERAFileName FROM hist_ERA WHERE ProcDate < @ReferenceDate
END
GO
	
GRANT EXECUTE ON [dbo].[histERAFilePurgeList] TO [IMNET] AS [dbo]
GO
