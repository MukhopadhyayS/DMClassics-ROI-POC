set common_src=../../../../common/src/commonServer

set Alert_src=%common_src%/Alert
set Audit_src=%common_src%/Audit
set ConfigServer_src=%common_src%/ConfigServer
set FileTransfer_src=%common_src%/FileTransfer
set InUse_src=%common_src%/InUse
set Reports_src=%common_src%/Reports
set Utils_src=%common_src%/Utils
set WebServices_src=%common_src%/WebServices
set Workflow_src=%common_src%/Workflow

rem xcopy common
xcopy "%Alert_src%/src" "../src/server-alert/alert/src/main/java" /D /E /C /R /H /I /K /Y /exclude:excludes.txt
xcopy "%Alert_src%/etc" "../src/server-alert/alert/src/main/resources" /D /E /C /R /H /I /K /Y /exclude:excludes.txt
xcopy "%Alert_src%/test" "../src/server-alert/alert/src/test/java" /D /E /C /R /H /I /K /Y /exclude:excludes.txt
xcopy "%Alert_src%/war" "../src/server-alert/alert-webapp/src/main/webapp" /D /E /C /R /H /I /K /Y /exclude:excludes.txt

xcopy "%Audit_src%/src/java" "../src/server-roi/audit/src/main/java" /D /E /C /R /H /I /K /Y /exclude:excludes.txt
xcopy "%Audit_src%/etc" "../src/server-roi/audit/src/main/resources" /D /E /C /R /H /I /K /Y /exclude:excludes.txt
xcopy "%Audit_src%/src/test" "../src/server-roi/audit/src/test/java" /D /E /C /R /H /I /K /Y /exclude:excludes.txt

xcopy "%ConfigServer_src%/src/java" "../src/server-roi/config/src/main/java" /D /E /C /R /H /I /K /Y /exclude:excludes.txt
xcopy "%ConfigServer_src%/etc" "../src/server-roi/config/src/main/resources" /D /E /C /R /H /I /K /Y /exclude:excludes.txt
xcopy "%ConfigServer_src%/src/test" "../src/server-roi/config/src/test/java" /D /E /C /R /H /I /K /Y /exclude:excludes.txt

xcopy "%FileTransfer_src%/src" "../src/server-roi/filetransfer/src/main/java" /D /E /C /R /H /I /K /Y /exclude:excludes.txt
xcopy "%FileTransfert_src%/etc" "../src/server-roi/filetransfer/src/main/resources" /D /E /C /R /H /I /K /Y /exclude:excludes.txt
xcopy "%FileTransfer_src%/test" "../src/server-roi/filetransfer/src/test/java" /D /E /C /R /H /I /K /Y /exclude:excludes.txt

xcopy "%InUse_src%/src" "../src/server-roi/inuse/src/main/java" /D /E /C /R /H /I /K /Y /exclude:excludes.txt
xcopy "%InUse_src%/etc" "../src/server-roi/inuse/src/main/resources" /D /E /C /R /H /I /K /Y /exclude:excludes.txt
xcopy "%InUse_src%/test" "../src/server-roi/inuse/src/test/java" /D /E /C /R /H /I /K /Y /exclude:excludes.txt

xcopy "%Reports_src%/src" "../src/server-roi/reports/src/main/java" /D /E /C /R /H /I /K /Y /exclude:excludes.txt
xcopy "%Reports_src%/etc" "../src/server-roi/reports/src/main/resources" /D /E /C /R /H /I /K /Y /exclude:excludes.txt
xcopy "%Reports_src%/test" "../src/server-roi/reports/src/test/java" /D /E /C /R /H /I /K /Y /exclude:excludes.txt

xcopy "%Utils_src%/src" "../src/server-roi/utils/src/main/java" /D /E /C /R /H /I /K /Y /exclude:excludes.txt
xcopy "%Utils_src%/etc" "../src/server-roi/utils/src/main/resources" /D /E /C /R /H /I /K /Y /exclude:excludes.txt
xcopy "%Utils_src%/test" "../src/server-roi/utils/src/test/java" /D /E /C /R /H /I /K /Y /exclude:excludes.txt

xcopy "%WebServices_src%/src" "../src/server-roi/webservices/src/main/java" /D /E /C /R /H /I /K /Y /exclude:excludes.txt
xcopy "%WebServices_src%/etc" "../src/server-roi/webservices/src/main/resources" /D /E /C /R /H /I /K /Y /exclude:excludes.txt
xcopy "%WebServices_src%/test" "../src/server-roi/webservices/src/test/java" /D /E /C /R /H /I /K /Y /exclude:excludes.txt

xcopy "%Workflow_src%/src" "../src/server-workflow/workflow/src/main/java" /D /E /C /R /H /I /K /Y /exclude:excludes.txt
xcopy "%Workflow_src%/etc" "../src/server-workflow/workflow/src/main/resources" /D /E /C /R /H /I /K /Y /exclude:excludes.txt
xcopy "%Workflow_src%/test" "../src/server-workflow/workflow/src/test/java" /D /E /C /R /H /I /K /Y /exclude:excludes.txt
xcopy "%Workflow_src%/war" "../src/server-workflow/workflow-webapp/src/main/webapp" /D /E /C /R /H /I /K /Y /exclude:excludes.txt

set ROI_src=../../../src/Server

xcopy "%ROI_src%/src" "../src/server-roi/core/src/main/java" /D /E /C /R /H /I /K /Y /exclude:excludes.txt
xcopy "%ROI_src%/res" "../src/server-roi/core/src/main/resources" /D /E /C /R /H /I /K /Y /exclude:excludes.txt
xcopy "%ROI_src%/test/com" "../src/server-roi/core/src/test/java/com" /D /E /C /R /H /I /K /Y /exclude:excludes.txt
xcopy "%ROI_src%/test/resources" "../src/server-roi/core/src/test/resources" /D /E /C /R /H /I /K /Y /exclude:excludes.txt

xcopy "%ROI_src%/WEB-INF" "../src/server-roi/webapp/src/main/webapp/WEB-INF" /D /E /C /R /H /I /K /Y /exclude:excludes.txt

set plugin_src=../../../src/Server/plug-in

xcopy "%plugin_src%/ClinicalCcdPrivoder/src" "../src/server-roi/plugin/clinicalccdprovider/src/main/java" /D /E /C /R /H /I /K /Y /exclude:excludes.txt
xcopy "%plugin_src%/HPFCcdPrivoder/src" "../src/server-roi/plugin/hpfccdprovider/src/main/java" /D /E /C /R /H /I /K /Y /exclude:excludes.txt
xcopy "%plugin_src%/ParagonCcdPrivoder/src" "../src/server-roi/plugin/paragonccdprovider/src/main/java" /D /E /C /R /H /I /K /Y /exclude:excludes.txt





