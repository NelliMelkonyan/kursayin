package gui;
        
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Main {
    public static void main(String[] args) {
        try {
            for (UIManager.LookAndFeelInfo lfInfo : UIManager.getInstalledLookAndFeels()) {
                if (!"Nimbus".equals(lfInfo.getName())) continue;
                UIManager.setLookAndFeel(lfInfo.getClassName());
                break;
            }
        }
        catch (ClassNotFoundException ex) {
            System.out.println(ex);
        }
        catch (InstantiationException ex) {
            System.out.println(ex);
        }
        catch (IllegalAccessException ex) {
            System.out.println(ex);
        }
        catch (UnsupportedLookAndFeelException ex) {
            System.out.println(ex);
        }
        SwingUtilities.invokeLater(new Runnable(){

            public void run() {
                MainGUI mainGUI = new MainGUI();
                mainGUI.createGUI();
                mainGUI.setVisible(true);
            }
        });
    }

}

