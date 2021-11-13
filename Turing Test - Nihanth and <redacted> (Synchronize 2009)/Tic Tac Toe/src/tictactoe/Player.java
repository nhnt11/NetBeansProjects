package tictactoe;

public class Player {
    
    String name;
    String XorO;
    static final String X = "X";
    static final String O = "O";
    public Player(String name, String XorO) {
        this.name = name;
        this.XorO = XorO;
    }
    String getXorO() {
        return XorO;
    }
    String getName() {
        return name;
    }
    void setName(String name) {
        this.name = name;
    }
    void setXorO(String XorO) {
        this.XorO = XorO;
    }
}