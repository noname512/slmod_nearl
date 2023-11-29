package nearlmod.arenaevents;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import nearlmod.cards.AbstractNearlCard;
import nearlmod.cards.WayToChampion;
import nearlmod.monsters.BloodKnight;
import nearlmod.relics.BloodEntangle;

public class BloodKnightBattle extends AbstractImageEvent {
    public static final String ID = "nearlmod:BloodKnightBattle";
    public static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    public static final String NAME = eventStrings.NAME;
    public static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    public static final String[] OPTIONS = eventStrings.OPTIONS;
    public BloodKnightBattle() {
        super(NAME, DESCRIPTIONS[0], "images/events/laughallyouwant.png");
        this.imageEventText.setDialogOption(OPTIONS[0]);
        this.imageEventText.setDialogOption(OPTIONS[1]);
    }

    @Override
    protected void buttonEffect(int buttonPressed) {
        switch (buttonPressed) {
            case 0:
                logMetric(ID, "Fight");
                AbstractDungeon.getCurrRoom().rewards.clear();
                AbstractCard c = new WayToChampion();
                if (AbstractDungeon.ascensionLevel < 12) c.upgrade();
                AbstractNearlCard.addSpecificCardsToReward(c);
                AbstractDungeon.getCurrRoom().addRelicToRewards(AbstractRelic.RelicTier.BOSS);
                AbstractDungeon.getCurrRoom().addGoldToRewards(200); // TODO 确定金币数量
                AbstractDungeon.lastCombatMetricKey = ID;
                AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMBAT;
                AbstractDungeon.getCurrRoom().monsters = new MonsterGroup(new BloodKnight(0.0F, 0.0F));
                enterCombatFromImage();
                return;
            case 1:
                logMetric(ID, "Leave");
                this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
                imageEventText.updateDialogOption(0, OPTIONS[2]);
                imageEventText.clearRemainingOptions();
                AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F, new BloodEntangle());
                openMap();
                return;
        }
        openMap();
    }
}
