/*
 * Copyright 2009 McKesson Corporation and/or one of its subsidiaries.
 * All Rights Reserved.
 *
 * Use of this material is governed by a license agreement. This material
 * contains confidential, proprietary and trade secret information of
 * McKesson Information Solutions and is protected under United States and
 * international copyright and other intellectual property laws. Use,
 * disclosure, reproduction, modification, distribution, or storage
 * in a retrieval system in any form or by any means is prohibited without
 * the prior express written permission of McKesson Information Solutions.
 */

package com.mckesson.eig.config.dao;

/**
 * User-defined type used in mapping Oracle XMLTYPE to java String type and vice versa.
 *
 * @date   Mar 30, 2009
 * @since  HECM 2.0; Mar 30, 2009
 */
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import oracle.jdbc.OracleResultSet;
import oracle.sql.OPAQUE;
import oracle.xdb.XMLType;

import org.apache.commons.dbcp.DelegatingConnection;
import org.apache.commons.dbcp.DelegatingResultSet;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.UserType;
import org.jboss.resource.adapter.jdbc.WrappedConnection;
import org.jboss.resource.adapter.jdbc.WrappedResultSet;

public class HibernateXmlType implements UserType, Serializable {
    private static final long serialVersionUID = 2308230823023L;
    private static final Class RETURNED_CLASS = String.class;
    private static final int[] SQL_TYPES = new int[] { oracle.xdb.XMLType._SQL_TYPECODE };

    public int[] sqlTypes() {
        return SQL_TYPES;
    }

    public Class returnedClass() {
        return RETURNED_CLASS;
    }

    public int hashCode(Object obj) {
        return obj.hashCode();
    }

    public Object assemble(Serializable cached, Object owner) {
        try {
            return cached;
        } catch (Throwable t) {
            throw new HibernateException(
                    "Could not assemble String to Document", t);
        }
    }

    public Serializable disassemble(Object obj) {
        try {
            return (String) obj;
        } catch (Exception e) {
            throw new HibernateException(
                    "Could not disassemble Document to Serializable", e);
        }
    }

    public Object replace(Object orig, Object tar, Object owner) {
        return deepCopy(orig);
    }

    public boolean equals(Object arg0, Object arg1) {
        if (arg0 == null && arg1 == null) {
            return true;
        } else if (arg0 == null && arg1 != null) {
            return false;
        } else {
            return arg0.equals(arg1);
        }
    }

    public Object nullSafeGet(ResultSet rs, String[] names, Object arg2)
            throws SQLException {
        XMLType xmlType = null;
        String stringVal = null;
        try {
            OPAQUE op = null;
            OracleResultSet ors = null;
            WrappedResultSet w;
            if (rs instanceof OracleResultSet) {
                ors = (OracleResultSet) rs;
            } else if (rs instanceof WrappedResultSet) { // Jboss
                ors = (OracleResultSet) ((WrappedResultSet) rs)
                        .getUnderlyingResultSet();
            } else if (rs instanceof DelegatingResultSet) { // Junit
                ors = (OracleResultSet) (toUnderlyingResultSet(rs));
            } else {
                String string = "ResultSet needs to be of type OracleResultSet"
                        + " or org.apache.commons.dbcp.DelegatingResultSet";
                throw new UnsupportedOperationException(string);
            }
            op = ors.getOPAQUE(names[0]);
            if (op != null) {

                xmlType = XMLType.createXML(op);
            }
            if (xmlType != null) {

                stringVal = xmlType.getStringVal();
            }
        } finally {
            if (null != xmlType) {
                xmlType.close();
            }
        }
        return stringVal;
    }

    public void nullSafeSet(PreparedStatement st, Object value, int index)
            throws SQLException {
        try {
            XMLType xmlType = null;

            if (value != null) {

                Connection conn = st.getConnection();
                Connection realConnection = null;
                if (conn instanceof DelegatingConnection) {
                    realConnection = ((DelegatingConnection) conn).getDelegate();
                } else if (conn instanceof WrappedConnection) {
                    realConnection = ((WrappedConnection)conn).getUnderlyingConnection();
                } else {
                    realConnection = conn;
                }
                String domToString = (String) value;
                xmlType = new oracle.xdb.XMLType(realConnection, domToString);
            }
            st.setObject(index, xmlType);
        } catch (Exception e) {
            throw new SQLException(
                    "Could not convert the XML String for storage");
        }
    }

    private ResultSet toUnderlyingResultSet(ResultSet rset) {
        while (rset instanceof DelegatingResultSet) {
            rset = ((DelegatingResultSet) rset).getDelegate();
        }
        return rset;
    }

    public Object deepCopy(Object value) {
        if (value == null) {
            return null;
        }
        return value;
    }

    public boolean isMutable() {
        return false;
    }

	@Override
	public Object nullSafeGet(ResultSet rs, String[] names, SharedSessionContractImplementor session, Object owner)
			throws HibernateException, SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void nullSafeSet(PreparedStatement st, Object value, int index, SharedSessionContractImplementor session)
			throws HibernateException, SQLException {
		// TODO Auto-generated method stub
		
	}

}
