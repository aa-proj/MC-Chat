package com.icloud.kayukawakaoru;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class NewTpCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
        if(command.getName().equalsIgnoreCase("tpa")){
            if(args.length == 0){
                sender.sendMessage("テレポートを要求する相手を指定してください");
                return false;
            }
            Player receiver = Bukkit.getPlayer(args[0]);
            if(receiver == null){
                sender.sendMessage("テレポート相手が見つかりませんでした");
                return false;
            }
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"tp "+sender.getName()+" "+receiver.getName());
            return true;
        }
        return false;
    }
}
