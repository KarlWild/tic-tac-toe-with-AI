package tictactoe;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;


class Game {
    private char[][] field;
    int turn;

    public Game() {
        turn = 0;
        field = new char[5][9];
        field[0] = new char[]{'-', '-', '-', '-', '-', '-', '-', '-', '-'};
        field[1] = new char[]{'|', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '|'};
        field[2] = new char[]{'|', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '|'};
        field[3] = new char[]{'|', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '|'};
        field[4] = new char[]{'-', '-', '-', '-', '-', '-', '-', '-', '-'};
    }
    public void outputField() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 9; j++) {
                System.out.print(field[i][j]);
            }
            System.out.println();
        }
    }


    public char[][] getField() {
        return field;
    }

    public void setField(int[] cell, char move) {
        field[cell[0]][cell[1]] = move;
    }

    public char getOneCell(int a, int b) {
        return field[a][b];
    }

    public Boolean isCellOccupied(int a, int b) {
        return field[a][b] != 'X' && field[a][b] != 'O';
    }

    public Boolean isCellOccupied(int[] cell) {
        if (field[cell[0]][cell[1]] == 'X' || field[cell[0]][cell[1]] == 'O') {
            System.out.println("This cell is occupied! Choose another one!");
            return false;
        } else return true;
    }

    public Boolean isCellOccupied(int[] cell, String message) {
        return field[cell[0]][cell[1]] != 'X' && field[cell[0]][cell[1]] != 'O';
    }

    public char winCond() {
        char result;
        if (field[1][2] == 'X' && field[1][4] == 'X' && field[1][6] == 'X') result = 'X';
        else if (field[2][2] == 'X' && field[2][4] == 'X' && field[2][6] == 'X') result = 'X';
        else if (field[3][2] == 'X' && field[3][4] == 'X' && field[3][6] == 'X') result = 'X';
        else if (field[1][2] == 'X' && field[2][2] == 'X' && field[3][2] == 'X') result = 'X';
        else if (field[1][4] == 'X' && field[2][4] == 'X' && field[3][4] == 'X') result = 'X';
        else if (field[1][6] == 'X' && field[2][6] == 'X' && field[3][6] == 'X') result = 'X';
        else if (field[1][2] == 'X' && field[2][4] == 'X' && field[3][6] == 'X') result = 'X';
        else if (field[3][2] == 'X' && field[2][4] == 'X' && field[1][6] == 'X') result = 'X';
        else if (field[1][2] == 'O' && field[1][4] == 'O' && field[1][6] == 'O') result = 'O';
        else if (field[2][2] == 'O' && field[2][4] == 'O' && field[2][6] == 'O') result = 'O';
        else if (field[3][2] == 'O' && field[3][4] == 'O' && field[3][6] == 'O') result = 'O';
        else if (field[1][2] == 'O' && field[2][2] == 'O' && field[3][2] == 'O') result = 'O';
        else if (field[1][4] == 'O' && field[2][4] == 'O' && field[3][4] == 'O') result = 'O';
        else if (field[1][6] == 'O' && field[2][6] == 'O' && field[3][6] == 'O') result = 'O';
        else if (field[1][2] == 'O' && field[2][4] == 'O' && field[3][6] == 'O') result = 'O';
        else if (field[3][2] == 'O' && field[2][4] == 'O' && field[1][6] == 'O') result = 'O';
        else result = ' ';
        return result;
    }

    public int getTurn() {
        return turn;
    }
}

class Player { //User
    char first_Move;//X or O
    static int creation = 0;
    Scanner sc = new Scanner(System.in);
    static Game game = new Game();

    public Player(Game game) {
        if (creation % 2 == 0) first_Move = 'X';
        else first_Move = 'O';
        creation++;
        Player.game = game;
    }

    public void makeAMove() {
        int[] cell = new int[2];
        do {
            System.out.println("Enter the coordinates:");
            while (!sc.hasNextInt()) {
                System.out.println("You should enter numbers!");
                sc.next();
            }
            cell[0] = sc.nextInt();
            cell[1] = sc.nextInt();
            if (cell[0] <= 0 || cell[0] > 3 || cell[1] <= 0 || cell[1] > 3) {
                System.out.println("Coordinates should be from 1 to 3!");
            }
            cell[1] = cell[1] + cell[1];
        } while (cell[0] <= 0 || cell[0] > 3 || cell[1] <= 0 || cell[1] > 6 || !game.isCellOccupied(cell));
        game.setField(cell, first_Move);
    }

    public static char getWinGame() {
        return game.winCond();
    }
}

class AI extends Player {
    private static Random r;

    public AI(Game game) {
        super(game);
        r = new Random();
    }

    @Override
    public void makeAMove() {
        int[] cell = new int[2];
        do {
            cell[0] = r.nextInt(3) + 1;
            cell[1] = r.nextInt(3) + 1;
            cell[1] = cell[1] + cell[1];
        } while (!game.isCellOccupied(cell, "AI"));
        System.out.println("Making move level " + '"' + "easy" + '"');
        game.setField(cell, first_Move);
    }
}

class Medium_AI extends AI {
    private static Random r;
    char oppMove;

    public Medium_AI(Game game) {
        super(game);
        r = new Random();
        if (first_Move == 'X') oppMove = 'O';
        else oppMove = 'X';
    }

    @Override
    public void makeAMove() {
        int[] cell = new int[2];
        cell = makeMediumMove();
        if (cell[0] == 0 && cell[1] == 0) {
            do {
                cell[0] = r.nextInt(3) + 1;
                cell[1] = r.nextInt(3) + 1;
                cell[1] = cell[1] + cell[1];
            } while (!game.isCellOccupied(cell, "AI"));
            System.out.println("Making move level " + '"' + "medium" + '"');
            game.setField(cell, first_Move);
        } else {
            System.out.println("Making move level " + '"' + "medium" + '"');
            game.setField(cell, first_Move);
        }
    }

    public int[] makeMediumMove() {
        int[] cell = new int[2];
        char stateOfFuture;
        char[][] gameField = game.getField();
        for (int i = 1; i < 4; i++) {
            for (int j = 2; j < 7; j += 2) {
                if (game.isCellOccupied(i, j)) {
                    gameField[i][j] = first_Move;
                    stateOfFuture = PossibleWinLose(gameField);
                    if (stateOfFuture == first_Move) {
                        cell[0] = i;
                        cell[1] = j;
                        return cell;
                    }
                    gameField[i][j] = ' ';
                }
            }
        }
        for (int i = 1; i < 4; i++) {
            for (int j = 2; j < 7; j += 2) {
                if (game.isCellOccupied(i, j)) {
                    gameField[i][j] = oppMove;
                    stateOfFuture = PossibleWinLose(gameField);
                    if (stateOfFuture == oppMove) {
                        cell[0] = i;
                        cell[1] = j;
                        return cell;
                    }
                    gameField[i][j] = ' ';
                }
            }
        }
        return cell;
    }

    public char PossibleWinLose(char[][] gameField) {
        char result;
        if (gameField[1][2] == 'X' && gameField[1][4] == 'X' && gameField[1][6] == 'X') result = 'X';
        else if (gameField[2][2] == 'X' && gameField[2][4] == 'X' && gameField[2][6] == 'X') result = 'X';
        else if (gameField[3][2] == 'X' && gameField[3][4] == 'X' && gameField[3][6] == 'X') result = 'X';
        else if (gameField[1][2] == 'X' && gameField[2][2] == 'X' && gameField[3][2] == 'X') result = 'X';
        else if (gameField[1][4] == 'X' && gameField[2][4] == 'X' && gameField[3][4] == 'X') result = 'X';
        else if (gameField[1][6] == 'X' && gameField[2][6] == 'X' && gameField[3][6] == 'X') result = 'X';
        else if (gameField[1][2] == 'X' && gameField[2][4] == 'X' && gameField[3][6] == 'X') result = 'X';
        else if (gameField[3][2] == 'X' && gameField[2][4] == 'X' && gameField[1][6] == 'X') result = 'X';
        else if (gameField[1][2] == 'O' && gameField[1][4] == 'O' && gameField[1][6] == 'O') result = 'O';
        else if (gameField[2][2] == 'O' && gameField[2][4] == 'O' && gameField[2][6] == 'O') result = 'O';
        else if (gameField[3][2] == 'O' && gameField[3][4] == 'O' && gameField[3][6] == 'O') result = 'O';
        else if (gameField[1][2] == 'O' && gameField[2][2] == 'O' && gameField[3][2] == 'O') result = 'O';
        else if (gameField[1][4] == 'O' && gameField[2][4] == 'O' && gameField[3][4] == 'O') result = 'O';
        else if (gameField[1][6] == 'O' && gameField[2][6] == 'O' && gameField[3][6] == 'O') result = 'O';
        else if (gameField[1][2] == 'O' && gameField[2][4] == 'O' && gameField[3][6] == 'O') result = 'O';
        else if (gameField[3][2] == 'O' && gameField[2][4] == 'O' && gameField[1][6] == 'O') result = 'O';
        else result = ' ';
        return result;
    }
}

class Hard_AI extends AI {
    char oppMove;

    public Hard_AI(Game game) {
        super(game);
        if (first_Move == 'X') oppMove = 'O';
        else oppMove = 'X';
    }

    @Override
    public void makeAMove() {
        int[] move = minimax(2, first_Move);
        int[] cell = {move[1], move[2]};
        game.setField(cell, first_Move);
    }

    private int[] minimax(int depth, char player) {
        char[][] testGameField = game.getField();
        List<int[]> nextMoves = generateMoves();

        int bestScore = (player == first_Move) ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        int currentScore;
        int bestRow = -1;
        int bestCol = -1;

        if (nextMoves.isEmpty() || depth == 0) {
            bestScore = evaluate();
        } else {
            for (int[] moves : nextMoves) {
                //try this move for the current player
                testGameField[moves[0]][moves[1]] = player;
                if (player == first_Move) {
                    currentScore = minimax(depth - 1, oppMove)[0];
                    if (currentScore > bestScore) {
                        bestScore = currentScore;
                        bestRow = moves[0];
                        bestCol = moves[1];
                    }
                } else {
                    currentScore = minimax(depth - 1, first_Move)[0];
                    if (currentScore < bestScore) {
                        bestScore = currentScore;
                        bestRow = moves[0];
                        bestCol = moves[1];
                    }
                }
                testGameField[moves[0]][moves[1]] = ' ';
            }
        }

        return new int[]{bestScore, bestRow, bestCol};
    }

    public char PossibleWinLose(char[][] gameField) {
        char result;
        if (gameField[1][2] == 'X' && gameField[1][4] == 'X' && gameField[1][6] == 'X') result = 'X';
        else if (gameField[2][2] == 'X' && gameField[2][4] == 'X' && gameField[2][6] == 'X') result = 'X';
        else if (gameField[3][2] == 'X' && gameField[3][4] == 'X' && gameField[3][6] == 'X') result = 'X';
        else if (gameField[1][2] == 'X' && gameField[2][2] == 'X' && gameField[3][2] == 'X') result = 'X';
        else if (gameField[1][4] == 'X' && gameField[2][4] == 'X' && gameField[3][4] == 'X') result = 'X';
        else if (gameField[1][6] == 'X' && gameField[2][6] == 'X' && gameField[3][6] == 'X') result = 'X';
        else if (gameField[1][2] == 'X' && gameField[2][4] == 'X' && gameField[3][6] == 'X') result = 'X';
        else if (gameField[3][2] == 'X' && gameField[2][4] == 'X' && gameField[1][6] == 'X') result = 'X';
        else if (gameField[1][2] == 'O' && gameField[1][4] == 'O' && gameField[1][6] == 'O') result = 'O';
        else if (gameField[2][2] == 'O' && gameField[2][4] == 'O' && gameField[2][6] == 'O') result = 'O';
        else if (gameField[3][2] == 'O' && gameField[3][4] == 'O' && gameField[3][6] == 'O') result = 'O';
        else if (gameField[1][2] == 'O' && gameField[2][2] == 'O' && gameField[3][2] == 'O') result = 'O';
        else if (gameField[1][4] == 'O' && gameField[2][4] == 'O' && gameField[3][4] == 'O') result = 'O';
        else if (gameField[1][6] == 'O' && gameField[2][6] == 'O' && gameField[3][6] == 'O') result = 'O';
        else if (gameField[1][2] == 'O' && gameField[2][4] == 'O' && gameField[3][6] == 'O') result = 'O';
        else if (gameField[3][2] == 'O' && gameField[2][4] == 'O' && gameField[1][6] == 'O') result = 'O';
        else result = ' ';
        return result;
    }

    private List<int[]> generateMoves() {
        List<int[]> newMoves = new ArrayList<int[]>();
        if (PossibleWinLose(game.getField()) == 'X' || PossibleWinLose(game.getField()) == 'O') return newMoves;
        for (int i = 1; i < 4; i++) {
            for (int j = 2; j < 7; j += 2) {
                if (game.isCellOccupied(i, j)) newMoves.add(new int[]{i, j});
            }
        }
        return newMoves;
    }

    private int evaluate() {
        int score = 0;
        score += evaluateLine(1, 2, 1, 4, 1, 6);  // row 0
        score += evaluateLine(2, 2, 2, 4, 2, 6);  // row 1
        score += evaluateLine(3, 2, 3, 4, 3, 6);  // row 2
        score += evaluateLine(1, 2, 2, 2, 3, 2);  // col 0
        score += evaluateLine(1, 4, 2, 4, 3, 4);  // col 1
        score += evaluateLine(1, 6, 2, 6, 3, 6);  // col 2
        score += evaluateLine(1, 2, 2, 4, 3, 6);  // diagonal
        score += evaluateLine(1, 6, 2, 4, 3, 2);  // alternate diagonal
        return score;
    }

    private int evaluateLine(int row1, int col1, int row2, int col2, int row3, int col3) {
        int score = 0;
        // First cell
        if (game.getOneCell(row1, col1) == first_Move) {
            score = 1;
        } else if (game.getOneCell(row1, col1) == oppMove) {
            score = -1;
        }

        // Second cell
        if (game.getOneCell(row2, col2) == first_Move) {
            if (score == 1) {   // cell1 is mySeed
                score = 10;
            } else if (score == -1) {  // cell1 is oppSeed
                return 0;
            } else {  // cell1 is empty
                score = 1;
            }
        } else if (game.getOneCell(row2, col2) == oppMove) {
            if (score == -1) { // cell1 is oppSeed
                score = -10;
            } else if (score == 1) { // cell1 is mySeed
                return 0;
            } else {  // cell1 is empty
                score = -1;
            }
        }

        // Third cell
        if (game.getOneCell(row3, col3) == first_Move) {
            if (score > 0) {  // cell1 and/or cell2 is mySeed
                score *= 10;
            } else if (score < 0) {  // cell1 and/or cell2 is oppSeed
                return 0;
            } else {  // cell1 and cell2 are empty
                score = 1;
            }
        } else if (game.getOneCell(row3, col3) == oppMove) {
            if (score < 0) {  // cell1 and/or cell2 is oppSeed
                score *= 10;
            } else if (score > 1) {  // cell1 and/or cell2 is mySeed
                return 0;
            } else {  // cell1 and cell2 are empty
                score = -1;
            }
        }
        return score;
    }
}

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
        String menu = "";
        String[] dictionary = {"user", "easy", "medium", "hard"};
        Player[] players = new Player[2];
        while (!menu.equals("exit")) {
            System.out.print("Input command: ");
            menu = r.readLine();
            if (menu.equals("exit")) break;
            String[] words = menu.split(" ");
            if (words.length != 3) {
                System.out.println("Bad parameters!");
                continue;
            }
            boolean check = (words[1].equals("user") || words[1].equals("easy") || words[1].equals("medium") || words[1].equals("hard")) && (words[2].equals("user") || words[2].equals("easy") || words[2].equals("medium") || words[2].equals("hard"));
            if (!check) {
                System.out.println("Bad parameters!");
                continue;
            }
            Game game = new Game();
            for (int i = 1, k = 0; i < 3; i++, k++) {
                for (String s : dictionary) {
                    if (words[i].equals(s)) {
                        switch (words[i]) {
                            case "user":
                                players[k] = new Player(game);
                                break;
                            case "easy":
                                players[k] = new AI(game);
                                break;
                            case "medium":
                                players[k] = new Medium_AI(game);
                                break;
                            case "hard":
                                players[k] = new Hard_AI(game);
                                break;
                        }
                    }
                }
            }
            int k = 0;
            game.outputField();
            while (game.getTurn() != 9) {
                players[k].makeAMove();
                game.turn++;
                char stateOfGame = Player.getWinGame();
                game.outputField();
                k = 1 - k;
                if (stateOfGame == ' ' && game.getTurn() == 9) {
                    System.out.println("Draw");
                    break;
                } else if (stateOfGame == 'X') {
                    System.out.println("X wins");
                    break;
                } else if (stateOfGame == 'O') {
                    System.out.println("O wins");
                    break;
                }
            }
        }
    }
}