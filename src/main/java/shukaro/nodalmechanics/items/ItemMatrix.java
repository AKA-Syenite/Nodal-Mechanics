package shukaro.nodalmechanics.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gnu.trove.map.TMap;
import gnu.trove.map.hash.THashMap;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import org.apache.commons.lang3.text.WordUtils;
import shukaro.nodalmechanics.NodalMechanics;
import shukaro.nodalmechanics.util.FormatCodes;
import thaumcraft.api.aspects.Aspect;

import java.util.List;
import java.util.Locale;

public class ItemMatrix extends Item
{
    private IIcon icon;

    public ItemMatrix()
    {
        super();
        this.setHasSubtypes(true);
        this.setCreativeTab(NodalMechanics.mainTab);
        this.setUnlocalizedName("nodalmechanics.matrix");
    }

    @Override
    public String getUnlocalizedName(ItemStack stack)
    {
        if (stack.hasTagCompound())
            return this.getUnlocalizedName() + ".attuned";
        else
            return this.getUnlocalizedName() + ".unattuned";
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item id, CreativeTabs tab, List list)
    {
        list.add(new ItemStack(id, 1, 0));
        for (Aspect aspect : Aspect.getPrimalAspects())
        {
            ItemStack stack = new ItemStack(id, 1, 0);
            NBTTagCompound tag = new NBTTagCompound();
            tag.setString("aspects", multiplyAspect(aspect.getTag()));
            stack.setTagCompound(tag);
            list.add(stack);
        }
    }

    private String multiplyAspect(String aspect)
    {
        return aspect + "," + aspect + "," + aspect + "," + aspect + "," + aspect + "," + aspect + "," + aspect + "," + aspect;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int meta)
    {
        return this.icon;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister reg)
    {
        this.icon = reg.registerIcon(NodalMechanics.modID.toLowerCase(Locale.ENGLISH) + ":" + "itemMatrix");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public EnumRarity getRarity(ItemStack stack)
    {
        return stack.hasTagCompound() ? EnumRarity.uncommon : EnumRarity.common;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack stack, int pass)
    {
        return stack.hasTagCompound();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List infoList, boolean advancedTooltips)
    {
        if (stack.hasTagCompound() && stack.getTagCompound().hasKey("aspects"))
        {
            TMap<String, Integer> aspectMap = new THashMap<String, Integer>();
            for (String s : stack.getTagCompound().getString("aspects").split(","))
            {
                if (!aspectMap.containsKey(s))
                    aspectMap.put(s, 1);
                else
                    aspectMap.put(s, aspectMap.get(s) + 1);
            }
            for (String s : aspectMap.keySet())
                infoList.add("\u00A7" + (Aspect.getAspect(s).getChatcolor() != null ? Aspect.getAspect(s).getChatcolor() : "7") + WordUtils.capitalize(s) + FormatCodes.Reset.code + FormatCodes.White.code + " x " + aspectMap.get(s));

            infoList.add(FormatCodes.DarkGrey.code + FormatCodes.Italic.code + StatCollector.translateToLocal("tooltip.nodalmechanics.matrix.attuned"));
        }
        else
            infoList.add(FormatCodes.DarkGrey.code + FormatCodes.Italic.code + StatCollector.translateToLocal("tooltip.nodalmechanics.matrix.unattuned"));
    }
}
