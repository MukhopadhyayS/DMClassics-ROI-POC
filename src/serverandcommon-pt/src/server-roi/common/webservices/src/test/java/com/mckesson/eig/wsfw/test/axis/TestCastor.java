package com.mckesson.eig.wsfw.test.axis;

import java.io.IOException;

import javax.xml.namespace.QName;

import junit.framework.TestCase;

import org.apache.axis.encoding.DeserializationContext;
import org.apache.axis.encoding.SerializationContext;
import org.apache.axis.wsdl.fromJava.Types;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.mckesson.eig.wsfw.axis.CastorDeserializer;
import com.mckesson.eig.wsfw.axis.CastorSerializer;
/**
 * Validates the methods in CastorSerializer,CastorDeserializer.Positive
 * scenarios for these methods are tested through the service .This tests all
 * the possible exceptions.
 */
public class TestCastor extends TestCase {

    /**
     * Holds the instance of <code>CastorSerializer</code>.
     */
    private CastorSerializer _serializer;

    /**
     * Holds the instance of <code>CastorDeserializer</code>.
     */
    private CastorDeserializer _deserialiazer;

    /**
     * It sets the data essential for this testcase.We use
     * <code>UnitSpringInitialisation</code> for initialising the Spring
     * config file.(CasstorSerialiser needs CastorContext bean to get
     * initialized).
     * 
     * @throws Exception
     *             general exception
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
        UnitSpringInitialization.init();
    }

    /**
     * Validates the <code>getMechanismType</code> and tests its return value.
     */
    public void testgetMechanismType() {
        _serializer = new CastorSerializer();
        assertEquals("Axis SAX Mechanism", _serializer.getMechanismType());
    }

    /**
     * checks whether <code>WriteSchema</code> returns <code>null</code> or
     * not.
     */
    public void testWriteSchema() {
        _serializer = new CastorSerializer();
        Class javatype = null;
        Types types = null;
        try {
            assertNull(_serializer.writeSchema(javatype, types));
        } catch (Exception e) {
            e.getMessage();
        }
    }

    /**
     * Tests Marshal Exception by passing the Object value as null.
     */
    public void testMarshalException() {
        _serializer = new CastorSerializer();
        QName qname = new QName("{urn.eig.mckesson}house");
        Attributes attributes = null;
        Object value = null;
        SerializationContext context = null;
        try {
            _serializer.serialize(qname, attributes, value, context);
            fail();
        } catch (IOException e) {
            e.getMessage();
        }
    }

    /**
     * Tests the validation exception by passing the <code>QName</code> as
     * null.
     */
    public void testValidationException() {
        _serializer = new CastorSerializer();
        QName qname = null;
        Attributes attributes = null;
        Object value = this.getClass();
        SerializationContext context = null;
        try {
            _serializer.serialize(qname, attributes, value, context);
            fail();
        } catch (IOException e) {
            e.getMessage();
        }
    }

    /**
     * Tests the general exception while deserialising.
     */
    public void testException() {
        QName qname = new QName("{urn.eig.mckesson}house");
        String nameSpace = "urn.eig.mckesson.com";
        String localName = "room";
        DeserializationContext context = null;
        _deserialiazer = new CastorDeserializer(this.getClass(), qname);
        try {
            _deserialiazer.onEndElement(nameSpace, localName, context);
            fail();
        } catch (SAXException e) {
            e.getMessage();
        }

    }
}
