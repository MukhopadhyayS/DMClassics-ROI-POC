USE [cabinet]
GO

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[histEOBInsert]') AND type in (N'P'))
DROP PROCEDURE [dbo].[histEOBInsert]
GO

CREATE PROCEDURE [dbo].[histEOBInsert]
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
	@ERAID int
)
AS

SET NOCOUNT OFF

INSERT INTO [hist_EOB] ([RefId], [CLP01], [ISACtrl], [STCtrl], [GroupCtrl], [LX], [Status], [PtName], [Encounter], [MRN], [Facility], [NPI], [ERAID]) 
VALUES (@RefId, @CLP01, @ISACtrl, @STCtrl, @GroupCtrl, @LX, @Status, @PtName, @Encounter, @MRN, @Facility, @NPI, @ERAID)
	
SELECT RefId, CLP01, ISACtrl, STCtrl, GroupCtrl, LX, Status, PtName, Encounter, MRN, Facility, NPI, ERAID FROM hist_EOB WHERE (RefId = @RefId)
GO
	
GRANT EXECUTE ON [dbo].[histEOBInsert] TO [IMNET] AS [dbo]
GO
