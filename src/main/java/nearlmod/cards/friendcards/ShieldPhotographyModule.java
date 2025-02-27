package nearlmod.cards.friendcards;

import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import nearlmod.orbs.Aurora;
import nearlmod.patches.AbstractCardEnum;
import nearlmod.patches.NearlTags;
import nearlmod.powers.BloodLoss;

public class ShieldPhotographyModule extends AbstractFriendCard {
    public static final String ID = "nearlmod:ShieldPhotographyModule";
    public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String IMG_PATH = "resources/nearlmod/images/cards/shieldphotographymodule.png";
    private static final int COST = 2;
    private static final int TEMP_HP_GAIN = 30;
    private static final int UPGRADE_PLUS_HP = 15;

    public ShieldPhotographyModule() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                CardType.SKILL, AbstractCardEnum.FRIEND_BLUE,
                CardRarity.SPECIAL, CardTarget.SELF, "nearlmod:Aurora");

        this.bannerSmallRegion = ImageMaster.CARD_BANNER_RARE;
        this.bannerLargeRegion = ImageMaster.CARD_BANNER_RARE_L;
        secondMagicNumber = baseSecondMagicNumber = TEMP_HP_GAIN;
        tags.add(NearlTags.IS_UNIQUE_CARD);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new AddTemporaryHPAction(p, p, secondMagicNumber));
        addToBot(new ApplyPowerAction(p, p, new BloodLoss(p)));
        setUniqueUsed(Aurora.ORB_ID);
    }

    @Override
    public AbstractCard makeCopy() {
        return new ShieldPhotographyModule();
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeSecondMagicNumber(UPGRADE_PLUS_HP);
        }
    }
}
