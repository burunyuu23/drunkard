package ru.vsu.edu.shlyikov_d_g;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import ru.vsu.edu.shlyikov_d_g.util.SwingUtils;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;


public class FrameMain extends JFrame {

    private JPanel panelMain;
    private JPanel startMenu;
    private JPanel game;
    private JLabel gameLogo;
    private JButton playFirstVariantButton;
    private JButton playSecondVariantButton;
    private JButton buttonNdPlayer;
    private JButton buttonStPlayer;
    private JLabel stCountCards;
    private JLabel ndCountCards;
    private JLabel stPlayer;
    private JLabel ndPlayer;
    private JLabel status;
    private JPanel ndPlayerPanel;
    private JPanel stPlayerPanel;
    private JPanel gamePanel;
    private JPanel stCard;
    private JPanel ndCard;
    private JPanel stCardBack;
    private JPanel stCardFront;
    private JLabel stCardLeftTopValue;
    private JPanel stCardLeftTop;
    private JLabel stCardRightBottomValue;
    private JPanel ndCardContainer;
    private JPanel ndCardBack;
    private JPanel ndCardFront;
    private JLabel ndCardLeftTopValue;
    private JLabel ndCardRightBottomValue;
    private JButton checkButton;
    private JLabel stListCards;
    private JLabel ndListCards;
    private JLabel statusConflict;

    private JFileChooser fileChooserOpen;
    private JFileChooser fileChooserSave;
    private JMenuBar menuBarMain;
    private JMenu menuLookAndFeel;

    boolean stFront = false;
    boolean ndFront = false;

    Game gm = new Game();

    public FrameMain() throws IOException {
        this.setTitle("task3_21");
        this.setContentPane(panelMain);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();

        fileChooserOpen = new JFileChooser();
        fileChooserSave = new JFileChooser();
        fileChooserOpen.setCurrentDirectory(new File("."));     
        fileChooserSave.setCurrentDirectory(new File("."));
        FileFilter filter = new FileNameExtensionFilter("Text files", "txt");
        fileChooserOpen.addChoosableFileFilter(filter);
        fileChooserSave.addChoosableFileFilter(filter);

        fileChooserSave.setAcceptAllFileFilterUsed(false);
        fileChooserSave.setDialogType(JFileChooser.SAVE_DIALOG);
        fileChooserSave.setApproveButtonText("Save");

        menuBarMain = new JMenuBar();
        setJMenuBar(menuBarMain);

        menuLookAndFeel = new JMenu();
        menuLookAndFeel.setText("Вид");
        menuBarMain.add(menuLookAndFeel);
        SwingUtils.initLookAndFeelMenu(menuLookAndFeel);

        this.pack();

        JFrame jf = this;

        // сделать класс моделлинг и экземпляр класса вызывать тут

        stListCards.setText(gm.watchQueue(1));
        ndListCards.setText(gm.watchQueue(2));

        panelMain.add(startMenu, "start");
        panelMain.add(game, "game");

        stCard.add(stCardBack, "back");
        stCard.add(stCardFront, "front");
        ndCardContainer.add(ndCardBack, "back");
        ndCardContainer.add(ndCardFront, "front");

        stCard.setVisible(false);
        ndCard.setVisible(false);

        goToLayout(panelMain, "start");

        statusConflict.setVisible(false);

        playFirstVariantButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                goToLayout(panelMain, "game");
                gm.firstGame();
            }
        });
        playSecondVariantButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                goToLayout(panelMain, "game");
                gm.secondGame();
            }
        });
        buttonStPlayer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (!stFront && !stCard.isVisible()){
                    stCard.setVisible(true);

                    stCountCards.setText(gm.countText(1));
                    stCardLeftTopValue.setText(gm.getCur1P());
                    stCardRightBottomValue.setText(gm.getCur1P());

                    status.setText("Second player turn.");
                }
            }
        });
        buttonNdPlayer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (!ndFront && !ndCard.isVisible() && stCard.isVisible()){
                    ndCard.setVisible(true);

                    ndCountCards.setText(gm.countText(2));
                    ndCardLeftTopValue.setText(gm.getCur2P());
                    ndCardRightBottomValue.setText(gm.getCur2P());
                    status.setText("Time to check.");
                }
            }
        });
        checkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (ndCard.isVisible() && stCard.isVisible() && !stFront && !ndFront){
                    goToLayout(ndCardContainer, "front");
                    ndFront = true;
                    goToLayout(stCard, "front");
                    stFront = true;

                    switch (gm.compare()) {
                        case 1:
                            status.setText("First player win.");
                            gm.pariWin(1);
                            statusConflict.setVisible(false);
                            break;
                        case 0:
                            gm.pariConflict();
                            status.setText("Conflict!");
                            statusConflict.setVisible(true);
                            statusConflict.setText(gm.cardsInConflict());
                            break;
                        case -1:
                            status.setText("Second player win.");
                            gm.pariWin(2);
                            statusConflict.setVisible(false);
                            break;
                    }
                        stListCards.setText(gm.watchQueue(1));
                        ndListCards.setText(gm.watchQueue(2));

                        stCountCards.setText(gm.countText(1));
                        ndCountCards.setText(gm.countText(2));
                }
                else {
                    if (stFront && ndFront) {
                        goToLayout(ndCardContainer, "back");
                        ndFront = false;
                        ndCard.setVisible(false);

                        gm.cur();

                        goToLayout(stCard, "back");
                        stFront = false;
                        stCard.setVisible(false);

                        if (gm.getGameMode() == GameMode.GAME_FIRST) {
                            if (gm.qIsEmpty(1) || gm.qIsEmpty(2)) {
                                if (gm.qIsEmpty(1) && !gm.qIsEmpty(2)) {
                                    gm.setGameMode(GameMode.GAME_OVER);
                                    status.setText("First player lose. Game over");
                                } else if (!gm.qIsEmpty(1) && gm.qIsEmpty(2)) {
                                    gm.setGameMode(GameMode.GAME_OVER);
                                    status.setText("Second player lose. Game over");
                                } else if (gm.qIsEmpty(1) && gm.qIsEmpty(2)) {
                                    status.setText("Super conflict! Game over");
                                    gm.setGameMode(GameMode.GAME_OVER);
                                }
                                if (gm.getGameMode() == GameMode.GAME_OVER) {
                                    createFrame();
                                    jf.setEnabled(false);
                                }
                            } else {
                                status.setText("First player turn.");
                            }
                            gm.removeLast();
                        }
                    } else {
                        status.setText("Not all players have made their move. " + (stCard.isVisible() ? "Second player turn." : "First player turn."));
                    }
                }
            }
        });
    }

    public JFrame createFrame(){
        JFrame frame = new JFrame("JFrameWindowListener");
        frame.add(new JLabel("Game Over.", SwingConstants.CENTER), BorderLayout.CENTER);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(250, 80));
        frame.pack();
        frame.setVisible(true);
        return frame;
    }

    public static void goToLayout(JPanel jf, String name) {
        CardLayout cardLayout = (CardLayout) jf.getLayout();
        cardLayout.show(jf, name);
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        panelMain = new JPanel();
        panelMain.setLayout(new GridLayoutManager(5, 2, new Insets(10, 10, 10, 10), 10, 10));
        final JScrollPane scrollPane1 = new JScrollPane();
        scrollPane1.setVerticalScrollBarPolicy(21);
        panelMain.add(scrollPane1, new GridConstraints(0, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(-1, 200), null, 0, false));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 4, new Insets(0, 0, 0, 0), -1, -1));
        panelMain.add(panel1, new GridConstraints(1, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        panel1.add(spacer1, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, new Dimension(100, -1), null, 0, false));
        final JScrollPane scrollPane2 = new JScrollPane();
        scrollPane2.setVerticalScrollBarPolicy(21);
        panelMain.add(scrollPane2, new GridConstraints(3, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(-1, 200), null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        panelMain.add(panel2, new GridConstraints(2, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        panel2.add(spacer2, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        panelMain.add(panel3, new GridConstraints(4, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final Spacer spacer3 = new Spacer();
        panel3.add(spacer3, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panelMain;
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
