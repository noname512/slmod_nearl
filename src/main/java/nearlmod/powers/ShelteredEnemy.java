package nearlmod.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class ShelteredEnemy extends AbstractPower implements CloneablePowerInterface {
    public static final String POWER_ID = "nearlmod:ShelteredEnemy";
    public static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public ShelteredEnemy(AbstractCreature owner) {
        name = NAME;
        ID = POWER_ID;
        this.owner = owner;
        region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage("resources/nearlmod/images/powers/sheltered 128.png"), 0, 0, 128, 128);
        region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage("resources/nearlmod/images/powers/sheltered 48.png"), 0, 0, 48, 48);
        type = PowerType.BUFF;
        updateDescription();
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0];
    }

    @Override
    public AbstractPower makeCopy() {
        return new ShelteredEnemy(owner);
    }

    @Override
    public float atDamageReceive(float damage, DamageInfo.DamageType damageType) {
        if ((damageType == DamageInfo.DamageType.NORMAL) && (owner.currentHealth * 2 <= owner.maxHealth))
            return damage * 0.4F;
        return damage;
    }

    @Override
    public int onLoseHp(int damageAmount) {
        if (owner.currentHealth > owner.maxHealth && (owner.currentHealth - damageAmount) * 2 <= owner.maxHealth) {
            this.flash();
        }
        return damageAmount;
    }
}
