set common_src=../../common/src/commonServer

set Alert_src=%common_src%/Alert
set Audit_src=%common_src%/Audit
set ConfigServer_src=%common_src%/ConfigServer
set FileTransfer_src=%common_src%/FileTransfer
set InUse_src=%common_src%/InUse
set Reports_src=%common_src%/Reports
set Utils_src=%common_src%/Utils
set WebServices_src=%common_src%/WebServices

rem xcopy common
xcopy "%Alert_src%/src" "server-alert/alert/src/main/java" /D /E /C /R /H /I /K /Y
xcopy "%Alert_src%/etc" "server-alert/alert/src/main/resources" /D /E /C /R /H /I /K /Y
xcopy "%Alert_src%/test" "server-alert/alert/src/test/java" /D /E /C /R /H /I /K /Y

xcopy "%Alert_src%/war" "server-alert/alert-webapp/src/main/webapp" /D /E /C /R /H /I /K /Y

xcopy "%Audit_src%/src/java" "server-roi/common/audit/src/main/java" /D /E /C /R /H /I /K /Y
xcopy "%Audit_src%/etc" "server-roi/common/audit/src/main/resources" /D /E /C /R /H /I /K /Y
xcopy "%Audit_src%/src/test" "server-roi/common/audit/src/test/java" /D /E /C /R /H /I /K /Y

xcopy "%ConfigServer_src%/src/java" "server-roi/common/config/src/main/java" /D /E /C /R /H /I /K /Y
xcopy "%ConfigServer_src%/etc" "server-roi/common/config/src/main/resources" /D /E /C /R /H /I /K /Y
xcopy "%ConfigServer_src%/src/test" "server-roi/common/config/src/test/java" /D /E /C /R /H /I /K /Y

xcopy "%FileTransfer_src%/src" "server-roi/common/filetransfer/src/main/java" /D /E /C /R /H /I /K /Y
xcopy "%FileTransfert_src%/etc" "server-roi/common/filetransfer/src/main/resources" /D /E /C /R /H /I /K /Y
xcopy "%FileTransfer_src%/test" "server-roi/common/filetransfer/src/test/java" /D /E /C /R /H /I /K /Y

xcopy "%InUse_src%/src" "server-roi/common/inuse/src/main/java" /D /E /C /R /H /I /K /Y
xcopy "%InUse_src%/etc" "server-roi/common/inuse/src/main/resources" /D /E /C /R /H /I /K /Y
xcopy "%InUse_src%/test" "server-roi/common/inuse/src/test/java" /D /E /C /R /H /I /K /Y

xcopy "%Reports_src%/src" "server-roi/common/reports/src/main/java" /D /E /C /R /H /I /K /Y
xcopy "%Reports_src%/etc" "server-roi/common/reports/src/main/resources" /D /E /C /R /H /I /K /Y
xcopy "%Reports_src%/test" "server-roi/common/reports/src/test/java" /D /E /C /R /H /I /K /Y

xcopy "%Utils_src%/src" "server-roi/common/utils/src/main/java" /D /E /C /R /H /I /K /Y
xcopy "%Utils_src%/etc" "server-roi/common/utils/src/main/resources" /D /E /C /R /H /I /K /Y
xcopy "%Utils_src%/test" "server-roi/common/utils/src/test/java" /D /E /C /R /H /I /K /Y

xcopy "%WebServices_src%/src" "server-roi/common/webservices/src/main/java" /D /E /C /R /H /I /K /Y
xcopy "%WebServices_src%/etc" "server-roi/common/webservices/src/main/resources" /D /E /C /R /H /I /K /Y
xcopy "%WebServices_src%/test" "server-roi/common/webservices/src/test/java" /D /E /C /R /H /I /K /Y


set ROI_src=../Server

xcopy "%ROI_src%/src" "server-roi/core/src/main/java" /D /E /C /R /H /I /K /Y
xcopy "%ROI_src%/res" "server-roi/core/src/main/resources" /D /E /C /R /H /I /K /Y
xcopy "%ROI_src%/test/com" "server-roi/core/src/test/java/com" /D /E /C /R /H /I /K /Y
xcopy "%ROI_src%/test/resources" "server-roi/core/src/test/resources" /D /E /C /R /H /I /K /Y

xcopy "%ROI_src%/WEB-INF" "server-roi/webapp/src/main/webapp/WEB-INF" /D /E /C /R /H /I /K /Y

set plugin_src=../Server/plug-in

xcopy "%plugin_src%/ClinicalCcdPrivoder/src" "server-roi/plugin/clinicalccdprovider/src/main/jav" /D /E /C /R /H /I /K /Y
xcopy "%plugin_src%/HPFCcdPrivoder/src" "server-roi/plugin/hpfccdprovider/src/main/java" /D /E /C /R /H /I /K /Y
xcopy "%plugin_src%/ParagonCcdPrivoder/src" "server-roi/plugin/paragonccdprovider/src/main/java" /D /E /C /R /H /I /K /Y





