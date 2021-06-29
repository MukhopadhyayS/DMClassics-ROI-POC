/****************************************************************
Rem *  	Copyright © 2005 McKesson Corporation and/or one of its subsidiaries. 
Rem *		All rights reserved. 
Rem *
Rem * 	The copyright to the computer program(s) herein
Rem * 	is the property of McKessonHBOC. The program(s)
Rem * 	may be used and/or copied only with the written
Rem * 	permission of McKessonHBOC or in accordance with
Rem * 	the terms and conditions stipulated in the     
Rem * 	agreement/contract under which the program(s)
Rem * 	have been supplied.
Rem *************************************************************
Rem * Batch File Name: check_current_version.sql
Rem * Description:     	This script will check current HPF version.
Rem *			It also determine which serice pack to upgrade.
Rem *
Rem *		 Name			Type		Description
Rem *		 ---------		-------		----------------
Rem * 
Rem * 
Rem * 
Rem * 
Rem *    
Rem * 
Rem * Database Used:	master
Rem * 
Rem * NOTES: 
Rem * Revision History:
Rem *	Name		Date		ChangeType/Version/Track#
Rem *	---------	-------		--------------------------
Rem * 	
Rem * 	Joseph Chen	2/23/2007
Rem * 	
Rem *************************************************************/

set nocount on
go

declare @current_major_release char(5), --- example 10.00
@next_major_release char(5)--- example '11.0'

select @current_major_release='13.0.'
select @next_major_release='13.5.'


if not exists(select * from master.dbo.eprs_version where DB_NAME = 'HPF' and db_version like @current_major_release+'%') -- check to see if HPF is on current version
BEGIN
  select 'echo:Upgrade_Error:Your current HPF is not on version HPF 13.0!>>HPF13.15_Upgrade.log'
 
END

else

begin
select 'call upgrade_steps.cmd'+' '+ '%1 %2'
end


go
