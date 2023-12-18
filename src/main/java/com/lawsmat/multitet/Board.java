package com.lawsmat.multitet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class Board {
    private final LinkedList<Brick> entryQueue = new LinkedList<>();
    private final LinkedList<Brick> onScreen = new LinkedList<>();
    private final float[][][] board = new float[20][10][3];
    private Board nextBoard = null;
    private boolean active = true;
    private int t = 0;

    private static final boolean DEBUG_MODE = false;

    public static final float[] background = {0, 0, 0};

    public boolean isEmptyCell(int[] loc) {
        if(loc[0] >= board[0].length || loc[0] < 0 || loc[1] >= board.length)
            return false;
        return Arrays.equals(board[loc[1]][loc[0]], background);
    }

    @SuppressWarnings("unchecked")
    public void tick(int down, int right, boolean controlledOnly, boolean rotate) {
        if(!active) return;
        var anythingMoved = false;
        ArrayList<int[]> redSquares = new ArrayList<>();
        for(var brick : onScreen) {
            if(brick != getControlledPiece() && controlledOnly) continue;
            // can we possibly move to the target?
            var art = brick.getArt();
            var locationsNow = new ArrayList<int[]>();
            var destinations = new ArrayList<int[]>();
            for(int r = 0; r < art.length; r++) {
                for(int c = 0; c < art[r].length; c++) {
                    if(art[r][c] == 0)
                        continue;
                    locationsNow.add(new int[]{c + brick.getX(), r + brick.getY()});
                    destinations.add(new int[]{c + right + brick.getX(), r + down + brick.getY()});
                }
            }
            // can we rotate?
            if(rotate) {
                // what would the rotation look like?
                var rotated = brick.rotate();
                var rart = rotated.getArt();

                // check all the spots we need
                var ok = true;
                for(int r = 0; r < rart[0].length; r++) {
                    for(int c = 0; c < rart.length; c++) {
                        if(rart[c][r] == 1) {
                            var d = new int[]{rotated.getX() + c, rotated.getY() + r};
                            var alreadyThere = false;
                            for(var ln : locationsNow) {
                                if (Arrays.equals(ln, d)) {
                                    alreadyThere = true;
                                    break;
                                }
                            }
                            if(!isEmptyCell(d) && !alreadyThere) {
                                redSquares.add(d);
                                ok = false;
                            }
                        }
                    }
                }

                if(ok) {
                    // since only the brick at the end of the queue can be rotated, we just pop+push a new one on
                    onScreen.removeLast();
                    onScreen.addLast(rotated);
                    brick = rotated;
                }
            }
            var stuck = false;
            for(var d : destinations) {
                boolean alreadyThere = false;
                for(var loc : locationsNow) {
                    if (Arrays.equals(loc, d)) {
                        alreadyThere = true;
                        break;
                    }
                }
                if(
                        // if something's there already and it's not us,
                        !alreadyThere && !isEmptyCell(d)
                ) {
                    // we're stuck :(
                    if(DEBUG_MODE && d[1] < board.length && d[0] < board[0].length) {
                        redSquares.add(d.clone());
                    }
                    stuck = true;
                    break;
                }
            }
            if(!stuck) {
                brick.setY(brick.getY() + down);
                brick.setX(brick.getX() + right);
                anythingMoved = true;
            }
        }
        for(int r = 0; r < board.length; r++) {
            for(int c = 0; c < board[r].length; c++) {
                board[r][c] = new float[]{0,0,0};
            }
        }
        for (var brick : onScreen) {
            // calculate board colors
            var art = brick.getArt();
            for (int r = 0; r < art.length; r++) {
                for (int c = 0; c < art[r].length; c++) {
                    if(art[r][c] == 1 && r + brick.getY() < board.length && c + brick.getX() < board[0].length) {
                        board[r+brick.getY()][c+brick.getX()] = brick.getColor();
                    }
                }

            }
        }

        if(!controlledOnly && !anythingMoved && entryQueue.peek() != null) {
            var newBrick = entryQueue.peek();
            var a = newBrick.getArt();

            check_loop:
            for(int r = 0; r < a.length; r++) {
                for(int c = 0; c < a.length; c++) {
                    if(!isEmptyCell(new int[]{c + newBrick.getX(), r + newBrick.getY()})) {
                        active = false;
                        break check_loop;
                    }
                }
            }
            onScreen.add(entryQueue.poll());
        }

        // what if a row is full?
        for(int r = 0; r < board.length; r++) {
            var full = true;
            for (int c = 0; c < board[0].length; c++) {
                if (isEmptyCell(new int[]{c, r}))
                    full = false;
            }
            if(full) {
                var nb = getNextBoard();
                if(nb == null) {
                    addBoard();
                    nb = getNextBoard();
                }
                for(var b : (LinkedList<Brick>) onScreen.clone()) {
                    var art = b.getArt();

                    for(int r2d2 = 0; r2d2 < art.length; r2d2++) {
                        if(r2d2 + b.getY() == r) {
                            if(onScreen.remove(b)) {
                                nb.addBrick(b);
                                break;
                            }
                        }
                    }
                }
            }
        }


        for(var s : redSquares) {
            board[s[1]][s[0]] = new float[]{1.0f, 0, 0};
        }

        t++;
    }

    public Board getNextBoard() {
        return nextBoard;
    }

    public void addBoard() {
        if(nextBoard != null) {
            nextBoard.addBoard();
            return;
        }
        nextBoard = new Board();
    }

    public float[][][] getBoard() {
        return board;
    }

    public void addBrick(Brick b) {
        b.setY(0);
        b.setX(3);
        entryQueue.add(b);
    }

    public boolean isActive() {
        return active;
    }

    public Brick getControlledPiece() {
        return onScreen.getLast();
    }

    public LinkedList<Brick> getEntryQueue() {
        return entryQueue;
    }
}
