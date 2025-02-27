package nearlmod.cards.friendcards;

import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import nearlmod.actions.AddFriendCardToHandAction;
import nearlmod.patches.AbstractCardEnum;

public class TempestCommand extends AbstractFriendCard {
    public static final String ID = "nearlmod:TempestCommand";
    public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String IMG_PATH = "resources/nearlmod/images/cards/tempestcommand.png";
    private static final int COST = 2;
    private static final int ATTACK_DMG = 14;
    private static final int UPGRADE_DMG = 3;

    public TempestCommand() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                CardType.ATTACK, AbstractCardEnum.FRIEND_BLUE,
                CardRarity.SPECIAL, CardTarget.ENEMY, "nearlmod:Horn");
        magicNumber = baseMagicNumber = ATTACK_DMG;
        cardsToPreview = new Salvo();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        DamageInfo info = new DamageInfo(p, magicNumber);
        info.name = belongFriend + damageSuffix;
        addToBot(new DamageAction(m, info));
        AbstractFriendCard card = new Salvo();
        if (upgraded) card.upgrade();
        addToBot(new AddFriendCardToHandAction(card));
    }

    @Override
    public AbstractCard makeCopy() {
        return new TempestCommand();
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_DMG);
            cardsToPreview.upgrade();
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
