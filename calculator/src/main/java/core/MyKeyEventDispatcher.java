package core;

import java.awt.KeyEventDispatcher;
import java.awt.event.KeyEvent;
import javax.swing.JTextArea;

public class MyKeyEventDispatcher
implements KeyEventDispatcher {
    private JTextArea txtInput;

    public MyKeyEventDispatcher(JTextArea txtInput) {
        this.txtInput = txtInput;
    }

    public boolean dispatchKeyEvent(KeyEvent ke) {
        char c;
        int id = ke.getID();
        if (id == 400 && ((c = ke.getKeyChar()) >= 'a' && c <= 'n' || c >= 'o' && c <= 't' || c >= 'w' && c <= 'z')) {
            System.out.println("key character = '" + c + "'");
            this.txtInput.append("" + c + " ");
        }
        return false;
    }
}

