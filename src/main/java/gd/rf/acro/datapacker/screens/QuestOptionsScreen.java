package gd.rf.acro.datapacker.screens;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonListWidget;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.Option;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;

public class QuestOptionsScreen extends Screen {
    public QuestOptionsScreen() {
        super(new LiteralText("OptionsQuests"));
    }

    @Override
    protected void init() {
        super.init();
        this.addDrawableChild(new ButtonWidget(width/2,20,100,20,new LiteralText("Make New Band"),f->{}));
        this.addDrawableChild(new ButtonWidget(width/2,60,100,20,new LiteralText("Invite Player to Band"),f->{}));
        this.addDrawableChild(new ButtonWidget(width/2,100,100,20,new LiteralText("Join a Band"),f->{}));



    }
}
