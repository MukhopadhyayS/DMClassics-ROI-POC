package com.mckesson.eig.roi.utils;

import org.apache.commons.lang.StringUtils;
import org.identityconnectors.common.security.GuardedString;


/**
 * This class will secure any String object and prevent Heap inspection vulnerability.
 *
 */
public final class SecureString {

    private GuardedString secretText;

    public SecureString(String secretText) {
        setSecretText(secretText);
    }

    /**
     * Returns clear form of secured text
     *
     * @return String
     */
    public String getClearText() {
        StringBuilder builder = new StringBuilder();
        this.secretText.access(builder::append);
        return builder.toString();
    }

    /**
     * Ewrurns secured string
     *
     * @return GuardedString
     */
    public GuardedString getSecretText() {
        return secretText;
    }

    private void setSecretText(String secretText) {
        if (StringUtils.isNotBlank(secretText)) {
            this.secretText = new GuardedString(secretText.toCharArray());
        } else {
            this.secretText = new GuardedString(new char[]{});
        }
        this.secretText.makeReadOnly();
    }
}
