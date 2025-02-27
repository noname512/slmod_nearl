package nearlmod.cards;

import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import nearlmod.actions.SummonFriendAction;
import nearlmod.characters.Nearl;
import nearlmod.orbs.Nightingale;
import nearlmod.orbs.Shining;
import nearlmod.patches.AbstractCardEnum;
import nearlmod.patches.NearlTags;
import nearlmod.stances.AtkStance;

public class TheReturn extends AbstractNearlCard {
    public static final String ID = "nearlmod:TheReturn";
    public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String IMG_PATH = "resources/nearlmod/images/cards/thereturn.png";
    private static final int COST = 3;
    private static final int UPGRADE_COST = 2;

    public TheReturn() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                CardType.POWER, AbstractCardEnum.NEARL_GOLD,
                CardRarity.RARE, CardTarget.SELF);
        tags.add(NearlTags.IS_SUMMON_CARD);
        tags.add(NearlTags.FRIEND_RELATED);
        belongFriend = Nightingale.ORB_ID;

        previewList = Nearl.getFriendCard(Shining.ORB_ID);
        previewList.addAll(Nearl.getFriendCard(Nightingale.ORB_ID));
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SummonFriendAction(new Shining()));
        addToBot(new SummonFriendAction(new Nightingale()));

        if (!p.stance.ID.equals(AtkStance.STANCE_ID)) {
            addToBot(new ChangeStanceAction(new AtkStance()));
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new TheReturn();
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            rawDescription = UPGRADE_DESCRIPTION + DESCRIPTION;
            upgradeBaseCost(UPGRADE_COST);
            // isInnate = true;
            initializeDescription();
        }
    }
}
