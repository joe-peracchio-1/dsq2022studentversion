package com.dsq2022.game;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Board class (Model in M-V-C)
 *
 * rule book:
 *   use this initial setup.
 *   http://veryspecial.us/free-downloads/AncientChess.com-DouShouQi.pdf
 *
 * video of game play & nice picture of initial game setup:
 *   use this (same as above) initial setup.
 *   http://ancientchess.com/page/play-doushouqi.htm
 *
 * play online:
 *   But do NOT use this setup!  Dog and wolf are interchanged.
 *   http://liacs.leidenuniv.nl/~visjk/doushouqi/
 *   Note: Dog and wolf are in different positions.
 *
 * Copyright © George J. Grevera, 2016. All rights reserved.
 *
 * Framework provided by Dr. Grevera
 * Student assignments are based around completing the logic of the project
 */
public class Board implements Serializable {
    private static final long serialVersionUID = 208041731299892L;

    // constants for the size of the Board
    public static final int   fRows = 9;  ///< no. of Board rows
    public static final int   fCols = 7;  ///< no. of Board cols

    /// the (underlying) playing surface/base. base[0][0] is the upper left corner.
    public Base[][]  base = new Base[ fRows ][ fCols ];

    /// the noveable pieces on the playing Board. piece[0][0] is the upper left corner.
    public Piece[][] piece = new Piece[ fRows ][ fCols ];

    public boolean  bluesTurn = true;  ///< by convention, blue goes first
    public boolean  moveWasCapture = false;  ///< last move resulted in a capture
    public static final boolean  universalTraps = true;  ///< all traps are universal (see below)
    //=======================================================================
    /** Init the Board. The "Board" consists of the base which doesn't change
     *  and the pieces which move.
     *  By convention, red will initially be in the top half (0,0) of the
     *  Board, and blue will start in the bottom half. be careful. the
     *  opposite sides do not mirror each other!
     *  @todo v1
     */
    public Board ( ) {
        //init the underlying Board base
        this.base[0][0] = Base.cGround;
        this.base[0][1] = Base.cGround;
        this.base[0][2] = Base.cRTrap;
        this.base[0][3] = Base.cRDen;
        this.base[0][4] = Base.cRTrap;
        this.base[0][5] = Base.cGround;
        this.base[0][6] = Base.cGround;
        this.base[1][0] = Base.cGround;
        this.base[1][1] = Base.cGround;
        this.base[1][2] = Base.cGround;
        this.base[1][3] = Base.cRTrap;
        this.base[1][4] = Base.cGround;
        this.base[1][5] = Base.cGround;
        this.base[1][6] = Base.cGround;
        this.base[2][0] = Base.cGround;
        this.base[2][1] = Base.cGround;
        this.base[2][2] = Base.cGround;
        this.base[2][3] = Base.cGround;
        this.base[2][4] = Base.cGround;
        this.base[2][5] = Base.cGround;
        this.base[2][6] = Base.cGround;
        this.base[3][0] = Base.cGround;
        this.base[3][1] = Base.cWater;
        this.base[3][2] = Base.cWater;
        this.base[3][3] = Base.cGround;
        this.base[3][4] = Base.cWater;
        this.base[3][5] = Base.cWater;
        this.base[3][6] = Base.cGround;
        this.base[4][0] = Base.cGround;
        this.base[4][1] = Base.cWater;
        this.base[4][2] = Base.cWater;
        this.base[4][3] = Base.cGround;
        this.base[4][4] = Base.cWater;
        this.base[4][5] = Base.cWater;
        this.base[4][6] = Base.cGround;
        this.base[5][0] = Base.cGround;
        this.base[5][1] = Base.cWater;
        this.base[5][2] = Base.cWater;
        this.base[5][3] = Base.cGround;
        this.base[5][4] = Base.cWater;
        this.base[5][5] = Base.cWater;
        this.base[5][6] = Base.cGround;
        this.base[6][0] = Base.cGround;
        this.base[6][1] = Base.cGround;
        this.base[6][2] = Base.cGround;
        this.base[6][3] = Base.cGround;
        this.base[6][4] = Base.cGround;
        this.base[6][5] = Base.cGround;
        this.base[6][6] = Base.cGround;
        this.base[7][0] = Base.cGround;
        this.base[7][1] = Base.cGround;
        this.base[7][2] = Base.cGround;
        this.base[7][3] = Base.cBTrap;
        this.base[7][4] = Base.cGround;
        this.base[7][5] = Base.cGround;
        this.base[7][6] = Base.cGround;
        this.base[8][0] = Base.cGround;
        this.base[8][1] = Base.cGround;
        this.base[8][2] = Base.cBTrap;
        this.base[8][3] = Base.cBDen;
        this.base[8][4] = Base.cBTrap;
        this.base[8][5] = Base.cGround;
        this.base[8][6] = Base.cGround;
        // ...

        //place the pieces
        this.piece[0][0] = Piece.rLion;
        this.piece[0][6] = Piece.rTiger;
        this.piece[1][1] = Piece.rDog;
        this.piece[1][5] = Piece.rCat;
        this.piece[2][0] = Piece.rRat;
        this.piece[2][2] = Piece.rLeopard;
        this.piece[2][4] = Piece.rWolf;
        this.piece[2][6] = Piece.rElephant;
        this.piece[6][0] = Piece.bElephant;
        this.piece[6][2] = Piece.bWolf;
        this.piece[6][4] = Piece.bLeopard;
        this.piece[6][6] = Piece.bRat;
        this.piece[7][1] = Piece.bCat;
        this.piece[7][5] = Piece.bDog;
        this.piece[8][0] = Piece.bTiger;
        this.piece[8][6] = Piece.bLion;
        // ...
    }
    //-----------------------------------------------------------------------
    /** @return the specific (moveable) piece (e.g., bWolf or rbNone) at the
     *  indicated position.
     *  @todo v1
     */
    public Piece getPiece ( int r, int c ) {
        if (r > 8 || r < 0) {
            return Piece.rbNone;
        } else if (c > 6 || c < 0) {
            return Piece.rbNone;
        }
        if (this.piece[r][c] == Piece.rbNone) {
            return Piece.rbNone;
        } else {
            return this.piece[r][c];
        }
    }
    //-----------------------------------------------------------------------
    /** @return what appears on the underlying Board base at the specified position
     *  (e.g., cWater), or cNone if out of bounds.
     *  @todo v1
     */
    public Base getBase ( int r, int c ) {
        if (r > 8 || r < 0) {
            return Base.cNone;
        } else if (c > 6 || c < 0) {
            return Base.cNone;
        }
        return this.base[r][c];
    }
    //-----------------------------------------------------------------------
    /** @return a string representing the Board that can be pretty-printed.
     *  It should look something like the following:
     *  <pre><code>
     *     --...-        --...-     \\n
     *    |      |      |      |    \\n
     *    .      .      .      .     .
     *    .      .      .      .     .
     *    .      .      .      .     .
     *    |      |      |      |    \\n
     *     --...-        --...-     \\n
     * </code></pre>
     * The left side of the string should be the underlying Board base.
     * The right side should be the pieces at their specific locations.
     * Put the first 3 characters of the name at each location
     * (e.g., rLi for the red lion, and bEl for the blue elephant).
     * If you have a better idea, please let me know!
     * @todo v1
     */
    @Override public String toString ( ) {
        String printVar = "|";
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 7; j++) {
                if (getBase(i, j) == Base.cGround) {
                    printVar +="G  ";
                } else if (getBase(i, j) == Base.cWater) {
                    printVar +="W  ";
                } else if (getBase(i, j) == Base.cRTrap) {
                    printVar +="RT ";
                } else if (getBase(i, j) == Base.cRDen) {
                    printVar +="RD ";
                } else if (getBase(i, j) == Base.cBDen) {
                    printVar +="BD ";
                } else if (getBase(i, j) == Base.cBTrap) {
                    printVar +="BT ";
                }
            }
            printVar += "|     |";
            for (int z = 0; z < 7; z++) {
                if (getPiece(i, z) == Piece.rRat) {
                    printVar +=" rRa ";
                } else if (getPiece(i, z) == Piece.rCat) {
                    printVar +=" rCa ";
                } else if (getPiece(i, z) == Piece.rDog) {
                    printVar +=" rDo ";
                } else if (getPiece(i, z) == Piece.rWolf) {
                    printVar +=" rWo ";
                } else if (getPiece(i, z) == Piece.rLeopard) {
                    printVar +=" rLe ";
                } else if (getPiece(i, z) == Piece.rTiger) {
                    printVar +=" rTi ";
                } else if (getPiece(i, z) == Piece.rLion) {
                    printVar +=" rLi ";
                } else if (getPiece(i, z) == Piece.rElephant) {
                    printVar +=" rEl ";
                } else if (getPiece(i, z) == Piece.bRat) {
                    printVar +=" bRa ";
                }else if (getPiece(i, z) == Piece.bCat) {
                    printVar +=" bCa ";
                } else if (getPiece(i, z) == Piece.bDog) {
                    printVar +=" bDo ";
                } else if (getPiece(i, z) == Piece.bWolf) {
                    printVar +=" bWo ";
                } else if (getPiece(i, z) == Piece.bLeopard) {
                    printVar +=" bLe ";
                } else if (getPiece(i, z) == Piece.bTiger) {
                    printVar +=" bTi ";
                } else if (getPiece(i, z) == Piece.bLion) {
                    printVar +=" bLi ";
                } else if (getPiece(i, z) == Piece.bElephant) {
                    printVar +=" bEl ";
                } else {
                    printVar +="  .  ";
                }
            }
            printVar += "|\n|";
        }
        return  printVar;
    }
    //=======================================================================
    /** Set the piece at the specified position (r,c).
     *  This function should NOT change the underlying Board pieces contents
     *  (e.g., cWater) at the specified location.
     *  @param r is the row
     *  @param c is the col
     *  @param p should/must be rbNone or rRat ... rElephant or bRat ...
     *         bElephant.
     *  @todo v2
     */
    public void setPiece ( int r, int c, Piece p ) {
        if (r <= 8 && r >= 0 && c <= 6 && c >= 0) {
            this.piece[r][c] = p;
        }

    }
    //-----------------------------------------------------------------------
    /** Given a piece p, return its rank (or 0 for an unknown piece).
     *  Rat is 1, cat is 2, dog is 3, wolf is 4, leopard is 5, tiger is 6,
     *  lion is 7, elephant is 8, regardless of color.
     *  @param p is a piece
     *  @return the piece's rank
     *  @todo v2
     */
    public static int getRank ( Piece p ) {
        switch (p) {
            case rRat:
            case bRat:
                return 1;
            case rCat:
            case bCat:
                return 2;
            case rDog:
            case bDog:
                return 3;
            case rWolf:
            case bWolf:
                return 4;
            case rLeopard:
            case bLeopard:
                return 5;
            case rTiger:
            case bTiger:
                return 6;
            case rLion:
            case bLion:
                return 7;
            case bElephant:
            case rElephant:
                return 8;
            default:
                return 0;
        }
    }
    //-----------------------------------------------------------------------
    /** Return the rank of the piece at the specified position (or 0 for none).
     *  Rat is 1, cat is 2, dog is 3, wolf is 4, leopard is 5, tiger is 6, lion
     *  is 7, elephant is 8, regardless of color.
     *  @return the rank of the piece at the specified position (or 0 for none).
     *  @todo v2
     */
    public int getRank ( int r, int c ) {
        if (r > 8 || r < 0) {
            return 0;
        } else if (c > 6 || c < 0) {
            return 0;
        }
        return getRank(this.piece[r][c]);
    }
    //-----------------------------------------------------------------------
    /** Returns the color of the piece (or GameColor.None).
     *  @return the color of the piece (or GameColor.None).
     *  @todo v2
     */
    public static GameColor getColor ( Piece p ) {
        if (p == Piece.rRat || p == Piece.rCat || p == Piece.rDog || p == Piece.rWolf ||
        p == Piece.rLeopard || p == Piece.rTiger || p == Piece.rLion || p == Piece.rElephant) {
            return GameColor.Red;
        } else if (p == Piece.bRat || p == Piece.bCat || p == Piece.bDog || p == Piece.bWolf ||
                p == Piece.bLeopard || p == Piece.bTiger || p == Piece.bLion || p == Piece.bElephant) {
            return GameColor.Blue;
        }
        return GameColor.None;
    }
    //-----------------------------------------------------------------------
    /** Returns the color of the piece (or GameColor.None) at the specified
     *  location.
     *  @return the color of the piece (or GameColor.None) at the specified
     *  location.
     *  @todo v2
     */
    public GameColor getColor ( int r, int c ) {
        if (r > 8 || r < 0) {
            return GameColor.None;
        } else if (c > 6 || c < 0) {
            return GameColor.None;
        }
        return getColor(this.piece[r][c]);
    }
    //-----------------------------------------------------------------------
    /** Returns t if this spot does not have any (moveable) piece on it;
     *  f otherwise or if out of bounds.
     *  @return t if this spot does not have any (moveable) piece on it;
     *  f otherwise or if out of bounds.
     *  @todo v2
     */
    public boolean isEmpty ( int r, int c ) {
        if (r > 8 || r < 0) {
            return false;
        } else if (c > 6 || c < 0) {
            return false;
        }
        if (this.piece[r][c] == Piece.rRat || this.piece[r][c] == Piece.bRat ||
                this.piece[r][c] == Piece.rCat || this.piece[r][c] == Piece.bTiger ||
                this.piece[r][c] == Piece.rDog || this.piece[r][c] == Piece.bLeopard ||
                this.piece[r][c] == Piece.rWolf || this.piece[r][c] == Piece.bWolf ||
                this.piece[r][c] == Piece.rTiger || this.piece[r][c] == Piece.bCat ||
                this.piece[r][c] == Piece.rLion || this.piece[r][c] == Piece.bDog ||
                this.piece[r][c] == Piece.rElephant || this.piece[r][c] == Piece.bElephant ||
                this.piece[r][c] == Piece.rLeopard || this.piece[r][c] == Piece.bLion) {
            return false;
        } else {
            return true;
        }
    }
    //-----------------------------------------------------------------------
    /** Count the number of rats in the water.
     *  @return the count of rats in the water.
     *  @todo v2
     */
    public int countOfRatsInWater ( ) {
        int ratCount = 0;
        for (int i = 3; i < 6; i++) {
            for (int j = 1; j < 6; j++) {
                if (j == 3) {
                    continue;
                }
                if (this.piece[i][j] == Piece.rRat || this.piece[i][j] == Piece.bRat) {
                    ratCount++;
                }
            }
        }
        return ratCount;
    }
    //=======================================================================
    // v3 (version 3): isValidMove, doMove
    //-----------------------------------------------------------------------
    /** Use these rules for game play (no variations except for the one noted
     *  below):
     *  http://veryspecial.us/free-downloads/AncientChess.com-DouShouQi.pdf
     *  This is the most challenging part of the assignment.
     *  <p>
     *  Required variation (from https://en.wikipedia.org/wiki/Jungle_(board_game)):
     *  "All traps are universal. If an animal goes into a trap in its own
     *  region, an opponent animal is able to capture it regardless of rank
     *  difference if it is beside the trapped animal."  This avoids the
     *  known draw described in http://www.chessvariants.com/other.dir/shoudouqi2.html.
     *  </p>
     *  <p>
     *  Clarification: do not allow moves/captures where the attacker "loses."
     *  For example, do not allow the mouse to attack the lion and "lose"
     *  to the lion on purpose and be removed. </p>
     *  <p>
     *  As much as possible, use the functions that you have already defined.
     *  </p>
     *  @return true if the proposed move is valid
     *  <u><i><b>regardless of whose turn it is</b></i></u>;
     *  false otherwise.
     * @todo v3
     */
    protected boolean isValidMove ( int fromRow, int fromCol, int toRow, int toCol ) {
        if (fromRow > 8 || fromRow < 0 || fromCol < 0 || fromCol > 6) {
            return false;
        } else if (toRow > 8 || toRow < 0 || toCol < 0 || toCol > 6) {
            return false;
        }
        if ((fromRow==toRow) && (fromCol == toCol)) {
            return false;
        }
        Piece originalPiece = getPiece(fromRow, fromCol);
        Piece toPiece = getPiece(toRow, toCol);

        if (originalPiece == Piece.rbNone || originalPiece == null) {
            return false;
        }
        if (toPiece == null) {
            toPiece = Piece.rbNone;
        }
        if (getColor(originalPiece) == getColor(toPiece)) {
            return false;
        }
        Base toPieceBase = getBase(toRow, toCol);
        Base fromBase = getBase(fromRow, fromCol);
        GameColor origPieceColor = getColor(originalPiece);
        if (toPieceBase == Base.cRDen || toPieceBase == Base.cBDen) {
            if (origPieceColor == GameColor.Blue && toPieceBase == Base.cBDen) {
                return false;
            } else if (origPieceColor == GameColor.Red && toPieceBase == Base.cRDen) {
                return false;
            }
        }
        if (!bluesTurn && getColor(originalPiece) == GameColor.Blue) {
            return false;
        }
        if (bluesTurn && getColor(originalPiece) != GameColor.Blue) {
            return false;
        }
        int horizDiff = fromRow - toRow;
        if ((java.lang.Math.abs(horizDiff) != 1 && java.lang.Math.abs(horizDiff) != 0) &&
                (originalPiece != Piece.rTiger && originalPiece != Piece.bTiger && originalPiece !=
                        Piece.rLion && originalPiece != Piece.bLion)) {
            return false;
        }
        int vertDiff = fromCol - toCol;
        if ((java.lang.Math.abs(vertDiff) != 1 && java.lang.Math.abs(vertDiff) != 0) &&
                (originalPiece != Piece.rTiger && originalPiece != Piece.bTiger && originalPiece !=
                        Piece.rLion && originalPiece != Piece.bLion)) {
            return false;
        }
        if (toPieceBase == Base.cBDen || toPieceBase == Base.cRDen) {
            if (origPieceColor == GameColor.Blue && toPieceBase == Base.cBDen) {
                return false;
            }
            if (origPieceColor == GameColor.Red && toPieceBase == Base.cRDen) {
                return false;
            }
        }
        if (toPieceBase == Base.cBTrap || toPieceBase == Base.cRTrap) {
            return true;
        }
        int origRank = getRank(originalPiece);
        int toRank = getRank(toPiece);
        if (origRank == 0) {
            return false;
        }
        if (toPieceBase == Base.cWater && (originalPiece != Piece.rRat && originalPiece != Piece.bRat)) {
            return false;
        }
        if (originalPiece == Piece.rTiger || originalPiece == Piece.bTiger || originalPiece ==
        Piece.rLion || originalPiece == Piece.bLion) {
            if (java.lang.Math.abs(vertDiff) == 1 || java.lang.Math.abs(horizDiff) == 1) {
                if (getRank(originalPiece) >= getRank(toPiece)) {
                    return true;
                } else {
                    return false;
                }
            }
            if (fromRow == toRow) {
                if (java.lang.Math.abs(vertDiff) > 3) {
                    return false;
                } else if (java.lang.Math.abs(horizDiff) > 4) {
                    return false;
                }
                if (countOfRatsInWater() == 0 && toPieceBase == Base.cGround) {
                    if (getRank(originalPiece) >= getRank(toPiece)) {
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    if (fromCol < toCol) {
                        for (int i = fromCol + 1; i < fromCol + 3; i++) {
                            if (getBase(fromRow, i) != Base.cWater) {
                                return false;
                            }
                            if (getPiece(fromRow, i) == Piece.rRat || getPiece(fromRow, i) ==
                                    Piece.bRat) {
                                return false;
                            }
                        }
                        if (getRank(originalPiece) >= getRank(toPiece)) {
                            return true;
                        } else {
                            return false;
                        }
                    } else {
                       for (int i = toCol - 1; i > toCol - 3; i--) {
                           if (getBase(toRow, i) != Base.cWater) {
                               return false;
                           }
                           if (getPiece(toRow, i) == Piece.rRat || getPiece(toRow, i) ==
                                   Piece.bRat) {
                               return false;
                           }
                       }
                        if (getRank(originalPiece) >= getRank(toPiece)) {
                            return true;
                        } else {
                            return false;
                        }
                    }
                }
            } else if (fromCol == toCol) {
                if (java.lang.Math.abs(vertDiff) > 3) {
                    return false;
                } else if (java.lang.Math.abs(horizDiff) > 4) {
                    return false;
                }
                if (countOfRatsInWater() == 0 && toPieceBase == Base.cGround) {
                    if (getRank(originalPiece) >= getRank(toPiece)) {
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    if (fromRow < toRow) {
                        for (int i = fromRow + 1; i < fromRow + 4; i++) {
                            if (getBase(i, fromCol) != Base.cWater) {
                                return false;
                            }
                            if (getPiece(i, fromCol) == Piece.rRat || getPiece(i, fromCol) ==
                                    Piece.bRat) {
                                return false;
                            }
                        }
                        if (getRank(originalPiece) >= getRank(toPiece)) {
                            return true;
                        } else {
                            return false;
                        }
                    } else {
                        for (int i = toRow - 1; i > toRow - 4; i--) {
                            if (getBase(i, toCol) != Base.cWater) {
                                return false;
                            }
                            if (getPiece(i, toCol) == Piece.rRat || getPiece(i, toCol) ==
                                    Piece.bRat) {
                                return false;
                            }
                        }
                        if (getRank(originalPiece) >= getRank(toPiece)) {
                            return true;
                        } else {
                            return false;
                        }
                    }
                }
            }
        }
        if (fromBase == Base.cWater && (originalPiece != Piece.rRat && originalPiece != Piece.bRat)) {
            return false;
        }
        if (originalPiece == Piece.rRat || originalPiece == Piece.bRat) {
            if (fromBase == Base.cGround && toPieceBase == Base.cWater) {
                return true;
            }
            if (toPieceBase == Base.cWater && toPiece == Piece.rbNone) {
                return true;
            }
            if (toPieceBase == Base.cGround && (toPiece == Piece.rElephant || toPiece == Piece.bElephant)
            && fromBase == Base.cWater) {
                return false;
            }
            if (toPieceBase == Base.cGround && (toPiece == Piece.rElephant || toPiece == Piece.bElephant)
            && fromBase == Base.cGround) {
                return true;
            }
        }
        if (toPiece == Piece.rbNone && toRank == 0) {
            return true;
        }
        if ((originalPiece == Piece.rElephant || originalPiece == Piece.bElephant) && (toPiece
        == Piece.rRat || toPiece == Piece.bRat)) {
            return false;
        }
        if (getRank(originalPiece) >= getRank(toPiece)) {
            return true;
        } else {
            return false;
        }
    }
    //-----------------------------------------------------------------------
    /** Perform the specified move (update the piece array), but only if it's
     *  valid!
     *
     *  Hints: You must check whose turn it is. You should call
     *  <em>isValidMove</em>. Also, set <i>moveWasCapture</i> to true iff the
     *  move is valid and it resulted in a capture; otherwise, set it to false.
     *  Also, indicate that it the other player's turn (iff valid).
     *
     *  @return true if the proposed move is valid; false otherwise.
     *  @todo v3
     */
    public boolean doMove ( int fromRow, int fromCol, int toRow, int toCol ) {
        if (fromRow > 8 || fromRow < 0 || fromCol < 0 || fromCol > 6) {
            return false;
        } else if (toRow > 8 || toRow < 0 || toCol < 0 || toCol > 6) {
            return false;
        }
        if ((fromRow == toRow) && (fromCol == toCol)) {
            return false;
        }
        if (isValidMove(fromRow, fromCol, toRow, toCol)) {
            Piece p1 = getPiece(fromRow, fromCol);
            if (p1 == null) {
                return false;
            }
            if (!bluesTurn && getColor(p1) == GameColor.Blue) {
                return false;
            }
            if (bluesTurn && getColor(p1) != GameColor.Blue) {
                return false;
            }
            if (getPiece(toRow, toCol) != null) {
                moveWasCapture = true;
            } else {
                moveWasCapture = false;
            }
            setPiece(toRow, toCol, p1);
            setPiece(fromRow, fromCol, Piece.rbNone);
            bluesTurn = !bluesTurn;
            return true;
        }
        moveWasCapture = false;
        return false;
    }
    //=======================================================================
    // v4 (version 4): countBlue, countRed, isRedWinner, isBlueWinner,
    //                 copy ctor, equals, equalsBoard (?)
    // -----------------------------------------------------------------------
    /** @return the number of blue pieces remaining.
     *  @todo v4
     */
    public int countBlue ( ) {
        int count = 0;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 7; j++) {
                Piece p1 = getPiece(i, j);
                if (getColor(p1) == GameColor.Blue) {
                    count++;
                }
            }
        }
        return count;
    }
    //-----------------------------------------------------------------------
    /** @returns the number of red pieces remaining.
     *  @todo v4
     */
    public int countRed ( ) {
        int count = 0;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 7; j++) {
                Piece p1 = getPiece(i, j);
                if (getColor(p1) == GameColor.Red) {
                    count++;
                }
            }
        }
        return count;
    }
    //-----------------------------------------------------------------------
    /** @return true if red is a winner (regardless of whose turn it is);
     *  false otherwise.
     *  accomplish this by checking if a red piece is in the blue den
     *  or when red has remaining pieces and blue does not.
     *  @todo v4
     */
    public boolean isRedWinner ( ) {
        if (countBlue() == 0 && countRed() != 0) {
            return true;
        } else if (getColor(getPiece(8, 3)) == GameColor.Red && (getBase(8, 3) == Base.cBDen)) {
            return true;
        } else {
            return false;
        }
    }
    //-----------------------------------------------------------------------
    /** @return true if blue is a winner (regardless of whose turn it is);
     *  false otherwise.
     *  accomplish this by checking if a blue piece is in the red den
     *  or when blue has remaining pieces and red does not.
     *  @todo v4
     */
    public boolean isBlueWinner ( ) {
        if (countRed() == 0 && countBlue() != 0) {
            return true;
        } else if ((getColor(getPiece(0, 3)) == GameColor.Blue) && (getBase(0, 3) == Base.cRDen)) {
            return true;
        } else {
            return false;
        }
    }
    //-----------------------------------------------------------------------
    /** copy ctor.
     *  make this a separate, independent (deep) copy of the original.
     *  @param original is the original Board to copy.
     *  @todo v4
     */
    public Board ( final Board original ) {
        super();
        this.piece = original.piece;
        this.base = original.base;
        this.moveWasCapture = original.moveWasCapture;
        this.bluesTurn = original.bluesTurn;
    }
    //-----------------------------------------------------------------------
    /** this is a "proper" equals method.
     *  @return true if the Board _objects_ are equal; false otherwise.
     *  @todo v4
     */
    @Override
    public boolean equals( Object other ) {
        if (other == null) {
            return false;
        }
        if (!(other instanceof Board)) {
            return false;
        }
        if(!(this.equals(((Board) other).piece))) {
            return false;
        }
        if (this.bluesTurn != ((Board) other).bluesTurn) {
            return false;
        }
        if (this.moveWasCapture != ((Board) other).moveWasCapture) {
            return false;
        }
        return true;
    }
    //-----------------------------------------------------------------------
    /** this is NOT a "proper" equals method but can be used by a "proper" one.
     *  do not consider anything else for equality; only consider the array
     *  contents. you may assume that this.piece and otherPiece are both not
     *  null, properly initialized, and both of the same size.
     *  @return true if the piece array _contents_ are equal; false otherwise.
     *  @todo v4
     */
    @PublicForTesting( shouldBe="private" )
    public boolean equals ( Piece[][] otherPiece ) {
        for (int i = 0; i < otherPiece.length; i++) {
            for (int j = 0; j < otherPiece[i].length; j++) {
                if (!(this.piece[i][j] == otherPiece[i][j])) {
                    return false;
                }
            }
        }
        return true;
    }
    //-----------------------------------------------------------------------
    // **** this function is not required / tested / necessary.
    // **** students may assume that the underlying Board base is always the same. ****
    //
    // this is NOT a "proper" equals method but can be used by a "proper" one.
    // return true if the piece array _contents_ are equal; false otherwise.
    // do not consider anything else for equality; only consider the array contents.
    // you may assume that this.piece and otherPiece are both not null, properly
    // initialized, and both of the same size.
    @PublicForTesting( shouldBe="private" )
    public boolean equals ( Base[][] otherBase ) {
        return false;
    }
    //=======================================================================
    // v5 (version 5): hashCode, gameOver (mention change to doMove to call
    // gameOver)
    //=======================================================================
    /** this function deteremines if the game is over.
     *  <pre> when is the game over? the game is over when:
     *      a. blue is a winner, or
     *      b. red is a winner,  or when
     *      c. blue or red don't have any pieces left. </pre>
     *  @return true if the game is over; false otherwise.
     *  @todo v5
     */
    public boolean gameOver ( ) {
        return false;
    }
    //-----------------------------------------------------------------------
    /** this function overrides the default hashCode function.
     *  <p> from "Effective Java" by J. Bloch:
     *  "Item 9: Always override hashCode when you override equals" </p>
     * 
     *  <p> use the string algorithm from
     *  https://en.wikipedia.org/wiki/Java_hashCode%28%29#The_java.lang.String_hash_function.
     *  simply treat the individual piece values (i.e., piece[r][c].ordinal()) as the
     *  individual character values. (DO NOT use charAt on the string returned
     *  from toString for this function.) also include the value of mBlacksTurn
     *  as described below as the last (i.e., nth) character. </p>
     * 
     *  <p> DO NOT include anything else (just the piece array contents and then bluesTurn).
     *  It quickly becomes too complicated. </p>
     *
     *  @return the calculated hash code.
     *  @todo v5
     */
    @Override
    public int hashCode ( ) {
        return -1;
    }
    //=======================================================================
    /** create a list of all possible moves given this as the starting
     *  position.
     *  @return the list of all possible moves.
     *  @todo v6
     */
    public ArrayList< Board > suggest ( ) {
        ArrayList< Board > list = new ArrayList<>();
        for (int i = 0; i < fRows; i++) {
            for (int j = 0; j < fCols; j++) {
                Piece temp = getPiece(i, j);
                if (temp == Piece.rbNone || temp == null) {
                    continue;
                }
                if (temp == Piece.rTiger || temp == Piece.rLion || temp == Piece.bTiger ||
                temp == Piece.bLion) {
                    //horiztal jump check
                    if (j == 0 && (i > 2 && i < 6)) {
                        if (isValidMove(i, j, i, j+3)) {
                            Piece toPiece = getPiece(i, j+3);
                            if (toPiece == null) {
                                toPiece = Piece.rbNone;
                            }
                            doMove(i, j, i, j+3);
                            list.add(this.cloneBoard());
                            this.setPiece(i, j+3, toPiece);
                            this.setPiece(i, j, temp);
                            bluesTurn = !bluesTurn;
                        }
                    } else if (j == 6 && (i > 2 && i < 6)) {
                        if (isValidMove(i, j, i, j-3)) {
                            Piece toPiece = getPiece(i, j-3);
                            if (toPiece == null) {
                                toPiece = Piece.rbNone;
                            }
                            doMove(i, j, i, j-3);
                            list.add(this.cloneBoard());
                            this.setPiece(i, j-3, toPiece);
                            this.setPiece(i, j, temp);
                            bluesTurn = !bluesTurn;
                        }
                    } else if (j == 3 && (i > 2 && i < 6)) {
                        if (isValidMove(i, j, i, j+3)) {
                            Piece toPiece = getPiece(i, j+3);
                            if (toPiece == null) {
                                toPiece = Piece.rbNone;
                            }
                            doMove(i, j, i, j+3);
                            list.add(this.cloneBoard());
                            this.setPiece(i, j+3, toPiece);
                            this.setPiece(i, j, temp);
                            bluesTurn = !bluesTurn;
                        }
                        if (isValidMove(i, j, i, j-3)) {
                            Piece toPiece = getPiece(i, j-3);
                            if (toPiece == null) {
                                toPiece = Piece.rbNone;
                            }
                            doMove(i, j, i, j-3);
                            list.add(this.cloneBoard());
                            this.setPiece(i, j-3, toPiece);
                            this.setPiece(i, j, temp);
                            bluesTurn = !bluesTurn;
                        }
                    }
                    //vertical jump check
                    if (i == 2 && ((j > 0 && j < 3) || (j > 3 && j < 6))) {
                        if (doMove(i, j, i+4, j)) {
                            Piece toPiece = getPiece(i+4, j);
                            if (toPiece == null) {
                                toPiece = Piece.rbNone;
                            }
                            doMove(i, j, i+4, j);
                            list.add(this.cloneBoard());
                            this.setPiece(i+4, j, toPiece);
                            this.setPiece(i, j, temp);
                            bluesTurn = !bluesTurn;
                        }
                    } else if (i == 6 && ((j > 0 && j < 3) || (j > 3 && j < 6))) {
                        if (doMove(i, j, i-4, j)) {
                            Piece toPiece = getPiece(i-4, j);
                            if (toPiece == null) {
                                toPiece = Piece.rbNone;
                            }
                            doMove(i, j, i-4, j);
                            list.add(this.cloneBoard());
                            this.setPiece(i-4, j, toPiece);
                            this.setPiece(i, j, temp);
                            bluesTurn = !bluesTurn;
                        }
                    }
                }
                if (isValidMove(i, j, i-1, j)) {
                    Piece toPiece = getPiece(i-1, j);
                    if (toPiece == null) {
                        toPiece = Piece.rbNone;
                    }
                    doMove(i, j, i-1, j);
                    list.add(this.cloneBoard());
                    this.setPiece(i-1, j, toPiece);
                    this.setPiece(i, j, temp);
                    bluesTurn = !bluesTurn;
                }
                if (isValidMove(i, j, i+1, j)) {
                    Piece toPiece = getPiece(i+1, j);
                    if (toPiece == null) {
                        toPiece = Piece.rbNone;
                    }
                    doMove(i, j, i+1, j);
                    list.add(this.cloneBoard());
                    this.setPiece(i+1, j, toPiece);
                    this.setPiece(i, j, temp);
                    bluesTurn = !bluesTurn;
                }
                if (isValidMove(i, j, i, j-1)) {
                    Piece toPiece = getPiece(i, j-1);
                    if (toPiece == null) {
                        toPiece = Piece.rbNone;
                    }
                    doMove(i, j, i, j-1);
                    list.add(this.cloneBoard());
                    this.setPiece(i, j-1, toPiece);
                    this.setPiece(i, j, temp);
                    bluesTurn = !bluesTurn;
                }
                if (isValidMove(i, j, i, j+1)) {
                    Piece toPiece = getPiece(i, j+1);
                    if (toPiece == null) {
                        toPiece = Piece.rbNone;
                    }
                    doMove(i, j, i, j+1);
                    list.add(this.cloneBoard());
                    this.setPiece(i, j+1, toPiece);
                    this.setPiece(i, j, temp);
                    bluesTurn = !bluesTurn;
                }
            }
        }
        return list;
    }
    //clone
    public Board cloneBoard() {
        Board updated = new Board();
        for (int i = 0; i < fRows; i++) {
            for (int j = 0; j < fCols; j++) {
                updated.setPiece(i, j, this.getPiece(i, j));
            }
        }
        return updated;
    }
    //-----------------------------------------------------------------------
    public static void serialize ( String out, ArrayList< Board > list ) {
        FileOutputStream fout;
        try {
            fout = new FileOutputStream( out );
        } catch (Exception e) {
            System.err.println( "MyBoard.serialize: failed to open file output stream " + e );
            return;
        }
        ObjectOutputStream oout;
        try {
            oout = new ObjectOutputStream( fout );
        } catch (Exception e) {
            System.err.println( "MyBoard.serialize: failed to open object output stream " + e );
            return;
        }
        try {
            oout.writeObject( list );
        } catch (Exception e) {
            System.err.println( "MyBoard.serialize: failed to write object(s) " + e );
            return;
        }

        //save toString versions of the Boards as well.
        String s = "\n\n";
        for (Board b : list)    s += b;
        s += "\n";
        try {
            oout.writeObject( s );
        } catch (Exception e) {
            System.err.println( "MyBoard.serialize: failed to write object(s) " + e );
            return;
        }

        try {
            oout.close();
            fout.close();
        } catch (Exception ignored) { }
    }
    //-----------------------------------------------------------------------
    @SuppressWarnings( "unchecked" )  //should use try-catch instead but i'm lazy.
    public static ArrayList< Board > deserialize ( String in ) {
        FileInputStream fin;
        try {
            fin = new FileInputStream( in );
        } catch ( Exception e) {
            System.err.println( "MyBoard.deserialize: failed to open file input stream " + e );
            return null;
        }
        ObjectInputStream oin;
        try {
            oin = new ObjectInputStream( fin );
        } catch (Exception e) {
            System.err.println( "MyBoard.serialize: failed to open object input stream " + e );
            return null;
        }

        ArrayList< Board > list;
        try {
            list = (ArrayList< Board >) oin.readObject();
        } catch (Exception e) {
            System.err.println( "MyBoard.serialize: failed to read object(s) " + e );
            return null;
        }

        String s;
        try {
            s = (String) oin.readObject();
        } catch (Exception e) {
            System.err.println( "MyBoard.serialize: failed to read object(s) " + e );
            return null;
        }
        System.out.println( s );

        try {
            oin.close();
            fin.close();
        } catch (Exception ignored) { }

        return list;
    }
    //=======================================================================
    /** call suggest() and choose the best from the last.
     *  to start, you may choose one at random but you will NOT receive any
     *  credit for that approach.
     *  @return the best
     *  @todo v7
     */
    public Board chooseBest ( ) {
        ArrayList< Board > list = suggest();
        Board best = null;

        //todo: add your code here.
        // call h for each element in list and pick the best.

        return best;
    }

    //define infinity with a little headroom
    public static final int INF = Integer.MAX_VALUE / 2;

    //this function evaluates the current Board piece configuration, b, and returns
    // a value in [-INF, ..., +INF] where +INF indicates a winner, -INF is a
    // loser, and values in between are better (greater than) or worse (less
    // than) a particular alternative.
    //
    // question: "Which player is the maximizing one?" Stated another way,
    // "If bluesTurn is true and the current Board being evaluated by h is a
    // winner for red, should that be assigned -INF or +INF?"
    //
    // answer: +INF because we arrived at this current state after red moved
    // and the game would end before blue gets the next turn (and similarly
    // for the case when bluesTurn is false, a blue winner is assigned +INF.
    //
    // consider the converse. "If bluesTurn is true and the current Board
    // being evaluated by h is a winner for blue, should that be assigned
    // -INF or +INF?" -INF because we arrived at this current state after
    // red moved and red lost.
    //
    // of course, if there is no clear winner, h should return a value in
    // between -INF and +INF with values biased towards the favorite (the
    // maximizing player).
    public static int h ( Board b ) {
        if (b.bluesTurn) {  //it's blues turn next (after this)
            //eval for red (because red moved to get to this position)
            if (b.isRedWinner())   return INF;
            if (b.isBlueWinner())  return -INF;
            //this is where the hard work begins. calc a value between
            // [-INF,        ..., +INF]
            // [blue winner, ..., red winner]
            // [red loser,   ..., red winner]
            //Evaluate e = new Evaluate( b );
        } else {  //it's reds turn next (after this)
            //eval for blue (because blue moved to get to this position)
            if (b.isBlueWinner())  return INF;
            if (b.isRedWinner())   return -INF;
            //this is where the hard work begins. calc a value between
            // [-INF,       ..., +INF]
            // [red winner, ..., blue winner]
            // [blue loser, ..., blue winner]
            //Evaluate e = new Evaluate( b );
        }
        return 0;
    }

}  //end class Board
