package me.ryanmood.fateuhc.placeholders.fateuhcplaceholders;

import com.mongodb.BasicDBObject;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import net.fateuhc.plugin.api.FateUHCAPI;
import org.bson.Document;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PlaceHolderAPIExpansion extends PlaceholderExpansion {

   // private String leaderBoardFormat = "&9<pos>. &e<name>&7: &f<amount>";
    private String leaderBoardFormat = FateUHCPlaceholders.getInstance().getConfig().getString("leaderboardFormat");

    @Override
    public boolean canRegister(){
        return true;
    }

    @Override
    public boolean persist(){
        return true;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "FateUHC-Placeholders";
    }

    @Override
    public @NotNull String getAuthor() {
        return "RyanMoodGAMING";
    }

    @Override
    public @NotNull String getVersion() {
        return "0.1";
    }


    @Override
    public String onPlaceholderRequest(Player player, String identifier) {
        if (identifier.equalsIgnoreCase("leaderboard_wins")) {
            return getLeaderBoard(player, "wins");
        }
        if (identifier.equalsIgnoreCase("leaderboard_kills")) {
            return getLeaderBoard(player, "kills");
        }
        if (identifier.equalsIgnoreCase("leaderboard_killstreak")) {
            return getLeaderBoard(player, "killStreak");
        }
        if (identifier.equalsIgnoreCase("leaderboard_diamonds_mined")) {
            return getLeaderBoard(player, "diamondsMined");
        }
        if (identifier.equalsIgnoreCase("leaderboard_games_played")) {
            return getLeaderBoard(player, "gamesPlayed");
        }
        if (identifier.equalsIgnoreCase("leaderboard_golden_apples_eaten")) {
            return getLeaderBoard(player, "goldenApplesEaten");
        }
        return null;
    }

    public Map<String, Object> getDefaults() {
        return null;
    }

    private String getLeaderBoard(Player player, String dataLook) {
        List<Document> documents = FateUHCAPI.INSTANCE.getMongoManager().getStatsCollection().find().limit(3).sort(new BasicDBObject(dataLook, -1)).into(new ArrayList<>());
        String spotOne = ""; //= leaderBoardFormat.replace("<pos>", "1").replace("<name>", player.getName()).replace("<amount>", );
        String spotTwo = "";
        String spotThree = "";
        String returnValue = "";
        int pos = 1;
        for (Document document : documents) {
            if (!(pos >= 4)) {
                if (pos == 1) {
                    spotOne = leaderBoardFormat.replace("<pos>", String.valueOf(pos)).replace("<name>", document.getString("realName")).replace("<amount>", document.get(dataLook).toString());
                } else if (pos == 2) {
                    spotTwo = leaderBoardFormat.replace("<pos>", String.valueOf(pos)).replace("<name>", document.getString("realName")).replace("<amount>", document.get(dataLook).toString());
                } else if (pos == 3) {
                    spotThree = leaderBoardFormat.replace("<pos>", String.valueOf(pos)).replace("<name>", document.getString("realName")).replace("<amount>", document.get(dataLook).toString());
                }
                pos++;
            }
        }
        returnValue = spotOne + "\n" + spotTwo + "\n" + spotThree;
       // player.sendMessage("(Debug) " + returnValue);
        return returnValue;
    }

}
