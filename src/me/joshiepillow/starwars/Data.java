package me.joshiepillow.starwars;

import me.joshiepillow.starwars.classes.BountyHunter;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class Data implements Serializable {
    private static transient final long serialVersionUID = -1681012206529286330L;

    public boolean saveData(String filePath) {
        try {
            BukkitObjectOutputStream out = new BukkitObjectOutputStream(new GZIPOutputStream(new FileOutputStream(filePath)));
            out.writeObject(this);
            out.close();
            System.out.println("-- GALAXIES HORIZON PLUGIN DATA SAVED SUCCESSFULLY --");
            return true;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("-- GALAXIES HORIZON SAVE FAILED --");
            return false;
        }
    }

    public static Data loadData(String filePath) {
        try {
            BukkitObjectInputStream in = new BukkitObjectInputStream(new GZIPInputStream(new FileInputStream(filePath)));
            Data data = (Data) in.readObject();
            in.close();
            return data;
        } catch (ClassNotFoundException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    public final List<BountyHunter> hunters;

    // Can be used for saving
    public Data(List<BountyHunter> hunters) {
        this.hunters = hunters;

    }
    // Can be used for loading
    public Data(Data loadedData) {
        if (loadedData.hunters != null)
            this.hunters = loadedData.hunters;
        else
            this.hunters = new ArrayList<>();
    }

}
