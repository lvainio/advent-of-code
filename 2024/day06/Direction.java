public enum Direction {
    UP(0, -1),   
    DOWN(0, 1),  
    LEFT(-1, 0), 
    RIGHT(1, 0); 

    private final int dr; 
    private final int dc; 

    Direction(int dr, int dc) {
        this.dr = dr;
        this.dc = dc;
    }

    public Direction turnRight() {
        switch (this) {
            case UP -> {
                return RIGHT;
            }
            case RIGHT -> {
                return DOWN;
            }
            case DOWN -> {
                return LEFT;
            }
            case LEFT -> {
                return UP;
            }
            default -> throw new IllegalArgumentException("Unexpected direction: " + this);
        }
    }

    public int getDr() {
        return dr;
    }

    public int getDc() {
        return dc;
    }
}