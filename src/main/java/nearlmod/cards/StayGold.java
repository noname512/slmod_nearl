package nearlmod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import nearlmod.actions.SummonFriendAction;
import nearlmod.characters.Nearl;
import nearlmod.orbs.Blemishine;
import nearlmod.patches.AbstractCardEnum;
import nearlmod.patches.NearlTags;
import nearlmod.powers.LightPower;
import nearlmod.stances.AtkStance;
import nearlmod.stances.DefStance;

public class StayGold extends AbstractNearlCard {
    public static final String ID = "nearlmod:StayGold";
    public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String IMG_PATH = "resources/nearlmod/images/cards/staygold.png";
    private static final int COST = 2;
    private static final int UPGRADE_COST = 1;
    private static final int LIGHT_GAIN = 3;
    private static final int BLOCK_AMT = 5;

    public StayGold() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                CardType.POWER, AbstractCardEnum.NEARL_GOLD,
                CardRarity.UNCOMMON, CardTarget.SELF);
        magicNumber = baseMagicNumber = LIGHT_GAIN;
        block = baseBlock = BLOCK_AMT;
        tags.add(NearlTags.IS_SUMMON_CARD);
        tags.add(NearlTags.IS_GAIN_LIGHT);
        tags.add(NearlTags.FRIEND_RELATED);
        belongFriend = Blemishine.ORB_ID;

        previewList = Nearl.getFriendCard(Blemishine.ORB_ID);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new LightPower(p, magicNumber), magicNumber));
        if (upgraded) {
            addToBot(new GainBlockAction(p, block));
        }
        if (p.stance.ID.equals(DefStance.STANCE_ID)) {
            addToBot(new ChangeStanceAction(new AtkStance()));
        }

        addToBot(new SummonFriendAction(new Blemishine()));
    }

    @Override
    public AbstractCard makeCopy() {
        return new StayGold();
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(UPGRADE_COST);
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
