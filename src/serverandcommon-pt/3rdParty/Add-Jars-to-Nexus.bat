CALL mvn install:install-file -DgroupId=com.mckesson.eig.roi -DartifactId=testws -Dversion=1.0.0 -Dpackaging=jar -Dfile=jars/testws.jar
CALL mvn install:install-file -DgroupId=com.mckesson.eig.roi -DartifactId=iws-ha -Dversion=1.0.0 -Dpackaging=jar -Dfile=jars/iws-ha.jar
CALL mvn install:install-file -DgroupId=com.mckesson.eig.roi -DartifactId=hpf.stub.config -Dversion=1.0.0 -Dpackaging=jar -Dfile=jars/hpf.stub.config.jar
CALL mvn install:install-file -DgroupId=com.mckesson.eig.roi -DartifactId=hpf-signIn-stub -Dversion=1.0.0 -Dpackaging=jar -Dfile=jars/hpf-signIn-stub.jar
REM CALL mvn install:install-file -DgroupId=com.mckesson.eig.roi -DartifactId=ha-security -Dversion=1.0.0 -Dpackaging=jar -Dfile=jars/ha-security.jar
REM CALL mvn install:install-file -DgroupId=com.mckesson.eig.roi -DartifactId=ha-orm -Dversion=1.0.0 -Dpackaging=jar -Dfile=jars/ha-orm.jar
REM CALL mvn install:install-file -DgroupId=com.mckesson.eig.roi -DartifactId=ha-core -Dversion=1.0.0 -Dpackaging=jar -Dfile=jars/ha-core.jar
REM CALL mvn install:install-file -DgroupId=com.mckesson.eig.roi -DartifactId=ccdccr -Dversion=1.0.0 -Dpackaging=jar -Dfile=jars/ccdccr.jar
CALL mvn install:install-file -DgroupId=com.mckesson.eig.roi -DartifactId=Snow.Snowbnd -Dversion=10.0.1 -Dpackaging=jar -Dfile=jars/snow-10.0.1.jar
CALL mvn install:install-file -DgroupId=com.mckesson.eig.roi -DartifactId=org.apache.commons-dbcp -Dversion=1.2.1 -Dpackaging=jar -Dfile=jars/commons-dbcp-1.2.1.jar
CALL mvn install:install-file -DgroupId=com.mckesson.eig.roi -DartifactId=jboss-jdbc-wrapper -Dversion=5.1.0.GA -Dpackaging=jar -Dfile=jars/jboss-common-jdbc-wrapper.jar
CALL mvn install:install-file -DgroupId=com.mckesson.eig.roi -DartifactId=oracle.xdb -Dversion=1.0.0 -Dpackaging=jar -Dfile=jars/xdb.jar
REM CALL mvn deploy:deploy-file -DgroupId=com.mckesson.eig.roi -DartifactId=oracle.xdb -Dversion=1.0.0 -Dpackaging=jar -Dfile=jars/xdb.jar -DrepositoryId=nexus -Durl=http://emidevenv1001.eigqclab.mckesson.com:8085/nexus/content/repositories/releases
