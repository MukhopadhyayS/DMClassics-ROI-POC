USE [cabinet]
GO
/****** Object:  UserDefinedFunction [dbo].[SplitUtil]    Script Date: 11/14/2008 20:32:49 ******/
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[SplitUtil]') AND type in (N'FN', N'IF', N'TF', N'FS', N'FT'))
DROP FUNCTION [dbo].[SplitUtil]
GO
/****** Object:  UserDefinedFunction [dbo].[SplitUtil]    Script Date: 11/14/2008 17:20:17 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE function [dbo].[SplitUtil](
 @String nvarchar (MAX),
 @Delimiter nvarchar (10)
 )
returns @ValueTable table ([Value] nvarchar(MAX))
begin
 declare @NextString nvarchar(MAX)
 declare @Pos int
 declare @NextPos int
 declare @CommaCheck nvarchar(1)

 --Initialize
 set @NextString = ''
 set @CommaCheck = right(@String,1)

 --Check for trailing Comma, if not exists, INSERT
 --if (@CommaCheck <> @Delimiter )
 set @String = @String + @Delimiter

 --Get position of first Comma
 set @Pos = charindex(@Delimiter,@String)
 set @NextPos = 1

 --Loop while there is still a comma in the String of levels
 while (@pos <>  0)
 begin
  set @NextString = substring(@String,1,@Pos - 1)

  insert into @ValueTable ( [Value]) Values (LTRIM(RTRIM(@NextString)))

  set @String = substring(@String,@pos +1,len(@String))

  set @NextPos = @Pos
  set @pos  = charindex(@Delimiter,@String)
 end

 return
end
GO
GRANT SELECT ON [dbo].[SplitUtil] TO [IMNET]
GO