package nearlmod.monsters;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;
import nearlmod.actions.SummonOrbAction;
import nearlmod.cards.BraveTheDarkness;
import nearlmod.cards.NightScouringGleam;
import nearlmod.orbs.Blemishine;
import nearlmod.powers.DoubleBossPower;

public class CorruptKnight extends AbstractMonster {
    public static final String ID = "nearlmod:CorruptKnight";
    public static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = monsterStrings.NAME;
    public static final String[] MOVES = monsterStrings.MOVES;
    public static final String[] DIALOG = monsterStrings.DIALOG;
    public static final String IMAGE = "images/monsters/corruptknight.png";
    public boolean isWhitheredDead = false;

    public CorruptKnight(float x, float y) {
        super(NAME, ID, 130, 25.0F, 0, 150.0F, 320.0F, IMAGE, x, y);
        this.type = EnemyType.ELITE;
        if (AbstractDungeon.ascensionLevel >= 8)
            setHp(145);
        if (AbstractDungeon.ascensionLevel >= 18) {
            this.damage.add(new DamageInfo(this, 20));
            this.damage.add(new DamageInfo(this, 99));
        } else if (AbstractDungeon.ascensionLevel >= 3) {
            this.damage.add(new DamageInfo(this, 20));
            this.damage.add(new DamageInfo(this, 90));
        } else {
            this.damage.add(new DamageInfo(this, 18));
            this.damage.add(new DamageInfo(this, 90));
        }
        loadAnimation("images/monsters/enemy_1513_dekght/enemy_1513_dekght.atlas", "images/monsters/enemy_1513_dekght/enemy_1513_dekght37.json", 1.5F);
        this.flipHorizontal = true;
        this.stateData.setMix("Idle", "Die", 0.1F);
        this.state.setAnimation(0, "Idle", true);
    }


    @Override
    public void usePreBattleAction() {
        addToBot(new ApplyPowerAction(this, this, new DoubleBossPower(this)));
        AbstractPlayer p = AbstractDungeon.player;
        if (AbstractDungeon.ascensionLevel < 15) {
            addToBot(new ApplyPowerAction(p, this, new ArtifactPower(p, 2)));
        }
        else {
            addToBot(new ApplyPowerAction(p, this, new ArtifactPower(p, 1)));
        }
        addToBot(new SummonOrbAction(new Blemishine()));
        addToBot(new SummonOrbAction(new Blemishine()));
        AbstractCard card = new NightScouringGleam();
        card.upgrade();
        int amount = 1;
        if (AbstractDungeon.ascensionLevel < 15) amount = 2;
        addToBot(new MakeTempCardInHandAction(card, amount));
        card = new BraveTheDarkness();
        card.upgrade();
        card.rawDescription += " NL 保留 。";
        card.selfRetain = true;
        card.initializeDescription();
        AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(card, Settings.WIDTH * 0.5F, Settings.HEIGHT * 0.5F));
    }

    @Override
    public void takeTurn() {
        int attTimes = 1;
        if (isWhitheredDead) attTimes++;
        if (this.nextMove == 2) {
            addToBot(new WaitAction(2.5F));
            this.state.setAnimation(0, "Skill_End", false);
            this.state.addAnimation(0, "Idle", true, 0);
            for (int i = 0; i < attTimes; i++) {
                addToBot(new DamageAction(AbstractDungeon.player, this.damage.get(1), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
            }
            setMove((byte) 3, Intent.ATTACK, this.damage.get(0).base, attTimes, (attTimes > 1));
        } else {
            addToBot(new WaitAction(1.0F));
            this.state.setAnimation(0, "Attack", false);
            this.state.addAnimation(0, "Idle", true, 0);
            for (int i = 0; i < attTimes; i++) {
                addToBot(new DamageAction(AbstractDungeon.player, this.damage.get(0)));
            }
            if ((this.nextMove != 7) && (this.nextMove != 1)) {
                setMove((byte) (this.nextMove + 1), Intent.ATTACK, this.damage.get(0).base, attTimes, (attTimes > 1));
            } else {
                setMove(MOVES[0], (byte) 2, Intent.ATTACK, this.damage.get(1).base, attTimes, (attTimes > 1));
                this.state.setAnimation(0, "Skill_Start", false);
                this.state.addAnimation(0, "Skill_Loop", true, 0);
            }
        }
        if (this.nextMove == 1) {
            addToBot(new TalkAction(this, DIALOG[0], 0.3F, 3.0F));
            AbstractMonster ms = AbstractDungeon.getMonsters().getMonster("nearlmod:WitheredKnight");
            if (ms != null)
                addToBot(new TalkAction(ms, DIALOG[1], 0.3F, 3.0F));
        }
    }

    @Override
    protected void getMove(int i) {
        setMove((byte) 1, Intent.ATTACK, this.damage.get(0).base);
    }

    public void die() {
        super.die();
        for (AbstractMonster ms : AbstractDungeon.getMonsters().monsters)
            if (ms instanceof WitheredKnight && !ms.isDeadOrEscaped()) {
                ms.getPower("nearlmod:DoubleBoss").onSpecificTrigger();
                ((WitheredKnight) ms).CorruptedDead();
            }
    }

    public void WhitheredDead() {
        isWhitheredDead = true;
        addToBot(new TalkAction(this, DIALOG[2], 0.3F, 3.0F));
        if (this.nextMove == 2) {
            setMove(MOVES[0], (byte) 2, Intent.ATTACK, this.damage.get(1).base, 2, true);
            this.createIntent();
        }
        else {
            setMove(this.nextMove, Intent.ATTACK, this.damage.get(0).base, 2, true);
            this.createIntent();
        }
    }
}
