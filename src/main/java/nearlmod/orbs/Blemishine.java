package nearlmod.orbs;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import nearlmod.cards.friendcards.*;

public class Blemishine extends AbstractFriend {

    public static final String ORB_ID = "nearlmod:Blemishine";
    private static final OrbStrings orbStrings = CardCrawlGame.languagePack.getOrbString(ORB_ID);
    public static final String NAME = orbStrings.NAME;
    public static final String[] DESCRIPTION = orbStrings.DESCRIPTION;
    public static final String IMAGE = "images/orbs/blemishine.png";
    public boolean upgraded;

    public Blemishine(int amount) {
        super(ORB_ID, NAME, 0, 0, DESCRIPTION[0], "", IMAGE);

        showEvokeValue = false;
        angle = MathUtils.random(360.0f);
        channelAnimTimer = 0.5f;
        passiveAmount = amount;
        upgraded = false;
        updateDescription();
    }

    public Blemishine() {
        this(0);
    }

    @Override
    public void applyStrength(int amount) {
        super.applyStrength(amount);
        updateDescription();
    }

    public void upgrade() {
        upgraded = true;
        applyStrength(2);
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTION[upgraded? 2 : 0] + passiveAmount + DESCRIPTION[1];
    }

    @Override
    public AbstractOrb makeCopy() {
        return new Blemishine();
    }

    @Override
    public void onStartOfTurn() {
        int random = AbstractDungeon.cardRng.random(uniqueUsed? 1 : 0, 3);
        AbstractCard card;
        switch (random) {
            case 0:
                card = new DivineAvatar();
                break;
            case 1:
                card = new SurgingBrilliance();
                break;
            case 2:
                card = new DeterringRadiance();
                break;
            default:
                card = new CraftsmanEcho();
        }
        if (upgraded) card.upgrade();
        AbstractDungeon.player.hand.addToHand(card);
    }
}
