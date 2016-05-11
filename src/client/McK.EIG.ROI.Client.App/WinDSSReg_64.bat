REM
REM BEGIN-COPYRIGHT-COMMENT Do not remove or modify this line!
REM
REM Copyright © 2010 McKesson Corporation and/or one of its subsidiaries. All Rights Reserved.
REM Use of this software and related documentation is governed by a license agreement.
REM This material contains confidential, proprietary and trade secret information of
REM McKesson Information Solutions and is protected under United States
REM and international copyright and other intellectual property laws.
REM Use, disclosure, reproduction, modification, distribution, or storage
REM in a retrieval system in any form or by any means is prohibited without the
REM prior express written permission of McKesson Information Solutions.
REM
REM END-COPYRIGHT-COMMENT  Do not remove or modify this line!
REM

Rem *		 Name			Type		Description
Rem *		 ---------		-------		----------------
Rem * Params In:

rem *		  %1 			String		server name
rem *		  %2			String		user name (must be epr)
rem *		  %3			String		user pwd

reg add "HKLM\SOFTWARE\Wow6432Node\IMNET" /f
reg add "HKLM\SOFTWARE\Wow6432Node\IMNET\EPRS" /f
reg add "HKLM\SOFTWARE\Wow6432Node\IMNET\EPRS\AIS" /v Port /t REG_DWORD /d 00000384 /f
reg add "HKLM\SOFTWARE\Wow6432Node\IMNET\EPRS\AIS" /v User /t REG_SZ /d AIS /f
reg add "HKLM\SOFTWARE\Wow6432Node\IMNET\EPRS\AIS" /v Password /t REG_SZ /d C6EC76852DD12F08 /f
reg add "HKLM\SOFTWARE\Wow6432Node\IMNET\EPRS\AIS" /v PreferredAIS /t REG_SZ /d "" /f

reg add "HKLM\SOFTWARE\Wow6432Node\IMNET\EPRS\AUDIT" /v DBServer /t REG_SZ /d %1 /f
reg add "HKLM\SOFTWARE\Wow6432Node\IMNET\EPRS\AUDIT" /v DBName /t REG_SZ /d Audit /f


reg add "HKLM\SOFTWARE\Wow6432Node\IMNET\EPRS\CABINET" /v DBServer /t REG_SZ /d %1 /f
reg add "HKLM\SOFTWARE\Wow6432Node\IMNET\EPRS\CABINET" /v DBName /t REG_SZ /d Cabinet /f

reg add "HKLM\SOFTWARE\Wow6432Node\IMNET\EPRS\Database" /v User /t REG_SZ /d epr /f
reg add "HKLM\SOFTWARE\Wow6432Node\IMNET\EPRS\Database" /v pswd /t REG_SZ /d 7D4B6B1787DF55A3 /f

reg add "HKLM\SOFTWARE\Wow6432Node\IMNET\EPRS\HIS" /v DBServer /t REG_SZ /d %1 /f
reg add "HKLM\SOFTWARE\Wow6432Node\IMNET\EPRS\HIS" /v DBName /t REG_SZ /d HIS /f

reg add "HKLM\SOFTWARE\Wow6432Node\IMNET\EPRS\IE" /f

reg add "HKLM\SOFTWARE\Wow6432Node\IMNET\EPRS\IE\Database" /v User /t REG_SZ /d %2 /f
reg add "HKLM\SOFTWARE\Wow6432Node\IMNET\EPRS\IE\Database" /v pswd /t REG_SZ /d %3 /f

reg add "HKLM\SOFTWARE\Wow6432Node\IMNET\EPRS\DSS" /f
reg add "HKLM\SOFTWARE\Wow6432Node\IMNET\EPRS\DSS" /v LastImage /t REG_SZ /d "" /f
reg add "HKLM\SOFTWARE\Wow6432Node\IMNET\EPRS\DSS" /v LastLogDocId /t REG_SZ /d "" /f
reg add "HKLM\SOFTWARE\Wow6432Node\IMNET\EPRS\DSS" /v LastVolume /t REG_SZ /d "" /f
reg add "HKLM\SOFTWARE\Wow6432Node\IMNET\EPRS\DSS" /v LastStartAddress /t REG_SZ /d "" /f
reg add "HKLM\SOFTWARE\Wow6432Node\IMNET\EPRS\DSS" /v LastNumPages /t REG_SZ /d "" /f
reg add "HKLM\SOFTWARE\Wow6432Node\IMNET\EPRS\DSS" /v LastEnhance /t REG_SZ /d "" /f

reg add "HKLM\SOFTWARE\Wow6432Node\IMNET\EPRS\DSS\Windows" /f
reg add "HKLM\SOFTWARE\Wow6432Node\IMNET\EPRS\DSS\Windows\1" /f
reg add "HKLM\SOFTWARE\Wow6432Node\IMNET\EPRS\DSS\Windows\1" /v ToolBarVisible /t REG_SZ /d "" /f
reg add "HKLM\SOFTWARE\Wow6432Node\IMNET\EPRS\DSS\Windows\1" /v StatusBarVisible /t REG_SZ /d "" /f
reg add "HKLM\SOFTWARE\Wow6432Node\IMNET\EPRS\DSS\Windows\1" /v AnnoToolBarVisible /t REG_SZ /d "" /f
reg add "HKLM\SOFTWARE\Wow6432Node\IMNET\EPRS\DSS\Windows\1" /v DragScroll /t REG_SZ /d "" /f
reg add "HKLM\SOFTWARE\Wow6432Node\IMNET\EPRS\DSS\Windows\1" /v Position /t REG_SZ /d "" /f
reg add "HKLM\SOFTWARE\Wow6432Node\IMNET\EPRS\DSS\Windows\1" /v ScaleToGray /t REG_SZ /d "" /f
reg add "HKLM\SOFTWARE\Wow6432Node\IMNET\EPRS\DSS\Windows\1" /v AlwaysOnTop /t REG_SZ /d "" /f
reg add "HKLM\SOFTWARE\Wow6432Node\IMNET\EPRS\DSS\Windows\1" /v ZoomFactor /t REG_SZ /d "" /f
reg add "HKLM\SOFTWARE\Wow6432Node\IMNET\EPRS\DSS\Windows\1" /v ZoomFit /t REG_SZ /d "" /f
reg add "HKLM\SOFTWARE\Wow6432Node\IMNET\EPRS\DSS\Windows\1" /v Rotation /t REG_SZ /d "" /f
reg add "HKLM\SOFTWARE\Wow6432Node\IMNET\EPRS\DSS\Windows\1" /v Invert /t REG_SZ /d "" /f
reg add "HKLM\SOFTWARE\Wow6432Node\IMNET\EPRS\DSS\Windows\1" /v ExtStatusBarVisible /t REG_SZ /d "" /f

reg add "HKLM\SOFTWARE\Wow6432Node\IMNET\INSTALLEDAPPS" /f
reg add "HKLM\SOFTWARE\Wow6432Node\IMNET\INSTALLEDAPPS" /v InstallDir /t REG_SZ /d "C:\\imnet" /f
reg add "HKLM\SOFTWARE\Wow6432Node\IMNET\INSTALLEDAPPS" /v Version /t REG_SZ /d "17.0.1" /f

regsvr32 /s EPRSCryptography.dll

REGINI "RegPermWinDSS.txt"

If not exist "C:\imnet\Common" MD C:\imnet\Common
copy McKesson.eig.WebServiceLogConfig.xml C:\imnet\Common
copy McKesson.eig.WinDssLogConfig.xml C:\imnet\Common