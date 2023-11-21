package nearlmod.cards.friendcards;

import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import nearlmod.orbs.AbstractFriend;
import nearlmod.patches.AbstractCardEnum;

public class WhipSword extends AbstractFriendCard {
    public static final String ID = "nearlmod:WhipSword";
    public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String IMG_PATH = "images/cards/whipsword.png";
    private static final int COST = 2;
    private static final int ATTACK_DMG = 3;
    private static final int EXTRA_DMG = 8;

    public WhipSword() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                CardType.ATTACK, AbstractCardEnum.NEARL_GOLD,
                CardRarity.SPECIAL, CardTarget.ALL_ENEMY, "nearlmod:Whislash");
        magicNumber = baseMagicNumber = EXTRA_DMG;
        secondMagicNumber = baseSecondMagicNumber = ATTACK_DMG;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int dmg = secondMagicNumber;
        if (p.hasPower("Strength"))
            dmg += p.getPower("Strength").amount;
        if (upgraded)
            dmg += secondMagicNumber;
        DamageInfo info = new DamageInfo(p, dmg);
        info.name = belongFriend + AbstractFriendCard.damageSuffix;
        for (AbstractMonster ms : AbstractDungeon.getMonsters().monsters)
            addToBot(new DamageAction(ms, info));
        for (AbstractOrb orb : p.orbs)
            if (orb instanceof AbstractFriend) {
                info = new DamageInfo(p, secondMagicNumber + ((AbstractFriend)orb).trustAmount);
                info.name = orb.ID + AbstractFriendCard.damageSuffix;
                for (AbstractMonster ms : AbstractDungeon.getMonsters().monsters)
                    addToBot(new DamageAction(ms, info));
            }
    }

    @Override
    public AbstractCard makeCopy() {
        return new WhipSword();
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
