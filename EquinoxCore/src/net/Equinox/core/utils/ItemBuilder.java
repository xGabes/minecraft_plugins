package net.Equinox.core.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.material.MaterialData;

public class ItemBuilder 
{

    private ItemStack item;

    public ItemBuilder(ItemStack item) 
    {
        this.item = item;
    }

    public ItemBuilder(Material material) 
    {
        this.item = new ItemStack(material);
    }

    public ItemBuilder(Material material, int amount) 
    {
        this.item = new ItemStack(material, amount);
    }


    public ItemBuilder type(Material material)
    {
        item.setType(material);
        return this;
    }

    public ItemBuilder amount(int amt)
    {
        item.setAmount(amt);
        return this;
    }

    @SuppressWarnings("deprecation")
	public ItemBuilder data(int data)
    {
        MaterialData md = item.getData();
        md.setData((byte)data);
        item.setData(md);
        return this;
    }

    public ItemBuilder durability(int durability) {
        item.setDurability((short)durability);
        return this;
    }

    public ItemBuilder name(String name)
    {
        ItemMeta im = item.getItemMeta();
        im.setDisplayName(name);
        item.setItemMeta(im);
        return this;
    }

    public ItemBuilder lore(boolean append, String... lines) {
        List<String> lore = append && item.getItemMeta().hasLore() ? item.getItemMeta().getLore() : new ArrayList<String>();
        for(String str : lines)
            lore.add(str);
        ItemMeta im = item.getItemMeta();
        im.setLore(lore);
        im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        item.setItemMeta(im);
        return this;
    }

    public ItemBuilder enchant(Enchantment enchantment, int level) {
        this.item.addEnchantment(enchantment, level);
        return this;
    }
    
    public ItemBuilder unsafeEnchant(Enchantment enchant, int level)
    {
		this.item.addUnsafeEnchantment(enchant, level);
		return this;
    }

    public ItemBuilder enchant(Map<Enchantment, Integer> enchantments) {
        this.item.addEnchantments(enchantments);
        return this;
    }
    
    public ItemBuilder setOwner(OfflinePlayer toPunish)
    {
    	    SkullMeta meta = (SkullMeta) item.getItemMeta();
    	    meta.setOwningPlayer(toPunish);
    	    item.setItemMeta(meta);
    	    return this;
    }

    public ItemStack build() {
        return item;
    }

}
