package nearlmod.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.unique.LoseEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class LoseEnergyPower extends AbstractPower implements CloneablePowerInterface {
    public static final String POWER_ID = "nearlmod:LoseEnergyPower";
    public static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public final AbstractPlayer p;

    public LoseEnergyPower(AbstractPlayer player, int amount) {
        name = NAME;
        ID = POWER_ID;
        this.owner = player;
        this.p = player;
        region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage("resources/nearlmod/images/powers/loseenergy power 84.png"), 0, 0, 84, 84);
        region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage("resources/nearlmod/images/powers/loseenergy power 32.png"), 0, 0, 32, 32);
        type = PowerType.DEBUFF;
        this.amount = amount;
        updateDescription();
    }

    @Override
    public void updateDescription() {
        StringBuilder sb = new StringBuilder();
        sb.append(DESCRIPTIONS[0]);
        for (int i = 0; i < amount; i++) {
            sb.append(" [E] ");
        }
        sb.append(DESCRIPTIONS[1]);
        description = sb.toString();
    }

    @Override
    public void atStartOfTurn() {
        addToBot(new LoseEnergyAction(amount));
        addToBot(new RemoveSpecificPowerAction(p, p, this));
    }

    @Override
    public AbstractPower makeCopy() {
        return new LoseEnergyPower(p, amount);
    }
}
