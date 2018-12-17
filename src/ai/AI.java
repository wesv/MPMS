package ai;

public class AI {
    /*private Model brain;

    public void doMove(Model m, View toClickOn) {
        //Find Probability of safe spaces
        int xSize = m.getSize();
        int ySize = m.getSize();
        brain = m;
        Probability[][] safe = new Probability[m.getSize()][m.getSize()];

        doubleForLoop(xSize, ySize, (x, y) -> {
            if(m.isFlipped(x, y)) {
                Location here = new Location(x, y);

                int nearbyFlags = countNearbyFlags(m, here);
            }
        });


    }

    private void doubleForLoop(int x, int y, BiConsumer<Integer, Integer> action) {
        for(int i = 0; i < x; i++) {
            for(int j = 0; j < y; j++) {
                action.accept(i, j);
            }
        }
    }

    private int countNearbyFlags(Location current) {
        int count = 0;

        if(locationIsFlagged(current.diagonalUpLeft())) count++;
        if(locationIsFlagged(current.up())) count++;


        return count;
    }

    private boolean locationIs(Location space) {
        return brain.isValid(space) &&
                brain.getStatus(space) == Model.FLAGGED);
    }*/
}
