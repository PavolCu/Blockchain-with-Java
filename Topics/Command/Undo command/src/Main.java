
interface Movable {
    int getX();

    int getY();

    void setX(int newX);

    void setY(int newY);
}

interface Storable {
    int getInventoryLength();

    String getInventoryItem(int index);

    void setInventoryItem(int index, String item);

}

interface Command {
    void execute();

    void undo();
}

class CommandMove implements Command {
    Movable entity;
    int xMovement;
    int yMovement;
    int previousX;
    int previousY;

    @Override
    public void execute() {
        previousX = entity.getX();
        previousY = entity.getY();
        entity.setX(previousX + xMovement);
        entity.setY(previousY + yMovement);
    }

    @Override
    public void undo() {
        entity.setX(previousX);
        entity.setY(previousY);
    }
}

class CommandPutItem implements Command {
    Storable entity;
    String item;
    int previousSlot;

    @Override
    public void execute() {
        for (int i = 0; i < entity.getInventoryLength(); i++) {
            if (entity.getInventoryItem(i) == null) {
                previousSlot = i;
                entity.setInventoryItem(i, item);
                break;
            }
        }
    }

    @Override
    public void undo() {
        entity.setInventoryItem(previousSlot, null);
    }
}
