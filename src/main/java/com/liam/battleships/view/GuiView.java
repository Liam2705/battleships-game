package com.liam.battleships.view;

import com.liam.battleships.controller.GameController;
import com.liam.battleships.model.board.Board;
import com.liam.battleships.model.board.CellState;
import com.liam.battleships.model.board.Coordinate;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;


public class GuiView implements GameView {
    private GameController controller;

    private JFrame frame;
    private JLabel messageLabel;
    private JPanel humanBoardPanel;
    private JPanel enemyBoardPanel;

    private JButton[][] humanButtons;
    private JButton[][] enemyButtons;
    private boolean isInitialized = false;

    public GuiView() {
        SwingUtilities.invokeLater(this::createAndShowGUI);
    }

    private void createAndShowGUI() {
        frame = new JFrame("Battleships - GUI VIEW");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout(10, 10));

        messageLabel = new JLabel("Connecting to game...", SwingConstants.CENTER);
        messageLabel.setFont(new Font("Arial", Font.BOLD, 16));
        messageLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        frame.add(messageLabel, BorderLayout.NORTH);

        JPanel boardsContainer = new JPanel(new GridLayout(1, 2, 20, 0));
        boardsContainer.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        humanBoardPanel = new JPanel();
        humanBoardPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.BLACK), "Your Fleet",
                TitledBorder.CENTER, TitledBorder.TOP, new Font("Arial", Font.BOLD, 14)));

        enemyBoardPanel = new JPanel();
        enemyBoardPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.BLACK), "Enemy Waters",
                TitledBorder.CENTER, TitledBorder.TOP, new Font("Arial", Font.BOLD, 14)));

        boardsContainer.add(humanBoardPanel);
        boardsContainer.add(enemyBoardPanel);
        frame.add(boardsContainer, BorderLayout.CENTER);

        frame.setSize(800, 450);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    @Override
    public void setController(GameController controller) {
        this.controller = controller;
    }

    @Override
    public void showMessage(String message) {
        SwingUtilities.invokeLater(() -> messageLabel.setText(message));
    }

    @Override
    public void displayBoard(Board board, boolean hideShips) {
        SwingUtilities.invokeLater(() -> {
            if (!isInitialized) {
                initializeGrids(board.getSize());
            }
            updateGrid(board, humanButtons, hideShips);
        });
    }

    @Override
    public void displayEnemyBoard(Board board) {
        SwingUtilities.invokeLater(() -> {
            if (!isInitialized) {
                initializeGrids(board.getSize());
            }
            updateGrid(board, enemyButtons, true);
        });
    }

    /**
     * Dynamically builds the grid of buttons based on the board size.
     */
    private void initializeGrids(int size) {
        humanButtons = new JButton[size][size];
        enemyButtons = new JButton[size][size];

        humanBoardPanel.setLayout(new GridLayout(size, size));
        enemyBoardPanel.setLayout(new GridLayout(size, size));

        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                // Setup Human Board
                humanButtons[y][x] = createGridButton();
                humanBoardPanel.add(humanButtons[y][x]);

                // Setup Enemy Board (Clickable view)
                JButton enemyBtn = createGridButton();
                final int targetX = x;
                final int targetY = y;

                // Event listener that passes the coordinate to the Controller on click
                enemyBtn.addActionListener(e -> {
                    if (controller != null) {
                        controller.executeHumanTurn((new Coordinate(targetX, targetY)));
                    }
                });

                enemyButtons[y][x] = enemyBtn;
                enemyBoardPanel.add(enemyBtn);
            }
        }

        frame.revalidate();
        frame.repaint();
        isInitialized = true;
    }

    private JButton createGridButton() {
        JButton btn = new JButton();
        btn.setFocusPainted(false);
        btn.setFont(new Font("Arial", Font.BOLD, 12));
        btn.setBackground(new Color(173, 216, 230));
        return btn;
    }

    /**
     * Syncs the actual Board state to the visual JButtons.
     */
    private void updateGrid(Board board, JButton[][] buttons, boolean hideShips) {
        int size = board.getSize();
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                CellState state = board.getCellState(new Coordinate(x, y));
                JButton btn = buttons[y][x];

                switch (state) {
                    case WATER:
                        btn.setBackground(new Color(173, 216, 230));
                        btn.setText("");
                        break;
                    case SHIP:
                        if (hideShips) {
                            btn.setBackground(new Color(173, 216, 230));
                            btn.setText("");
                        } else {
                            btn.setBackground(Color.GRAY);
                            btn.setText("S");
                        }
                        break;
                    case HIT:
                        btn.setBackground(Color.RED);
                        btn.setText("X");
                        btn.setEnabled(false);
                        break;
                    case MISS:
                        btn.setBackground(Color.WHITE);
                        btn.setText("O");
                        btn.setEnabled(false);
                        break;
                    case SUNK:
                        btn.setBackground(Color.BLACK);
                        btn.setForeground(Color.WHITE);
                        btn.setText("#");
                        btn.setEnabled(false);
                        break;
                }
            }
        }
    }
}
