package com.koreait.basic.board.cmt.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardCmtEntity {
    private int icmt;
    private int iboard;
    private String ctnt;
    private int writer;
    private String rdt;
}
