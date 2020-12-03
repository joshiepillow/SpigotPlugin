package me.joshiepillow.starwars.classes;

public class Products {
    public static Product A180;
    public static Product A280;
    public static Product A280C;
    public static Product DL18;
    public static Product K16_Bryar;
    public static Product SE14C;
    public static Product A295;
    public static Product TL50;
    public static Product Cycler_Rifle;
    public static Product RelbyV10;
    public static Product Gaderffii;
    public static Product Gammorean_Axe;
    public static Product Mythosaur_Axe;
    public static Product Kyuso_Petar;
    public static Product Purple_Electro_Staff;
    public static Product Yellow_Electro_Staff;
    public static Product Vibro_Blade_1;
    public static Product Vibro_Blade_2;
    public static Product Vibro_Blade_3;
    public static Product Top_Hilt;
    public static Product Activation_Stud;
    public static Product Blade_Emitter;
    public static Product Bottom_Hilt;
    public static Product Jedi_Cap;
    public static Product Jedi_Tunic;
    public static Product Jedi_Pants;
    public static Product Jedi_Boots;
    public static Product CG_Helmet;
    public static Product CG_Chestplate;
    public static Product CG_Leggings;
    public static Product CG_Boots;
    public static Product _501th_Helmet;
    public static Product _501th_Chestplate;
    public static Product _501th_Leggings;
    public static Product _501th_Boots;
    public static Product Durasteel_Helmet;
    public static Product Durasteel_Chestplate;
    public static Product Durasteel_Leggings;
    public static Product Durasteel_Boots;
    public static Product Beskar_Helmet;
    public static Product Beskar_Chestplate;
    public static Product Beskar_Leggings;
    public static Product Beskar_Boots;

    public static void setItems() {
        A180 = Product.create("A180", 1000, "shot give %name a180");
        A280 = Product.create("A280", 1100, "shot give %name a280");
        A280C = Product.create("A280C", 1200, "shot give %name a280c");
        DL18 = Product.create("DL18", 1500, "shot give %name dl18");
        K16_Bryar = Product.create("K16_Bryar", 2500, "shot give %name k16");
        SE14C = Product.create("SE14C", 1250, "shot give %name se14");
        A295 = Product.create("A295", 1500, "shot give %name a295");
        TL50 = Product.create("TL50", 1750, "shot give %name tl50");
        Cycler_Rifle = Product.create("Cycler_Rifle", 2000, "shot give %name cycler");
        RelbyV10 = Product.create("RelbyV10", 3000, "shot give %name relby");

        Gaderffii = Product.create("Gaderffii", 3, "give %name wooden_sword{display:{Name:'{\"text\":\"§4Gaderffii\"}'},HideFlags:3,Unbreakable:1b,Enchantments:[{id:\"minecraft:flame\",lvl:1s}]} 1");
        Gammorean_Axe = Product.create("Gammorean_Axe", 15, "give %name wooden_axe{display:{Name:'{\"text\":\"§4Gammorean Axe\"}'},HideFlags:3,Unbreakable:1b,Enchantments:[{id:\"minecraft:knockback\",lvl:1s}],AttributeModifiers:[{AttributeName:\"generic.movement_speed\",Name:\"generic.movement_speed\",Amount:-0.25,Operation:1,UUID:[I;1756465379,-1127069635,-1142137308,1772638539],Slot:\"mainhand\"}]} 1");
        Mythosaur_Axe = Product.create("Mythosaur_Axe", 30, "give %name stone_sword{display:{Name:'{\"text\":\"§4Mythosaur Axe\"}'},HideFlags:3,Unbreakable:1b,Enchantments:[{id:\"minecraft:knockback\",lvl:2s}],AttributeModifiers:[{AttributeName:\"generic.movement_speed\",Name:\"generic.movement_speed\",Amount:-0.15,Operation:1,UUID:[I;-933672602,-92976853,-1837723013,123554692],Slot:\"mainhand\"}]} 1");
        Kyuso_Petar = Product.create("Kyuso_Petar", 60, "give %name stone_axe{display:{Name:'{\"text\":\"§4Kyuso Petar\"}'},HideFlags:3,Unbreakable:1b,Enchantments:[{id:\"minecraft:flame\",lvl:1s}],AttributeModifiers:[{AttributeName:\"generic.attack_speed\",Name:\"generic.attack_speed\",Amount:100000,Operation:0,UUID:[I;-933672602,-92976853,-1837723013,123554692],Slot:\"mainhand\"}]} 1");
        Yellow_Electro_Staff = Product.create("Yellow_Electro_Staff", 140, "give %name golden_axe{display:{Name:'{\"text\":\"§4Yellow Electro Staff\"}'},HideFlags:3,Unbreakable:1b,Enchantments:[{id:\"minecraft:flame\",lvl:1s}],AttributeModifiers:[{AttributeName:\"generic.attack_damage\",Name:\"generic.attack_damage\",Amount:1,Operation:0,UUID:[I;-933672602,-92976853,-1837723013,123554692],Slot:\"mainhand\"}]} 1");
        Purple_Electro_Staff = Product.create("Purple_Electro_Staff", 120, "give %name golden_sword{display:{Name:'{\"text\":\"§4Purple Electro Staff\"}'},HideFlags:3,Unbreakable:1b,Enchantments:[{id:\"minecraft:flame\",lvl:1s}],AttributeModifiers:[{AttributeName:\"generic.attack_damage\",Name:\"generic.attack_damage\",Amount:8,Operation:0,UUID:[I;-933672602,-92976853,-1837723013,123554692],Slot:\"mainhand\"}]} 1");
        Vibro_Blade_1 = Product.create("Vibro_Blade_1", 250, "give %name netherite_sword{display:{Name:'{\"text\":\"§4Vibro Blade #1\"}'},HideFlags:3,Unbreakable:1b,Enchantments:[{id:\"minecraft:flame\",lvl:1s}],AttributeModifiers:[{AttributeName:\"generic.movement_speed\",Name:\"generic.movement_speed\",Amount:1,Operation:2,UUID:[I;-933672602,-92976853,-1837723013,123554692],Slot:\"mainhand\"}]} 1");
        Vibro_Blade_2 = Product.create("Vibro_Blade_2", 250, "give %name diamond_axe{display:{Name:'{\"text\":\"§4Vibro Blade #2\"}'},HideFlags:3,Unbreakable:1b,Enchantments:[{id:\"minecraft:flame\",lvl:1s}],AttributeModifiers:[{AttributeName:\"generic.attack_damage\",Name:\"generic.attack_damage\",Amount:6,Operation:0,UUID:[I;-933672602,-92976853,-1837723013,123554692],Slot:\"mainhand\"}]} 1");
        Vibro_Blade_3 = Product.create("Vibro_Blade_3", 250, "give %name netherite_axe{display:{Name:'{\"text\":\"§4Vibro Blade #3\"}'},HideFlags:3,Unbreakable:1b,Enchantments:[{id:\"minecraft:knockback\",lvl:3s}],Name:\"generic.max_health\",Amount:0.5,Operation:2,UUID:[I;-933672602,-92976853,-1837723013,123554692],Slot:\"mainhand\"} 1");

        Top_Hilt = Product.create("Top_Hilt", 3000, "give %name brown_mushroom{display:{Name:'{\"text\":\"§1Top Hilt\"}'}} 1");
        Activation_Stud = Product.create("Activation_Stud", 500, "give %name gold_nugget{display:{Name:'{\"text\":\"§1Activation Stud\"}'}} 1");
        Blade_Emitter = Product.create("Blade_Emitter", 500, "give %name iron_nugget{display:{Name:'{\"text\":\"§1Blade Emitter\"}'}} 1");
        Bottom_Hilt = Product.create("Bottom_Hilt", 3000, "give %name red_mushroom{display:{Name:'{\"text\":\"§1Bottom Hilt\"}'}} 1");

        Jedi_Cap = Product.create("Jedi_Cap", 150, "give %name leather_helmet");
        Jedi_Tunic = Product.create("Jedi_Tunic", 170, "give %name leather_chestplate");
        Jedi_Pants = Product.create("Jedi_Pants", 160, "give %name leather_leggings");
        Jedi_Boots = Product.create("Jedi_Boots", 150, "give %name leather_boots");
        CG_Helmet = Product.create("CG_Helmet", 330, "give %name chainmail_helmet");
        CG_Chestplate = Product.create("CG_Chestplate", 350, "give %name chainmail_chestplate");
        CG_Leggings = Product.create("CG_Leggings", 340, "give %name chainmail_leggings");
        CG_Boots = Product.create("CG_Boots", 330, "give %name chainmail_boots");
        _501th_Helmet = Product.create("501th_Helmet", 700, "give %name iron_helmet");
        _501th_Chestplate = Product.create("501th_Chestplate", 1000, "give %name iron_chestplate");
        _501th_Leggings = Product.create("501th_Leggings", 850, "give %name iron_leggings");
        _501th_Boots = Product.create("501th_Boots", 700, "give %name iron_boots");
        Durasteel_Helmet = Product.create("Durasteel_Helmet", 1500, "give %name diamond_helmet");
        Durasteel_Chestplate = Product.create("Durasteel_Chestplate", 2000, "give %name diamond_chestplate");
        Durasteel_Leggings = Product.create("Durasteel_Leggings", 1850, "give %name diamond_leggings");
        Durasteel_Boots = Product.create("Durasteel_Boots", 1500, "give %name diamond_boots");
        Beskar_Helmet = Product.create("Beskar_Helmet", 4000, "give %name netherite_helmet");
        Beskar_Chestplate = Product.create("Beskar_Chestplate", 5000, "give %name netherite_chestplate");
        Beskar_Leggings = Product.create("Beskar_Leggings", 4500, "give %name netherite_leggings");
        Beskar_Boots = Product.create("Beskar_Boots", 4000, "give %name netherite_boots");

    }
}
