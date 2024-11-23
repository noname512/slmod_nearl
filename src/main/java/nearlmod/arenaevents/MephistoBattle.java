package nearlmod.arenaevents;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.colorless.Panacea;
import com.megacrit.cardcrawl.cards.curses.Parasite;
import com.megacrit.cardcrawl.cards.curses.Regret;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import nearlmod.actions.SummonFriendAction;
import nearlmod.cards.AbstractNearlCard;
import nearlmod.cards.BattlefieldCulinarian;
import nearlmod.cards.NightScouringGleam;
import nearlmod.cards.special.Beginning;
import nearlmod.monsters.CorruptKnight;
import nearlmod.monsters.Mephisto;
import nearlmod.monsters.WitheredKnight;
import nearlmod.orbs.Blemishine;
import nearlmod.relics.LateLight;

public class MephistoBattle extends AbstractArenaEvent {
    public static final String ID = "nearlmod:MephistoBattle";
    public static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    public static final String NAME = eventStrings.NAME;
    public static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    public static final String[] OPTIONS = eventStrings.OPTIONS;

    public MephistoBattle() {
        super(NAME, DESCRIPTIONS[0], "resources/nearlmod/images/events/corruptedwitheredbattle.png");
        this.imageEventText.setDialogOption(OPTIONS[0]);
        this.imageEventText.setDialogOption(OPTIONS[1], CardLibrary.getCopy("Regret"), new LateLight());
        noCardsInRewards = true;
    }

    @Override
    protected void buttonEffect(int buttonPressed) {
        if (screen != CurScreen.INTRO) {
            openMap();
            return;
        }
        switch (buttonPressed) {
            case 0:
                logMetric(ID, "Fight");
                CardCrawlGame.music.fadeOutTempBGM();
                CardCrawlGame.music.playTempBgmInstantly("m_bat_putrid_combine.mp3", true);
                screen = CurScreen.FIGHT;
                AbstractDungeon.getCurrRoom().rewards.clear();
                AbstractNearlCard.addSpecificCardsToReward(new BattlefieldCulinarian());
                int gold = 48;
                if (AbstractDungeon.ascensionLevel >= 13) gold = 36;
                AbstractDungeon.getCurrRoom().addGoldToRewards(gold);
                AbstractDungeon.lastCombatMetricKey = ID;
                AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMBAT;
                AbstractDungeon.getCurrRoom().monsters = new MonsterGroup(new AbstractMonster[] { new Mephisto(100.0F, 0.0F)});
                enterCombatFromImage();
                return;
            case 1:
                logMetric(ID, "Leave");
                screen = CurScreen.LEAVE;
                imageEventText.updateBodyText(DESCRIPTIONS[1]);
                imageEventText.updateDialogOption(0, OPTIONS[2]);
                imageEventText.clearRemainingOptions();
                AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Parasite(), Settings.WIDTH / 2.0F - 350.0F * Settings.xScale, Settings.HEIGHT / 2.0F));
                AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Parasite(), Settings.WIDTH / 2.0F -   0.0F * Settings.xScale, Settings.HEIGHT / 2.0F));
                AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Parasite(), Settings.WIDTH / 2.0F + 350.0F * Settings.xScale, Settings.HEIGHT / 2.0F));
                openMap();
                return;
        }
        openMap();
    }
}
