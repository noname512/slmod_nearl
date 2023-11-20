package nearlmod.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import nearlmod.actions.SummonFromDeckToHandAction;
import nearlmod.patches.AbstractCardEnum;
import nearlmod.patches.NearlTags;

public class ReliableFriend extends AbstractNearlCard {
    public static final String ID = "nearlmod:ReliableFriend";
    public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String IMG_PATH = "images/cards/nearldefend.png";
    private static final int COST = 1;
    private static final int UPGRADE_PLUS_COST = 0;

    public ReliableFriend() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                CardType.SKILL, AbstractCardEnum.NEARL_GOLD,
                CardRarity.UNCOMMON, CardTarget.SELF);
        exhaust = true;
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if (!super.canUse(p, m)) return false;
        for (AbstractCard card : p.drawPile.group)
            if (card.hasTag(NearlTags.IS_SUMMON_CARD))
                return true;
        this.cantUseMessage = "抽牌堆内没有召唤伙伴的能力牌。";
        return false;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SummonFromDeckToHandAction(1, true));
    }

    @Override
    public AbstractCard makeCopy() {
        return new ReliableFriend();
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(UPGRADE_PLUS_COST);
        }
    }
}
