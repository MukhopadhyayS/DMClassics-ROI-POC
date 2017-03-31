CALL mvn install:install-file -DgroupId=com.mckesson.eig.roi -DartifactId=testws -Dversion=1.0.0 -Dpackaging=jar -Dfile=repository/testws.jar
CALL mvn install:install-file -DgroupId=com.mckesson.eig.roi -DartifactId=iws-ha -Dversion=1.0.0 -Dpackaging=jar -Dfile=repository/iws-ha.jar
CALL mvn install:install-file -DgroupId=com.mckesson.eig.roi -DartifactId=hpf.stub.config -Dversion=1.0.0 -Dpackaging=jar -Dfile=repository/hpf.stub.config.jar
CALL mvn install:install-file -DgroupId=com.mckesson.eig.roi -DartifactId=hpf-signIn-stub -Dversion=1.0.0 -Dpackaging=jar -Dfile=repository/hpf-signIn-stub.jar
CALL mvn install:install-file -DgroupId=com.mckesson.eig.roi -DartifactId=ha-security -Dversion=1.0.0 -Dpackaging=jar -Dfile=repository/ha-security.jar
CALL mvn install:install-file -DgroupId=com.mckesson.eig.roi -DartifactId=ha-orm -Dversion=1.0.0 -Dpackaging=jar -Dfile=repository/ha-orm.jar
CALL mvn install:install-file -DgroupId=com.mckesson.eig.roi -DartifactId=ha-core -Dversion=1.0.0 -Dpackaging=jar -Dfile=repository/ha-core.jar
CALL mvn install:install-file -DgroupId=com.mckesson.eig.roi -DartifactId=ccdccr -Dversion=1.0.0 -Dpackaging=jar -Dfile=repository/ccdccr.jar
