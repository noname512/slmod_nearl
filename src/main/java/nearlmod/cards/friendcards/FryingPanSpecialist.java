package nearlmod.cards.friendcards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.purple.FollowUp;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import nearlmod.orbs.Gummy;
import nearlmod.patches.AbstractCardEnum;

public class FryingPanSpecialist extends AbstractFriendCard {
    public static final String ID = "nearlmod:FryingPanSpecialist";
    public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String IMG_PATH = "resources/nearlmod/images/cards/fryingpanspecialist.png";
    private static final int COST = 1;
    private static final int ATTACK_DMG = 8;
    private static final int UPGRADE_PLUS_DMG = 3;

    public FryingPanSpecialist() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                CardType.ATTACK, AbstractCardEnum.FRIEND_BLUE,
                CardRarity.SPECIAL, CardTarget.ENEMY, "nearlmod:Gummy");
        magicNumber = baseMagicNumber = ATTACK_DMG;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        DamageInfo info = new DamageInfo(p, magicNumber);
        info.name = Gummy.ORB_ID + damageSuffix;
        addToBot(new DamageAction(m, info));
        if (extraTriggered(1)) {    // 这张卡已经在 cardsPlayedThisCombat 里了，所以要检查上一张
            addToBot(new ApplyPowerAction(m, p, new StrengthPower(m, -99)));
            if (m != null && !m.hasPower("Artifact")) {
                addToBot(new ApplyPowerAction(m, p, new GainStrengthPower(m, 99)));
            }
        }
    }

    @Override
    public boolean extraTriggered() {
        return extraTriggered(0);
    }

    public boolean extraTriggered(int prev) {
        if (AbstractDungeon.actionManager.cardsPlayedThisCombat.size() <= prev) {
            return false;
        }
        AbstractCard c = AbstractDungeon.actionManager.cardsPlayedThisCombat.get(AbstractDungeon.actionManager.cardsPlayedThisCombat.size() - 1 - prev);
        if (c instanceof AbstractFriendCard && ((AbstractFriendCard)c).belongFriend.equals(belongFriend)) {
            return true;
        }
        else {
            return false;
        }

    }

    @Override
    public AbstractCard makeCopy() {
        return new FryingPanSpecialist();
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_PLUS_DMG);
        }
    }
}
