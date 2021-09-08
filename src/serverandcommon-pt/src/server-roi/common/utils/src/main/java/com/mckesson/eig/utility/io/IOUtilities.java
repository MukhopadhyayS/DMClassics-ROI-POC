/**
 * Copyright © 2010 McKesson Corporation and/or one of its subsidiaries.
 * All rights reserved.
 *
 * Use of this material is governed by a license agreement. This material
 * contains confidential, proprietary and trade secret information of
 * McKesson Corporation and is protected under United States and
 * international copyright and other intellectual property laws. Use,
 * disclosure, reproduction, modification, distribution, or storage
 * in a retrieval system in any form or by any means is prohibited without
 * the prior express written permission of McKesson Corporation.
 */

package com.mckesson.eig.utility.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.io.Reader;
import java.io.Writer;

import com.mckesson.dm.core.common.logging.OCLogger;
import com.mckesson.eig.utility.util.UtilitiesException;

/**
 * Provides methods which represents output and input stream of bytes.
 */
public final class IOUtilities {

    /**
     * Foe <code>Log</code> messaging.
     */
    private static final OCLogger LOG = new OCLogger(IOUtilities.class);

    /**
     * Buffer size.
     */
    private static final int BUFFER_SIZE = 4096;

    /**
     * string used for getting the line separation.
     */
    public static final String NEW_LINE = System.getProperty("line.separator", "\n");

    /**
     * No argument Constructor.
     */
    private IOUtilities() {
    }

    /**
     * Closes the Closable thereby releasing any system resources associated with
     * the stream. It also logs error message also.
     *
     * @param closable
     *            instance of <code>Closable</code>.
     */
    public static void close(Closeable closable) {
        try {
            if (closable != null) {
            	closable.close();
            }
        } catch (Throwable t) {
            LOG.error("Error closing Closable: " + closable , t);
        }
    }

    /**
     * Writes <code>b.length</code> bytes from the specified byte array to
     * this output stream.
     *
     * @param os
     *            instance of <code>OutputStream</code>.
     * @param bytes
     *            the data.
     *
     */
    public static void write(OutputStream os, byte[] bytes) {
        try {
            os.write(bytes);
        } catch (IOException e) {
            throw new UtilitiesException("Error in write to output stream", e);
        }
    }

	/**
	 * Transfers bytes from the <code>InputStream</code> to the
	 * <code>OutputStream</code> using the given buffer.
	 *
	 * @param is
	 * @param buffer
	 * @param os
	 * @return long number of bytes actually written
	 */
	public static long write(InputStream is, byte[] buffer, OutputStream os) {
		long result = 0;
		try {
			int len = 0;
			while ((len = is.read(buffer)) > 0) {
				os.write(buffer, 0, len);
				result += len;
			}
			os.flush();
		} catch (IOException e) {
			return -1L;
		}
		return result;
	}

	/**
	 * Transfers bytes from the <code>InputStream</code> to the
	 * <code>OutputStream</code> using a temporary buffer.
	 *
	 * @param is
	 * @param os
	 * @return long number of bytes actually written
	 * @see #write(InputStream, byte[], OutputStream)
	 */
	public static long write(InputStream is, OutputStream os) {
		byte[] b = new byte[BUFFER_SIZE];
		return write(is, b, os);
	}

    /**
     * Writes <code>b.length</code> bytes from the specified byte array to
     * this output stream.It further closes this output stream and releases any
     * system resources associated with this stream.
     *
     * @param out
     *            instance of <code>OutputStream</code>.
     * @param bytes
     *            the data.
     */
    public static void writeAndClose(OutputStream out, byte[] bytes) {
        try {
            write(out, bytes);
        } finally {
            close(out);
        }
    }

    /**
     * Writes <code>b.length</code> bytes from the specified byte array to
     * this output stream.Finally closes this output stream and releases any
     * system resources associated with this stream.
     *
     * @param file
     *            source object.
     * @param bytes
     *            the data.
     */
    public static void write(File file, byte[] bytes) {
        OutputStream stream = createFileOutputStream(file);
        try {
            write(stream, bytes);
            flush(stream);
        } finally {
            close(stream);
        }
    }


    /**
     * Appends <code>b.length</code> bytes from the specified byte array to
     * this output stream. Finally closes this output stream and releases any
     * system resources associated with this stream.
     *
     * @param file
     *            source object.
     * @param bytes
     *            the data.
     */
    public static void writeAppend(File file, byte[] bytes) {

    	OutputStream stream = null;
    	 try {
             stream = new FileOutputStream(file, true);
             write(stream, bytes);
             flush(stream);
    	 } catch (IOException e) {
             throw new UtilitiesException("Error creating file output stream", e);
         } finally {
            if (stream != null) {
            	close(stream);
            }
        }
    }


    /**
     * Writes <code>b.length</code> bytes from the specified byte array to
     * this output stream in a new line.
     *
     * @param os
     *            instance of <code>OutputStream</code>.
     * @param s
     *            string to be written.
     */
    public static void writeln(OutputStream os, String s) {
        try {
            os.write(s.getBytes());
            os.write(NEW_LINE.getBytes());
        } catch (IOException e) {
            throw new UtilitiesException("Error in write to output stream", e);
        }
    }

    /**
     * Writes <code>b.length</code> bytes from the specified byte array to
     * this output stream.
     *
     * @param os
     *            instance of <code>OutputStream</code>.
     * @param s
     *            data.
     */
    public static void write(OutputStream os, String s) {
        write(os, s.getBytes());
    }

    /**
     * Write a string.
     *
     * @param out
     *            instance of <code>Writer</code>.
     * @param s
     *            String to be written
     */
    public static void write(Writer out, String s) {
        try {
            out.write(s);
        } catch (IOException e) {
            throw new UtilitiesException(e);
        }
    }

    /**
     * Read characters into a portion of an array. This method will block until
     * some input is available, an I/O error occurs, or the end of the stream is
     * reached.
     *
     * @param chars
     *            Destination buffer
     * @param offset
     *            Offset at which to start storing characters
     * @param length
     *            Maximum number of characters to read
     * @param reader
     *            instance of <code>Reader</code>
     * @return The number of characters read, or -1 if the end of the stream has
     *         been reached
     *
     */
    public static int read(Reader reader, char[] chars, int offset, int length) {
        try {
            return reader.read(chars, offset, length);
        } catch (IOException e) {
            throw new UtilitiesException("Error in read input stream", e);
        }
    }

    /**
     * Reads up to <code>len</code> bytes of data from the input stream into
     * an array of bytes. An attempt is made to read as many as <code>len</code>
     * bytes, but a smaller number may be read. The number of bytes actually
     * read is returned as an integer.
     *
     * @param is
     *            instance of <code>InputStream</code>.
     * @param bytes
     *            the buffer into which the data is read.
     * @param offset
     *            the start offset in array <code>b</code> at which the data
     *            is written.
     * @param length
     *            the maximum number of bytes to read.
     * @return the total number of bytes read into the buffer, or
     *         <code>-1</code> if there is no more data because the end of the
     *         stream has been reached.
     *
     */
    public static int read(InputStream is, byte[] bytes, int offset, int length) {
        try {
            return is.read(bytes, offset, length);
        } catch (IOException e) {
            throw new UtilitiesException("Error in read input stream", e);
        }
    }

    /**
     * Reads some bytes from an input stream and stores them into the buffer
     * array <code>bytes</code>. The number of bytes read is equal to the
     * length of <code>bytes</code>.
     *
     * @param in
     *            instance of <code>DataInput</code>.
     * @param bytes
     *            the buffer into which the data is read.
     */
    public static void readFully(DataInput in, byte[] bytes) {
        try {
            in.readFully(bytes);
        } catch (IOException e) {
            throw new UtilitiesException("Error reading from data input", e);
        }
    }

    /**
     * Read a line of text.
     *
     * @param br
     *            instance of <code>BufferedReader</code>
     * @return A String containing the contents of the line, not including any
     *         line-termination characters, or null if the end of the stream has
     *         been reached
     */
    public static String readLine(BufferedReader br) {
        try {
            return br.readLine();
        } catch (IOException e) {
            throw new UtilitiesException("Error in BufferedReader readLine", e);
        }
    }

    /**
     * Create a buffering character-input stream that uses a default-sized input
     * buffer.
     *
     * @param file
     *            file to be read.
     * @return <code> BufferedReader</code> object.
     */
    public static BufferedReader createBufferedReader(File file) {
    	// Need to be careful to not leak the file handle when
    	// there is an exception creating the Buffered wrapper
    	FileReader fileReader = null;
        try {
            fileReader = new FileReader(file);
			return new BufferedReader(fileReader);
        } catch (IOException e) {
        	close(fileReader);
            throw new UtilitiesException("Error creating file reader", e);
        }
    }

    /**
     * Creates a file output stream to write to the file represented by the
     * specified file name. A new <code>FileDescriptor</code> object is
     * created to represent this file connection.
     *
     * @param fileName
     *            the name of the file to be created.
     * @return creates a file output stream.
     */
    public static FileOutputStream createFileOutputStream(String fileName) {
        return createFileOutputStream(new File(fileName));
    }

    /**
     * Creates a file output stream to write to the file represented by the
     * specified <code>File</code> object. A new <code>FileDescriptor</code>
     * object is created to represent this file connection.
     *
     * @param file
     *            the file to be opened for writing.
     * @return creates a file output stream.
     */
    public static FileOutputStream createFileOutputStream(File file) {
        try {
        	File parent = file.getParentFile();
        	if ((parent != null) && !parent.exists()) {
        		parent.mkdir();
        	}
            return new FileOutputStream(file);
        } catch (IOException e) {
            throw new UtilitiesException("Error creating file output stream", e);
        }
    }

    /**
     * Creates a file output stream to write to the file represented by the
     * specified file name. A new <code>FileDescriptor</code> object is
     * created to represent this file connection.
     *
     * @param fileName
     *            the name of the file to be created.
     * @return creates a file output stream.
     */
    public static FileInputStream createFileInputStream(String fileName) {
        return createFileInputStream(new File(fileName));
    }

    /**
     * Creates a file output stream to write to the file represented by the
     * specified <code>File</code> object. A new <code>FileDescriptor</code>
     * object is created to represent this file connection.
     *
     * @param file
     *            the file to be opened for writing.
     * @return creates a file output stream.
     */
    public static FileInputStream createFileInputStream(File file) {
        try {
            return new FileInputStream(file);
        } catch (IOException e) {
            throw new UtilitiesException("Error creating file output stream", e);
        }
    }

    /**
     * Reads some bytes from an input stream and stores them into the buffer
     * array <code>bytes</code>. The number of bytes read is equal to the
     * length of <code>bytes</code>.
     *
     * @param in
     *            instance of <code>InputStream</code>.
     * @param length
     *            array length.
     * @return buffer in to which data is stored.
     */
    public static byte[] readFullyAndClose(InputStream in, int length) {
        DataInputStream dis = null;
        byte[] buffer = null;
        try {
            buffer = new byte[length];
            dis = new DataInputStream(in);
            readFully(dis, buffer);
        } finally {
            close(dis, in);
        }
        return buffer;
    }

    /**
     * Flushes this output stream and forces any buffered output bytes to be
     * written out.
     *
     * @param stream
     *            instance of <code>OutputStream</code>.
     */
    public static void flush(OutputStream stream) {
        try {
            stream.flush();
        } catch (IOException e) {
            throw new UtilitiesException("Error while flushing output stream",
                    e);
        }
    }

    /**
     * It loads the available resource file to <code>byte</code> array.
     *
     * @param path
     *            path of the resource.
     * @return a byte array.
     */
    public static byte[] loadLocalResourceAsByteArray(String path) {
        File file = FileLoader.getResourceAsFile(path);
        return toByteArray(file);
    }

    /**
     * converts the file in to a <code>byte</code> array.
     *
     * @param file
     *            source file.
     * @return byte arrayf
     */
    public static byte[] toByteArray(File file) {
        RandomAccessFile in = null;
        byte[] result = null;
        try {
            in = new RandomAccessFile(file, "r");
            result = new byte[(int) in.length()];
            in.readFully(result);
        } catch (IOException e) {
            throw new UtilitiesException(e);
        } finally {
            close(in);
        }
        return result;
    }

    /**
     * Check whether this logger is enabled for the debug priority and returns
     * the <code>DiagnosticOutputStream</code> object.
     *
     * @param out
     *            instance of <code>OutputStream</code>.
     * @return <code>OutStream</code> object.
     */
    public static OutputStream diagnostic(OutputStream out) {
        if (LOG.isDebugEnabled()) {
            return new DiagnosticOutputStream(out);
        }
        return out;
    }

    /**
     * Check whether this logger is enabled for the debug priority and returns
     * an <code>InputStream</code> object capable of performing read and write
     * operations.
     *
     * @param in
     *            instance of <code>InputStream</code>
     * @return an <code>InputStream</code> object.
     */
    public static InputStream diagnostic(InputStream in) {
        if (LOG.isDebugEnabled()) {
            return new DiagnosticInputStream(in);
        }
        return in;
    }

    /**
     * Creates a new buffered output stream to write data to the specified
     * underlying output stream with the specified buffer size and Writes the
     * specified object to the ObjectOutputStream.
     *
     * @param raw
     *            instance of<code>OutputStream</code>.
     * @param object
     *            specified object to be written.
     */
    public static void writeObjectAndClose(OutputStream raw, Object object) {
        ObjectOutputStream stream = null;
        try {
            stream = new ObjectOutputStream(new BufferedOutputStream(
                    diagnostic(raw), BUFFER_SIZE));
            stream.writeObject(object);
            stream.flush();
        } catch (Exception e) {
            throw new UtilitiesException(e);
        } finally {
            close(stream, raw);
        }
    }

    /**
     * Creates a new buffered output stream to read data to the specified
     * underlying input stream with the specified buffer size and reads the
     * specified object to the ObjectInputStream.
     *
     * @param raw
     *            instance of <code>InputStream</code>.
     * @return read object .
     */
    public static Object readObjectAndClose(InputStream raw) {
        ObjectInputStream stream = null;
        try {
            stream = new ClassLoaderObjectInputStream(new BufferedInputStream(
                    diagnostic(raw), BUFFER_SIZE));
            return stream.readObject();
        } catch (Exception e) {
            throw new UtilitiesException(e);
        } finally {
            close(stream, raw);
        }

        /*
         * Note: Use ClassLoaderObjectInputStream to resolve class loading
         * problem in JBoss 3.2.3 which during the de-serialization JBoss uses
         * Tomcat's class loader and can not find our classes, e.g.
         * DocumentSpecification. The problem occurs in HFN 10, which uses JBoss
         * 3.2.3. With higher version of JBoss, use ObjectInputStream.
         */

    }
    /**
     * Closes this input stream and releases any system resources associated
     * with this stream.
     *
     * @param wrapper
     *            instance of <code>InputStream</code>.
     * @param underlying
     *            underlying <code>InputStream</code>.
     */
    private static void close(InputStream wrapper, InputStream underlying) {
        if (wrapper != null) {
            close(wrapper);
        } else {
            close(underlying);
        }
    }

    /**
     * Closes this output stream and releases any system resources associated
     * with this stream.
     *
     * @param wrapper
     *            instance of <code>OutputStream</code>.
     * @param underlying
     *            underlying <code>OutputStream</code>.
     */
    private static void close(OutputStream wrapper, OutputStream underlying) {
        if (wrapper != null) {
            close(wrapper);
        } else {
            close(underlying);
        }
    }
}
