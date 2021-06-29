rem 
rem Changes the Current directory
CD %~dp0
set CLASSPATH=dom4j-1.6.1.jar;jaxen-1.1-beta-5.jar;
java -cp *; sslconfigutil.SSLConfigUtil