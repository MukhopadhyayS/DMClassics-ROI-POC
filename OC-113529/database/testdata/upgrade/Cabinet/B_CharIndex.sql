USE [cabinet]
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[CHARINDEX2]') AND type in (N'FN'))
DROP FUNCTION [dbo].[CHARINDEX2]
USE [cabinet]
GO
/****** Object:  UserDefinedFunction [dbo].[CHARINDEX2]    Script Date: 12/10/2008 12:53:05 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
Create FUNCTION [dbo].[CHARINDEX2]
(
@TargetStr varchar(8000), 
@SearchedStr varchar(8000), 
@Occurrence int
)
RETURNS int
as
begin
declare @pos int, @counter int, @ret int
set @pos = CHARINDEX(@TargetStr, @SearchedStr,1)
set @counter = 1
if @Occurrence = 0 set @ret = 1
else
begin
if @Occurrence = 1 set @ret =  @pos
else
begin
while (@counter < @Occurrence) 
begin
select @ret = CHARINDEX(@TargetStr, @SearchedStr, @pos + 1)

set @counter = @counter + 1
if @ret <> 0 
set @pos = @ret
end
end
end
RETURN(@ret)
end
GO
GRANT EXECUTE ON [dbo].[CHARINDEX2] TO [IMNET]
GO