package com.icloud.kayukawakaoru;

import club.minnced.discord.webhook.WebhookClient;
import club.minnced.discord.webhook.WebhookClientBuilder;
import club.minnced.discord.webhook.send.WebhookMessageBuilder;
import org.apache.commons.lang.ObjectUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.AbstractMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class MinecraftListener implements Listener {
    private String webHookUrl;
    private Timer shutdownTimer = new Timer();;
    private DiscordSender discordSender;

    public void setWebHookUrl(String _url) {
        webHookUrl = _url;
    }
    public void setDiscordSender(DiscordSender s){
        discordSender = s;
    }

    private Map<String, String> localAdvancement = Map.ofEntries(
            new AbstractMap.SimpleEntry<String, String>("story/root", "Minecraft"),
            new AbstractMap.SimpleEntry<String, String>("story/mine_stone", "石器時代"),
            new AbstractMap.SimpleEntry<String, String>("story/upgrade_tools", "アップグレード"),
            new AbstractMap.SimpleEntry<String, String>("story/smelt_iron", "金属を手に入れる"),
            new AbstractMap.SimpleEntry<String, String>("story/obtain_armor", "装備せよ"),
            new AbstractMap.SimpleEntry<String, String>("story/lava_bucket", "ホットスタッフ"),
            new AbstractMap.SimpleEntry<String, String>("story/iron_tools", "鉄のツルハシで決まり"),
            new AbstractMap.SimpleEntry<String, String>("story/deflect_arrow", "今日はやめておきます"),
            new AbstractMap.SimpleEntry<String, String>("story/form_obsidian", "アイス・バケツ・チャレンジ"),
            new AbstractMap.SimpleEntry<String, String>("story/mine_diamond", "ダイヤモンド！"),
            new AbstractMap.SimpleEntry<String, String>("story/enter_the_nether", "さらなる深みへ"),
            new AbstractMap.SimpleEntry<String, String>("story/shiny_gear", "ダイヤモンドで私を覆って"),
            new AbstractMap.SimpleEntry<String, String>("story/enchant_item", "エンチャントの使い手"),
            new AbstractMap.SimpleEntry<String, String>("story/cure_zombie_villager", "ゾンビドクター"),
            new AbstractMap.SimpleEntry<String, String>("story/follow_ender_eye", "アイ・スパイ"),
            new AbstractMap.SimpleEntry<String, String>("story/enter_the_end", "おしまい？"),
            new AbstractMap.SimpleEntry<String, String>("nether/root", "ネザー"),
            new AbstractMap.SimpleEntry<String, String>("nether/return_to_sender", "差出人に返送"),
            new AbstractMap.SimpleEntry<String, String>("nether/find_bastion", "兵どもが夢の跡"),
            new AbstractMap.SimpleEntry<String, String>("nether/obtain_ancient_debris", "深淵に隠されしもの"),
            new AbstractMap.SimpleEntry<String, String>("nether/fast_travel", "亜空間バブル"),
            new AbstractMap.SimpleEntry<String, String>("nether/find_fortress", "恐ろしい要塞"),
            new AbstractMap.SimpleEntry<String, String>("nether/obtain_crying_obsidian", "玉ねぎを切っているのは誰？"),
            new AbstractMap.SimpleEntry<String, String>("nether/distract_piglin", "わーいぴかぴか！"),
            new AbstractMap.SimpleEntry<String, String>("nether/ride_strider", "足の生えたボート"),
            new AbstractMap.SimpleEntry<String, String>("nether/uneasy_alliance", "不安な同盟"),
            new AbstractMap.SimpleEntry<String, String>("nether/loot_bastion", "ブタ戦争"),
            new AbstractMap.SimpleEntry<String, String>("nether/use_lodestone", "この道をずっとゆけば"),
            new AbstractMap.SimpleEntry<String, String>("nether/netherite_armor", "残骸で私を覆って"),
            new AbstractMap.SimpleEntry<String, String>("nether/get_wither_skull", "不気味で怖いスケルトン"),
            new AbstractMap.SimpleEntry<String, String>("nether/obtain_blaze_rod", "炎の中へ"),
            new AbstractMap.SimpleEntry<String, String>("nether/charge_respawn_anchor", "不死身とまではいかない"),
            new AbstractMap.SimpleEntry<String, String>("nether/explore_nether", "ホットな観光地"),
            new AbstractMap.SimpleEntry<String, String>("nether/summon_wither", "荒が丘"),
            new AbstractMap.SimpleEntry<String, String>("nether/brew_potion", "町のお薬屋さん"),
            new AbstractMap.SimpleEntry<String, String>("nether/create_beacon", "生活のビーコン"),
            new AbstractMap.SimpleEntry<String, String>("nether/all_potions", "猛烈なカクテル"),
            new AbstractMap.SimpleEntry<String, String>("nether/create_full_beacon", "ビーコネーター"),
            new AbstractMap.SimpleEntry<String, String>("nether/all_effects", "どうやってここまで？"),
            new AbstractMap.SimpleEntry<String, String>("end/root", "ジ・エンド"),
            new AbstractMap.SimpleEntry<String, String>("end/kill_dragon", "エンドの解放"),
            new AbstractMap.SimpleEntry<String, String>("end/dragon_egg", "ザ・ネクストジェネレーション"),
            new AbstractMap.SimpleEntry<String, String>("end/enter_end_gateway", "遠方への逃走"),
            new AbstractMap.SimpleEntry<String, String>("end/respawn_dragon", "おしまい…再び…"),
            new AbstractMap.SimpleEntry<String, String>("end/dragon_breath", "口臭に気をつけよう"),
            new AbstractMap.SimpleEntry<String, String>("end/find_end_city", "ゲームの果ての都市"),
            new AbstractMap.SimpleEntry<String, String>("end/elytra", "空はどこまでも高く"),
            new AbstractMap.SimpleEntry<String, String>("end/levitate", "ここからの素晴らしい"),
            new AbstractMap.SimpleEntry<String, String>("adventure/root", "冒険"),
            new AbstractMap.SimpleEntry<String, String>("adventure/voluntary_exile", "自主的な亡命"),
            new AbstractMap.SimpleEntry<String, String>("adventure/kill_a_mob", "モンスターハンター"),
            new AbstractMap.SimpleEntry<String, String>("adventure/trade", "良い取引だ！"),
            new AbstractMap.SimpleEntry<String, String>("adventure/honey_block_slide", "べとべとな状況"),
            new AbstractMap.SimpleEntry<String, String>("adventure/ol_betsy", "おてんば"),
            new AbstractMap.SimpleEntry<String, String>("adventure/sleep_in_bed", "良い夢見てね"),
            new AbstractMap.SimpleEntry<String, String>("adventure/hero_of_the_village", "村の英雄"),
            new AbstractMap.SimpleEntry<String, String>("adventure/throw_trident", "もったいぶった一言"),
            new AbstractMap.SimpleEntry<String, String>("adventure/shoot_arrow", "狙いを定めて"),
            new AbstractMap.SimpleEntry<String, String>("adventure/kill_all_mobs", "モンスター狩りの達人"),
            new AbstractMap.SimpleEntry<String, String>("adventure/totem_of_undying", "死を超えて"),
            new AbstractMap.SimpleEntry<String, String>("adventure/summon_iron_golem", "お手伝いさん"),
            new AbstractMap.SimpleEntry<String, String>("adventure/two_birds_one_arrow", "一石二鳥"),
            new AbstractMap.SimpleEntry<String, String>("adventure/whos_the_pillager_now", "どっちが略奪者？"),
            new AbstractMap.SimpleEntry<String, String>("adventure/arbalistic", "クロスボウの達人"),
            new AbstractMap.SimpleEntry<String, String>("adventure/adventuring_time", "冒険の時間"),
            new AbstractMap.SimpleEntry<String, String>("adventure/very_very_frightening", "とてもとても恐ろしい"),
            new AbstractMap.SimpleEntry<String, String>("adventure/sniper_duel", "スナイパー対決"),
            new AbstractMap.SimpleEntry<String, String>("adventure/avoid_vibration", "スニーク100"),
            new AbstractMap.SimpleEntry<String, String>("adventure/fall_from_world_height", "洞窟と崖"),
            new AbstractMap.SimpleEntry<String, String>("adventure/kill_mob_near_sculk_catalyst", "「それ」は侵食する"),
            new AbstractMap.SimpleEntry<String, String>("adventure/walk_on_powder_snow_with_leather_boots", "ウサギのように軽く"),
            new AbstractMap.SimpleEntry<String, String>("adventure/spyglass_at_ghast", "あれは風船？"),
            new AbstractMap.SimpleEntry<String, String>("adventure/spyglass_at_dragon", "あれは飛行機？"),
            new AbstractMap.SimpleEntry<String, String>("adventure/spyglass_at_parrot", "あれは鳥？"),
            new AbstractMap.SimpleEntry<String, String>("adventure/trade_at_world_height", "星の商人"),
            new AbstractMap.SimpleEntry<String, String>("adventure/play_jukebox_in_meadows", "サウンド・オブ・ミュージック"),
            new AbstractMap.SimpleEntry<String, String>("adventure/lightning_rod_with_villager_no_fire", "危機一髪"),
            new AbstractMap.SimpleEntry<String, String>("adventure/bullseye", "的中"),
            new AbstractMap.SimpleEntry<String, String>("husbandry/root", "農業"),
            new AbstractMap.SimpleEntry<String, String>("husbandry/safely_harvest_honey", "秘蜜の晩餐会"),
            new AbstractMap.SimpleEntry<String, String>("husbandry/breed_an_animal", "コウノトリの贈り物"),
            new AbstractMap.SimpleEntry<String, String>("husbandry/tame_an_animal", "永遠の親友となるだろう"),
            new AbstractMap.SimpleEntry<String, String>("husbandry/fishy_business", "生臭い仕事"),
            new AbstractMap.SimpleEntry<String, String>("husbandry/silk_touch_nest", "綿蜜に引越し"),
            new AbstractMap.SimpleEntry<String, String>("husbandry/plant_seed", "種だらけの場所"),
            new AbstractMap.SimpleEntry<String, String>("husbandry/bred_all_animals", "二匹ずつ"),
            new AbstractMap.SimpleEntry<String, String>("husbandry/complete_catalogue", "猫大全集"),
            new AbstractMap.SimpleEntry<String, String>("husbandry/tactical_fishing", "戦術的漁業"),
            new AbstractMap.SimpleEntry<String, String>("husbandry/balanced_diet", "バランスの取れた食事"),
            new AbstractMap.SimpleEntry<String, String>("husbandry/obtain_netherite_hoe", "真面目な献身"),
            new AbstractMap.SimpleEntry<String, String>("husbandry/ride_a_boat_with_a_goat", "あなたのヤギたい様に！"),
            new AbstractMap.SimpleEntry<String, String>("husbandry/wax_on", "錆止め"),
            new AbstractMap.SimpleEntry<String, String>("husbandry/wax_off", "錆止め落とし"),
            new AbstractMap.SimpleEntry<String, String>("husbandry/axolotl_in_a_bucket", "いちばんカワイイ捕食者"),
            new AbstractMap.SimpleEntry<String, String>("husbandry/kill_axolotl_target", "友情の癒しパワー！"),
            new AbstractMap.SimpleEntry<String, String>("husbandry/allay_deliver_item_to_player", "君はともだち"),
            new AbstractMap.SimpleEntry<String, String>("husbandry/make_a_sign_glow", "この輝きに驚くことなかれ！"),
            new AbstractMap.SimpleEntry<String, String>("husbandry/tadpole_in_a_bucket", "おけおけ"),
            new AbstractMap.SimpleEntry<String, String>("husbandry/allay_deliver_cake_to_note_block", "バースデー・ソング"),
            new AbstractMap.SimpleEntry<String, String>("husbandry/leash_all_frog_variants", "みんなで町に跳び込もう"),
            new AbstractMap.SimpleEntry<String, String>("husbandry/froglights", "僕たちの力を合わせて！"),
            new AbstractMap.SimpleEntry<String, String>("nether/ride_strider_in_overworld_lava", "実家のような安心感")
    );

    @EventHandler
    public void onMessage(AsyncPlayerChatEvent e) {
        //channel.sendMessage(createEmbed(e.getPlayer().getDisplayName(),e.getMessage())).queue();
        send(webHookUrl, e.getPlayer().getDisplayName(), e.getMessage(), e.getPlayer().getUniqueId().toString());
        //Bukkit.getServer().getLogger().info(e.getMessage());
    }

    @EventHandler
    public void onJoin(AsyncPlayerPreLoginEvent e) {
        send(webHookUrl, e.getName(), e.getName() + "がゲームに参加しました", e.getUniqueId().toString());
        shutdownTimer.cancel();
    }

    @EventHandler
    public void onLeft(PlayerQuitEvent e) {
        send(webHookUrl, e.getPlayer().getName(), e.getPlayer().getName() + "がゲームから退出しました", e.getPlayer().getUniqueId().toString());
        Bukkit.getLogger().info("Player Number"+Bukkit.getServer().getOnlinePlayers().size());
        if(Bukkit.getServer().getOnlinePlayers().size()==1){
            shutdownTimer = new Timer();
            shutdownTimer.schedule(
                new TimerTask() {
                public void run(){
                    discordSender.send();
                }
            }
            ,600000);
        }
    }

    @EventHandler
    public void onAchieve(PlayerAdvancementDoneEvent e) {
        if (e.getAdvancement().getKey().getKey().contains("recipes")) return;
        send(webHookUrl, e.getPlayer().getName(), e.getPlayer().getName() + "が進捗「" +localAdvancement.get( e.getAdvancement().getKey().getKey() )+ "」を達成しました。", e.getPlayer().getUniqueId().toString());
    }

    @EventHandler
    public void onKilledByPlayer(PlayerDeathEvent e){
        if(e.getEntity().getKiller() == null) return;
        send(webHookUrl, e.getEntity().getName(),e.getEntity().getKiller().getName()+"が私を殺しました。",e.getEntity().getUniqueId().toString());
    }

    public void send(String url, String name, String context, String uuid) {
        if(name == null || context == null || uuid == null) {
            Bukkit.getLogger().warning("HOGEHOGE");
            return;
        }
        WebhookClientBuilder builder = new WebhookClientBuilder(url);
        builder.setThreadFactory(
                (job) -> {
                    Thread thread = new Thread(job);
                    thread.setName("WebHook");
                    return thread;
                });
        builder.setWait(true);
        WebhookMessageBuilder messageBuilder = new WebhookMessageBuilder();
        messageBuilder.setUsername(name); // use this username
        messageBuilder.setAvatarUrl("https://mc-heads.net/avatar/" + uuid); // use this avatar
        messageBuilder.setContent(context);
        WebhookClient client = builder.build();
        client.send(messageBuilder.build());
        client.close();
    }

}
