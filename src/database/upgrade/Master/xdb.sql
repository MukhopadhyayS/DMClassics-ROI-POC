use master 
go

/*************************************************************************************************\
*  	Copyright © 2010 McKesson Corporation and/or one of its subsidiaries. 
*		All rights reserved. 
*
* 	The copyright to the computer program(s) herein
* 	is the property of MCKESSON. The program(s)
* 	may be used and/or copied only with the written
* 	permission of MCKESSON or in accordance with
* 	the terms and conditions stipulated in the     
* 	agreement/contract under which the program(s)
* 	have been supplied.
**************************************************************************************************
* Script Name: xdb.sql
*
* Function		: Set database chaining for only HPF databases
*
* Database		: master
*
* Revision History:
*	Name			Date		Changes/Version&Build/ClearQuest#
*	---------		-------		--------------------------------------------------
*	MD Meersma		01/20/2010	Enable database chaining only on HPF databases
*	MD Meersma		04/10/2013	Use DATABASEPROPERTYEX/sys.databases to only alter database setting only if required
\*************************************************************************************************/

sp_configure  'cross db ownership chaining', 0
go
reconfigure with override
go

IF (0 = DATABASEPROPERTYEX ('Audit', 'IsAnsiNullDefault'))
ALTER DATABASE [audit] SET ANSI_NULL_DEFAULT ON
GO
IF (1 = DATABASEPROPERTYEX ('Audit', 'IsAnsiNullsEnabled'))
ALTER DATABASE [audit] SET ANSI_NULLS OFF
GO
IF (0 = DATABASEPROPERTYEX ('Audit', 'IsAnsiPaddingEnabled'))
ALTER DATABASE [audit] SET ANSI_PADDING ON
GO
IF (1 = DATABASEPROPERTYEX ('Audit', 'IsAnsiWarningsEnabled'))
ALTER DATABASE [audit] SET ANSI_WARNINGS OFF
GO
IF (1 = DATABASEPROPERTYEX ('Audit', 'IsArithmeticAbortEnabled'))
ALTER DATABASE [audit] SET ARITHABORT OFF
GO
IF (1 = DATABASEPROPERTYEX ('Audit', 'IsAutoClose'))
ALTER DATABASE [audit] SET AUTO_CLOSE OFF
GO
IF (0 = DATABASEPROPERTYEX ('Audit', 'IsAutoCreateStatistics'))
ALTER DATABASE [audit] SET AUTO_CREATE_STATISTICS ON
GO
IF (1 = DATABASEPROPERTYEX ('Audit', 'IsAutoShrink'))
ALTER DATABASE [audit] SET AUTO_SHRINK OFF
GO
IF (0 = DATABASEPROPERTYEX ('Audit', 'IsAutoUpdateStatistics'))
ALTER DATABASE [audit] SET AUTO_UPDATE_STATISTICS ON
GO
IF (1 = DATABASEPROPERTYEX ('Audit', 'IsCloseCursorsOnCommitEnabled'))
ALTER DATABASE [audit] SET CURSOR_CLOSE_ON_COMMIT OFF
GO
IF (1 = DATABASEPROPERTYEX ('Audit', 'IsLocalCursorsDefault'))
ALTER DATABASE [audit] SET CURSOR_DEFAULT  GLOBAL
GO
IF (1 = DATABASEPROPERTYEX ('Audit', 'IsNullConcat'))
ALTER DATABASE [audit] SET CONCAT_NULL_YIELDS_NULL OFF
GO
IF (1 = DATABASEPROPERTYEX ('Audit', 'IsNumericRoundAbortEnabled'))
ALTER DATABASE [audit] SET NUMERIC_ROUNDABORT OFF
GO
IF (1 = DATABASEPROPERTYEX ('Audit', 'IsQuotedIdentifiersEnabled'))
ALTER DATABASE [audit] SET QUOTED_IDENTIFIER OFF
GO
IF (1 = DATABASEPROPERTYEX ('Audit', 'IsRecursiveTriggersEnabled'))
ALTER DATABASE [audit] SET RECURSIVE_TRIGGERS OFF
GO
if exists (select 1 from sys.databases where is_broker_enabled = 1 and name = 'audit')
ALTER DATABASE [audit] SET  DISABLE_BROKER
GO
if exists (select 1 from sys.databases where is_auto_update_stats_async_on = 1 and name = 'audit')
ALTER DATABASE [audit] SET AUTO_UPDATE_STATISTICS_ASYNC OFF
GO
if exists (select 1 from sys.databases where is_date_correlation_on = 1 and name = 'audit')
ALTER DATABASE [audit] SET DATE_CORRELATION_OPTIMIZATION OFF
GO
if exists (select 1 from sys.databases where is_trustworthy_on = 1 and name = 'audit')
ALTER DATABASE [audit] SET TRUSTWORTHY OFF
GO
if exists (select 1 from sys.databases where snapshot_isolation_state = 0 and name = 'audit')
ALTER DATABASE [audit] SET ALLOW_SNAPSHOT_ISOLATION ON
GO
if exists (select 1 from sys.databases where is_parameterization_forced = 1 and name = 'audit')
ALTER DATABASE [audit] SET PARAMETERIZATION SIMPLE
GO
if exists (select 1 from sys.databases where is_read_committed_snapshot_on = 0 and name = 'audit')
ALTER DATABASE [audit] SET READ_COMMITTED_SNAPSHOT ON
GO
IF ('READ_ONLY' = DATABASEPROPERTYEX ('Audit', 'Updateability'))
ALTER DATABASE [audit] SET  READ_WRITE
GO
if exists (select 1 from sys.databases where page_verify_option !=2 and name = 'audit')
ALTER DATABASE [audit] SET PAGE_VERIFY CHECKSUM
GO
if exists (select 1 from sys.databases where is_db_chaining_on = 0 and name = 'audit')
ALTER DATABASE [audit] SET DB_CHAINING ON
GO



IF (0 = DATABASEPROPERTYEX ('cabinet', 'IsAnsiNullDefault'))
ALTER DATABASE [cabinet] SET ANSI_NULL_DEFAULT ON
GO
IF (1 = DATABASEPROPERTYEX ('cabinet', 'IsAnsiNullsEnabled'))
ALTER DATABASE [cabinet] SET ANSI_NULLS OFF 
GO
IF (0 = DATABASEPROPERTYEX ('cabinet', 'IsAnsiPaddingEnabled'))
ALTER DATABASE [cabinet] SET ANSI_PADDING ON
GO
IF (1 = DATABASEPROPERTYEX ('cabinet', 'IsAnsiWarningsEnabled'))
ALTER DATABASE [cabinet] SET ANSI_WARNINGS OFF 
GO
IF (1 = DATABASEPROPERTYEX ('cabinet', 'IsArithmeticAbortEnabled'))
ALTER DATABASE [cabinet] SET ARITHABORT OFF 
GO
IF (1 = DATABASEPROPERTYEX ('cabinet', 'IsAutoClose'))
ALTER DATABASE [cabinet] SET AUTO_CLOSE OFF 
GO
IF (0 = DATABASEPROPERTYEX ('cabinet', 'IsAutoCreateStatistics'))
ALTER DATABASE [cabinet] SET AUTO_CREATE_STATISTICS ON 
GO
IF (1 = DATABASEPROPERTYEX ('cabinet', 'IsAutoShrink'))
ALTER DATABASE [cabinet] SET AUTO_SHRINK OFF 
GO
IF (0 = DATABASEPROPERTYEX ('cabinet', 'IsAutoUpdateStatistics'))
ALTER DATABASE [cabinet] SET AUTO_UPDATE_STATISTICS ON 
GO
IF (1 = DATABASEPROPERTYEX ('cabinet', 'IsCloseCursorsOnCommitEnabled'))
ALTER DATABASE [cabinet] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
IF (1 = DATABASEPROPERTYEX ('cabinet', 'IsLocalCursorsDefault'))
ALTER DATABASE [cabinet] SET CURSOR_DEFAULT  GLOBAL 
GO
IF (1 = DATABASEPROPERTYEX ('cabinet', 'IsNullConcat'))
ALTER DATABASE [cabinet] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
IF (1 = DATABASEPROPERTYEX ('cabinet', 'IsNumericRoundAbortEnabled'))
ALTER DATABASE [cabinet] SET NUMERIC_ROUNDABORT OFF 
GO
IF (1 = DATABASEPROPERTYEX ('cabinet', 'IsQuotedIdentifiersEnabled'))
ALTER DATABASE [cabinet] SET QUOTED_IDENTIFIER OFF 
GO
IF (1 = DATABASEPROPERTYEX ('cabinet', 'IsRecursiveTriggersEnabled'))
ALTER DATABASE [cabinet] SET RECURSIVE_TRIGGERS OFF 
GO
if exists (select 1 from sys.databases where is_broker_enabled = 1 and name = 'cabinet')
ALTER DATABASE [cabinet] SET  DISABLE_BROKER 
GO
if exists (select 1 from sys.databases where is_auto_update_stats_async_on = 1 and name = 'cabinet')
ALTER DATABASE [cabinet] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
if exists (select 1 from sys.databases where is_date_correlation_on = 1 and name = 'cabinet')
ALTER DATABASE [cabinet] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
if exists (select 1 from sys.databases where is_trustworthy_on = 1 and name = 'cabinet')
ALTER DATABASE [cabinet] SET TRUSTWORTHY OFF 
GO
if exists (select 1 from sys.databases where snapshot_isolation_state = 0 and name = 'cabinet')
ALTER DATABASE [cabinet] SET ALLOW_SNAPSHOT_ISOLATION ON
GO
if exists (select 1 from sys.databases where is_parameterization_forced = 1 and name = 'cabinet')
ALTER DATABASE [cabinet] SET PARAMETERIZATION SIMPLE 
GO
if exists (select 1 from sys.databases where is_read_committed_snapshot_on = 0 and name = 'cabinet')
ALTER DATABASE [cabinet] SET READ_COMMITTED_SNAPSHOT ON
GO
IF ('READ_ONLY' = DATABASEPROPERTYEX ('cabinet', 'Updateability'))
ALTER DATABASE [cabinet] SET  READ_WRITE 
GO
if exists (select 1 from sys.databases where page_verify_option !=2 and name = 'cabinet')
ALTER DATABASE [cabinet] SET PAGE_VERIFY CHECKSUM  
GO
if exists (select 1 from sys.databases where is_db_chaining_on = 0 and name = 'cabinet')
ALTER DATABASE [cabinet] SET DB_CHAINING ON 
GO



IF (0 = DATABASEPROPERTYEX ('eiwdata', 'IsAnsiNullDefault'))
ALTER DATABASE [eiwdata] SET ANSI_NULL_DEFAULT ON
GO
IF (1 = DATABASEPROPERTYEX ('eiwdata', 'IsAnsiNullsEnabled'))
ALTER DATABASE [eiwdata] SET ANSI_NULLS OFF 
GO
IF (0 = DATABASEPROPERTYEX ('eiwdata', 'IsAnsiPaddingEnabled'))
ALTER DATABASE [eiwdata] SET ANSI_PADDING ON
GO
IF (1 = DATABASEPROPERTYEX ('eiwdata', 'IsAnsiWarningsEnabled'))
ALTER DATABASE [eiwdata] SET ANSI_WARNINGS OFF 
GO
IF (1 = DATABASEPROPERTYEX ('eiwdata', 'IsArithmeticAbortEnabled'))
ALTER DATABASE [eiwdata] SET ARITHABORT OFF 
GO
IF (1 = DATABASEPROPERTYEX ('eiwdata', 'IsAutoClose'))
ALTER DATABASE [eiwdata] SET AUTO_CLOSE OFF 
GO
IF (0 = DATABASEPROPERTYEX ('eiwdata', 'IsAutoCreateStatistics'))
ALTER DATABASE [eiwdata] SET AUTO_CREATE_STATISTICS ON 
GO
IF (1 = DATABASEPROPERTYEX ('eiwdata', 'IsAutoShrink'))
ALTER DATABASE [eiwdata] SET AUTO_SHRINK OFF 
GO
IF (0 = DATABASEPROPERTYEX ('eiwdata', 'IsAutoUpdateStatistics'))
ALTER DATABASE [eiwdata] SET AUTO_UPDATE_STATISTICS ON 
GO
IF (1 = DATABASEPROPERTYEX ('eiwdata', 'IsCloseCursorsOnCommitEnabled'))
ALTER DATABASE [eiwdata] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
IF (1 = DATABASEPROPERTYEX ('eiwdata', 'IsLocalCursorsDefault'))
ALTER DATABASE [eiwdata] SET CURSOR_DEFAULT  GLOBAL 
GO
IF (1 = DATABASEPROPERTYEX ('eiwdata', 'IsNullConcat'))
ALTER DATABASE [eiwdata] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
IF (1 = DATABASEPROPERTYEX ('eiwdata', 'IsNumericRoundAbortEnabled'))
ALTER DATABASE [eiwdata] SET NUMERIC_ROUNDABORT OFF 
GO
IF (1 = DATABASEPROPERTYEX ('eiwdata', 'IsQuotedIdentifiersEnabled'))
ALTER DATABASE [eiwdata] SET QUOTED_IDENTIFIER OFF 
GO
IF (1 = DATABASEPROPERTYEX ('eiwdata', 'IsRecursiveTriggersEnabled'))
ALTER DATABASE [eiwdata] SET RECURSIVE_TRIGGERS ON 
GO
if exists (select 1 from sys.databases where is_broker_enabled = 1 and name = 'eiwdata')
ALTER DATABASE [eiwdata] SET  DISABLE_BROKER 
GO
if exists (select 1 from sys.databases where is_auto_update_stats_async_on = 1 and name = 'eiwdata')
ALTER DATABASE [eiwdata] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
if exists (select 1 from sys.databases where is_date_correlation_on = 1 and name = 'eiwdata')
ALTER DATABASE [eiwdata] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
if exists (select 1 from sys.databases where is_trustworthy_on = 1 and name = 'eiwdata')
ALTER DATABASE [eiwdata] SET TRUSTWORTHY OFF 
GO
if exists (select 1 from sys.databases where snapshot_isolation_state = 0 and name = 'eiwdata')
ALTER DATABASE [eiwdata] SET ALLOW_SNAPSHOT_ISOLATION ON
GO
if exists (select 1 from sys.databases where is_parameterization_forced = 1 and name = 'eiwdata')
ALTER DATABASE [eiwdata] SET PARAMETERIZATION SIMPLE 
GO
if exists (select 1 from sys.databases where is_read_committed_snapshot_on = 0 and name = 'eiwdata')
ALTER DATABASE [eiwdata] SET READ_COMMITTED_SNAPSHOT ON
GO
IF ('READ_ONLY' = DATABASEPROPERTYEX ('eiwdata', 'Updateability'))
ALTER DATABASE [eiwdata] SET  READ_WRITE 
GO
if exists (select 1 from sys.databases where page_verify_option !=2 and name = 'eiwdata')
ALTER DATABASE [eiwdata] SET PAGE_VERIFY CHECKSUM  
GO
if exists (select 1 from sys.databases where is_db_chaining_on = 0 and name = 'eiwdata')
ALTER DATABASE [eiwdata] SET DB_CHAINING ON 
GO



IF (0 = DATABASEPROPERTYEX ('his', 'IsAnsiNullDefault'))
ALTER DATABASE [his] SET ANSI_NULL_DEFAULT ON
GO
IF (1 = DATABASEPROPERTYEX ('his', 'IsAnsiNullsEnabled'))
ALTER DATABASE [his] SET ANSI_NULLS OFF 
GO
IF (0 = DATABASEPROPERTYEX ('his', 'IsAnsiPaddingEnabled'))
ALTER DATABASE [his] SET ANSI_PADDING ON
GO
IF (1 = DATABASEPROPERTYEX ('his', 'IsAnsiWarningsEnabled'))
ALTER DATABASE [his] SET ANSI_WARNINGS OFF 
GO
IF (1 = DATABASEPROPERTYEX ('his', 'IsArithmeticAbortEnabled'))
ALTER DATABASE [his] SET ARITHABORT OFF 
GO
IF (1 = DATABASEPROPERTYEX ('his', 'IsAutoClose'))
ALTER DATABASE [his] SET AUTO_CLOSE OFF 
GO
IF (0 = DATABASEPROPERTYEX ('his', 'IsAutoCreateStatistics'))
ALTER DATABASE [his] SET AUTO_CREATE_STATISTICS ON 
GO
IF (1 = DATABASEPROPERTYEX ('his', 'IsAutoShrink'))
ALTER DATABASE [his] SET AUTO_SHRINK OFF 
GO
IF (0 = DATABASEPROPERTYEX ('his', 'IsAutoUpdateStatistics'))
ALTER DATABASE [his] SET AUTO_UPDATE_STATISTICS ON 
GO
IF (1 = DATABASEPROPERTYEX ('his', 'IsCloseCursorsOnCommitEnabled'))
ALTER DATABASE [his] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
IF (1 = DATABASEPROPERTYEX ('his', 'IsLocalCursorsDefault'))
ALTER DATABASE [his] SET CURSOR_DEFAULT  GLOBAL 
GO
IF (1 = DATABASEPROPERTYEX ('his', 'IsNullConcat'))
ALTER DATABASE [his] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
IF (1 = DATABASEPROPERTYEX ('his', 'IsNumericRoundAbortEnabled'))
ALTER DATABASE [his] SET NUMERIC_ROUNDABORT OFF 
GO
IF (1 = DATABASEPROPERTYEX ('his', 'IsQuotedIdentifiersEnabled'))
ALTER DATABASE [his] SET QUOTED_IDENTIFIER OFF 
GO
IF (1 = DATABASEPROPERTYEX ('his', 'IsRecursiveTriggersEnabled'))
ALTER DATABASE [his] SET RECURSIVE_TRIGGERS OFF 
GO
if exists (select 1 from sys.databases where is_broker_enabled = 1 and name = 'his')
ALTER DATABASE [his] SET  DISABLE_BROKER 
GO
if exists (select 1 from sys.databases where is_auto_update_stats_async_on = 1 and name = 'his')
ALTER DATABASE [his] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
if exists (select 1 from sys.databases where is_date_correlation_on = 1 and name = 'his')
ALTER DATABASE [his] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
if exists (select 1 from sys.databases where is_trustworthy_on = 1 and name = 'his')
ALTER DATABASE [his] SET TRUSTWORTHY OFF 
GO
if exists (select 1 from sys.databases where snapshot_isolation_state = 0 and name = 'his')
ALTER DATABASE [his] SET ALLOW_SNAPSHOT_ISOLATION ON
GO
if exists (select 1 from sys.databases where is_parameterization_forced = 1 and name = 'his')
ALTER DATABASE [his] SET PARAMETERIZATION SIMPLE 
GO
if exists (select 1 from sys.databases where is_read_committed_snapshot_on = 0 and name = 'his')
ALTER DATABASE [his] SET READ_COMMITTED_SNAPSHOT ON
GO
IF ('READ_ONLY' = DATABASEPROPERTYEX ('his', 'Updateability'))
ALTER DATABASE [his] SET  READ_WRITE 
GO
if exists (select 1 from sys.databases where page_verify_option !=2 and name = 'his')
ALTER DATABASE [his] SET PAGE_VERIFY CHECKSUM  
GO
if exists (select 1 from sys.databases where is_db_chaining_on = 0 and name = 'his')
ALTER DATABASE [his] SET DB_CHAINING ON 
GO



IF (0 = DATABASEPROPERTYEX ('MCK_HPF', 'IsAnsiNullDefault'))
ALTER DATABASE [MCK_HPF] SET ANSI_NULL_DEFAULT ON
GO
IF (1 = DATABASEPROPERTYEX ('MCK_HPF', 'IsAnsiNullsEnabled'))
ALTER DATABASE [MCK_HPF] SET ANSI_NULLS OFF 
GO
IF (0 = DATABASEPROPERTYEX ('MCK_HPF', 'IsAnsiPaddingEnabled'))
ALTER DATABASE [MCK_HPF] SET ANSI_PADDING ON
GO
IF (1 = DATABASEPROPERTYEX ('MCK_HPF', 'IsAnsiWarningsEnabled'))
ALTER DATABASE [MCK_HPF] SET ANSI_WARNINGS OFF 
GO
IF (1 = DATABASEPROPERTYEX ('MCK_HPF', 'IsArithmeticAbortEnabled'))
ALTER DATABASE [MCK_HPF] SET ARITHABORT OFF 
GO
IF (1 = DATABASEPROPERTYEX ('MCK_HPF', 'IsAutoClose'))
ALTER DATABASE [MCK_HPF] SET AUTO_CLOSE OFF 
GO
IF (0 = DATABASEPROPERTYEX ('MCK_HPF', 'IsAutoCreateStatistics'))
ALTER DATABASE [MCK_HPF] SET AUTO_CREATE_STATISTICS ON 
GO
IF (1 = DATABASEPROPERTYEX ('MCK_HPF', 'IsAutoShrink'))
ALTER DATABASE [MCK_HPF] SET AUTO_SHRINK OFF 
GO
IF (0 = DATABASEPROPERTYEX ('MCK_HPF', 'IsAutoUpdateStatistics'))
ALTER DATABASE [MCK_HPF] SET AUTO_UPDATE_STATISTICS ON 
GO
IF (1 = DATABASEPROPERTYEX ('MCK_HPF', 'IsCloseCursorsOnCommitEnabled'))
ALTER DATABASE [MCK_HPF] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
IF (1 = DATABASEPROPERTYEX ('MCK_HPF', 'IsLocalCursorsDefault'))
ALTER DATABASE [MCK_HPF] SET CURSOR_DEFAULT  GLOBAL 
GO
IF (1 = DATABASEPROPERTYEX ('MCK_HPF', 'IsNullConcat'))
ALTER DATABASE [MCK_HPF] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
IF (1 = DATABASEPROPERTYEX ('MCK_HPF', 'IsNumericRoundAbortEnabled'))
ALTER DATABASE [MCK_HPF] SET NUMERIC_ROUNDABORT OFF 
GO
IF (1 = DATABASEPROPERTYEX ('MCK_HPF', 'IsQuotedIdentifiersEnabled'))
ALTER DATABASE [MCK_HPF] SET QUOTED_IDENTIFIER OFF 
GO
IF (1 = DATABASEPROPERTYEX ('MCK_HPF', 'IsRecursiveTriggersEnabled'))
ALTER DATABASE [MCK_HPF] SET RECURSIVE_TRIGGERS OFF 
GO
if exists (select 1 from sys.databases where is_broker_enabled = 1 and name = 'MCK_HPF')
ALTER DATABASE [MCK_HPF] SET  DISABLE_BROKER
GO
if exists (select 1 from sys.databases where is_auto_update_stats_async_on = 1 and name = 'MCK_HPF')
ALTER DATABASE [MCK_HPF] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
if exists (select 1 from sys.databases where is_date_correlation_on = 1 and name = 'MCK_HPF')
ALTER DATABASE [MCK_HPF] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
if exists (select 1 from sys.databases where is_trustworthy_on = 1 and name = 'MCK_HPF')
ALTER DATABASE [MCK_HPF] SET TRUSTWORTHY OFF 
GO
if exists (select 1 from sys.databases where snapshot_isolation_state = 0 and name = 'MCK_HPF')
ALTER DATABASE [MCK_HPF] SET ALLOW_SNAPSHOT_ISOLATION ON
GO
if exists (select 1 from sys.databases where is_parameterization_forced = 1 and name = 'MCK_HPF')
ALTER DATABASE [MCK_HPF] SET PARAMETERIZATION SIMPLE 
GO
if exists (select 1 from sys.databases where is_read_committed_snapshot_on = 0 and name = 'MCK_HPF')
ALTER DATABASE [MCK_HPF] SET READ_COMMITTED_SNAPSHOT ON
GO
IF ('READ_ONLY' = DATABASEPROPERTYEX ('MCK_HPF', 'Updateability'))
ALTER DATABASE [MCK_HPF] SET  READ_WRITE 
GO
if exists (select 1 from sys.databases where page_verify_option !=2 and name = 'MCK_HPF')
ALTER DATABASE [MCK_HPF] SET PAGE_VERIFY CHECKSUM  
GO
if exists (select 1 from sys.databases where is_db_chaining_on = 1 and name = 'MCK_HPF')
ALTER DATABASE [MCK_HPF] SET DB_CHAINING OFF 
GO




IF (0 = DATABASEPROPERTYEX ('wfe', 'IsAnsiNullDefault'))
ALTER DATABASE [wfe] SET ANSI_NULL_DEFAULT ON
GO
IF (1 = DATABASEPROPERTYEX ('wfe', 'IsAnsiNullsEnabled'))
ALTER DATABASE [wfe] SET ANSI_NULLS OFF 
GO
IF (0 = DATABASEPROPERTYEX ('wfe', 'IsAnsiPaddingEnabled'))
ALTER DATABASE [wfe] SET ANSI_PADDING ON
GO
IF (1 = DATABASEPROPERTYEX ('wfe', 'IsAnsiWarningsEnabled'))
ALTER DATABASE [wfe] SET ANSI_WARNINGS OFF 
GO
IF (1 = DATABASEPROPERTYEX ('wfe', 'IsArithmeticAbortEnabled'))
ALTER DATABASE [wfe] SET ARITHABORT OFF 
GO
IF (1 = DATABASEPROPERTYEX ('wfe', 'IsAutoClose'))
ALTER DATABASE [wfe] SET AUTO_CLOSE OFF 
GO
IF (0 = DATABASEPROPERTYEX ('wfe', 'IsAutoCreateStatistics'))
ALTER DATABASE [wfe] SET AUTO_CREATE_STATISTICS ON 
GO
IF (1 = DATABASEPROPERTYEX ('wfe', 'IsAutoShrink'))
ALTER DATABASE [wfe] SET AUTO_SHRINK OFF 
GO
IF (0 = DATABASEPROPERTYEX ('wfe', 'IsAutoUpdateStatistics'))
ALTER DATABASE [wfe] SET AUTO_UPDATE_STATISTICS ON 
GO
IF (1 = DATABASEPROPERTYEX ('wfe', 'IsCloseCursorsOnCommitEnabled'))
ALTER DATABASE [wfe] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
IF (1 = DATABASEPROPERTYEX ('wfe', 'IsLocalCursorsDefault'))
ALTER DATABASE [wfe] SET CURSOR_DEFAULT  GLOBAL 
GO
IF (1 = DATABASEPROPERTYEX ('wfe', 'IsNullConcat'))
ALTER DATABASE [wfe] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
IF (1 = DATABASEPROPERTYEX ('wfe', 'IsNumericRoundAbortEnabled'))
ALTER DATABASE [wfe] SET NUMERIC_ROUNDABORT OFF 
GO
IF (1 = DATABASEPROPERTYEX ('wfe', 'IsQuotedIdentifiersEnabled'))
ALTER DATABASE [wfe] SET QUOTED_IDENTIFIER OFF 
GO
IF (1 = DATABASEPROPERTYEX ('wfe', 'IsRecursiveTriggersEnabled'))
ALTER DATABASE [wfe] SET RECURSIVE_TRIGGERS OFF 
GO
if exists (select 1 from sys.databases where is_broker_enabled = 1 and name = 'wfe')
ALTER DATABASE [wfe] SET  DISABLE_BROKER 
GO
if exists (select 1 from sys.databases where is_auto_update_stats_async_on = 1 and name = 'wfe')
ALTER DATABASE [wfe] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
if exists (select 1 from sys.databases where is_date_correlation_on = 1 and name = 'wfe')
ALTER DATABASE [wfe] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
if exists (select 1 from sys.databases where is_trustworthy_on = 1 and name = 'wfe')
ALTER DATABASE [wfe] SET TRUSTWORTHY OFF 
GO
if exists (select 1 from sys.databases where snapshot_isolation_state = 0 and name = 'wfe')
ALTER DATABASE [wfe] SET ALLOW_SNAPSHOT_ISOLATION ON
GO
if exists (select 1 from sys.databases where is_parameterization_forced = 1 and name = 'wfe')
ALTER DATABASE [wfe] SET PARAMETERIZATION SIMPLE 
GO
if exists (select 1 from sys.databases where is_read_committed_snapshot_on = 0 and name = 'wfe')
ALTER DATABASE [wfe] SET READ_COMMITTED_SNAPSHOT ON
GO
IF ('READ_ONLY' = DATABASEPROPERTYEX ('wfe', 'Updateability'))
ALTER DATABASE [wfe] SET  READ_WRITE 
GO
if exists (select 1 from sys.databases where page_verify_option !=2 and name = 'wfe')
ALTER DATABASE [wfe] SET PAGE_VERIFY CHECKSUM  
GO
if exists (select 1 from sys.databases where is_db_chaining_on = 0 and name = 'wfe')
ALTER DATABASE [wfe] SET DB_CHAINING ON 
GO
