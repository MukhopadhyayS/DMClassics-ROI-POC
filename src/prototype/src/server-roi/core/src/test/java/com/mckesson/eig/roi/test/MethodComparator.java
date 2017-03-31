package com.mckesson.eig.roi.test;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.lang.reflect.Method;
import java.util.Comparator;

import org.apache.log4j.Logger;
import org.junit.Ignore;

public class MethodComparator<T> implements Comparator<T> {

    private static final Logger logger = Logger.getLogger(MethodComparator.class.getName());

    private static final char[] METHOD_SEPARATORS = {1, 5, 7, 8, 11};

    private MethodComparator() {
    }

//    public static MethodComparator<FrameworkMethod> getFrameworkMethodComparatorForJUnit4() {
//        return new MethodComparator<FrameworkMethod>();
//    }

    public static MethodComparator<Method> getMethodComparatorForJUnit3() {
        return new MethodComparator<Method>();
    }

    @Override
    public int compare(T o1, T o2) {
        try {
            final MethodPosition methodPosition1 = this.getIndexOfMethodPosition(o1);
            final MethodPosition methodPosition2 = this.getIndexOfMethodPosition(o2);
            return methodPosition1.compareTo(methodPosition2);
        }catch(Exception e) {
            e.printStackTrace();
        }
        return 0;

    }

    private MethodPosition getIndexOfMethodPosition(final Object method) {
//        if (method instanceof FrameworkMethod) {
//            return this.getIndexOfMethodPosition(method);
//        } else
            if (method instanceof Method) {
            return this.getIndexOfMethodPosition((Method) method);
        } else {
            logger.error("getIndexOfMethodPosition(): Given object is not a method! Object class is "
                    + method.getClass().getCanonicalName());
            return new NullMethodPosition();
        }
    }

//    private MethodPosition getIndexOfMethodPosition(final FrameworkMethod frameworkMethod) {
//        return getIndexOfMethodPosition(frameworkMethod.getMethod());
//    }

    private MethodPosition getIndexOfMethodPosition(final Method method) {
        final Class aClass = method.getDeclaringClass();
        if (method.getAnnotation(Ignore.class) == null) {
            return getIndexOfMethodPosition(aClass, method.getName());
        } else {
            logger.debug("getIndexOfMethodPosition(): Method is annotated as Ignored: " + method.getName()
                    + " in class: " + aClass.getCanonicalName());
            return new NullMethodPosition();
        }
    }

    private MethodPosition getIndexOfMethodPosition(final Class aClass, final String methodName) {

        MethodPosition methodPosition = getMethodPosition(aClass, methodName);
        if (methodPosition instanceof NullMethodPosition) {
            logger.debug("getIndexOfMethodPosition(): Trying to use another method separator for method: " + methodName);
            System.out.println("getIndexOfMethodPosition(): Trying to use another method separator for method: " + methodName);
        } else {
            return methodPosition;
        }
        return new NullMethodPosition();
    }

    private MethodPosition getMethodPosition(final Class aClass, final String methodName) {

        final InputStream inputStream = aClass.getResourceAsStream(aClass.getSimpleName() + ".class");
        final LineNumberReader lineNumberReader = new LineNumberReader(new InputStreamReader(inputStream));
        try {
            try {
                String line;
                while ((line = lineNumberReader.readLine()) != null) {

                    while (line.contains(methodName)) {

                        int lineNumber = lineNumberReader.getLineNumber();
                        int index = line.indexOf(methodName);
                        if (line.length() == (index + methodName.length())) {
                            return new MethodPosition(lineNumber, index);
                        }

                        String substring = line.substring((index + methodName.length()), (index + methodName.length()+ 1));
                        byte[] bytes = substring.getBytes();
                        if (bytes[0] < 32) {
                            return new MethodPosition(lineNumber, index);
                        }

                        line = line.substring(index + methodName.length());
                    }
                }
            } finally {
                lineNumberReader.close();
            }
        } catch (IOException e) {
            logger.error("getIndexOfMethodPosition(): Error while reading byte code of class " + aClass.getCanonicalName(), e);
            return new NullMethodPosition();
        }
        logger.warn("getIndexOfMethodPosition(): Can't find method " + methodName + " in byte code of class " + aClass.getCanonicalName());
        return new NullMethodPosition();
    }

    private static class MethodPosition implements Comparable<MethodPosition> {
        private final Integer lineNumber;
        private final Integer indexInLine;

        public MethodPosition(int lineNumber, int indexInLine) {
            this.lineNumber = lineNumber;
            this.indexInLine = indexInLine;
        }

        @Override
        public int compareTo(MethodPosition o) {

            // If line numbers are equal, then compare by indexes in this line.
            if (this.lineNumber.equals(o.lineNumber)) {
                return this.indexInLine.compareTo(o.indexInLine);
            } else {
                return this.lineNumber.compareTo(o.lineNumber);
            }
        }

        @Override
        public String toString() {
            return new StringBuffer().append("LineNumber:").append(lineNumber).append(",Index:").append(indexInLine).toString();
        }
    }

    private static class NullMethodPosition extends MethodPosition {
        public NullMethodPosition() {
            super(-1, -1);
        }
    }
}
