package ai;

import logic.Array2D;
import logic.Location;
import ui.Model;
import ui.View;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class AI {
    private Model memory;
    private View view;


    public AI(Model m, View toClickOn) {
        memory = m;
        view = toClickOn;

    }

    public void advancedMove(Model m) {
        memory = m;

        int xSize = memory.getSize();
        int ySize = memory.getSize();

        AtomicInteger numUnflippedTiles = new AtomicInteger(0);
        /* Calculate total number of bomb arrangements */
        doubleForLoop(xSize, ySize, (x, y) -> {
            if(locationIs(Model.Status.UNFLIPPED, new Location(x, y)))
                numUnflippedTiles.incrementAndGet();
        });

        //TODO implement Stirlings Approximation

    }

    public void doMove(Model m) {
        memory = m;

        //Find Probability of mines spaces
        int xSize = memory.getSize();
        int ySize = memory.getSize();
        Array2D<Probability> mines = new Array2D<>(memory.getSize());

        /* Initialize Safe */
        //doubleForLoop(xSize, ySize, (x, y) ->
        //    mines.putAt(new Location(x, y), new Probability(0).setAsVirgin())
        //);

        /* Find all mine tiles */
        doubleForLoop(xSize, ySize, (x, y) -> {
            Location here = new Location(x, y);

            if(memory.isFlipped(x, y)) {
                /* Count number of surrounding flags */
                LambdaObject<Integer> nearbyFlags = new LambdaObject<>(0);
                doFunctionAroundLocation(here, Model.Status.FLAGGED, (location) ->
                    nearbyFlags.set(nearbyFlags.get() + 1)
                );

                /* If all flags are accounted for, it is safe to click */
                if(nearbyFlags.get() == memory.getStatus(here).value()) {
                    doFunctionAroundLocation(here, Model.Status.UNFLIPPED, location -> {
                        mines.putAt(location, new Probability(Integer.MIN_VALUE));
                        System.out.println(location + "exited at -1 ("  + mines.at(location) + ")");
                    });
                }
                /* Else if only a few flags are accounted for */
                else if(nearbyFlags.get() < memory.getStatus(here).value()) {
                    Double difference = (double) (memory.getStatus(here).value() - nearbyFlags.get());

                    /* Count the number of Unflipped Tiles around the location */
                    LambdaObject<Integer> numUnflippedTiles = new LambdaObject<>(0);
                    doFunctionAroundLocation(here, Model.Status.UNFLIPPED, location ->
                        numUnflippedTiles.set(numUnflippedTiles.get() + 1)
                    );

                    /* If the number of Unflipped tiles is equal to
                        the number at this tile, it is not mines
                     */
                    if(difference == (double)numUnflippedTiles.get()) {
                        doFunctionAroundLocation(here, Model.Status.UNFLIPPED, location -> {
                            mines.putAt(location, new Probability(1));
                            System.out.println(location + "exited at 1 ("  + mines.at(location) + ")");
                        });
                    }
                    else {
                        doFunctionAroundLocation(here, Model.Status.UNFLIPPED, location -> {
                            double p = difference / numUnflippedTiles.get();
                            Probability newProbability = new Probability(p).multiply(mines.at(location));
                            mines.putAt(location, newProbability);
                            System.out.println(location + "exited at multiply ("  + mines.at(location) + ")");
                        });
                    }
                }
                else {
                    System.err.println("Not a valid state");
                }
            } else if(memory.getStatus(x, y) == Model.Status.UNFLIPPED) {
                mines.putAt(here, new Probability(0).multiply(mines.at(here)));
            }
        });

        for(int i = 0; i < xSize; i++) {
            for (int j = 0; j < ySize; j++)
            {
                System.out.print(String.format("%7s | ", mines.at(i, j)));
            }
            System.out.println();
        }
        System.out.println("-----------------------------------------\n\n");

    }

    private void doubleForLoop(int x, int y, BiConsumer<Integer, Integer> action) {
        for(int i = 0; i < x; i++) {
            for(int j = 0; j < y; j++) {
                action.accept(i, j);
            }
        }
    }

    private void doFunctionAroundLocation(Location start, Model.Status statusToLookFor, Consumer<Location> response) {
        if(locationIs(statusToLookFor, start.diagonalUpLeft())) response.accept(start.diagonalUpLeft());
        if(locationIs(statusToLookFor, start.up())) response.accept(start.up());
        if(locationIs(statusToLookFor, start.diagonalUpRight())) response.accept(start.diagonalUpRight());
        if(locationIs(statusToLookFor, start.left())) response.accept(start.left());
        if(locationIs(statusToLookFor, start.right())) response.accept(start.right());
        if(locationIs(statusToLookFor, start.diagonalDownLeft())) response.accept(start.diagonalDownLeft());
        if(locationIs(statusToLookFor, start.down())) response.accept(start.down());
        if(locationIs(statusToLookFor, start.diagonalDownRight())) response.accept(start.diagonalDownRight());
    }

    private boolean locationIs(Model.Status type, Location space) {
        return memory.isValid(space) &&
                memory.getStatus(space) == type;
    }
}
