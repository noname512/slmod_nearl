package nearlmod.orbs;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import nearlmod.actions.AddFriendCardToHandAction;
import nearlmod.cards.BladeOfBlazingSun;
import nearlmod.cards.Cooperate;
import nearlmod.cards.StayGold;
import nearlmod.cards.SwordShield;
import nearlmod.cards.friendcards.*;

import java.util.ArrayList;

public class Blemishine extends AbstractFriend {

    public static final String ORB_ID = "nearlmod:Blemishine";
    private static final OrbStrings orbStrings = CardCrawlGame.languagePack.getOrbString(ORB_ID);
    public static final String NAME = orbStrings.NAME;
    public static final String[] DESCRIPTION = orbStrings.DESCRIPTION;
    public static final String IMAGE = "resources/nearlmod/images/orbs/blemishine.png";

    public Blemishine(int amount) {
        super(ORB_ID, NAME, DESCRIPTION, IMAGE, amount);
    }

    public Blemishine() {
        this(0);
    }

    @Override
    public AbstractOrb makeCopy() {
        return new Blemishine();
    }

    public static AbstractFriendCard getRandomCard(boolean upgraded, boolean notUnique) {
        ArrayList<AbstractFriendCard> cards = new ArrayList<>();
        if (!notUnique)
            cards.add(new DivineAvatar());
        cards.add(new SurgingBrilliance());
        cards.add(new DeterringRadiance());
        cards.add(new CraftsmanEcho());
        return getRandomCard(cards, upgraded);
    }

    @Override
    public AbstractFriendCard getUniqueCard() {
        return new DivineAvatar();
    }

    @Override
    public void onStartOfTurn() {
        addToBot(new AddFriendCardToHandAction(getRandomCard(upgraded, uniqueUsed)));
    }

    @Override
    public ArrayList<AbstractCard> getRelateCards() {
        ArrayList<AbstractCard> list = new ArrayList<AbstractCard>();
        list.add(new StayGold());
        list.add(new Cooperate());
        list.add(new SwordShield());
        list.add(new BladeOfBlazingSun());
        return list;
    }
}
