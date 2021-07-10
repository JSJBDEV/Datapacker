package gd.rf.acro.datapacker;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.scoreboard.ScoreboardCriterion;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.LiteralText;
import net.minecraft.world.World;
import org.apache.commons.lang3.RandomUtils;

import java.util.List;
import java.util.stream.Collectors;

public class BandSystem {
    public static void makeBand(PlayerEntity leader, ServerCommandSource source)
    {
        if(!leader.getScoreboardTags().contains("_joined"))
        {
            int v = RandomUtils.nextInt(0,99999);
            leader.addScoreboardTag(v+"_joined_boss");
            source.sendFeedback(new LiteralText("New band created! no."+v),true);
        }
        else
        {
            source.sendError(new LiteralText("You must leave your current band to make a new one!"));
        }
    }
    public static void inviteToBand(PlayerEntity member,PlayerEntity recruit, ServerCommandSource source)
    {
        if(member.getScoreboardTags().contains("_boss"))
        {
            String v = getBandFor(member);
            recruit.addScoreboardTag(v+"_invited");
            source.sendFeedback(new LiteralText("Invited "+recruit.getName()+" to the band!"),false);
            recruit.sendMessage(new LiteralText("You have been invited to band No."+v+" by "+member+" !"),false);
        }
        else
        {
            source.sendError(new LiteralText("Only a band leader can do this action!"));
        }

    }
    public static void joinBand(PlayerEntity recruit, String band, ServerCommandSource source)
    {
        if(recruit.getScoreboardTags().contains(band+"_invited"))
        {
            if (!recruit.getScoreboardTags().contains("_joined"))
            {
                recruit.removeScoreboardTag(band+"_invited");
                recruit.addScoreboardTag(band+"_joined");
                source.sendFeedback(new LiteralText("You join the band!"),false);
            }
            else
            {
                source.sendError(new LiteralText("You must leave your current band to join a new one!"));
            }
        }
        else
        {
            source.sendError(new LiteralText("You do not have an invite from that band!"));
        }
    }
    public static void leaveBand(PlayerEntity member, ServerCommandSource source)
    {
        String v = getBandFor(member);
        if(member.getScoreboardTags().contains(v+"_joined"))
        {
            member.removeScoreboardTag(v+"_joined");
            source.sendFeedback(new LiteralText("You left the band!"),false);
        }
        else
        {
            source.sendError(new LiteralText("You are not in a band!"));
        }
    }
    public static void kickFromBand(PlayerEntity member, PlayerEntity recruit, ServerCommandSource source)
    {
        String v = getBandFor(member);
        if(member.getScoreboardTags().contains(v+"_joined_boss") && recruit.getScoreboardTags().contains(v+"_joined"))
        {
            recruit.removeScoreboardTag(v+"_joined");
            source.sendFeedback(new LiteralText("You kicked "+recruit.getName()+" from the band!"),false);
        }
        else
        {
            source.sendError(new LiteralText("You are not in a band!"));
        }
    }
    public static void rejectBand(PlayerEntity member, String band, ServerCommandSource source)
    {
        if(member.getScoreboardTags().contains(band+"_invited"))
        {
            member.removeScoreboardTag(band+"_invited");
            source.sendFeedback(new LiteralText("You rejected that band invitation!"),false);
        }
        else
        {
            source.sendError(new LiteralText("You do not have an invite from that band!"));
        }
    }

    public static String getBandFor(PlayerEntity entity)
    {
        return entity.getScoreboardTags().stream().filter(s->s.contains("_joined")).findFirst().orElse("none").replace("_joined","");
    }
    public static List<String> getInvitesFor(PlayerEntity entity)
    {
        return entity.getScoreboardTags().stream().filter(s->s.contains("_invited")).collect(Collectors.toList());
    }
}
