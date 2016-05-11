USE [cabinet]
GO
/****** Object:  UserDefinedFunction [dbo].[IsZero]    Script Date: 1/27/2009 17:32:49 ******/
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[IsZero]') AND type in (N'FN', N'IF', N'TF', N'FS', N'FT'))
DROP FUNCTION [dbo].[IsZero]
GO
/****** Object:  UserDefinedFunction [dbo].[IsZero]    Script Date: 1/27/2009 17:32:49 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE function [dbo].[IsZero](@input int, @default int)
returns int
AS
begin
 if(@input = 0)
     return @default
 else
    return @input
return @input
end
GO
GRANT EXECUTE ON [dbo].[IsZero] TO [IMNET]
GO