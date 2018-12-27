package ai;

import logic.Array2D;
import logic.Location;
import ui.Controller;
import ui.Model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class AI {
    private Controller _c;
    /* the probability, where if its higher to flag */
    private double flag_confidence = 0.96;
    private double click_confidence = 0.40;
    private FileWriter out;

    public AI(Controller toClickOn) {
        _c = toClickOn;
        try {
            out = new FileWriter(new File("ai_mind.log"));
        } catch (IOException e) {
            e.printStackTrace();
        }

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

    public void executeMove() {
        Model _memory = _c.getModel();

        //Find Probability of mines spaces
        int xSize = _memory.getSize();
        int ySize = _memory.getSize();
        Array2D<Probability> mines = new Array2D<>(_memory.getSize());

        /* ********************** Find Probability of each tile ********************** */
        for (int x = 0; x < xSize; x++) {
            for (int y = 0; y < ySize; y++) {
                Location here = new Location(x, y);

                if(_memory.getStatus(here) == Model.Status.CLICKED_MINE)
                {
                    return;
                }

                if (_memory.isFlipped(x, y)) {
                    /* Count number of surrounding flags */
                    LambdaObject<Integer> nearbyFlags = new LambdaObject<>(0);
                    doFunctionAroundLocation(here, Model.Status.FLAGGED, (location) ->
                            nearbyFlags.set(nearbyFlags.get() + 1)
                    );

                    /* If all flags are accounted for, it is safe to click */
                    if (nearbyFlags.get() == _memory.getStatus(here).value()) {
                        doFunctionAroundLocation(here, Model.Status.UNFLIPPED, location -> {
                            mines.putAt(location, new Probability(Integer.MIN_VALUE));
                        });
                    }
                    /* Else if only a few flags are accounted for */
                    else if (nearbyFlags.get() < _memory.getStatus(here).value()) {
                        Double difference = (double) (_memory.getStatus(here).value() - nearbyFlags.get());

                        /* Count the number of Unflipped Tiles around the location */
                        LambdaObject<Integer> numUnflippedTiles = new LambdaObject<>(0);
                        doFunctionAroundLocation(here, Model.Status.UNFLIPPED, location ->
                                numUnflippedTiles.set(numUnflippedTiles.get() + 1)
                        );

                    /* If the number of Unflipped tiles is equal to
                        the number at this tile, it is not mines
                     */
                        if (difference == (double) numUnflippedTiles.get()) {
                            doFunctionAroundLocation(here, Model.Status.UNFLIPPED, location -> {
                                mines.putAt(location, new Probability(1));
                            });
                        } else {
                            doFunctionAroundLocation(here, Model.Status.UNFLIPPED, location -> {
                                double p = difference / numUnflippedTiles.get();
                                Probability newProbability = new Probability(p).multiply(mines.at(location));
                                mines.putAt(location, newProbability);
                            });
                        }
                    } else {
                        System.err.println("Not a valid state");
                    }
                } else if (_memory.getStatus(x, y) == Model.Status.UNFLIPPED) {
                    mines.putAt(here, new Probability(0).multiply(mines.at(here)));
                }
            }
        }

         /*For loop printing out all probabilities */
        try {
            for(int i = 0; i < xSize; i++) {
                for (int j = 0; j < ySize; j++)
                {

                        out.write(String.format("%7s | ", mines.at(i, j)));
                }
                out.write("\n");
            }
            out.write("-----------------------------------------\n\n\n");
        }catch (IOException e) {
            e.printStackTrace();
        }
        /* ********************** Select move ********************** */

        /*Flag if mine is known 100%, otherwise click lowest probability */
        List<Location> safePlaces = new ArrayList<>();
        List<Location> flagPlaces = new ArrayList<>();
        double minProb = flag_confidence;
        double maxFlagProb = 0;

        for(int x=0; x < xSize; x++) {
            for(int y= 0; y < ySize; y++) {
                if(mines.at(x, y) == null) continue;
                if(mines.at(x, y).prob() < 0) {
                    _c.flip(new Location(x, y));
                    return;
                }

                /*if(mines.at(x, y).prob() >= 1 ){
                    _c.flag(new Location(x, y));
                    System.out.println("Flag" + new Location(x, y));
                    return;
                }*/

                if(mines.at(x, y).prob() > maxFlagProb)
                {
                    flagPlaces = new ArrayList<>();
                    flagPlaces.add(new Location(x, y));
                    maxFlagProb = mines.at(x, y).prob();
                } else if (mines.at(x, y).prob() == minProb) {
                    safePlaces.add(new Location(x, y));
                }

                if(mines.at(x, y).prob() < minProb)
                {
                    safePlaces = new ArrayList<>();
                    safePlaces.add(new Location(x, y));
                    minProb = mines.at(x, y).prob();
                } else if (mines.at(x, y).prob() == minProb) {
                    safePlaces.add(new Location(x, y));
                }
            }
        }

        /*if(minProb > 0 || maxFlagProb > flag_confidence) {

        } else {

        }*/

        if(minProb <= click_confidence && maxFlagProb <= flag_confidence) {

            //Pick the location to click randomly
            int index = (int) (Math.random() * safePlaces.size());

            _c.flip(safePlaces.get(index));
        } else {
            //Pick the location to flag randomly
            int index = (int) (Math.random() * flagPlaces.size());

            _c.flag(flagPlaces.get(index));
        }
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
