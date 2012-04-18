/*
 * Copyright (C) 2011 Victor Nazarov <asviraspossible@gmail.com>
 */

package org.sviperll.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author vir
 */
public class TruncatedInputStreamTest {
    @Test
    public void testTruncationToEmpty() throws Exception {
        InputStream is = new ByteArrayInputStream(new byte[] {1, 2, 3, 4, 5, 6});
        InputStream tr = new TruncatedInputStream(is, 0);
        byte[] res = readByteArray(tr);
        assertArrayEquals(new byte[] {}, res);
    }

    @Test
    public void testTruncationToOneByte() throws Exception {
        InputStream is = new ByteArrayInputStream(new byte[] {1, 2, 3, 4, 5, 6});
        InputStream tr = new TruncatedInputStream(is, 1);
        byte[] res = readByteArray(tr);
        assertArrayEquals(new byte[] {1}, res);
    }

    @Test
    public void testTruncationToOneByteLessThenRealLength() throws Exception {
        InputStream is = new ByteArrayInputStream(new byte[] {1, 2, 3, 4, 5, 6});
        InputStream tr = new TruncatedInputStream(is, 5);
        byte[] res = readByteArray(tr);
        assertArrayEquals(new byte[] {1, 2, 3, 4, 5}, res);
    }

    @Test
    public void testTruncationToRealLength() throws Exception {
        InputStream is = new ByteArrayInputStream(new byte[] {1, 2, 3, 4, 5, 6});
        InputStream tr = new TruncatedInputStream(is, 6);
        byte[] res = readByteArray(tr);
        assertArrayEquals(new byte[] {1, 2, 3, 4, 5, 6}, res);
    }

    @Test
    public void testTruncationToOneByteMoreThenRealLength() throws Exception {
        InputStream is = new ByteArrayInputStream(new byte[] {1, 2, 3, 4, 5, 6});
        InputStream tr = new TruncatedInputStream(is, 7);
        byte[] res = readByteArray(tr);
        assertArrayEquals(new byte[] {1, 2, 3, 4, 5, 6}, res);
    }

    @Test
    public void testSingleCharacterRead() throws Exception {
        InputStream is = new ByteArrayInputStream(new byte[] {1, 2, 3, 4, 5, 6});
        InputStream tr = new TruncatedInputStream(is, 3);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int b;
        while ((b = tr.read()) != -1) {
            baos.write(b);
        }
        assertArrayEquals(new byte[] {1, 2, 3}, baos.toByteArray());
    }

    @Test
    public void testWholeBufferRead() throws Exception {
        InputStream is = new ByteArrayInputStream(new byte[] {1, 2, 3, 4, 5, 6});
        InputStream tr = new TruncatedInputStream(is, 4);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buf = new byte[2];
        int len;
        while ((len = tr.read(buf)) != -1)
            baos.write(buf, 0, len);
        assertArrayEquals(new byte[] {1, 2, 3, 4}, baos.toByteArray());
    }


    @Test
    public void testBufferSegmentRead() throws Exception {
        InputStream is = new ByteArrayInputStream(new byte[] {1, 2, 3, 4, 5, 6});
        InputStream tr = new TruncatedInputStream(is, 4);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buf = new byte[4];
        int len;
        while ((len = tr.read(buf, 2, 2)) != -1)
            baos.write(buf, 2, len);
        assertArrayEquals(new byte[] {1, 2, 3, 4}, baos.toByteArray());
    }

    private byte[] readByteArray(InputStream tr) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int b;
        while ((b = tr.read()) != -1) {
            baos.write(b);
        }
        return baos.toByteArray();
    }
}