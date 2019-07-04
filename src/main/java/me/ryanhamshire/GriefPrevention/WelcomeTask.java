package me.ryanhamshire.GriefPrevention;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFactory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

public class WelcomeTask implements Runnable
{
    private Player player;
    
    public WelcomeTask(Player player)
    {
        this.player = player;
    }
    
    @Override
    public void run()
    {
        //abort if player has logged out since this task was scheduled
        if(!this.player.isOnline()) return;
        
        //offer advice and a helpful link
        GriefPrevention.sendMessage(player, TextMode.Instr, Messages.AvoidGriefClaimLand);
        GriefPrevention.sendMessage(player, TextMode.Instr, Messages.SurvivalBasicsVideo2, DataStore.SURVIVAL_VIDEO_URL);
        
        //give the player a reference book for later
        if(GriefPrevention.instance.config_claims_supplyPlayerManual)
        {
            ItemFactory factory = Bukkit.getItemFactory();
            BookMeta meta = (BookMeta) factory.getItemMeta(Material.WRITTEN_BOOK);

            DataStore datastore = GriefPrevention.instance.dataStore;
            meta.setAuthor(datastore.getMessage(Messages.BookAuthor));
            meta.setTitle(datastore.getMessage(Messages.BookTitle));
            
            StringBuilder page1 = new StringBuilder();
            String URL = datastore.getMessage(Messages.BookLink, DataStore.SURVIVAL_VIDEO_URL);
            String intro = datastore.getMessage(Messages.BookIntro);
            
            page1.append(intro).append("\n\n");
            String editToolName = GriefPrevention.instance.config_claims_modificationTool.name().replace('_', ' ').toLowerCase();
            String infoToolName = GriefPrevention.instance.config_claims_investigationTool.name().replace('_', ' ').toLowerCase();
            String configClaimTools = datastore.getMessage(Messages.BookTools, editToolName, infoToolName);
            page1.append(configClaimTools);
            if(GriefPrevention.instance.config_claims_automaticClaimsForNewPlayersRadius < 0)
            {
                page1.append(datastore.getMessage(Messages.BookDisabledChestClaims));
            }
            
            
            StringBuilder page2 = new StringBuilder(datastore.getMessage(Messages.BookUsefulCommands)).append("\n\n");
            page2.append("/AccessTrust\n");
            page2.append("/ContainerTrust\n");
            page2.append("/PermissionTrust");
            page2.append("/Trust /UnTrust /TrustList\n");
            page2.append("/ClaimList\n");
            page2.append("/Claim /ExtendClaim\n\n");
            page2.append(URL).append("\n");
            
            StringBuilder page3 = new StringBuilder("How To Play").append("\n\n");
            page3.append("During your first 30 minutes, gather resources and play as normal SMP. ");
            page3.append("Any time after your first 30 minutes, a Draft may happen. When this happens, ");
            page3.append("You will be selected for a team.");
            
            StringBuilder page4 = new StringBuilder("Teams").append("\n\n");
            page4.append("Once on a team, you should listen to directions from your Team's captain.");
            page4.append("You will be directed to your Team's Team-Base, where most things will take place with your team.");


            StringBuilder page5 = new StringBuilder("Team Score").append("\n\n");
            page5.append("Your Team has a Score, shown on the right side of your screen.");
            page5.append("Your TeamScore changes based on kills and deaths. When you or a teammate dies, ");
            page5.append("your score decreases by 1. When you or a teammate get a kill, it increases by 3.");

            StringBuilder page6 = new StringBuilder("Sieging").append("\n\n");
            page6.append("Sieging is an event in which someone from a different team starts a siege against you while you are home. ");
            page6.append("During the siege, the attacker can break 'weak' blocks such as sand, grass, cobblestone, stone, and glass.");

            StringBuilder page7 = new StringBuilder("Sieging (cont.)").append("\n\n");
            page7.append("The attacker can also open doors. You and your teammates must defend the base against the attacker. ");
            page7.append("The siege ends when the attacker dies, the base-owner dies, or one of them runs away or escapes.");

            StringBuilder page8 = new StringBuilder("Team Bases").append("\n\n");
            page8.append("Team Bases are bases run by your Team Captain (The Permanent Member on your team). ");
            page8.append("They are the central meeting place of the team, and any other outposts the team owns should be secondary to this.");
            
            StringBuilder page9 = new StringBuilder("Server Builds").append("\n\n");
            page9.append("Because this is still SMP, Griefing is not allowed. Most builds that are not bases will be protected by server-claims. ");
            page9.append("Currently, the most prominent example is the Community Blaze Farm in the nether, which is protected.");
            
            StringBuilder page10 = new StringBuilder("Ready to Play").append("\n\n");
            page10.append("Now that you have read everything, you are ready to play. Type /ready in chat to leave spawn. Thank You for playing ShrekSMP. Good Luck and Have Fun!");
            
            
            meta.setPages(page1.toString(), page2.toString(), page3.toString(), page4.toString(), page5.toString(), page6.toString(), page7.toString(), page8.toString(), page9.toString(), page10.toString());

            ItemStack item = new ItemStack(Material.WRITTEN_BOOK);
            item.setItemMeta(meta);
            player.getInventory().addItem(item);
        }
        
    }
    

}
