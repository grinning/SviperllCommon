/*
 * Copyright (C) 2012 Victor Nazarov <asviraspossible@gmail.com>
 */

package org.sviperll.io;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

/**
 *
 * @author vir
 */
public class Base64OutputStream extends OutputStream {
    private static final byte[] base64codes;
    static {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
            sb.append("abcdefghijklmnopqrstuvwxyz");
            sb.append("0123456789");
            sb.append("+/");
            base64codes = sb.toString().getBytes("ASCII");
        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static byte[] encode(byte[] bytes) {
        return encode(bytes, true);
    }
    public static byte[] encode(byte[] bytes, boolean lineSpliting) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            OutputStream os = newInstance(baos, lineSpliting);
            try {
                os.write(bytes);
            } finally {
                os.close();
            }
            return baos.toByteArray();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static String encode(String s, String encoding) throws UnsupportedEncodingException {
        return encode(s, encoding, true);
    }
    public static String encode(String s, String encoding, boolean lineSpliting) throws UnsupportedEncodingException {
        return new String(encode(s.getBytes(encoding), lineSpliting), "ASCII");
    }

    public static OutputStream newInstance(OutputStream encoded, boolean lineSpliting) {
        OutputStream os = encoded;
        if (lineSpliting)
            os = new LineSplitterOutputStream(encoded);
        return new Base64OutputStream(os);
    }
    public static OutputStream newInstance(OutputStream encoded, int splitLinesAt) {
        OutputStream os = new LineSplitterOutputStream(encoded, splitLinesAt);
        return new Base64OutputStream(os);
    }
    public static OutputStream newInstance(OutputStream encoded) {
        return newInstance(encoded, true);
    }


    private final byte[] buf = new byte[3];
    private final OutputStream encoded;

    private int bufOff = 0;
    private Base64OutputStream(OutputStream encoded) {
        this.encoded = encoded;
    }

    @Override
    public void write(int b) throws IOException {
        buf[bufOff++] = (byte) b;
        if (bufOff == buf.length) {
            writeBuf();
            bufOff = 0;
        }
    }

    @Override
    public void flush() throws IOException {
        encoded.flush();
    }

    @Override
    public void close() throws IOException {
        if (bufOff != 0) {
            for (int i = bufOff; i < buf.length; i++)
                buf[i] = 0;
            int padding = buf.length - bufOff;
            writeBuf(4 - padding);
            for (int i = 0; i < padding; i++)
                encoded.write("=".getBytes("ASCII"));
        }
        encoded.close();
    }

    private void writeBuf() throws IOException {
        writeBuf(4);
    }
    private void writeBuf(int len) throws IOException {
        int j = ((buf[0] & 0xff) << 16) + ((buf[1] & 0xff) << 8) + (buf[2] & 0xff);
        for (int i = 0, shift = 18; i < len; i++, shift -= 6) {
            encoded.write(base64codes[(j >> shift) & 0x3f]);
        }
    }
}
