USE [cabinet]
GO
/****** Object:  UserDefinedFunction [dbo].[ReplaceString]    Script Date: 01/28/2009 12:34:03 ******/
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[ReplaceString]') AND type in (N'FN', N'IF', N'TF', N'FS', N'FT'))
DROP FUNCTION [dbo].[ReplaceString]
GO

/****** Object:  UserDefinedFunction [dbo].[ReplaceString]    Script Date: 01/28/2009 12:34:03 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE function [dbo].[ReplaceString](@inputString varchar(max),
									  @CompareString varchar(max),
									  @replacewith varchar(max))
returns varchar(max)
AS
begin
 if(@inputString = @CompareString)
     return @replacewith
 else
    return @inputString
return @inputString
end
GO
GRANT EXECUTE ON [dbo].[ReplaceString] TO [IMNET]
GO
