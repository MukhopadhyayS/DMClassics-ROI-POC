package com.mckesson.eig.workflow.process.api.hibernate;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import oracle.jdbc.OracleResultSet;
import oracle.sql.OPAQUE;
import oracle.xdb.XMLType;

import org.apache.commons.dbcp.DelegatingConnection;
import org.apache.commons.dbcp.DelegatingResultSet;
import org.hibernate.HibernateException;
import org.hibernate.usertype.UserType;
import org.jboss.resource.adapter.jdbc.WrappedConnection;
import org.jboss.resource.adapter.jdbc.WrappedResultSet;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class HibernateXmlType implements UserType, Serializable {
    private static final long serialVersionUID = 2308230823023L;
    private static final Class RETURNED_CLASS = Document.class;
    private static final int[] SQL_TYPES = new int[]{oracle.xdb.XMLType._SQL_TYPECODE};

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
            return HibernateXmlType.stringToDom((String) cached);
        } catch (Throwable t) {
            throw new HibernateException(
                    "Could not assemble String to Document", t);
        }
    }

    public Serializable disassemble(Object obj) {
        try {
            return HibernateXmlType.domToString((Document) obj);
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
        Document doc = null;
        try {
            OPAQUE op = null;
            OracleResultSet ors = null;

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
            // Handle null for xmltype
            doc = xmlType.getDOM();
        } finally {
            if (null != xmlType) {
                xmlType.close();
            }
        }
        return doc;
    }

    public void nullSafeSet(PreparedStatement st, Object value, int index)
            throws SQLException {
        try {
            XMLType xmlType = null;

            if (value != null) {

                Connection conn = st.getConnection();
                Connection realConnection = null;
                if (conn instanceof DelegatingConnection) {
                    realConnection = ((DelegatingConnection) conn)
                            .getDelegate();
                } else if (conn instanceof WrappedConnection) {
                    realConnection = ((WrappedConnection) conn)
                            .getUnderlyingConnection();
                } else {
                    realConnection = conn;
                }
                String domToString = HibernateXmlType
                        .domToString((Document) value);
                xmlType = new oracle.xdb.XMLType(realConnection, domToString);
            }
            st.setObject(index, xmlType);
        } catch (Exception e) {
            throw new SQLException(
                    "Could not convert Document to String for storage");
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
        return (Document) ((Document) value).cloneNode(true);
    }

    public boolean isMutable() {
        return false;
    }

    protected static String domToString(Document document)
            throws TransformerException {
        TransformerFactory tFactory = TransformerFactory.newInstance();
        Transformer transformer = tFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        DOMSource source = new DOMSource(document);
        StringWriter sw = new StringWriter();
        StreamResult result = new StreamResult(sw);
        transformer.transform(source, result);

        return sw.toString();
    }

    protected static Document stringToDom(String xmlSource)
            throws SAXException, ParserConfigurationException, IOException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.parse(new ByteArrayInputStream(xmlSource
                .getBytes("UTF-8")));
    }
}
