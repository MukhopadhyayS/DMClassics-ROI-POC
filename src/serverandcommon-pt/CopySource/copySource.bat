rem set common_src=M:\Code\DM-Classics\trunk\Common\src\CommonServer
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

xcopy "%Audit_src%/src/java" "../src/server-roi/common/audit/src/main/java" /D /E /C /R /H /I /K /Y /exclude:excludes.txt
xcopy "%Audit_src%/etc/com" "../src/server-roi/common/audit/src/main/resources/com" /D /E /C /R /H /I /K /Y /exclude:excludes.txt
xcopy "%Audit_src%/etc/config" "../src/server-roi/common/audit/src/main/resources/config" /D /E /C /R /H /I /K /Y /exclude:excludes.txt
xcopy "%Audit_src%/etc/meta-inf" "../src/server-roi/common/audit/src/main/resources/MATA-INF" /D /E /C /R /H /I /K /Y /exclude:excludes.txt
xcopy "%Audit_src%/src/test" "../src/server-roi/common/audit/src/test/java" /D /E /C /R /H /I /K /Y /exclude:excludes.txt

xcopy "%ConfigServer_src%/src/java" "../src/server-roi/common/config/src/main/java" /D /E /C /R /H /I /K /Y /exclude:excludes.txt
xcopy "%ConfigServer_src%/etc" "../src/server-roi/common/config/src/main/resources" /D /E /C /R /H /I /K /Y /exclude:excludes.txt
xcopy "%ConfigServer_src%/src/test" "../src/server-roi/common/config/src/test/java" /D /E /C /R /H /I /K /Y /exclude:excludes.txt

xcopy "%FileTransfer_src%/src" "../src/server-roi/common/filetransfer/src/main/java" /D /E /C /R /H /I /K /Y /exclude:excludes.txt
xcopy "%FileTransfert_src%/etc" "../src/server-roi/common/filetransfer/src/main/resources" /D /E /C /R /H /I /K /Y /exclude:excludes.txt
xcopy "%FileTransfer_src%/test" "../src/server-roi/common/filetransfer/src/test/java" /D /E /C /R /H /I /K /Y /exclude:excludes.txt

xcopy "%InUse_src%/src" "../src/server-roi/common/inuse/src/main/java" /D /E /C /R /H /I /K /Y /exclude:excludes.txt
xcopy "%InUse_src%/etc" "../src/server-roi/common/inuse/src/main/resources" /D /E /C /R /H /I /K /Y /exclude:excludes.txt
xcopy "%InUse_src%/test" "../src/server-roi/common/inuse/src/test/java" /D /E /C /R /H /I /K /Y /exclude:excludes.txt

xcopy "%Reports_src%/src" "../src/server-roi/common/reports/src/main/java" /D /E /C /R /H /I /K /Y /exclude:excludes.txt
xcopy "%Reports_src%/etc" "../src/server-roi/common/reports/src/main/resources" /D /E /C /R /H /I /K /Y /exclude:excludes.txt
xcopy "%Reports_src%/test" "../src/server-roi/common/reports/src/test/java" /D /E /C /R /H /I /K /Y /exclude:excludes.txt

xcopy "%Utils_src%/src" "../src/server-roi/common/utils/src/main/java" /D /E /C /R /H /I /K /Y /exclude:excludes.txt
xcopy "%Utils_src%/etc" "../src/server-roi/common/utils/src/main/resources" /D /E /C /R /H /I /K /Y /exclude:excludes.txt
xcopy "%Utils_src%/test" "../src/server-roi/common/utils/src/test/java" /D /E /C /R /H /I /K /Y /exclude:excludes.txt

xcopy "%WebServices_src%/src" "../src/server-roi/common/webservices/src/main/java" /D /E /C /R /H /I /K /Y /exclude:excludes.txt
xcopy "%WebServices_src%/etc" "../src/server-roi/common/webservices/src/main/resources" /D /E /C /R /H /I /K /Y /exclude:excludes.txt
xcopy "%WebServices_src%/test" "../src/server-roi/common/webservices/src/test/java" /D /E /C /R /H /I /K /Y /exclude:excludes.txt

xcopy "%Workflow_src%/src" "../src/server-workflow/workflow/src/main/java" /D /E /C /R /H /I /K /Y /exclude:excludes.txt
xcopy "%Workflow_src%/etc" "../src/server-workflow/workflow/src/main/resources" /D /E /C /R /H /I /K /Y /exclude:excludes.txt
xcopy "%Workflow_src%/test" "../src/server-workflow/workflow/src/test/java" /D /E /C /R /H /I /K /Y /exclude:excludes.txt
xcopy "%Workflow_src%/war" "../src/server-workflow/workflow-webapp/src/main/webapp" /D /E /C /R /H /I /K /Y /exclude:excludes.txt

rem set ROI_src=M:\Code\DM-Classics\trunk\ROI\src\server
set ROI_src=../../../src/Server

xcopy "%ROI_src%/src" "../src/server-roi/core/src/main/java" /D /E /C /R /H /I /K /Y /exclude:excludes.txt
xcopy "%ROI_src%/res" "../src/server-roi/core/src/main/resources" /D /E /C /R /H /I /K /Y /exclude:excludes.txt
xcopy "%ROI_src%/test/com" "../src/server-roi/core/src/test/java/com" /D /E /C /R /H /I /K /Y /exclude:excludes.txt
xcopy "%ROI_src%/test/resources" "../src/server-roi/core/src/test/resources" /D /E /C /R /H /I /K /Y /exclude:excludes.txt
rmdir /s /q "../src/server-roi/core/src/test/java/com/mckesson/eig/roi/ccd"

xcopy "%ROI_src%/WEB-INF" "../src/server-roi/webapp/src/main/webapp/WEB-INF" /D /E /C /R /H /I /K /Y /exclude:excludes.txt

xcopy "%ROI_src%/plug-in/ClinicalCcdPrivoder/src" "../src/server-roi/plugin/clinicalccdprovider/src/main/java" /D /E /C /R /H /I /K /Y /exclude:excludes.txt
xcopy "%ROI_src%/test/com/mckesson/eig/roi/ccd" "../src/server-roi/plugin/clinicalccdprovider/src/test/java/com/mckesson/eig/roi/ccd" /D /E /C /R /H /I /K /Y /exclude:excludes.txt
xcopy "%ROI_src%/plug-in/HPFCcdPrivoder/src" "../src/server-roi/plugin/hpfccdprovider/src/main/java" /D /E /C /R /H /I /K /Y /exclude:excludes.txt
xcopy "%ROI_src%/plug-in/ParagonCcdPrivoder/src" "../src/server-roi/plugin/paragonccdprovider/src/main/java" /D /E /C /R /H /I /K /Y /exclude:excludes.txt





