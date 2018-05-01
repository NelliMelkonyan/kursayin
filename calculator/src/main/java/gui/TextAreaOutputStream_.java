package gui;

import java.io.CharArrayWriter;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.LinkedList;
import javax.swing.JTextArea;

public class TextAreaOutputStream_
extends OutputStream {
    private static byte[] LINE_SEP = System.getProperty("line.separator", "\n").getBytes();
    private JTextArea textArea;
    private int maxLines;
    private LinkedList lineLengths;
    private int curLength;
    private byte[] oneByte;

    public TextAreaOutputStream_(JTextArea ta) {
        this(ta, 100000);
    }

    public TextAreaOutputStream_(JTextArea ta, int ml) {
        this.textArea = ta;
        this.maxLines = ml;
        this.lineLengths = new LinkedList();
        this.curLength = 0;
        this.oneByte = new byte[1];
    }

    public synchronized void clear() {
        this.lineLengths = new LinkedList();
        this.curLength = 0;
        this.textArea.setText("");
    }

    public synchronized int getMaximumLines() {
        return this.maxLines;
    }

    public synchronized void setMaximumLines(int val) {
        this.maxLines = val;
    }

    public void close() {
        if (this.textArea != null) {
            this.textArea = null;
            this.lineLengths = null;
            this.oneByte = null;
        }
    }

    public void flush() {
    }

    public void write(int val) {
        this.oneByte[0] = (byte)val;
        this.write(this.oneByte, 0, 1);
    }

    public void write(byte[] ba) {
        this.write(ba, 0, ba.length);
    }

    public synchronized void write(byte[] ba, int str, int len) {
        try {
            this.curLength += len;
            if (this.bytesEndWith(ba, str, len, LINE_SEP)) {
                this.lineLengths.addLast(new Integer(this.curLength));
                this.curLength = 0;
                if (this.lineLengths.size() > this.maxLines) {
                    this.textArea.replaceRange(null, 0, (Integer)this.lineLengths.removeFirst());
                }
            }
            for (int xa = 0; xa < 10; ++xa) {
                try {
                    this.textArea.append(new String(ba, str, len));
                    break;
                }
                catch (Throwable thr) {
                    continue;
                }
            }
        }
        catch (Throwable thr) {
            CharArrayWriter caw = new CharArrayWriter();
            thr.printStackTrace(new PrintWriter(caw, true));
            this.textArea.append(System.getProperty("line.separator", "\n"));
            this.textArea.append(caw.toString());
        }
    }

    private boolean bytesEndWith(byte[] ba, int str, int len, byte[] ew) {
        if (len < LINE_SEP.length) {
            return false;
        }
        int xa = 0;
        int xb = str + len - LINE_SEP.length;
        while (xa < LINE_SEP.length) {
            if (LINE_SEP[xa] != ba[xb]) {
                return false;
            }
            ++xa;
            ++xb;
        }
        return true;
    }
}

