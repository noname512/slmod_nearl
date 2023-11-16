package nearlmod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.colorless.RitualDagger;
import com.megacrit.cardcrawl.cards.purple.LessonLearned;
import com.megacrit.cardcrawl.cards.red.SearingBlow;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import nearlmod.actions.DawnAction;
import nearlmod.patches.AbstractCardEnum;

import java.util.Iterator;

public class Dawn extends AbstractNearlCard {
    public static final String ID = "nearlmod:Dawn";
    public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String IMG_PATH = "images/cards/dawn.png";
    private static final int COST = 3;
    private static final int ATTACK_DMG = 10;
    private static final int UPGRADE_PLUS_DMG = 5;

    public Dawn copiedFrom = null;

    public Dawn() {
        this(null, 0);
    }

    public Dawn(Dawn from, int upgrades) {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                CardType.ATTACK, AbstractCardEnum.NEARL_GOLD,
                CardRarity.RARE, CardTarget.ALL_ENEMY);
        damage = baseDamage = ATTACK_DMG;
        isMultiDamage = true;
        exhaust = true;
        isEthereal = true;
        this.timesUpgraded = upgrades;
        copiedFrom = from;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DawnAction(new DamageInfo(p, damage, damageTypeForTurn), this));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Dawn(this, timesUpgraded);
    }

    @Override
    public void upgrade() {
        upgradeDamage(UPGRADE_PLUS_DMG);
        ++this.timesUpgraded;
        this.upgraded = true;
        this.name = cardStrings.NAME + "+" + this.timesUpgraded;
        this.initializeTitle();
    }
    @Override
    public boolean canUpgrade() {
        return true;
    }

}
