package com.mckesson.eig.roi.dao;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

import org.apache.commons.configuration.ConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.mckesson.dm.security.util.AESCryptograph;
import com.mckesson.dm.core.common.exceptions.ErrorCode;


import com.mckesson.dm.security.util.KeyStoreUtilities;
import com.mckesson.dm.security.util.Constant;

import com.mckesson.dm.security.util.SensitiveData;
import com.mckesson.eig.roi.common.config.BootstrapConfiguration;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;


public class DBDataSource extends HikariDataSource {

    /** The bootstrap configuration. */

    private BootstrapConfiguration _bootstrapConfiguration;
    private static final String CHARSET = "UTF-8";
    final static Logger LOGGER = LoggerFactory.getLogger(DBDataSource.class);
    private static byte[] KE;
    String pw = "";

    public BootstrapConfiguration getBootstrapConfiguration() {
        return _bootstrapConfiguration;
    }

    public void setBootstrapConfiguration(BootstrapConfiguration bootstrapConfiguration) {
        _bootstrapConfiguration = bootstrapConfiguration;
    }


    public DBDataSource(HikariConfig configuration) {
        super();
        _bootstrapConfiguration = BootstrapConfiguration.getInstance();
        try {
            initialize(configuration);
        } catch (Exception e) {
            LOGGER.error("Failed to setup DBDataSource", e);
            throw new DaoException(e, ErrorCode.DB_CONNECTION_FAILURE,
                    "Error while setting up the datasource setup DBDataSource");
        }
        configuration.validate();
        configuration.copyStateTo(this);
    }



    private void initialize(HikariConfig configuration) throws Exception{
        pw = _bootstrapConfiguration.getDbPassword();
        Constant.setCONFIG_KEYSTORE_PASS("CKS");
        Constant.setCONFIG_KEY_STORE_DIRECTORY("../../security");
        Constant.setCONFIG_KEY_STORE_FILENAME("ConfigKS.keystore");

        SensitiveData sd = KeyStoreUtilities.getKeystorePasswordFromEnvVariable(Constant.getCONFIG_KEYSTORE_PASS());
        if(sd.isEmpty()){
            LOGGER.error("Error while retrieving the keystore password");
            throw new DaoException(ErrorCode.DAO_SECURITY_EXCEPTION,"Error while retrieving the keystore password");
        }
        if(Files.notExists(Paths.get(Constant.getCONFIG_KEY_STORE_DIRECTORY() + "/" +Constant.getCONFIG_KEY_STORE_FILENAME()))){
            KeyStoreUtilities.initializeKS(Constant.getCONFIG_KEY_STORE_DIRECTORY() ,Constant.getCONFIG_KEY_STORE_FILENAME(),sd);
        }
        KE =KeyStoreUtilities.getKeyFromKeyStore(Constant.getCONFIG_KEY_STORE_DIRECTORY() ,Constant.getCONFIG_KEY_STORE_FILENAME(), sd,KeyStoreUtilities.DB_ENC_KEY ).getClearTextByteArray();
        sd.cleanup();
        encryptDecryptProperties(KE);
        configuration.setPassword(pw);
    }

    public void encryptDecryptProperties(byte[] decodedKeyBytes) throws Exception{
        if(pw.startsWith("ENC_")){

            byte[] decodedEncryptedPwString = Base64.getDecoder().decode(pw.substring(4));
            byte[] decryptedPw = AESCryptograph.aesDecryptData(decodedKeyBytes,decodedEncryptedPwString);
            byte [] decodedDecPw = Base64.getDecoder().decode(decryptedPw);
            pw = new String(decodedDecPw);

        } else{
            byte[] encodedPw = Base64.getEncoder().encode(pw.getBytes(CHARSET));
            byte[] encPwBytes =  AESCryptograph.aesEncryptData(decodedKeyBytes,encodedPw);
            String encodedEncPwString= Base64.getEncoder().encodeToString(encPwBytes);
            saveProperties(encodedEncPwString);

        }
    }

    private void saveProperties(String encodedEncPwString)
            throws ConfigurationException {
        _bootstrapConfiguration.setDbPassword(encodedEncPwString);
        _bootstrapConfiguration.savePropsConfig();
    }

}
