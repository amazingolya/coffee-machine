class Move {
    public static void moveRobot(Robot robot, int toX, int toY) {
        final int pathX = toX - robot.getX();
        final int pathY = toY - robot.getY();

            final Move move = new Move();
            move.turnInDirectionX(pathX > 0, robot);
            move.moveInOneDirection(Math.abs(pathX), robot);
            move.turnInDirectionY(pathY > 0, robot);
            move.moveInOneDirection(Math.abs(pathY), robot);


    }

    private void moveInOneDirection(final int path, final Robot robot) {
        for (int i = 0; i < path; i++) {
            robot.stepForward();
        }
    }

    private void turnInDirectionX(final boolean isPositive, final Robot robot) {
        final Direction currentDirection = robot.getDirection();
        switch (currentDirection) {
            case UP: {
                if (isPositive) robot.turnRight();
                else robot.turnLeft();
                break;
            }
            case DOWN: {
                if (isPositive) robot.turnLeft();
                else robot.turnRight();
                break;
            }
            case LEFT: {
                if (isPositive) {
                    robot.turnRight();
                    robot.turnRight();
                }
                break;
            }
            case RIGHT: {
                if (isPositive) {
                    robot.turnLeft();
                    robot.turnLeft();
                }
                break;

            }
        }
    }

    private void turnInDirectionY(final boolean isPositive, final Robot robot) {
        final Direction currentDirection = robot.getDirection();
        switch (currentDirection) {
            case UP: {
                if (isPositive) {
                    robot.turnRight();
                    robot.turnRight();
                    break;
                }
            }
            case DOWN: {
                if (isPositive) {
                    robot.turnLeft();
                    robot.turnLeft();
                    break;
                }
            }
            case LEFT: {
                if (isPositive) robot.turnRight();
                else robot.turnLeft();
                break;
            }
            case RIGHT: {
                if (isPositive) robot.turnLeft();
                else robot.turnRight();
                break;
            }
        }
    }
}

//Don't change code below

enum Direction {
    UP(0, 1),
    DOWN(0, -1),
    LEFT(-1, 0),
    RIGHT(1, 0);

    private final int dx;
    private final int dy;

    Direction(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public Direction turnLeft() {
        switch (this) {
            case UP:
                return LEFT;
            case DOWN:
                return RIGHT;
            case LEFT:
                return DOWN;
            case RIGHT:
                return UP;
            default:
                throw new IllegalStateException();
        }
    }

    public Direction turnRight() {
        switch (this) {
            case UP:
                return RIGHT;
            case DOWN:
                return LEFT;
            case LEFT:
                return UP;
            case RIGHT:
                return DOWN;
            default:
                throw new IllegalStateException();
        }
    }

    public int dx() {
        return dx;
    }

    public int dy() {
        return dy;
    }
}

class Robot {
    private int x;
    private int y;
    private Direction direction;

    public Robot(int x, int y, Direction direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    public void turnLeft() {
        direction = direction.turnLeft();
    }

    public void turnRight() {
        direction = direction.turnRight();
    }

    public void stepForward() {
        x += direction.dx();
        y += direction.dy();
    }

    public Direction getDirection() {
        return direction;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}