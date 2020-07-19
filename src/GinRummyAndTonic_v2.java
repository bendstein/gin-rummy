import java.util.*;
import java.util.stream.Collectors;

/**
 * Class containing the player
 */
public class GinRummyAndTonic_v2 implements GinRummyPlayer {

    // <editor-fold desc="Instance Variables">
    /**
     * The number assigned to our player
     */
    private int playerNum;

    /**
     * prng
     */
    private Random random = new Random();

    /**
     * Becomes true if opponent has knocked.
     */
    private boolean opponentKnocked = false;

    /**
     * The id of the card which was drawn
     */
    private int drawn;

    /**
     * The current state of the game
     */
    private State state;

    /**
     * Parameters used for different decision points
     */
    private GeneralStrategy generalStrategy;

    /**
     * If the opponent knocks, this is what their final meld set is
     */
    private ArrayList<ArrayList<Card>> oppMelds;
    // </editor-fold>

    public GinRummyAndTonic_v2() {

        // Information set recorded including index into deck, but only considering
        // deadwood here
        HashMap<String, Double> knockStrat = new HashMap<String, Double>() {
            {
                put("9_39", 0.500);
                put("9_38", 0.000);
                put("9_37", 0.000);
                put("9_36", 0.000);
                put("9_35", 0.000);
                put("9_34", 0.000);
                put("9_33", 0.000);
                put("9_32", 0.000);
                put("9_31", 0.000);
                put("9_30", 0.000);
                put("9_29", 0.000);
                put("9_28", 0.000);
                put("9_27", 0.000);
                put("9_26", 0.000);
                put("9_25", 0.021);
                put("9_24", 0.003);
                put("9_23", 0.500);
                put("9_22", 0.000);
                put("9_21", 0.583);
                put("8_39", 0.500);
                put("8_38", 1.000);
                put("8_37", 0.000);
                put("8_36", 0.000);
                put("8_35", 0.000);
                put("8_34", 0.000);
                put("8_33", 0.000);
                put("8_32", 0.000);
                put("8_31", 0.000);
                put("8_30", 0.000);
                put("8_29", 0.000);
                put("8_28", 0.000);
                put("8_27", 0.001);
                put("8_26", 0.035);
                put("8_25", 0.001);
                put("8_24", 0.049);
                put("8_23", 0.029);
                put("8_22", 0.000);
                put("8_21", 1.000);
                put("7_39", 0.500);
                put("7_38", 0.001);
                put("7_37", 0.001);
                put("7_36", 0.000);
                put("7_35", 0.006);
                put("7_34", 0.000);
                put("7_33", 0.000);
                put("7_32", 0.000);
                put("7_31", 0.000);
                put("7_30", 0.000);
                put("7_29", 0.066);
                put("7_28", 0.000);
                put("7_27", 0.001);
                put("7_26", 1.000);
                put("7_25", 0.008);
                put("7_24", 0.028);
                put("7_23", 0.112);
                put("7_22", 0.000);
                put("7_21", 0.000);
                put("6_39", 0.500);
                put("6_38", 0.000);
                put("6_37", 1.000);
                put("6_36", 0.003);
                put("6_35", 1.000);
                put("6_34", 0.003);
                put("6_33", 1.000);
                put("6_32", 1.000);
                put("6_31", 0.000);
                put("6_30", 0.000);
                put("6_29", 0.011);
                put("6_28", 0.000);
                put("6_27", 0.000);
                put("6_26", 0.000);
                put("6_25", 0.001);
                put("6_24", 0.000);
                put("6_23", 0.020);
                put("6_22", 0.002);
                put("6_21", 1.000);
                put("5_39", 0.500);
                put("5_38", 0.783);
                put("5_37", 1.000);
                put("5_36", 0.002);
                put("5_35", 0.000);
                put("5_34", 0.000);
                put("5_33", 0.000);
                put("5_32", 0.000);
                put("5_31", 0.000);
                put("5_30", 0.000);
                put("5_29", 0.000);
                put("5_28", 0.000);
                put("5_27", 0.000);
                put("5_26", 1.000);
                put("5_25", 0.001);
                put("5_24", 1.000);
                put("5_23", 0.015);
                put("5_22", 0.002);
                put("5_21", 0.000);
                put("4_39", 0.500);
                put("4_38", 1.000);
                put("4_37", 0.001);
                put("4_36", 0.000);
                put("4_35", 0.000);
                put("4_34", 0.000);
                put("4_33", 0.000);
                put("4_32", 0.000);
                put("4_31", 0.000);
                put("4_30", 0.000);
                put("4_29", 0.000);
                put("4_28", 0.000);
                put("4_27", 0.000);
                put("4_26", 0.000);
                put("4_25", 0.000);
                put("4_24", 0.076);
                put("4_23", 1.000);
                put("4_22", 0.000);
                put("4_21", 0.202);
                put("3_39", 0.500);
                put("3_38", 0.001);
                put("3_37", 0.000);
                put("3_36", 0.000);
                put("3_35", 0.000);
                put("3_34", 1.000);
                put("3_33", 0.000);
                put("3_32", 0.000);
                put("3_31", 0.000);
                put("3_30", 0.000);
                put("3_29", 0.000);
                put("3_28", 0.000);
                put("3_27", 0.000);
                put("3_26", 0.000);
                put("3_25", 0.000);
                put("3_24", 0.005);
                put("3_23", 1.000);
                put("3_22", 0.000);
                put("3_21", 0.000);
                put("2_39", 0.500);
                put("2_38", 1.000);
                put("2_37", 1.000);
                put("2_36", 0.007);
                put("2_35", 0.000);
                put("2_34", 0.000);
                put("2_33", 0.000);
                put("2_32", 0.000);
                put("2_31", 0.001);
                put("2_30", 0.000);
                put("2_29", 1.000);
                put("2_28", 0.000);
                put("2_27", 0.000);
                put("2_26", 0.000);
                put("2_25", 0.000);
                put("2_24", 0.001);
                put("2_23", 0.000);
                put("2_22", 0.009);
                put("2_21", 0.000);
                put("1_39", 0.500);
                put("1_38", 0.001);
                put("1_37", 0.000);
                put("1_36", 1.000);
                put("1_35", 0.000);
                put("1_34", 0.001);
                put("1_33", 0.000);
                put("1_32", 0.000);
                put("1_31", 0.000);
                put("1_30", 0.000);
                put("1_29", 0.000);
                put("1_28", 0.000);
                put("1_27", 0.000);
                put("1_26", 0.000);
                put("1_25", 0.000);
                put("1_24", 0.002);
                put("1_23", 0.000);
                put("1_22", 0.000);
                put("1_21", 0.000);
                put("10_39", 0.500);
                put("10_38", 0.001);
                put("10_37", 0.000);
                put("10_36", 0.000);
                put("10_35", 0.000);
                put("10_34", 0.000);
                put("10_33", 0.000);
                put("10_32", 0.000);
                put("10_31", 0.000);
                put("10_30", 0.000);
                put("10_29", 0.000);
                put("10_28", 0.000);
                put("10_27", 1.000);
                put("10_26", 0.000);
                put("10_25", 0.005);
                put("10_24", 0.000);
                put("10_23", 0.003);
                put("10_22", 0.044);
                put("10_21", 0.273);

            }
        };

        HashMap<String, Double> drawStrat = new HashMap<String, Double>() {
            {
                put("9_36_false", 0.500);
                put("9_35_false", 0.000);
                put("9_34_false", 1.000);
                put("9_33_false", 0.500);
                put("9_32_false", 0.500);
                put("9_31_false", 0.925);
                put("9_30_false", 0.593);
                put("9_29_false", 0.841);
                put("9_28_false", 0.236);
                put("9_27_false", 0.745);
                put("9_26_false", 0.014);
                put("9_25_false", 0.500);
                put("9_24_false", 0.998);
                put("9_23_false", 0.000);
                put("9_22_false", 0.406);
                put("9_21_false", 0.988);
                put("8_35_false", 0.000);
                put("8_34_false", 0.168);
                put("8_33_false", 0.699);
                put("8_32_false", 0.346);
                put("8_31_false", 0.462);
                put("8_30_false", 0.941);
                put("8_29_false", 0.842);
                put("8_28_false", 0.673);
                put("8_27_false", 0.506);
                put("8_26_false", 0.624);
                put("8_25_false", 0.009);
                put("8_24_false", 0.957);
                put("8_23_false", 0.103);
                put("8_22_false", 0.919);
                put("8_21_false", 0.000);
                put("7_36_false", 0.500);
                put("7_35_false", 0.315);
                put("7_34_false", 0.344);
                put("7_33_false", 0.526);
                put("7_32_false", 0.519);
                put("7_31_false", 0.988);
                put("7_30_false", 0.995);
                put("7_29_false", 0.195);
                put("7_28_false", 0.095);
                put("7_27_false", 0.029);
                put("7_26_false", 0.893);
                put("7_25_false", 0.004);
                put("7_24_false", 1.000);
                put("7_23_false", 0.075);
                put("7_22_false", 0.979);
                put("7_21_false", 0.002);
                put("6_37_false", 0.000);
                put("6_36_false", 0.013);
                put("6_35_false", 0.998);
                put("6_34_false", 0.372);
                put("6_33_false", 0.694);
                put("6_32_false", 0.340);
                put("6_31_false", 0.996);
                put("6_30_false", 0.807);
                put("6_29_false", 0.059);
                put("6_28_false", 0.055);
                put("6_27_false", 0.822);
                put("6_26_false", 0.256);
                put("6_25_false", 0.214);
                put("6_24_false", 1.000);
                put("6_23_false", 0.016);
                put("6_22_false", 0.996);
                put("6_21_false", 0.011);
                put("5_37_false", 0.567);
                put("5_36_false", 0.125);
                put("5_35_false", 0.554);
                put("5_34_false", 0.660);
                put("5_33_false", 0.915);
                put("5_32_false", 0.873);
                put("5_31_false", 0.961);
                put("5_30_false", 0.527);
                put("5_29_false", 0.985);
                put("5_28_false", 0.000);
                put("5_27_false", 0.270);
                put("5_26_false", 0.060);
                put("5_25_false", 0.999);
                put("5_24_false", 1.000);
                put("5_23_false", 0.090);
                put("5_22_false", 0.996);
                put("5_21_false", 0.001);
                put("4_38_false", 0.125);
                put("4_37_false", 0.000);
                put("4_36_false", 0.152);
                put("4_35_false", 0.873);
                put("4_34_false", 0.855);
                put("4_33_false", 0.451);
                put("4_32_false", 0.025);
                put("4_31_false", 0.966);
                put("4_30_false", 0.769);
                put("4_29_false", 0.197);
                put("4_28_false", 0.000);
                put("4_27_false", 0.780);
                put("4_26_false", 0.102);
                put("4_25_false", 0.366);
                put("4_24_false", 1.000);
                put("4_23_false", 0.016);
                put("4_22_false", 0.996);
                put("4_21_false", 0.000);
                put("3_38_false", 0.500);
                put("3_37_false", 0.042);
                put("3_36_false", 0.510);
                put("3_35_false", 0.635);
                put("3_34_false", 0.414);
                put("3_33_false", 0.348);
                put("3_32_false", 0.308);
                put("3_31_false", 0.922);
                put("3_30_false", 0.886);
                put("3_29_false", 0.041);
                put("3_28_false", 0.010);
                put("3_27_false", 0.012);
                put("3_26_false", 0.001);
                put("3_25_false", 0.818);
                put("3_24_false", 1.000);
                put("3_23_false", 0.002);
                put("3_22_false", 0.999);
                put("3_21_false", 0.003);
                put("2_38_false", 0.500);
                put("2_37_false", 0.628);
                put("2_36_false", 0.221);
                put("2_35_false", 0.668);
                put("2_34_false", 0.581);
                put("2_33_false", 0.299);
                put("2_32_false", 0.018);
                put("2_31_false", 0.221);
                put("2_30_false", 0.047);
                put("2_29_false", 0.000);
                put("2_28_false", 0.005);
                put("2_27_false", 0.003);
                put("2_26_false", 0.000);
                put("2_25_false", 0.049);
                put("2_24_false", 1.000);
                put("2_23_false", 0.002);
                put("2_22_false", 0.999);
                put("2_21_false", 0.001);
                put("1_38_false", 0.500);
                put("1_37_false", 0.017);
                put("1_36_false", 0.772);
                put("1_35_false", 0.413);
                put("1_34_false", 0.042);
                put("1_33_false", 0.053);
                put("1_32_false", 0.007);
                put("1_31_false", 0.002);
                put("1_30_false", 0.000);
                put("1_29_false", 0.008);
                put("1_28_false", 0.009);
                put("1_27_false", 0.002);
                put("1_26_false", 0.006);
                put("1_25_false", 0.001);
                put("1_24_false", 1.000);
                put("1_23_false", 0.000);
                put("1_22_false", 1.000);
                put("1_21_false", 0.002);
                put("0_38_true", 0.500);
                put("0_38_false", 0.000);
                put("0_37_true", 0.273);
                put("0_37_false", 0.001);
                put("0_36_true", 0.000);
                put("0_36_false", 0.001);
                put("0_35_true", 0.244);
                put("0_35_false", 0.000);
                put("0_34_true", 0.000);
                put("0_34_false", 0.001);
                put("0_33_true", 0.150);
                put("0_33_false", 0.000);
                put("0_32_true", 0.000);
                put("0_32_false", 0.000);
                put("0_31_true", 0.001);
                put("0_31_false", 0.000);
                put("0_30_true", 0.691);
                put("0_30_false", 0.000);
                put("0_29_true", 0.022);
                put("0_29_false", 0.000);
                put("0_28_true", 0.042);
                put("0_28_false", 0.000);
                put("0_27_true", 0.004);
                put("0_27_false", 0.000);
                put("0_26_true", 0.012);
                put("0_26_false", 0.000);
                put("0_25_true", 0.001);
                put("0_25_false", 0.000);
                put("0_24_true", 0.663);
                put("0_24_false", 1.000);
                put("0_23_true", 0.041);
                put("0_23_false", 0.000);
                put("0_22_true", 0.035);
                put("0_22_false", 1.000);
                put("0_21_true", 0.002);
                put("0_21_false", 0.000);

            }
        };

        /*
         * Order of strategy parameters:
         *
         * maxIsolatedSingleDeadwood minIsolatedSingleDiscardTurn maxSingleDeadwood
         * minSingleDiscardTurn minPickupDifference
         */
        generalStrategy = new GeneralStrategy(MyGinRummyUtil.decoded("34466"), knockStrat, drawStrat);

    }

    @Override
    public void startGame(int playerNum, int startingPlayerNum, Card[] cards) {
        this.playerNum = playerNum;

        state = new State(new ArrayList<>(Arrays.asList(cards)));

        oppMelds = null;
        opponentKnocked = false;

    }

    @Override
    public boolean willDrawFaceUpCard(Card card) {
        int card_id = card.getId();
        // If first turn, record the face-up card. All other unseen face-up cards should
        // be recorded in reportDiscard()
        if (state.getTurn() == 0) {
            state.addToSeen(card_id);
            state.increaseTopCard();
            state.decreaseNumRemaining();
        }

        // Card is our face-up
        state.setFaceUp(card_id);

        return willDrawFaceUpCard(state.getHand(), state.getFaceUp());

    }

    /**
     * @param hand    A hand of cards
     * @param card_id The id of the face-up card
     * @return true if we would pick up card_id with the given hand
     */
    public boolean willDrawFaceUpCard(long hand, int card_id) {
        int improvement = MyGinRummyUtil.getImprovement(hand, card_id);
        boolean makesNewMeld = MyGinRummyUtil.makesNewMeld(hand, card_id);

        if (improvement > 0 && makesNewMeld)
            return true;

        ArrayList<ArrayList<ArrayList<Card>>> bestMeldSets = GinRummyUtil
                .cardsToBestMeldSets(GinRummyUtil.bitstringToCards(hand));
        boolean inBestMeld = false;
        if (!bestMeldSets.isEmpty()) {
            ArrayList<ArrayList<Card>> set = bestMeldSets.get(0);
            for (ArrayList<Card> meld : set) {
                if (meld.contains(Card.getCard(card_id))) {
                    inBestMeld = true;
                    break;
                }
            }
        }

        String infoset = improvement + "_" + state.getTopCard() + "_" + inBestMeld;

        if (random.nextDouble() < generalStrategy.getDrawAt(infoset))
            return true;
        else
            return false;

    }

    @Override
    public void reportDraw(int playerNum, Card drawnCard) {

        // If drawn card is null or its id != the face up, player drew face-down.
        // Decrease numRemaining, and add to oppForwent
        if (drawnCard == null || drawnCard.getId() != state.getFaceUp()) {
            state.decreaseNumRemaining();
            state.increaseTopCard();
            if (playerNum != this.playerNum)
                state.addToOppForwent(state.getFaceUp());
        }

        // Ignore other player draws. Add to cards if playerNum is this player.
        if (playerNum == this.playerNum) {

            state.addToHand(drawnCard.getId());
            this.drawn = drawnCard.getId();

        }
        // If the other player drew, and drawnCard isn't null, other player drew
        // face-up.
        else {
            if (drawnCard != null)
                state.addToOppHand(drawnCard.getId());
        }
    }

    @Override
    public Card getDiscard() {
        long potentialDiscards = findDiscard(state.getHand(), state.getFaceUp());
        return MyGinRummyUtil.bitstringToCards(potentialDiscards)
                .get(random.nextInt(MyGinRummyUtil.size(potentialDiscards)));
    }

    /**
     * @param hand    A hand of cards
     * @param face_up The id of the face-up card
     * @return A group of all cards which we would most prefer to discard
     */
    public long findDiscard(long hand, int face_up) {

        /*
         * First, get all cards who's removal would lower our deadwood the most
         */
        long candidateCards = MyGinRummyUtil.findHighestDiscards(hand, drawn, face_up, 0);
        long toRemove = 0L;
        ArrayList<Integer> ids = MyGinRummyUtil.bitstringToIDs(candidateCards);
        ArrayList<Integer> temp = new ArrayList<>(ids);

        for (int i : ids) {
            temp.remove((Integer) i);
            if (!MyGinRummyUtil
                    .contains((MyGinRummyUtil.getIsolatedSingles(hand, MyGinRummyUtil.idsToBitstring(temp), state)), i))
                toRemove = MyGinRummyUtil.add(toRemove, i);
            temp.add((Integer) i);
        }

        if (toRemove != candidateCards)
            candidateCards = MyGinRummyUtil.removeAll(candidateCards, toRemove);

        else {
            toRemove = 0L;

            for (int i : ids) {
                temp.remove((Integer) i);
                if (!MyGinRummyUtil
                        .contains((MyGinRummyUtil.getSingles(hand, MyGinRummyUtil.idsToBitstring(temp), state)), i))
                    toRemove = MyGinRummyUtil.add(toRemove, i);
                temp.add((Integer) i);
            }

            if (toRemove != candidateCards)
                candidateCards = MyGinRummyUtil.removeAll(candidateCards, toRemove);
        }

        /*
         * Prefer cards who cannot be melded even after 2 draws. If there are none (or
         * no cards can), prefer those who can't be melded after 1 draw.
         */
        long not_singles = ~MyGinRummyUtil.getIsolatedSingles(hand, 0L, state);
        candidateCards = MyGinRummyUtil.removeAll(candidateCards, not_singles) == 0L ? candidateCards
                : MyGinRummyUtil.removeAll(candidateCards, not_singles);

        if (MyGinRummyUtil.removeAll(candidateCards, not_singles) == 0L) {
            not_singles = ~MyGinRummyUtil.getSingles(hand, 0L, state);
            candidateCards = MyGinRummyUtil.removeAll(candidateCards, not_singles) == 0L ? candidateCards
                    : MyGinRummyUtil.removeAll(candidateCards, not_singles);
        }

        /*
         * Then, filter out cards which would be helpful to the opponent
         */
        if (MyGinRummyUtil.size(candidateCards) > 1) {
            toRemove = 0L; // Don't remove until after loop
            long preferred = 0L; // Cards we would prefer to remove

            for (Card c : MyGinRummyUtil.bitstringToCards(candidateCards)) {

                /*
                 * If a card could be used in an opp meld, or at least bring them closer, avoid
                 * discarding it
                 */
                if (MyGinRummyUtil.canOpponentMeld(c, state))
                    toRemove = MyGinRummyUtil.add(toRemove, c.getId()); // If card could help opp meld, avoid tossing
                else if (MyGinRummyUtil.containsRank(state.getOppHand(), c.getId())
                        || MyGinRummyUtil.containsSuit(state.getOppHand(), c.getId(), 2))
                    toRemove = MyGinRummyUtil.add(toRemove, c.getId()); // If card brings opp closer to a meld, avoid
                                                                        // tossing

                /*
                 * If the opp has discarded cards that could be melded with this card, it is
                 * less likely they would find it useful. Prefer to discard any of these cards.
                 */
                else if (MyGinRummyUtil.containsRank(state.getOppDiscard(), c.getId())
                        || MyGinRummyUtil.containsSuit(state.getOppDiscard(), c.getId(), 2))
                    preferred = MyGinRummyUtil.add(preferred, c.getId()); // If similar cards have been tossed, prefer
                else if (MyGinRummyUtil.containsRank(state.getOppForwent(), c.getId())
                        || MyGinRummyUtil.containsSuit(state.getOppForwent(), c.getId(), 1))
                    preferred = MyGinRummyUtil.add(preferred, c.getId());
                ;
            }

            if (toRemove != candidateCards)
                candidateCards = MyGinRummyUtil.removeAll(candidateCards, toRemove); // Remove useful cards to the
                                                                                     // opponent, unless all cards would
                                                                                     // be useful
            if (preferred != 0L && preferred != candidateCards)
                candidateCards = MyGinRummyUtil.removeAll(candidateCards, ~preferred); // Only consider cards which we
                                                                                       // would prefer to discard

        }

        /*
         * If there are more than 2 cards left, if any are dupled, avoid throwing them
         * away
         */
        if (MyGinRummyUtil.size(candidateCards) > 2) {
            ArrayList<Integer> cards = MyGinRummyUtil.bitstringToIDs(candidateCards);
            long duples = 0L;

            for (int i : cards)
                duples += MyGinRummyUtil.getDuples(candidateCards, i);

            if (MyGinRummyUtil.removeAll(candidateCards, duples) != 0L)
                candidateCards = MyGinRummyUtil.removeAll(candidateCards, duples);

        }

        return candidateCards;

    }

    @Override
    public void reportDiscard(int playerNum, Card discardedCard) {
        // Ignore other player discards. Remove from cards if playerNum is this player.
        if (playerNum == this.playerNum) {
            state.removeFromHand(discardedCard.getId());
            state.nextTurn();
        }

        // If we knew the discarded card was in the opponent's hand, remove. If we
        // didn't, add it to seen.
        else {
            state.addToSeen(discardedCard.getId());
            state.removeFromOppHand(discardedCard.getId());
        }
    }

    @Override
    public ArrayList<ArrayList<Card>> getFinalMelds() {

        /*
         * TODO: Consider the deadwood of opponent discards in figuring out whether they're preparing to knock or not.
         *  If they are consistently tossing low deadwood cards, they could be preparing to knock. Maybe.
         */

        ArrayList<ArrayList<ArrayList<Card>>> bestMeldSets;
        int deadwood;

        bestMeldSets = MyGinRummyUtil.cardsToBestMeldSets(MyGinRummyUtil.bitstringToCards(state.getHand()));
        deadwood = bestMeldSets.isEmpty()
                ? MyGinRummyUtil.getDeadwoodPoints(MyGinRummyUtil.bitstringToCards(state.getHand()))
                : MyGinRummyUtil.getDeadwoodPoints(bestMeldSets.get(0),
                        MyGinRummyUtil.bitstringToCards(state.getHand()));

        // Check if deadwood of maximal meld is low enough to go out.

        if (!opponentKnocked && (bestMeldSets.isEmpty() || deadwood > MyGinRummyUtil.MAX_DEADWOOD))
            return null;
        else if (!opponentKnocked) {

            String k = deadwood + "_" + state.getTopCard();

            //If we have gin, or if we get a random value less than our probability to knock at infoset k, knock.
            if (deadwood == 0 || random.nextDouble() < generalStrategy.getKnockAt(k)) {

                //Select the meld configuration to submit.
                ArrayList<ArrayList<Card>> bestMeldSet = null;
                 double minExpectedLayoff = Double.MAX_VALUE;
                 for(ArrayList<ArrayList<Card>> meldSet : bestMeldSets) {
                     ArrayList<Card> layoff = new ArrayList<>();

                     // Add all cards to layoff who could be inserted into our hand
                     for (ArrayList<Card> meld : meldSet) {
                         // Meld of cards of same rank
                         if (meld.get(0).getRank() == meld.get(1).getRank()) {
                             layoff.addAll(
                                     MyGinRummyUtil.getSameRank(MyGinRummyUtil.bitstringToCards(
                                             MyGinRummyUtil.addAll(state.getOppHand(), state.getUnaccounted())), meld.get(0)));
                         }

                         // Cards of same suit
                         else {
                             layoff.addAll(MyGinRummyUtil.getSameSuit(MyGinRummyUtil.bitstringToCards(
                                     MyGinRummyUtil.addAll(state.getOppHand(), state.getUnaccounted())),
                                     meld.get(0), 1));
                             layoff.addAll(MyGinRummyUtil.getSameSuit(MyGinRummyUtil.bitstringToCards(
                                     MyGinRummyUtil.addAll(state.getOppHand(), state.getUnaccounted())),
                                     meld.get(meld.size() - 1), 1));
                         }
                     }

                     /*
                      * The sum of the deadwood of each layoff card * the probability that the opponent has said card
                      * is expectedLayoff. If expectedLayoff < minExpectedLayoff, it is the new minimum, so assign
                      * bestMeldSet to the current meld set. In the end, return the meld set with the lowest expectedLayoff.
                      */
                     double expectedLayoff = 0d;
                     for(Card card : layoff) {
                         //If the card is in an opponent meld, we don't expect them to try to lay it off.
                         if(MyGinRummyUtil.canOpponentMeld(card, state)) continue;
                         expectedLayoff += GinRummyUtil.getDeadwoodPoints(card) *
                                 MyGinRummyUtil.getProbabilityThatOpponentHasUnseenCard(card, state);
                     }

                     if(expectedLayoff < minExpectedLayoff) {
                         minExpectedLayoff = expectedLayoff;
                         bestMeldSet = meldSet;
                     }

                 }

                 return bestMeldSet;

            }
            else
                return null;
        }

        else {
            ArrayList<Card> layoff = new ArrayList<>();

            if (bestMeldSets.isEmpty())
                return new ArrayList<>();

            // Add all cards to layoff who could be inserted into opponent hand
            for (ArrayList<Card> meld : oppMelds) {
                // Meld of cards of same rank
                if (meld.get(0).getRank() == meld.get(1).getRank()) {
                    layoff.addAll(
                            MyGinRummyUtil.getSameRank(MyGinRummyUtil.bitstringToCards(state.getHand()), meld.get(0)));
                }

                // Cards of same suit
                else {
                    layoff.addAll(MyGinRummyUtil.getSameSuit(MyGinRummyUtil.bitstringToCards(state.getHand()),
                            meld.get(0), 1));
                    layoff.addAll(MyGinRummyUtil.getSameSuit(MyGinRummyUtil.bitstringToCards(state.getHand()),
                            meld.get(meld.size() - 1), 1));
                }
            }

            /*
             * Deadwood cards will be laid off no matter what, so check potential layoffs in
             * melds to see if a better config is available.
             */

            ArrayList<Card> temp;
            ArrayList<ArrayList<Card>> bestMeldSet = bestMeldSets.get(0);
            int minDeadwood = deadwood;

            if (layoff.isEmpty())
                return bestMeldSet;

            // Go through EVERY permutation of potential layoffs to find the one that leaves
            // the best deadwood
            for (int i = 0; i < Math.pow(2, layoff.size()); i++) {
                String bString = Integer.toBinaryString(i);
                temp = MyGinRummyUtil.bitstringToCards(state.getHand());

                for (int j = 0; j < bString.length(); j++) {
                    if (bString.charAt(bString.length() - 1 - j) == '1') {
                        temp.remove(layoff.get(j));
                    }
                }

                ArrayList<ArrayList<ArrayList<Card>>> meldSets = MyGinRummyUtil.cardsToBestMeldSets(temp);

                if (meldSets.isEmpty()) {
                    if (MyGinRummyUtil.getDeadwoodPoints(temp) < minDeadwood) {
                        minDeadwood = MyGinRummyUtil.getDeadwoodPoints(temp);
                        bestMeldSet = new ArrayList<>();
                    }
                }

                else {
                    if (MyGinRummyUtil.getDeadwoodPoints(meldSets.get(0), temp) < minDeadwood) {
                        minDeadwood = MyGinRummyUtil.getDeadwoodPoints(meldSets.get(0), temp);
                        bestMeldSet = meldSets.get(0);
                    }
                }
            }

            return bestMeldSet;

        }

    }

    @Override
    public void reportFinalMelds(int playerNum, ArrayList<ArrayList<Card>> melds) {
        // Melds ignored by simple player, but could affect which melds to make for
        // complex player.
        if (playerNum != this.playerNum) {
            opponentKnocked = true;
            oppMelds = melds;
        }
    }

    @Override
    public void reportScores(int[] scores) {
        // Ignored by simple player, but could affect strategy of more complex player.
    }

    @Override
    public void reportLayoff(int playerNum, Card layoffCard, ArrayList<Card> opponentMeld) {
        // Ignored by simple player, but could affect strategy of more complex player.

    }

    @Override
    public void reportFinalHand(int playerNum, ArrayList<Card> hand) {
        // Ignored by simple player, but could affect strategy of more complex player.
    }
}

/**
 * Class to record details about the current state of the game
 */
class State {

    // <editor-fold desc="Instance Variables">
    /**
     * Our hand
     */
    private long hand;

    /**
     * Cards which have been seen by the player
     */
    private long seen;

    /**
     * Cards which we know are currently in the opponent hand
     */
    private long oppHand;

    /**
     * Cards which we have seen the opponent discard
     */
    private long oppDiscard;

    /**
     * Cards which the opponent has ignored when face-up
     */
    private long oppForwent;

    /**
     * The id of the current face-up card
     */
    private int faceUp;

    /**
     * Index into deck for top card
     */
    private int topCard;

    /**
     * Current turn of the game
     */
    private int turn;

    /**
     * Number of remaining cards in the deck
     */
    private int num_remaining;
    // </editor-fold>

    State(ArrayList<Card> hand) {

        this.hand = MyGinRummyUtil.cardsToBitstring(hand);
        seen = this.hand;
        oppHand = 0L;
        oppDiscard = 0L;
        oppForwent = 0L;

        turn = 0;
        topCard = 20;
        faceUp = -1;
        num_remaining = 32;
    }

    /**
     * Clear the game state to be used again
     */
    void clear() {
        hand = seen = oppHand = oppDiscard = 0L;
        faceUp = turn = 0;
        num_remaining = 32;
        topCard = 20;
    }

    // <editor-fold desc="Methods to add and remove cards from the lists recorded in
    // this class">
    /**
     * The following methods are for adding and removing cards/sets of cards from a
     * hand. I don't think they need to documented.
     */
    public void addToHand(int card_id) {
        this.hand = MyGinRummyUtil.add(this.hand, card_id);
    }

    public void addAllToHand(long cards) {
        this.hand = MyGinRummyUtil.addAll(this.hand, cards);
    }

    public void removeFromHand(int card_id) {
        this.hand = MyGinRummyUtil.remove(this.hand, card_id);
    }

    public void removeAllFromHand(long cards) {
        this.hand = MyGinRummyUtil.removeAll(this.hand, cards);
    }

    public void addToSeen(int card_id) {
        this.seen = MyGinRummyUtil.add(this.seen, card_id);
    }

    public void addAllToSeen(long cards) {
        this.seen = MyGinRummyUtil.addAll(this.seen, cards);
    }

    public void removeFromSeen(int card_id) {
        this.seen = MyGinRummyUtil.remove(this.seen, card_id);
    }

    public void removeAllFromSeen(long cards) {
        this.seen = MyGinRummyUtil.removeAll(this.seen, cards);
    }

    public void addToOppHand(int card_id) {
        this.oppHand = MyGinRummyUtil.add(this.oppHand, card_id);
    }

    public void addAllToOppHand(long cards) {
        this.oppHand = MyGinRummyUtil.addAll(this.oppHand, cards);
    }

    public void removeFromOppHand(int card_id) {
        this.oppHand = MyGinRummyUtil.remove(this.oppHand, card_id);
    }

    public void removeAllFromOppHand(long cards) {
        this.oppHand = MyGinRummyUtil.removeAll(this.oppHand, cards);
    }

    public void addToOppDiscard(int card_id) {
        this.oppDiscard = MyGinRummyUtil.add(this.oppDiscard, card_id);
    }

    public void addAllToOppDiscard(long cards) {
        this.oppDiscard = MyGinRummyUtil.addAll(this.oppDiscard, cards);
    }

    public void removeFromOppDiscard(int card_id) {
        this.oppDiscard = MyGinRummyUtil.remove(this.oppDiscard, card_id);
    }

    public void removeAllFromOppDiscard(long cards) {
        this.oppDiscard = MyGinRummyUtil.removeAll(this.oppDiscard, cards);
    }

    public void addToOppForwent(int card_id) {
        this.oppForwent = MyGinRummyUtil.add(this.oppForwent, card_id);
    }

    public void addAllToOppForwent(long cards) {
        this.oppForwent = MyGinRummyUtil.addAll(this.oppForwent, cards);
    }

    public void removeFromOppForwent(int card_id) {
        this.oppForwent = MyGinRummyUtil.remove(this.oppForwent, card_id);
    }

    public void removeAllFromOppForwent(long cards) {
        this.oppForwent = MyGinRummyUtil.removeAll(this.oppForwent, cards);
    }
    // </editor-fold>

    /**
     * Increment the turn
     */
    public void nextTurn() {
        turn++;
    }

    /**
     * Decrement the number of cards remaining
     */
    public void decreaseNumRemaining() {
        num_remaining--;
    }

    /**
     * @param id The id of the card
     * @return All melds which the opponent could make with id
     */
    public ArrayList<Long> getPotentialOpponentMelds(int id) {

        // All cards which are/could be available to the opponent
        long available = getUnaccounted() + getOppHand();
        ArrayList<Long> melds = new ArrayList<>();

        // All available cards of the same rank as id
        long sameRank = MyGinRummyUtil.add(MyGinRummyUtil.getSameRank(available, id), id);
        int[] sameRankIds = MyGinRummyUtil.bitstringToIDArray(sameRank);

        if(MyGinRummyUtil.size(sameRank) >= 3) melds.add(sameRank);

        // Add all potential same-rank melds to the list
        for (int i : sameRankIds) {
            for (int j : sameRankIds) {
                if (i != j) {
                    long meld = MyGinRummyUtil.idsToBitstring(new int[] { i, j, id });
                    ArrayList<Card> md = GinRummyUtil.bitstringToCards(meld);
                    if (!melds.contains(meld))
                        melds.add(meld);
                }
            }
        }


        ArrayDeque<Integer> toCheck = new ArrayDeque<>();
        HashSet<Integer> checked = new HashSet<>();
        toCheck.add(id);
        ArrayList<Integer> run = new ArrayList<>();

        while(!toCheck.isEmpty()) {
            //Dequeue a card, and add it to the run
            int cardId = toCheck.removeFirst();
            checked.add(cardId);
            run.add(cardId);

            //Get all available adjacent cards to cardId of the same suit
            long sameSuit = MyGinRummyUtil.getSameSuit(available, cardId, 1);
            ArrayList<Card> ss = MyGinRummyUtil.bitstringToCards(sameSuit);
            int[] sameSuitIds = MyGinRummyUtil.bitstringToIDArray(sameSuit);

            //Enqueue them
            for(int i : sameSuitIds) {
                if(!toCheck.contains(i) && !checked.contains(i)) toCheck.add(i);
            }

            /*
            // Add all potential same-suit melds to the list
            if (sameSuitIds.length == 2)
                melds.add(MyGinRummyUtil.add(MyGinRummyUtil.idsToBitstring(sameSuitIds), id));

            for (int i : sameSuitIds) {
                long adj = MyGinRummyUtil.getSameSuit(available, i, 1);
                int[] adjIds = MyGinRummyUtil.bitstringToIDArray(adj);
                if (adjIds.length == 2)
                    melds.add(MyGinRummyUtil.add(MyGinRummyUtil.idsToBitstring(sameSuitIds), i));
            }

             */
        }

        if(run.size() >= 3) melds.add(MyGinRummyUtil.idsToBitstring(run));

        ArrayList<ArrayList<Card>> mel = new ArrayList<>();
        for(long ec : melds)
            mel.add(MyGinRummyUtil.bitstringToCards(ec));

        return melds;
    }

    /**
     * @param id The id of the card
     * @return How many ways the opponent could use a card, ignoring melds of 4+
     */
    public int getUsefulnessToOpponent(int id) {
        return getPotentialOpponentMelds(id).size();
    }

    /**
     * @param id Card to check
     * @return The number of melds we could make with this card
     */
    public int getNumberOfPossibleMelds(int id) {

        // All cards which are/could be available to the opponent
        long available = getUnaccounted() + getHand();
        ArrayList<Long> melds = new ArrayList<>();

        // All available cards of the same rank as id
        long sameRank = MyGinRummyUtil.getSameRank(available, id);
        int[] sameRankIds = MyGinRummyUtil.bitstringToIDArray(sameRank);

        // Add all potential same-rank melds to the list
        for (int i : sameRankIds) {
            for (int j : sameRankIds) {
                if (i != j) {
                    long meld = MyGinRummyUtil.idsToBitstring(new int[] { i, j, id });
                    if (!melds.contains(meld))
                        melds.add(meld);
                }
            }
        }

        // All available adjacent cards to id of the same suit
        long sameSuit = MyGinRummyUtil.getSameSuit(available, id, 1);
        int[] sameSuitIds = MyGinRummyUtil.bitstringToIDArray(sameSuit);

        // Add all potential same-suit melds to the list
        if (sameSuitIds.length == 2)
            melds.add(MyGinRummyUtil.add(MyGinRummyUtil.idsToBitstring(sameSuitIds), id));

        for (int i : sameSuitIds) {
            long adj = MyGinRummyUtil.getSameSuit(available, i, 1);
            int[] adjIds = MyGinRummyUtil.bitstringToIDArray(adj);
            if (adjIds.length == 2)
                melds.add(MyGinRummyUtil.add(MyGinRummyUtil.idsToBitstring(sameSuitIds), i));
        }

        return melds.size();
    }

    /**
     * @return A bitstring of all cards which are under the face-up card. We know
     *         for a fact that neither us nor the opponent can use any of these
     *         cards
     */
    public long getBuried() {
        long seen = this.seen;
        seen = MyGinRummyUtil.removeAll(seen, hand);
        seen = MyGinRummyUtil.removeAll(seen, oppHand);
        seen = MyGinRummyUtil.removeAll(seen, faceUp);

        return seen;
    }

    /**
     * @return A list of all cards which have not yet been seen. They are either
     *         still in the deck, or in the opponent's hand.
     */
    public long getUnaccounted() {
        long unaccounted = MyGinRummyUtil.cardsToBitstring(new ArrayList<>(Arrays.asList(Card.allCards)));
        unaccounted = MyGinRummyUtil.removeAll(unaccounted, seen);
        return unaccounted;
    }

    // <editor-fold desc="Getters and Setters">
    public int getTurn() {
        return turn;
    }

    public long getHand() {
        return hand;
    }

    public void setHand(long hand) {
        this.hand = hand;
    }

    public void setHand(ArrayList<Card> hand) {
        this.hand = MyGinRummyUtil.cardsToBitstring(hand);
    }

    public long getSeen() {
        return seen;
    }

    public long getUnseen() {
        return ~seen;
    }

    public long getOppHand() {
        return oppHand;
    }

    public void setOppHand(long oppHand) {
        this.oppHand = oppHand;
    }

    public void setOppHand(ArrayList<Card> oppHand) {
        this.oppHand = MyGinRummyUtil.cardsToBitstring(oppHand);
    }

    public long getOppDiscard() {
        return oppDiscard;
    }

    public void setOppDiscard(long oppDiscard) {
        this.oppDiscard = oppDiscard;
    }

    public void setOppDiscard(ArrayList<Card> oppDiscard) {
        this.oppDiscard = MyGinRummyUtil.cardsToBitstring(oppDiscard);
    }

    public long getOppForwent() {
        return oppForwent;
    }

    public void setOppForwent(long oppForwent) {
        this.oppForwent = oppForwent;
    }

    public void setOppForwent(ArrayList<Card> oppForwent) {
        this.oppForwent = MyGinRummyUtil.cardsToBitstring(oppForwent);
    }

    public int getFaceUp() {
        return faceUp;
    }

    public void setFaceUp(int faceUp) {
        this.faceUp = faceUp;
    }

    public void setFaceUp(Card c) {
        this.faceUp = c.getId();
    }

    public int getNum_remaining() {
        return num_remaining;
    }

    public int getTopCard() {
        return topCard;
    }

    public void setTopCard(int topCard) {
        this.topCard = topCard;
    }

    public void increaseTopCard() {
        topCard++;
    }
    // </editor-fold>

}

/*
 * Class containing helper methods
 */
class MyGinRummyUtil extends GinRummyUtil {

    /**
     * @param cards   a hand of cards
     * @param exclude cards to exclude from the check
     * @return all cards not in melds
     */
    public static ArrayList<Card> getUnmelded(ArrayList<Card> cards, ArrayList<Card> exclude) {

        ArrayList<Card> temp = new ArrayList<>(cards);
        if (exclude != null)
            temp.removeAll(exclude);

        ArrayList<ArrayList<ArrayList<Card>>> bestMeldSets = cardsToBestMeldSets(temp);
        if (bestMeldSets.size() == 0)
            return temp;

        ArrayList<ArrayList<Card>> bestMeldSet = bestMeldSets.get(0);

        ArrayList<Card> unmelded = new ArrayList<>(temp);

        unmelded.removeIf(card -> {
            for (ArrayList<Card> meld : bestMeldSet)
                if (meld.contains(card))
                    return true;
            return false;
        });

        return unmelded;

    }

    /**
     * @param cards   a hand of cards
     * @param exclude cards to exclude from the check
     * @return all cards not in melds
     */
    public static long getUnmelded(long cards, long exclude) {
        return cardsToBitstring(getUnmelded(bitstringToCards(cards), bitstringToCards(exclude)));
    }

    /**
     * @param hand    A hand of cards
     * @param card_id the id of a card
     * @return The hand with the card added
     */
    public static long add(long hand, int card_id) {
        return hand | 1L << card_id;
    }

    /**
     * @param hand  A hand of cards
     * @param toAdd The cards to add
     * @return The hand with the cards added
     */
    public static long addAll(long hand, long toAdd) {
        return hand | toAdd;
    }

    /**
     * @param hand    A hand of cards
     * @param card_id the id of a card
     * @return The hand with the card removed. Nothing is changed if hand doesn't
     *         contain card.
     */
    public static long remove(long hand, int card_id) {
        return hand & (hand ^ 1L << card_id);
    }

    /**
     * @param hand     A hand of cards
     * @param toRemove The set of cards to remove from hand
     * @return The hand with the cards removed. Nothing is changed if hand doesn't
     *         contain card.
     */
    public static long removeAll(long hand, long toRemove) {
        return hand & (hand ^ toRemove);
    }

    /**
     * @param hand    a hand of cards
     * @param card_id the reference card
     * @return true if hand contains card_id
     */
    public static boolean contains(long hand, int card_id) {
        return (hand & 1L << card_id) != 0;
    }

    /**
     *
     * @param hand
     * @param cardBitString
     * @return true if hand contains card
     */
    public static boolean contains(long hand, long cardBitString) {
        return (hand & cardBitString) != 0;
    }

    /**
     * @param cards   a hand of cards
     * @param exclude cards to exclude from the check
     * @return all cards who are in one of the melds in the best meld set for cards
     */
    public static ArrayList<Card> getMelded(ArrayList<Card> cards, ArrayList<Card> exclude) {
        ArrayList<Card> melded = new ArrayList<>(cards);
        melded.removeAll(getUnmelded(melded, exclude));
        return melded;
    }

    /**
     * @param cards   a hand of cards
     * @param exclude cards to exclude from the check
     * @return all cards who are in one of the melds in the best meld set for cards
     */
    public static long getMelded(long cards, long exclude) {
        cards = removeAll(cards, exclude);
        return cards & (cards ^ getUnmelded(cards, exclude));
    }

    /**
     * @param cards   a hand of cards
     * @param exclude cards to exclude from the check
     * @param state   the current state of the game
     * @return all cards in hand that cannot be made into any melds even after
     *         drawing one card.
     */
    public static ArrayList<Card> getSingles(ArrayList<Card> cards, ArrayList<Card> exclude, State state) {

        ArrayList<Card> temp = new ArrayList<>(cards);
        if (exclude != null)
            temp.removeAll(exclude);
        if (temp.isEmpty())
            return temp;

        ArrayList<Card> singles = getUnmelded(temp, exclude);
        ArrayList<Card> toRemove = new ArrayList<>();

        for (Card card : singles) {

            // If there exists another card of the same rank in the hand and unseen, it can
            // be melded within 1 draw
            if (containsRank(temp, card) && containsRank(state.getUnaccounted(), card.getId())) {
                toRemove.add(card);
                continue;
            }

            /*
             * For each adjacent card of the same suit to c, if there is an adjacent card of
             * the same suit to that card which is unseen, it can be melded within 1 draw
             */

            for (Card c : getSameSuit(temp, card, 1)) {
                if (containsSuit(state.getUnaccounted(), card.getId(), 1)) {
                    toRemove.add(card);
                    break;
                }
            }
        }

        singles.removeAll(toRemove);
        return singles;

    }

    public static ArrayList<Card> getSingles(ArrayList<Card> cards, ArrayList<Card> exclude, ArrayList<Card> unaccounted) {

        ArrayList<Card> temp = new ArrayList<>(cards);
        if (exclude != null)
            temp.removeAll(exclude);
        if (temp.isEmpty())
            return temp;

        ArrayList<Card> singles = getUnmelded(temp, exclude);
        ArrayList<Card> toRemove = new ArrayList<>();

        for (Card card : singles) {

            // If there exists another card of the same rank in the hand and unseen, it can
            // be melded within 1 draw
            if (containsRank(temp, card) && containsRank(cardsToBitstring(unaccounted), card.getId())) {
                toRemove.add(card);
                continue;
            }

            /*
             * For each adjacent card of the same suit to c, if there is an adjacent card of
             * the same suit to that card which is unseen, it can be melded within 1 draw
             */

            for (Card c : getSameSuit(temp, card, 1)) {
                if (containsSuit(cardsToBitstring(unaccounted), card.getId(), 1)) {
                    toRemove.add(card);
                    break;
                }
            }
        }

        singles.removeAll(toRemove);
        return singles;

    }

    /**
     * @param cards   a hand of cards
     * @param state   the current state of the game
     * @param exclude cards to exclude from the check
     * @return all cards in hand that cannot be made into any melds even after
     *         drawing one card.
     */
    public static long getSingles(long cards, long exclude, State state) {
        return cardsToBitstring(getSingles(bitstringToCards(cards), bitstringToCards(exclude), state));
    }

    public static long getSingles(long cards, long exclude, long unaccounted) {
        return cardsToBitstring(getSingles(bitstringToCards(cards), bitstringToCards(exclude), bitstringToCards(unaccounted)));
    }

    /**
     * @param cards   a hand of cards
     * @param exclude cards to exclude from the check
     * @param state   the current state of the game
     * @return all cards in hand that cannot be made into any melds even after
     *         drawing two cards.
     */
    public static ArrayList<Card> getIsolatedSingles(ArrayList<Card> cards, ArrayList<Card> exclude, State state) {
        ArrayList<Card> singles = getSingles(cards, exclude, state); // All cards which cannot be made into a meld after
                                                                     // drawing one card
        ArrayList<Card> unaccounted = bitstringToCards(state.getUnaccounted()); // All cards which have not yet been
                                                                                // seen
        //unaccounted.removeAll(bitstringToCards(state.getSeen()));

        ArrayList<Card> toRemove = new ArrayList<>();

        for (Card card : singles) {

            // Get all cards of the same rank as the card
            ArrayList<Card> adjacent = new ArrayList<>(getSameRank(unaccounted, card));

            // If at least 2 unseen cards of the same rank as card, it can be melded after
            // drawing 2 cards
            if (adjacent.size() > 1) {
                toRemove.add(card);
                break;
            }

            // All cards of the same suit as the card whose rank is within 1 (should only be
            // 2 cards max)
            adjacent = new ArrayList<>(getSameSuit(unaccounted, card, 1));

            // If no adjacent cards, then it cannot be melded even after drawing 2 cards.
            // Check next card.
            if (adjacent.isEmpty())
                continue;

            // For each adjacent card, see if the next card also exists. If any do, then the
            // card can be melded after drawing 2 cards.
            for (Card c : adjacent) {
                ArrayList<Card> c_adjacent = new ArrayList<>(getSameSuit(unaccounted, c, 1)); // Unaccounted cards
                                                                                              // adjacent to c. Should
                                                                                              // never contain card, so
                                                                                              // should contain 1 card
                                                                                              // max.
                if (!c_adjacent.isEmpty()) {
                    toRemove.add(card);
                    break;
                }

            }

        }

        singles.removeAll(toRemove);

        return singles;

    }

    public static ArrayList<Card> getIsolatedSingles(ArrayList<Card> cards, ArrayList<Card> exclude, ArrayList<Card> unaccounted) {
        ArrayList<Card> singles = getSingles(cards, exclude, unaccounted); // All cards which cannot be made into a meld after
        // drawing one card
        // seen
        //unaccounted.removeAll(bitstringToCards(state.getSeen()));

        ArrayList<Card> toRemove = new ArrayList<>();

        for (Card card : singles) {

            // Get all cards of the same rank as the card
            ArrayList<Card> adjacent = new ArrayList<>(getSameRank(unaccounted, card));

            // If at least 2 unseen cards of the same rank as card, it can be melded after
            // drawing 2 cards
            if (adjacent.size() > 1) {
                toRemove.add(card);
                break;
            }

            // All cards of the same suit as the card whose rank is within 1 (should only be
            // 2 cards max)
            adjacent = new ArrayList<>(getSameSuit(unaccounted, card, 1));

            // If no adjacent cards, then it cannot be melded even after drawing 2 cards.
            // Check next card.
            if (adjacent.isEmpty())
                continue;

            // For each adjacent card, see if the next card also exists. If any do, then the
            // card can be melded after drawing 2 cards.
            for (Card c : adjacent) {
                ArrayList<Card> c_adjacent = new ArrayList<>(getSameSuit(unaccounted, c, 1)); // Unaccounted cards
                // adjacent to c. Should
                // never contain card, so
                // should contain 1 card
                // max.
                if (!c_adjacent.isEmpty()) {
                    toRemove.add(card);
                    break;
                }

            }

        }

        singles.removeAll(toRemove);

        return singles;

    }

    /**
     * @param cards   a hand of cards
     * @param exclude cards to exclude from the check
     * @param state   the current state of the game
     * @return all cards in hand that cannot be made into any melds even after
     *         drawing two cards.
     */
    public static long getIsolatedSingles(long cards, long exclude, State state) {
        return cardsToBitstring(getIsolatedSingles(bitstringToCards(cards), bitstringToCards(exclude), state));
    }

    public static long getIsolatedSingles(long cards, long exclude, long unaccounted) {
        return cardsToBitstring(getIsolatedSingles(bitstringToCards(cards), bitstringToCards(exclude), bitstringToCards(unaccounted)));
    }

    /**
     * @param cards a hand of cards
     * @param card  the reference card
     * @return A list of all duples containing card
     */
    public static ArrayList<Set<Card>> getDuples(ArrayList<Card> cards, Card card) {

        ArrayList<Set<Card>> duples = new ArrayList<>();
        ArrayList<Card> sameRank = getSameRank(cards, card);
        ArrayList<Card> sameSuit = getSameSuit(cards, card, 2);

        for (Card c : sameRank)
            duples.add(new HashSet<>(Arrays.asList(card, c)));
        for (Card c : sameSuit)
            duples.add(new HashSet<>(Arrays.asList(card, c)));

        return duples;
    }

    /**
     * @param cards a hand of cards
     * @param card  the reference card
     * @return A list of all cards in duples with the reference card
     */
    public static long getDuples(long cards, int card) {

        long duples = cards;
        long sameRank = getSameRank(cards, card);
        long sameSuit = getSameSuit(cards, card, 2);

        duples = addAll(duples, addAll(sameRank, sameSuit));
        return duples;
    }

    /**
     * @param hand A hand of cards
     * @return The number of cards in the hand
     */
    public static int getHandSize(long hand) {
        int count = 0;
        for (int i = 0; i < Card.NUM_CARDS; i++) {
            if (hand % 2 == 1)
                count++;
            hand /= 2;
        }

        return count;
    }

    /**
     * @param hand A hand of cards
     * @return A list of all the card ids in the hand
     */
    public static ArrayList<Integer> bitstringToIDs(long hand) {
        ArrayList<Integer> cards = new ArrayList<>();
        for (int i = 0; i < Card.NUM_CARDS; i++) {
            if (hand % 2 == 1)
                cards.add(Card.allCards[i].getId());
            hand /= 2;
        }
        return cards;
    }

    /**
     * @param hand A hand of cards
     * @return An array of all the card ids in the hand
     */
    public static int[] bitstringToIDArray(long hand) {
        int[] ids = new int[getHandSize(hand)];
        int index = 0;
        for (int i = 0; i < Card.NUM_CARDS; i++) {
            if (hand % 2 == 1) {
                ids[index] = Card.allCards[i].getId();
                index++;
            }
            hand /= 2;
        }

        return ids;
    }

    /**
     * @param ids A list of ids in a hand
     * @return The list of ids as a long
     */
    public static long idsToBitstring(ArrayList<Integer> ids) {
        long bitstring = 0L;
        for (int i : ids)
            bitstring = add(bitstring, i);
        return bitstring;
    }

    /**
     * @param ids An array of ids in a hand
     * @return The list of ids as a long
     */
    public static long idsToBitstring(int[] ids) {
        long bitstring = 0L;
        for (int i : ids)
            bitstring = add(bitstring, i);
        return bitstring;
    }

    /**
     * @param c The reference card
     * @param s The current state of the game
     * @return true if the opponent can make a meld from c
     */
    public static boolean canOpponentMeld(Card c, State s) {

        // Cards of same rank as c
        ArrayList<Card> sameRank = getSameRank(new ArrayList<>(Arrays.asList(Card.allCards)), c);

        // Cards of same suit as c which are adjacent
        ArrayList<Card> sameSuitAdj = getSameSuit(new ArrayList<>(Arrays.asList(Card.allCards)), c, 1);

        // Cards of same suit as c who's rank is 2 away
        ArrayList<Card> sameSuit = getSameSuit(new ArrayList<>(Arrays.asList(Card.allCards)), c, 2);
        sameSuit.removeAll(sameSuitAdj);

        // Filter cards from collections so they only contain cards in the deck or in
        // the opponent's hand
        sameRank.removeIf(card -> !contains(s.getHand(), card.getId()) && !contains(s.getBuried(), card.getId()));
        sameSuitAdj.removeIf(card -> !contains(s.getHand(), card.getId()) && !contains(s.getBuried(), card.getId()));
        sameSuit.removeIf(card -> !contains(s.getHand(), card.getId()) && !contains(s.getBuried(), card.getId()));

        if (sameRank.size() >= 2)
            return true;

        else if (sameSuitAdj.isEmpty() || sameSuit.isEmpty())
            return false;

        // Looking at all the cards which are 2 away, if there is no card between it and
        // c, remove it
        sameSuit.removeIf(card -> {
            for (Card card1 : sameSuitAdj) {
                if (card.getRank() > c.getRank())
                    return card.getRank() - 1 != card1.getRank();
                else
                    return card.getRank() + 1 != card1.getRank();
            }
            return false;
        });

        return !sameSuit.isEmpty();
    }

    public static boolean canOpponentMeld(Card c, long oppHand, long buried) {

        // Cards of same rank as c
        ArrayList<Card> sameRank = getSameRank(new ArrayList<>(Arrays.asList(Card.allCards)), c);

        // Cards of same suit as c which are adjacent
        ArrayList<Card> sameSuitAdj = getSameSuit(new ArrayList<>(Arrays.asList(Card.allCards)), c, 1);

        // Cards of same suit as c who's rank is 2 away
        ArrayList<Card> sameSuit = getSameSuit(new ArrayList<>(Arrays.asList(Card.allCards)), c, 2);
        sameSuit.removeAll(sameSuitAdj);

        // Filter cards from collections so they only contain cards in the deck or in
        // the opponent's hand
        sameRank.removeIf(card -> !contains(oppHand, card.getId()) && !contains(buried, card.getId()));
        sameSuitAdj.removeIf(card -> !contains(oppHand, card.getId()) && !contains(buried, card.getId()));
        sameSuit.removeIf(card -> !contains(oppHand, card.getId()) && !contains(buried, card.getId()));

        if (sameRank.size() >= 2)
            return true;

        else if (sameSuitAdj.isEmpty() || sameSuit.isEmpty())
            return false;

        // Looking at all the cards which are 2 away, if there is no card between it and
        // c, remove it
        sameSuit.removeIf(card -> {
            for (Card card1 : sameSuitAdj) {
                if (card.getRank() > c.getRank())
                    return card.getRank() - 1 != card1.getRank();
                else
                    return card.getRank() + 1 != card1.getRank();
            }
            return false;
        });

        return !sameSuit.isEmpty();
    }

    /**
     * @param hand A hand of cards
     * @param c    The card we're checking
     * @return true if the added card makes a new meld
     */
    public static boolean makesNewMeld(ArrayList<Card> hand, Card c) {
        ArrayList<Card> newHand = new ArrayList<>(hand);
        newHand.add(c);

        ArrayList<ArrayList<ArrayList<Card>>> bestMeldSets = MyGinRummyUtil.cardsToBestMeldSets(newHand);
        if (bestMeldSets.size() == 0)
            return false;

        for (ArrayList<Card> meld : bestMeldSets.get(0)) {
            if (meld.contains(c))
                return true;
        }

        return false;
    }

    /**
     * @param hand A hand of cards
     * @param id   The card we're checking
     * @return true if the added card makes a new meld
     */
    public static boolean makesNewMeld(long hand, int id) {
        return makesNewMeld(MyGinRummyUtil.bitstringToCards(hand), Card.getCard(id));
    }

    /**
     * @param hand A hand of cards
     * @param c    The card we're considering
     * @return The change in deadwood from inserting c and discarding the worst
     *         card. If return value is negative, drawing c increases the overall
     *         deadwood of our hand.
     */
    public static int getImprovement(ArrayList<Card> hand, Card c) {
        int minDeadwood = Integer.MAX_VALUE;
        ArrayList<Card> newCards = new ArrayList<>(hand);
        newCards.add(c);

        // Find all cards whose removal would reduce the hand's deadwood by the max
        // amount
        for (Card card : newCards) {
            ArrayList<Card> remainingCards = new ArrayList<>(newCards);
            remainingCards.remove(card);

            ArrayList<ArrayList<ArrayList<Card>>> bestMeldSets = cardsToBestMeldSets(remainingCards);

            int deadwood = bestMeldSets.isEmpty() ? getDeadwoodPoints(remainingCards)
                    : getDeadwoodPoints(bestMeldSets.get(0), remainingCards);
            if (deadwood < minDeadwood)
                minDeadwood = deadwood;
        }

        return cardsToBestMeldSets(hand).isEmpty() ? getDeadwoodPoints(hand) - minDeadwood
                : getDeadwoodPoints(cardsToBestMeldSets(hand).get(0), getUnmelded(hand, null)) - minDeadwood;
    }

    /**
     * @param hand A hand of cards
     * @param c_id The id of the card we're considering
     * @return The change in deadwood from inserting c and discarding the worst
     *         card. If return value is negative, drawing c increases the overall
     *         deadwood of our hand.
     */
    public static int getImprovement(long hand, int c_id) {
        return getImprovement(bitstringToCards(hand), Card.getCard(c_id));
    }

    /**
     * @param hand A hand of cards
     * @param c    The reference card
     * @return true if there exists at least one card of the same rank as c
     */
    public static boolean containsRank(ArrayList<Card> hand, Card c) {
        for (Card card : hand) {
            if (card.equals(c))
                continue; // Don't count card c
            if (card.getRank() == c.getRank())
                return true;
        }

        return false;
    }

    /**
     * @param hand A hand of cards
     * @param c    The reference card's id
     * @return true if there exists at least one card of the same rank as c
     */
    public static boolean containsRank(long hand, int c) {
        return containsRank(bitstringToCards(hand), Card.getCard(c));
    }

    /**
     * @param hand A hand of cards
     * @param c    The reference card
     * @return a list of all cards of the same rank as c
     */
    public static ArrayList<Card> getSameRank(ArrayList<Card> hand, Card c) {
        if (c == null)
            return new ArrayList<>();
        return (ArrayList<Card>) hand.stream().filter(card -> (card.getRank() == c.getRank() && !card.equals(c)))
                .collect(Collectors.toList());
    }

    /**
     * @param hand A hand of cards
     * @param c    The reference card's id
     * @return a list of all cards of the same rank as c
     */
    public static long getSameRank(long hand, int c) {
        return cardsToBitstring(getSameRank(bitstringToCards(hand), Card.getCard(c)));
    }

    /**
     * @param hand A hand of cards
     * @param c    The reference card
     * @param diff the difference between the cards' ranks
     * @return true if there exists at least one card of the same suit as c, given
     *         that its rank is within diff of c's rank
     */
    public static boolean containsSuit(ArrayList<Card> hand, Card c, int diff) {
        for (Card card : hand) {
            if (card.equals(c))
                continue; // Don't count card c
            if (card.getSuit() == c.getSuit() && Math.abs(c.getRank() - card.getRank()) <= diff)
                return true;
        }

        return false;
    }

    /**
     * @param hand A hand of cards
     * @param c    The reference card's id
     * @param diff the difference between the cards' ranks
     * @return true if there exists at least one card of the same suit as c, given
     *         that its rank is within diff of c's rank
     */
    public static boolean containsSuit(long hand, int c, int diff) {
        return containsSuit(bitstringToCards(hand), Card.getCard(c), diff);
    }

    /**
     * @param hand A hand of cards
     * @param c    The reference card
     * @param diff the difference between the cards' ranks
     * @return a list of all cards of same suit as c, given that its rank is within
     *         diff of c's rank
     */
    public static ArrayList<Card> getSameSuit(ArrayList<Card> hand, Card c, int diff) {
        if (c == null)
            return new ArrayList<>();
        return (ArrayList<Card>) hand
                .stream().filter(card -> (card.getSuit() == c.getSuit()
                        && Math.abs(c.getRank() - card.getRank()) <= diff && !card.equals(c)))
                .collect(Collectors.toList());
    }

    /**
     * @param hand A hand of cards
     * @param c    The reference card's id
     * @param diff the difference between the cards' ranks
     * @return a list of all cards of same suit as c, given that its rank is within
     *         diff of c's rank
     */
    public static long getSameSuit(long hand, int c, int diff) {
        return cardsToBitstring(getSameSuit(bitstringToCards(hand), Card.getCard(c), diff));
    }

    /**
     * @param cards A hand of cards
     * @return the card with the highest deadwood
     */
    public static Card getHighestDeadwood(ArrayList<Card> cards) {
        if (cards.size() == 0)
            return null;
        return cards.stream().reduce(cards.get(0), (max, c) -> getDeadwoodPoints(c) > getDeadwoodPoints(max) ? c : max);
    }

    /**
     * @param cards A hand of cards
     * @return the id of the card with the highest deadwood
     */
    public static int getHighestDeadwood(long cards) {
        try {
            return getHighestDeadwood(bitstringToCards(cards)).getId();
        } catch (NullPointerException e) {
            return -1;
        }

    }

    /**
     * @param hand      A hand of cards
     * @param drawnCard The card drawn - null if a card wasn't just picked up
     * @param face_up   The card that was face-up before the draw
     * @param range     The range of deadwoods still recorded. Range of 0 only gives
     *                  those who would lower deadwood the most.
     * @return A list of all cards whose removal would lower the deadwood within
     *         range of the most
     */
    public static ArrayList<Card> findHighestDiscards(ArrayList<Card> hand, Card drawnCard, Card face_up, int range) {

        ArrayList<Card> candidateCards = new ArrayList<>(hand);
        HashMap<Integer, ArrayList<Card>> candidateLog = new HashMap<>();

        int minDeadwood = Integer.MAX_VALUE;

        // Find all cards whose removal would reduce the hand's deadwood by the max
        // amount
        for (Card card : hand) {
            // Cannot draw and discard face up card.
            if (drawnCard != null && (card == drawnCard && drawnCard == face_up))
                continue;

            ArrayList<Card> remainingCards = new ArrayList<>(hand);
            remainingCards.remove(card);
            ArrayList<ArrayList<ArrayList<Card>>> bestMeldSets = cardsToBestMeldSets(remainingCards);
            int deadwood = bestMeldSets.isEmpty() ? getDeadwoodPoints(remainingCards)
                    : getDeadwoodPoints(bestMeldSets.get(0), remainingCards);
            if (deadwood <= minDeadwood) {
                if (deadwood < minDeadwood) {

                    candidateLog.put(minDeadwood, new ArrayList<>(candidateCards));

                    candidateCards.clear();
                    minDeadwood = deadwood;

                }
                candidateCards.add(card);
            }
        }

        for (HashMap.Entry<Integer, ArrayList<Card>> entry : candidateLog.entrySet())
            if (entry.getKey() - minDeadwood <= range)
                candidateCards.addAll(entry.getValue());

        return candidateCards;
    }

    /**
     * @param hand      A hand of cards
     * @param drawnCard The card drawn's id - -1 if a card wasn't just picked up
     * @param face_up   The if of the card that was face-up before the draw
     * @param range     The range of deadwoods still recorded
     * @return A list of all cards whose removal would lower the deadwood within
     *         range of the most
     */
    public static long findHighestDiscards(long hand, int drawnCard, int face_up, int range) {
        return cardsToBitstring(findHighestDiscards(bitstringToCards(hand),
                drawnCard == -1 ? null : Card.getCard(drawnCard), face_up == -1 ? null : Card.getCard(face_up), range));
    }

    /**
     * @param state The current state of the game
     * @return The expected deadwood for drawing one more card
     */
    public static double expectedDeadwoodForNextDraw(State state) {
        ArrayList<Card> unaccounted = bitstringToCards(state.getUnaccounted()); // Cards which have not been seen
        double sum = 0;

        for (Card card : unaccounted)
            sum += 1d / unaccounted.size() * getImprovement(bitstringToCards(state.getHand()), card);

        return sum;
    }

    /**
     * @param hand A hand of cards
     * @return The number of cards in the hand
     */
    public static int size(long hand) {
        String s = Long.toBinaryString(hand);
        int count = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '1')
                count++;
        }
        return count;
    }

    /**
     * Print out a hand of cards, with melds separated from the rest
     * 
     * @param hand A hand of cards
     */
    public static void printHandWithMelds(ArrayList<Card> hand) {
        ArrayList<ArrayList<ArrayList<Card>>> bestMeldSets = GinRummyUtil.cardsToBestMeldSets(hand);
        ArrayList<ArrayList<Card>> bestMeldSet;
        ArrayList<Card> unmelded = MyGinRummyUtil.getUnmelded(hand, null);
        unmelded.sort((c1, c2) -> {
            String c1str;
            String c2str;
            char first;

            switch (c1.toString().charAt(0)) {
                case '2': {
                    first = 'B';
                    break;
                }
                case '3': {
                    first = 'C';
                    break;
                }
                case '4': {
                    first = 'D';
                    break;
                }
                case '5': {
                    first = 'E';
                    break;
                }
                case '6': {
                    first = 'F';
                    break;
                }
                case '7': {
                    first = 'G';
                    break;
                }
                case '8': {
                    first = 'H';
                    break;
                }
                case '9': {
                    first = 'I';
                    break;
                }
                case 'T': {
                    first = 'J';
                    break;
                }
                case 'J': {
                    first = 'K';
                    break;
                }
                case 'Q': {
                    first = 'L';
                    break;
                }
                case 'K': {
                    first = 'M';
                    break;
                }
                default: {
                    first = 'A';
                }

            }
            c1str = first + c1.toString().substring(1);

            switch (c2.toString().charAt(0)) {
                case '2': {
                    first = 'B';
                    break;
                }
                case '3': {
                    first = 'C';
                    break;
                }
                case '4': {
                    first = 'D';
                    break;
                }
                case '5': {
                    first = 'E';
                    break;
                }
                case '6': {
                    first = 'F';
                    break;
                }
                case '7': {
                    first = 'G';
                    break;
                }
                case '8': {
                    first = 'H';
                    break;
                }
                case '9': {
                    first = 'I';
                    break;
                }
                case 'T': {
                    first = 'J';
                    break;
                }
                case 'J': {
                    first = 'K';
                    break;
                }
                case 'Q': {
                    first = 'L';
                    break;
                }
                case 'K': {
                    first = 'M';
                    break;
                }
                default: {
                    first = 'A';
                }

            }
            c2str = first + c2.toString().substring(1);

            return c1str.compareTo(c2str);
        });
        unmelded.forEach(c -> System.out.print(c.toString() + " "));
        System.out.println();

        if (!bestMeldSets.isEmpty()) {
            bestMeldSet = bestMeldSets.get(0);

            for (ArrayList<Card> meld : bestMeldSet) {
                meld.sort((c1, c2) -> {
                    String c1str;
                    String c2str;
                    char first;

                    switch (c1.toString().charAt(0)) {
                        case '2': {
                            first = 'B';
                            break;
                        }
                        case '3': {
                            first = 'C';
                            break;
                        }
                        case '4': {
                            first = 'D';
                            break;
                        }
                        case '5': {
                            first = 'E';
                            break;
                        }
                        case '6': {
                            first = 'F';
                            break;
                        }
                        case '7': {
                            first = 'G';
                            break;
                        }
                        case '8': {
                            first = 'H';
                            break;
                        }
                        case '9': {
                            first = 'I';
                            break;
                        }
                        case 'T': {
                            first = 'J';
                            break;
                        }
                        case 'J': {
                            first = 'K';
                            break;
                        }
                        case 'Q': {
                            first = 'L';
                            break;
                        }
                        case 'K': {
                            first = 'M';
                            break;
                        }
                        default: {
                            first = 'A';
                        }

                    }
                    c1str = first + c1.toString().substring(1);

                    switch (c2.toString().charAt(0)) {
                        case '2': {
                            first = 'B';
                            break;
                        }
                        case '3': {
                            first = 'C';
                            break;
                        }
                        case '4': {
                            first = 'D';
                            break;
                        }
                        case '5': {
                            first = 'E';
                            break;
                        }
                        case '6': {
                            first = 'F';
                            break;
                        }
                        case '7': {
                            first = 'G';
                            break;
                        }
                        case '8': {
                            first = 'H';
                            break;
                        }
                        case '9': {
                            first = 'I';
                            break;
                        }
                        case 'T': {
                            first = 'J';
                            break;
                        }
                        case 'J': {
                            first = 'K';
                            break;
                        }
                        case 'Q': {
                            first = 'L';
                            break;
                        }
                        case 'K': {
                            first = 'M';
                            break;
                        }
                        default: {
                            first = 'A';
                        }

                    }
                    c2str = first + c2.toString().substring(1);

                    return c1str.compareTo(c2str);
                });
                meld.forEach(c -> System.out.print(c.toString() + " "));
                System.out.println();
            }

            System.out.println("Deadwood: " + MyGinRummyUtil.getDeadwoodPoints(bestMeldSet, hand));
        }

        else
            System.out.println("Deadwood: " + MyGinRummyUtil.getDeadwoodPoints(hand));
    }

    /**
     * Print out a hand of cards, with melds separated from the rest
     * 
     * @param hand A hand of cards
     */
    public static void printHandWithMelds(long hand) {
        printHandWithMelds(bitstringToCards(hand));
    }

    /**
     * Print out a hand of cards
     * 
     * @param hand A hand of cards
     */
    public static void printHand(ArrayList<Card> hand) {
        hand.sort((c1, c2) -> {
            String c1str;
            String c2str;
            char first;

            switch (c1.toString().charAt(0)) {
                case '2': {
                    first = 'B';
                    break;
                }
                case '3': {
                    first = 'C';
                    break;
                }
                case '4': {
                    first = 'D';
                    break;
                }
                case '5': {
                    first = 'E';
                    break;
                }
                case '6': {
                    first = 'F';
                    break;
                }
                case '7': {
                    first = 'G';
                    break;
                }
                case '8': {
                    first = 'H';
                    break;
                }
                case '9': {
                    first = 'I';
                    break;
                }
                case 'T': {
                    first = 'J';
                    break;
                }
                case 'J': {
                    first = 'K';
                    break;
                }
                case 'Q': {
                    first = 'L';
                    break;
                }
                case 'K': {
                    first = 'M';
                    break;
                }
                default: {
                    first = 'A';
                }

            }
            c1str = first + c1.toString().substring(1);

            switch (c2.toString().charAt(0)) {
                case '2': {
                    first = 'B';
                    break;
                }
                case '3': {
                    first = 'C';
                    break;
                }
                case '4': {
                    first = 'D';
                    break;
                }
                case '5': {
                    first = 'E';
                    break;
                }
                case '6': {
                    first = 'F';
                    break;
                }
                case '7': {
                    first = 'G';
                    break;
                }
                case '8': {
                    first = 'H';
                    break;
                }
                case '9': {
                    first = 'I';
                    break;
                }
                case 'T': {
                    first = 'J';
                    break;
                }
                case 'J': {
                    first = 'K';
                    break;
                }
                case 'Q': {
                    first = 'L';
                    break;
                }
                case 'K': {
                    first = 'M';
                    break;
                }
                default: {
                    first = 'A';
                }

            }
            c2str = first + c2.toString().substring(1);

            return c1str.compareTo(c2str);
        });

        hand.forEach(c -> System.out.print(c.toString() + " "));
        System.out.println();
    }

    /**
     * Print out a hand of cards
     * 
     * @param hand A hand of cards
     */
    public static void printHand(long hand) {
        printHand(bitstringToCards(hand));
    }

    /**
     * @param strategy A strategy
     * @return The encoded strategy
     */
    static String encoded(int[] strategy) {
        String encoded = "";
        for (int i = 0; i < strategy.length; i++) {
            encoded += Integer.toHexString(strategy[i]);
        }

        return encoded;
    }

    /**
     * @param hex An encoded strategy
     * @return The decoded strategy
     */
    static int[] decoded(String hex) {
        int[] strategy = new int[6];
        for (int i = 0; i < hex.length(); i++) {
            strategy[i] = (int) Long.parseLong(hex.substring(i, i + 1), 16);
        }

        return strategy;
    }

    /**
     * @param hex An encoded strategy
     * @return The decoded strategy
     */
    static int[] decoded(int hex) {
        String hexString = Integer.toHexString(hex);
        int[] strategy = new int[6];
        for (int i = 0; i < hexString.length(); i++) {
            strategy[i] = (int) Long.parseLong(hexString.substring(i, i + 1), 16);
        }

        return strategy;
    }

    /**
     * Gets the probability that the opponent has the card in question
     * 
     * @param c card in question
     * @param s Game State
     * @return The probability (%) of the opponent having a card
     */
    static double getProbabilityThatOpponentHasUnseenCard(Card c, State s) {
        return getProbabilityThatOpponentHasUnseenCard(cardAsBitString(c), s);
    }

    /**
     * Gets the probability that the opponent has the card in question
     * 
     * @param c Bitstring of the card in question
     * @param s Game State
     * @return The probability (%) of the opponent having a card
     */
    static double getProbabilityThatOpponentHasUnseenCard(long c, State s) {
        // if card is not in the game anymore, return 0
        if (contains(s.getBuried(), c))
            return 0d;
        // if opponent has the card passed, return 1.0;
        else if (contains(s.getOppHand(), c))
            return 1d;
        // if we know all cards on the opponent hand, and card is not in hand, then prob
        // = 0%
        else if (bitstringToCards(s.getOppHand()).size() == 10)
            return 0d;

        // at this point, we know the card in question is unseen

        // card in question
        Card card = bitstringToCards(c).get(0);

        // can the opponent make a meld out of this card?
        int opponentPossibleMelds = s.getPotentialOpponentMelds(card.getId()).size();

        int sizeOfUnseenOppHand = 10 - MyGinRummyUtil.size(s.getOppHand());
        int sizeOfUnseen = MyGinRummyUtil.size(s.getUnaccounted());

        double unweightedProbabilityInOppHand = sizeOfUnseenOppHand/(double)sizeOfUnseen;

        // if opponent can meld, then it means that there's a
        // chance that the opponent has this card
        if (opponentPossibleMelds > 0) {
            // nominator adding decimal of how many possible melds can the opponent make
            double nominator = 1 + (opponentPossibleMelds / 10d);
            return nominator / sizeOfUnseen;
        }


        // figure out a way to calculate possible melds with those cards
        // see if the meld cards are still in play (unseen , opponent hand)

        // 1/ unseen cards : chance if everything fails
        //return 1d / bitstringToCards(s.getUnseen()).size();

        //return (number of unseen cards in opponent's hand)/(number of unseen cards) if all else fails
        return unweightedProbabilityInOppHand;
    }

    /**
     * Return a bitstring representation of this card
     * 
     * @param card the card
     * @return a bitstring with the 1 set in bit position equal the card's id
     */
    public static long cardAsBitString(Card card) {
        return 1L << card.getId();
    }
}

/**
 * Class to hold parameters for player strategy TODO: Implement the regret
 * matching algorithm for sequential games.
 */
class GeneralStrategy {

    // <editor-fold desc="Instance Variables">
    /**
     * A map from the information set to the probability that we will knock
     */
    private HashMap<String, Double> knockStrat;

    /**
     * A map from the information set to the probability that we will draw
     */
    private HashMap<String, Double> drawStrat;

    /**
     * Max deadwood a card can contribute to a hand while not being able to be
     * melded within 2 turns, in order for us to avoid discarding it.
     */
    private int maxIsolatedSingleDeadwood;

    /**
     * After this turn has passed, we will no longer go out of our way to keep cards
     * "protected" by maxIsolatedSingleDeadwood.
     */
    private int minIsolatedSingleDiscardTurn;

    /**
     * Max deadwood a card can contribute to a hand while not being able to be
     * melded within 1 turn, in order for us to avoid discarding it.
     */
    private int maxSingleDeadwood;

    /**
     * After this turn has passed, we will no longer go out of our way to keep cards
     * "protected" by maxSingleDeadwood.
     */
    private int minSingleDiscardTurn;

    /**
     * If the probability of getting a gin within x turns is >=
     * minWaitForGinProbability, we should wait to try to get a gin. TODO: Figure
     * out if this is even useful, and if it is, implement a way to calculate the
     * probability of getting a gin within x turns. Maybe generalize to find the
     * probability that total deadwood will become <= some y in x turns.
     */
    private double minWaitForGinProbability;

    /**
     * If our total deadwood is below this value, we should wait and try to
     * undercut. TODO: Determine whether this would affect our strategy differently
     * than minWaitForGinProbability. They might mostly overlap, in which case I
     * only need to consider one of the 2.
     */
    private int minUndercutDeadwood;

    /**
     * Min turn to try to layoff cards
     */
    private int minLayoffTurn;

    /**
     * Minimum change in deadwood the face-up card can contribute in order for us to
     * consider drawing it.
     */
    private int minPickupDifference;
    // </editor-fold>

    /**
     * Constructor
     */

    GeneralStrategy(int[] strategy, HashMap<String, Double> knockStrat, HashMap<String, Double> drawStrat) {

        this.knockStrat = knockStrat;
        this.drawStrat = drawStrat;

        this.maxIsolatedSingleDeadwood = strategy[1] <= 10 && strategy[1] > 0 ? strategy[1] : 10;
        this.minIsolatedSingleDiscardTurn = Math.max(strategy[2], 0);

        this.maxSingleDeadwood = strategy[3] <= 10 && strategy[3] > 0 ? strategy[3] : 10;
        this.minSingleDiscardTurn = Math.max(strategy[4], 0);

        // this.minWaitForGinProbability = minWaitForGinProbability <= 1 &&
        // minWaitForGinProbability >= 0 ? minWaitForGinProbability : 0.0;

        // this.minUndercutDeadwood = minUndercutDeadwood <= 10 && minUndercutDeadwood
        // >= 0 ? minUndercutDeadwood : 10;

        // this.minLayoffTurn = Math.max(minLayoffTurn, 0);

        this.minPickupDifference = strategy[5] <= 10 && strategy[5] >= 0 ? strategy[5] : 0;

    }

    // <editor-fold desc="Getters and Setters">
    public HashMap<String, Double> getKnockStrat() {
        return knockStrat;
    }

    public double getKnockAt(String s) {
        try {
            return knockStrat.get(s);
        } catch (Exception e) {
            return 0.0;
        }
    }

    public double getDrawAt(String s) {
        try {
            return drawStrat.get(s);
        } catch (Exception e) {
            return 0.0;
        }
    }

    int getMaxIsolatedSingleDeadwood() {
        return maxIsolatedSingleDeadwood;
    }

    int getMinIsolatedSingleDiscardTurn() {
        return minIsolatedSingleDiscardTurn;
    }

    int getMaxSingleDeadwood() {
        return maxSingleDeadwood;
    }

    int getMinSingleDiscardTurn() {
        return minSingleDiscardTurn;
    }

    double getMinWaitForGinProbability() {
        return minWaitForGinProbability;
    }

    int getMinUndercutDeadwood() {
        return minUndercutDeadwood;
    }

    int getMinLayoffTurn() {
        return minLayoffTurn;
    }

    int getMinPickupDifference() {
        return minPickupDifference;
    }

    public void setMaxIsolatedSingleDeadwood(int maxIsolatedSingleDeadwood) {
        this.maxIsolatedSingleDeadwood = maxIsolatedSingleDeadwood;
    }

    public void setMinIsolatedSingleDiscardTurn(int minIsolatedSingleDiscardTurn) {
        this.minIsolatedSingleDiscardTurn = minIsolatedSingleDiscardTurn;
    }

    public void setMaxSingleDeadwood(int maxSingleDeadwood) {
        this.maxSingleDeadwood = maxSingleDeadwood;
    }

    public void setMinSingleDiscardTurn(int minSingleDiscardTurn) {
        this.minSingleDiscardTurn = minSingleDiscardTurn;
    }

    public void setMinWaitForGinProbability(double minWaitForGinProbability) {
        this.minWaitForGinProbability = minWaitForGinProbability;
    }

    public void setMinUndercutDeadwood(int minUndercutDeadwood) {
        this.minUndercutDeadwood = minUndercutDeadwood;
    }

    public void setMinLayoffTurn(int minLayoffTurn) {
        this.minLayoffTurn = minLayoffTurn;
    }

    public void setMinPickupDifference(int minPickupDifference) {
        this.minPickupDifference = minPickupDifference;
    }
    // </editor-fold>
}