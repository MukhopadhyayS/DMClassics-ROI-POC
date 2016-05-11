@echo off
SETLOCAL

IF EXIST "..\jre\bin\java.exe" (
	set JAVA_EXE=..\jre\bin\java.exe
) ELSE (
	IF "x%JAVA_HOME%" == "x" (
		echo JAVA_HOME is not set. Set JAVA_HOME to the directory of you local JDK and restart conversion tool.
		goto END
	) else (
		set JAVA_EXE="%JAVA_HOME%\bin\java.exe"
	)
)
%JAVA_EXE% -cp ..\common\roi-conversion-tool.jar -Dlog4j.configuration=log4j-preconversion.properties com.mckesson.eig.roi.preconversion.PreConversionMain

:END
