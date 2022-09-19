package ru.vsu.edu.shlyikov_d_g;

import ru.vsu.edu.shlyikov_d_g.util.SwingUtils;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Queue;

public class Main {

    public static void main(String[] args) throws Exception {
        winMain();
    }

    public static void winMain(){
        Locale.setDefault(Locale.ROOT);
        SwingUtils.setDefaultFont("Consolas", 1,18);
        //SwingUtils.setDefaultFont("Microsoft Sans Serif", 18);

        java.awt.EventQueue.invokeLater(() -> {
            try {
                new FrameMain().setVisible(true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        // 0 - Plain
        // 1 - Bold
        // 2 - Italic
        // 3 - Bold+Italic
    }
}
