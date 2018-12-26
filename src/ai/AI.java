package ai;

import logic.Array2D;
import logic.Location;
import ui.Controller;
import ui.Model;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class AI {
    private Controller _c;


    public AI(Controller toClickOn) {
        _c = toClickOn;

    }

    public void advancedMove() {
        Model _memory = _c.getModel();

        int xSize = _memory.getSize();
        int ySize = _memory.getSize();

        AtomicInteger numUnflippedTiles = new AtomicInteger(0);
        /* Calculate total number of bomb arrangements */
        doubleForLoop(xSize, ySize, (x, y) -> {
            if(locationIs(Model.Status.UNFLIPPED, new Location(x, y)))
                numUnflippedTiles.incrementAndGet();
        });

        //TODO implement Stirlings Approximation

    }

    public void doMove() {
        Model _memory = _c.getModel();

        //Find Probability of mines spaces
        int xSize = _memory.getSize();
        int ySize = _memory.getSize();
        Array2D<Probability> mines = new Array2D<>(_memory.getSize());

        /* Initialize Safe */
        doubleForLoop(xSize, ySize, (x, y) ->
            mines.putAt(new Location(x, y), new Probability(0).setAsVirgin())
        );

        /* Find all mine tiles */
        doubleForLoop(xSize, ySize, (x, y) -> {
            if(_memory.isFlipped(x, y)) {
                Location here = new Location(x, y);

                /* Count number of surrounding flags */
                LambdaObject<Integer> nearbyFlags = new LambdaObject<>(0);
                doFunctionAroundLocation(here, Model.Status.FLAGGED, (location) ->
                    nearbyFlags.set(nearbyFlags.get() + 1)
                );

                /* If all flags are accounted for, it is safe to click */
                if(nearbyFlags.get() == _memory.getStatus(here).value()) {
                    doFunctionAroundLocation(here, Model.Status.UNFLIPPED, location -> {
                        mines.putAt(location, new Probability(1).negate());
                        System.out.println(location + "exited at -1 ("  + mines.at(location) + ")");
                    });
                }
                /* Else if only a few flags are accounted for */
                else if(nearbyFlags.get() < _memory.getStatus(here).value()) {
                    Double difference = (double) (_memory.getStatus(here).value() - nearbyFlags.get());

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
                            Probability newProbability = mines.at(location).multiply(new Probability(p));
                            mines.putAt(location, newProbability);
                            System.out.println(location + "exited at multiply ("  + mines.at(location) + ")");
                        });
                    }
                }
                else {
                    System.err.println("Not a valid state");
                }
            }
        });

        for(int i = 0; i < xSize; i++) {
            for (int j = 0; j < ySize; j++)
            {
                System.out.print(String.format("%7s | ", mines.at(i, j).toString()));
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
        return _c.getModel().isValid(space) &&
                _c.getModel().getStatus(space) == type;
    }
}
