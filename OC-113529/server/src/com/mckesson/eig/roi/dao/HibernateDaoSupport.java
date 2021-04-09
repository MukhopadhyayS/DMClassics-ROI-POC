package com.mckesson.eig.roi.dao;

import java.io.Serializable;

import org.hibernate.metadata.ClassMetadata;
import org.hibernate.type.AbstractStandardBasicType;
import org.hibernate.type.Type;

import com.mckesson.eig.iws.orm.Dao;
import com.mckesson.eig.iws.orm.ObjectId;
import com.mckesson.eig.iws.orm.ObjectIdSupport;;

public abstract class HibernateDaoSupport extends org.springframework.orm.hibernate5.support.HibernateDaoSupport implements Dao {
  protected ClassMetadata getClassMetadata() {
    return getSessionFactory().getClassMetadata(getObjectType());
  }
  
  public Object createObject() {
    try {
      return getObjectType().newInstance();
    } catch (InstantiationException e) {
      throw new RuntimeException("Cannot instantiate " + getObjectType().getName(), e);
    } catch (IllegalAccessException e) {
      throw new RuntimeException("Cannot instantiate " + getObjectType().getName(), e);
    } 
  }
  
  public Object createObject(Class objectClass) {
    return createObject();
  }
  
  public void deleteObject(Object object) {
    if (object != null) {
      verifyCompatibleObjectType(object.getClass());
      getHibernateTemplate().delete(object);
    } 
  }
  
  public void flush() {
    getHibernateTemplate().flush();
  }
  
  public Object getObject(Serializable id) {
    return getHibernateTemplate().get(getObjectType(), id);
  }
  
  public Object getObject(Class objectClass, Serializable id) {
    return getObject(id);
  }
  
  public Object getObject(ObjectId objectId) {
    verifyCompatibleObjectType(objectId.getType());
    return getObject(objectId.getSerializableId());
  }
  
  public ObjectId getObjectId(String stringId) {
    ObjectId objectId = ObjectIdSupport.valueOf(this, stringId);
    verifyCompatibleObjectType(objectId.getType());
    return objectId;
  }
  
  public ObjectId getObjectId(Object object) {
    verifyCompatibleObjectType(object.getClass());
    return (ObjectId)new ObjectIdSupport(getObjectType(), getSerializableId(object));
  }
  
  public boolean isValidObjectId(String objectIdStr) {
    return (getObjectId(objectIdStr) != null);
  }
  
  public boolean isValidObjectId(ObjectId objectId) {
    verifyCompatibleObjectType(objectId.getType());
    return true;
  }
  
  public abstract Class getObjectType();
  
  public Object getObjectVersion(Object object) {
    ClassMetadata metadata = getClassMetadata();
    verifyCompatibleObjectType(object.getClass());
    return metadata.getVersion(object);
  }
  
  public Serializable getSerializableId(Object object) {
    ClassMetadata metadata = getClassMetadata();
    verifyCompatibleObjectType(object.getClass());
    return metadata.getIdentifier(object);
  }
  
  public Serializable getSerializableId(String id) {
    ClassMetadata metadata = getClassMetadata();
    Type idType = metadata.getIdentifierType();
    if (idType instanceof AbstractStandardBasicType)
      return (Serializable)((AbstractStandardBasicType)idType).fromStringValue(id); 
    throw new IllegalStateException("Cannot automatically create " + idType.getClass().getName() + " Serializable id from a String");
  }
  
  public Serializable getSerializableId(Class objectClass, String id) {
    return getSerializableId(id);
  }
  
  public boolean isObjectVersioned() {
    return getClassMetadata().isVersioned();
  }
  
  public void saveObject(Object object) {
    verifyCompatibleObjectType(object.getClass());
    getHibernateTemplate().save(object);
  }
  
  public void updateObject(Object object) {
    verifyCompatibleObjectType(object.getClass());
    getHibernateTemplate().update(object);
  }
  
  protected void verifyCompatibleObjectType(Class<?> type) {
    if (!getObjectType().isAssignableFrom(type))
      throw new IllegalArgumentException("Cannot use " + getClass().getName() + " to manage " + type.getName() + " objects"); 
  }
}
