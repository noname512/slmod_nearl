package nearlmod.potions;

import basemod.BaseMod;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import nearlmod.NLMOD;
import nearlmod.actions.ChooseSpecificCardAction;
import nearlmod.characters.Nearl;

import java.util.ArrayList;
import java.util.Collections;

public class FriendPotion extends AbstractPotion {
    public static String ID = "nearlmod:FriendPotion";
    public static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(ID);
    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;
    public FriendPotion() {
        super(NAME, ID, PotionRarity.COMMON, PotionSize.CARD, PotionColor.ANCIENT);
        this.labOutlineColor = NLMOD.NearlGold;
        this.description = potionStrings.DESCRIPTIONS[0];
        this.isThrown = false;
        this.targetRequired = false;
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.tips.add(new PowerTip(TipHelper.capitalize(BaseMod.getKeywordTitle("nearlmod:friendcard")), BaseMod.getKeywordDescription("nearlmod:friendcard")));
    }
    @Override
    public void use(AbstractCreature abstractCreature) {
        ArrayList<AbstractCard> list = Nearl.getUnuniqueFriendCard(false);
        Collections.shuffle(list);
        ArrayList<AbstractCard> chooseList = new ArrayList<>(list.subList(0, 3));
        addToBot(new ChooseSpecificCardAction(chooseList, true));
    }

    @Override
    public int getPotency(int i) {
        return 0;
    }

    @Override
    public AbstractPotion makeCopy() {
        return new FriendPotion();
    }
}
