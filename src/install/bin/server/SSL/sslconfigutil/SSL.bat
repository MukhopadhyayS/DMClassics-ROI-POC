SET ROI_JAVA_HOME=%JAVA_HOME_SET%
SET PATH=%PATH%;%ROI_JAVA_HOME%\Bin;
DEL /S hpf.ssl_cerificate.keystore
%ROI_JAVA_HOME%\bin\keytool.exe -genkey -alias mykey -keypass changeit -keyalg RSA -keystore hpf.ssl_cerificate.keystore -storepass changeit -dname "CN=%COMPUTERNAME%, OU=EIG, O=MPT, L=Alpharetta, ST=GA, C=US" -validity 999
%ROI_JAVA_HOME%\bin\keytool.exe -import -noprompt -alias mykey -keystore hpf.ssl_cerificate.keystore -storepass changeit -trustcacerts -file "C:\Temp\junk.cer"
%ROI_JAVA_HOME%\bin\keytool.exe -noprompt -import -alias mykey -storepass changeit -keystore %ROI_JAVA_HOME%\jre\lib\security\cacerts -file "C:\Temp\junk.cer"
